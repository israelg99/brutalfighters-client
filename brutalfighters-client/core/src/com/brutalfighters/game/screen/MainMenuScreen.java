package com.brutalfighters.game.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.brutalfighters.game.menu.MenuUtils;
import com.brutalfighters.game.multiplayer.GameClient;
import com.brutalfighters.game.sound.BGM;
import com.brutalfighters.game.sound.menu.MainMenuSFX;
import com.brutalfighters.game.tween.ActorAccessor;

public class MainMenuScreen implements Screen {
	
	private Stage stage;
	private Table table;
	private TweenManager tweenManager;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

		tweenManager.update(delta);
		
		BGM.update();
		
	}

	@Override
	public void resize(int width, int height) {
		table.invalidateHierarchy();
		
	}

	@Override
	public void show() {
		ScreenManager.setScreen(ScreenManager.Screen.MENU);
		
		Gdx.input.setCursorCatched(false);
		
		MenuUtils.reset();
		
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		table = new Table(MenuUtils.skin);
		table.setFillParent(true);
				
		// Creating Buttons
		TextButton buttonPlay = new TextButton("PLAY", MenuUtils.blueButton); //$NON-NLS-1$

		TextButton buttonSettings = new TextButton("SETTINGS", MenuUtils.blueButton); //$NON-NLS-1$

		TextButton buttonExit = new TextButton("EXIT", MenuUtils.blueButton); //$NON-NLS-1$

		// putting stuff together
		table.setPosition(0, 20);
		table.add(MenuUtils.gameLogo).padBottom(MenuUtils.logoPadBot).row();
		table.add(buttonPlay).padBottom(20).row();
		table.add(buttonSettings).padBottom(20).row();
		table.add(buttonExit).padBottom(0);

		stage.addActor(MenuUtils.menuBG);
		stage.addActor(table);

		// creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		// heading and buttons fade-in
		Timeline.createSequence().beginSequence()
				.push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonSettings, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
				.push(Tween.from(MenuUtils.gameLogo, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(0))
				.push(Tween.to(buttonPlay, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(buttonSettings, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(buttonExit, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.end().start(tweenManager);

		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, MenuUtils.GLIDE_DURATION).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, MenuUtils.GLIDE_DURATION).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

		tweenManager.update(Gdx.graphics.getDeltaTime());
		
		buttonPlay.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(GameClient.isLoaded()) {
					MainMenuSFX.playClick();
					
					Timeline.createParallel().beginParallel()
					.push(Tween.to(table, ActorAccessor.ALPHA, MenuUtils.GLIDE_DURATION).target(0))
					.push(Tween.to(table, ActorAccessor.Y, MenuUtils.GLIDE_DURATION).target(table.getY() - 50)
							.setCallback(new TweenCallback() {
	
								@Override
								public void onEvent(int type, BaseTween<?> source) {
									((Game) Gdx.app.getApplicationListener()).setScreen(new GameModeScreen());
								}
							}))
					.end().start(tweenManager);
				}
			}
		});
		
		buttonSettings.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				MainMenuSFX.playClick();
				
				Timeline.createParallel().beginParallel()
						.push(Tween.to(table, ActorAccessor.ALPHA, MenuUtils.GLIDE_DURATION).target(0))
						.push(Tween.to(table, ActorAccessor.Y, MenuUtils.GLIDE_DURATION).target(table.getY() - 50)
								.setCallback(new TweenCallback() {

									@Override
									public void onEvent(int type, BaseTween<?> source) {
										((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsScreen());
									}
								}))
						.end().start(tweenManager);
			}
		});
		
		buttonExit.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				MainMenuSFX.playClick();
				
				Timeline.createParallel().beginParallel()
						.push(Tween.to(table, ActorAccessor.ALPHA, MenuUtils.GLIDE_DURATION).target(0))
						.push(Tween.to(table, ActorAccessor.Y, MenuUtils.GLIDE_DURATION).target(table.getY() - 50)
								.setCallback(new TweenCallback() {

									@Override
									public void onEvent(int type, BaseTween<?> source) {
										MenuUtils.disposeExit();
										Gdx.app.exit();
									}
								}))
						.end().start(tweenManager);
			}
		});
		
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
		stage.dispose();
		
	}

}
