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
	
	private BitmapFont font;
	private GlyphLayout glyphLayout;
	
	private Color brutalColor;
	
	public KillsCounter(int fade) {
		
		this.FADE = fade;
		
		resetCounter();
		
		font = GameFont.KillsCounter.getFont();
		
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
				draw(brutalColor, brutalColor);
			} else if(getEnemiesKilled() > 1) {
				draw(Color.BLACK, brutalColor);
			} else {
				draw(Color.BLACK, Color.WHITE);
			}
		}
	}
	
	private void draw(Color messageColor, Color counterColor) {
		glyphLayout.setText(font, message);
		float message_width = glyphLayout.width;
		glyphLayout.setText(font, prefix+getEnemiesKilled());
		float counter_width = glyphLayout.width;
		
		font.setColor(messageColor);
		font.draw(Render.batch, message, (Gdx.graphics.getWidth()-(message_width+counter_width))/2, (Gdx.graphics.getHeight()/4));
		
		font.setColor(counterColor);
		font.draw(Render.batch, prefix+getEnemiesKilled(), (Gdx.graphics.getWidth()-(message_width+counter_width))/2+message_width+25, (Gdx.graphics.getHeight()/4));
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
