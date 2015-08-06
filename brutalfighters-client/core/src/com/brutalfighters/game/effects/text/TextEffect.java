package com.brutalfighters.game.effects.text;


public class TextEffect {
	public TextFX effect;
	public String text;
	public float x, y;
	public int time;
	
	public TextEffect(TextFX effect, String text, float x, float y, int time) {
		this.effect = effect;
		this.text = text;
		this.x = x;
		this.y = y;
		this.time = time;
	}
	
	public TextFX getTextFX() {
		return effect;
	}
}
