package com.brutalfighters.game.sound;

import com.badlogic.gdx.Gdx;
import com.brutalfighters.game.player.Player;
import com.brutalfighters.game.utility.GameMath;

public class GameSFXManager {
	
	private final static int ftLength = 2, ftGrassLength = 2, ftDirtLength = 2;
	
	private final static float DEFAULT_VOLUME = 0.3f;

	public static int getStepLength() {
		return ftLength;
	}
	public static int getGrassStepLength() {
		return ftGrassLength;
	}
	public static int getDirtStepLength() {
		return ftDirtLength;
	}
	public static float getDefaultVolume() {
		return DEFAULT_VOLUME;
	}
	
	public static void play(GameSFX sfx) {
		SoundUtil.playSound(sfx.getSFX(), DEFAULT_VOLUME);
	}
	public static void play(GameSFX sfx, float volume) {
		SoundUtil.playSound(sfx.getSFX(), volume);
	}
	public static void playStereo(GameSFX sfx, float x) {
		SoundUtil.playStereo(sfx.getSFX(), x);
	}
	
	public static void moveStepsSFX(Player p, float delay) {
		p.toNextStep -= Gdx.graphics.getDeltaTime();
		if (p.toNextStep < 0) {
			p.playStepType();
			p.toNextStep = delay;
		}
	}
	
	public static void playRandomCrowd() {
		if(GameMath.nextInt(1, 2) > 1) {
			play(GameSFX.Crowd1, 0.7f);
		} else {
			play(GameSFX.Crowd2, 0.7f);
		}
	}

	public static void disposeAll() {
		for (GameSFX sfx : GameSFX.values()) {
			sfx.getSFX().dispose();
		}
	}
	
	
}
