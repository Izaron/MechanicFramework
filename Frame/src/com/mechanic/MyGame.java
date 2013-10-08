package com.mechanic;

import com.mechanic.game.MechanicGame;
import com.mechanic.game.Screen;

public class MyGame extends MechanicGame
{
	@Override
	public Screen GetStartScreen()
	{
		return new GameScreen(this);
	}
}
