package com.brutalfighters.game.player;

import com.brutalfighters.game.player.fighters.Fighter;
import com.brutalfighters.game.player.fighters.FighterFactory;



public class Players {
	private Fighter[] players;
	
	public Players(PlayerData[] pdata) {
		newPlayers(pdata);
	}
	
	public Fighter[] getPlayers() {
		return players;
	}
	public Fighter getPlayer(int i) {
		return players[i];
	}
	public PlayerData getPlayerData(int i) {
		return players[i].getPlayer();
	}
	public int getLength() {
		return players.length;
	}
	
	public void newPlayers(PlayerData[] pdata) {
		players = new Fighter[pdata.length];
		for(int i = 0; i < players.length; i++) {
			players[i] = FighterFactory.valueOf(pdata[i].getName()).getNew(pdata[i]);
		}
	}
	public void setPlayers(PlayerData[] pdata) {
		if(pdata.length != players.length) {
			newPlayers(pdata);
		} else {
			for(int i = 0; i < players.length; i++) {
				players[i].assignPlayer(pdata[i]);
			}
		}
	}
	
	public void updatePlayers() {
		for(int i = 0; i < getLength(); i++) {
			getPlayer(i).update();
		}
	}
}
