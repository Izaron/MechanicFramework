package com.mechanic.input;

import android.hardware.SensorEventListener;

public interface Accelerometer extends SensorEventListener
{
	public float GetAccelX();
	public float GetAccelY();
	public float GetAccelZ();
}
