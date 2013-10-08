package com.mechanic.input;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class MechanicAccelerometer implements Accelerometer
{
	float accelX, accelY, accelZ;
	
	
	public MechanicAccelerometer(Context context)
	{
		SensorManager manager = (SensorManager)
				context.getSystemService(Context.SENSOR_SERVICE);
		
		if(manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0)
		{
			Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{

	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		accelX = event.values[0];
		accelY = event.values[1];
		accelZ = event.values[2];
	}

	@Override
	public float GetAccelX()
	{
		return accelX;
	}

	@Override
	public float GetAccelY()
	{
		return accelY;
	}

	@Override
	public float GetAccelZ()
	{
		return accelZ;
	}
}
