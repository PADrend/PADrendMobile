/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class de_padrend_mobile_gl_OpenGLCore */

#ifndef _Included_de_padrend_mobile_gl_OpenGLCore
#define _Included_de_padrend_mobile_gl_OpenGLCore
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     de_padrend_mobile_gl_OpenGLCore
 * Method:    onSystemInit
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_de_padrend_mobile_gl_OpenGLCore_onSystemInit
  (JNIEnv *, jclass);

/*
 * Class:     de_padrend_mobile_gl_OpenGLCore
 * Method:    onDrawFrame
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_de_padrend_mobile_gl_OpenGLCore_onDrawFrame
  (JNIEnv *, jclass);

/*
 * Class:     de_padrend_mobile_gl_OpenGLCore
 * Method:    onSurfaceChanged
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_de_padrend_mobile_gl_OpenGLCore_onSurfaceChanged
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     de_padrend_mobile_gl_OpenGLCore
 * Method:    onSurfaceCreated
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_de_padrend_mobile_gl_OpenGLCore_onSurfaceCreated
  (JNIEnv *, jclass, jstring);

/*
 * Class:     de_padrend_mobile_gl_OpenGLCore
 * Method:    interact
 * Signature: (FFFF)V
 */
JNIEXPORT void JNICALL Java_de_padrend_mobile_gl_OpenGLCore_interact
  (JNIEnv *, jclass, jfloat, jfloat, jfloat, jfloat);

/*
 * Class:     de_padrend_mobile_gl_OpenGLCore
 * Method:    resetCamera
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_de_padrend_mobile_gl_OpenGLCore_resetCamera
  (JNIEnv *, jclass);

/*
 * Class:     de_padrend_mobile_gl_OpenGLCore
 * Method:    startBenchmark
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_de_padrend_mobile_gl_OpenGLCore_startBenchmark
  (JNIEnv *, jclass);

/*
 * Class:     de_padrend_mobile_gl_OpenGLCore
 * Method:    getRenderedPolygonCount
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_de_padrend_mobile_gl_OpenGLCore_getRenderedPolygonCount
  (JNIEnv *, jclass);

/*
 * Class:     de_padrend_mobile_gl_OpenGLCore
 * Method:    setBudget
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_de_padrend_mobile_gl_OpenGLCore_setBudget
  (JNIEnv *, jclass, jint);

/*
 * Class:     de_padrend_mobile_gl_OpenGLCore
 * Method:    toggleApproximateRendering
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_de_padrend_mobile_gl_OpenGLCore_toggleApproximateRendering
  (JNIEnv *, jclass);

/*
 * Class:     de_padrend_mobile_gl_OpenGLCore
 * Method:    toggleFixAxis
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_de_padrend_mobile_gl_OpenGLCore_toggleFixAxis
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
