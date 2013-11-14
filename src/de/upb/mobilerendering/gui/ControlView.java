package de.upb.mobilerendering.gui;

import de.upb.mobilerendering.gl.InputProxy;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Abstract class for a views that works like a joystick.
 */
public abstract class ControlView extends View
{
	/**
	 * The maximum deflection of the joystick in pixels.
	 */
	private static final float MAX_DEFLECTION = 60.0f;
	
	private float _initialX, _initialY;
	
	private Paint _paint;
	
	protected InputProxy _inputProxy;
	
	public ControlView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		_paint = new Paint();
		_paint.setARGB(64, 255, 255, 255);
		_paint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		float halfWidth = getWidth() / 2.0f;
		canvas.drawCircle(halfWidth, halfWidth, halfWidth, _paint);
		
		super.onDraw(canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int eventType = event.getAction();
		
		switch(eventType)
		{
		case MotionEvent.ACTION_DOWN:
			_initialX = event.getX();
			_initialY = event.getY();
			break;
			
		case MotionEvent.ACTION_MOVE:
			float x = (event.getX() - _initialX) / MAX_DEFLECTION;
			if(x > 1.0f)
				x = 1.0f;
			else if(x < -1.0f)
				x = -1.0f;
			onXValueChanged(x);
			
			float z = (event.getY() - _initialY) / MAX_DEFLECTION;
			if(z > 1.0f)
				z = 1.0f;
			else if(z < -1.0f)
				z = -1.0f;
			onYValueChanged(z);
			break;
			
		case MotionEvent.ACTION_UP:
			onXValueChanged(0);
			onYValueChanged(0);
			break;
		}
		
		return true;
	}
	
	protected abstract void onXValueChanged(float value);
	protected abstract void onYValueChanged(float value);
	
	public void setInputProxy(InputProxy inputProxy)
	{
		_inputProxy = inputProxy;
	}
}
