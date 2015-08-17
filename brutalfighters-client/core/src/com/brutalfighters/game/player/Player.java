package com.brutalfighters.game.player;

import java.awt.Rectangle;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.buffs.Buff;
import com.brutalfighters.game.buffs.BuffData;
import com.brutalfighters.game.effects.particles.ParticleEffects;
import com.brutalfighters.game.effects.particles.ParticlesCollection;
import com.brutalfighters.game.map.GameMap;
import com.brutalfighters.game.resources.Assets;
import com.brutalfighters.game.sound.GameSFX;
import com.brutalfighters.game.sound.GameSFXManager;
import com.brutalfighters.game.utility.ServerInfo;
import com.brutalfighters.game.utility.rendering.TexturePacker;
import com.brutalfighters.game.utility.rendering.TexturesPacker;

public class Player {
	
	public static final int gravityForce = 27;
	public static final int fallingMomentum = 9;
	
	private float BLEEDING_CD = 1000;
	
	private PlayerData p;
	private int changedHealth;
	public int steps;
	public float toNextStep;
	private float deathTimer;
	private float[] s_timers;
	private boolean[] skillPlayed;
	private boolean[] cameraLock; // 0 = Y  1 = X
	private float bleedingCD;
	
	private Rectangle bounds;

	public Player(PlayerData pdata) {
		this.p = pdata;
		this.steps = 0;
		this.toNextStep = 0;
		this.deathTimer = 0.0f;
		this.s_timers = new float[Champion.skills];
		this.skillPlayed = new boolean[Champion.skills];
		Arrays.fill(this.skillPlayed, false);
		this.cameraLock = new boolean[2];
		Arrays.fill(this.cameraLock, false);
		
		setBleedingCD(this.BLEEDING_CD);
		
		this.bounds = new Rectangle();
			
	}
	
	public PlayerData getPlayer() {
		return this.p;
	}
	
	public void newPlayer(PlayerData p) {
		this.p = p;
	}
	public void setPlayer(PlayerData p) {

		compareUpdate(p, this);
		
		float posY = (p.vely == 0 || p.onGround || p.collidesTop) ? p.posy : getPlayer().posy;
		float posX = (p.velx == 0 || p.collidesLeft || p.collidesRight) ? p.posx : getPlayer().posx;
		
		this.p = p;
		if(p.isExtrapolating) {
			getPlayer().posy = posY;
			getPlayer().posx = posX;
		}
		
		if(getPlayer().isRight && getPlayer().isLeft) {
			getPlayer().isRight = false;
			getPlayer().isLeft = false;
		}
	}
	
	public boolean isWalking() {
		return getPlayer().isRight || getPlayer().isLeft;
	}
	
	public float getX() {
		return getPlayer().posx;
	}
	public float getY() {
		return getPlayer().posy;
	}
	public float velX() {
		return getPlayer().velx;
	}
	public float velY() {
		return getPlayer().vely;
	}
	public float syncedVelX() {
		return ServerInfo.syncServer(getPlayer().velx);
	}
	public float syncedVelY() {
		return ServerInfo.syncServer(getPlayer().vely);
	}
	
	public int getWidth() {
		return getPlayer().width;
	}
	public int getHeight() {
		return getPlayer().height;
	}
	
	public boolean isLeft() {
		return getPlayer().flip.equals("left"); //$NON-NLS-1$
	}
	public boolean isRight() {
		return getPlayer().flip.equals("right"); //$NON-NLS-1$
	}
	
	public String name() {
		return getPlayer().name;
	}

	public boolean movingX() {
		return getPlayer().velx != 0;
	}
	public boolean movingY() {
		return getPlayer().vely != 0;
	}
	
	public void applyVelX() {
		getPlayer().posx += velX();
	}
	public void applyVelY() {
		getPlayer().posy += velY();
	}
	public void applySyncedVelX() {
		getPlayer().posx += syncedVelX();
	}
	public void applySyncedVelY() {
		getPlayer().posy += syncedVelY();
	}

	public void applyGravity() {
		if(getPlayer().vely <= 0) { // Because we don't want to lower the jump height too, remember, in the server the jump height is not affected by gravitation.
			getPlayer().vely = getPlayer().vely - ServerInfo.syncServer(fallingMomentum) < -gravityForce ? -gravityForce : getPlayer().vely - ServerInfo.syncServer(fallingMomentum);
		}
	}
	
	public boolean moveLeft() {
		return getPlayer().velx < 0 && !getPlayer().collidesLeft;
	}
	public boolean moveRight() {
		return getPlayer().velx > 0 && !getPlayer().collidesRight;
	}
	
	public void alignGround() {
		getPlayer().posy = (int)(getPlayer().posy / Assets.map.getTileHeight()) * Assets.map.getTileHeight() + getPlayer().height/2 - 1;
	}

	public void addDeathTime() {
		this.deathTimer+=GameTime.getStateTime();
	}
	public void resetDeathTime() {
		this.deathTimer=0.0f;
	}
	public float getDeathTime() {
		return this.deathTimer;
	}
	
	public float getSTimer(int index) {
		return this.s_timers[index];
	}
	public void resetSTimers() {
		this.s_timers = new float[Champion.skills];
	}
	public void addSTimer(int index) {
		this.s_timers[index] += Gdx.graphics.getDeltaTime();
	}
	
	public boolean isSkillPlayed(int index) {
		return this.skillPlayed[index];
	}
	public void isSkillPlayed(int index, boolean b) {
		this.skillPlayed[index] = b;
	}
	public void resetSkillPlayed() {
		this.skillPlayed = new boolean[Champion.skills];
	}
	
	public int changedHealth() {
		return this.changedHealth;
	}
	public void changeHealth(int hp) {
		this.changedHealth = hp;
	}
	
	public boolean isBum() {
		return getPlayer().hasControl && !getPlayer().isSkilling && getPlayer().onGround && getPlayer().velx == 0 && getPlayer().vely == 0;
	}
	
	public Champion getChamp() {
		return Champion.valueOf(name());
	}

	public void cameraLockY(boolean lock) {
		this.cameraLock[0] = lock;
	}
	public void cameraLockX(boolean lock) {
		this.cameraLock[1] = lock;
	}
	public boolean cameraLockY() {
		return this.cameraLock[0];
	}
	public boolean cameraLockX() {
		return this.cameraLock[1];
	}

	public float getBleedingCD() {
		return this.bleedingCD;
	}
	public void setBleedingCD(float bleedingCD) {
		this.bleedingCD = bleedingCD;
	}
	public void resetBleedingCD() {
		this.bleedingCD = this.BLEEDING_CD;
	}
	public void subBleedingCD() {
		this.bleedingCD-=this.BLEEDING_CD*Gdx.graphics.getDeltaTime();
	}
	
	public TexturesPacker getSprites() {
		return getChamp().drawChamp(this);
	}

	public void draw() { // If we want we can add the variables width and height to the player data and the size of the player will be dynamic.
		PlayerData p = getPlayer();
		
		applyCameras(); // Reset Cameras
		
		TexturesPacker sprites = getSprites();
		
		for(int i = 0; i < sprites.length(); i++) {
			TexturePacker sprite = sprites.get(i);
			Render.getSpriteBatch().draw(sprite.getTexture(), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		}
		
		Render.getSpriteBatch().flush();
		
		Render.getSpriteBatch().end();
		HUD.drawHPBar(p);
		HUD.drawMANABar(p);
				
		Render.getSpriteBatch().begin();
		
		applyBuffs();
		
		endUpdate();
	}

	public void update() {
		if(!getPlayer().isDead) {
			if(p.isExtrapolating) { // It has to be here, otherwise after you teleport you will have extrapolation issues, because the client will get the full position + extrapolation which will be too much and overflow the actual position.
				applyPosition();
			}
			if(!getPlayer().isSkilling && getPlayer().hasControl) {
				applyTimers();
			}
			applyBleeding(); // Not in champion enum because it's the same for all fighters at the moment.
		} else {
			applyBodyGravity();
		}
	}

	private void applyBuffs() {
		
		BuffData[] buffs = getPlayer().buffs;
		
		for(int i = 0; i < buffs.length; i++) {
			Buff.valueOf(buffs[i].name).draw(getPlayer(), buffs[i]);
		}
	}

	private void applyBleeding() {
		if(getPlayer().hp < getPlayer().maxhp/4) {
			if(getBleedingCD() <= 0) {
				ParticleEffects.add(ParticlesCollection.Bleeding, getX(), getY(), false); 
				resetBleedingCD();
			} else {
				subBleedingCD();
			}
		}
	}

	private void applyCameras() {
		cameraLockY(false);
		cameraLockX(false);
		
	}

	public static void compareUpdate(PlayerData newP, Player oldP) {
		PlayerData p = oldP.getPlayer();
		
		oldP.changeHealth(newP.hp - p.hp);
		if(newP.isJump && !p.isJump) {
			oldP.getChamp().playJump(newP.posx);
		}
		
		if(newP.isDead && !p.isDead) {
			oldP.getChamp().playDeath(newP.posx);
			if(newP.team != Assets.player.getPlayer().team) {
				Assets.killsCounter.enemyKilled();
			}
		}
		
		if(!p.isJump && p.onGround && newP.isJump && !newP.onGround) {
			ParticleEffects.add(ParticlesCollection.JumpDash, oldP.getX(), oldP.getY() + oldP.getBot(), true); 
		}
	}
	
	private void endUpdate() {
		changeHealth(0);
	}
	
	private void applyBodyGravity() {
		if(getPlayer().onGround) {
			getPlayer().vely = 0;
			alignGround();
		} else {
			applyGravity();
			applySyncedVelY();
		}
	}

	private void applyPosition() {
		if((getPlayer().onGround || collidesBot()) && velY() <= 0) {
			getPlayer().vely = 0;
			getPlayer().onGround = true;
			alignGround();
		} else {
			applyGravity();
		}
		
		// Y AXIS
		if(velY() != 0 && ((velY() > 0 && !getPlayer().collidesTop)
						|| (velY() < 0 && !getPlayer().collidesBot))) {
			applySyncedVelY();
		}
		
		// X AXIS
		if(velX() != 0) {
			if((velX() > 0 && !getPlayer().collidesRight)
					|| velX() < 0 && !getPlayer().collidesLeft) {
				applySyncedVelX();
			}
		}
	}
	
	public void playStepType() {
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
	
	private boolean playCheckStepType(String step, int stepLength, String SFX, ParticlesCollection particles) {
		if(checkFootStepType(step)) {
			applyFootStepType(stepLength, SFX, particles);
			return true;
		}
		return false;
	}
	private boolean checkFootStepType(String step) {
		return GameMap.hasProperty("step", step, getCellOn()); //$NON-NLS-1$
	}
	private void applyFootStepType(int stepLength, String SFX, ParticlesCollection particles) {
		this.steps = this.steps < stepLength ? this.steps + 1 : 1;
		GameSFXManager.playStereo(GameSFX.valueOf(SFX + this.steps), getX());
		ParticleEffects.add(particles, getX(), getY() + getBot(), true); 
	}
	
	private void applyTimers() {
		resetDeathTime();	
		resetSTimers();
	}
	
	// Boundary Methods
	public float getLeft() {
		return -getPlayer().width/2;
	}
	public float getRight() {
		return getPlayer().width/2;
	}
	public float getTop() {
		return getPlayer().height/2;
	}
	public float getBot() {
		return -getPlayer().height/2;
	}
	
	public Rectangle getVelocityBounds(boolean velx, boolean vely) {
		Rectangle bounds = getBounds();
		bounds.x += velx ? getPlayer().velx : 0;
		bounds.y += vely ? getPlayer().vely : 0;
		return bounds;
	}
	
	public boolean collidesBot() {
		// BOT!
		return Assets.map.intersectsSurroundXBoth("top", getPlayer().posx, getPlayer().posy+getBot()+syncedVelY(), getVelocityBounds(false, true)) || getPlayer().posy + getPlayer().vely + getBot() < Assets.map.getBotBoundary(); //$NON-NLS-1$
	}
	public boolean collidesLeft() {
		// LEFT!
		return Assets.map.intersectsSurroundY(getPlayer().posx+getLeft()+syncedVelX(), getPlayer().posy, getVelocityBounds(true, false)) || getPlayer().posx + getPlayer().velx + getLeft() < Assets.map.getLeftBoundary();
	}
	public boolean collidesRight() {
		// RIGHT!
		return Assets.map.intersectsSurroundY(getPlayer().posx+getRight()+syncedVelX(), getPlayer().posy, getVelocityBounds(true, false)) || getPlayer().posx + getPlayer().velx + getRight() > Assets.map.getRightBoundary();
	}
	public boolean collidesTop() {
		// TOP!
		return Assets.map.intersectsSurroundX(getPlayer().posx, getPlayer().posy+getTop()+syncedVelY(), getVelocityBounds(false, true)) || getPlayer().posy + getPlayer().vely + getTop() > Assets.map.getTopBoundary();
	}
	
	public Cell getCellOn() {
		return Assets.map.getCell(getPlayer().posx, getPlayer().posy + getBot());
	}
	
	public boolean isFacingCollision() {
		return (getPlayer().flip.equals("right") && getPlayer().collidesRight) || (getPlayer().flip.equals("left") && getPlayer().collidesLeft); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public Rectangle getBounds() {
		this.bounds.setBounds((int)getPlayer().posx-getPlayer().width/2, (int)getPlayer().posy-getPlayer().height/2, getPlayer().width, getPlayer().height);
		return this.bounds;
	}
	public boolean intersects(Rectangle rect) {
		return getBounds().intersects(rect);
	}
	public boolean intersects(Player p) {
		return getBounds().intersects(p.getBounds());
	}
}
