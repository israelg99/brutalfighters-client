package com.brutalfighters.game.multiplayer.packets;

import com.brutalfighters.game.flags.FlagData;
import com.brutalfighters.game.objects.projectiles.ProjectileData;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.utility.Score;

public class Packet2Players extends GameMatchPacket {
	public PlayerData theClient;
	public PlayerData[] players;
	public ProjectileData[] projectiles;
	public FlagData[] flags;
	public Score score;
}
