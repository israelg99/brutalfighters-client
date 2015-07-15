package com.brutalfighters.game.utility.rendering;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationHandler {
	
	public static Animation getAnimation(String flip, TextureRegion[] frames, float speed, PlayMode playMode) {
		Animation temp = returnAfterCheck(flip, frames, speed);
		temp.setPlayMode(playMode);
		return temp;
	}
	
	public static TextureRegion returnAfterCheck(String flip, TextureRegion tr) {
		if(flip.equals("right")) { //$NON-NLS-1$
			if(tr.isFlipX()) {
				tr.flip(true, false);
			}
			return tr;
		} else if(flip.equals("left")) { //$NON-NLS-1$
			if(!tr.isFlipX()) {
				tr.flip(true, false);
			}
			return tr;
		} else {
			System.err.println(" ---- You are trying to get TextureRegion with invalid argument.. maybe you spelled flip wrong? hahaha :)"); //$NON-NLS-1$
			return null;
		}
	}
	
	public static Animation returnAfterCheck(String flip, TextureRegion[] tr, float delay) {
		if(flip.equals("right")) { //$NON-NLS-1$
			if(tr[0].isFlipX()) {
				tr = TextureHandle.Flip(true, false, tr);
			}
			return new Animation(delay, tr);
		} else if(flip.equals("left")) { //$NON-NLS-1$
			if(!tr[0].isFlipX()) {
				tr = TextureHandle.Flip(true, false, tr);
			}
			return new Animation(delay, tr);
		} else {
			System.err.println(" ---- You are trying to get Animation with invalid argument.. maybe you spelled flip wrong? hahaha :)"); //$NON-NLS-1$
			return null;
		}
	}
	
}
