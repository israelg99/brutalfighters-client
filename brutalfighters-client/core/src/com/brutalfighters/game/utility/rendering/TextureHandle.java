package com.brutalfighters.game.utility.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class TextureHandle {
	
	public static Texture getTexture(String path, boolean linear) {
		Texture sheet = new Texture(Gdx.files.internal(path));
		if(linear) {
			sheet.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		return sheet;
	}
	
	public static TextureRegion[][] TextureSplit(String path, int w, int h, boolean linear, boolean flip) {
		Texture sheet = getTexture(path, linear);
		
		TextureRegion[][] temp = TextureRegion.split(sheet, w, h);
		for(int i = 0; i < temp.length; i++) {
			for(int j = 0; j < temp[i].length; j++) {
				temp[i][j].flip(false, flip);
			}
		}
		
		return temp;
	}
	
	public static TextureRegion[] ApplyFrames(int sc, int sr, int fc, int fr, TextureRegion[][] frames) {
		// SC = start column
		// SR = start row
		// FC = end column
		// FR = end row
		TextureRegion[] temp = new TextureRegion[(fr - sr) * (fc - sc)];
		int index = 0;
		for (int i = sr; i < fr; i++) {
            for (int j = sc; j < fc; j++) {
            	temp[index++] = frames[i][j];
            }
		}
		
		return temp;
	}
	
	public static TextureRegion[] Flip(boolean x, boolean y, TextureRegion[] temp) {
		for(int i = 0; i < temp.length; i++) {
			temp[i].flip(x, y);
		}
		
		return temp;
	}
	
	public static Sprite getSprite(String path, boolean linear, boolean x, boolean y) {
		Texture temp = getTexture(path, linear);
		
		Sprite tempS = new Sprite(temp);
		tempS.flip(x, y);
		return tempS;
	}
	
	public static Sprite getSprite(String path) {
		return getSprite(path, false, false, false);
	}
	
	public static Sprite getSpriteLinear(String path) {
		return getSprite(path, true, false, false);
	}
	
	public static Sprite getSprite(String path, boolean x, boolean y) {
		return getSprite(path, false, x, y);
	}
	
	public static Sprite getSpriteFlippedX(String path) {
		return getSprite(path, true, false);
	}
	
	public static Sprite getSpriteFlippedY(String path) {
		return getSprite(path, false, true);
	}
	
	public static BitmapFont getFont(String path, int size, Color color, boolean linear, boolean flip) {
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(path));
		
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		parameter.flip = flip;
		
		BitmapFont font = gen.generateFont(parameter);
		
		if(linear) {
			font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		font.setColor(color);
		
		return font;
	}
}
