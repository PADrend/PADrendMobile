/*
 * This file is part of the PADrendMobile project.
 * Web page: http://www.padrend.de/
 * Copyright (C) 2010 Benjamin Eikel <benjamin@eikel.org>
 * Copyright (C) 2010 Robert Gmyr
 *
 * This project is subject to the terms of the Mozilla Public License, v. 2.0.
 * You should have received a copy of the MPL along with this project; see the
 * file LICENSE. If not, you can obtain one at http://mozilla.org/MPL/2.0/.
 */
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
