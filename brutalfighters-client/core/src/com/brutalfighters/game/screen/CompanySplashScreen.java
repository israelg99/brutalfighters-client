package com.brutalfighters.game.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brutalfighters.game.menu.MenuUtils;
import com.brutalfighters.game.sound.BGM;
import com.brutalfighters.game.tween.SpriteAccessor;

public class CompanySplashScreen implements Screen {
	private SpriteBatch batch;
	private Sprite splash;
	private TweenManager tweenManager;
	private float fadeTime = 3.0f;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		splash.draw(batch);
		batch.end();

		tweenManager.update(delta);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
		ScreenManager.setScreen(ScreenManager.Screen.MENU);
		
		Gdx.input.setCursorCatched(true);
		
		batch = new SpriteBatch();
		
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		splash = new Sprite(new Texture(MenuUtils.companyLogo_path));
		splash.setPosition(Gdx.graphics.getWidth() / 2 - splash.getWidth() / 2, Gdx.graphics.getHeight() / 2 - splash.getHeight() / 2);

		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, fadeTime).target(1).repeatYoyo(1, .5f).setCallback(new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
				BGM.setMainMenu();
			}
		}).start(tweenManager);

		tweenManager.update(Float.MIN_VALUE); // update once avoid short flash of splash before animation
		
	}

	@Override
	public void hide() {
		dispose();
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		
	}

}
