package com.brutalfighters.game.sound;

import com.badlogic.gdx.audio.Sound;

public enum GameSFX {
	
	FootStep1(SoundUtil.getSound("sfx/champ/footstep1.wav")), //$NON-NLS-1$ 
	FootStep2(SoundUtil.getSound("sfx/champ/footstep2.wav")), //$NON-NLS-1$ 
	
	GrassStep1(SoundUtil.getSound("sfx/champ/step_grass1.wav")), //$NON-NLS-1$ 
	GrassStep2(SoundUtil.getSound("sfx/champ/step_grass2.wav")), //$NON-NLS-1$ 
	
	DirtStep1(SoundUtil.getSound("sfx/champ/step_dirt1.wav")), //$NON-NLS-1$ 
	DirtStep2(SoundUtil.getSound("sfx/champ/step_dirt2.wav")), //$NON-NLS-1$ 
	
	RockStep1(SoundUtil.getSound("sfx/champ/step_rock1.wav")), //$NON-NLS-1$ 
	RockStep2(SoundUtil.getSound("sfx/champ/step_rock2.wav")), //$NON-NLS-1$ 
	
	SnowStep1(SoundUtil.getSound("sfx/champ/step_snow1.wav")), //$NON-NLS-1$ 
	SnowStep2(SoundUtil.getSound("sfx/champ/step_snow2.wav")), //$NON-NLS-1$ 

	Teleport(SoundUtil.getSound("sfx/teleport/woosh.wav")), //$NON-NLS-1$ 
	Portal(SoundUtil.getSound("sfx/teleport/portal.wav")), //$NON-NLS-1$ 
	
	BlockAction(SoundUtil.getSound("sfx/other/blockAction.wav")), //$NON-NLS-1$ 
	
	Crowd1(SoundUtil.getSound("sfx/finish/crowd.wav")), //$NON-NLS-1$ 
	Crowd2(SoundUtil.getSound("sfx/finish/crowd1.wav")); //$NON-NLS-1$ 
	
	private Sound SFX;
	
	GameSFX(Sound SFX) {
		this.SFX = SFX;
	}
	
	public Sound getSFX() {
		return SFX;
	}
	
	public static void init() {
		values();
	}
}
