package com.brutalfighters.game.utility.rendering;

import com.brutalfighters.game.player.Player;
import com.brutalfighters.game.player.PlayerData;

public class RenderUtility {
	public static float CenterX(float posx, int width) {
		return posx-width/2;
	}
	
	public static float CenterFX_X(boolean isRight, float posx, int width, int fx_width) {
		if(isRight) {
			return CenterX(posx, width);
		}
		return CenterX(posx, width) - fx_width;
	}
	public static float CenterFX_X(PlayerData p, int width, int fx_width) {
		return CenterFX_X(p.flip.equals("right"), p.posx, width, fx_width); //$NON-NLS-1$
	}
	public static float CenterFX_X(Player p, int width, int fx_width) {
		return CenterFX_X(p.isRight(), p.getX(), width, fx_width);
	}

	public static float CenterY(float posy, int height) {
		return posy-height/2;
	}
}
