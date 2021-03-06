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
 * A TextView to show the number of visible polygons. 
 */
public class PolygonCountView extends TextView
{
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();
	
	private final Handler _handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            int polygonCount = msg.getData().getInt("polygonCount");
            setText(NUMBER_FORMAT.format(polygonCount) + " " + getContext().getString(R.string.polygons));
        }
    };
    
    static
    {
    	NUMBER_FORMAT.setMinimumIntegerDigits(7);
    }
	
	public PolygonCountView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * Should be called after a frame has been drawn.
	 * Message-passing is used to separate the rendering- and the GUI-thread.
	 */
	public void setPolygonCount(int polygonCount)
	{
		// ask to update fpsTextView
		Message msg = _handler.obtainMessage();
        Bundle b = new Bundle();
        b.putInt("polygonCount", polygonCount);
        msg.setData(b);
        _handler.sendMessage(msg);
	}
}
