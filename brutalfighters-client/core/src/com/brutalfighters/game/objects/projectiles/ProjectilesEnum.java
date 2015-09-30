package com.brutalfighters.game.objects.projectiles;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.math.Vec2;
import com.brutalfighters.game.sound.GameSFX;
import com.brutalfighters.game.sound.SoundUtil;
import com.brutalfighters.game.utility.rendering.AnimationHandler;
import com.brutalfighters.game.utility.rendering.TextureHandle;

public enum ProjectilesEnum {
	
	Pheonix(300, 500, 256, 257, getPath()+"phoenix_right.png") { //$NON-NLS-1$
		
		@Override
		public void load() {
			setIdle(TextureHandle.ApplyFrames(0, 0, 3, 4, getSprite()));
			setExplosion(TextureHandle.ApplyFrames(0, 0, 0, 0, getSprite()));
		}
		
	},
	
	SkullFire(105, 80, 60, 70, getPath()+"skullfire_right.png") { //$NON-NLS-1$

		@Override
		public void load() {
			setIdle(TextureHandle.ApplyFrames(0, 0, 4, 1, getSprite()));
			setExplosion(TextureHandle.ApplyFrames(0, 1, 3, 2, getSprite()));
		}

	},
	
	BloodBall(105, 80, 70, 50, getPath()+"bloodball_right.png") { //$NON-NLS-1$

		@Override
		public void load() {
			setIdle(TextureHandle.ApplyFrames(0, 0, 4, 1, getSprite()));
			setExplosion(TextureHandle.ApplyFrames(0, 1, 4, 2, getSprite()));
		}

	},
	
	PurpleBat(160, 120, 60, 50, getPath()+"purplebat_right.png") { //$NON-NLS-1$

		@Override
		public void load() {
			setIdle(TextureHandle.ApplyFrames(0, 0, 3, 1, getSprite()));
			setExplosion(TextureHandle.ApplyFrames(0, 1, 4, 2, getSprite()));
		}

	},
	
	SmallBats(200, 240, 100, 100, getPath()+"bats_right.png") { //$NON-NLS-1$

		@Override
		public void load() {
			setIdle(TextureHandle.ApplyFrames(0, 0, 4, 1, getSprite()));
			setExplosion(TextureHandle.ApplyFrames(0, 1, 4, 2, getSprite()));
		}

	},
	
	PurpleLaser(200, 80, 130, 40, getPath()+"laser_right.png") { //$NON-NLS-1$

		@Override
		public void load() {
			setIdle(TextureHandle.ApplyFrames(0, 0, 2, 1, getSprite()));
			setExplosion(TextureHandle.ApplyFrames(0, 1, 4, 2, getSprite()));
		}

	},
	
	BigBats(300, 175, 130, 80, getPath()+"batz_right.png") { //$NON-NLS-1$

		@Override
		public void load() {
			setAnimationSpeed(0.16f);
			setAnimationExplodeSpeed(0.1f);
			setIdle(TextureHandle.ApplyFrames(0, 0, 4, 1, getSprite()));
			setExplosion(TextureHandle.ApplyFrames(0, 1, 4, 2, getSprite()));
		}

	},
	
	Mine(200, 200, 145, 150, getPath()+"mine_right.png") { //$NON-NLS-1$

		@Override
		public void load() {
			setIdle(TextureHandle.ApplyFrames(0, 0, 4, 1, getSprite()));
			setExplosion(TextureHandle.ApplyFrames(0, 1, 5, 2, getSprite()));
			setExplodeSFX(GameSFX.Explode2.get());
		}

	},
	
	TNT(200, 200, 100, 90, getPath()+"tnt_right.png") { //$NON-NLS-1$

		@Override
		public void load() {
			setIdle(TextureHandle.ApplyFrames(0, 0, 7, 1, getSprite()));
			setExplosion(TextureHandle.ApplyFrames(0, 1, 6, 2, getSprite()));
			setExplodeSFX(GameSFX.Explode1.get());
		}

	},
	
	RPG(138, 105, 138, 105, getPath()+"rpg_right.png") { //$NON-NLS-1$

		@Override
		public void load() {
			setIdle(TextureHandle.ApplyFrames(0, 0, 4, 1, getSprite()));
			setExplosion(TextureHandle.ApplyFrames(0, 1, 6, 2, getSprite()));
			setExplodeSFX(GameSFX.Explode1.get());
		}

	},
	
	BlueEnergyWave(300, 100, 150, 80, getPath()+"energywave_right.png") { //$NON-NLS-1$
		
		@Override
		public void load() {
			setIdle(TextureHandle.ApplyFrames(0, 0, 4, 1, getSprite()));
			setExplosion(TextureHandle.ApplyFrames(0, 1, 4, 2, getSprite()));
		}
		
	},
	
	BlueDashBall(150, 100, 80, 80, getPath()+"dashball_right.png") { //$NON-NLS-1$
		
		@Override
		public void load() {
			setAnimationSpeed(0.1f);
			setIdle(TextureHandle.ApplyFrames(0, 0, 3, 1, getSprite()));
			setExplosion(TextureHandle.ApplyFrames(0, 1, 3, 2, getSprite()));
		}
		
	},
	
	RedEnergyBall(100, 67, 90, 45, getPath()+"energyball.png") { //$NON-NLS-1$
		
		@Override
		public void load() {
			setIdle(TextureHandle.ApplyFrames(0, 0, 4, 1, getSprite()));
			setExplosion(TextureHandle.ApplyFrames(0, 1, 4, 2, getSprite()));
			
		}

	};
	
	private final static String PATH = "projectiles/"; //$NON-NLS-1$
	
	private final Vec2 SIZE, BLOCK_SIZE;
	private final TextureRegion[][] sprite;
	
	private float animationSpeed;
	private float animationExplodeSpeed;
	private Sound explode_sfx;
	
	private TextureRegion[] idle, explosion;
	
	ProjectilesEnum(int width, int height, int blockW, int blockH, String sprite) {
		this.BLOCK_SIZE = new Vec2(blockW, blockH);
		this.SIZE = new Vec2(width, height);
		
		this.sprite = TextureHandle.TextureSplit(sprite, (int)getBlockSize().getX(), (int)getBlockSize().getY(), true, false);
		
		setAnimationSpeed(0.08f);
		setAnimationExplodeSpeed(0.08f);
		setExplodeSFX(GameSFX.Explode3.get());
		
		load();
	}
	
	public abstract void load();
	
	public static String getPath() {
		return PATH;
	}
	
	public Vec2 getSize() {
		return SIZE;
	}

	public Vec2 getBlockSize() {
		return BLOCK_SIZE;
	}

	public TextureRegion[][] getSprite() {
		return sprite;
	}
	
	public float getAnimationSpeed() {
		return animationSpeed;
	}
	public void setAnimationSpeed(float animationSpeed) {
		this.animationSpeed = animationSpeed;
	}

	public float getAnimationExplodeSpeed() {
		return animationExplodeSpeed;
	}
	public void setAnimationExplodeSpeed(float animationExplodeSpeed) {
		this.animationExplodeSpeed = animationExplodeSpeed;
	}

	public Sound getExplodeSFX() {
		return explode_sfx;
	}
	public void setExplodeSFX(Sound explode_sfx) {
		this.explode_sfx = explode_sfx;
	}

	public TextureRegion[] getIdle() {
		return idle;
	}
	public void setIdle(TextureRegion[] idle) {
		this.idle = idle;
	}

	public TextureRegion[] getExplosion() {
		return explosion;
	}
	public void setExplosion(TextureRegion[] explosion) {
		this.explosion = explosion;
	}

	public void update(Projectile p) {
		p.updateVelocities();
	}
	
	public Animation getAnimation(Projectile p) {
		return AnimationHandler.getAnimation(p.getData().getFlip(), idle, animationSpeed, Animation.PlayMode.LOOP_PINGPONG);
	}
	public Animation getExplodedAnimation(ExplodeProjectile p) {
		return AnimationHandler.getAnimation(p.getData().getFlip(), explosion, animationExplodeSpeed, Animation.PlayMode.NORMAL);
	}

	public void playExplode() {
		getExplodeSFX().play();
	}
	public void playExplode(float x) {
		SoundUtil.playStereo(getExplodeSFX(), x);
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
