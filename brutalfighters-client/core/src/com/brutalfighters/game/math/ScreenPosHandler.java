package com.brutalfighters.game.math;

import com.brutalfighters.game.basic.Render;

public class ScreenPosHandler {
	
	public static float getCenterScreenX(float w) {
		return Render.getResX() / 2 - w / 2;
	}
	public static float getCenterScreenY(float h) {
		return Render.getResY() / 2 + h / 2;
	}
	
	public static int getPosCenterX(int sp, int s, int is) {
		return  sp + (s/ 2 - is / 2);
	}
	public static int getPosCenterY(int sp, int s, int is) {
		return  sp - (s/ 2 - is / 2);
	}
	
	public static int getViableSize(int s, int is) {
		return (s / is * is);
	}
}
