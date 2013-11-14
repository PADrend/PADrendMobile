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
package de.padrend.mobile.gui;

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
