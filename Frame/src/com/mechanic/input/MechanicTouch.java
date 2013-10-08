package com.mechanic.input;

import java.util.ArrayList;
import java.util.List;

import com.mechanic.input.Input.MechanicTouchEvent;
import com.mechanic.input.Pool.PoolFactory;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("NewApi")
public class MechanicTouch implements Touch
{
	boolean EnableMultiTouch;
	final int MaxTouchers = 20;
	boolean[] IsTouched = new boolean[MaxTouchers];
	int[] TouchX = new int[MaxTouchers];
	int[] TouchY = new int[MaxTouchers];
	Pool<MechanicTouchEvent> TouchEventPool;
	List<MechanicTouchEvent> TouchEvents = new ArrayList<MechanicTouchEvent>();
	List<MechanicTouchEvent> TouchEventsBuffer = new ArrayList<MechanicTouchEvent>();
	float ScaleX;
	float ScaleY;
	
	@SuppressWarnings("deprecation")
	public MechanicTouch(View view, float scaleX, float scaleY)
	{
		if(Integer.parseInt(VERSION.SDK) < 5)
			EnableMultiTouch = false;
		else
			EnableMultiTouch = true;
		
		PoolFactory<MechanicTouchEvent> factory = new PoolFactory<MechanicTouchEvent>()
		{
			@Override
			public MechanicTouchEvent Create()
			{
				return new MechanicTouchEvent();
			}
		};
		
		TouchEventPool = new Pool<MechanicTouchEvent>(factory, 100);
		view.setOnTouchListener(this);
		
		this.ScaleX = scaleX;
		this.ScaleY = scaleY;
	}
	
	@TargetApi(Build.VERSION_CODES.ECLAIR)
	@SuppressLint("NewApi")
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		synchronized (this)
		{
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			
			@SuppressWarnings("deprecation")
			int pointerIndex = (event.getAction() &
			MotionEvent.ACTION_POINTER_ID_MASK)
			>> MotionEvent.ACTION_POINTER_ID_SHIFT;
			
			int pointerId = event.getPointerId(pointerIndex);
			
			MechanicTouchEvent TouchEvent;
			
			switch (action)
			{
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
					TouchEvent = TouchEventPool.NewObject();
					TouchEvent.Type = MechanicTouchEvent.TOUCH_DOWN;
					TouchEvent.Pointer = pointerId;
					TouchEvent.X = TouchX[pointerId] = (int)(event.getX(pointerIndex) * ScaleX);
					TouchEvent.Y = TouchY[pointerId] = (int)(event.getY(pointerIndex) * ScaleY);
					IsTouched[pointerId] = true;
					TouchEventsBuffer.add(TouchEvent);
				break;
				
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
				case MotionEvent.ACTION_CANCEL:
					TouchEvent = TouchEventPool.NewObject();
					TouchEvent.Type = MechanicTouchEvent.TOUCH_UP;
					TouchEvent.Pointer = pointerId;
					TouchEvent.X = TouchX[pointerId] = (int)(event.getX(pointerIndex) * ScaleX);
					TouchEvent.Y = TouchY[pointerId] = (int)(event.getY(pointerIndex) * ScaleY);
					IsTouched[pointerId] = false;
					TouchEventsBuffer.add(TouchEvent);
				break;
				
				case MotionEvent.ACTION_MOVE:
					int pointerCount = event.getPointerCount();
					
					for (int i = 0; i < pointerCount; i++)
					{
						pointerIndex = i;
						pointerId = event.getPointerId(pointerIndex);
						TouchEvent = TouchEventPool.NewObject();
						TouchEvent.Type = MechanicTouchEvent.TOUCH_DRAGGED;
						TouchEvent.Pointer = pointerId;
						TouchEvent.X = TouchX[pointerId] = (int)(event.getX(pointerIndex) * ScaleX);
						TouchEvent.Y = TouchY[pointerId] = (int)(event.getY(pointerIndex) * ScaleY);
						TouchEventsBuffer.add(TouchEvent);
					}
				break;
			}
			
			return true;
		}
	}

	@Override
	public boolean IsTouchDown(int pointer)
	{
		synchronized(this)
		{
			if(pointer < 0 || pointer >= MaxTouchers)
				return false;
			else
				return IsTouched[pointer];
		}
	}

	@Override
	public int GetTouchX(int pointer)
	{
		synchronized(this)
		{
			if (pointer < 0 || pointer >= MaxTouchers)
				return 0;
			else
				return TouchX[pointer];
		}
	}

	@Override
	public int GetTouchY(int pointer)
	{
		synchronized(this)
		{
			if (pointer < 0 || pointer >= 20)
				return 0;
			else
				return TouchY[pointer];
		}
	}

	@Override
	public List<MechanicTouchEvent> GetTouchEvents()
	{
		synchronized (this)
		{
			for (int i = 0; i < TouchEvents.size(); i++)
				TouchEventPool.Free(TouchEvents.get(i));
			
			TouchEvents.clear();
			TouchEvents.addAll(TouchEventsBuffer);
			TouchEventsBuffer.clear();
			return TouchEvents;
		}
	}
}
