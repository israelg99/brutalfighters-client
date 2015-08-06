package com.brutalfighters.game.multiplayer;

import com.badlogic.gdx.Gdx;
import com.brutalfighters.game.basic.GameLoopManager;
import com.brutalfighters.game.basic.Update;
import com.brutalfighters.game.multiplayer.packets.Packet;
import com.brutalfighters.game.multiplayer.packets.Packet2MatchFinished;
import com.brutalfighters.game.multiplayer.packets.Packet2MatchOver;
import com.brutalfighters.game.multiplayer.packets.Packet2Players;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class NetworkListener extends Listener {
    @Override
	public void received (Connection connection, Object object) {
 	   if(object instanceof Packet) {
 		   if(!GameLoopManager.isQuitting()) {
	 		   if(object instanceof Packet2Players) {
	 			   Update.updateGameData((Packet2Players)object);
	 		   } else if(object instanceof Packet2MatchFinished) {
	 			   GameLoopManager.matchFinished((Packet2MatchFinished)object);
	 			   
	 		   } else if(object instanceof Packet2MatchOver) {
	 			   
	 			   // Callback to the Main OpenGL Thread
	 			   Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							GameLoopManager.matchOver();
						}
	 			   });
	 		   }
 		   }
 	   }
    }
}
