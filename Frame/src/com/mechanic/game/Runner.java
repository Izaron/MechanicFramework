package com.mechanic.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class Runner extends SurfaceView implements Runnable
{
	MechanicGame game;
	Canvas canvas;
	Bitmap buffer;
	Thread thread = null;
	SurfaceHolder holder;
	volatile boolean running = false;
	
	public Runner(Object context, MechanicGame game,
			Bitmap buffer)
	{
		super(game);
		this.game = game;
		this.buffer = buffer;
		this.holder = getHolder();
	}

	public void Resume()
	{
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run()
	{
		Rect dstRect = new Rect();
		long startTime = System.nanoTime();
		
		while(running)
		{
			if(!holder.getSurface().isValid())
				continue;
			
			float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
			startTime = System.nanoTime();
			
			game.GetCurrentScreen().Update(deltaTime);
			game.GetCurrentScreen().Present(deltaTime);
			
			canvas = holder.lockCanvas();
			canvas.getClipBounds(dstRect);
			canvas.drawBitmap(buffer, null, dstRect, null);
			holder.unlockCanvasAndPost(canvas);
		}
	}
	
	public void Pause()
	{
		running = false;
		
		while(true)
		{
			try
			{
				thread.join();
				break;
			}
			catch (InterruptedException e)
			{

			}
		}
	}
}
