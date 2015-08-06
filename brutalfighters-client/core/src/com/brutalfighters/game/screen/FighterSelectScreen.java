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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.brutalfighters.game.menu.MenuUtils;
import com.brutalfighters.game.multiplayer.GameClient;
import com.brutalfighters.game.multiplayer.GameMode;
import com.brutalfighters.game.sound.BGMManager;
import com.brutalfighters.game.sound.menu.MainMenuSFX;
import com.brutalfighters.game.tween.ActorAccessor;

public class FighterSelectScreen implements Screen {
	
	private Stage stage;
	private Table table;
	private TweenManager tweenManager;
	private GameMode gamemode;
	
	public FighterSelectScreen(GameMode gamemode) {
		setGameMode(gamemode);
	}
	
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
		
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);
		
		
		// Not in MenuUtils because different listeners will apply to the button.
		ImageButton logo = new ImageButton(MenuUtils.gameLogo.getDrawable());
		
		ImageButton blaze_face = getFace("blaze"); //$NON-NLS-1$
		ImageButton dusk_face = getFace("dusk"); //$NON-NLS-1$
		ImageButton surge_face = getFace("surge"); //$NON-NLS-1$
		ImageButton chip_face = getFace("chip"); //$NON-NLS-1$
		ImageButton lust_face = getFace("lust"); //$NON-NLS-1$
		
		table = new Table(MenuUtils.skin);
		
		// putting stuff together
		table.setPosition(0, 20);
		table.add(logo).padBottom(MenuUtils.logoPadBot).colspan(2).center().row();
		table.add(blaze_face).pad(20);
		table.add(lust_face).pad(20).row();
		table.add(surge_face).pad(20);
		table.add(chip_face).pad(20).row();
		table.add(dusk_face).pad(20).colspan(2).center();
		
		stage.addActor(MenuUtils.menuBG);
		stage.addActor(table);
		
		table.setFillParent(true);

		// creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		// heading and buttons fade-in
		Timeline.createSequence().beginSequence()
				.push(Tween.set(blaze_face, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(dusk_face, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(surge_face, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(chip_face, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(lust_face, ActorAccessor.ALPHA).target(0))
				.push(Tween.from(MenuUtils.gameLogo, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(0))
				.push(Tween.to(blaze_face, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(dusk_face, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(surge_face, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(chip_face, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(lust_face, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.end().start(tweenManager);

		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, MenuUtils.GLIDE_DURATION).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, MenuUtils.GLIDE_DURATION).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

		tweenManager.update(Gdx.graphics.getDeltaTime());
				
		logo.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				MainMenuSFX.playClick();
				
				Timeline.createParallel().beginParallel()
				.push(Tween.to(table, ActorAccessor.ALPHA, MenuUtils.GLIDE_DURATION).target(0))
				.push(Tween.to(table, ActorAccessor.Y, MenuUtils.GLIDE_DURATION).target(table.getY() - 50)
						.setCallback(new TweenCallback() {

							@Override
							public void onEvent(int type, BaseTween<?> source) {
								MenuUtils.quitLobby();
							}
						}))
				.end().start(tweenManager);
			}
		});
		
		addListener(blaze_face, "blaze"); //$NON-NLS-1$
		addListener(dusk_face, "dusk"); //$NON-NLS-1$
		addListener(surge_face, "surge"); //$NON-NLS-1$
		addListener(chip_face, "chip"); //$NON-NLS-1$
		addListener(lust_face, "lust"); //$NON-NLS-1$
		
	}

	private ImageButton getFace(String fighter) {
		ImageButton face = new ImageButton(MenuUtils.skin.getDrawable(fighter), MenuUtils.skin.getDrawable(fighter + "_down"), MenuUtils.skin.getDrawable(fighter + "_hover")); //$NON-NLS-1$ //$NON-NLS-2$
		face.getStyle().imageOver = MenuUtils.skin.getDrawable(fighter + "_hover"); //$NON-NLS-1$
		
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
								GameClient.setConnection(gamemode, fighter);
								((Game) Gdx.app.getApplicationListener()).setScreen(new MatchmakingScreen());
							}
						}))
				.end().start(tweenManager);
			}
		});
	}
	
	private GameMode getGameMode() {
		return gamemode;
	}
	private void setGameMode(GameMode gamemode) {
		this.gamemode = gamemode;
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
