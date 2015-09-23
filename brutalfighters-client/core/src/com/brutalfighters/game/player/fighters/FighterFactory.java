package com.brutalfighters.game.player.fighters;

import com.brutalfighters.game.player.PlayerData;

public enum FighterFactory {
	
	Blaze {
		@Override
		public Fighter getNew(PlayerData pdata) {
			return new Blaze(pdata);
		}
		
	},
	
	Dusk {
		@Override
		public Fighter getNew(PlayerData pdata) {
			return new Dusk(pdata);
		}
		
	},
	
	Chip {
		@Override
		public Fighter getNew(PlayerData pdata) {
			return new Chip(pdata);
		}
		
	},
	
	Surge {
		@Override
		public Fighter getNew(PlayerData pdata) {
			return new Surge(pdata);
		}
		
	},
	
	Lust {
		@Override
		public Fighter getNew(PlayerData pdata) {
			return new Lust(pdata);
		}
	};
	
	public abstract Fighter getNew(PlayerData pdata);
	
	public static boolean contains(String fighter) {
	    for (FighterFactory c : FighterFactory.values()) {
	        if (c.name().equals(fighter)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	public static void init() {
		values();
	}
}
