package com.brutalfighters.game.buffs;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.effects.particles.Particles;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.utility.GameMath;
import com.brutalfighters.game.utility.rendering.RenderUtility;
import com.brutalfighters.game.utility.rendering.TextureHandle;

public enum Buff {
	
	HALF_SLOW(), BIT_SLOW(), SLOW_HEALING(),
	
	RED_BATS() {
		
		@Override
		public void draw(PlayerData p, BuffData buff) {
			if(GameMath.nextBoolean(90)) {
				Particles.add("red_bats", p.posx, p.posy, false); //$NON-NLS-1$
			}
		}
		
	},
	
	ICE_STUN() {
		
		private Sprite ice = TextureHandle.getSprite("buffs/ice.png", true, false, false); //$NON-NLS-1$
		
		@Override
		public void draw(PlayerData p, BuffData buff) {
			Render.batch.draw(ice, RenderUtility.CenterX(p.posx, p.width+p.width/2), RenderUtility.CenterY(p.posy, p.height+p.height/2), p.width+p.width/2, p.height+p.height/2);
		}
		
	},
	
	BURN() {

		@Override
		public void draw(PlayerData p, BuffData buff) {
			if(GameMath.nextBoolean(90)) {
				Particles.add("burn", p.posx, p.posy, false); //$NON-NLS-1$
			}
		}
		
	};
	
	public void draw(PlayerData p, BuffData buff) {
		
	}
}
