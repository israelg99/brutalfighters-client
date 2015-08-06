package com.brutalfighters.game.effects.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;

public enum ParticlesCollection {

	GrassyWalk(loadParticle("grassyWalk.p")), //$NON-NLS-1$
	WalkingDust(loadParticle("walking_dust.p")), //$NON-NLS-1$
	
	Trail1(loadParticle("trail1.p")), //$NON-NLS-1$
	Trail2(loadParticle("trail2.p")), //$NON-NLS-1$
	Trail3(loadParticle("trail3.p")), //$NON-NLS-1$
	Trail4(loadParticle("trail4.p")), //$NON-NLS-1$
	Trail5(loadParticle("trail5.p")), //$NON-NLS-1$
	
	Bleeding(loadParticle("bleeding.p")), //$NON-NLS-1$
	
	BloodSplat_Left(loadParticle("blood_splat_left.p")), //$NON-NLS-1$
	BloodSplat_Right(loadParticle("blood_splat_right.p")), //$NON-NLS-1$
	BloodSplat_Big(loadParticle("blood_splat_big.p")), //$NON-NLS-1$
	
	HealthRegen(loadParticle("health_regen.p")), //$NON-NLS-1$
	
	TP_Spark(loadParticle("tp_sparks.p")), //$NON-NLS-1$
	TP_Dust(loadParticle("tp_dust.p")), //$NON-NLS-1$
	
	JumpDash(loadParticle("jump_dash.p")), //$NON-NLS-1$
	
	Burn(loadParticle("burn.p")), //$NON-NLS-1$
	RedBats(loadParticle("red_bats.p")), //$NON-NLS-1$
	
	RightBlueBeat(loadParticle("right_blue_beat.p")), //$NON-NLS-1$
	CenterBlueRedPump(loadParticle("center_redblue_pump.p")), //$NON-NLS-1$
	BlueFirework(loadParticle("firework_blue1.p")), //$NON-NLS-1$
	Fireworks3(loadParticle("3_fireworks.p")); //$NON-NLS-1$
	
	private ParticleEffectPool particleEffect;
	
	ParticlesCollection(ParticleEffectPool particleEffect) {
		this.particleEffect = particleEffect;
	}
	
	public ParticleEffectPool getEffect() {
		return particleEffect;
	}
	
	private final static String path = "particles"; //$NON-NLS-1$
	
	private static ParticleEffectPool loadParticle(String name) {
		ParticleEffect temp = new ParticleEffect();
		temp.load(Gdx.files.internal(path + "/" + name), Gdx.files.internal(path)); //$NON-NLS-1$
		temp.start();
		
		ParticleEffectPool tempPool = new ParticleEffectPool(temp, 0, 70); 
		
		return tempPool;
	}
	
	public static void init() {
		values();
	}
}