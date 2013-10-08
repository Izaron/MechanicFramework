package com.mechanic.game;

import com.mechanic.audio.Audio;
import com.mechanic.fileio.FileIO;
import com.mechanic.graphics.Graphics;
import com.mechanic.input.Input;

public interface Game
{
	public Input GetInput();
	public FileIO GetFileIO();
	public Graphics GetGraphics();
	public Audio GetAudio();
	public void SetScreen(Screen screen);
	public Screen GetCurrentScreen();
	public Screen GetStartScreen();
}
