package com.brutalfighters.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.brutalfighters.game.BrutalFighters;


public class DesktopLauncher {
	
	public static void main (String[] arg) {
		System.out.println("Initializing the Window!"); //$NON-NLS-1$
		Window.Initialize("Fighting Game", true); //$NON-NLS-1$
		System.err.println("The Window has Initialized"); //$NON-NLS-1$
		
		System.out.println("Calling the Game!"); //$NON-NLS-1$
		new LwjglApplication(new BrutalFighters(), Window.getCFG()); // Game starts afterwards, no point in saving it into a field.
	}
}
