package com.brutalfighters.game.sound;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.brutalfighters.game.player.Player;

public class SFX {
	private static Map<String, Sound> sfx;
	
	public final static int ftLength = 2, ftGrassLength = 2, ftDirtLength = 2;
	
	public final static float DEFAULT_VOLUME = 0.3f;

	public static void Load() {
		
		sfx = new HashMap<String, Sound>();
		
		sfx.put("ft1", SoundUtil.getSound("sfx/champ/footstep1.wav")); //$NON-NLS-1$ //$NON-NLS-2$
		sfx.put("ft2", SoundUtil.getSound("sfx/champ/footstep2.wav")); //$NON-NLS-1$ //$NON-NLS-2$
		
		sfx.put("ftGrass1", SoundUtil.getSound("sfx/champ/ftGrass.wav")); //$NON-NLS-1$ //$NON-NLS-2$
		sfx.put("ftGrass2", SoundUtil.getSound("sfx/champ/ftGrass2.wav")); //$NON-NLS-1$ //$NON-NLS-2$
		
		sfx.put("ftDirt1", SoundUtil.getSound("sfx/champ/ftDirt.wav")); //$NON-NLS-1$ //$NON-NLS-2$
		sfx.put("ftDirt2", SoundUtil.getSound("sfx/champ/ftDirt2.wav")); //$NON-NLS-1$ //$NON-NLS-2$

		sfx.put("teleport", SoundUtil.getSound("sfx/teleport/woosh.wav")); //$NON-NLS-1$ //$NON-NLS-2$
		sfx.put("portal", SoundUtil.getSound("sfx/teleport/portal.wav")); //$NON-NLS-1$ //$NON-NLS-2$
		
		sfx.put("blockAction", SoundUtil.getSound("sfx/other/blockAction.wav")); //$NON-NLS-1$ //$NON-NLS-2$
		
		sfx.put("crowd", SoundUtil.getSound("sfx/finish/crowd.wav")); //$NON-NLS-1$ //$NON-NLS-2$
		sfx.put("crowd1", SoundUtil.getSound("sfx/finish/crowd1.wav")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	


	public static void play(String s) {
		SoundUtil.playSound(sfx.get(s), DEFAULT_VOLUME);
	}
	public static void play(String s, float volume) {
		SoundUtil.playSound(sfx.get(s), volume);
	}
	public static void playStereo(String s, float x) {
		SoundUtil.playStereo(sfx.get(s), x);
	}
	
	public static void moveStepsSFX(Player p, float delay) {
		p.toNextStep -= Gdx.graphics.getDeltaTime();
		if (p.toNextStep < 0) {
			p.playStepType();
			p.toNextStep = delay;
		}
	}

	public static void disposeAll() {
		for (Sound sound : sfx.values()) {
		    sound.dispose();
		}
	}
}
