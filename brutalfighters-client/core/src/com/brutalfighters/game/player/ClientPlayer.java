package com.brutalfighters.game.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.brutalfighters.game.InputControls;
import com.brutalfighters.game.HUD.EscapeMenu;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.effects.particles.ParticleEffects;
import com.brutalfighters.game.effects.particles.ParticlesCollection;
import com.brutalfighters.game.graphics.Shaders;
import com.brutalfighters.game.map.GameMap;
import com.brutalfighters.game.multiplayer.GameClient;
import com.brutalfighters.game.multiplayer.packets.Packet;
import com.brutalfighters.game.multiplayer.packets.Packet3InputAAttack;
import com.brutalfighters.game.multiplayer.packets.Packet3InputJump;
import com.brutalfighters.game.multiplayer.packets.Packet3InputLeft;
import com.brutalfighters.game.multiplayer.packets.Packet3InputRight;
import com.brutalfighters.game.multiplayer.packets.Packet3InputRun;
import com.brutalfighters.game.multiplayer.packets.Packet3InputSkill1;
import com.brutalfighters.game.multiplayer.packets.Packet3InputSkill2;
import com.brutalfighters.game.multiplayer.packets.Packet3InputSkill3;
import com.brutalfighters.game.multiplayer.packets.Packet3InputSkill4;
import com.brutalfighters.game.multiplayer.packets.Packet3InputTeleport;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseAAttack;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseJump;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseLeft;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseRight;
import com.brutalfighters.game.multiplayer.packets.Packet4ReleaseRun;
import com.brutalfighters.game.player.fighters.Fighter;
import com.brutalfighters.game.resources.Assets;
import com.brutalfighters.game.resources.Prefs;
import com.brutalfighters.game.sound.GameSFX;
import com.brutalfighters.game.sound.GameSFXManager;

public class ClientPlayer implements InputProcessor {
	
	// The Camera
	private OrthographicCamera camera;
	private StretchViewport viewport;
	
	// The Control
	private boolean control;
	
	// Menu
	private boolean emShow;
	
	// States
	private boolean onPortal;
	
	public ClientPlayer() {
		camera = new OrthographicCamera();
		//camera.setToOrtho(false, Render.getResX(), Render.getResY());
		
		viewport = new StretchViewport(Render.getResX(), Render.getResY(), camera);
		
		control = true;
		emShow = false;
		
		Render.setCamera(this);
	}
	
	// Means should be ran and updated anytime, including during warmup
	public void applyBasic() {
		updateCamera();
		updateBackground();
		if(Prefs.getPPEffects()) {
			updateMotionBlur();
		}
	}
	
	// Will NOT be called during warmup, use basic update during warmup.
	public void update() {
		applyBasic();
		
		applyPortal();
	}
	
	public void applyPortal() {
		if(Assets.map.hasProperty(GameMap.TELEPORT(), getX(), getY())) {
			if(!onPortal()) {
				ParticleEffects.add(ParticlesCollection.TP_Dust, Assets.map.toPixelX(Assets.map.toCellX(getX())), Assets.map.toPixelY(Assets.map.toCellY(getY())) + Assets.map.getTileHeight()/2, true); 
				GameSFXManager.play(GameSFX.Portal);
			}
			onPortal(true);
		} else {
			onPortal(false);
		}
	}
	
	private static void updateMotionBlur() {
		Shaders.getMotionBlur().begin();
		Shaders.getMotionBlur().setUniformf("blurSize", Shaders.getBlur(velX() * Prefs.getMotionBlur())); //$NON-NLS-1$
		Shaders.getMotionBlur().end();
	}

	private void updateCamera() {
		Fighter client = Assets.player;
		
		// CAMERA X MOVER
		if(!client.getCameraLockX()) {
			getCamera().position.x = getX() < Render.getResX() / 2 ? Render.getResX() / 2 : getX() > Assets.map.getWidthSize() - Render.getResX() / 2 ? Assets.map.getWidthSize() - Render.getResX() / 2 : getX();
		}
		
		// CAMERA Y MOVER
		if(!client.getCameraLockY()) {
			getCamera().position.y = getY() < Render.getResY() / 2 ? Render.getResY() / 2 : getY() > Assets.map.getHeightSize() - Render.getResY() / 2 ? Assets.map.getHeightSize() - Render.getResY() / 2 : getY();
		}
		
		// CAMERA UPDATER
		getCamera().update();
		
	}
	
	public void updateViewPort(int width, int height) {
		viewport.update(width, height);
	}
	
	private static void updateBackground() {
		Assets.bg.applyPos(getX() * 1.5f);
	}
	
	private void setCameraPos(float x, float y) {
		getCamera().position.x = x;
		getCamera().position.y = y;
	}

	private static float getX() {
		return Assets.player.getPlayer().getPos().getX();
	}
	private static float getY() {
		return Assets.player.getPlayer().getPos().getY();
	}
	
	private static float velX() {
		return Assets.player.getPlayer().getVel().getX();
	}
	private static float velY() {
		return Assets.player.getPlayer().getVel().getY();
	}

	public void controlHimself(boolean con) {
		control = con;
	}
	public boolean inControl() {
		return control;
	}
	
	public void showEM(boolean show) {
		emShow = show;
	}
	public boolean isEmShown() {
		return emShow;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public boolean onPortal() {
		return onPortal;
	}
	public void onPortal(boolean onPortal) {
		this.onPortal = onPortal;
	}
	
	public void applyWalkingDust(float xoffest) {
		ParticleEffects.add(ParticlesCollection.WalkingDust, getX()+xoffest, getY() + Assets.player.getPlayer().getBot(), false); 
	}

	@Override
	public boolean keyDown(int keycode) {
		PlayerData p = Assets.player.getPlayer();
		
		// Inspiring ParticleEffects
		switch(keycode) {
			case Keys.NUM_1 :
				ParticleEffects.add(ParticlesCollection.RightBlueBeat, p.getPos().getX()+25, p.getPos().getY()-20, false); 
			return false;
	
			case Keys.NUM_2 :
				ParticleEffects.add(ParticlesCollection.CenterBlueRedPump, p.getPos().getX(), p.getPos().getY()+20, false); 
			return false;
				
			case Keys.NUM_3 :
				ParticleEffects.add(ParticlesCollection.BlueFirework, p.getPos().getX(), p.getPos().getY(), false); 
			return false;
			
			case Keys.NUM_4 :
				ParticleEffects.add(ParticlesCollection.Fireworks3, p.getPos().getX(), p.getPos().getY(), false); 
			return false;
		}
		
		if(inControl() && !p.isDead() && !HUD.isWarmup()) {
			switch(keycode) {
				case InputControls.LEFT :
					if(!p.isRight() && p.onGround()) {
						applyWalkingDust(p.getRight());
					}
					GameClient.sendPacketUDP(new Packet3InputLeft());
				return false;
				
				case InputControls.RIGHT :
					if(!p.isLeft() && p.onGround()) {
						applyWalkingDust(p.getLeft());
					}
					GameClient.sendPacketUDP(new Packet3InputRight());
				return false;
				
				case InputControls.Teleport :
					if(onPortal() && p.hasControl() && !p.isSkilling()) {
						GameClient.sendPacketUDP(new Packet3InputTeleport());
						GameSFX.Woosh.playSFX(getX());
					}
				return false;
			}
			
			// SKILLS
			if(p.onGround()) {
				switch(keycode) {
					case InputControls.JUMP :
						if(!p.isCollidingTop()) {
							GameClient.sendPacketUDP(new Packet3InputJump());
						}
					return false;
						
					case InputControls.RUN :
						GameClient.sendPacketUDP(new Packet3InputRun());
					return false;
					
					case InputControls.AAttack :
							GameClient.sendPacketUDP(new Packet3InputAAttack());
					return false;
				}
				
				//Skills p.hasControl && !p.isSkilling(in the method `skillAction()`);
				switch(keycode) {
					case InputControls.Skill1 :
						skillAction(p, new Packet3InputSkill1());
					return false;
					
					case InputControls.Skill2 :
						skillAction(p, new Packet3InputSkill2());
					return false;
					
					case InputControls.Skill3 :
						skillAction(p, new Packet3InputSkill3());
					return false;
					
					case InputControls.Skill4 :
						skillAction(p, new Packet3InputSkill4());
					return false;
				}
			}
		}
		
		if(keycode == InputControls.ESCAPE_MENU) {
			showEM(!isEmShown());
			HUD.showEscapeMenu(isEmShown());
			controlHimself(!isEmShown());
		}
		
		if(HUD.isEscapeMenuShown()) {
			switch(keycode) {
				case InputControls.EM_UP:
					EscapeMenu.subID();
				return false;
				case InputControls.EM_DOWN:
					EscapeMenu.addID();
				return false;
				case InputControls.EM_CLICK:
					EscapeMenu.click();
				return false;
			}
		}
		
		
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {		

		switch(keycode) {
			case InputControls.LEFT :
				GameClient.sendPacketUDP(new Packet4ReleaseLeft());
			return false;
			
			case InputControls.RIGHT :
				GameClient.sendPacketUDP(new Packet4ReleaseRight());
			return false;
			
			case InputControls.JUMP :
				GameClient.sendPacketUDP(new Packet4ReleaseJump());
			return false;
				
			case InputControls.RUN :
				GameClient.sendPacketUDP(new Packet4ReleaseRun());
			return false;
			
			case InputControls.AAttack :
				GameClient.sendPacketUDP(new Packet4ReleaseAAttack());
			return false;
		}
		
		return false;
	}
	
	public static void skillAction(PlayerData p, Packet packet) {
		if(p.hasControl() && !p.isSkilling()) {
			GameClient.sendPacketUDP(packet);
		} else {
			//GameSFXManager.play("blockAction");
		}
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
