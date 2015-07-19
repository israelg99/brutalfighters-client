package com.brutalfighters.game.HUD;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.brutalfighters.game.utility.rendering.TextureHandle;

public enum GameFont {
	
	MainMenu(TextureHandle.getFont("fonts/theboldfont.ttf", 36, Color.WHITE, true, false)) { //$NON-NLS-1$

	},
	
	EscapeButton(TextureHandle.getFont("fonts/Roboto.ttf", 32, Color.WHITE, true, false)) { //$NON-NLS-1$
		
	},
	
	EscapeButtonHover(TextureHandle.getFont("fonts/Roboto.ttf", 32, Color.RED, true, false)) { //$NON-NLS-1$
		
	},
	
	Blood(TextureHandle.getFont("fonts/BebasNeue.otf", 24, Color.RED, true, false)) { //$NON-NLS-1$
		
	},
	
	Heal(TextureHandle.getFont("fonts/BebasNeue.otf", 24, Color.GREEN, true, false)) { //$NON-NLS-1$
		
	},
	
	Info(TextureHandle.getFont("fonts/CaviarDreams.ttf", 32, Color.RED, true, false)) { //$NON-NLS-1$
		
	},
	
	BlueTeamBig(TextureHandle.getFont("fonts/BebasNeue.otf", 64, Color.BLUE, true, false)) { //$NON-NLS-1$
		
	},
	
	RedTeamBig(TextureHandle.getFont("fonts/BebasNeue.otf", 64, Color.RED, true, false)) { //$NON-NLS-1$
		
	},
	
	BlueTeam(TextureHandle.getFont("fonts/BebasNeue.otf", 32, Color.BLUE, true, false)) { //$NON-NLS-1$
		
	},
	
	RedTeam(TextureHandle.getFont("fonts/BebasNeue.otf", 32, Color.RED, true, false)) { //$NON-NLS-1$
		
	},
	
	Warmup(TextureHandle.getFont("fonts/theboldfont.ttf", 128, Color.DARK_GRAY, true, false)) { //$NON-NLS-1$
		
	},
	
	Respawn(TextureHandle.getFont("fonts/theboldfont.ttf", 128, Color.BLACK, true, false)) { //$NON-NLS-1$
		
	},
	
	Tutorial(TextureHandle.getFont("fonts/CaviarDreams.ttf", 54, Color.BLACK, true, false)) { //$NON-NLS-1$
		
	},
	
	EnemyKilled(TextureHandle.getFont("fonts/theboldfont.ttf", 100, Color.BLACK, true, false)) { //$NON-NLS-1$
		
	},
	
	KillsCounter(TextureHandle.getFont("fonts/theboldfont.ttf", 100, Color.WHITE, true, false)) { //$NON-NLS-1$
		
	};
	
	private final BitmapFont font;
	
	GameFont(BitmapFont font) {
		this.font = font;
	}
	
	public BitmapFont getFont() {
		return font;
	}

	public static void init() {
		values();
	}
}
