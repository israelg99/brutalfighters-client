package com.brutalfighters.game.HUD;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.brutalfighters.game.basic.Render;

public class KillsCounter {
	
	private final int FADE;

	private String message;
	private String prefix;
	
	private int enemy_killed;
	
	private Timer timer;
	
	private BitmapFont messageFont;
	private BitmapFont counterFont;
	private GlyphLayout glyphLayout;
	
	private Color brutalColor;
	
	public KillsCounter(int fade) {
		
		this.FADE = fade;
		
		resetCounter();
		
		messageFont = GameFont.EnemyKilled.getFont();
		counterFont = GameFont.KillsCounter.getFont();
		
		message = new String("ENEMY KILLED"); //$NON-NLS-1$
		prefix = new String("X"); //$NON-NLS-1$
		
		glyphLayout = new GlyphLayout();
		
		brutalColor = new Color();
		brutalColor.set(.6f, 0, 0, 1);
		
		timer = new Timer();
		
		scheduleFade();
	}
	
	private void scheduleFade() {
		timer.cancel();
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				resetCounter();
			}
		}, FADE, FADE);
	}
	
	public void render() {
		if(!isEmpty()) {
			if(getEnemiesKilled() > 2) {
				sentFontsColor(brutalColor, brutalColor);
			} else if(getEnemiesKilled() > 1) {
				sentFontsColor(Color.BLACK, brutalColor);
			} else {
				sentFontsColor(Color.BLACK, Color.WHITE);
			}
			draw();
		}
	}
	
	private void sentFontsColor(Color color1, Color color2) {
		messageFont.setColor(color1);
		counterFont.setColor(color2);
	}
	private void draw() {
		glyphLayout.setText(messageFont, message);
		float message_width = glyphLayout.width;
		glyphLayout.setText(counterFont, prefix+getEnemiesKilled());
		float counter_width = glyphLayout.width;
		
		messageFont.draw(Render.batch, message, (Gdx.graphics.getWidth()-(message_width+counter_width))/2, (Gdx.graphics.getHeight()/4));
		
		counterFont.draw(Render.batch, prefix+getEnemiesKilled(), (Gdx.graphics.getWidth()-(message_width+counter_width))/2+message_width+25, (Gdx.graphics.getHeight()/4));
	}
	
	public void enemyKilled() {
		enemy_killed++;
		scheduleFade();
	}
	private void fadeEnemyDeath() {
		enemy_killed--;
	}
	private void resetCounter() {
		enemy_killed = 0;
	}
	public int getEnemiesKilled() {
		return enemy_killed;
	}
	public boolean isEmpty() {
		return getEnemiesKilled() == 0;
	}
}
