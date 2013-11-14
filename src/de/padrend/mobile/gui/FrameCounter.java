/*
 * This file is part of the PADrendMobile project.
 * Web page: http://www.padrend.de/
 * Copyright (C) 2010 Robert Gmyr
 *
 * This project is subject to the terms of the Mozilla Public License, v. 2.0.
 * You should have received a copy of the MPL along with this project; see the
 * file LICENSE. If not, you can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.padrend.mobile.gui;

import java.text.NumberFormat;

import de.padrend.mobile.R;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * A TextView to calculate and show the frames per second. 
 */
public class FrameCounter extends TextView
{
	/**
	 * The FPS-value is smoothed by this parameter.
	 * For a value of 10 every 10 frames am new mean value is shown.
	 */
	private static final int FPS_SMOOTH_VALUE = 5;
	
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();
	
	private long _frameCount;
	
	private long _oldTime;
	private double _fps;

    private final Handler _fpsHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            double fps = msg.getData().getDouble("fps");
            setText(NUMBER_FORMAT.format(fps) + " " + getContext().getString(R.string.fps));
        }
    };
	
    static
    {
    	NUMBER_FORMAT.setMaximumFractionDigits(2);
    }
    
	public FrameCounter(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
		_frameCount = 0;
		
		_oldTime = System.currentTimeMillis();
		_fps = 0;
	}
	
	/**
	 * Has to be called after a frame has been drawn.
	 * Message-passing is used to separate the rendering- and the GUI-thread.
	 */
	public void frameDrawn()
	{
		_frameCount++;
		
		// calculate fps
		long time = System.currentTimeMillis();
		_fps += (1.0 / ((time - _oldTime) / 1000.0));
		_oldTime = time;
		
		if(_frameCount % FPS_SMOOTH_VALUE == 0)
		{
			_fps /= FPS_SMOOTH_VALUE;
			
			// ask to update fpsTextView
			Message msg = _fpsHandler.obtainMessage();
	        Bundle b = new Bundle();
	        b.putDouble("fps", _fps);
	        msg.setData(b);
	        _fpsHandler.sendMessage(msg);
	        
	        _fps = 0;
		}
	}
}
