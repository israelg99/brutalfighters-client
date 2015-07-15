package com.brutalfighters.game.effects.text;

import java.util.ArrayList;

import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.player.Player;

public class TextEffects {
	private ArrayList<TextEffect> textEffects;
	
	public TextEffects() {
		textEffects = new ArrayList<TextEffect>();
	}
	
	public void add(TextEffect te) {
		textEffects.add(te);
	}
	public void add(String name, String text, float x, float y, int time) {
		textEffects.add(new TextEffect(name, text, x, y, time));
	}
	public void add(String name, Player player) {
		add(TextFX.getDefault(name, player));	
	}
	public void remove(int i) {
		textEffects.remove(i);
	}
	public TextEffect get(int i) {
		return textEffects.get(i);
	}
	
	public void renderTextEffects() {
		for(int i = 0; i < textEffects.size(); i++) {
			TextFX.render(Render.batch, get(i), i);
		}
	}
}
