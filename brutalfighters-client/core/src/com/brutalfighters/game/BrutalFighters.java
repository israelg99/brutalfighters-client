package com.brutalfighters.game;

import com.badlogic.gdx.Game;
import com.brutalfighters.game.resources.Assets;
import com.brutalfighters.game.screen.CompanySplashScreen;

public class BrutalFighters extends Game {
	
	@Override
	public void create () {
		Assets.LoadGlobalResources();
		
		setScreen(new CompanySplashScreen());
	}
}
