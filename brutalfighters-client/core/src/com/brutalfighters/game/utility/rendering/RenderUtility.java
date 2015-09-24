package com.brutalfighters.game.utility.rendering;


public class RenderUtility {
	public static float CenterX(float posx, float width) {
		return posx-width/2;
	}
	
	public static float CenterFX_X(boolean isRight, float posx, float width, float fx_width) {
		if(isRight) {
			return CenterX(posx, width);
		}
		return CenterX(posx, width) - fx_width;
	}

	public static float CenterY(float posy, float height) {
		return posy-height/2;
	}
}
