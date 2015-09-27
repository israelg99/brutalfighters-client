package com.brutalfighters.game.player.fighters;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.math.Vec2;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.utility.rendering.TextureHandle;

public enum FighterFactory {
	
	Blaze() {
		
		private TextureRegion[] s1_frames, s2_frames, s3_frames, s4_frames, s1_fx_frames;
		
		private Music phoenix_sfx; // Sound will run like 1000x times! in Music you have the isPlaying Method!
		
		@Override
		public Fighter getNew(PlayerData pdata) {
			return new Blaze(pdata);
		}

		@Override
		public void loadSprite() {
			setBreathFrames(getSprites(0,0,4,1));
			setWalkFrames(getSprites(0,1,4,2));
			setRunFrames(getSprites(0,2,3,3));
			setAAttackFrames(getSprites(0,4,13,5));
			setDeathFrames(getSprites(0,9,6,10));
			
			setStandFrame(getSprite(0,0));
			setJumpFrame(getSprite(3,0));
		}
		
	},
	
	Dusk() {
		
		@Override
		public Fighter getNew(PlayerData pdata) {
			return new Dusk(pdata);
		}

		@Override
		public void loadSprite() {
			setBreathFrames(getSprites(0,0,4,1));
			setWalkFrames(getSprites(0,1,3,2));
			setRunFrames(getSprites(0,2,3,3));
			setAAttackFrames(getSprites(0,4,8,5));
			setDeathFrames(getSprites(0,9,6,10));
			
			setStandFrame(getSprite(0,0));
			setJumpFrame(getSprite(3,0));
		}
		
	},
	
	Chip() {
		
		@Override
		public Fighter getNew(PlayerData pdata) {
			return new Chip(pdata);
		}

		@Override
		public void loadSprite() {
			setBreathFrames(getSprites(0,0,4,1));
			setWalkFrames(getSprites(0,1,4,2));
			setRunFrames(getSprites(0,2,3,3));
			setAAttackFrames(getSprites(0,4,7,5));
			setDeathFrames(getSprites(0,9,6,10));
			
			setStandFrame(getSprite(0,0));
			setJumpFrame(getSprite(3,0));
		}
		
		
		
	},
	
	Surge() {
		
		@Override
		public Fighter getNew(PlayerData pdata) {
			return new Surge(pdata);
		}

		@Override
		protected void loadSprite() {
			setBreathFrames(getSprites(0,0,4,1));
			setWalkFrames(getSprites(0,1,4,2));
			setRunFrames(getSprites(0,2,3,3));
			setAAttackFrames(getSprites(0,4,12,5));
			setDeathFrames(getSprites(0,9,6,10));
			
			setStandFrame(getSprite(0,0));
			setJumpFrame(getSprite(3,0));
		}
		
	},
	
	Lust() {
		
		@Override
		public Fighter getNew(PlayerData pdata) {
			return new Lust(pdata);
		}

		@Override
		public void loadSprite() {
			setBreathFrames(getSprites(0,0,4,1));
			setWalkFrames(getSprites(0,1,4,2));
			setRunFrames(getSprites(0,2,3,3));
			setAAttackFrames(getSprites(0,4,8,5));
			setDeathFrames(getSprites(0,9,6,10));
			
			setStandFrame(getSprite(0,0));
			setJumpFrame(getSprite(3,0));
		}
	};
	
	/* Sprites */
	protected TextureRegion[][] sprite;
	protected Vec2 blockSize;
	protected Vec2 cols_rows;
	protected TextureRegion stand_frame, jump_frame;
	protected TextureRegion[] walk_frames, run_frames, aattack_frames, breath_frames, death_frames;
	
	private FighterFactory(Vec2 BlockSize) {
		setBlockSize(BlockSize);
		
		load();
	}
	private FighterFactory() {
		this(new Vec2(80,80));
	}
	
	protected void load() {
		initialLoad();
		loadSprite();
	}
	
	protected void initialLoad() {
		setSprite(TextureHandle.TextureSplit(getFighterSpritePath(), (int)getBlockSize().getX(), (int)getBlockSize().getY(), true, false));
		
		setColsRows(new Vec2(sprite[0].length, sprite.length));
	}
	
	public static String getPath() {
		return "fighters/"; //$NON-NLS-1$
	}
	public final String getFighterPath() {
		return getPath() + name().toLowerCase() + "/"; //$NON-NLS-1$
	}
	public final String getFighterSpritePath() {
		return getFighterPath() + name().toLowerCase() + "_right.png"; //$NON-NLS-1$
	}
	
	public final TextureRegion[] getSprites(int sc, int sr, int fc, int fr) {
		return TextureHandle.ApplyFrames(sc, sr, fc, fr, getSprite());
	}
	public final TextureRegion getSprite(int i, int j) {
		return getSprite()[i][j];
	}
	
	public final Vec2 getBlockSize() {
		return blockSize;
	}
	protected final void setBlockSize(Vec2 blockSize) {
		this.blockSize = new Vec2(blockSize);
	}

	public final TextureRegion getStandFrame() {
		return stand_frame;
	}
	protected final void setStandFrame(TextureRegion standframe) {
		this.stand_frame = standframe;
	}

	public final TextureRegion getJumpFrame() {
		return jump_frame;
	}
	protected final void setJumpFrame(TextureRegion jumpframe) {
		this.jump_frame = jumpframe;
	}
	
	public final TextureRegion[][] getSprite() {
		return sprite;
	}
	protected final void setSprite(TextureRegion[][] sprite) {
		this.sprite = sprite;
	}
	
	protected final Vec2 getColsRows() {
		return cols_rows;
	}
	protected final void setColsRows(Vec2 cols_rows) {
		this.cols_rows = new Vec2(cols_rows);
	}
	public final float getCOLS() {
		return getColsRows().getX();
	}
	protected final void setCOLS(float cols) {
		getColsRows().setX(cols);
	}
	public final float getROWS() {
		return getColsRows().getY();
	}
	protected final void setROWS(float rows) {
		getColsRows().setY(rows);
	}
	
	public final TextureRegion[] getWalkFrames() {
		return walk_frames;
	}
	protected final void setWalkFrames(TextureRegion[] walk_frames) {
		this.walk_frames = walk_frames;
	}
	
	public final TextureRegion[] getRunFrames() {
		return run_frames;
	}
	protected final void setRunFrames(TextureRegion[] run_frames) {
		this.run_frames = run_frames;
	}
	
	public final TextureRegion[] getAAttackFrames() {
		return aattack_frames;
	}
	protected final void setAAttackFrames(TextureRegion[] aattack_frames) {
		this.aattack_frames = aattack_frames;
	}
	
	public final TextureRegion[] getBreathFrames() {
		return breath_frames;
	}
	protected final void setBreathFrames(TextureRegion[] breath_frames) {
		this.breath_frames = breath_frames;
	}
	
	public final TextureRegion[] getDeathFrames() {
		return death_frames;
	}
	protected final void setDeathFrames(TextureRegion[] death_frames) {
		this.death_frames = death_frames;
	}
	
	public abstract Fighter getNew(PlayerData pdata);
	
	protected abstract void loadSprite();
	
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
