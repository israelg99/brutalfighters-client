package com.brutalfighters.game.utility;

import java.util.Random;

public class GameMath {
	private static Random random = new Random();
	
	public static int nextInt(int min, int max) {
		return random.nextInt((max - min) + 1) + min;
	}
	public static float nextFloat(float min, float max) {
		return min + (int)(random.nextFloat() * ((max - min) + 1));
	}
	public static boolean nextBoolean(int number) { // 0 - 100
		return nextInt(0, 100) > number;
	}
}
