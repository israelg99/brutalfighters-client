package com.brutalfighters.game.effects.text;

import java.util.ArrayList;

import com.brutalfighters.game.effects.text.effects.TextEffect;

public class TextEffects {
	private static ArrayList<TextEffect> textEffects;
	
	public static void load() {
		textEffects = new ArrayList<TextEffect>();
	}
	
	public static void add(TextEffect te) {
		textEffects.add(te);
	}
	public static void remove(int i) {
		textEffects.remove(i);
	}
	public static TextEffect get(int i) {
		return textEffects.get(i);
	}
	
	public static void renderTextEffects() {
		for(int i = 0; i < textEffects.size(); i++) {
			tick(get(i), i);
		}
	}
	
	public static void tick(TextEffect te, int i) {
		te.tick(i);
	}
	
}
