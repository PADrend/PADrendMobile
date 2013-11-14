#
# This file is part of the PADrendMobile project.
# Web page: http://www.padrend.de/
# Copyright (C) 2010-2013 Benjamin Eikel <benjamin@eikel.org>
# Copyright (C) 2010 Robert Gmyr
#
# This project is subject to the terms of the Mozilla Public License, v. 2.0.
# You should have received a copy of the MPL along with this project; see the
# file LICENSE. If not, you can obtain one at http://mozilla.org/MPL/2.0/.
#

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    	:= 	libOpenGLCore
LOCAL_CFLAGS    	:= 	-Wall -Wextra \
						-I$(LOCAL_PATH)/ext \
						-DUTIL_HAVE_LIB_CURL \
						-DUTIL_HAVE_LIB_PNG \
						-DUTIL_HAVE_LIB_ZIP \
						-DLIB_GLESv2 \
						-DMINSG_EXT_EVALUATORS \
						-DMINSG_EXT_OUTOFCORE \
						-DMINSG_EXT_SPHERICALSAMPLING \
						-DMINSG_EXT_TREE_SYNC \
						-DMINSG_EXT_TRIANGULATION \
						-DMINSG_EXT_WAYPOINTS \
						-DMINSG_EXT_VISIBILITY_SUBDIVISION \
						-DDEBUG_MODE
LOCAL_LDLIBS    	:=	-llog \
						-lGLESv2 \
						-lz
LOCAL_STATIC_LIBRARIES := curl-prebuilt \
						  Detri-prebuilt \
						  libpng-prebuilt \
						  libzip-prebuilt
LOCAL_SRC_FILES 	:= 	de_upb_mobilerendering_gl_OpenGLCore.cpp \
				Geometry/BoundingSphere.cpp \
				Geometry/BoxHelper.cpp \
				Geometry/BoxIntersection.cpp \
				Geometry/Frustum.cpp \
				Geometry/Tools.cpp \
				MinSG/Core/FrameContext.cpp \
				MinSG/Core/NodeAttributeModifier.cpp \
				MinSG/Core/RenderParam.cpp \
				MinSG/Core/Statistics.cpp \
				MinSG/Core/Behaviours/AbstractBehaviour.cpp \
				MinSG/Core/Behaviours/Behavior.cpp \
				MinSG/Core/Behaviours/BehaviorStatus.cpp \
				MinSG/Core/Behaviours/BehaviorStatusExtensions.cpp \
				MinSG/Core/Behaviours/BehaviourManager.cpp \
				MinSG/Core/Nodes/AbstractCameraNode.cpp \
				MinSG/Core/Nodes/CameraNode.cpp \
				MinSG/Core/Nodes/CameraNodeOrtho.cpp \
				MinSG/Core/Nodes/GeometryNode.cpp \
				MinSG/Core/Nodes/GroupNode.cpp \
				MinSG/Core/Nodes/LightNode.cpp \
				MinSG/Core/Nodes/ListNode.cpp \
				MinSG/Core/Nodes/Node.cpp \
				MinSG/Core/States/AlphaTestState.cpp \
				MinSG/Core/States/BlendingState.cpp \
				MinSG/Core/States/CullFaceState.cpp \
				MinSG/Core/States/GroupState.cpp \
				MinSG/Core/States/LightingState.cpp \
				MinSG/Core/States/MaterialState.cpp \
				MinSG/Core/States/NodeRendererState.cpp \
				MinSG/Core/States/PolygonModeState.cpp \
				MinSG/Core/States/ShaderState.cpp \
				MinSG/Core/States/ShaderUniformState.cpp \
				MinSG/Core/States/TextureState.cpp \
				MinSG/Core/States/TransparencyRenderer.cpp \
				MinSG/Ext/Evaluator/Evaluator.cpp \
				MinSG/Ext/Nodes/BillboardNode.cpp \
				MinSG/Ext/Nodes/GenericMetaNode.cpp \
				MinSG/Ext/KeyFrameAnimation/KeyFrameAnimationData.cpp \
				MinSG/Ext/KeyFrameAnimation/KeyFrameAnimationNode.cpp \
				MinSG/Ext/OcclusionCulling/CHCppRenderer.cpp \
				MinSG/Ext/OcclusionCulling/OcclusionCullingStatistics.cpp \
				MinSG/Ext/OcclusionCulling/OccRenderer.cpp \
				MinSG/Ext/OutOfCore/CacheContext.cpp \
				MinSG/Ext/OutOfCore/CacheLevel.cpp \
				MinSG/Ext/OutOfCore/CacheLevelFiles.cpp \
				MinSG/Ext/OutOfCore/CacheLevelFileSystem.cpp \
				MinSG/Ext/OutOfCore/CacheLevelGraphicsMemory.cpp \
				MinSG/Ext/OutOfCore/CacheLevelMainMemory.cpp \
				MinSG/Ext/OutOfCore/CacheManager.cpp \
				MinSG/Ext/OutOfCore/CacheObject.cpp \
				MinSG/Ext/OutOfCore/DataStrategy.cpp \
				MinSG/Ext/OutOfCore/ImportHandler.cpp \
				MinSG/Ext/OutOfCore/MeshAttributeSerialization.cpp \
				MinSG/Ext/OutOfCore/OutOfCore.cpp \
				MinSG/Ext/SceneManagement/Exporter/ExtBehaviourExporter.cpp \
				MinSG/Ext/SceneManagement/Exporter/ExtNodeExporter.cpp \
				MinSG/Ext/SceneManagement/Exporter/ExtStateExporter.cpp \
				MinSG/Ext/SceneManagement/Importer/ExtAdditionalDataImporter.cpp \
				MinSG/Ext/SceneManagement/Importer/ExtBehaviourImporter.cpp \
				MinSG/Ext/SceneManagement/Importer/ExtNodeImporter.cpp \
				MinSG/Ext/SceneManagement/Importer/ExtStateImporter.cpp \
				MinSG/Ext/SceneManagement/ExtConsts.cpp \
				MinSG/Ext/SphericalSampling/BudgetRenderer.cpp \
				MinSG/Ext/SphericalSampling/Helper.cpp \
				MinSG/Ext/SphericalSampling/PreprocessingContext.cpp \
				MinSG/Ext/SphericalSampling/Renderer.cpp \
				MinSG/Ext/SphericalSampling/SamplePoint.cpp \
				MinSG/Ext/SphericalSampling/SamplingSphere.cpp \
				MinSG/Ext/SphericalSampling/Statistics.cpp \
				MinSG/Ext/States/BudgetAnnotationState.cpp \
				MinSG/Ext/States/LODRenderer.cpp \
				MinSG/Ext/States/MirrorState.cpp \
				MinSG/Ext/States/ProjSizeFilterState.cpp \
				MinSG/Ext/States/SkyboxState.cpp \
				MinSG/Ext/TreeSync/Server.cpp \
				MinSG/Ext/Triangulation/Delaunay2d.cpp \
				MinSG/Ext/Triangulation/Delaunay3d.cpp \
				MinSG/Ext/Triangulation/Helper.cpp \
				MinSG/Ext/ValuatedRegion/ValuatedRegionNode.cpp \
				MinSG/Ext/VisibilitySubdivision/CostEvaluator.cpp \
				MinSG/Ext/VisibilitySubdivision/VisibilityVector.cpp \
				MinSG/Ext/Waypoints/FollowPathBehaviour.cpp \
				MinSG/Ext/Waypoints/PathNode.cpp \
				MinSG/Ext/Waypoints/Waypoint.cpp \
				MinSG/Helper/DataDirectory.cpp \
				MinSG/Helper/Helper.cpp \
				MinSG/Helper/StdNodeVisitors.cpp \
				MinSG/Helper/TextAnnotation.cpp \
				MinSG/Helper/VisibilityTester.cpp \
				MinSG/SceneManagement/Exporter/CoreNodeExporter.cpp \
				MinSG/SceneManagement/Exporter/CoreStateExporter.cpp \
				MinSG/SceneManagement/Exporter/ExporterContext.cpp \
				MinSG/SceneManagement/Exporter/ExporterTools.cpp \
				MinSG/SceneManagement/Importer/CoreNodeImporter.cpp \
				MinSG/SceneManagement/Importer/CoreStateImporter.cpp \
				MinSG/SceneManagement/Importer/ImportContext.cpp \
				MinSG/SceneManagement/Importer/ImporterTools.cpp \
				MinSG/SceneManagement/MeshImportHandler.cpp \
				MinSG/SceneManagement/ReaderDAE.cpp \
				MinSG/SceneManagement/ReaderMinSG.cpp \
				MinSG/SceneManagement/SceneDescription.cpp \
				MinSG/SceneManagement/SceneManager.cpp \
				MinSG/SceneManagement/WriterDAE.cpp \
				MinSG/SceneManagement/WriterMinSG.cpp \
				Rendering/Mesh/Mesh.cpp \
				Rendering/Mesh/MeshDataStrategy.cpp \
				Rendering/Mesh/MeshIndexData.cpp \
				Rendering/Mesh/MeshVertexData.cpp \
				Rendering/Mesh/VertexAttribute.cpp \
				Rendering/Mesh/VertexAttributeAccessors.cpp \
				Rendering/Mesh/VertexAttributeIds.cpp \
				Rendering/Mesh/VertexDescription.cpp \
				Rendering/MeshUtils/MeshBuilder.cpp \
				Rendering/MeshUtils/MeshUtils.cpp \
				Rendering/MeshUtils/PlatonicSolids.cpp \
				Rendering/MeshUtils/Simplification.cpp \
				Rendering/RenderingContext/ParameterStructs.cpp \
				Rendering/RenderingContext/RenderingContext.cpp \
				Rendering/Serialization/GenericAttributeSerialization.cpp \
				Rendering/Serialization/Serialization.cpp \
				Rendering/Serialization/StreamerMD2.cpp \
				Rendering/Serialization/StreamerMMF.cpp \
				Rendering/Serialization/StreamerMTL.cpp \
				Rendering/Serialization/StreamerMVBO.cpp \
				Rendering/Serialization/StreamerNGC.cpp \
				Rendering/Serialization/StreamerOBJ.cpp \
				Rendering/Serialization/StreamerPKM.cpp \
				Rendering/Serialization/StreamerPLY.cpp \
				Rendering/Serialization/StreamerXYZ.cpp \
				Rendering/Shader/Shader.cpp \
				Rendering/Shader/ShaderObjectInfo.cpp \
				Rendering/Shader/Uniform.cpp \
				Rendering/Shader/UniformRegistry.cpp \
				Rendering/Texture/Texture.cpp \
				Rendering/Texture/TextureUtils.cpp \
				Rendering/BufferObject.cpp \
				Rendering/Draw.cpp \
				Rendering/DrawCompound.cpp \
				Rendering/FBO.cpp \
				Rendering/Helper.cpp \
				Rendering/OcclusionQuery.cpp \
				Rendering/QueryObject.cpp \
				Rendering/TextRenderer.cpp \
				Util/Encoding.cpp \
				Util/GenericAttribute.cpp \
				Util/GenericAttributeSerialization.cpp \
				Util/JSON_Parser.cpp \
				Util/Macros.cpp \
				Util/MicroXML.cpp \
				Util/ProgressIndicator.cpp \
				Util/StringIdentifier.cpp \
				Util/StringUtils.cpp \
				Util/Timer.cpp \
				Util/Util.cpp \
				Util/Utils.cpp \
				Util/Concurrency/Concurrency.cpp \
				Util/Concurrency/CppMutex.cpp \
				Util/Concurrency/CppSemaphore.cpp \
				Util/Concurrency/DummySpinLock.cpp \
				Util/Concurrency/CppThread.cpp \
				Util/Concurrency/UserThread.cpp \
				Util/Graphics/Bitmap.cpp \
				Util/Graphics/BitmapUtils.cpp \
				Util/Graphics/ColorLibrary.cpp \
				Util/Graphics/EmbeddedFont.cpp \
				Util/Graphics/NoiseGenerator.cpp \
				Util/Graphics/PixelAccessor.cpp \
				Util/Graphics/PixelFormat.cpp \
				Util/IO/AbstractFSProvider.cpp \
				Util/IO/FileName.cpp \
				Util/IO/FileUtils.cpp \
				Util/IO/FSProvider.cpp \
				Util/IO/DBFSProvider.cpp \
				Util/IO/NetProvider.cpp \
				Util/IO/TemporaryDirectory.cpp \
				Util/IO/ZIPProvider.cpp \
				Util/Network/ClockSynchronizer.cpp \
				Util/Network/DataConnection.cpp \
				Util/Network/Network.cpp \
				Util/Network/NetworkTCP.cpp \
				Util/Network/NetworkUDP.cpp \
				Util/Serialization/Serialization.cpp \
				Util/Serialization/StreamerPNG.cpp \
				Util/Serialization/StreamerSDL.cpp \
				Util/Serialization/StreamerSDLImage.cpp

include $(BUILD_SHARED_LIBRARY)

$(call import-module,curl)
$(call import-module,Detri)
$(call import-module,libpng)
$(call import-module,libzip)
