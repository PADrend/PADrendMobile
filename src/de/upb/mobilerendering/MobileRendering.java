package de.upb.mobilerendering;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import de.upb.mobilerendering.gl.InputProxy;
import de.upb.mobilerendering.gl.OpenGLCore;
import de.upb.mobilerendering.gl.RendererProxy;
import de.upb.mobilerendering.gui.BudgetSlider;
import de.upb.mobilerendering.gui.FrameCounter;
import de.upb.mobilerendering.gui.LoadSceneActivity;
import de.upb.mobilerendering.gui.MovementView;
import de.upb.mobilerendering.gui.PolygonCountView;
import de.upb.mobilerendering.gui.RotationView;

/**
 * The main Activity of the application.
 */
public class MobileRendering extends Activity
{
	/**
	 * Log-Tag used for all of the logging in this application.
	 */
	public static final String LOG_TAG = "MobileRendering";
	
	private static String _currentScenePath = "";
	
	private static GLSurfaceView _surfaceView;
	private static RendererProxy _rendererProxy;
	private static InputProxy _inputProxy;
	private static BudgetSlider _budgetSlider;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	Log.d(LOG_TAG, "MobileRendering.onCreate()");
    	
        super.onCreate(savedInstanceState);
        
        AssetCopier.copyAssets(this);
        
        // request fullscreen and hide title
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main);
        
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        PolygonCountView polygonCountView = (PolygonCountView) findViewById(R.id.polygonCountView);
        FrameCounter frameCounter  = (FrameCounter) findViewById(R.id.frameCounter);
        _budgetSlider = (BudgetSlider) findViewById(R.id.budgetSlider);
        _budgetSlider.setVisibility(View.INVISIBLE);
        
        // create and start InputRepeater (it is paused, after it has been started)
        _inputProxy = new InputProxy();
        _inputProxy.start();
        
        MovementView movementView = (MovementView) findViewById(R.id.movementView);
        movementView.setInputProxy(_inputProxy);
        RotationView rotationView = (RotationView) findViewById(R.id.rotationView);
        rotationView.setInputProxy(_inputProxy);
        
        // create and add OpenGL context to layout
        _surfaceView = new GLSurfaceView(this);
        _surfaceView.setEGLContextClientVersion(2);
        _surfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        _surfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);
        _rendererProxy = new RendererProxy(frameCounter, polygonCountView, _budgetSlider, _inputProxy);
        _surfaceView.setRenderer(_rendererProxy);
        relativeLayout.addView(_surfaceView, 0);
    }
    
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
		return true;
    }
    
    @Override
    protected void onResume()
    {
    	Log.d(LOG_TAG, "MobileRendering.onResume()");
    	
        super.onResume();
        _inputProxy.onResume();
        _surfaceView.onResume();
    }

    @Override
    protected void onPause()
    {
    	Log.d(LOG_TAG, "MobileRendering.onPause()");
    	
        super.onPause();
        _surfaceView.onPause();
        _inputProxy.onPause();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
        case R.id.menuItemLoadScene:
        	Intent intent = new Intent(this, LoadSceneActivity.class);
        	startActivity(intent);
            return true;
        case R.id.menuItemResetCamera:
        	_surfaceView.queueEvent(new Runnable()
        	{
        		public void run()
        		{
        			OpenGLCore.resetCamera();
        		}
        	});
            return true;
        case R.id.menuItemStartBenchmark:
        	_surfaceView.queueEvent(new Runnable()
        	{
        		public void run()
        		{
        			OpenGLCore.startBenchmark();
        		}
        	});
            return true;
        case R.id.menuItemShowHideRenderBudget:
        	if(_budgetSlider.getVisibility() == View.INVISIBLE)
        	{
        		_budgetSlider.setVisibility(View.VISIBLE);
        	} else {
        		_budgetSlider.setVisibility(View.INVISIBLE);
        	}
            return true;
        case R.id.menuItemToggleApproximateRendering:
        	_surfaceView.queueEvent(new Runnable()
        	{
	        	public void run()
	    		{
	    			OpenGLCore.toggleApproximateRendering();
	    		}
        	});
        case R.id.menuItemFixAxis:
        	_surfaceView.queueEvent(new Runnable()
        	{
	        	public void run()
	    		{
	    			OpenGLCore.toggleFixAxis();
	    		}
        	});
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public static GLSurfaceView getSurfaceView()
    {
    	return _surfaceView;
    }

	public static String getCurrentScenePath()
	{
		return _currentScenePath;
	}

	public static void setCurrentScenePath(String _currentScenePath)
	{
		MobileRendering._currentScenePath = _currentScenePath;
	}
}