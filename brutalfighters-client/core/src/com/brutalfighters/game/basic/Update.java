package com.brutalfighters.game.basic;

import com.badlogic.gdx.Gdx;
import com.brutalfighters.game.HUD.EscapeOption;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.multiplayer.packets.Packet2Players;
import com.brutalfighters.game.multiplayer.packets.Packet2Warmup;
import com.brutalfighters.game.resources.Resources;
import com.brutalfighters.game.sound.BGM;


public class Update {
	
	private static float gameTime;
	private static final float stateTime = 1/60f;
	
	public static void update(float delta) {
		gameTime += delta;
		
		updateGame();
	}
	
	private static void updateGame() {
		if(!HUD.isWarmup()) {
			updatePlayers();
			updateClient();
			updateFlags();
			updateBGM();
		} else {
			Resources.client.applyBasic();
		}
	}

	private static void updateFlags() {
		Resources.flags.updateFlags();
	}

	private static void updateBGM() {
		BGM.update();
	}

	private static void updatePlayers() {
		Resources.players.updatePlayers();
		
		Resources.player.update();
	}

	public static void updateClient() {
		Resources.client.update();
		
	}

	public static float getTime() {
		return gameTime;
	}

	public static void updateGameData(Packet2Players packet) {
		Resources.players.setPlayers(packet.players);
		
		Resources.player.setPlayer(packet.theClient);
		
		Resources.projectiles.setNew(packet.projectiles);
		
		Resources.flags.updateFlags(packet.flags);
		
		Resources.score = packet.score;
	}
	
	public static float getStateTime() {
		return stateTime;
	}
	public static float getDeltaMS() {
		return Gdx.graphics.getDeltaTime() * 1000;
	}

	public static void matchOver() {
		EscapeOption.MainMenu.trigger();
	}

	public static void updateWarmup(Packet2Warmup object) {
		HUD.setWarmup(object.warmup);
	}
}
