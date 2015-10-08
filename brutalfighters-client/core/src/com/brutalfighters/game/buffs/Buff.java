package com.brutalfighters.game.buffs;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.effects.particles.ParticleEffects;
import com.brutalfighters.game.effects.particles.ParticlesCollection;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.utility.GameMath;
import com.brutalfighters.game.utility.rendering.RenderUtility;
import com.brutalfighters.game.utility.rendering.TextureHandle;

public enum Buff {
	
	Slow(), Heal(), AirJump(),
	
	RedBats() {
		
		@Override
		public void draw(PlayerData p, BuffData buff) {
			if(GameMath.nextBoolean(90)) {
				ParticleEffects.add(ParticlesCollection.RedBats, p.getPos().getX(), p.getPos().getY(), false);
			}
		}
		
	},
	
	IceStun() {
		
		private Sprite ice = TextureHandle.getSprite("buffs/ice.png", true, false, false); //$NON-NLS-1$
		
		@Override
		public void draw(PlayerData p, BuffData buff) {
			Render.getSpriteBatch().draw(ice, RenderUtility.CenterX(p.getPos().getX(), p.getSize().getX()+p.getSize().getX()/2), RenderUtility.CenterY(p.getPos().getY(), p.getSize().getY()+p.getSize().getY()/2), p.getSize().getX()+p.getSize().getX()/2, p.getSize().getY()+p.getSize().getY()/2);
		}
		
	},
	
	Burn() {

		@Override
		public void draw(PlayerData p, BuffData buff) {
			if(GameMath.nextBoolean(90)) {
				ParticleEffects.add(ParticlesCollection.Burn, p.getPos().getX(), p.getPos().getY(), false);
			}
		}
		
	};
	
	public void draw(PlayerData p, BuffData buff) {
		
	}
	
	public static void init() {
		values();
	}
}
