package de.upb.mobilerendering.gl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import de.upb.mobilerendering.MobileRendering;
import de.upb.mobilerendering.gui.BudgetSlider;
import de.upb.mobilerendering.gui.FrameCounter;
import de.upb.mobilerendering.gui.PolygonCountView;

import android.opengl.GLSurfaceView.Renderer;

/**
 * A proxy for the renderer implemented in C++.
 * It handles the FrameCounter and the PolygonCountView.
 * Furthermore it passes on function-calls and interactions to the C++-implementation.
 */
public class RendererProxy implements Renderer
{
	private PolygonCountView _polygonCountView;
	private FrameCounter _frameCounter;
	private BudgetSlider _budgetSlider;
	int _budgetCache;
	private InputProxy _inputProxy;
	
	public RendererProxy(FrameCounter _frameCounter, PolygonCountView _polygonCountView, BudgetSlider _budgetSlider, InputProxy _inputProxy)
	{	
		this._polygonCountView = _polygonCountView; 
		this._frameCounter = _frameCounter;
		this._budgetSlider = _budgetSlider;
		this._budgetCache = 0;
		this._inputProxy = _inputProxy;
	}
	
	public void onDrawFrame(GL10 gl)
	{
		OpenGLCore.interact(_inputProxy.getAccumulatedRotationY(),
							_inputProxy.getAccumulatedRotationX(),
							_inputProxy.getAccumulatedMovementZ(),
							_inputProxy.getAccumulatedMovementX());
		
		if(_budgetCache != _budgetSlider.getBudget()) {
			_budgetCache = _budgetSlider.getBudget();
			OpenGLCore.setBudget(_budgetCache);
		}
		
		OpenGLCore.onDrawFrame();

		// update statistics
		_frameCounter.frameDrawn();
		_polygonCountView.setPolygonCount(OpenGLCore.getRenderedPolygonCount());
	}

	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		OpenGLCore.onSurfaceChanged(width, height);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{		
		OpenGLCore.onSurfaceCreated(MobileRendering.getCurrentScenePath());
	}
}
