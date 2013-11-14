/*
	This file is part of the OpenGLCore library.
	Copyright (C) 2010-2012 Benjamin Eikel <benjamin@eikel.org>
	Copyright (C) 2010-2011 Robert Gmyr

	This library is subject to the terms of the Mozilla Public License, v. 2.0.
	You should have received a copy of the MPL along with this library; see the
	file LICENSE. If not, you can obtain one at http://mozilla.org/MPL/2.0/.
*/
#include "de_upb_mobilerendering_gl_OpenGLCore.h"

#include <MinSG/Core/Behaviours/BehaviourManager.h>
#include <MinSG/Core/Nodes/CameraNode.h>
#include <MinSG/Core/Nodes/LightNode.h>
#include <MinSG/Core/Nodes/ListNode.h>
#include <MinSG/Core/Nodes/Node.h>
#include <MinSG/Core/States/LightingState.h>
#include <MinSG/Core/FrameContext.h>
#include <MinSG/Core/Statistics.h>

//#include <MinSG/Ext/OutOfCore/CacheManager.h>
//#include <MinSG/Ext/OutOfCore/DataStrategy.h>
//#include <MinSG/Ext/OutOfCore/OutOfCore.h>
#include <MinSG/Ext/SphericalSampling/BudgetRenderer.h>
#include <MinSG/Ext/SphericalSampling/Definitions.h>
#include <MinSG/Ext/SphericalSampling/Renderer.h>
#include <MinSG/Ext/Waypoints/FollowPathBehaviour.h>
#include <MinSG/Ext/Waypoints/PathNode.h>

#include <MinSG/Helper/Helper.h>
#include <MinSG/Helper/StdNodeVisitors.h>

#include <MinSG/SceneManagement/SceneManager.h>

#include <Rendering/RenderingContext/RenderingContext.h>
#include <Rendering/Shader/Shader.h>
#include <Rendering/Helper.h>

#include <Util/IO/FileName.h>
#include <Util/IO/FileUtils.h>
#include <Util/Macros.h>
#include <Util/Util.h>
#include <Util/Utils.h>

#include <cstdint>
#include <cstdlib>
#include <fstream>
#include <deque>
#include <memory>
#include <string>
#include <sstream>

#include <android/log.h>

#define  LOG_TAG    "MobileRendering"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

std::unique_ptr<MinSG::SceneManagement::SceneManager> sceneMgr;
Util::Reference<MinSG::FrameContext> context;
Util::Reference<MinSG::ListNode> root;
Util::Reference<MinSG::CameraNode> camera;

bool benchmark = false;
std::deque<int> benchmarkBudgets;
double pathCounter = 0.0;
const double pathCounterIncrement = 0.1;
double pathCounterMax = 0.0;
std::ofstream benchmarkFile;

bool fixAxis = false;

JNIEXPORT void JNICALL Java_de_upb_mobilerendering_gl_OpenGLCore_onSystemInit(JNIEnv *, jclass) {
	Util::init();

	Rendering::enableGLErrorChecking();

	sceneMgr.reset(new MinSG::SceneManagement::SceneManager);
	context = new MinSG::FrameContext;
	camera = new MinSG::CameraNode;

//	MinSG::OutOfCore::setUp(context.get(), sceneMgr.get());
//
//	const std::size_t mebibyte = 1024 * 1024;
//	MinSG::OutOfCore::CacheManager & manager = MinSG::OutOfCore::getCacheManager();
//	manager.addCacheLevel(MinSG::OutOfCore::CacheLevelType::FILE_SYSTEM, 0);
//	manager.addCacheLevel(MinSG::OutOfCore::CacheLevelType::MAIN_MEMORY, 128 * mebibyte);
//	manager.addCacheLevel(MinSG::OutOfCore::CacheLevelType::GRAPHICS_MEMORY, 48 * mebibyte);
//	MinSG::OutOfCore::getDataStrategy().setMissingMode(MinSG::OutOfCore::DataStrategy::NO_WAIT_DISPLAY_COLORED_BOX);

	// The first benchmark makes sure that all meshes have been seen once.
	benchmarkBudgets.push_back(-1);
	for(int numRuns = 0; numRuns < 5; ++numRuns) {
		// SVS with approximate rendering
		benchmarkBudgets.push_back(500000);
		benchmarkBudgets.push_back(1000000);
		benchmarkBudgets.push_back(1500000);
		// SVS
		benchmarkBudgets.push_back(0);
		// Frustum culling
		benchmarkBudgets.push_back(-1);
	}

	LOGD("Init finished.");
}

JNIEXPORT void JNICALL Java_de_upb_mobilerendering_gl_OpenGLCore_onDrawFrame(JNIEnv * env, jclass jClass)
{
	if(benchmark) {
		LOGD("Benchmark progress: %f/%f", pathCounter, pathCounterMax);
		sceneMgr->getBehaviourManager()->executeBehaviours(pathCounter);
	}

	Rendering::RenderingContext::clearScreen(Util::Color4f(0.0f, 0.0f, 0.0f, 1.0f));
	context->setCamera(camera.get());
	context->beginFrame();
	root->display(*(context.get()), MinSG::FRUSTUM_CULLING);
	context->endFrame();

	if(benchmark) {
		auto statistics = context->getStatistics();
		benchmarkFile	<< pathCounter << '\t'
						<< statistics.getValueAsDouble(statistics.getFrameDurationCounter()) << '\t'
						<< statistics.getValueAsInt(statistics.getTrianglesCounter()) << '\n';
		if(pathCounter > pathCounterMax) {
			benchmark = false;
			benchmarkFile.close();
			LOGD("Benchmark finished!");
			if(!benchmarkBudgets.empty()) {
				// Start the next benchmark.
				Java_de_upb_mobilerendering_gl_OpenGLCore_startBenchmark(env, jClass);
			}
		}
		pathCounter += pathCounterIncrement;
	}
}

JNIEXPORT void JNICALL Java_de_upb_mobilerendering_gl_OpenGLCore_onSurfaceChanged(JNIEnv *, jclass, jint width, jint height)
{
	LOGD("onSurfaceChanged (width=%d, height=%d)", width, height);
	Geometry::Rect_i windowRect(0, 0, width, height);
	camera->setViewport(windowRect);
	camera->setNearFar(1.0f, 1000.0f);
	camera->applyVerticalAngle(60.0f);

	context->getRenderingContext().setWindowClientArea(windowRect);
}

JNIEXPORT void JNICALL Java_de_upb_mobilerendering_gl_OpenGLCore_onSurfaceCreated(JNIEnv * env, jclass, jstring currentScenePath)
{
	LOGD("onSurfaceCreated");
	{
		std::ostringstream outputStream;
		Rendering::outputGLInformation(outputStream);
		LOGD("%s", outputStream.str().c_str());
	}

	const char * scenePath = env->GetStringUTFChars(currentScenePath, nullptr);
	if (scenePath == nullptr) {
		// OutOfMemoryError already thrown
		return;
	}
	Util::FileName sceneFileName(scenePath);
	env->ReleaseStringUTFChars(currentScenePath, scenePath);

	// MinSG::destroy(root);
	// delete root; <-- BE: crashes for me
	root = new MinSG::ListNode();

	if(sceneFileName.getEnding() == "minsg") {
		// load from minsg file
		LOGD("Loading scene %s.", sceneFileName.toString().c_str());
		Util::Reference<MinSG::Node> scene;
		std::deque<Util::Reference<MinSG::Node> > nodes = sceneMgr->loadMinSGFile(sceneFileName);
		if(nodes.size() == 1) {
			scene = dynamic_cast<MinSG::GroupNode *>(nodes.front().get());
		} else {
			Util::Reference<MinSG::ListNode> list = new MinSG::ListNode;
			for(const auto & node : nodes) {
				list->addChild(node);
			}
			scene = list.get();
		}
		LOGD("%d Nodes loaded.", nodes.size());

		root->addChild(scene);

		// load path
		const std::deque<MinSG::Node *> children = MinSG::getChildNodes(scene.get());
		for(const auto & child : children) {
			MinSG::PathNode * pathNode = dynamic_cast<MinSG::PathNode *>(child);
			if(pathNode != nullptr) {
				MinSG::FollowPathBehaviour * behaviour = new MinSG::FollowPathBehaviour(pathNode, camera.get());
				sceneMgr->getBehaviourManager()->registerBehaviour(behaviour);
				pathCounterMax = pathNode->getMaxTime();
				LOGD("Path added.");
				break;
			}
		}

		Util::FileName pathFileName(sceneFileName.getDir() + "../path/" + Util::StringUtils::replaceAll(sceneFileName.getFile(), ".minsg", ".path"));
		if(Util::FileUtils::isFile(pathFileName)) {
			LOGD("Loading path %s.", pathFileName.toString().c_str());

			std::deque<Util::Reference<MinSG::Node> > nodes = sceneMgr->loadMinSGFile(pathFileName);
			for(const auto & node : nodes) {
				MinSG::PathNode * pathNode = dynamic_cast<MinSG::PathNode *>(node.get());
				if(pathNode != nullptr) {
					MinSG::FollowPathBehaviour * behaviour = new MinSG::FollowPathBehaviour(pathNode, camera.get());
					sceneMgr->getBehaviourManager()->registerBehaviour(behaviour);
					pathCounterMax = pathNode->getMaxTime();
					LOGD("Path successfully loaded.");
					break;
				}
			}
		}
	}

	{
		Util::Reference<MinSG::LightNode> light = new MinSG::LightNode(Rendering::LightParameters::POINT);
		light->setConstantAttenuation(1.0f);
		light->setLinearAttenuation(0.0f);
		light->setQuadraticAttenuation(0.0f);
		light->setRelPosition(Geometry::Vec3(100.0f, 100.0f, 100.0f));
		light->setAmbientLightColor(Util::Color4f(0.5f, 0.5f, 0.5f, 1.0f));
		light->setDiffuseLightColor(Util::Color4f(0.8f, 0.8f, 0.8f, 1.0f));
		light->setSpecularLightColor(Util::Color4f(1.0f, 1.0f, 1.0f, 1.0f));
		MinSG::LightingState * state = new MinSG::LightingState;
		state->setLight(light.get());
		root->addChild(light.get());
		root->addState(state);
	}
	LOGD("Light added.");

	GET_GL_ERROR()

	{
		Util::FileName vsFileName("/sdcard/MobileRendering/shader/SimplifiedPhongShader.vs");
		Util::FileName fsFileName("/sdcard/MobileRendering/shader/SimplifiedPhongShader.fs");

		Rendering::Shader * shader = Rendering::Shader::loadShader(vsFileName, fsFileName, Rendering::Shader::USE_UNIFORMS);
		context->getRenderingContext().pushAndSetShader(shader);
	}
	LOGD("Shader added.");
}

JNIEXPORT void JNICALL Java_de_upb_mobilerendering_gl_OpenGLCore_interact(JNIEnv *, jclass,
																		  jfloat rotationY,
																		  jfloat rotationX,
																		  jfloat movementZ,
																		  jfloat movementX)
{
	if(camera != nullptr)
	{
		camera->rotateRel_deg(rotationY, Geometry::Vec3(0.0f, -1.0f, 0.0f));
		camera->rotateLocal_deg(rotationX, Geometry::Vec3(-1.0f, 0.0f, 0.0f));
		float oldY = camera->getWorldPosition().getY();
		camera->moveLocal(Geometry::Vec3(0.0f, 0.0f, movementZ));
		camera->moveLocal(Geometry::Vec3(movementX, 0.0f, 0.0f));
		if(fixAxis)
		{
			Geometry::Vec3 pos = camera->getWorldPosition();
			pos.setY(oldY);
			camera->setWorldPosition(pos);
		}
	}
}

JNIEXPORT void JNICALL Java_de_upb_mobilerendering_gl_OpenGLCore_startBenchmark(JNIEnv *, jclass)
{
	if(!benchmark) {
		if(!benchmarkBudgets.empty()) {
			const auto svsRenderers = MinSG::collectStates<MinSG::SphericalSampling::Renderer>(root.get());
			const auto budgetRenderers = MinSG::collectStates<MinSG::SphericalSampling::BudgetRenderer>(root.get());

			const auto currentBudget = benchmarkBudgets.front();
			benchmarkBudgets.pop_front();

			if(currentBudget == -1) {
				// Frustum culling only
				for(const auto & state : svsRenderers) {
					state->deactivate();
				}
				for(const auto & state : budgetRenderers) {
					state->deactivate();
				}
			} else if(currentBudget == 0) {
				// SVS
				for(const auto & state : svsRenderers) {
					state->activate();
				}
				for(const auto & state : budgetRenderers) {
					state->deactivate();
				}
			} else {
				// SVS with budget rendering
				for(const auto & state : svsRenderers) {
					state->activate();
				}
				for(const auto & state : budgetRenderers) {
					state->activate();
					state->setBudget(currentBudget);
				}
			}
		}

		pathCounter = 0;
		std::string fileName("benchmark_");
		fileName += Util::Utils::createTimeStamp();

		const auto svsRenderers = MinSG::collectStates<MinSG::SphericalSampling::Renderer>(root.get());
		if(!svsRenderers.empty() && (*svsRenderers.cbegin())->isActive()) {
			fileName += "_svs";
		}

		const auto budgetRenderers = MinSG::collectStates<MinSG::SphericalSampling::BudgetRenderer>(root.get());
		if(!budgetRenderers.empty() && (*budgetRenderers.cbegin())->isActive()) {
			fileName += "_budget" + Util::StringUtils::toString((*budgetRenderers.cbegin())->getBudget());
		}

		fileName += ".tsv";
		benchmarkFile.open(std::string("/sdcard/MobileRendering/") + fileName, std::ios_base::out | std::ios_base::trunc);
		benchmarkFile << "Point\tFrameDuration\tNumTriangles\n";
		benchmark = true;
		LOGD("Benchmark started!");
	}
}

JNIEXPORT jint JNICALL Java_de_upb_mobilerendering_gl_OpenGLCore_getRenderedPolygonCount(JNIEnv *, jclass)
{
	return context->getStatistics().getValueAsInt(context->getStatistics().getTrianglesCounter());
}

JNIEXPORT void JNICALL Java_de_upb_mobilerendering_gl_OpenGLCore_resetCamera(JNIEnv *, jclass) {
	if(camera != nullptr) {
		camera->reset();
		fixAxis = false;
	}
}

JNIEXPORT void JNICALL Java_de_upb_mobilerendering_gl_OpenGLCore_setBudget(JNIEnv *, jclass, jint budget)
{
	const auto budgetRendererStates = MinSG::collectStates<MinSG::SphericalSampling::BudgetRenderer>(root.get());
	if(budgetRendererStates.empty()) {
		LOGD("No BudgetRenderer found.");
		return;
	}
	for(const auto & state : budgetRendererStates) {
		state->setBudget(budget);
	}
	LOGD("Configured %d BudgetRenderers.", budgetRendererStates.size());
}

JNIEXPORT void JNICALL Java_de_upb_mobilerendering_gl_OpenGLCore_toggleApproximateRendering (JNIEnv *, jclass)
{
	const auto budgetRendererStates = MinSG::collectStates<MinSG::SphericalSampling::BudgetRenderer>(root.get());
	if(budgetRendererStates.empty()) {
		LOGD("No BudgetRenderer found.");
		return;
	}
	const bool enableAll = !(*budgetRendererStates.cbegin())->isActive();
	for(const auto & state : budgetRendererStates) {
		if(enableAll) {
			state->activate();
		} else {
			state->deactivate();
		}
	}
	LOGD("%s %d BudgetRenderers.", enableAll ? "Enabled" : "Disabled", budgetRendererStates.size());
}

JNIEXPORT void JNICALL Java_de_upb_mobilerendering_gl_OpenGLCore_toggleFixAxis(JNIEnv *, jclass)
{
	fixAxis = !fixAxis;
}
