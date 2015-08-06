package com.brutalfighters.game.basic;

import com.brutalfighters.game.HUD.EscapeOption;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.multiplayer.packets.Packet2Players;
import com.brutalfighters.game.resources.Assets;
import com.brutalfighters.game.sound.BGMManager;


public class Update {

	public static void update() {
		
		updateTimes();
		
		updateGame();
	}
	
	private static void updateTimes() {
		GameTime.update();
	}

	private static void updateGame() {
		if(!HUD.isWarmup()) {
			updatePlayers();
			updateClient();
			updateFlags();
			updateBGM();
		} else {
			Assets.client.applyBasic();
		}
	}

	private static void updateFlags() {
		Assets.flags.updateFlags();
	}

	private static void updateBGM() {
		BGMManager.update();
	}

	private static void updatePlayers() {
		Assets.players.updatePlayers();
		
		Assets.player.update();
	}

	private static void updateClient() {
		Assets.client.update();
		
	}

	public static void updateGameData(Packet2Players packet) {
		Assets.players.setPlayers(packet.players);
		
		Assets.player.setPlayer(packet.theClient);
		
		Assets.projectiles.setNew(packet.projectiles);
		
		Assets.flags.updateFlags(packet.flags);
		
		Assets.score = packet.score;
	}
}
