package com.brutalfighters.game.sound.menu;

import com.badlogic.gdx.audio.Sound;
import com.brutalfighters.game.sound.SoundUtil;

public class MainMenuSFX {
	public static Sound click;

	public static void Load() {
		click = SoundUtil.getSound("menu/sfx/click.wav"); //$NON-NLS-1$
	}
	
	public static void playClick() {
		SoundUtil.playSound(click);
	}

	public static void disposeAll() {
		click.dispose();
	}

}
