package com.brutalfighters.game.multiplayer.packets;

import com.brutalfighters.game.flags.Flag;
import com.brutalfighters.game.player.PlayerData;

public class Packet1Connected extends ClosedMatchPacket {
	public String map;
	public PlayerData theClient;
	public PlayerData[] players;
	public Flag[] flags;
}
