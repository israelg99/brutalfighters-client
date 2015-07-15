package com.brutalfighters.game.HUD;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.brutalfighters.game.basic.GameLoopManager;
import com.brutalfighters.game.menu.MenuUtils;
import com.brutalfighters.game.multiplayer.GameClient;
import com.brutalfighters.game.multiplayer.packets.Packet5EscapeMatch;
import com.brutalfighters.game.screen.MainMenuScreen;
import com.brutalfighters.game.screen.SettingsScreen;
import com.brutalfighters.game.sound.BGM;

public enum EscapeOption {

	ResumeGame {

		@Override
		public void trigger() {
			EscapeMenu.disableEM();
			
		}
		
	},
	
	SettingsMenu {

		@Override
		public void trigger() {
			Gdx.input.setCursorCatched(false);
			EscapeMenu.disableEM();
			escapeMatch();
			BGM.setMainMenu();
			((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsScreen());
			
		}
		
	},
	
	MainMenu {

		@Override
		public void trigger() {
			Gdx.input.setCursorCatched(false);
			EscapeMenu.disableEM();
			escapeMatch();
			BGM.setMainMenu();
			((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
		}
		
	},
	
	Quit {

		@Override
		public void trigger() {
			GameLoopManager.Quit();
			disposeExit();
			Gdx.app.exit();
			
		}
		
	};
	
	public abstract void trigger();
	
	public static void escapeMatch() {
		GameClient.sendPacketTCP(new Packet5EscapeMatch());
		GameClient.removeListeners();
	}
	
	private static void disposeExit() {
		System.err.println("Disposing Escape Option EXIT!"); //$NON-NLS-1$
		// Everything else is already disposed when the GameScreen disposing.
		MenuUtils.disposeExit();
	}
}
