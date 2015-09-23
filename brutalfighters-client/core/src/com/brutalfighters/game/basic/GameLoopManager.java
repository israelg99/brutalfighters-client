package com.brutalfighters.game.basic;

import com.brutalfighters.game.HUD.EscapeOption;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.multiplayer.packets.Packet2MatchFinished;
import com.brutalfighters.game.sound.GameSFXManager;

public class GameLoopManager {
	
	private static boolean isQuitting;
	private static boolean isUpdating;

	public static void load() {
		resume();
		setUpdating(true);
	}
	
	public static void tick() {
		
		if(!isQuitting()) {
			
			// Update first then render to make things more smooth.
			// Even though we had a problem with it before, now it seems to be fixed.
			
			// Update
			if(isUpdating()) {
				Update.update();
			}
			
			// Render
			Render.render();
			
		}
		
	}
	
	public static void quit() {
		isQuitting = true;
	}
	public static void resume() {
		isQuitting = false;
	}
	public static boolean isQuitting() {
		return isQuitting;
	}

	public static boolean isUpdating() {
		return isUpdating;
	}
	public static void setUpdating(boolean isUpdating) {
		GameLoopManager.isUpdating = isUpdating;
	}
	
	public static void matchFinished(Packet2MatchFinished packet) {
		setUpdating(false);
		HUD.setTeamWon(packet);
		
		GameSFXManager.playRandomCrowd();
	}
	
	public static void matchOver() {
		EscapeOption.MainMenu.trigger();
	}
}
