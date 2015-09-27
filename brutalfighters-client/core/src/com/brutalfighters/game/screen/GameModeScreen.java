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
import com.brutalfighters.game.multiplayer.GameMode;
import com.brutalfighters.game.sound.BGMManager;
import com.brutalfighters.game.sound.GameSFX;
import com.brutalfighters.game.tween.ActorAccessor;

public class GameModeScreen implements Screen {
	
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
		
		BGMManager.update();
		
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
		TextButton buttonMatch = new TextButton("MATCH", MenuUtils.blueButton); //$NON-NLS-1$

		TextButton buttonFreestyle = new TextButton("FREESTYLE", MenuUtils.redButton); //$NON-NLS-1$

		TextButton buttonBack = new TextButton("BACK", MenuUtils.blueButton); //$NON-NLS-1$

		// putting stuff together
		table.setPosition(0, 20);
		table.add(MenuUtils.gameLogo).padBottom(MenuUtils.logoPadBot).row();
		table.add(buttonMatch).padBottom(20).row();
		table.add(buttonFreestyle).padBottom(20).row();
		table.add(buttonBack).padBottom(0);

		stage.addActor(MenuUtils.menuBG);
		stage.addActor(table);

		// creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		// heading and buttons fade-in
		Timeline.createSequence().beginSequence()
				.push(Tween.set(buttonMatch, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonFreestyle, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonBack, ActorAccessor.ALPHA).target(0))
				.push(Tween.from(MenuUtils.gameLogo, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(0))
				.push(Tween.to(buttonMatch, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(buttonFreestyle, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(buttonBack, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.end().start(tweenManager);

		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, MenuUtils.GLIDE_DURATION).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, MenuUtils.GLIDE_DURATION).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

		tweenManager.update(Gdx.graphics.getDeltaTime());
		
		buttonMatch.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameSFX.Click.play();
				
				Timeline.createParallel().beginParallel()
				.push(Tween.to(table, ActorAccessor.ALPHA, MenuUtils.GLIDE_DURATION).target(0))
				.push(Tween.to(table, ActorAccessor.Y, MenuUtils.GLIDE_DURATION).target(table.getY() - 50)
						.setCallback(new TweenCallback() {

							@Override
							public void onEvent(int type, BaseTween<?> source) {
								((Game) Gdx.app.getApplicationListener()).setScreen(new FighterSelectScreen(GameMode.MATCH));
							}
						}))
				.end().start(tweenManager);
			}
		});
		
		buttonFreestyle.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameSFX.Click.play();
				
				Timeline.createParallel().beginParallel()
						.push(Tween.to(table, ActorAccessor.ALPHA, MenuUtils.GLIDE_DURATION).target(0))
						.push(Tween.to(table, ActorAccessor.Y, MenuUtils.GLIDE_DURATION).target(table.getY() - 50)
								.setCallback(new TweenCallback() {

									@Override
									public void onEvent(int type, BaseTween<?> source) {
										((Game) Gdx.app.getApplicationListener()).setScreen(new FighterSelectScreen(GameMode.FREESTYLE));
									}
								}))
						.end().start(tweenManager);
			}
		});
		
		buttonBack.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameSFX.Click.play();
				
				Timeline.createParallel().beginParallel()
						.push(Tween.to(table, ActorAccessor.ALPHA, MenuUtils.GLIDE_DURATION).target(0))
						.push(Tween.to(table, ActorAccessor.Y, MenuUtils.GLIDE_DURATION).target(table.getY() - 50)
								.setCallback(new TweenCallback() {

									@Override
									public void onEvent(int type, BaseTween<?> source) {
										((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
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
