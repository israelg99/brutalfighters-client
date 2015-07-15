package com.brutalfighters.game.effects.text;


public class TextEffect {
	public String name, text;
	public float x, y;
	public int time;
	
	public TextEffect(String name, String text, float x, float y, int time) {
		this.name = name;
		this.text = text;
		this.x = x;
		this.y = y;
		this.time = time;
	}
	
	public TextFX getTextFX() {
		return TextFX.valueOf(name);
	}
}
