package com.mechanic.audio;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class MechanicMusic implements Music
{
	MediaPlayer Player;
	boolean IsPrepared = false;
	
	public MechanicMusic(AssetFileDescriptor descriptor)
	{
		Player = new MediaPlayer();
		
		try
		{
			Player.setDataSource(descriptor.getFileDescriptor(),
					descriptor.getStartOffset(), descriptor.getLength());
			Player.prepare();
			IsPrepared = true;
		}
		catch(Exception ex)
		{
			throw new RuntimeException("Ќевозможно загрузить потоковую музыку");
		}
	}
	
	public void Close()
	{
		if(Player.isPlaying())
			Player.stop();
		Player.release();
	}
	
	public boolean IsLooping()
	{
		return Player.isLooping();
	}
	
	public boolean IsPlaying()
	{
		return Player.isPlaying();
	}
	
	public boolean IsStopped()
	{
		return !IsPrepared;
	}
	
	public void Play()
	{
		if(Player.isPlaying())
			return;
		
		try
		{
			synchronized(this)
			{
				if(!IsPrepared)
					Player.prepare();
				Player.start();
			}
		}
		catch(IllegalStateException ex)
		{
			ex.printStackTrace();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void SetLooping(boolean loop)
	{
		Player.setLooping(loop);
	}
	
	public void SetVolume(float volume)
	{
		Player.setVolume(volume, volume);
	}
	
	public void Stop()
	{
		Player.stop();
		synchronized(this)
		{
			IsPrepared = false;
		}
	}
	
	@Override
	public void onCompletion(MediaPlayer player)
	{
		synchronized(this)
		{
			IsPrepared = false;
		}
	}
}
