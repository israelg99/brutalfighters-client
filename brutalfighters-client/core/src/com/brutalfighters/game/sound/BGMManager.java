package com.brutalfighters.game.sound;

import java.util.Arrays;
import java.util.List;

import com.brutalfighters.game.resources.Prefs;
import com.brutalfighters.game.screen.ScreenManager;
import com.brutalfighters.game.utility.GameMath;

public class BGMManager {

	private static final float GAME_DIVISOR = 3;
	private static final float MENU_DIVISOR = 1;
	private static float DIVISOR;
	
	private static List<BGM> game;
	private static List<BGM> menu;

	private static BGM currentBGM;
	
	private static BGM MAINMENU_THEME;
	
	private static boolean DISPOSE;

	public static void Load() {
		
		DISPOSE = false; // SO MUSIC WON'T SWITCH AFTER QUITTING THE GAME MAKING WEIRD NOISE! No need for setters as it's one time thing!
		
		MAINMENU_THEME = BGM.HighEnergy; // No need for setters it's like final
		
		game = Arrays.asList(BGM.values()); // No need for setters it's like final
		menu = Arrays.asList(BGM.values()); // No need for setters it's like final

		currentBGM = MAINMENU_THEME; // Set current doesn't apply here and we MUST set it to something, maybe add a BGM parameter to the constructor?
		DIVISOR = MENU_DIVISOR;
	}
	
	private static float getDivisor() {
		return DIVISOR;
	}
	private static void setDivisor(float divisor) {
		DIVISOR = divisor;
	}
	
	private static float getGameDivisor() {
		return GAME_DIVISOR;
	}
	private static void setGameDivisor() {
		setDivisor(getGameDivisor());
	}
	
	private static float getMenuDivisor() {
		return MENU_DIVISOR;
	}
	private static void setMenuDivisor() {
		setDivisor(getMenuDivisor());
	}
	
	public static List<BGM> getGameList() {
		return game;
	}
	private static void setGame(List<BGM> game) {
		BGMManager.game = game;
	}

	public static List<BGM> getMenuList() {
		return menu;
	}
	private static void setMenu(List<BGM> menu) {
		BGMManager.menu = menu;
	}

	public static BGM getMainMenuTheme() {
		return MAINMENU_THEME;
	}

	public static boolean isDispose() {
		return DISPOSE;
	}
	public static void dispose() {
		DISPOSE = true;
	}

	private static void setCurrent(BGM bgm) {
		stopCurrent();
		currentBGM = bgm;
	}
	public static void setMainMenu() {
		setMenuDivisor();
		playBGM(MAINMENU_THEME);
	}
	public static void setGame() {
		setGameDivisor();
		updateVolume();
	}
	
	private static BGM getCurrent() {
		return currentBGM;
	}

	private static void currentVolume() {
		getCurrent().setVolume(getCurrent().getVolume() / getDivisor());
	}
	
	private static void playBGM(BGM bgm) {
		setCurrent(bgm);
		playCurrent();
	}
	private static void playCurrent() {
		stopCurrent();
		currentVolume();
		getCurrent().play();
	}
	
	public static void stopCurrent() {
		getCurrent().getBGM().stop();
	}
	
	public static void initBGM() {
		playRandomBGM();
	}

	private static void playRandomBGM() {
		if(ScreenManager.getScreen().equals(ScreenManager.Screen.MENU)) {
			setRandomBGM(getMenuList());
		} else {
			setRandomBGM(getGameList());
		}
		
		playCurrent();
	}
	private static void setRandomBGM(List<BGM> bgms) {
		setCurrent(bgms.get(GameMath.nextInt(0, bgms.size()-1)));
	}
	
	public static void update() {
		if(Prefs.isVolume() && !isDispose()) { // We are calling it only here, for a live update, no need to call in other places.
			if(!currentBGM.getBGM().isPlaying()) {
				playRandomBGM();
			}
		} else {
			stopCurrent();
		}
	}

	public static void updateVolume() {
		currentVolume();
	}
	
	public static void disposeAll() {
		DISPOSE = true;
		stopCurrent();
		BGM.disposeAll();
	}

}
