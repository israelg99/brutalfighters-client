package com.brutalfighters.game.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.brutalfighters.game.menu.MenuUtils;
import com.brutalfighters.game.resources.Prefs;
import com.brutalfighters.game.sound.BGMManager;
import com.brutalfighters.game.sound.GameSFX;
import com.brutalfighters.game.tween.ActorAccessor;

public class SettingsScreen implements Screen {

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
		
		// Blue Style Slider
		TextureAtlas ssAtlas = new TextureAtlas(Gdx.files.internal("menu/ui/blueSlider.atlas")); //$NON-NLS-1$
		MenuUtils.skin.addRegions(ssAtlas);
		SliderStyle ss = new SliderStyle(MenuUtils.skin.getDrawable("blueSlider_back"), MenuUtils.skin.getDrawable("blueSlider_knob")); //$NON-NLS-1$ //$NON-NLS-2$
		
		
		// Blue Check Box Style
		TextureAtlas cbsAtlas = new TextureAtlas(Gdx.files.internal("menu/ui/blue_checkbox.atlas")); //$NON-NLS-1$
		MenuUtils.skin.addRegions(cbsAtlas);
		CheckBoxStyle cbs = new CheckBoxStyle(MenuUtils.skin.getDrawable("checkbox"), MenuUtils.skin.getDrawable("blue_v"), MenuUtils.menuFont, Color.WHITE); //$NON-NLS-1$ //$NON-NLS-2$
		
		
		// PP Effects
		
		final CheckBox ppeffects = new CheckBox("", cbs); //$NON-NLS-1$
		ppeffects.setChecked(Prefs.getPPEffects());
		ppeffects.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Prefs.setPPEffects(ppeffects.isChecked());
			}
		});
		
		Label pp_cb = new Label("Post  Processing  Effects :", new LabelStyle(MenuUtils.menuFont, Color.WHITE)); //$NON-NLS-1$
		
		
		// Volume
		
		final Slider vol = new Slider( 0f, 1f, 0.1f, false, ss );
		vol.setValue(Prefs.getVolume());
		vol.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Prefs.setVolume(vol.getValue());
				
			}
			
		});
		
		Label volText = new Label("Volume :", new LabelStyle(MenuUtils.menuFont, Color.WHITE)); //$NON-NLS-1$
		
		// Motion Blur
		
		final Slider motionBlur = new Slider( 0f, 2f, 0.1f, false, ss );
		motionBlur.setValue(Prefs.getMotionBlur());
		motionBlur.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Prefs.setMotionBlur(motionBlur.getValue());
				
			}
			
		});
		
		Label motionBlurText = new Label("Motion  Blur :", new LabelStyle(MenuUtils.menuFont, Color.WHITE)); //$NON-NLS-1$
		
		// Back
		
		TextButton buttonBack = new TextButton("Back", MenuUtils.blueButton); //$NON-NLS-1$
		buttonBack.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameSFX.Click.play();
				Prefs.flush();
				
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

		// putting stuff together
		table.setPosition(0, 20);
		table.add(MenuUtils.gameLogo).padBottom(MenuUtils.logoPadBot).colspan(2).center().row();
		table.add(pp_cb).padBottom(20);
		table.add(ppeffects).padBottom(32).row();
		table.add(volText).padBottom(14);
		table.add(vol).padBottom(20).row();
		table.add(motionBlurText).padBottom(25);
		table.add(motionBlur).padBottom(26).row();
		table.add(buttonBack).colspan(2).center();

		stage.addActor(MenuUtils.menuBG);
		stage.addActor(table);

		// creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		// heading and buttons fade-in
		Timeline.createSequence().beginSequence()
				.push(Tween.set(pp_cb, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(ppeffects, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(volText, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(vol, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(motionBlurText, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(motionBlur, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonBack, ActorAccessor.ALPHA).target(0))
				.push(Tween.from(MenuUtils.gameLogo, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(0))
				.push(Tween.to(pp_cb, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(ppeffects, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(volText, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(vol, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(motionBlurText, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(motionBlur, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.push(Tween.to(buttonBack, ActorAccessor.ALPHA, MenuUtils.FADE_DURATION).target(1))
				.end().start(tweenManager);

		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, MenuUtils.GLIDE_DURATION).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, MenuUtils.GLIDE_DURATION).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

		tweenManager.update(Gdx.graphics.getDeltaTime());
		
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
