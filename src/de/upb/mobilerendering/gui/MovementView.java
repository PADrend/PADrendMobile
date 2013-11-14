package de.upb.mobilerendering.gui;

import android.content.Context;
import android.util.AttributeSet;

/**
 * A joystick-like View for movement. 
 */
public class MovementView extends ControlView
{
	private float FACTOR = 0.05f; 
	
	public MovementView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onXValueChanged(float value)
	{
		if(_inputProxy != null)
			_inputProxy.setMovementX(FACTOR * value);
	}

	@Override
	protected void onYValueChanged(float value)
	{
		if(_inputProxy != null)
			_inputProxy.setMovementZ(FACTOR * value);	
	}
}
