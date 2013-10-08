package com.mechanic.audio;

import android.media.SoundPool;

public class MechanicSound implements Sound
{
	int id;
	SoundPool pool;
	
	public MechanicSound(SoundPool pool, int id)
	{
		this.pool = pool;
		this.id = id;
	}
	
	public void Play(float volume)
	{
		pool.play(id, volume, volume, 0, 0, 1);
	}
	
	public void Close()
	{
		pool.unload(id);
	}
}
