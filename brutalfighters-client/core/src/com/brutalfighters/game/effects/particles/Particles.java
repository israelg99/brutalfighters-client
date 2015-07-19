package com.brutalfighters.game.effects.particles;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Array;
import com.brutalfighters.game.basic.Render;

public class Particles {
	
	private static Map<String, ParticleEffectPool> particles = new HashMap<String, ParticleEffectPool>();

	private final static String path = "particles"; //$NON-NLS-1$
	private static Array<PooledEffect> beforeEffects;
	private static Array<PooledEffect> afterEffects;
	
	public static void Load() {
		
		beforeEffects = new Array<PooledEffect>();
		afterEffects = new Array<PooledEffect>();
		
		particles.put("grassyWalk", loadParticle("grassyWalk.p")); //$NON-NLS-1$ //$NON-NLS-2$
		
		particles.put("trail1", loadParticle("trail1.p")); //$NON-NLS-1$ //$NON-NLS-2$
		particles.put("trail2", loadParticle("trail2.p")); //$NON-NLS-1$ //$NON-NLS-2$
		particles.put("trail3", loadParticle("trail3.p")); //$NON-NLS-1$ //$NON-NLS-2$
		particles.put("trail4", loadParticle("trail4.p")); //$NON-NLS-1$ //$NON-NLS-2$
		particles.put("trail5", loadParticle("trail5.p")); //$NON-NLS-1$ //$NON-NLS-2$
		
		particles.put("bleeding", loadParticle("bleeding.p")); //$NON-NLS-1$ //$NON-NLS-2$
		
		particles.put("blood_splat_left", loadParticle("blood_splat_left.p")); //$NON-NLS-1$ //$NON-NLS-2$
		particles.put("blood_splat_right", loadParticle("blood_splat_right.p")); //$NON-NLS-1$ //$NON-NLS-2$
		particles.put("blood_splat_big", loadParticle("blood_splat_big.p")); //$NON-NLS-1$ //$NON-NLS-2$
		
		particles.put("health_regen", loadParticle("health_regen.p")); //$NON-NLS-1$ //$NON-NLS-2$
		
		particles.put("right_blue_beat", loadParticle("right_blue_beat.p")); //$NON-NLS-1$ //$NON-NLS-2$
		particles.put("center_redblue_pump", loadParticle("center_redblue_pump.p")); //$NON-NLS-1$ //$NON-NLS-2$
		particles.put("firework_blue1", loadParticle("firework_blue1.p")); //$NON-NLS-1$ //$NON-NLS-2$
		particles.put("3_fireworks", loadParticle("3_fireworks.p")); //$NON-NLS-1$ //$NON-NLS-2$
		
		particles.put("tp_sparks", loadParticle("tp_sparks.p")); //$NON-NLS-1$ //$NON-NLS-2$
		particles.put("tp_dust", loadParticle("tp_dust.p")); //$NON-NLS-1$ //$NON-NLS-2$
		
		particles.put("jump_dash", loadParticle("jump_dash.p")); //$NON-NLS-1$ //$NON-NLS-2$

		particles.put("burn", loadParticle("burn.p")); //$NON-NLS-1$ //$NON-NLS-2$
		particles.put("red_bats", loadParticle("red_bats.p")); //$NON-NLS-1$ //$NON-NLS-2$

		particles.put("walking_dust", loadParticle("walking_dust.p")); //$NON-NLS-1$ //$NON-NLS-2$
		
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
			effect.draw(Render.batch, Gdx.graphics.getDeltaTime());
			if(effect.isComplete()) {
				beforeEffects.removeValue(effect, true);
				effect.free();
			}
		}
	}
	public static void renderAfter() {
		for(PooledEffect effect : afterEffects) {
			effect.draw(Render.batch, Gdx.graphics.getDeltaTime());
			if(effect.isComplete()) {
				afterEffects.removeValue(effect, true);
				effect.free();
			}
		}
	}
	
	public static void add(String name, float posx, float posy, boolean before) {
		PooledEffect effect = particles.get(name).obtain();
		effect.setPosition(posx, posy);
		if(before) {
			beforeEffects.add(effect);
		} else {
			afterEffects.add(effect);
		}
	}
	
	private static ParticleEffectPool loadParticle(String name) {
		ParticleEffect temp = new ParticleEffect();
		temp.load(Gdx.files.internal(path + "/" + name), Gdx.files.internal(path)); //$NON-NLS-1$
		temp.start();
		
		ParticleEffectPool tempPool = new ParticleEffectPool(temp, 0, 70); 
		
		return tempPool;
	}
	
	public static void dispose() {
		
	}
}
