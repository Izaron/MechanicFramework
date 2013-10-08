package com.mechanic.game;

public abstract class Screen
{
	protected final Game game;
	
	public Screen(Game game)
	{
		this.game = game;
	}
	public abstract void Update(float deltaTime);
	public abstract void Present(float deltaTime);
	public abstract void Pause();
	public abstract void Resume();
	public abstract void Dispose();
}
