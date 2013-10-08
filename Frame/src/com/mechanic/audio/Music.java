package com.mechanic.audio;

import android.media.MediaPlayer.OnCompletionListener;

public interface Music extends OnCompletionListener
{
	public void Close();
	public boolean IsLooping();
	public boolean IsPlaying();
	public boolean IsStopped();
	public void Play();
	public void SetLooping(boolean loop);
	public void SetVolume(float volume);
	public void Stop();
}
