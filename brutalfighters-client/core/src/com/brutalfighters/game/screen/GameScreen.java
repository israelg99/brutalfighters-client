package com.brutalfighters.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.basic.GameLoopManager;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.graphics.Shaders;
import com.brutalfighters.game.multiplayer.GameClient;
import com.brutalfighters.game.resources.Resources;
import com.brutalfighters.game.sound.BGM;
import com.brutalfighters.game.sound.SFX;

public class GameScreen implements Screen {
	
	public GameScreen() {
		ScreenManager.setScreen(ScreenManager.Screen.GAME);
		
		System.out.println("The Game Screen has been Initialized!"); //$NON-NLS-1$
		GameLoopManager.Load();
		
		System.err.println("Loading everything needed for the game now!"); //$NON-NLS-1$
		changeGameListener();
		Gdx.input.setInputProcessor(Resources.client);
		
		Gdx.input.setCursorCatched(true);
		System.out.println("Moving on to the render() method to render the game!"); //$NON-NLS-1$
		
		BGM.setGame();
		
	}
	
	@Override
	public void render(float delta) {
		
		GameLoopManager.tick(delta);
		
	}

	@Override
	public void resize(int width, int height) {
		Resources.client.updateViewPort(width, height);
	}

	@Override
	public void show() {
		GameLoopManager.Load(); // IDK EVEN WHY PUT IT ALSO IN CONSTRUCTOR && HERE.
	}

	@Override
	public void hide() {
		System.err.println("Quiting the game :("); //$NON-NLS-1$
		GameLoopManager.Quit();
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
		Resources.map.dispose();
		Render.dispose();
		SFX.disposeAll();
		HUD.dispose();
		Shaders.dispose();
		
	}
	
}
