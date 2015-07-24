package com.brutalfighters.game.desktop;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Window {
	
	private static LwjglApplicationConfiguration cfg;
	
	public static void Initialize(String name, boolean fullscreen) {
		cfg = new LwjglApplicationConfiguration();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		cfg.title = name;
		cfg.useGL30 = false; // Screen goes black -_- but that's only an early alpha support
		cfg.vSyncEnabled = true;
//		cfg.width = (int) 3840;
//		cfg.height = (int) 2160;
		cfg.width = (int) screenSize.getWidth();
		cfg.height = (int) screenSize.getHeight();
		cfg.resizable = false;
		cfg.fullscreen = fullscreen;
	}
	
	public static LwjglApplicationConfiguration getCFG() {
		return cfg;
	}
}
