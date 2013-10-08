package com.mechanic.input;

import java.util.List;

import com.mechanic.input.Input.MechanicKeyEvent;

import android.view.View.OnKeyListener;

public interface Keyboard extends OnKeyListener
{
	public boolean IsKeyPressed(int KeyCode);
	public List<MechanicKeyEvent> GetKeyEvents();
}
