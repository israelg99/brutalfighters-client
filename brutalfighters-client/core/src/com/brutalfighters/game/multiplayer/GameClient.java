package com.brutalfighters.game.multiplayer;

import com.brutalfighters.game.buffs.BuffData;
import com.brutalfighters.game.flags.Flag;
import com.brutalfighters.game.multiplayer.packets.GameMatchPacket;
import com.brutalfighters.game.multiplayer.packets.ConnectGameMatch;
import com.brutalfighters.game.multiplayer.packets.Packet;
import com.brutalfighters.game.multiplayer.packets.Packet0ConnectMatch;
import com.brutalfighters.game.multiplayer.packets.Packet1Connected;
import com.brutalfighters.game.multiplayer.packets.Packet2MatchFinished;
import com.brutalfighters.game.multiplayer.packets.Packet2MatchOver;
import com.brutalfighters.game.multiplayer.packets.Packet2Players;
import com.brutalfighters.game.multiplayer.packets.Packet2Warmup;
import com.brutalfighters.game.multiplayer.packets.Packet3InputAAttack;
import com.brutalfighters.game.multiplayer.packets.Packet3InputJump;
import com.brutalfighters.game.multiplayer.packets.Packet3InputLeft;
import com.brutalfighters.game.multiplayer.packets.Packet3InputRight;
import com.brutalfighters.game.multiplayer.packets.Packet3InputRun;
import com.brutalfighters.game.multiplayer.packets.Packet3InputSkill1;
import com.brutalfighters.game.multiplayer.packets.Packet3InputSkill2;
import com.brutalfighters.game.multiplayer.packets.Packet3InputSkill3;
import com.brutalfighters.game.multiplayer.packets.Packet3InputSkill4;
import com.brutalfighters.game.multiplayer.packets.Packet3InputTeleport;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseAAttack;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseJump;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseLeft;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseRight;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseRun;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseSkill1;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseSkill2;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseSkill3;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseSkill4;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseTeleport;
import com.brutalfighters.game.multiplayer.packets.Packet5EscapeMatch;
import com.brutalfighters.game.objects.projectiles.ProjectileData;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.utility.Score;
import com.esotericsoftware.kryonet.Listener;

public class GameClient {
	private static MPClient client;
	private static boolean isConnected;
	private static NetworkListener networkListener;
	private static ConnectListener connectListener;
	private static boolean isLoaded = false;
	
	public static void Load() {
		if(!isLoaded()) {
			client = new MPClient();
			registerPackets();
			loadListeners();
			isLoaded(true);
		}
	}
	
	private static void registerPackets() {
		client.getKryo().register(Packet.class);
		client.getKryo().register(GameMatchPacket.class);
		client.getKryo().register(ConnectGameMatch.class);
		
		client.getKryo().register(Packet0ConnectMatch.class);
		client.getKryo().register(PlayerData.class);
		client.getKryo().register(PlayerData[].class);
		client.getKryo().register(BuffData.class);
		client.getKryo().register(BuffData[].class);
		client.getKryo().register(int[].class);
		client.getKryo().register(ProjectileData.class);
		client.getKryo().register(ProjectileData[].class);
		client.getKryo().register(Flag.class);
		client.getKryo().register(Flag[].class);
		client.getKryo().register(Score.class);
		client.getKryo().register(Packet1Connected.class);
		client.getKryo().register(Packet2Players.class);
		client.getKryo().register(Packet2Warmup.class);
		client.getKryo().register(Packet2MatchOver.class);
		client.getKryo().register(Packet2MatchFinished.class);
		
		client.getKryo().register(Packet3InputLeft.class);
		client.getKryo().register(Packet3InputRight.class);
		client.getKryo().register(Packet3InputJump.class);
		client.getKryo().register(Packet3InputRun.class);
		client.getKryo().register(Packet3InputAAttack.class);
		client.getKryo().register(Packet3InputSkill1.class);
		client.getKryo().register(Packet3InputSkill2.class);
		client.getKryo().register(Packet3InputSkill3.class);
		client.getKryo().register(Packet3InputSkill4.class);
		client.getKryo().register(Packet3InputTeleport.class);
		
		client.getKryo().register(Packet4ReleaseLeft.class);
		client.getKryo().register(Packet4ReleaseRight.class);
		client.getKryo().register(Packet4ReleaseJump.class);
		client.getKryo().register(Packet4ReleaseRun.class);
		client.getKryo().register(Packet4ReleaseAAttack.class);
		client.getKryo().register(Packet4ReleaseSkill1.class);
		client.getKryo().register(Packet4ReleaseSkill2.class);
		client.getKryo().register(Packet4ReleaseSkill3.class);
		client.getKryo().register(Packet4ReleaseSkill4.class);
		client.getKryo().register(Packet4ReleaseTeleport.class);
		
		client.getKryo().register(Packet5EscapeMatch.class);
	}
	
	public static boolean isLoaded() {
		return isLoaded;
	}
	private static void isLoaded(boolean load) {
		isLoaded = load;
	}
	
	private static void loadListeners() {
		networkListener = new NetworkListener();
		connectListener = new ConnectListener();
	}
	
	private static void addListener(Listener listener) {
		client.getClient().addListener(listener);
	}
	private static void setListener(Listener listener, Listener rev_listener) {
		client.getClient().addListener(listener);
		client.getClient().removeListener(rev_listener);
	}
	private static void removeListener(Listener listener) {
		client.getClient().removeListener(listener);
	}
	
	public static void setConnection(GameMode gamemode, String fighter) {
		Packet0ConnectMatch cnct = new Packet0ConnectMatch();
		cnct.username = "Username"; //$NON-NLS-1$
		cnct.fighter = fighter;
		cnct.gamemode = gamemode.name();
		sendPacketTCP(cnct);
	}
	
	public static void isConnected(boolean cnct) {
		isConnected = cnct;
	}
	public static boolean isConnected() {
		return isConnected;
	}
	
	public static void sendPacketTCP(Packet p) {
		client.sendPacketTCP(p);
	}
	public static void sendPacketUDP(Packet p) {
		client.sendPacketUDP(p);
	}
	public static void sentEscapedTCP() {
		client.sendPacketTCP(new Packet5EscapeMatch());
	}
	
	public static NetworkListener getNetworkListener() {
		return networkListener;
	}
	public static ConnectListener getConnectListener() {
		return connectListener;
	}
	public static void setNetworkListener() {
		setListener(getNetworkListener(), getConnectListener());
	}
	public static void setConnectListener() {
		setListener(getConnectListener(), getNetworkListener());
	}
	public static void removeListeners() {
		removeListener(getNetworkListener());
		removeListener(getConnectListener());
	}
	
}
