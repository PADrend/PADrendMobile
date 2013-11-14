#
#	This file is part of the OpenGLCore library.
#	Copyright (C) 2010-2013 Benjamin Eikel <benjamin@eikel.org>
#	Copyright (C) 2010 Robert Gmyr
#	
#	This library is subject to the terms of the Mozilla Public License, v. 2.0.
#	You should have received a copy of the MPL along with this library; see the 
#	file LICENSE. If not, you can obtain one at http://mozilla.org/MPL/2.0/.
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
				ext/Geometry/BoundingSphere.cpp \
				ext/Geometry/BoxHelper.cpp \
				ext/Geometry/BoxIntersection.cpp \
				ext/Geometry/Frustum.cpp \
				ext/Geometry/Tools.cpp \
				ext/MinSG/Core/FrameContext.cpp \
				ext/MinSG/Core/NodeAttributeModifier.cpp \
				ext/MinSG/Core/RenderParam.cpp \
				ext/MinSG/Core/Statistics.cpp \
				ext/MinSG/Core/Behaviours/AbstractBehaviour.cpp \
				ext/MinSG/Core/Behaviours/Behavior.cpp \
				ext/MinSG/Core/Behaviours/BehaviorStatus.cpp \
				ext/MinSG/Core/Behaviours/BehaviorStatusExtensions.cpp \
				ext/MinSG/Core/Behaviours/BehaviourManager.cpp \
				ext/MinSG/Core/Nodes/AbstractCameraNode.cpp \
				ext/MinSG/Core/Nodes/CameraNode.cpp \
				ext/MinSG/Core/Nodes/CameraNodeOrtho.cpp \
				ext/MinSG/Core/Nodes/GeometryNode.cpp \
				ext/MinSG/Core/Nodes/GroupNode.cpp \
				ext/MinSG/Core/Nodes/LightNode.cpp \
				ext/MinSG/Core/Nodes/ListNode.cpp \
				ext/MinSG/Core/Nodes/Node.cpp \
				ext/MinSG/Core/States/AlphaTestState.cpp \
				ext/MinSG/Core/States/BlendingState.cpp \
				ext/MinSG/Core/States/CullFaceState.cpp \
				ext/MinSG/Core/States/GroupState.cpp \
				ext/MinSG/Core/States/LightingState.cpp \
				ext/MinSG/Core/States/MaterialState.cpp \
				ext/MinSG/Core/States/NodeRendererState.cpp \
				ext/MinSG/Core/States/PolygonModeState.cpp \
				ext/MinSG/Core/States/ShaderState.cpp \
				ext/MinSG/Core/States/ShaderUniformState.cpp \
				ext/MinSG/Core/States/TextureState.cpp \
				ext/MinSG/Core/States/TransparencyRenderer.cpp \
				ext/MinSG/Ext/Evaluator/Evaluator.cpp \
				ext/MinSG/Ext/Nodes/BillboardNode.cpp \
				ext/MinSG/Ext/Nodes/GenericMetaNode.cpp \
				ext/MinSG/Ext/KeyFrameAnimation/KeyFrameAnimationData.cpp \
				ext/MinSG/Ext/KeyFrameAnimation/KeyFrameAnimationNode.cpp \
				ext/MinSG/Ext/OcclusionCulling/CHCppRenderer.cpp \
				ext/MinSG/Ext/OcclusionCulling/OcclusionCullingStatistics.cpp \
				ext/MinSG/Ext/OcclusionCulling/OccRenderer.cpp \
				ext/MinSG/Ext/OutOfCore/CacheContext.cpp \
				ext/MinSG/Ext/OutOfCore/CacheLevel.cpp \
				ext/MinSG/Ext/OutOfCore/CacheLevelFiles.cpp \
				ext/MinSG/Ext/OutOfCore/CacheLevelFileSystem.cpp \
				ext/MinSG/Ext/OutOfCore/CacheLevelGraphicsMemory.cpp \
				ext/MinSG/Ext/OutOfCore/CacheLevelMainMemory.cpp \
				ext/MinSG/Ext/OutOfCore/CacheManager.cpp \
				ext/MinSG/Ext/OutOfCore/CacheObject.cpp \
				ext/MinSG/Ext/OutOfCore/DataStrategy.cpp \
				ext/MinSG/Ext/OutOfCore/ImportHandler.cpp \
				ext/MinSG/Ext/OutOfCore/MeshAttributeSerialization.cpp \
				ext/MinSG/Ext/OutOfCore/OutOfCore.cpp \
				ext/MinSG/Ext/SceneManagement/Exporter/ExtBehaviourExporter.cpp \
				ext/MinSG/Ext/SceneManagement/Exporter/ExtNodeExporter.cpp \
				ext/MinSG/Ext/SceneManagement/Exporter/ExtStateExporter.cpp \
				ext/MinSG/Ext/SceneManagement/Importer/ExtAdditionalDataImporter.cpp \
				ext/MinSG/Ext/SceneManagement/Importer/ExtBehaviourImporter.cpp \
				ext/MinSG/Ext/SceneManagement/Importer/ExtNodeImporter.cpp \
				ext/MinSG/Ext/SceneManagement/Importer/ExtStateImporter.cpp \
				ext/MinSG/Ext/SceneManagement/ExtConsts.cpp \
				ext/MinSG/Ext/SphericalSampling/BudgetRenderer.cpp \
				ext/MinSG/Ext/SphericalSampling/Helper.cpp \
				ext/MinSG/Ext/SphericalSampling/PreprocessingContext.cpp \
				ext/MinSG/Ext/SphericalSampling/Renderer.cpp \
				ext/MinSG/Ext/SphericalSampling/SamplePoint.cpp \
				ext/MinSG/Ext/SphericalSampling/SamplingSphere.cpp \
				ext/MinSG/Ext/SphericalSampling/Statistics.cpp \
				ext/MinSG/Ext/States/BudgetAnnotationState.cpp \
				ext/MinSG/Ext/States/LODRenderer.cpp \
				ext/MinSG/Ext/States/MirrorState.cpp \
				ext/MinSG/Ext/States/ProjSizeFilterState.cpp \
				ext/MinSG/Ext/States/SkyboxState.cpp \
				ext/MinSG/Ext/TreeSync/Server.cpp \
				ext/MinSG/Ext/Triangulation/Delaunay2d.cpp \
				ext/MinSG/Ext/Triangulation/Delaunay3d.cpp \
				ext/MinSG/Ext/Triangulation/Helper.cpp \
				ext/MinSG/Ext/ValuatedRegion/ValuatedRegionNode.cpp \
				ext/MinSG/Ext/VisibilitySubdivision/CostEvaluator.cpp \
				ext/MinSG/Ext/VisibilitySubdivision/VisibilityVector.cpp \
				ext/MinSG/Ext/Waypoints/FollowPathBehaviour.cpp \
				ext/MinSG/Ext/Waypoints/PathNode.cpp \
				ext/MinSG/Ext/Waypoints/Waypoint.cpp \
				ext/MinSG/Helper/DataDirectory.cpp \
				ext/MinSG/Helper/Helper.cpp \
				ext/MinSG/Helper/StdNodeVisitors.cpp \
				ext/MinSG/Helper/TextAnnotation.cpp \
				ext/MinSG/Helper/VisibilityTester.cpp \
				ext/MinSG/SceneManagement/Exporter/CoreNodeExporter.cpp \
				ext/MinSG/SceneManagement/Exporter/CoreStateExporter.cpp \
				ext/MinSG/SceneManagement/Exporter/ExporterContext.cpp \
				ext/MinSG/SceneManagement/Exporter/ExporterTools.cpp \
				ext/MinSG/SceneManagement/Importer/CoreNodeImporter.cpp \
				ext/MinSG/SceneManagement/Importer/CoreStateImporter.cpp \
				ext/MinSG/SceneManagement/Importer/ImportContext.cpp \
				ext/MinSG/SceneManagement/Importer/ImporterTools.cpp \
				ext/MinSG/SceneManagement/MeshImportHandler.cpp \
				ext/MinSG/SceneManagement/ReaderDAE.cpp \
				ext/MinSG/SceneManagement/ReaderMinSG.cpp \
				ext/MinSG/SceneManagement/SceneDescription.cpp \
				ext/MinSG/SceneManagement/SceneManager.cpp \
				ext/MinSG/SceneManagement/WriterDAE.cpp \
				ext/MinSG/SceneManagement/WriterMinSG.cpp \
				ext/Rendering/Mesh/Mesh.cpp \
				ext/Rendering/Mesh/MeshDataStrategy.cpp \
				ext/Rendering/Mesh/MeshIndexData.cpp \
				ext/Rendering/Mesh/MeshVertexData.cpp \
				ext/Rendering/Mesh/VertexAttribute.cpp \
				ext/Rendering/Mesh/VertexAttributeAccessors.cpp \
				ext/Rendering/Mesh/VertexAttributeIds.cpp \
				ext/Rendering/Mesh/VertexDescription.cpp \
				ext/Rendering/MeshUtils/MeshBuilder.cpp \
				ext/Rendering/MeshUtils/MeshUtils.cpp \
				ext/Rendering/MeshUtils/PlatonicSolids.cpp \
				ext/Rendering/MeshUtils/Simplification.cpp \
				ext/Rendering/RenderingContext/ParameterStructs.cpp \
				ext/Rendering/RenderingContext/RenderingContext.cpp \
				ext/Rendering/Serialization/GenericAttributeSerialization.cpp \
				ext/Rendering/Serialization/Serialization.cpp \
				ext/Rendering/Serialization/StreamerMD2.cpp \
				ext/Rendering/Serialization/StreamerMMF.cpp \
				ext/Rendering/Serialization/StreamerMTL.cpp \
				ext/Rendering/Serialization/StreamerMVBO.cpp \
				ext/Rendering/Serialization/StreamerNGC.cpp \
				ext/Rendering/Serialization/StreamerOBJ.cpp \
				ext/Rendering/Serialization/StreamerPKM.cpp \
				ext/Rendering/Serialization/StreamerPLY.cpp \
				ext/Rendering/Serialization/StreamerXYZ.cpp \
				ext/Rendering/Shader/Shader.cpp \
				ext/Rendering/Shader/ShaderObjectInfo.cpp \
				ext/Rendering/Shader/Uniform.cpp \
				ext/Rendering/Shader/UniformRegistry.cpp \
				ext/Rendering/Texture/Texture.cpp \
				ext/Rendering/Texture/TextureUtils.cpp \
				ext/Rendering/BufferObject.cpp \
				ext/Rendering/Draw.cpp \
				ext/Rendering/DrawCompound.cpp \
				ext/Rendering/FBO.cpp \
				ext/Rendering/Helper.cpp \
				ext/Rendering/OcclusionQuery.cpp \
				ext/Rendering/QueryObject.cpp \
				ext/Rendering/TextRenderer.cpp \
				ext/Util/Encoding.cpp \
				ext/Util/GenericAttribute.cpp \
				ext/Util/GenericAttributeSerialization.cpp \
				ext/Util/JSON_Parser.cpp \
				ext/Util/Macros.cpp \
				ext/Util/MicroXML.cpp \
				ext/Util/ProgressIndicator.cpp \
				ext/Util/StringIdentifier.cpp \
				ext/Util/StringUtils.cpp \
				ext/Util/Timer.cpp \
				ext/Util/Util.cpp \
				ext/Util/Utils.cpp \
				ext/Util/Concurrency/Concurrency.cpp \
				ext/Util/Concurrency/CppMutex.cpp \
				ext/Util/Concurrency/CppSemaphore.cpp \
				ext/Util/Concurrency/DummySpinLock.cpp \
				ext/Util/Concurrency/CppThread.cpp \
				ext/Util/Concurrency/UserThread.cpp \
				ext/Util/Graphics/Bitmap.cpp \
				ext/Util/Graphics/BitmapUtils.cpp \
				ext/Util/Graphics/ColorLibrary.cpp \
				ext/Util/Graphics/EmbeddedFont.cpp \
				ext/Util/Graphics/NoiseGenerator.cpp \
				ext/Util/Graphics/PixelAccessor.cpp \
				ext/Util/Graphics/PixelFormat.cpp \
				ext/Util/IO/AbstractFSProvider.cpp \
				ext/Util/IO/FileName.cpp \
				ext/Util/IO/FileUtils.cpp \
				ext/Util/IO/FSProvider.cpp \
				ext/Util/IO/DBFSProvider.cpp \
				ext/Util/IO/NetProvider.cpp \
				ext/Util/IO/TemporaryDirectory.cpp \
				ext/Util/IO/ZIPProvider.cpp \
				ext/Util/Network/ClockSynchronizer.cpp \
				ext/Util/Network/DataConnection.cpp \
				ext/Util/Network/Network.cpp \
				ext/Util/Network/NetworkTCP.cpp \
				ext/Util/Network/NetworkUDP.cpp \
				ext/Util/Serialization/Serialization.cpp \
				ext/Util/Serialization/StreamerPNG.cpp \
				ext/Util/Serialization/StreamerSDL.cpp \
				ext/Util/Serialization/StreamerSDLImage.cpp

include $(BUILD_SHARED_LIBRARY)

$(call import-module,curl)
$(call import-module,Detri)
$(call import-module,libpng)
$(call import-module,libzip)
