package com.brutalfighters.game.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.brutalfighters.game.sound.BGMManager;

public class Prefs {
	
	private static Preferences prefs = Gdx.app.getPreferences("preferences"), //$NON-NLS-1$ 
							settings = Gdx.app.getPreferences("settings"); //$NON-NLS-1$
	
	private static Preferences getPrefs() {
		return prefs;
	}
	private static Preferences getSettings() {
		return settings;
	}

	public static void Load() {
		if(!getSettings().contains("volume")) { //$NON-NLS-1$
			setVolume(2);
			setPPEffects(true);
			setMotionBlur(1);
			setTextureRes(1);
			flush();
		}
	}
	
	public static void flush() {
		getPrefs().flush();
		getSettings().flush();
	}
	
	public static void setVolume(float vol) {
		getSettings().putFloat("volume", vol); //$NON-NLS-1$
		BGMManager.updateVolume();
	}
	public static float getVolume() {
		return getSettings().getFloat("volume"); //$NON-NLS-1$
	}
	public static boolean isVolume() {
		return getSettings().getFloat("volume") > 0; //$NON-NLS-1$
	}
	
	public static void setTextureRes(int tr) {
		getSettings().putInteger("texture_res", tr); //$NON-NLS-1$
	}
	public static int getTextureRes() {
		return getSettings().getInteger("texture_res"); //$NON-NLS-1$
	}
	
	public static void setPPEffects(boolean ppe) {
		getSettings().putBoolean("PP_effects", ppe); //$NON-NLS-1$
	}
	public static boolean getPPEffects() {
		return getSettings().getBoolean("PP_effects"); //$NON-NLS-1$
	}
	public static void setMotionBlur(float lvl) {
		getSettings().putFloat("Motion_Blur", lvl); //$NON-NLS-1$
	}
	public static float getMotionBlur() {
		return getSettings().getFloat("Motion_Blur"); //$NON-NLS-1$
	}
}
