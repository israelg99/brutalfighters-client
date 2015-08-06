package com.brutalfighters.game.basic;

import com.badlogic.gdx.Gdx;

public class GameTime {
	private static final float stateTime = 1/60f;
	private static float gameTime = 0;
	
	public static void update() {
		gameTime += Gdx.graphics.getDeltaTime();
	}
	public static void reset() {
		gameTime = 0;
	}
	
	public static float getTime() {
		return gameTime;
	}
	
	public static float getDeltaMS() {
		return Gdx.graphics.getDeltaTime() * 1000;
	}
	
	public static float getStateTime() {
		return stateTime;
	}
}
