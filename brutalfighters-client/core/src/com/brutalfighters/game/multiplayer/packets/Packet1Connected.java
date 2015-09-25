package com.brutalfighters.game.multiplayer.packets;

import com.brutalfighters.game.flags.FlagData;
import com.brutalfighters.game.player.PlayerData;

public class Packet1Connected extends GameMatchPacket {
	public String map;
	public PlayerData theClient;
	public PlayerData[] players;
	public FlagData[] flags;
	public int warmup;
}
