package com.brutalfighters.game.player;

import java.awt.Rectangle;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.basic.Update;
import com.brutalfighters.game.buffs.Buff;
import com.brutalfighters.game.buffs.BuffData;
import com.brutalfighters.game.effects.particles.Particles;
import com.brutalfighters.game.map.GameMap;
import com.brutalfighters.game.resources.Resources;
import com.brutalfighters.game.sound.SFX;
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

		float posY = (p.vely == 0 || p.collidesBot || p.collidesTop) ? p.posy : this.p.posy;
		float posX = (p.velx == 0 || p.collidesLeft || p.collidesRight) ? p.posx : this.p.posx;
		
		compareUpdate(p, this);
		
		this.p = p;
		this.p.posy = posY;
		this.p.posx = posX;
		
		if(this.p.isRight && this.p.isLeft) {
			this.p.isRight = false;
			this.p.isLeft = false;
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
		return ServerInfo.syncServer(this.p.velx);
	}
	public float syncedVelY() {
		return ServerInfo.syncServer(this.p.vely);
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
		return this.p.velx != 0;
	}
	public boolean movingY() {
		return this.p.vely != 0;
	}
	
	public void applyVelX() {
		this.p.posx += velX();
	}
	public void applyVelY() {
		this.p.posy += velY();
	}
	public void applySyncedVelX() {
		this.p.posx += syncedVelX();
	}
	public void applySyncedVelY() {
		this.p.posy += syncedVelY();
	}

	public void applyGravity() {
		if(this.p.vely <= 0) { // Because we don't want to lower the jump height too, remember, in the server the jump height is not affected by gravitation.
			this.p.vely = this.p.vely - ServerInfo.syncServer(fallingMomentum) < -gravityForce ? -gravityForce : this.p.vely - ServerInfo.syncServer(fallingMomentum);
		}
	}
	
	public boolean moveLeft() {
		return this.p.velx < 0 && !this.p.collidesLeft;
	}
	public boolean moveRight() {
		return this.p.velx > 0 && !this.p.collidesRight;
	}
	
	public void alignGround() {
		this.p.posy = (int)(this.p.posy / Resources.map.getTileHeight()) * Resources.map.getTileHeight() + this.p.height/2 - 1;
	}

	public void addDeathTime() {
		this.deathTimer+=Update.getStateTime();
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
		return this.p.hasControl && !this.p.isSkilling && this.p.onGround && this.p.velx == 0 && this.p.vely == 0;
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
			Render.batch.draw(sprite.getTexture(), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		}
		
		Render.batch.flush();
		
		Render.batch.end();
		HUD.drawHPBar(p);
		HUD.drawMANABar(p);
				
		Render.batch.begin();
		
		applyBuffs();
		
		endUpdate();
	}

	public void update() {
		if(!getPlayer().isDead) {
			applyPosition();
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
				Particles.add("bleeding", getX(), getY(), false); //$NON-NLS-1$
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
			if(newP.team != Resources.player.getPlayer().team) {
				Resources.killsCounter.enemyKilled();
			}
		}
		
		if(!p.isJump && p.onGround && newP.isJump && !newP.onGround) {
			Particles.add("jump_dash", oldP.getX(), oldP.getY() + oldP.getBot(), true); //$NON-NLS-1$
		}
	}
	
	private void endUpdate() {
		changeHealth(0);
	}
	
	private void applyBodyGravity() {
		if(this.p.onGround || collidesBot()) {
			this.p.vely = 0;
			alignGround();
		} else {
			applyGravity();
			applySyncedVelY();
		}
	}

	private void applyPosition() {
		if(this.p.onGround || collidesBot()) {
			this.p.vely = this.p.vely < 0 ? 0 : this.p.vely;
			this.p.onGround = true;
			alignGround();
		} else {
			applyGravity();
		}
		
		// Y AXIS
		if(this.p.vely != 0 && ((this.p.vely > 0 && !this.p.collidesTop)
						|| (this.p.vely < 0 && !this.p.collidesBot))) {
			applySyncedVelY();
		}
		
		// X AXIS
		if(this.p.velx != 0) {
			if(moveRight()) {
				applySyncedVelX();
				this.p.isRight = true;
			} else if(moveLeft()) {
				applySyncedVelX();
				this.p.isLeft = true;
			}
		}
	}
	
	public void playStepType() {
		if(!HUD.isMatchFinished()) {
			if(GameMap.hasProperty("grass", getCellOn())) { //$NON-NLS-1$
				this.steps = this.steps < SFX.ftGrassLength ? this.steps + 1 : 1;
				SFX.playStereo("ftGrass" + this.steps, getX()); //$NON-NLS-1$
				Particles.add("grassyWalk", getX(), getY() + getBot(), true); //$NON-NLS-1$
			} else if(GameMap.hasProperty("dirt", getCellOn())) { //$NON-NLS-1$
				this.steps = this.steps < SFX.ftDirtLength ? this.steps + 1 : 1;
				SFX.playStereo("ftDirt" + this.steps, getX()); //$NON-NLS-1$
				Particles.add("grassyWalk", getX(), getY() + getBot(), true); //$NON-NLS-1$
			}
		}
	}
	private void applyTimers() {
		resetDeathTime();	
		resetSTimers();
	}
	
	// Boundary Methods
	public float getLeft() {
		return -this.p.width/2;
	}
	public float getRight() {
		return this.p.width/2;
	}
	public float getTop() {
		return this.p.height/2;
	}
	public float getBot() {
		return -this.p.height/2;
	}
	
	public Rectangle getVelocityBounds(boolean velx, boolean vely) {
		Rectangle bounds = getBounds();
		bounds.x += velx ? this.p.velx : 0;
		bounds.y += vely ? this.p.vely : 0;
		return bounds;
	}
	
	public boolean collidesBot() {
		// BOT!
		return Resources.map.intersectsSurroundX(this.p.posx, this.p.posy+getBot()+this.p.vely, getVelocityBounds(false, true)) || this.p.posy + this.p.vely + getBot() < Resources.map.getBotBoundary();
	}
	public boolean collidesLeft() {
		// LEFT!
		return Resources.map.intersectsSurroundY(this.p.posx+getLeft()+this.p.velx, this.p.posy, getVelocityBounds(true, false)) ||this.p.posx + this.p.velx + getLeft() < Resources.map.getLeftBoundary();
	}
	public boolean collidesRight() {
		// RIGHT!
		return Resources.map.intersectsSurroundY(this.p.posx+getRight()+this.p.velx, this.p.posy, getVelocityBounds(true, false)) || this.p.posx + this.p.velx + getRight() > Resources.map.getRightBoundary();
	}
	public boolean collidesTop() {
		// TOP!
		return Resources.map.intersectsSurroundX(this.p.posx, this.p.posy+getTop(), getVelocityBounds(false, true)) || this.p.posy + this.p.vely + getTop() > Resources.map.getTopBoundary();
	}
	
	public Cell getCellOn() {
		return Resources.map.getCell(this.p.posx, this.p.posy + getBot());
	}
	
	public boolean isFacingCollision() {
		return (this.p.flip.equals("right") && this.p.collidesRight) || (this.p.flip.equals("left") && this.p.collidesLeft); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public Rectangle getBounds() {
		this.bounds.setBounds((int)this.p.posx-this.p.width/2, (int)this.p.posy-this.p.height/2, this.p.width, this.p.height);
		return this.bounds;
	}
	public boolean intersects(Rectangle rect) {
		return getBounds().intersects(rect);
	}
	public boolean intersects(Player p) {
		return getBounds().intersects(p.getBounds());
	}
}
