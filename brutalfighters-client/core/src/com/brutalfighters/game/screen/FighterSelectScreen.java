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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.brutalfighters.game.menu.MenuUtils;
import com.brutalfighters.game.multiplayer.GameClient;
import com.brutalfighters.game.sound.BGM;
import com.brutalfighters.game.sound.menu.MainMenuSFX;
import com.brutalfighters.game.tween.ActorAccessor;

public class FighterSelectScreen implements Screen {
	
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
		
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		table = new Table(MenuUtils.skin);
		
		ButtonStyle blaze_face = getFace("blaze"); //$NON-NLS-1$
		ButtonStyle dusk_face = getFace("dusk"); //$NON-NLS-1$
		ButtonStyle surge_face = getFace("surge"); //$NON-NLS-1$
		ButtonStyle chip_face = getFace("chip"); //$NON-NLS-1$
		ButtonStyle lust_face = getFace("lust"); //$NON-NLS-1$
		
		Button blaze = new Button(blaze_face);
		addListener(blaze, "blaze"); //$NON-NLS-1$
		
		Button dusk = new Button(dusk_face);
		addListener(dusk, "dusk"); //$NON-NLS-1$
		
		Button surge = new Button(surge_face);
		addListener(surge, "surge"); //$NON-NLS-1$
		
		Button chip = new Button(chip_face);
		addListener(chip, "chip"); //$NON-NLS-1$
		
		Button lust = new Button(lust_face);
		addListener(lust, "lust"); //$NON-NLS-1$
		
		
		// putting stuff together
		table.setPosition(0, 20);
		table.add(MenuUtils.gameLogo).padBottom(MenuUtils.logoPadBot).colspan(2).center().row();
		table.add(blaze).pad(20);
		table.add(lust).pad(20).row();
		table.add(surge).pad(20);
		table.add(chip).pad(20).row();
		table.add(dusk).pad(20).colspan(2).center();
		
		stage.addActor(MenuUtils.bg);
		stage.addActor(table);
		
		table.setFillParent(true);

		// creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		// heading and buttons fade-in
		Timeline.createSequence().beginSequence()
				.push(Tween.set(blaze, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(dusk, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(surge, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(chip, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(lust, ActorAccessor.ALPHA).target(0))
				.push(Tween.from(MenuUtils.gameLogo, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(0))
				.push(Tween.to(blaze, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(dusk, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(surge, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(chip, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(lust, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.end().start(tweenManager);

		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, MenuUtils.GLIDE_DURATION).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, MenuUtils.GLIDE_DURATION).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

		tweenManager.update(Gdx.graphics.getDeltaTime());
		
		
		System.out.println("Initializing the Game Client for Fighter Selection!"); //$NON-NLS-1$
		GameClient.Load();
		GameClient.setConnectListener();
		
	}
	
	private ButtonStyle getFace(String fighter) {
		ButtonStyle face = new ButtonStyle(MenuUtils.skin.getDrawable(fighter), MenuUtils.skin.getDrawable(fighter + "_down"), MenuUtils.skin.getDrawable(fighter + "_hover")); //$NON-NLS-1$ //$NON-NLS-2$
		face.over = MenuUtils.skin.getDrawable(fighter + "_hover"); //$NON-NLS-1$
		
		return face;
	}
	
	private void addListener(Button face, final String fighter) {
		face.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				MainMenuSFX.playClick();
				
				Timeline.createParallel().beginParallel()
				.push(Tween.to(table, ActorAccessor.ALPHA, MenuUtils.GLIDE_DURATION).target(0))
				.push(Tween.to(table, ActorAccessor.Y, MenuUtils.GLIDE_DURATION).target(table.getY() - 50)
						.setCallback(new TweenCallback() {

							@Override
							public void onEvent(int type, BaseTween<?> source) {
								GameClient.setConnection(fighter);
								((Game) Gdx.app.getApplicationListener()).setScreen(new MatchmakingScreen());
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
