package com.brutalfighters.game.player.fighters;

import java.awt.Rectangle;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.buffs.Buff;
import com.brutalfighters.game.effects.particles.ParticleEffects;
import com.brutalfighters.game.effects.particles.ParticlesCollection;
import com.brutalfighters.game.effects.text.TextEffects;
import com.brutalfighters.game.effects.text.effects.TextBlood;
import com.brutalfighters.game.effects.text.effects.TextHeal;
import com.brutalfighters.game.flags.Flag;
import com.brutalfighters.game.map.GameMap;
import com.brutalfighters.game.math.Vec2;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.resources.Assets;
import com.brutalfighters.game.resources.Prefs;
import com.brutalfighters.game.sound.GameSFX;
import com.brutalfighters.game.sound.GameSFXManager;
import com.brutalfighters.game.sound.SoundUtil;
import com.brutalfighters.game.utility.CollisionDetection;
import com.brutalfighters.game.utility.GameMath;
import com.brutalfighters.game.utility.NextStep;
import com.brutalfighters.game.utility.ServerInfo;
import com.brutalfighters.game.utility.rendering.AnimationHandler;
import com.brutalfighters.game.utility.rendering.RenderUtility;
import com.brutalfighters.game.utility.rendering.TextureHandle;
import com.brutalfighters.game.utility.rendering.TexturePacker;
import com.brutalfighters.game.utility.rendering.TexturesPacker;

abstract public class Fighter {
	
	/* Finals */
	protected static final String PATH = "fighters/"; //$NON-NLS-1$
	protected static final float VOLUME = 0.5f;
	
	protected static final int GRAVITY_FORCE = 27;
	protected static final int FALLING_MOMENTUM = 9;
	
	protected static final int SKILLS = 4;

	/* Player */
	protected PlayerData player;

	protected Vec2 size;
	protected float changedHealth;
	
	protected Rectangle bounds;
	
	/* Timers */
	protected float deathTimer;
	protected float[] s_timers;
	
	/* Camera */
	protected boolean[] cameraLock; // 0 = Y  1 = X
	
	/* Bleeding */
	protected Vec2 bleedingCD; // max = 1000
	
	/* Sprites */
	protected final TextureRegion[][] SPRITE;
	protected final Vec2 COLS_ROWS;
	protected TextureRegion stand_frame, jump_frame;
	protected TextureRegion[] walk_frames, run_frames, aattack_frames, breath_frames, death_frames;
	
	/* AA */
	protected NextStep toNextAA;
	protected int AA_SFX_LENGTH;
	protected float AA_SFX_DELAY;
	
	/* SFX */
	protected Sound jumpSFX, AA_SFX[], deathSFX, skillSFX[];
	
	/* Steps */
	protected int steps;
	protected NextStep toNextStep;
	protected float timeWalkSteps;
	protected float timeRunSteps;
	
	/* Skills */
	protected boolean[] skillPlayed;
	
	protected Fighter(PlayerData pdata, Vec2 size, int BLOCK_WIDTH, int BLOCK_HEIGHT) {
		assignPlayer(pdata);
		
		resetSteps();
		toNextStep = new NextStep();
		toNextAA = new NextStep();
		
		resetDeathTimer();
		
		resetSkillsTimers();
		resetSkillsPlayed();
		
		resetCameraLock();
		
		resetBleedingCD();
		
		setBounds(new Rectangle());
		
		setSize(size);
		
		setTimeWalkSteps(0.5f);
		setTimeRunSteps(0.3f);
		
		this.SPRITE = TextureHandle.TextureSplit(getPath() + getPlayer().getName() + "_right.png", BLOCK_WIDTH, BLOCK_HEIGHT, true, false); //$NON-NLS-1$
		
		this.COLS_ROWS = new Vec2(SPRITE[0].length, SPRITE.length);
		
		loadSprite();
		loadBasicSFX();
		loadSFX();
	}
	protected Fighter(PlayerData pdata, Vec2 size) {
		this(pdata, size, 80, 80);
	}
	protected Fighter(PlayerData pdata) {
		this(pdata, new Vec2(200,135), 80, 80);
	}
	
	public final static float getVolume() {
		return VOLUME;
	}
	public final static int getGravityForce() {
		return GRAVITY_FORCE;
	}
	public final static int getFallingMomentum() {
		return FALLING_MOMENTUM;
	}
	public final static int getSkills() {
		return SKILLS;
	}
	
	public final TextureRegion[][] getSPRITE() {
		return SPRITE;
	}

	public final float getCOLS() {
		return COLS_ROWS.getX();
	}
	public final float getROWS() {
		return COLS_ROWS.getY();
	}

	public final PlayerData getPlayer() {
		return player;
	}
	public final void setPlayer(PlayerData player) {
		this.player = player;
	}
	public final void assignPlayer(PlayerData p) {

		compareUpdate(p);
		
		float posY = (p.getVel().getY() == 0 || p.onGround() || p.isCollidingTop()) ? p.getPos().getY() : getPlayer().getPos().getY();
		float posX = (p.getVel().getX() == 0 || p.isCollidingLeft() || p.isCollidingRight()) ? p.getPos().getX() : getPlayer().getPos().getX();
		
		setPlayer(p);
		if(p.isExtrapolating()) {
			getPlayer().getPos().setX(posX);
			getPlayer().getPos().setY(posY);
		}
		
		if(getPlayer().isRight() && getPlayer().isLeft()) {
			getPlayer().setRight(false);
			getPlayer().setLeft(false);
		}
	}
	
	public final float changedHealth() {
		return changedHealth;
	}
	public final void changeHealth(float changedHealth) {
		this.changedHealth = changedHealth;
	}

	public final Vec2 getSize() {
		return size;
	}
	public final void setSize(Vec2 size) {
		this.size = new Vec2(size);
	}

	public final Rectangle getBounds() {
		CollisionDetection.setBounds(this.bounds, "both", getPlayer().getPos().getX(), getPlayer().getPos().getY(), getPlayer().getSize().getX(), getPlayer().getSize().getY()); //$NON-NLS-1$
		return this.bounds;
	}
	public final void setBounds(Rectangle bounds) {
		this.bounds = new Rectangle(bounds);
	}

	public final float getDeathTimer() {
		return deathTimer;
	}
	public final void setDeathTimer(float deathTimer) {
		this.deathTimer = deathTimer;
	}
	public final void addDeathTime() {
		this.deathTimer+=GameTime.getStateTime();
	}
	public final void resetDeathTimer() {
		this.setDeathTimer(0.0f);
	}

	public final float getSkillTimer(int index) {
		return s_timers[index];
	}
	public final void setSkillsTimers(float[] s_timers) {
		this.s_timers = s_timers;
	}
	public final void setSkillTimer(int index, float s_timer) {
		this.s_timers[index] = s_timer;
	}
	public final void resetSkillsTimers() {
		this.setSkillsTimers(new float[getSkills()]);
	}
	public final void resetSkillTimers(int index) {
		this.setSkillTimer(index, 0);
	}
	public final void addSkillTimer(int index) {
		this.s_timers[index] += Gdx.graphics.getDeltaTime();
	}

	public final boolean getCameraLockX() {
		return cameraLock[0];
	}
	public final boolean getCameraLockY() {
		return cameraLock[1];
	}
	public final void setCameraLockX(boolean cameraLock) {
		this.cameraLock[0] = cameraLock;
	}
	public final void setCameraLockY(boolean cameraLock) {
		this.cameraLock[1] = cameraLock;
	}
	public final void resetCameraLock() {
		Arrays.fill(this.cameraLock, false);
	}

	public final Vec2 getBleedingCD() {
		return bleedingCD;
	}
	public final void setBleedingCD(Vec2 bleedingCD) {
		this.bleedingCD = new Vec2(bleedingCD);
	}
	public final void resetBleedingCD() {
		getBleedingCD().setX(getBleedingCD().getY());
	}
	public final void subBleedingCD() {
		getBleedingCD().subX(getBleedingCD().getY()*Gdx.graphics.getDeltaTime());
	}

	public final TextureRegion getStandFrame() {
		return stand_frame;
	}
	public final void setStandFrame(TextureRegion stand_frame) {
		this.stand_frame = stand_frame;
	}

	public final TextureRegion getJumpFrame() {
		return jump_frame;
	}
	public final void setJumpFrame(TextureRegion jump_frame) {
		this.jump_frame = jump_frame;
	}

	public final int getAA_SFX_Length() {
		return AA_SFX_LENGTH;
	}
	public final void setAA_SFX_Length(int aASFX_LENGTH) {
		AA_SFX_LENGTH = aASFX_LENGTH;
	}

	public final float getAA_SFX_Delay() {
		return AA_SFX_DELAY;
	}
	public final void setAA_SFX_Delay(float aASFX_DELAY) {
		AA_SFX_DELAY = aASFX_DELAY;
	}

	public final Sound getJumpSFX() {
		return jumpSFX;
	}
	public final void setJumpSFX(Sound jumpSFX) {
		this.jumpSFX = jumpSFX;
	}

	public final Sound[] getAA_SFX() {
		return AA_SFX;
	}
	public final void setAA_SFX(Sound[] aASFX) {
		AA_SFX = aASFX;
	}

	public final Sound getDeathSFX() {
		return deathSFX;
	}
	public final void setDeathSFX(Sound deathSFX) {
		this.deathSFX = deathSFX;
	}

	public final Sound[] getSkillSFX() {
		return skillSFX;
	}
	public final void setSkillSFX(Sound[] skillSFX) {
		this.skillSFX = skillSFX;
	}

	public final int getSteps() {
		return steps;
	}
	public final void setSteps(int steps) {
		this.steps = steps;
	}
	public final void resetSteps() {
		setSteps(1);
	}

	public final NextStep getNextStep() {
		return toNextStep;
	}
	
	public final NextStep getNextAA() {
		return toNextAA;
	}

	public final float getTimeWalkSteps() {
		return timeWalkSteps;
	}
	public final void setTimeWalkSteps(float timeWalkSteps) {
		this.timeWalkSteps = timeWalkSteps;
	}

	public final float getTimeRunSteps() {
		return timeRunSteps;
	}
	public final void setTimeRunSteps(float timeRunSteps) {
		this.timeRunSteps = timeRunSteps;
	}

	public final boolean isSkillPlayed(int index) {
		return this.skillPlayed[index];
	}
	public final void areSkillsPlayed(boolean[] skillsPlayed) {
		this.skillPlayed = skillsPlayed;
	}
	public final void isSkillPlayed(int index, boolean skillPlayed) {
		this.skillPlayed[index] = skillPlayed;
	}
	public final void resetSkillPlayed(int index) {
		this.isSkillPlayed(index, false);
	}
	public final void resetSkillsPlayed() {
		Arrays.fill(this.skillPlayed, false);
	}

	protected abstract void loadSprite();
	
	protected abstract TexturesPacker drawBreath();
	protected abstract TexturesPacker drawWalking();
	protected abstract TexturesPacker drawRunning();
	
	protected abstract TexturesPacker drawAAttack();
	
	protected abstract TexturesPacker drawSkill1();
	protected abstract TexturesPacker drawSkill2();
	protected abstract TexturesPacker drawSkill3();
	protected abstract TexturesPacker drawSkill4();
	
	protected abstract TexturesPacker drawDead();
	
	protected abstract void applyRunningParticles();
	
	protected final void loadSFX() {			
		skillSFX = new Sound[SKILLS];
		skillSFX[0] = SoundUtil.getSound(getPath() + "skill1.wav"); //$NON-NLS-1$
		skillSFX[1] = SoundUtil.getSound(getPath() + "skill2.wav"); //$NON-NLS-1$
		skillSFX[2] = SoundUtil.getSound(getPath() + "skill3.wav"); //$NON-NLS-1$
		skillSFX[3] = SoundUtil.getSound(getPath() + "skill4.wav"); //$NON-NLS-1$
	}
	
	protected final void loadBasicSFX() {			
		AA_SFX_LENGTH = 2;
		AA_SFX_DELAY = 0.5f;
		
		jumpSFX = SoundUtil.getSound(PATH + "sfx/jump.wav"); //$NON-NLS-1$
		
		AA_SFX = new Sound[AA_SFX_LENGTH];
		AA_SFX[0] = SoundUtil.getSound(PATH + "sfx/AA1.wav"); //$NON-NLS-1$
		AA_SFX[1] = SoundUtil.getSound(PATH + "sfx/AA2.wav"); //$NON-NLS-1$
		
		deathSFX = SoundUtil.getSound(PATH + "sfx/death1.wav"); //$NON-NLS-1$
	}

	protected final void playSkill(int i, float x) {
		SoundUtil.playStereo(skillSFX[i], x);
	}
	
	protected final void playJump(float x) {
		SoundUtil.playStereo(jumpSFX, x);
		
	}
	protected final void playAA(int i, float x) {
		SoundUtil.playStereo(AA_SFX[i], x);
		
	}
	protected final void playDeath(float x) {
		SoundUtil.playStereo(deathSFX, x);
		
	}
	
	protected final TexturesPacker drawJump() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.returnAfterCheck(getPlayer().getFlip(), jump_frame), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}
	protected final TexturesPacker drawStand() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.returnAfterCheck(getPlayer().getFlip(), stand_frame), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}
	
	protected final void playJumpStep() {
		if(getPlayer().isJump() && getPlayer().onGround() && !getPlayer().isCollidingTop()) { // GameSFXManager when you hit the surface after the jump and getting ready for the next, good for design!
			moveStepsSFX(timeWalkSteps);
			//return drawJump(); // 	 TO LOOK IF THE JUMP IS SMOOTH ONLY TESTING!@!!!@@@!@!@!@!
		}
	}
	
	protected final TexturesPacker drawChamp() {

		playJumpStep();
		
		applyParticles();
		
		if(getPlayer().isDead()) {
			return drawDead();
		} else if(getPlayer().isSkilling()) { // Skills GameSFXManager inside of the skill functions below
			if(getPlayer().isSkill1()) {
				return drawSkill1(); // We cannot lock the camera here, we need it to be unlocked.
			} else if(getPlayer().isSkill2()) {
				return drawSkill2();
			} else if(getPlayer().isSkill3()) {
				return drawSkill3();
			} else if(getPlayer().isSkill4()) {
				return drawSkill4();
			}
		} else {
			resetSkillsPlayed();
			if(!getPlayer().onGround()) {
				getNextStep().reset();
				resetSteps();
				return drawJump();
			} else if(getPlayer().hasControl()) {
				if(getPlayer().isWalking()) {
					if(getPlayer().isRunning()) {
						moveStepsSFX(timeRunSteps);
						return drawRunning();
					}
					moveStepsSFX(timeWalkSteps);
					return drawWalking();
				} else if(getPlayer().isAAttack()) {
					playAA();
					return drawAAttack();
				}
			}
		}
		
		return drawBreath();
	}
	
	protected final void moveStepsSFX(float delay) {
		getNextStep().sub();
		if(getNextStep().isTime()) {
			playStepType();
			getNextStep().set(delay);
		}
	}
	
	protected final void applyParticles() {
		applyRunningParticles();
		applyHealthParticles();
	}
	
	protected final void playSkill(int i, int ms) {
		if(Prefs.isVolume()) {
			if(getSkillTimer(i) >= (float)ms/1000) {
				if(!isSkillPlayed(i)) {
					playSkill(i, getPlayer().getPos().getX());
					isSkillPlayed(i, true);
				}
			} else {
				isSkillPlayed(i, false);
			}
		}
	}
	
	protected final void playAA() {
		getNextAA().sub();
		if(getNextAA().isTime()) {
			setSteps(getSteps() < getAA_SFX_Length() ? getSteps()+1 : 1);
			playAA(getSteps()-1, getPlayer().getPos().getX()); // Because its an array here, not a string that you put into hashmap
			getNextAA().set(getAA_SFX_Delay());
		}
	}
	
	protected final void applyHealthParticles() {
		if(changedHealth() < 0) {
			TextEffects.add(new TextBlood(this)); 
			if(changedHealth() <= -200) {
				ParticleEffects.add(ParticlesCollection.BloodSplat_Big, getPlayer().getPos().getX(), getPlayer().getPos().getY()-getPlayer().getSize().getY()/6, false); 
			} else if(GameMath.nextInt(1, 2) > 1) {
				ParticleEffects.add(ParticlesCollection.BloodSplat_Left, getPlayer().getPos().getX(), getPlayer().getPos().getY()-getPlayer().getSize().getY()/6, false); 
			} else {
				ParticleEffects.add(ParticlesCollection.BloodSplat_Right, getPlayer().getPos().getX(), getPlayer().getPos().getY()-getPlayer().getSize().getY()/6, false); 
			}
		} else if(changedHealth() > 0) {
			TextEffects.add(new TextHeal(this)); 
			ParticleEffects.add(ParticlesCollection.HealthRegen, getPlayer().getPos().getX(), getPlayer().getPos().getY()-getPlayer().getSize().getY()/6, false); 
		}
	}
	
	protected final void applyRunningParticles(int trailNumber) {
		if(getPlayer().isRunning() && !getPlayer().isSkilling() && getPlayer().hasControl() && getPlayer().getVel().getX() != 0 && GameMath.nextBoolean(70)) {
			ParticleEffects.add("Trail"+trailNumber, getPlayer().getPos().getX(), getPlayer().getPos().getY(), true); //$NON-NLS-1$
		}
	}
	
	protected final static String getPath(String name) {
		return "champs/" + name + "/"; //$NON-NLS-1$ //$NON-NLS-2$
	}
	protected final String getPath() {
		return getPath(getPlayer().getName());
	}
	
	protected final float syncedVelX() {
		return ServerInfo.syncServer(getPlayer().getVel().getX());
	}
	protected final float syncedVelY() {
		return ServerInfo.syncServer(getPlayer().getVel().getY());
	}
	
	protected final void applySyncedVelX() {
		getPlayer().getPos().addX(syncedVelX());
	}
	protected final void applySyncedVelY() {
		getPlayer().getPos().addY(syncedVelY());
	}

	protected final void applyGravity() {
		if(getPlayer().getVel().getY() <= 0) { // Because we don't want to lower the jump height too, remember, in the server the jump height is not affected by gravitation.
			getPlayer().getVel().setY(getPlayer().getVel().getY() - ServerInfo.syncServer(getFallingMomentum()) < -getGravityForce() ? -getGravityForce() : getPlayer().getVel().getY() - ServerInfo.syncServer(getFallingMomentum()));
		}
	}
	
	protected final void alignGround() {
		getPlayer().getPos().setY((int)(getPlayer().getPos().getY() / Assets.map.getTileHeight()) * Assets.map.getTileHeight() + getPlayer().getSize().getY()/2 - 1);
	}
	
	protected final TexturesPacker getSprites() {
		return drawChamp();
	}

	public final void draw() { // If we want we can add the variables width and height to the player data and the size of the player will be dynamic.
		applyCameras(); // Reset Cameras
		
		TexturesPacker sprites = getSprites();
		
		for(int i = 0; i < sprites.length(); i++) {
			TexturePacker sprite = sprites.get(i);
			Render.getSpriteBatch().draw(sprite.getTexture(), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		}
		
		Render.getSpriteBatch().flush();
		
		Render.getSpriteBatch().end();
		
		drawHPBar();
		drawMANABar();
				
		Render.getSpriteBatch().begin();
		
		applyBuffs();
		
		endUpdate();
	}

	public final void update() {
		if(!getPlayer().isDead()) {
			if(getPlayer().isExtrapolating()) { // It has to be here, otherwise after you teleport you will have extrapolation issues, because the client will get the full position + extrapolation which will be too much and overflow the actual position.
				applyPosition();
			}
			if(!getPlayer().isSkilling() && getPlayer().hasControl()) {
				applyTimers();
			}
			applyBleeding(); // Not in champion enum because it's the same for all fighters at the moment.
			updateFlag();
		} else {
			applyBodyGravity();
		}
	}
	
	protected final boolean updateFlag() {
		if(getPlayer().isHoldingFlag()) {
			
			Flag flag = Assets.flags.getEnemyFlag(getPlayer().getTeam());
			
			float pad = getPlayer().getSize().getX()/3; // IT DEPENDS ON THE PLAYER, THEREFORE STATIC FIELD IS NOT VIABLE!
			
			if(getPlayer().facingLeft()) {
				flag.flip = "right"; //$NON-NLS-1$
			} else {
				pad = -pad;
				flag.flip = "left"; //$NON-NLS-1$
			}
			
			flag.posx = getPlayer().getPos().getX() + pad;
			flag.posy = getPlayer().getPos().getY() + getPlayer().getSize().getY()/3 + 15;
			
			return true;
		}
		return false;
	}
	
	protected final void drawHPBar() {
		if(getPlayer().getTeam() == Assets.player.getPlayer().getTeam()) {
			HUD.drawHPBar(getPlayer().getPos().getX(), getPlayer().getPos().getY(), getPlayer().getHP().getX(), getPlayer().getHP().getY(), Color.GREEN);
		} else {
			HUD.drawHPBar(getPlayer().getPos().getX(), getPlayer().getPos().getY(), getPlayer().getHP().getX(), getPlayer().getHP().getY(), Color.RED);
		}
	}
	
	protected final void drawMANABar() {
		HUD.drawMANABar(getPlayer().getPos().getX(), getPlayer().getPos().getY(), getPlayer().getMana().getX(), getPlayer().getMana().getY(), Color.BLUE);
	}

	protected final void applyBuffs() {		
		for(int i = 0; i < getPlayer().getBuffs().length; i++) {
			Buff.valueOf(getPlayer().getBuffs()[i].name).draw(getPlayer(), getPlayer().getBuffs()[i]);
		}
	}

	protected final void applyBleeding() {
		if(getPlayer().getHP().getX() < getPlayer().getHP().getY()/4) {
			if(getBleedingCD().getX() <= 0) {
				ParticleEffects.add(ParticlesCollection.Bleeding, getPlayer().getPos().getX(), getPlayer().getPos().getY(), false); 
				resetBleedingCD();
			} else {
				subBleedingCD();
			}
		}
	}

	protected final void applyCameras() {
		resetCameraLock();
	}

	protected final void compareUpdate(PlayerData newP) {
		changeHealth(newP.getHP().getX() - getPlayer().getHP().getX());
		if(newP.isJump() && !getPlayer().isJump()) {
			playJump(newP.getPos().getX());
		}
		
		if(newP.isDead() && !getPlayer().isDead()) {
			playDeath(newP.getPos().getX());
			if(newP.getTeam() != Assets.player.getPlayer().getTeam()) {
				Assets.killsCounter.enemyKilled();
			}
		}
		
		if(!getPlayer().isJump() && getPlayer().onGround() && newP.isJump() && !newP.onGround()) {
			ParticleEffects.add(ParticlesCollection.JumpDash, getPlayer().getPos().getX(), getPlayer().getPos().getY() + getPlayer().getBot(), true); 
		}
	}
	
	protected final void endUpdate() {
		changeHealth(0);
	}
	
	protected final void applyBodyGravity() {
		if(getPlayer().onGround()) {
			getPlayer().getVel().resetY();
			alignGround();
		} else {
			applyGravity();
			applySyncedVelY();
		}
	}

	protected final void applyPosition() {
		if((getPlayer().onGround() || collidesBot()) && getPlayer().getVel().getY() <= 0) {
			getPlayer().getVel().resetY();
			getPlayer().isOnGround(true);
			alignGround();
		} else {
			applyGravity();
		}
		
		// Y AXIS
		if(getPlayer().getVel().getY() != 0 && ((getPlayer().getVel().getY() > 0 && !getPlayer().isCollidingTop())
						|| (getPlayer().getVel().getY() < 0 && !getPlayer().isCollidingBot()))) {
			applySyncedVelY();
		}
		
		// X AXIS
		if(getPlayer().getVel().getX() != 0) {
			if((getPlayer().getVel().getX() > 0 && !getPlayer().isCollidingRight())
					|| getPlayer().getVel().getX() < 0 && !getPlayer().isCollidingLeft()) {
				applySyncedVelX();
			}
		}
	}
	
	protected final void playStepType() {
		if(checkFootStepType("grass")) { //$NON-NLS-1$
			applyFootStepType(GameSFXManager.getGrassStepLength(), "GrassStep", ParticlesCollection.Step_Grass); //$NON-NLS-1$
		} else if(checkFootStepType("dirt")) { //$NON-NLS-1$
			applyFootStepType(GameSFXManager.getDirtStepLength(), "DirtStep", ParticlesCollection.Step_Dirt); //$NON-NLS-1$
		} else if(checkFootStepType("rock")) { //$NON-NLS-1$
			applyFootStepType(GameSFXManager.getRockStepLength(), "RockStep", ParticlesCollection.Step_Rock); //$NON-NLS-1$
		} else if(checkFootStepType("ice")) { //$NON-NLS-1$
			applyFootStepType(GameSFXManager.getIceStepLength(), "SnowStep", ParticlesCollection.Step_Snow); //$NON-NLS-1$
		}

	}
	
	protected final boolean playCheckStepType(String step, int stepLength, String SFX, ParticlesCollection particles) {
		if(checkFootStepType(step)) {
			applyFootStepType(stepLength, SFX, particles);
			return true;
		}
		return false;
	}
	protected final boolean checkFootStepType(String step) {
		return GameMap.hasProperty("step", step, getCellOn()); //$NON-NLS-1$
	}
	protected final void applyFootStepType(int stepLength, String SFX, ParticlesCollection particles) {
		this.steps = this.steps < stepLength ? this.steps + 1 : 1;
		GameSFXManager.playStereo(GameSFX.valueOf(SFX + this.steps), getPlayer().getPos().getX());
		ParticleEffects.add(particles, getPlayer().getPos().getX(), getPlayer().getPos().getY() + getPlayer().getBot(), true); 
	}
	
	protected final void applyTimers() {
		resetDeathTimer();	
		resetSkillsTimers();
	}
	
	public final float CenterFX_X(float width, float fx_width) {
		return RenderUtility.CenterFX_X(getPlayer().facingRight(), getPlayer().getPos().getX(), width, fx_width);
	}
	
	protected final Rectangle getVelocityBounds(boolean velx, boolean vely) {
		Rectangle bounds = getBounds();
		bounds.x += velx ? getPlayer().getVel().getX() : 0;
		bounds.y += vely ? getPlayer().getVel().getY() : 0;
		return bounds;
	}
	
	protected final boolean collidesBot() {
		// BOT!
		return Assets.map.intersectsSurroundXBoth("top", getPlayer().getPos().getX(), getPlayer().getPos().getY()+getPlayer().getBot()+syncedVelY(), getVelocityBounds(false, true)) || getPlayer().getPos().getY() + getPlayer().getVel().getY() + getPlayer().getBot() < Assets.map.getBotBoundary(); //$NON-NLS-1$
	}
	protected final boolean collidesLeft() {
		// LEFT!
		return Assets.map.intersectsSurroundY(getPlayer().getPos().getX()+getPlayer().getLeft()+syncedVelX(), getPlayer().getPos().getY(), getVelocityBounds(true, false)) || getPlayer().getPos().getX() + getPlayer().getVel().getX() + getPlayer().getLeft() < Assets.map.getLeftBoundary();
	}
	protected final boolean collidesRight() {
		// RIGHT!
		return Assets.map.intersectsSurroundY(getPlayer().getPos().getX()+getPlayer().getRight()+syncedVelX(), getPlayer().getPos().getY(), getVelocityBounds(true, false)) || getPlayer().getPos().getX() + getPlayer().getVel().getX() + getPlayer().getRight() > Assets.map.getRightBoundary();
	}
	protected final boolean collidesTop() {
		// TOP!
		return Assets.map.intersectsSurroundX(getPlayer().getPos().getX(), getPlayer().getPos().getY()+getPlayer().getTop()+syncedVelY(), getVelocityBounds(false, true)) || getPlayer().getPos().getY() + getPlayer().getVel().getY() + getPlayer().getTop() > Assets.map.getTopBoundary();
	}
	
	protected final Cell getCellOn() {
		return Assets.map.getCell(getPlayer().getPos().getX(), getPlayer().getPos().getY() + getPlayer().getBot());
	}
	
	protected final boolean intersects(Rectangle rect) {
		return getBounds().intersects(rect);
	}
	protected final boolean intersects(Fighter p) {
		return getBounds().intersects(p.getBounds());
	}
}
