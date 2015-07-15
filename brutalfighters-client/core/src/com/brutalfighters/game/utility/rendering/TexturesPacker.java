package com.brutalfighters.game.utility.rendering;

import java.util.ArrayList;

public class TexturesPacker {
	private ArrayList<TexturePacker> textures;
	
	public TexturesPacker() {
		textures = new ArrayList<TexturePacker>();
	}
	public TexturesPacker(TexturePacker tex) {
		this();
		add(tex);
	}
	
	public void add(TexturePacker tex) {
		textures.add(tex);
	}
	public TexturePacker get(int i) {
		return textures.get(i);
	}
	public int length() {
		return textures.size();
	}
	public ArrayList<TexturePacker> getTextures() {
		return textures;
	}
}
