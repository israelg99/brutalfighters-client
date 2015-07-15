package com.brutalfighters.game.screen;

public class ScreenManager {
	public enum Screen {
		MENU, GAME
	}
	
	private static Screen screen = Screen.MENU;
	
	public static void setScreen(Screen name) {
		screen = name;
	}
	public static Screen getScreen() {
		return screen;
	}
}
