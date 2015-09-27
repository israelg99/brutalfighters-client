package com.brutalfighters.game.sound;

import com.badlogic.gdx.audio.Sound;

public enum GameSFX {
	
	/* Footsteps */
	FootStep1(getSFX("footstep/footstep1.wav"), getStepsVolume()), //$NON-NLS-1$ 
	FootStep2(getSFX("footstep/footstep2.wav"), getStepsVolume()), //$NON-NLS-1$ 
	
	GrassStep1(getSFX("footstep/step_grass1.wav"), getStepsVolume()), //$NON-NLS-1$ 
	GrassStep2(getSFX("footstep/step_grass2.wav"), getStepsVolume()), //$NON-NLS-1$ 
	
	DirtStep1(getSFX("footstep/step_dirt1.wav"), getStepsVolume()), //$NON-NLS-1$ 
	DirtStep2(getSFX("footstep/step_dirt2.wav"), getStepsVolume()), //$NON-NLS-1$ 
	
	RockStep1(getSFX("footstep/step_rock1.wav"), getStepsVolume()), //$NON-NLS-1$ 
	RockStep2(getSFX("footstep/step_rock2.wav"), getStepsVolume()), //$NON-NLS-1$ 
	
	SnowStep1(getSFX("footstep/step_snow1.wav"), getStepsVolume()), //$NON-NLS-1$ 
	SnowStep2(getSFX("footstep/step_snow2.wav"), getStepsVolume()), //$NON-NLS-1$ 

	/* Basic */
	BlockAction(getSFX("basic/block_action.wav")), //$NON-NLS-1$ 
	Click(getSFX("basic/click.wav")), //$NON-NLS-1$ 
	Tick(getSFX("basic/tick.wav")), //$NON-NLS-1$ 
	Woosh(getSFX("basic/woosh.wav")), //$NON-NLS-1$ 
	
	/* Special */
	Bats(getSFX("special/bats.wav")), //$NON-NLS-1$ 
	BigFireball(getSFX("special/big_fireball.wav")), //$NON-NLS-1$ 
	BloodSkulls(getSFX("special/bloodskulls.wav")), //$NON-NLS-1$ 
	DarkTeleport(getSFX("special/dark_teleport.wav")), //$NON-NLS-1$ 
	Electricity(getSFX("special/electricity.wav")), //$NON-NLS-1$ 
	FastBlock(getSFX("special/fast_block.wav")), //$NON-NLS-1$ 
	Fireball(getSFX("special/fireball.wav")), //$NON-NLS-1$ 
	LaserCharge(getSFX("special/laser_charge.wav")), //$NON-NLS-1$ 
	Lightning(getSFX("special/lightning.wav")), //$NON-NLS-1$ 
	MagicSwarm(getSFX("special/magic_swarm.wav")), //$NON-NLS-1$ 
	MagicThrust(getSFX("special/magic_thrust.wav")), //$NON-NLS-1$ 
	Phoenix(getSFX("special/phoenix.wav")), //$NON-NLS-1$ 
	Plant(getSFX("special/plant.wav")), //$NON-NLS-1$ 
	Portal(getSFX("special/portal.wav")), //$NON-NLS-1$ 
	Project1(getSFX("special/project1.wav")), //$NON-NLS-1$ 
	Project2(getSFX("special/project2.wav")), //$NON-NLS-1$ 
	Shotgun(getSFX("special/shotgun.wav")), //$NON-NLS-1$ 
	StrongBlock(getSFX("special/strong_block.wav")), //$NON-NLS-1$ 
	StrongCharge(getSFX("special/strong_charge.wav")), //$NON-NLS-1$ 
	Throw(getSFX("special/throw.wav")), //$NON-NLS-1$ 
	Thrust(getSFX("special/thrust.wav")), //$NON-NLS-1$ 
	
	/* Explosion */
	Explode1(getSFX("explosion/explode1.wav")), //$NON-NLS-1$
	Explode2(getSFX("explosion/explode2.wav")), //$NON-NLS-1$
	Explode3(getSFX("explosion/explode3.wav")), //$NON-NLS-1$
	
	/* Growl */
	FastGrowl(getSFX("growl/fast_growl.wav")), //$NON-NLS-1$
	LongGrowl1(getSFX("growl/long_growl1.wav")), //$NON-NLS-1$
	LongGrowl2(getSFX("growl/long_growl2.wav")), //$NON-NLS-1$
	
	/* Punch */
	Punch1(getSFX("punch/punch1.wav")), //$NON-NLS-1$
	Punch2(getSFX("punch/punch2.wav")), //$NON-NLS-1$
	
	/* Action */
	Jump(getSFX("action/jump.wav")), //$NON-NLS-1$ 
	
	/* Crowd */
	Crowd1(getSFX("crowd/crowd.wav"), getCrowdVolume()), //$NON-NLS-1$ 
	Crowd2(getSFX("crowd/crowd1.wav"), getCrowdVolume()); //$NON-NLS-1$ 
	
	private final static String PATH = "sfx/"; //$NON-NLS-1$
	
	private final static float DEFAULT_VOLUME = 0.5f;
	private final static float DEFAULT_STEPS_VOLUME = 0.3f;
	private final static float DEFAULT_CROWD_VOLUME = 0.7f;
	
	private final static int ftLength = 2, ftGrassLength = 2, ftDirtLength = 2,
							 ftRockLength = 2, ftIceLength = 2;
	
	private Sound SFX;
	private float volume;
	
	public static String getPath() {
		return PATH;
	}

	public static float getDefaultVolume() {
		return DEFAULT_VOLUME;
	}
	public static float getStepsVolume() {
		return DEFAULT_STEPS_VOLUME;
	}
	public static float getCrowdVolume() {
		return DEFAULT_CROWD_VOLUME;
	}
	
	public static int getStepLength() {
		return ftLength;
	}
	public static int getGrassStepLength() {
		return ftGrassLength;
	}
	public static int getDirtStepLength() {
		return ftDirtLength;
	}
	public static int getRockStepLength() {
		return ftRockLength;
	}
	public static int getIceStepLength() {
		return ftIceLength;
	}
	
	public static Sound getSFX(String path) {
		return SoundUtil.getSound(getPath() + path);
	}
	
	private GameSFX(Sound SFX, float volume) {
		this.SFX = SFX;
		this.volume = volume;
	}
	private GameSFX(Sound SFX) {
		this(SFX, getDefaultVolume());
	}
	
	public Sound get() {
		return SFX;
	}
	public float getVolume() {
		return volume;
	}
	
	public void play(float x) {
		SoundUtil.playSound(get(), x);
	}
	public void play() {
		play(getVolume());
	}
	
	public void playStereo(float x, float vol) {
		SoundUtil.playStereo(get(), x, vol);
	}
	public void playStereo(float x) {
		playStereo(x, getVolume());
	}
	
	public static void disposeAll() {
		for (GameSFX sfx : values()) {
			sfx.get().dispose();
		}
	}
	
	public static void init() {
		values();
	}
}
