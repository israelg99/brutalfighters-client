package com.brutalfighters.game.sound;

import com.badlogic.gdx.audio.Sound;

public enum GameSFX {
	
	/* Footsteps */
	FootStep1(SoundUtil.getSound("footstep/footstep1.wav")), //$NON-NLS-1$ 
	FootStep2(SoundUtil.getSound("footstep/footstep2.wav")), //$NON-NLS-1$ 
	
	GrassStep1(SoundUtil.getSound("footstep/step_grass1.wav")), //$NON-NLS-1$ 
	GrassStep2(SoundUtil.getSound("footstep/step_grass2.wav")), //$NON-NLS-1$ 
	
	DirtStep1(SoundUtil.getSound("footstep/step_dirt1.wav")), //$NON-NLS-1$ 
	DirtStep2(SoundUtil.getSound("footstep/step_dirt2.wav")), //$NON-NLS-1$ 
	
	RockStep1(SoundUtil.getSound("footstep/step_rock1.wav")), //$NON-NLS-1$ 
	RockStep2(SoundUtil.getSound("footstep/step_rock2.wav")), //$NON-NLS-1$ 
	
	SnowStep1(SoundUtil.getSound("footstep/step_snow1.wav")), //$NON-NLS-1$ 
	SnowStep2(SoundUtil.getSound("footstep/step_snow2.wav")), //$NON-NLS-1$ 

	/* Basic */
	BlockAction(SoundUtil.getSound("basic/block_action.wav")), //$NON-NLS-1$ 
	Click(SoundUtil.getSound("basic/click.wav")), //$NON-NLS-1$ 
	Tick(SoundUtil.getSound("basic/tick.wav")), //$NON-NLS-1$ 
	Woosh(SoundUtil.getSound("basic/woosh.wav")), //$NON-NLS-1$ 
	
	/* Special */
	Bats(SoundUtil.getSound("special/bats.wav")), //$NON-NLS-1$ 
	BigFireball(SoundUtil.getSound("special/big_fireball.wav")), //$NON-NLS-1$ 
	BloodSkulls(SoundUtil.getSound("special/bloodskulls.wav")), //$NON-NLS-1$ 
	DarkTeleport(SoundUtil.getSound("special/dark_teleport.wav")), //$NON-NLS-1$ 
	Electricity(SoundUtil.getSound("special/electricity.wav")), //$NON-NLS-1$ 
	FastBlock(SoundUtil.getSound("special/fast_block.wav")), //$NON-NLS-1$ 
	Fireball(SoundUtil.getSound("special/fireball.wav")), //$NON-NLS-1$ 
	LaserCharge(SoundUtil.getSound("special/laser_charge.wav")), //$NON-NLS-1$ 
	Lightning(SoundUtil.getSound("special/lightning.wav")), //$NON-NLS-1$ 
	MagicSwarm(SoundUtil.getSound("special/magic_swarm.wav")), //$NON-NLS-1$ 
	MagicThrust(SoundUtil.getSound("special/magic_thrust.wav")), //$NON-NLS-1$ 
	Phoenix(SoundUtil.getSound("special/phoenix.wav")), //$NON-NLS-1$ 
	Plant(SoundUtil.getSound("special/plant.wav")), //$NON-NLS-1$ 
	Portal(SoundUtil.getSound("special/portal.wav")), //$NON-NLS-1$ 
	Project1(SoundUtil.getSound("special/project1.wav")), //$NON-NLS-1$ 
	Project2(SoundUtil.getSound("special/project2.wav")), //$NON-NLS-1$ 
	Shotgun(SoundUtil.getSound("special/shotgun.wav")), //$NON-NLS-1$ 
	StrongBlock(SoundUtil.getSound("special/strong_block.wav")), //$NON-NLS-1$ 
	StrongCharge(SoundUtil.getSound("special/strong_charge.wav")), //$NON-NLS-1$ 
	Throw(SoundUtil.getSound("special/throw.wav")), //$NON-NLS-1$ 
	Thrust(SoundUtil.getSound("special/thrust.wav")), //$NON-NLS-1$ 
	
	/* Explosion */
	Explode1(SoundUtil.getSound("explosion/explode1.wav")), //$NON-NLS-1$
	Explode2(SoundUtil.getSound("explosion/explode2.wav")), //$NON-NLS-1$
	Explode3(SoundUtil.getSound("explosion/explode3.wav")), //$NON-NLS-1$
	
	/* Growl */
	FastGrowl(SoundUtil.getSound("growl/fast_growl.wav")), //$NON-NLS-1$
	LongGrowl1(SoundUtil.getSound("growl/long_growl1.wav")), //$NON-NLS-1$
	LongGrowl2(SoundUtil.getSound("growl/long_growl2.wav")), //$NON-NLS-1$
	
	/* Punch */
	Punch1(SoundUtil.getSound("punch/punch1.wav")), //$NON-NLS-1$
	Punch2(SoundUtil.getSound("punch/punch2.wav")), //$NON-NLS-1$
	
	/* Action */
	Jump(SoundUtil.getSound("action/jump.wav")), //$NON-NLS-1$ 
	
	/* Crowd */
	Crowd1(SoundUtil.getSound("crowd/crowd.wav")), //$NON-NLS-1$ 
	Crowd2(SoundUtil.getSound("crowd/crowd1.wav")); //$NON-NLS-1$ 
	
	private Sound SFX;
	
	GameSFX(Sound SFX) {
		this.SFX = SFX;
	}
	
	public Sound getSFX() {
		return SFX;
	}
	public void playSFX() {
		SoundUtil.playSound(getSFX());
	}
	public void playSFX(float x) {
		SoundUtil.playStereo(getSFX(), x);
	}
	
	public static void init() {
		values();
	}
}
