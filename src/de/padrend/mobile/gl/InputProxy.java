/*
 * This file is part of the PADrendMobile project.
 * Web page: http://www.padrend.de/
 * Copyright (C) 2010 Robert Gmyr
 *
 * This project is subject to the terms of the Mozilla Public License, v. 2.0.
 * You should have received a copy of the MPL along with this project; see the
 * file LICENSE. If not, you can obtain one at http://mozilla.org/MPL/2.0/.
 */
package de.padrend.mobile.gl;

/**
 * A Thread to receive interactions from the GUI and accumulate them fixed interval.
 */
public class InputProxy extends Thread
{
	/**
	 * The interval in which the interactions should be accumulated.
	 */
	private static final int REPEAT_INTERVAL_MS = 5;
	
	private boolean _paused;
	
	private float _movementX, _movementZ;
	private float _rotationX, _rotationY;
	
	private float _accumulatedMovementX, _accumulatedMovementZ;
	private float _accumulatedRotationX, _accumulatedRotationY;
	
	public InputProxy()
	{
		_paused = true;
		
		_movementX = 0.0f;
		_movementZ = 0.0f;
		_rotationX = 0.0f;
		_rotationY = 0.0f;
		
		_accumulatedMovementX = 0.0f;
		_accumulatedMovementZ = 0.0f;
		_accumulatedRotationX = 0.0f;
		_accumulatedRotationY = 0.0f;
	}
	
	public void run()
	{
		try {
			while(true)
			{
				waitIfPaused();				
				accumulateInput();
				sleep(REPEAT_INTERVAL_MS);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void onResume()
	{
		_paused = false;
		notify();
	}
	
	public synchronized void onPause()
	{
		_paused = true;
	}
	
	public synchronized void waitIfPaused() throws InterruptedException
	{
		if(_paused)
			wait();
	}
	
	private synchronized void accumulateInput()
	{
		_accumulatedMovementX += _movementX;
		_accumulatedMovementZ += _movementZ;
		_accumulatedRotationX += _rotationX;
		_accumulatedRotationY += _rotationY;
	}
	
	public synchronized float getAccumulatedMovementX()
	{
		float temp = _accumulatedMovementX;
		_accumulatedMovementX = 0.0f;
		return temp;
	}
	
	public synchronized float getAccumulatedMovementZ()
	{
		float temp = _accumulatedMovementZ;
		_accumulatedMovementZ = 0.0f;
		return temp;
	}
	
	public synchronized float getAccumulatedRotationX()
	{
		float temp = _accumulatedRotationX;
		_accumulatedRotationX = 0.0f;
		return temp;
	}
	
	public synchronized float getAccumulatedRotationY()
	{
		float temp = _accumulatedRotationY;
		_accumulatedRotationY = 0.0f;
		return temp;
	}
	
	public synchronized void setMovementX(float value)
	{
		_movementX = value;
	}
	
	public synchronized void setMovementZ(float value)
	{
		_movementZ = value;
	}
	
	public synchronized void setRotationX(float value)
	{
		_rotationX = value;
	}
	
	public synchronized void setRotationY(float value)
	{
		_rotationY = value;
	}
}
