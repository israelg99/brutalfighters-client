package com.brutalfighters.game.resources;

import com.brutalfighters.game.HUD.EscapeMenu;
import com.brutalfighters.game.HUD.GameFont;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.HUD.KillsCounter;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.effects.particles.Particles;
import com.brutalfighters.game.effects.text.TextEffects;
import com.brutalfighters.game.effects.text.TextFX;
import com.brutalfighters.game.flags.FlagHandler;
import com.brutalfighters.game.flags.Flags;
import com.brutalfighters.game.graphics.Shaders;
import com.brutalfighters.game.map.Background;
import com.brutalfighters.game.map.GameMap;
import com.brutalfighters.game.map.Teleport;
import com.brutalfighters.game.menu.MenuUtils;
import com.brutalfighters.game.multiplayer.packets.Packet1Connected;
import com.brutalfighters.game.objects.projectiles.Projectiles;
import com.brutalfighters.game.objects.projectiles.ProjectilesEnum;
import com.brutalfighters.game.player.Champion;
import com.brutalfighters.game.player.ClientPlayer;
import com.brutalfighters.game.player.Player;
import com.brutalfighters.game.player.Players;
import com.brutalfighters.game.sound.BGM;
import com.brutalfighters.game.sound.SFX;
import com.brutalfighters.game.sound.menu.MainMenuSFX;
import com.brutalfighters.game.utility.Score;

public class Resources {
	
	public static GameMap map;
	public static Background bg;
	public static ClientPlayer client;
	public static Player player;
	
	public static Players players;
	
	public static Projectiles projectiles;
	
	public static Flags flags;
	
	public static TextEffects textEffects;
	
	public static Score score;
	
	public static KillsCounter killsCounter;
	
	public static boolean globalResourcesLoaded;
	
	public static void LoadResources(Packet1Connected res) {
		
		// Loading the Players
		System.out.println("Loading the players!"); //$NON-NLS-1$
		players  = new Players(res.players);
		
		// Initialize the Projectiles
		System.err.println("Initializing the Projectiles!"); //$NON-NLS-1$
		projectiles = new Projectiles();
		
		// Initialize the Text Effects
		System.err.println("Initializing the Text Effects!"); //$NON-NLS-1$
		textEffects = new TextEffects();
		
		// Loading the Chars
		System.out.println("Loading the Chars"); //$NON-NLS-1$
		
		// Loading the Particles
		System.err.println("Loading the Particles!"); //$NON-NLS-1$
		Particles.Load();
		System.out.println("Finished Loading the Particles!"); //$NON-NLS-1$
		
		// Loading the SFX Sound
		System.err.println("Loading the SFX Sound!"); //$NON-NLS-1$
		SFX.Load();
		System.out.println("Finished Loading the SFX Sound!"); //$NON-NLS-1$
		
		// Load The Teleports
		System.err.println("Loading the Teleports!"); //$NON-NLS-1$
		Teleport.load();
		System.out.println("Finished Loading the Teleports!"); //$NON-NLS-1$
		
		// Load The Game Map
		System.err.println("Loading the map!"); //$NON-NLS-1$
		map = new GameMap(res.map, 0.75f);
		System.out.println("Finished Loading the Game Map!"); //$NON-NLS-1$
		
		// Load the Render Class
		System.err.println("Loading the Render Class!"); //$NON-NLS-1$
		Render.Load();
		System.out.println("Finished Loading the Render Class!"); //$NON-NLS-1$
		
		// Load The (Client)Player
		System.out.println("Loading the player!"); //$NON-NLS-1$
		client = new ClientPlayer();
		player = new Player(res.theClient);
		
		// Load The (Client)Player
		System.out.println("Loading the Flags and FlagHandler!"); //$NON-NLS-1$
		flags = new Flags(res.flags);
		FlagHandler.Load();
		
		// Load The Map Background
		System.err.println("Loading the background!"); //$NON-NLS-1$
		bg = new Background(res.map, 0.5f, Background.getDefaultDivide());
		System.out.println("Finished Loading background!"); //$NON-NLS-1$
		
		// Loading the Shaders
		System.err.println("Loading the Shaders!"); //$NON-NLS-1$
		Shaders.Load();
		System.out.println("Finished to load the Shaders"); //$NON-NLS-1$
		
		// Loading the fonts
		System.err.println("Loading the Escape Menu!"); //$NON-NLS-1$
		EscapeMenu.load();
		System.out.println("Finished Loading the Escape Menu!"); //$NON-NLS-1$
		
		// Loading the HUD
		System.err.println("Loading the HUD!"); //$NON-NLS-1$
		HUD.Load();
		System.out.println("Finished Loading the HUD!"); //$NON-NLS-1$
		
		System.err.println("Initializing the Score!"); //$NON-NLS-1$
		score = new Score();
		score.flags = new int[] {0,0};
		score.kills = new int[] {0,0};
		
		// Loading the Champion Enum
		System.err.println("Initialize the Champion Enum!"); //$NON-NLS-1$
		Champion.init();
		System.out.println("Finished Initializing the Champion Enum!"); //$NON-NLS-1$
		
		// Loading the Projectile Enum
		System.err.println("Initialize the Projectile Enum!"); //$NON-NLS-1$
		ProjectilesEnum.init();
		System.out.println("Finished Initializing the Projectile Enum!"); //$NON-NLS-1$
		
		// Loading the Projectile Enum
		System.err.println("Initialize the Text FX Enum!"); //$NON-NLS-1$
		TextFX.init();
		System.out.println("Finished Initializing the Text FX Enum!"); //$NON-NLS-1$
		
		// Loading the Projectile Enum
		System.err.println("Initialize the Kills Counter!"); //$NON-NLS-1$
		killsCounter = new KillsCounter(4000);
		System.out.println("Finished Initializing the Kills Counter!"); //$NON-NLS-1$
		
		if(!globalResourcesLoaded) {
			LoadGlobalResources();
		}
	}
	
	public static void LoadGlobalResources() { // Resources needed for not only the game, but for other parts like the MainMenu.
		
		System.err.println("Loading the Main Menu Utils!"); //$NON-NLS-1$
		MenuUtils.Load();
		
		System.err.println("Loading the Main Menu SFX!"); //$NON-NLS-1$
		MainMenuSFX.Load();
		
		System.err.println("Loading the BGM!"); //$NON-NLS-1$
		BGM.Load();
		System.out.println("Finished Loading the BGM!"); //$NON-NLS-1$
		
		System.err.println("Loading the Fonts!"); //$NON-NLS-1$
		GameFont.init();
		
		// Loading the Prefs
		System.err.println("Loading the Prefs!"); //$NON-NLS-1$
		Prefs.Load();
		System.out.println("Finished Loading the Prefs!"); //$NON-NLS-1$
		
		globalResourcesLoaded = true;
	}
}
