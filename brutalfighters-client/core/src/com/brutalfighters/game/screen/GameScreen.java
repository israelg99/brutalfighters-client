package com.brutalfighters.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.basic.GameLoopManager;
import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.graphics.Shaders;
import com.brutalfighters.game.multiplayer.GameClient;
import com.brutalfighters.game.resources.Assets;
import com.brutalfighters.game.sound.BGMManager;
import com.brutalfighters.game.sound.GameSFX;

public class GameScreen implements Screen {
	
	public GameScreen() {
		
		ScreenManager.setScreen(ScreenManager.Screen.GAME);
		
		System.out.println("The Game Screen has been Initialized!"); //$NON-NLS-1$
		
		System.err.println("Preparing everything needed for the game now!"); //$NON-NLS-1$
		
		GameTime.reset();
		
		GameLoopManager.load();
		
		changeGameListener();
		
		Gdx.input.setInputProcessor(Assets.client);
		
		Gdx.input.setCursorCatched(true);
		
		BGMManager.setGame();
		
		System.out.println("Moving on to the render() method to render the game!"); //$NON-NLS-1$
		
	}
	
	@Override
	public void render(float delta) {
		
		GameLoopManager.tick();
		
	}

	@Override
	public void resize(int width, int height) {
		Assets.client.updateViewPort(width, height);
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		System.err.println("Quiting the game :("); //$NON-NLS-1$
		GameLoopManager.quit();
		dispose();
		
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}
	
	private void changeGameListener() {
		System.err.println("Setting KryoNet's server a new network listener"); //$NON-NLS-1$
		GameClient.setNetworkListener();
	}

	@Override
	public void dispose() {
		System.err.println("Disposing Everything GAME RELATED!"); //$NON-NLS-1$
		Assets.dispose();
		Render.dispose();
		GameSFX.disposeAll();
		HUD.dispose();
		Shaders.dispose();
		
	}
	
}
