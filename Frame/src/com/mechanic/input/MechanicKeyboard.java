package com.mechanic.input;

import java.util.ArrayList;
import java.util.List;

import android.view.KeyEvent;
import android.view.View;

import com.mechanic.input.Input.MechanicKeyEvent;
import com.mechanic.input.Pool.PoolFactory;
import com.mechanic.input.Pool;


public class MechanicKeyboard implements Keyboard
{
	boolean[] PressedKeys = new boolean[128];
	Pool<MechanicKeyEvent> KeyEventPool;
	
	List<MechanicKeyEvent> KeyEventsBuffer = new ArrayList<MechanicKeyEvent>();
	List<MechanicKeyEvent> KeyEvents = new ArrayList<MechanicKeyEvent>();
	
	public MechanicKeyboard(View view)
	{
		PoolFactory<MechanicKeyEvent> pool = new PoolFactory<MechanicKeyEvent>()
		{
			@Override
			public MechanicKeyEvent Create()
			{
				return new MechanicKeyEvent();
			}
		};
		KeyEventPool = new Pool<MechanicKeyEvent>(pool,100);
		
		view.setOnKeyListener(this);
		view.setFocusableInTouchMode(true);
		view.requestFocus();
	}

	public boolean IsKeyPressed(int KeyCode)
	{
		if(KeyCode < 0 || KeyCode > 127)
			return false;
		return PressedKeys[KeyCode];
	}

	public List<MechanicKeyEvent> GetKeyEvents()
	{
		synchronized(this)
		{
			for(int i = 0; i < KeyEvents.size(); i++)
				KeyEventPool.Free(KeyEvents.get(i));
			
			KeyEvents.clear();
			KeyEvents.addAll(KeyEventsBuffer);
			KeyEventsBuffer.clear();
			
			return KeyEvents;
		}
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event)
	{
		if(event.getAction() == KeyEvent.ACTION_MULTIPLE)
			return false;
		
		synchronized(this)
		{
			MechanicKeyEvent key = KeyEventPool.NewObject();
			key.KeyCode = keyCode;
			key.KeyChar = (char)event.getUnicodeChar();
			
			if(event.getAction() == KeyEvent.ACTION_DOWN)
			{
				key.Type = MechanicKeyEvent.KEY_DOWN;
				if(keyCode > 0 && keyCode < 128)
					PressedKeys[keyCode] = true;
			}
			
			if(event.getAction() == KeyEvent.ACTION_UP)
			{
				key.Type = MechanicKeyEvent.KEY_UP;
				if(keyCode > 0 && keyCode < 128)
					PressedKeys[keyCode] = false;
			}
			
			KeyEventsBuffer.add(key);
		}
		
		return false;
	}
}
