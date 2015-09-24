package com.brutalfighters.game.effects.text.effects;

import com.brutalfighters.game.HUD.GameFont;
import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.effects.text.TextEffects;
import com.brutalfighters.game.math.Vec2;


abstract public class TextEffect {
	
	private final int MAX_TIME;
	private final GameFont gameFont;
	private String text;
	private Vec2 pos;
	private int time;
	
	public TextEffect(String text, Vec2 pos, int time, int MAX_TIME, GameFont gameFont) {
		
		this.MAX_TIME = MAX_TIME;
		this.gameFont = gameFont;
		
		setText(text);
		setPos(pos);
		setTime(time);
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public Vec2 getPos() {
		return pos;
	}
	public void setPos(Vec2 pos) {
		this.pos = new Vec2(pos);
	}

	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public void subTime() {
		this.time -= GameTime.getDeltaMS();
	}

	public int getMaxTime() {
		return MAX_TIME;
	}
	public GameFont getGameFont() {
		return gameFont;
	}

	public void tick(int i) {
		update();
		if(getTime() > 0) {
			draw();
		} else {
			TextEffects.remove(i);
		}
		subTime();
	}
	
	public abstract void update();
	
	public void draw() {
		gameFont.getFont().draw(Render.getSpriteBatch(), getText(), getPos().getX(), getPos().getY());
	}
}
