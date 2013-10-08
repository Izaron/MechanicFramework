package com.mechanic.game;

import com.mechanic.audio.Audio;
import com.mechanic.audio.MechanicAudio;
import com.mechanic.fileio.FileIO;
import com.mechanic.fileio.MechanicFileIO;
import com.mechanic.graphics.Graphics;
import com.mechanic.graphics.MechanicGraphics;
import com.mechanic.input.Input;
import com.mechanic.input.MechanicInput;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

public abstract class MechanicGame extends Activity implements Game
{
	Runner runner;
	Graphics graphics;
	Audio audio;
	Input input;
	FileIO fileIO;
	Screen screen;
	WakeLock wakeLock;

	static final int SCREEN_WIDTH = 80;
	static final int SCREEN_HEIGHT = 128;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		boolean IsLandscape = (getResources().getConfiguration().orientation ==
				Configuration.ORIENTATION_LANDSCAPE);
		
		int frameBufferWidth = IsLandscape ? SCREEN_HEIGHT : SCREEN_WIDTH;
		int frameBufferHeight = IsLandscape ? SCREEN_WIDTH : SCREEN_HEIGHT;
		
		Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
				frameBufferHeight, Config.RGB_565);
		
		float scaleX = (float) frameBufferWidth /
				getWindowManager().getDefaultDisplay().getWidth();
		float scaleY = (float) frameBufferHeight /
			getWindowManager().getDefaultDisplay().getHeight();
		
		runner = new Runner(null, this, frameBuffer);
		graphics = new MechanicGraphics(getAssets(), frameBuffer);
		fileIO = new MechanicFileIO(getAssets());
		audio = new MechanicAudio(this);
		input = new MechanicInput(this, runner, scaleX, scaleY);
		screen = GetStartScreen();
		setContentView(runner);
		
		PowerManager powerManager = (PowerManager)
		getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
				"Game");
	}

	@Override
	public Input GetInput()
	{
		return input;
	}
	
	@Override
	public FileIO GetFileIO()
	{
		return fileIO;
	}
	
	@Override
	public Graphics GetGraphics()
	{
		return graphics;
	}
	
	@Override
	public Audio GetAudio()
	{
		return audio;
	}
	
	@Override
	public void SetScreen(Screen screen)
	{
		if (screen == null)
			throw new IllegalArgumentException("Screen не может быть null");
		
		this.screen.Pause();
		this.screen.Dispose();
		
		screen.Resume();
		screen.Update(0);
		
		this.screen = screen;
	}

	@Override
	public Screen GetCurrentScreen()
	{
		return screen;
	}

	@Override
	public Screen GetStartScreen()
	{
		return null;
	}
	
	@SuppressLint("Wakelock")
	@Override
	public void onResume()
	{
		super.onResume();
		wakeLock.acquire();
		screen.Resume();
		runner.Resume();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		wakeLock.release();
		runner.Pause();
		screen.Pause();
		
		if(isFinishing())
			screen.Dispose();
	}
}