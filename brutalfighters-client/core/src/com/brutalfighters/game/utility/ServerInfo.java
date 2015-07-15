package com.brutalfighters.game.utility;

import com.brutalfighters.game.basic.Update;

public class ServerInfo {
	private static final int FPS = 50;
	private static final int DIVIDE = 1000/ServerInfo.getFPS();
	private static final int DEFAULT_WARMUP = 20000;
	public static final int FLAG_WIDTH = 20, FLAG_HEIGHT = 155;
	
	public static int getFPS() {
		return FPS;
	}
	public static int getDIVIDE() {
		return DIVIDE;
	}
	public static int getWarmup() {
		return DEFAULT_WARMUP;
	}
	
	public static float syncServer(float num) {
		return num / (getFPS() / Update.getDeltaMS());
	}
	public static float getRatio() {
		return (getFPS() / Update.getDeltaMS());
	}
}
