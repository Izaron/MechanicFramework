package com.mechanic;

import com.mechanic.game.Game;
import com.mechanic.game.Screen;
import com.mechanic.graphics.Graphics;
import com.mechanic.graphics.Image;

public class GameScreen extends Screen
{
	Graphics g = game.GetGraphics();
	Image wikitan;
	
	float x = 0.0f;
	
	public GameScreen(Game game)
	{
		super(game);
		wikitan = g.NewImage("wikipetan.png");
	}
 
	@Override
	public void Update(float deltaTime)
	{
		if(game.GetInput().IsTouchDown(0))
			x += 1.0f * deltaTime;
	}

	@Override
	public void Present(float deltaTime)
	{
		g.Clear(0);
		g.DrawImage(wikitan, (int)x, 0);
	}

	@Override
	public void Pause()
	{

	}

	@Override
	public void Resume()
	{

	}

	@Override
	public void Dispose()
	{
		wikitan.Dispose();
	}
}
