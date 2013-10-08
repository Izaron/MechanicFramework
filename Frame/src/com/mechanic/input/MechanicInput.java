package com.mechanic.input;

import java.util.List;

import android.content.Context;
import android.view.View;


public class MechanicInput implements Input
{
	MechanicKeyboard keyboard;
	MechanicAccelerometer accel;
	MechanicTouch touch;
	
	
	public MechanicInput(Context context, View view, float scaleX, float scaleY)
	{
		accel = new MechanicAccelerometer(context);
		keyboard = new MechanicKeyboard(view);
		touch = new MechanicTouch(view, scaleX, scaleY);
	}

	@Override
	public boolean IsKeyPressed(int keyCode)
	{
		return keyboard.IsKeyPressed(keyCode);
	}
	
	@Override
	public boolean IsKeyPressed(char keyChar)
	{
		return keyboard.IsKeyPressed(keyChar);
	}

	@Override
	public boolean IsTouchDown(int pointer)
	{
		return touch.IsTouchDown(pointer);
	}

	@Override
	public int GetTouchX(int pointer)
	{
		return touch.GetTouchX(pointer);
	}

	@Override
	public int GetTouchY(int pointer)
	{
		return touch.GetTouchY(pointer);
	}

	@Override
	public float GetAccelX()
	{
		return accel.GetAccelX();
	}

	@Override
	public float GetAccelY()
	{
		return accel.GetAccelY();
	}

	@Override
	public float GetAccelZ()
	{
		return accel.GetAccelZ();
	}

	@Override
	public List<MechanicTouchEvent> GetTouchEvents()
	{
		return touch.GetTouchEvents();
	}

	@Override
	public List<MechanicKeyEvent> GetKeyEvents()
	{
		return keyboard.GetKeyEvents();
	}
}
