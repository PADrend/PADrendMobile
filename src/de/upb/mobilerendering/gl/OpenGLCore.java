package de.upb.mobilerendering.gl;

/**
 * The Java interface to the C++ libraries.
 */
public final class OpenGLCore
{
	static
	{
		// It is important that the support libraries are loaded first.
		System.loadLibrary("gnustl_shared");
		System.loadLibrary("OpenGLCore");
		onSystemInit();
	}
	
	public static native void onSystemInit();
	public static native void onDrawFrame();
	public static native void onSurfaceChanged(int width, int height);
	public static native void onSurfaceCreated(String currentScenePath);
	
	public static native void interact(float rotationY, float rotationX, float movementZ, float movementX);
	
	public static native void resetCamera();
	public static native void startBenchmark();
	
	public static native int getRenderedPolygonCount();
	public static native void setBudget(int budget);
	public static native void toggleApproximateRendering();
	public static native void toggleFixAxis();
}
