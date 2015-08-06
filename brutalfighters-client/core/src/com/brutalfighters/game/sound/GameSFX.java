package com.brutalfighters.game.sound;

import com.badlogic.gdx.audio.Sound;

public enum GameSFX {
	
	FootStep1(SoundUtil.getSound("sfx/champ/footstep1.wav")), //$NON-NLS-1$ 
	FootStep2(SoundUtil.getSound("sfx/champ/footstep2.wav")), //$NON-NLS-1$ 
	
	GrassStep1(SoundUtil.getSound("sfx/champ/ftGrass.wav")), //$NON-NLS-1$ 
	GrassStep2(SoundUtil.getSound("sfx/champ/ftGrass2.wav")), //$NON-NLS-1$ 
	
	DirtStep1(SoundUtil.getSound("sfx/champ/ftDirt.wav")), //$NON-NLS-1$ 
	DirtStep2(SoundUtil.getSound("sfx/champ/ftDirt2.wav")), //$NON-NLS-1$ 

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
