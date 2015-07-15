package com.brutalfighters.game.sound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.audio.Music;
import com.brutalfighters.game.resources.Prefs;
import com.brutalfighters.game.screen.ScreenManager;
import com.brutalfighters.game.utility.GameMath;

public class BGM {

	private static Map<String, Music> BGM;
	private static List<String> game;
	private static List<String> menu;

	private static Music currentBGM;
	
	public static final float MAINMENU_VOLUME = 0.12f;
	public static final float GAME_VOLUME = 0.05f;
	
	public static final String MAINMENU_THEME = "highenergy"; //$NON-NLS-1$
	
	private static boolean DISPOSE;

	public static void Load() {
		
		DISPOSE = false; // SO MUSIC WON'T SWITCH AFTER QUITTING THE GAME MAKING WEIRD NOISE!
		
		BGM = new HashMap<String, Music>();
		
		game = new ArrayList<String>();
		menu = new ArrayList<String>();
		
		BGM.put("blank", getBGM("bgm/blank.mp3")); //$NON-NLS-1$ //$NON-NLS-2$
		BGM.put("hotheat", getBGM("bgm/hotheat.mp3")); //$NON-NLS-1$ //$NON-NLS-2$
		BGM.put("lastdawn", getBGM("bgm/lastdawn.mp3")); //$NON-NLS-1$ //$NON-NLS-2$
		BGM.put("magicmarker", getBGM("bgm/magicmarker.mp3")); //$NON-NLS-1$ //$NON-NLS-2$
		BGM.put("payday", getBGM("bgm/payday.mp3")); //$NON-NLS-1$ //$NON-NLS-2$
		BGM.put("waterlily", getBGM("bgm/waterlily.mp3")); //$NON-NLS-1$ //$NON-NLS-2$
		BGM.put("bgmfun1", getBGM("bgm/bgmfun1.mp3")); //$NON-NLS-1$ //$NON-NLS-2$
		BGM.put("chillbgm", getBGM("bgm/chillbgm.mp3")); //$NON-NLS-1$ //$NON-NLS-2$
		BGM.put("copycat", getBGM("bgm/copycat.mp3")); //$NON-NLS-1$ //$NON-NLS-2$
		BGM.put("funbgm", getBGM("bgm/funbgm.mp3")); //$NON-NLS-1$ //$NON-NLS-2$
		BGM.put("funbgm2", getBGM("bgm/funbgm2.mp3")); //$NON-NLS-1$ //$NON-NLS-2$
		BGM.put("highenergy", getBGM("bgm/highenergy.mp3")); //$NON-NLS-1$ //$NON-NLS-2$
		BGM.put("melanbeats", getBGM("bgm/melanbeats.mp3")); //$NON-NLS-1$ //$NON-NLS-2$
		BGM.put("seasonsforever", getBGM("bgm/seasonsforever.mp3")); //$NON-NLS-1$ //$NON-NLS-2$
		
		menu.addAll(Arrays.asList("blank", "hotheat", "lastdawn", "magicmarker", "payday", "waterlily", "bgmfun1", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
								  "chillbgm", "copycat", "funbgm", "funbgm2", "highenergy", "melanbeats", "seasonsforever")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
		
		game.addAll(Arrays.asList("blank", "hotheat", "lastdawn", "magicmarker", "payday", "waterlily", "bgmfun1", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
								  "chillbgm", "copycat", "funbgm", "funbgm2", "highenergy", "melanbeats", "seasonsforever")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
		
		currentBGM = BGM.get(MAINMENU_THEME);
	}
	
	private static Music getBGM(String path) {
		return SoundUtil.getMusic(path); // WE use SoundUtil static VOLUME
	}
	
	private static void setCurrent(Music bgm) {
		stopCurrent();
		currentBGM = bgm;
	}
	public static void setCurrent(String bgm) {
		setCurrent(BGM.get(bgm));
	}
	public static void setMainMenu() {
		playBGM(MAINMENU_THEME, MAINMENU_VOLUME);
	}
	public static void setGame() {
		currentVolume(GAME_VOLUME);
	}
	
	private static Music getCurrent() {
		return currentBGM;
	}
	
	private static void currentVolume(float vol) {
		getCurrent().setVolume(SoundUtil.getVolume(vol));
	}
	
	private static void playCurrent(float vol) {
		stopCurrent();
		currentVolume(vol);
		getCurrent().play();
	}
	private static void playBGM(Music bgm, float vol) {
		setCurrent(bgm);
		playCurrent(vol);
	}
	private static void playBGM(String bgm, float vol) {
		playBGM(BGM.get(bgm), vol);
	}
	
	public static void stopCurrent() {
		getCurrent().stop();
	}
	
	public static void initBGM() {
		playRandomBGM();
	}

	private static void playRandomBGM() {
		if(ScreenManager.getScreen().equals(ScreenManager.Screen.MENU)) {
			setRandomBGM(menu);
			playCurrent(MAINMENU_VOLUME);
		} else {
			setRandomBGM(game);
			playCurrent(GAME_VOLUME);
		}
	}
	private static void setRandomBGM(List<String> bgms) {
		setCurrent(BGM.get(bgms.get(GameMath.nextInt(0, bgms.size()-1))));
	}
	
	public static void update() {
		if(Prefs.isVolume() && !DISPOSE) { // We are calling it only here, for a live update, no need to call in other places.
			if(!currentBGM.isPlaying()) {
				playRandomBGM();
			}
		} else {
			stopCurrent();
		}
	}

	public static void updateVolume() {
		if(ScreenManager.getScreen().equals(ScreenManager.Screen.MENU)) {
			currentVolume(MAINMENU_VOLUME);
		} else {
			currentVolume(GAME_VOLUME);
		}
	}
	
	public static void disposeAll() {
		DISPOSE = true;
		stopCurrent();
		for (Music bgm : BGM.values()) {
			bgm.stop();
		    bgm.dispose();
		}
	}

}
