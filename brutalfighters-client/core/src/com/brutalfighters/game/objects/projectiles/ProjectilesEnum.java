package com.brutalfighters.game.objects.projectiles;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.sound.SoundUtil;
import com.brutalfighters.game.utility.rendering.AnimationHandler;
import com.brutalfighters.game.utility.rendering.TextureHandle;

public enum ProjectilesEnum {
	
	Blaze_PHEONIX(300, 500, 256, 257, "champs/blaze/phoenix_right.png") { //$NON-NLS-1$
		
		@Override
		public void LoadFrames() {
			idle = TextureHandle.ApplyFrames(0, 0, 3, 4, sprite);
			explosion = TextureHandle.ApplyFrames(0, 0, 0, 0, sprite);
		}
		
	},
	
	Blaze_SkullFire(105, 80, 60, 70, "champs/blaze/skullfire_right.png") { //$NON-NLS-1$

		@Override
		public void LoadFrames() {
			idle = TextureHandle.ApplyFrames(0, 0, 4, 1, sprite);
			explosion = TextureHandle.ApplyFrames(0, 1, 3, 2, sprite);
		}

	},
	
	Blaze_BloodBall(105, 80, 70, 50, "champs/blaze/bloodball_right.png") { //$NON-NLS-1$

		@Override
		public void LoadFrames() {
			idle = TextureHandle.ApplyFrames(0, 0, 4, 1, sprite);
			explosion = TextureHandle.ApplyFrames(0, 1, 4, 2, sprite);
		}

	},
	
	Dusk_PurpleBat(160, 120, 60, 50, "champs/dusk/purplebat_right.png") { //$NON-NLS-1$

		@Override
		public void LoadFrames() {
			idle = TextureHandle.ApplyFrames(0, 0, 3, 1, sprite);
			explosion = TextureHandle.ApplyFrames(0, 1, 4, 2, sprite);
		}

	},
	
	Dusk_BATS(200, 240, 100, 100, "champs/dusk/bats_right.png") { //$NON-NLS-1$

		@Override
		public void LoadFrames() {
			idle = TextureHandle.ApplyFrames(0, 0, 4, 1, sprite);
			explosion = TextureHandle.ApplyFrames(0, 1, 4, 2, sprite);
		}

	},
	
	Dusk_LASER(200, 80, 130, 40, "champs/dusk/laser_right.png") { //$NON-NLS-1$

		@Override
		public void LoadFrames() {
			idle = TextureHandle.ApplyFrames(0, 0, 2, 1, sprite);
			explosion = TextureHandle.ApplyFrames(0, 1, 4, 2, sprite);
		}

	},
	
	Dusk_BATZ(300, 175, 130, 80, "champs/dusk/batz_right.png") { //$NON-NLS-1$

		@Override
		public void LoadFrames() {
			animationSpeed = 0.16f;
			animationExplodeSpeed = 0.1f;
			idle = TextureHandle.ApplyFrames(0, 0, 4, 1, sprite);
			explosion = TextureHandle.ApplyFrames(0, 1, 4, 2, sprite);
			explode_sfx = SoundUtil.getSound("projectiles/explode2.wav"); //$NON-NLS-1$
		}

	},
	
	Chip_MINE(200, 200, 145, 150, "champs/chip/mine_right.png") { //$NON-NLS-1$

		@Override
		public void LoadFrames() {
			idle = TextureHandle.ApplyFrames(0, 0, 4, 1, sprite);
			explosion = TextureHandle.ApplyFrames(0, 1, 5, 2, sprite);
			explode_sfx = SoundUtil.getSound("projectiles/explode2.wav"); //$NON-NLS-1$
		}

	},
	
	Chip_TNT(200, 200, 100, 90, "champs/chip/tnt_right.png") { //$NON-NLS-1$

		@Override
		public void LoadFrames() {
			idle = TextureHandle.ApplyFrames(0, 0, 7, 1, sprite);
			explosion = TextureHandle.ApplyFrames(0, 1, 6, 2, sprite);
			explode_sfx = SoundUtil.getSound("projectiles/explode1.wav"); //$NON-NLS-1$
		}

	},
	
	Chip_RPG(138, 105, 138, 105, "champs/chip/rpg_right.png") { //$NON-NLS-1$

		@Override
		public void LoadFrames() {
			idle = TextureHandle.ApplyFrames(0, 0, 4, 1, sprite);
			explosion = TextureHandle.ApplyFrames(0, 1, 6, 2, sprite);
			explode_sfx = SoundUtil.getSound("projectiles/explode1.wav"); //$NON-NLS-1$
		}

	},
	
	Surge_EnergyWave(300, 100, 150, 80, "champs/surge/energywave_right.png") { //$NON-NLS-1$
		
		@Override
		public void LoadFrames() {
			idle = TextureHandle.ApplyFrames(0, 0, 4, 1, sprite);
			explosion = TextureHandle.ApplyFrames(0, 1, 4, 2, sprite);
		}
		
	},
	
	Surge_DashBall(150, 100, 80, 80, "champs/surge/dashball_right.png") { //$NON-NLS-1$
		
		@Override
		public void LoadFrames() {
			animationSpeed = 0.1f;
			idle = TextureHandle.ApplyFrames(0, 0, 3, 1, sprite);
			explosion = TextureHandle.ApplyFrames(0, 1, 3, 2, sprite);
		}
		
	},
	
	Lust_EnergyBall(100, 67, 90, 45, "champs/lust/energyball.png") { //$NON-NLS-1$
		
		@Override
		public void LoadFrames() {
			idle = TextureHandle.ApplyFrames(0, 0, 4, 1, sprite);
			explosion = TextureHandle.ApplyFrames(0, 1, 4, 2, sprite);
			
		}

	};
	
	public final int WIDTH,HEIGHT;
	public final int BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT;
	public final TextureRegion[][] sprite;
	
	public float animationSpeed = 0.08f;
	public float animationExplodeSpeed = 0.08f;
	public Sound explode_sfx = SoundUtil.getSound("projectiles/explode3.wav"); //$NON-NLS-1$
	
	public TextureRegion[] idle, explosion;
	
	ProjectilesEnum(int width, int height, int blockW, int blockH, String sprite) {
		this.BLOCK_SIZE_WIDTH = blockW;
		this.BLOCK_SIZE_HEIGHT = blockH;
		this.WIDTH = width;
		this.HEIGHT = height;
		this.sprite = TextureHandle.TextureSplit(sprite, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, true, false);
		LoadFrames();
	}
	
	public abstract void LoadFrames();
	
	public void update(Projectile p) {
		p.updateVelocities();
	}
	
	public Animation getAnimation(Projectile p) {
		return AnimationHandler.getAnimation(p.getData().flip, idle, animationSpeed, Animation.PlayMode.LOOP_PINGPONG);
	}
	public Animation getExplodedAnimation(ExplodeProjectile p) {
		return AnimationHandler.getAnimation(p.getData().flip, explosion, animationExplodeSpeed, Animation.PlayMode.NORMAL);
	}

	public void playExplode() {
		explode_sfx.play();
	}
	public void playExplode(float x) {
		SoundUtil.playStereo(explode_sfx, x);
	}
	
	public static boolean contains(String projectile) {
	    for (ProjectilesEnum c : ProjectilesEnum.values()) {
	        if (c.name().equals(projectile)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	public static void init() {
		values();
	}
}
