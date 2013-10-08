package com.mechanic.input;

import java.util.List;

public interface Input
{
	public static class MechanicKeyEvent
	{
		public static final int KEY_DOWN = 0, KEY_UP = 1;
		
		public int Type;
		public int KeyCode;
		public char KeyChar;
	}

	public static class MechanicTouchEvent
	{
		public static final int TOUCH_DOWN = 0, TOUCH_UP = 1, TOUCH_DRAGGED = 2;
		
		public int Type;
		public int X, Y;
		public int Pointer;
	}
	
	public boolean IsKeyPressed(int KeyCode);
	public boolean IsKeyPressed(char KeyChar);
	
	public boolean IsTouchDown(int pointer);
	public int GetTouchX(int pointer);
	public int GetTouchY(int pointer);
	
	public float GetAccelX();
	public float GetAccelY();
	public float GetAccelZ();
	
	public List<MechanicTouchEvent> GetTouchEvents();
	public List<MechanicKeyEvent> GetKeyEvents();
}
