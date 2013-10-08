package com.mechanic.audio;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

public class MechanicAudio implements Audio
{
	AssetManager assets;
	SoundPool pool;
	
	public MechanicAudio(Activity activity)
	{
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		pool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}
	
	public Music NewMusic(String name)
	{
		try
		{
			AssetFileDescriptor descriptor = assets.openFd(name);
			return new MechanicMusic(descriptor);
		}
		catch(IOException ex)
		{
			throw new RuntimeException("Невозможно загрузить потоковую музыку " + name);
		}
	}
	
	public Sound NewSound(String name)
	{
		try
		{
			AssetFileDescriptor descriptor = assets.openFd(name);
			int id = pool.load(descriptor, 0);
			return new MechanicSound(pool, id);
		}
		catch(IOException ex)
		{
			throw new RuntimeException("Невозможно загрузить звуковой эффект " + name);
		}
	}
}
