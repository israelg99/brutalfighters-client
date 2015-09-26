package com.brutalfighters.game.player.fighters;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.math.Vec2;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.sound.SoundUtil;
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
			
			setSkill1Frames(getSprites(0,5,8,6));
			setSkill2Frames(getSprites(0,6,7,7));
			setSkill3Frames(getSprites(0,7,10,8));
			setSkill4Frames(getSprites(0,8,10,9));
			
			setStandFrame(getSprite(0,0));
			setJumpFrame(getSprite(3,0));
			
			TextureRegion[][] s1_fx = TextureHandle.TextureSplit(getFighterPath() + name().toLowerCase() + "_fx_right.png", 380, 80, true, false); //$NON-NLS-1$
			setSkill1FX(TextureHandle.ApplyFrames(0, 0, 1, 4, s1_fx));
			
			loadExtraSFX();
		}
		
		public void loadExtraSFX() {
			setPhoenixSFX(SoundUtil.getMusic(getPath() + "phoenix.wav", SoundUtil.getVolume(0.3f))); //$NON-NLS-1$
		}
		
		public TextureRegion[] getSkill1Frames() {
			return s1_frames;
		}
		private void setSkill1Frames(TextureRegion[] frames) {
			this.s1_frames = frames;
		}
		
		public TextureRegion[] getSkill2Frames() {
			return s1_frames;
		}
		private void setSkill2Frames(TextureRegion[] frames) {
			this.s2_frames = frames;
		}
		
		public TextureRegion[] getSkill3Frames() {
			return s3_frames;
		}
		private void setSkill3Frames(TextureRegion[] frames) {
			this.s3_frames = frames;
		}
		
		public TextureRegion[] getSkill4Frames() {
			return s4_frames;
		}
		private void setSkill4Frames(TextureRegion[] frames) {
			this.s4_frames = frames;
		}
		
		public TextureRegion[] getSkill1FX() {
			return s1_fx_frames;
		}
		private void setSkill1FX(TextureRegion[] frames) {
			this.s1_fx_frames = frames;
		}
		
		public Music getPhoenixSFX() {
			return phoenix_sfx;
		}
		private void setPhoenixSFX(Music frames) {
			this.phoenix_sfx = frames;
		}
		
	},
	
	Dusk() {
		
		private TextureRegion[] s1_frames, s2_frames, s3_frames, s4_frames, bats_frames, laser_frames;
		
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
			
			setSkill1Frames(getSprites(0,5,5,6));
			setSkill2Frames(getSprites(0,6,10,7));
			setSkill3Frames(getSprites(0,7,4,8));
			setSkill4Frames(getSprites(0,8,6,9));
			
			setStandFrame(getSprite(0,0));
			setJumpFrame(getSprite(3,0));
			
			TextureRegion[][] bats_fx = TextureHandle.TextureSplit(getFighterPath() + name().toLowerCase() + "_bats_right.png", 160, 80, true, false); //$NON-NLS-1$
			setBatsFrames(TextureHandle.ApplyFrames(0, 0, 4, 1, bats_fx));

			TextureRegion[][] laser_fx = TextureHandle.TextureSplit(getFighterPath() + name().toLowerCase() + "_laser_right.png", 160, 80, true, false); //$NON-NLS-1$
			setLaserFrames(TextureHandle.ApplyFrames(0, 0, 3, 1, laser_fx));
		}
		
		public TextureRegion[] getSkill1Frames() {
			return s1_frames;
		}
		private void setSkill1Frames(TextureRegion[] frames) {
			this.s1_frames = frames;
		}
		
		public TextureRegion[] getSkill2Frames() {
			return s1_frames;
		}
		private void setSkill2Frames(TextureRegion[] frames) {
			this.s2_frames = frames;
		}
		
		public TextureRegion[] getSkill3Frames() {
			return s3_frames;
		}
		private void setSkill3Frames(TextureRegion[] frames) {
			this.s3_frames = frames;
		}
		
		public TextureRegion[] getSkill4Frames() {
			return s4_frames;
		}
		private void setSkill4Frames(TextureRegion[] frames) {
			this.s4_frames = frames;
		}
		
		public TextureRegion[] getBatsFrames() {
			return bats_frames;
		}
		private void setBatsFrames(TextureRegion[] frames) {
			this.bats_frames = frames;
		}
		
		public TextureRegion[] getLaserFrames() {
			return laser_frames;
		}
		private void setLaserFrames(TextureRegion[] frames) {
			this.laser_frames = frames;
		}
		
	},
	
	Chip() {
		
		private TextureRegion[] s1_frames, s2_frames, s3_frames, s4_frames;
		
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
			
			setSkill1Frames(getSprites(0,5,11,6));
			setSkill2Frames(getSprites(0,6,8,7));
			setSkill3Frames(getSprites(0,7,6,8));
			setSkill4Frames(getSprites(0,8,5,9));
			
			setStandFrame(getSprite(0,0));
			setJumpFrame(getSprite(3,0));
		}
		
		public TextureRegion[] getSkill1Frames() {
			return s1_frames;
		}
		private void setSkill1Frames(TextureRegion[] frames) {
			this.s1_frames = frames;
		}
		
		public TextureRegion[] getSkill2Frames() {
			return s1_frames;
		}
		private void setSkill2Frames(TextureRegion[] frames) {
			this.s2_frames = frames;
		}
		
		public TextureRegion[] getSkill3Frames() {
			return s3_frames;
		}
		private void setSkill3Frames(TextureRegion[] frames) {
			this.s3_frames = frames;
		}
		
		public TextureRegion[] getSkill4Frames() {
			return s4_frames;
		}
		private void setSkill4Frames(TextureRegion[] frames) {
			this.s4_frames = frames;
		}
		
	},
	
	Surge() {
		
		private TextureRegion[] s1_frames, s2_frames, s3_frames, s4_frames, s2_fx_frames, s4_fx_frames;
		
		private Music lightning_sfx; // Music because Sound will run like 1000x times and Music has isPlaying() to stop it.
		
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
			
			setSkill1Frames(getSprites(0,5,6,6));
			setSkill2Frames(getSprites(0,6,8,7));
			setSkill3Frames(getSprites(0,7,8,8));
			setSkill4Frames(getSprites(0,8,13,9));
			
			setStandFrame(getSprite(0,0));
			setJumpFrame(getSprite(3,0));
			
			TextureRegion[][] s2_fx = TextureHandle.TextureSplit(getFighterPath() + name().toLowerCase() + "_fx_right.png", 160, 80, true, false); //$NON-NLS-1$
			setSkill2FX(TextureHandle.ApplyFrames(0, 0, 3, 1, s2_fx));

			TextureRegion[][] s4_fx = TextureHandle.TextureSplit(getFighterPath() + "lightning_right.png", 170, 415, true, false); //$NON-NLS-1$
			setSkill4FX(TextureHandle.ApplyFrames(0, 0, 4, 1, s4_fx));
			
			loadExtraSFX();
		}
		
		public void loadExtraSFX() {
			setLightningSFX(SoundUtil.getMusic(getPath() + "lightning.wav", SoundUtil.getVolume(0.3f))); //$NON-NLS-1$
		}
		
		public TextureRegion[] getSkill1Frames() {
			return s1_frames;
		}
		private void setSkill1Frames(TextureRegion[] frames) {
			this.s1_frames = frames;
		}
		
		public TextureRegion[] getSkill2Frames() {
			return s1_frames;
		}
		private void setSkill2Frames(TextureRegion[] frames) {
			this.s2_frames = frames;
		}
		
		public TextureRegion[] getSkill3Frames() {
			return s3_frames;
		}
		private void setSkill3Frames(TextureRegion[] frames) {
			this.s3_frames = frames;
		}
		
		public TextureRegion[] getSkill4Frames() {
			return s4_frames;
		}
		private void setSkill4Frames(TextureRegion[] frames) {
			this.s4_frames = frames;
		}
		
		public TextureRegion[] getSkill2FX() {
			return s2_fx_frames;
		}
		private void setSkill2FX(TextureRegion[] frames) {
			this.s2_fx_frames = frames;
		}
		
		public TextureRegion[] getSkill4FX() {
			return s4_fx_frames;
		}
		private void setSkill4FX(TextureRegion[] frames) {
			this.s4_fx_frames = frames;
		}
		
		public Music getLightningSFX() {
			return lightning_sfx;
		}
		private void setLightningSFX(Music frames) {
			this.lightning_sfx = frames;
		}
		
	},
	
	Lust() {
		
		/* Sprites */
		private TextureRegion[] s1_frames, s2_frames;
		private TextureRegion s4_frame;
		
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
			
			setSkill1Frames(getSprites(0,5,8,6));
			setSkill2Frames(getSprites(0,6,6,7));
			setSkill4Frame(getSprite(8,0));
			
			setStandFrame(getSprite(0,0));
			setJumpFrame(getSprite(3,0));
		}
		
		public TextureRegion[] getSkill1Frames() {
			return s1_frames;
		}
		private void setSkill1Frames(TextureRegion[] frames) {
			this.s1_frames = frames;
		}
		
		public TextureRegion[] getSkill2Frames() {
			return s1_frames;
		}
		private void setSkill2Frames(TextureRegion[] frames) {
			this.s2_frames = frames;
		}
		
		public TextureRegion getSkill4Frame() {
			return s4_frame;
		}
		private void setSkill4Frame(TextureRegion frame) {
			this.s4_frame = frame;
		}
	};
	
	/* Sprites */
	protected TextureRegion[][] sprite;
	protected Vec2 blockSize;
	protected Vec2 cols_rows;
	protected TextureRegion stand_frame, jump_frame;
	protected TextureRegion[] walk_frames, run_frames, aattack_frames, breath_frames, death_frames;
	
	/* SFX */
	protected Sound jumpSFX, AA_SFX[], deathSFX, skillSFX[];
	protected int AA_SFX_Length;
	
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
		loadSFX();
		loadSkillsSFX();
	}
	
	protected void initialLoad() {
		setSprite(TextureHandle.TextureSplit(getFighterSpritePath(), (int)getBlockSize().getX(), (int)getBlockSize().getY(), true, false));
		
		setColsRows(new Vec2(sprite[0].length, sprite.length));
	}
	
	protected void loadSkillsSFX() { // MAKE IT ABSTRACT, BECAUSE SOUNDS WILL BE GENERALIZED	
		setSkillSFX(new Sound[Fighter.getSkills()]);
		getSkillSFX()[0] = getSFX("skill1.wav"); //$NON-NLS-1$
		getSkillSFX()[1]  = getSFX("skill2.wav"); //$NON-NLS-1$
		getSkillSFX()[2]  = getSFX("skill3.wav"); //$NON-NLS-1$
		getSkillSFX()[3]  = getSFX("skill4.wav"); //$NON-NLS-1$
	}
	
	protected void loadSFX() {			
		setAA_SFX_Length(2);
		
		setJumpSFX(getSFX("jump.wav")); //$NON-NLS-1$
		
		setAA_SFX(new Sound[AA_SFX_Length]);
		getAA_SFX()[0] = getSFX("AA1.wav"); //$NON-NLS-1$
		getAA_SFX()[1] = getSFX("AA2.wav"); //$NON-NLS-1$
		
		setDeathSFX(getSFX("death1.wav")); //$NON-NLS-1$
	}
	
	public static String getPath() {
		return "fighters/"; //$NON-NLS-1$
	}
	public static String getSFXPath() {
		return "fighters/sfx/"; //$NON-NLS-1$
	}
	public static Sound getSFX(String sfx) {
		return SoundUtil.getSound(getSFXPath() + sfx);
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

	public final Sound getJumpSFX() {
		return jumpSFX;
	}
	protected final void setJumpSFX(Sound jumpsfx) {
		this.jumpSFX = jumpsfx;
	}

	public final Sound[] getAA_SFX() {
		return AA_SFX;
	}
	protected final void setAA_SFX(Sound[] aASFX) {
		this.AA_SFX = aASFX;
	}

	public final Sound getDeathSFX() {
		return deathSFX;
	}
	protected final void setDeathSFX(Sound deathsfx) {
		this.deathSFX = deathsfx;
	}

	public final Sound[] getSkillSFX() {
		return skillSFX;
	}
	protected final void setSkillSFX(Sound[] skillsfx) {
		this.skillSFX = skillsfx;
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
	
	public final int getAA_SFX_Length() {
		return AA_SFX_Length;
	}
	protected final void setAA_SFX_Length(int aA_SFX_LENGTH) {
		this.AA_SFX_Length = aA_SFX_LENGTH;
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
