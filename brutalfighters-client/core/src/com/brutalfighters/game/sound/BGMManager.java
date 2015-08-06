package com.brutalfighters.game.sound;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.audio.Music;
import com.brutalfighters.game.resources.Prefs;
import com.brutalfighters.game.screen.ScreenManager;
import com.brutalfighters.game.utility.GameMath;

public class BGMManager {

	private static List<BGM> game;
	private static List<BGM> menu;

	private static Music currentBGM;
	
	public static final float MAINMENU_VOLUME = 0.12f;
	public static final float GAME_VOLUME = 0.05f;
	
	public static BGM MAINMENU_THEME;
	
	private static boolean DISPOSE;

	public static void Load() {
		
		DISPOSE = false; // SO MUSIC WON'T SWITCH AFTER QUITTING THE GAME MAKING WEIRD NOISE!
		
		MAINMENU_THEME = BGM.HighEnergy;
		
		game = Arrays.asList(BGM.values());
		menu = Arrays.asList(BGM.values());

		currentBGM = MAINMENU_THEME.getBGM();
	}
	
	private static void setCurrent(Music bgm) {
		stopCurrent();
		currentBGM = bgm;
	}
	public static void setCurrent(BGM bgm) {
		setCurrent(bgm.getBGM());
	}
	public static void setMainMenu() {
		playBGM(MAINMENU_THEME.getBGM(), MAINMENU_VOLUME);
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
	private static void playBGM(BGM bgm, float vol) {
		playBGM(bgm.getBGM(), vol);
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
	private static void setRandomBGM(List<BGM> bgms) {
		setCurrent(bgms.get(GameMath.nextInt(0, bgms.size()-1)));
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
		for (BGM bgm : BGM.values()) {
			bgm.getBGM().stop();
		    bgm.getBGM().dispose();
		}
	}

}
