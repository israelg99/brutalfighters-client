package com.brutalfighters.game.screen;

import java.util.Timer;
import java.util.TimerTask;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.brutalfighters.game.HUD.GameFont;
import com.brutalfighters.game.menu.MenuUtils;
import com.brutalfighters.game.sound.BGM;
import com.brutalfighters.game.tween.ActorAccessor;
import com.brutalfighters.game.utility.GameMath;

public class MatchmakingScreen implements Screen {
	
	private Stage stage;
	private Table table;
	private TweenManager tweenManager;

	private SpriteBatch batch;
	
	private final String FONT_NAME = new String("MainMenu"); //$NON-NLS-1$
	private final String INDICATOR_TEXT = new String("Searching for players.."); //$NON-NLS-1$
	
	private String[] waiting_lines;
	private int currentLineIndex;
	
	private BitmapFont font;
	private GlyphLayout glyphLayout;
	
	private final int WAITING = 3000;
	private Timer timer;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

		tweenManager.update(delta);
		
		batch.begin();
		
		glyphLayout = new GlyphLayout(font, INDICATOR_TEXT);
		font.draw(batch, INDICATOR_TEXT, (Gdx.graphics.getWidth()-glyphLayout.width)/2, (Gdx.graphics.getHeight()-glyphLayout.height)/2-MenuUtils.gameLogo.getHeight()/2);
		
		glyphLayout = new GlyphLayout(font, waiting_lines[currentLineIndex]);
		font.draw(batch, waiting_lines[currentLineIndex], (Gdx.graphics.getWidth()-glyphLayout.width)/2, (Gdx.graphics.getHeight()-glyphLayout.height)/2-MenuUtils.gameLogo.getHeight()/2-glyphLayout.height*2);
		
		batch.end();
		
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
		table.setFillParent(true);

		// putting stuff together
		table.add(MenuUtils.gameLogo).spaceBottom(0).padBottom(MenuUtils.logoPadBot).row();

		stage.addActor(MenuUtils.menuBG);
		stage.addActor(table);

		// creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		// heading and buttons fade-in
		Timeline.createSequence().beginSequence()
				.push(Tween.from(MenuUtils.gameLogo, ActorAccessor.ALPHA, .25f).target(0))
				.end().start(tweenManager);

		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, MenuUtils.GLIDE_DURATION).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, MenuUtils.GLIDE_DURATION).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

		tweenManager.update(Gdx.graphics.getDeltaTime());
		
		batch = new SpriteBatch();
		
		font = GameFont.valueOf(FONT_NAME).getFont();
		waiting_lines = Gdx.files.internal("menu/text/waiting_lines.txt").readString().split("\\r?\\n"); //$NON-NLS-1$ //$NON-NLS-2$
		
		currentLineIndex = GameMath.nextInt(0, waiting_lines.length-1);
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				currentLineIndex = GameMath.nextInt(0, waiting_lines.length-1);
				System.out.println(currentLineIndex);
			}
		}, WAITING, WAITING);
		
		System.out.println("No need to initialize the Game Client as it was already in the Fighter Selection menu!"); //$NON-NLS-1$
		
	}

	@Override
	public void hide() {
		timer.cancel();
		timer.purge();
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
		batch.dispose();
		
	}

}
