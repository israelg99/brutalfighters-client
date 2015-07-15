package com.brutalfighters.game.multiplayer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.brutalfighters.game.multiplayer.packets.Packet;
import com.brutalfighters.game.multiplayer.packets.Packet1Connected;
import com.brutalfighters.game.resources.Resources;
import com.brutalfighters.game.screen.GameScreen;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ConnectListener extends Listener {
		@Override
		public void received (Connection connection, final Object object) {
	 	   if(object instanceof Packet) {
	 		   if(object instanceof Packet1Connected) {
	 			   System.out.println("WE GOT THE RESOURCE PACKET"); //$NON-NLS-1$
	 			   final Packet1Connected cnct = (Packet1Connected)object;
	 			   Gdx.app.postRunnable(new Runnable() {
	 				   @Override
					public void run() {	
	 					   Resources.LoadResources(cnct);
	 					   GameClient.isConnected(true);
	 					   gameScreen();
	 				   }
	 			   });
	 		   }
	 	   }
		}
		
		private void gameScreen() {
			System.out.println("Initializing the game screen!"); //$NON-NLS-1$
			((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
		
		}
	}
