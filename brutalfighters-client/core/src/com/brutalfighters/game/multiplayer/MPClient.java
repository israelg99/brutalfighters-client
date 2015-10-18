package com.brutalfighters.game.multiplayer;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.brutalfighters.game.multiplayer.packets.Packet;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

public class MPClient {
	
	private Client client;
	private Kryo kryo;
	
	public MPClient() {
		client = new Client(33668, 4096);
		client.start();
		try {
			//client.connect(5000, "188.226.195.78", 54777, 54666); // OLD_OUTDATED_DIGITALOCEAN_EU //$NON-NLS-1$
			client.connect(5000, "107.170.243.67", 54777, 54666); // NA_SanFrancisco_DigitalOcean //$NON-NLS-1$
			//client.connect(5000, "127.0.0.1", 54777, 54666); //$NON-NLS-1$
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Cannot connect to the server, Maybe the server isn't started yet"); //$NON-NLS-1$
		}
		
		kryo = client.getKryo();
		
		registerPackets();
	}

	private void registerPackets() {
	    kryo.register(Packet.class);
	}
	
	public Kryo getKryo() {
		return kryo;
	}
	
	public void sendPacketTCP(Packet p) {
		client.sendTCP(p);
	}
	public void sendPacketUDP(Packet p) {
		client.sendUDP(p);
	}
	
	public Client getClient() {
		return client;
	}
}
