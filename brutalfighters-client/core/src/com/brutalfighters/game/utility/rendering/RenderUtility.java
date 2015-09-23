package com.brutalfighters.game.utility.rendering;

import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.player.fighters.Fighter;

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
	public static float CenterFX_X(PlayerData p, float width, float fx_width) {
		return CenterFX_X(p.facingRight(), p.getPos().getX(), width, fx_width);
	}
	public static float CenterFX_X(Fighter p, float width, float fx_width) {
		return CenterFX_X(p.getPlayer().facingRight(), p.getPlayer().getPos().getX(), width, fx_width);
	}

	public static float CenterY(float posy, float height) {
		return posy-height/2;
	}
}
