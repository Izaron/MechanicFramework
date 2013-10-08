package com.mechanic.input;

import java.util.List;

import com.mechanic.input.Input.MechanicTouchEvent;

import android.view.View.OnTouchListener;

public interface Touch extends OnTouchListener
{
	public boolean IsTouchDown(int pointer);
	public int GetTouchX(int pointer);
	public int GetTouchY(int pointer);
	
	public List<MechanicTouchEvent> GetTouchEvents();
}
