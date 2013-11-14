package de.upb.mobilerendering.gui;

import android.content.Context;
import android.util.AttributeSet;

/**
 * A joystick-like View for movement. 
 */
public class RotationView extends ControlView
{
	private float FACTOR = 0.5f;
	
	public RotationView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void onXValueChanged(float value)
	{
		if(_inputProxy != null)
			_inputProxy.setRotationY(FACTOR * value);
	}

	@Override
	protected void onYValueChanged(float value)
	{
		if(_inputProxy != null)
			_inputProxy.setRotationX(FACTOR * value);
	}
}
