package com.brutalfighters.game.HUD;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brutalfighters.game.InputControls;
import com.brutalfighters.game.basic.GameLoopManager;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.flags.Flags;
import com.brutalfighters.game.multiplayer.packets.Packet2MatchFinished;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.resources.Assets;
import com.brutalfighters.game.sound.SoundUtil;
import com.brutalfighters.game.utility.rendering.TextureHandle;

public class HUD {
	private static boolean isEscapeMenuShown = false;
	
	private static final int barWidth = 115;
	private static final int barHeight = 8;
	private static final int barPad = 50;
	
	private static Sprite score;
	
	private static Timer warmupTimer;
	private static float WARMUP;
	
	private static Sound tick;
	
	private static Sprite victory, defeat;
	private static int teamWon = -1;
	
	public static void Load() {
		isEscapeMenuShown = false;
		
		score = TextureHandle.getSprite("hud/score/score.png", true, false, false); //$NON-NLS-1$
		
		tick = SoundUtil.getSound("sfx/tick/tick.wav"); //$NON-NLS-1$
		
		victory = TextureHandle.getSprite("hud/finish/victory.png", true, false, false); //$NON-NLS-1$
		defeat = TextureHandle.getSprite("hud/finish/defeat.png", true, false, false); //$NON-NLS-1$
		
		victory.setSize(1000, 352);
		defeat.setSize(1000, 352);
		
		teamWon = -1;
		
		warmupTimer = new Timer();
	}	
	
	public static void setWarmup(float wu) {
		warmupTimer.cancel();
		warmupTimer = new Timer();
		WARMUP = wu;
		if(isWarmup()) {
			warmupTimer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					if(isWarmup()) {
						SoundUtil.playSound(tick);
						WARMUP-=1000;
					}
				}
			}, 1000, 1000);
		}
	}
	
	private static void drawWarmup() {
		GameFont.Warmup.getFont().draw(Render.getSpriteBatch(), Integer.toString((int)WARMUP/1000), Render.getResX()/2-32, Render.getResY()/2+64);
	}
	
	private static void drawRespawn() {
		GameFont.Respawn.getFont().draw(Render.getSpriteBatch(), Integer.toString(Assets.player.getPlayer().DCD/1000 + 1), Render.getResX()/2-32, Render.getResY()/2+64);
	}
	
	public static void drawTutorial() {
		GameFont.Tutorial.getFont().draw(Render.getSpriteBatch(), "Arrow keys for movement", 200, Render.getResY()/3+300); //$NON-NLS-1$
		GameFont.Tutorial.getFont().draw(Render.getSpriteBatch(), "Shift for running", 200, Render.getResY()/3 + 200); //$NON-NLS-1$
		GameFont.Tutorial.getFont().draw(Render.getSpriteBatch(), "Spacebar for auto basic attacks", 200, Render.getResY()/3+100); //$NON-NLS-1$
		GameFont.Tutorial.getFont().draw(Render.getSpriteBatch(), "Q,W,E,R for skills", 200, Render.getResY()/3); //$NON-NLS-1$
		GameFont.Tutorial.getFont().draw(Render.getSpriteBatch(), "Z to teleport", 200, Render.getResY()/3 - 100); //$NON-NLS-1$
	}
	public static void drawDebugInfo() {
		PlayerData p = Assets.player.getPlayer();
		GameFont.DebugInfo.getFont().draw(Render.getSpriteBatch(), "Version : Alpha || FPS - " + Gdx.graphics.getFramesPerSecond(), 50, Gdx.graphics.getHeight() - 30); //$NON-NLS-1$
		GameFont.DebugInfo.getFont().draw(Render.getSpriteBatch(), "Camera Pos X : " + Assets.client.getCamera().position.x + " Pos Y : " + Assets.client.getCamera().position.y, 50, Gdx.graphics.getHeight() - 80); //$NON-NLS-1$ //$NON-NLS-2$
		GameFont.DebugInfo.getFont().draw(Render.getSpriteBatch(), "Player Pos X : " + p.posx + " Pos Y : " + p.posy, 50, Gdx.graphics.getHeight() - 130); //$NON-NLS-1$ //$NON-NLS-2$
		GameFont.DebugInfo.getFont().draw(Render.getSpriteBatch(), "Player Pos X : " + p.posx + " Pos Y : " + p.posy, 50, Gdx.graphics.getHeight() - 130); //$NON-NLS-1$ //$NON-NLS-2$
		GameFont.DebugInfo.getFont().draw(Render.getSpriteBatch(), "Player Vel X : " + p.velx + " Vel Y : " + p.vely, 50, Gdx.graphics.getHeight() - 180); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	// Not flexible but good enough!
	private static void drawScore() {
		Render.getSpriteBatch().draw(score, Render.getResX()/2-score.getWidth()/2, Render.getResY()-score.getHeight(), score.getWidth(), score.getHeight());
		GameFont.BlueTeamBig.getFont().draw(Render.getSpriteBatch(), Integer.toString(Assets.score.kills[Flags.BLUE_TEAM]), Render.getResX()/2-116-64, Render.getResY()-score.getHeight()/2+20);
		GameFont.RedTeamBig.getFont().draw(Render.getSpriteBatch(), Integer.toString(Assets.score.kills[Flags.RED_TEAM]), Render.getResX()/2+116-32, Render.getResY()-score.getHeight()/2+20);
		
		GameFont.BlueTeam.getFont().draw(Render.getSpriteBatch(), Integer.toString(Assets.score.flags[Flags.BLUE_TEAM]), Render.getResX()/2-score.getWidth()/2+60, Render.getResY()-28);
		GameFont.RedTeam.getFont().draw(Render.getSpriteBatch(), Integer.toString(Assets.score.flags[Flags.RED_TEAM]), Render.getResX()/2+score.getWidth()/2-140, Render.getResY()-28);
	}
	
	private static void drawFinishedMatch() {
		if(getTeamWon() == Assets.player.getPlayer().team) {
			Render.drawMiddle(victory);
		} else {
			Render.drawMiddle(defeat);
		}
	}
	
	private static void drawKillsCounter() {
		Assets.killsCounter.render();
	}
	
	private static void drawEM() {
		EscapeMenu.draw(Render.getSpriteBatch());
	}

	public static void drawHUD() {
		drawScore();
		
		if(isMatchFinished()) {
			drawFinishedMatch();
		} else {
			drawKillsCounter();
			if(Assets.player.getPlayer().isDead) {
				drawRespawn();
				drawTutorial();
			} else if(isWarmup()) {
				drawWarmup();
				drawTutorial();
			}
		}
		
		if (Gdx.input.isKeyPressed(InputControls.TUTORIAL)) {
			drawTutorial();
		} else if(Gdx.input.isKeyPressed(InputControls.DebugInfo)) {
			drawDebugInfo();
		}
		
		if(isEscapeMenuShown) {
			drawEM();
		}
	}

	public static boolean isEscapeMenuShown() {
		return isEscapeMenuShown;
	}

	public static void showEscapeMenu(boolean show) {
		isEscapeMenuShown = show;
	}

	public static int barWidth() {
		return barWidth;
	}

	public static int barHeight() {
		return barHeight;
	}

	public static int barPad() {
		return barPad;
	}
	
	public static void drawHPBar(float posx, float posy, int hp, int maxhp, Color color) {
		Render.drawRectFilled(Color.BLACK, posx - barWidth()/2 - 8, posy + HUD.barPad() + HUD.barHeight(), HUD.barWidth() + 4, HUD.barHeight() + 4);
		Render.drawRectFilled(new Color(0.2f,0.2f,0.2f,1), posx - barWidth()/2 - 6, posy + HUD.barPad() + HUD.barHeight() + 2, HUD.barWidth(), HUD.barHeight());
		Render.drawRectFilled(color, posx - barWidth()/2 - 6, posy + HUD.barPad() + HUD.barHeight() + 2, (int)((float)hp/maxhp * HUD.barWidth()), HUD.barHeight());
	}
	public static void drawHPBar(PlayerData p) {
		if(p.team == Assets.player.getPlayer().team) {
			drawHPBar(p.posx, p.posy, p.hp, p.maxhp, Color.GREEN);
		} else {
			drawHPBar(p.posx, p.posy, p.hp, p.maxhp, Color.RED);
		}
	}
	
	public static void drawMANABar(float posx, float posy, int mana, int maxmana, Color color) {
		Render.drawRectFilled(Color.BLACK, posx - barWidth()/2 - 8, posy + HUD.barPad() - 2, HUD.barWidth() + 4, HUD.barHeight() + 4);
		Render.drawRectFilled(new Color(0.2f,0.2f,0.2f,1), posx - barWidth()/2 - 6, posy + HUD.barPad(), HUD.barWidth(), HUD.barHeight());
		Render.drawRectFilled(color, posx - barWidth()/2 - 6, posy + HUD.barPad(), (int)((float)mana/maxmana * HUD.barWidth()), HUD.barHeight());
	}
	public static void drawMANABar(PlayerData p) {
		drawMANABar(p.posx, p.posy, p.mana, p.maxmana, Color.BLUE);
	}

	public static boolean isWarmup() {
		if(WARMUP <= 0) {
			warmupTimer.cancel();
			warmupTimer.purge();
			return false;
		}
		return true;
	}
	
	public static boolean toBlur() {
		return isEscapeMenuShown() || isWarmup() || Assets.player.getPlayer().isDead || !GameLoopManager.isUpdating() || getTeamWon() != -1;
	}
	
	public static boolean isMatchFinished() {
		return !GameLoopManager.isUpdating() && getTeamWon() != -1;
	}

	public static int getTeamWon() {
		return teamWon;
	}
	public static void setTeamWon(Packet2MatchFinished packet) {
		HUD.teamWon = packet.teamWon;
	}
	
	public static void dispose() {
		warmupTimer.cancel();
		warmupTimer.purge();
	}
	
}
