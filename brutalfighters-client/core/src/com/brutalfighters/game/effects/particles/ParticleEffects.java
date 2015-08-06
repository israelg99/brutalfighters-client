package com.brutalfighters.game.effects.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Array;
import com.brutalfighters.game.basic.Render;

public class ParticleEffects {
	private static Array<PooledEffect> beforeEffects;
	private static Array<PooledEffect> afterEffects;
	
	public static void load() {
		beforeEffects = new Array<PooledEffect>();
		afterEffects = new Array<PooledEffect>();
	}
	
	public static void render(boolean before) {
		if(before) {
			renderBefore();
		} else {
			renderAfter();
			return;
		}
	}
	public static void renderBefore() {
		for(PooledEffect effect : beforeEffects) {
			effect.draw(Render.getSpriteBatch(), Gdx.graphics.getDeltaTime());
			if(effect.isComplete()) {
				beforeEffects.removeValue(effect, true);
				effect.free();
			}
		}
	}
	public static void renderAfter() {
		for(PooledEffect effect : afterEffects) {
			effect.draw(Render.getSpriteBatch(), Gdx.graphics.getDeltaTime());
			if(effect.isComplete()) {
				afterEffects.removeValue(effect, true);
				effect.free();
			}
		}
	}
	
	public static void add(PooledEffect effect, float posx, float posy, boolean before) {
		effect.setPosition(posx, posy);
		if(before) {
			beforeEffects.add(effect);
		} else {
			afterEffects.add(effect);
		}
	}
	public static void add(String name, float posx, float posy, boolean before) {
		add(ParticlesCollection.valueOf(name).getEffect().obtain(), posx, posy, before);
	}
	public static void add(ParticlesCollection particle, float posx, float posy, boolean before) {
		add(particle.getEffect().obtain(), posx, posy, before);
	}
}
