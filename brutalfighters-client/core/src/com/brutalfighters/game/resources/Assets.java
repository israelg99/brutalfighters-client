package com.brutalfighters.game.resources;

import com.brutalfighters.game.HUD.EscapeMenu;
import com.brutalfighters.game.HUD.GameFont;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.HUD.KillsCounter;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.buffs.Buff;
import com.brutalfighters.game.effects.particles.ParticleEffects;
import com.brutalfighters.game.effects.particles.ParticlesCollection;
import com.brutalfighters.game.effects.text.TextEffects;
import com.brutalfighters.game.flags.FlagHandler;
import com.brutalfighters.game.flags.Flags;
import com.brutalfighters.game.graphics.Shaders;
import com.brutalfighters.game.map.Background;
import com.brutalfighters.game.map.GameMap;
import com.brutalfighters.game.map.Teleport;
import com.brutalfighters.game.menu.MenuUtils;
import com.brutalfighters.game.multiplayer.GameClient;
import com.brutalfighters.game.multiplayer.packets.Packet1Connected;
import com.brutalfighters.game.objects.projectiles.Projectiles;
import com.brutalfighters.game.objects.projectiles.ProjectilesEnum;
import com.brutalfighters.game.player.ClientPlayer;
import com.brutalfighters.game.player.Players;
import com.brutalfighters.game.player.fighters.Fighter;
import com.brutalfighters.game.player.fighters.FighterFactory;
import com.brutalfighters.game.sound.BGM;
import com.brutalfighters.game.sound.BGMManager;
import com.brutalfighters.game.sound.GameSFX;
import com.brutalfighters.game.sound.menu.MainMenuSFX;
import com.brutalfighters.game.utility.Score;

public class Assets {
	
	public static GameMap map;
	public static Background bg;
	public static ClientPlayer client;
	public static Fighter player;
	
	public static Players players;
	
	public static Projectiles projectiles;
	
	public static Flags flags;
	
	public static TextEffects textEffects;
	
	public static Score score;
	
	public static KillsCounter killsCounter;
	
	public static boolean globalResourcesLoaded;
	
	public static void LoadResources(Packet1Connected res) {
		
		// Loading the Champion Enum
		System.err.println("Initialize the FighterFactory Enum!"); //$NON-NLS-1$
		FighterFactory.init();
		System.out.println("Finished initializing the FighterFactory Enum!"); //$NON-NLS-1$
		
		// Loading the Players
		System.err.println("Initializing the players!"); //$NON-NLS-1$
		players  = new Players(res.players);
		System.out.println("Finished initializing the players!"); //$NON-NLS-1$
		
		// Initialize the Projectiles
		System.err.println("Initializing the Projectiles!"); //$NON-NLS-1$
		projectiles = new Projectiles();
		System.out.println("Finished initializing the Projectiles!"); //$NON-NLS-1$
		
		// Initialize the Text Effects
		System.err.println("Loading the Text Effects!"); //$NON-NLS-1$
		TextEffects.load();
		System.out.println("Finished loading the ParticlesCollection!"); //$NON-NLS-1$
		
		// Initializing the ParticlesCollection
		System.err.println("Loading the Particle Effects!"); //$NON-NLS-1$
		ParticleEffects.load();
		System.out.println("Finished loading the Particle Effects!"); //$NON-NLS-1$
		
		// Initializing the ParticlesCollection
		System.err.println("Initializing the ParticlesCollection!"); //$NON-NLS-1$
		ParticlesCollection.init();
		System.out.println("Finished initializing the ParticlesCollection!"); //$NON-NLS-1$
	
		// Initializing the Buffs
		System.err.println("Initializing the Buffs!"); //$NON-NLS-1$
		Buff.init();
		System.out.println("Finished initializing the Buffs!"); //$NON-NLS-1$
		
		// Loading the GameSFXManager Sound
		System.err.println("Initializing the GameSFX Sound!"); //$NON-NLS-1$
		GameSFX.init();
		System.out.println("Finished initializing the GameSFX Sound!"); //$NON-NLS-1$
		
		// Load The Teleports
		System.err.println("Loading the Teleports!"); //$NON-NLS-1$
		Teleport.load();
		System.out.println("Finished loading the Teleports!"); //$NON-NLS-1$
		
		// Load The Game Map
		System.err.println("Initializing the map!"); //$NON-NLS-1$
		map = new GameMap(res.map, 0.75f);
		System.out.println("Finished initializing the Game Map!"); //$NON-NLS-1$
		
		// Load the Render Class
		System.err.println("Loading the Render Class!"); //$NON-NLS-1$
		Render.load();
		System.out.println("Finished loading the Render Class!"); //$NON-NLS-1$
		
		// Load The (Client)Player
		System.err.println("Initializing the client player!"); //$NON-NLS-1$
		client = new ClientPlayer();
		player = FighterFactory.valueOf(res.theClient.getName()).getNew(res.theClient);
		System.out.println("Finished initializing the client player!"); //$NON-NLS-1$
		
		// Load The (Client)Player
		System.err.println("Loading the Flags and FlagHandler!"); //$NON-NLS-1$
		flags = new Flags(res.flags);
		FlagHandler.Load();
		System.out.println("Finished loading the Flags and FlagHandler!"); //$NON-NLS-1$
		
		// Load The Map Background
		System.err.println("Initializing the background!"); //$NON-NLS-1$
		bg = new Background(res.map, 0.5f, Background.getDefaultDivide());
		System.out.println("Finished initializing background!"); //$NON-NLS-1$
		
		// Loading the Shaders
		System.err.println("Loading the Shaders!"); //$NON-NLS-1$
		Shaders.Load();
		System.out.println("Finished to load the Shaders"); //$NON-NLS-1$
		
		// Loading the fonts
		System.err.println("Loading the Escape Menu!"); //$NON-NLS-1$
		EscapeMenu.load();
		System.out.println("Finished loading the Escape Menu!"); //$NON-NLS-1$
		
		// Loading the HUD
		System.err.println("Loading the HUD!"); //$NON-NLS-1$
		HUD.Load();
		HUD.setWarmup(res.warmup);
		System.out.println("Finished loading the HUD!"); //$NON-NLS-1$
		
		System.err.println("Initializing the Score!"); //$NON-NLS-1$
		score = new Score();
		score.flags = new int[] {0,0};
		score.kills = new int[] {0,0};
		System.out.println("Finished initializing the Score!"); //$NON-NLS-1$
		
		// Loading the Projectile Enum
		System.err.println("Initialize the Projectile Enum!"); //$NON-NLS-1$
		ProjectilesEnum.init();
		System.out.println("Finished initializing the Projectile Enum!"); //$NON-NLS-1$
		
		// Loading the Projectile Enum
		System.err.println("Initialize the Kills Counter!"); //$NON-NLS-1$
		killsCounter = new KillsCounter(4000);
		System.out.println("Finished initializing the Kills Counter!"); //$NON-NLS-1$
		
		if(!globalResourcesLoaded) {
			LoadGlobalResources();
		}
	}
	
	public static void LoadGlobalResources() { // Assets needed for not only the game, but for other parts like the MainMenu.
		
		System.err.println("Loading the Main Menu Utils!"); //$NON-NLS-1$
		MenuUtils.Load();
		System.out.println("Finished loading the Main Menu Utils!"); //$NON-NLS-1$
		
		System.err.println("Loading the Main Menu Main Menu SFX!"); //$NON-NLS-1$
		MainMenuSFX.Load();
		System.out.println("Finished loading the Main Menu Main Menu SFX!"); //$NON-NLS-1$
		
		System.err.println("Initializing the BGM!"); //$NON-NLS-1$
		BGM.init();
		System.out.println("Finished initializing the BGM!"); //$NON-NLS-1$
		
		System.err.println("Loading the BGMManager!"); //$NON-NLS-1$
		BGMManager.Load();
		System.out.println("Finished loading the BGMManager!"); //$NON-NLS-1$
		
		System.err.println("Initializing the Fonts!"); //$NON-NLS-1$
		GameFont.init();
		System.out.println("Finished initializing the Fonts!"); //$NON-NLS-1$
		
		// Loading the Prefs
		System.err.println("Loading the Prefs!"); //$NON-NLS-1$
		Prefs.Load();
		System.out.println("Finished loading the Prefs!"); //$NON-NLS-1$
		
		System.err.println("Initializing the Game Client in another THREAD!"); //$NON-NLS-1$
		(new Thread() {
			@Override
			public void run() {
				GameClient.Load();
				GameClient.setConnectListener();
				System.out.println("Finished initializing the Game Client in another THREAD!"); //$NON-NLS-1$
			}
		}).start();
		
		globalResourcesLoaded = true;
	}

	public static void dispose() {
		map.dispose();
	}
}
