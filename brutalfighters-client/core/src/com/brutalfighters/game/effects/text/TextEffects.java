package com.brutalfighters.game.effects.text;

import java.util.ArrayList;

import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.player.Player;

public class TextEffects {
	private static ArrayList<TextEffect> textEffects;
	
	public static void load() {
		textEffects = new ArrayList<TextEffect>();
	}
	
	public static void add(TextEffect te) {
		textEffects.add(te);
	}
	public static void add(TextFX effect, String text, float x, float y, int time) {
		textEffects.add(new TextEffect(effect, text, x, y, time));
	}
	public static void add(TextFX effect, Player player) {
		add(effect.getDefault(player));	
	}
	public static void remove(int i) {
		textEffects.remove(i);
	}
	public static TextEffect get(int i) {
		return textEffects.get(i);
	}
	
	public static void renderTextEffects() {
		for(int i = 0; i < textEffects.size(); i++) {
			render(get(i), i);
		}
	}
	
	public static void render(TextEffect te, int i) {
		te.effect.update(te);
		if(te.time > 0) {
			te.effect.draw(te);
		} else {
			TextEffects.remove(i);
		}
		te.time -= GameTime.getDeltaMS();
	}
	
}
