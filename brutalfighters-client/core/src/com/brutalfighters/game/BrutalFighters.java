package com.brutalfighters.game;

import com.badlogic.gdx.Game;
import com.brutalfighters.game.resources.Resources;
import com.brutalfighters.game.screen.CompanySplashScreen;

public class BrutalFighters extends Game {
	
	@Override
	public void create () {
		Resources.LoadGlobalResources();
		
		setScreen(new CompanySplashScreen());
	}
}
