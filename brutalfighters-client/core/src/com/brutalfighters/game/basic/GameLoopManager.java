package com.brutalfighters.game.basic;

import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.multiplayer.packets.Packet2MatchFinished;
import com.brutalfighters.game.sound.SFX;
import com.brutalfighters.game.utility.GameMath;

public class GameLoopManager {
	
	private static boolean isQuitting;
	private static boolean isUpdating;
	
	public static void Load() {
		Resume();
		setUpdating(true);
	}
	
	public static void tick(float delta) {
		
		if(!isQuitting()) {
			
			// Update first then render to make things more smooth.
			// Even though we had a problem with it before, now it seems to be fixed.
			
			// Update
			if(isUpdating()) {
				Update.update(delta);
			}
			
			// Render
			Render.render();
			
		}
		
	}
	
	public static void Quit() {
		isQuitting = true;
	}
	public static void Resume() {
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
		
		if(GameMath.nextInt(1, 2) > 1) {
			SFX.play("crowd", 0.6f); //$NON-NLS-1$
		} else {
			SFX.play("crowd1", 0.6f); //$NON-NLS-1$
		}
	}
}
