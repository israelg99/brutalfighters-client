package com.brutalfighters.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.utility.rendering.TextureHandle;

public class Background {
	
	private final static int DEFAULT_divideSpeed = 100000; // 100,000
	
	private Sprite bg;
	private float scrollSpeed;
	private float imgNum;
	private final int divideSpeed;
	
	public Background(String name, float im, int divideSpeed) {
		bg = getProPallax(name);
		
		scrollSpeed = 0;
		imgNum = im;
		this.divideSpeed = divideSpeed;
		
		setU();
	}
	
	public void render() {
		Render.getSpriteBatch().draw(bg, 0, 0, Render.getResX(), Render.getResY());
	}
	
	public static Sprite getProPallax(String name) {
		Texture temp = TextureHandle.getTexture("maps/" + name + "/bg12.png", true); //$NON-NLS-1$ //$NON-NLS-2$
		temp.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		Sprite sTemp = new Sprite(temp);
		sTemp.setSize(Render.getResX(), Render.getResY());
		
		return sTemp;
	}
	
	private void setU() {
		bg.setU(scrollSpeed);
		bg.setU2(scrollSpeed + imgNum);
	}
	
	public void applyMove(float x) {
		scrollSpeed += x / divideSpeed;
		
		setU();
	}
	public void applyPos(float x) {
		scrollSpeed = x / divideSpeed;
		
		setU();
	}

	public static int getDefaultDivide() {
		return DEFAULT_divideSpeed; 
	}
}
