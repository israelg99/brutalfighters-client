package com.brutalfighters.game.player;



public class Players {
	private Player[] players;
	
	public Players(PlayerData[] pdata) {
		newPlayers(pdata);
	}
	
	public Player[] getPlayers() {
		return players;
	}
	public Player getPlayer(int i) {
		return players[i];
	}
	public PlayerData getPlayerData(int i) {
		return players[i].getPlayer();
	}
	public int getLength() {
		return players.length;
	}
	
	public void newPlayers(PlayerData[] pdata) {
		players = new Player[pdata.length];
		for(int i = 0; i < players.length; i++) {
			players[i] = new Player(pdata[i]);
		}
	}
	public void setPlayers(PlayerData[] pdata) {
		if(pdata.length != players.length) {
			newPlayers(pdata);
			return;
		}
		for(int i = 0; i < players.length; i++) {
			players[i].setPlayer(pdata[i]);
		}
	}
	
	public void updatePlayers() {
		for(int i = 0; i < getLength(); i++) {
			getPlayer(i).update();
		}
	}
}
