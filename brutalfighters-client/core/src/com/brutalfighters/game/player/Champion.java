package com.brutalfighters.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.effects.particles.ParticleEffects;
import com.brutalfighters.game.effects.particles.ParticlesCollection;
import com.brutalfighters.game.effects.text.TextEffects;
import com.brutalfighters.game.effects.text.TextFX;
import com.brutalfighters.game.resources.Prefs;
import com.brutalfighters.game.sound.GameSFXManager;
import com.brutalfighters.game.sound.SoundUtil;
import com.brutalfighters.game.utility.GameMath;
import com.brutalfighters.game.utility.ServerInfo;
import com.brutalfighters.game.utility.rendering.AnimationHandler;
import com.brutalfighters.game.utility.rendering.RenderUtility;
import com.brutalfighters.game.utility.rendering.TextureHandle;
import com.brutalfighters.game.utility.rendering.TexturePacker;
import com.brutalfighters.game.utility.rendering.TexturesPacker;

public enum Champion {

	Blaze("blaze", 1000, 200, 135, 80, 80) { //$NON-NLS-1$
		private TextureRegion[] walk_frames, run_frames, aattack_frames, breath_frames, death_frames,
				s1_frames, s2_frames, s3_frames, s4_frames, s1_fx_frames;
		
		private Music phoenix_sfx; // Sound will run like 1000x times! in Music you have the isPlaying Method!
		private int skill1_fx_width = WIDTH * 3; // It should be WIDTH * 4 in total, one WIDTH is the fighter itself.
		
		@Override
		public void loadSprite() {
			breath_frames = TextureHandle.ApplyFrames(0, 0, 4, 1, SPRITE);
			
			walk_frames = TextureHandle.ApplyFrames(0, 1, 4, 2, SPRITE);
			
			run_frames = TextureHandle.ApplyFrames(0, 2, 3, 3, SPRITE);
			
			aattack_frames = TextureHandle.ApplyFrames(0, 4, 13, 5, SPRITE);
		
			death_frames = TextureHandle.ApplyFrames(0, 9, 6, 10, SPRITE);
			
			s1_frames = TextureHandle.ApplyFrames(0, 5, 8, 6, SPRITE);
			
			s2_frames = TextureHandle.ApplyFrames(0, 6, 7, 7, SPRITE);
			
			s3_frames = TextureHandle.ApplyFrames(0, 7, 10, 8, SPRITE);
			
			s4_frames = TextureHandle.ApplyFrames(0, 8, 10, 9, SPRITE);
			
			TextureRegion[][] s1_fx = TextureHandle.TextureSplit(getPath(this) + NAME + "_fx_right.png", 380, 80, true, false); //$NON-NLS-1$
			s1_fx_frames = TextureHandle.ApplyFrames(0, 0, 1, 4, s1_fx);
			
			stand_frame = SPRITE[0][0];
			
			jump_frame = SPRITE[3][0];
			
			loadExtraSFX();
			
		}
		
		public void loadExtraSFX() {
			phoenix_sfx = SoundUtil.getMusic(getPath(this) + "phoenix.wav", SoundUtil.getVolume(0.3f)); //$NON-NLS-1$
			
		}
		
		@Override
		public void applyRunningParticles(PlayerData p) {
			applyRunningParticles(p,5);
		}
		
		@Override
		public TexturesPacker drawBreath(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, breath_frames, 0.27f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}
		
		@Override
		public TexturesPacker drawWalking(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, walk_frames, 0.12f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}
		
		@Override
		public TexturesPacker drawRunning(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, run_frames, 0.16f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}
		
		@Override
		public TexturesPacker drawAAttack(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, aattack_frames, 0.1f, Animation.PlayMode.LOOP).getKeyFrame(GameTime.getTime(), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}
		
		@Override
		public TexturesPacker drawSkill1(Player p) {
			p.addSTimer(0);
			
			playSkill(p, 0, 100);
			
			float skillSpeed = 0.08f;
			Animation skill = AnimationHandler.getAnimation(p.getPlayer().flip, s1_frames, skillSpeed, Animation.PlayMode.NORMAL);
			if(!skill.isAnimationFinished(p.getSTimer(0))) {
				return new TexturesPacker(new TexturePacker(skill.getKeyFrame(p.getSTimer(0), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
			}
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s1_fx_frames, skillSpeed, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(0) - skillSpeed*s1_frames.length, false), 800, 135, RenderUtility.CenterFX_X(p, WIDTH, skill1_fx_width), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}
		
		@Override
		public TexturesPacker drawSkill2(Player p) {
			p.addSTimer(1);
			
			playSkill(p, 1, 0);
			
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s2_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(1), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}
		
		@Override
		public TexturesPacker drawSkill3(Player p) {
			p.addSTimer(2);
			
			playSkill(p, 2, 100);
			
			if(p.getPlayer().skillCD[2] <= ServerInfo.getFPS() * 8 && 
					!phoenix_sfx.isPlaying()) {
				SoundUtil.playStereo(phoenix_sfx, p.getX());
			}
			
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s3_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(2), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}
		
		@Override
		public TexturesPacker drawSkill4(Player p) {
			p.addSTimer(3);
			
			if(p.getPlayer().skillCD[3] / ServerInfo.getFPS() % 3 == 0) {
				SoundUtil.playStereo(skill_sfx[3], p.getX());
				p.isSkillPlayed(3, true);
			} else {
				p.isSkillPlayed(3, false);
			}
			
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s4_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(3), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}
		
		@Override
		public TexturesPacker drawDead(Player p) {
			p.addDeathTime();
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, death_frames, 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(p.getDeathTime(), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}
		
	},
	
	Dusk("dusk", 1000, 200, 135, 80, 80) { //$NON-NLS-1$
		private TextureRegion[] walk_frames, run_frames, aattack_frames, breath_frames, death_frames,
				s1_frames, s2_frames, s3_frames, s4_frames, bats_frames, laser_frames;
		
		private int bats_width = WIDTH, laser_width = WIDTH;
		
		@Override
		public void loadSprite() {
			breath_frames = TextureHandle.ApplyFrames(0, 0, 4, 1, SPRITE);
			
			walk_frames = TextureHandle.ApplyFrames(0, 1, 3, 2, SPRITE);
			
			run_frames = TextureHandle.ApplyFrames(0, 2, 3, 3, SPRITE);
			
			aattack_frames = TextureHandle.ApplyFrames(0, 4, 8, 5, SPRITE);

			death_frames = TextureHandle.ApplyFrames(0, 9, 6, 10, SPRITE);
			
			s1_frames = TextureHandle.ApplyFrames(0, 5, 5, 6, SPRITE);
			
			s2_frames = TextureHandle.ApplyFrames(0, 6, 10, 7, SPRITE);
			
			s3_frames = TextureHandle.ApplyFrames(0, 7, 4, 8, SPRITE);
			
			s4_frames = TextureHandle.ApplyFrames(0, 8, 6, 9, SPRITE);
			
			TextureRegion[][] bats_fx = TextureHandle.TextureSplit(getPath(this) + NAME + "_bats_right.png", 160, 80, true, false); //$NON-NLS-1$
			bats_frames = TextureHandle.ApplyFrames(0, 0, 4, 1, bats_fx);
			
			TextureRegion[][] laser_fx = TextureHandle.TextureSplit(getPath(this) + NAME + "_laser_right.png", 160, 80, true, false); //$NON-NLS-1$
			laser_frames = TextureHandle.ApplyFrames(0, 0, 3, 1, laser_fx);
			
			stand_frame = SPRITE[0][0];
			
			jump_frame = SPRITE[3][0];
			
		}
		
		@Override
		public void applyRunningParticles(PlayerData p) {
			applyRunningParticles(p,4);
		}

		@Override
		public TexturesPacker drawBreath(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, breath_frames, 0.27f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawWalking(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, walk_frames, 0.2f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawRunning(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, run_frames, 0.16f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawAAttack(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, aattack_frames, 0.1f, Animation.PlayMode.LOOP).getKeyFrame(GameTime.getTime(), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill1(Player p) {
			p.addSTimer(0);
			
			playSkill(p, 0, 300);
			
			float skillSpeed = 0.08f;
			Animation skill = AnimationHandler.getAnimation(p.getPlayer().flip, s1_frames, skillSpeed, Animation.PlayMode.NORMAL);
			if(!skill.isAnimationFinished(p.getSTimer(0))) {
				return new TexturesPacker(new TexturePacker(skill.getKeyFrame(p.getSTimer(0), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
			}
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, bats_frames, skillSpeed, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(0) - skillSpeed*s1_frames.length, false), 400, 135, RenderUtility.CenterFX_X(p, WIDTH, bats_width), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill2(Player p) {
			p.addSTimer(1);
			
			playSkill(p, 1, 0);
			
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s2_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(1), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill3(Player p) {
			p.addSTimer(2);
			
			playSkill(p, 2, 0);
			
			float skillSpeed = 0.08f;
			Animation skill = AnimationHandler.getAnimation(p.getPlayer().flip, s3_frames, skillSpeed, Animation.PlayMode.NORMAL);
			if(!skill.isAnimationFinished(p.getSTimer(2))) {
				return new TexturesPacker(new TexturePacker(skill.getKeyFrame(p.getSTimer(2), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
			}
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, laser_frames, skillSpeed, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(2) - skillSpeed*s3_frames.length, false), 400, 135, RenderUtility.CenterFX_X(p, WIDTH, laser_width), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill4(Player p) {
			p.addSTimer(3);
			
			playSkill(p, 3, 0);
			
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s4_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(3), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawDead(Player p) {
			p.addDeathTime();
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, death_frames, 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(p.getDeathTime(), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}
	},
	
	Chip("chip", 1000, 200, 135, 80, 80) { //$NON-NLS-1$

		private TextureRegion[] walk_frames, run_frames, aattack_frames, breath_frames, death_frames,
		s1_frames, s2_frames, s3_frames, s4_frames;

		@Override
		public void loadSprite() {
			breath_frames = TextureHandle.ApplyFrames(0, 0, 4, 1, SPRITE);
			
			walk_frames = TextureHandle.ApplyFrames(0, 1, 4, 2, SPRITE);
			
			run_frames = TextureHandle.ApplyFrames(0, 2, 3, 3, SPRITE);
			
			aattack_frames = TextureHandle.ApplyFrames(0, 4, 7, 5, SPRITE);

			death_frames = TextureHandle.ApplyFrames(0, 9, 6, 10, SPRITE);
			
			s1_frames = TextureHandle.ApplyFrames(0, 5, 11, 6, SPRITE);
			
			s2_frames = TextureHandle.ApplyFrames(0, 6, 8, 7, SPRITE);
			
			s3_frames = TextureHandle.ApplyFrames(0, 7, 6, 8, SPRITE);
			
			s4_frames = TextureHandle.ApplyFrames(0, 8, 5, 9, SPRITE);
			
			stand_frame = SPRITE[0][0];
			
			jump_frame = SPRITE[3][0];
			
		}
		
		@Override
		public void applyRunningParticles(PlayerData p) {
			applyRunningParticles(p,3);
		}

		@Override
		public TexturesPacker drawBreath(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, breath_frames, 0.27f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawWalking(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, walk_frames, 0.13f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawRunning(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, run_frames, 0.16f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawAAttack(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, aattack_frames, 0.12f, Animation.PlayMode.LOOP).getKeyFrame(GameTime.getTime(), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill1(Player p) {
			p.addSTimer(0);
			
			playSkill(p, 0, 450);
			
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s1_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(0), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill2(Player p) {
			p.addSTimer(1);
			
			playSkill(p, 1, 0);
			
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s2_frames, 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(1), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill3(Player p) {
			p.addSTimer(2);
			
			playSkill(p, 2, 180);
			
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s3_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(2), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill4(Player p) {
			p.addSTimer(3);
			
			playSkill(p, 3, 250);
			
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s4_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(3), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawDead(Player p) {
			p.addDeathTime();
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, death_frames, 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(p.getDeathTime(), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}
		
	},
	
	Surge("surge", 1000, 200, 135, 80, 80) { //$NON-NLS-1$

		private TextureRegion[] walk_frames, run_frames, aattack_frames, breath_frames, death_frames,
				s1_frames, s2_frames, s3_frames, s4_frames, s2_fx_frames, s4_fx_frames;
		
		private int s2_fx_width = WIDTH; // 2*WIDTH total, but this variable stores only the FX width, fighter not included.
	
		private Music lightning_sfx; // Music because Sound will run like 1000x times and Music has isPlaying() to stop it.
		
		@Override
		public void loadSprite() {
			breath_frames = TextureHandle.ApplyFrames(0, 0, 4, 1, SPRITE);
			
			walk_frames = TextureHandle.ApplyFrames(0, 1, 4, 2, SPRITE);
			
			run_frames = TextureHandle.ApplyFrames(0, 2, 3, 3, SPRITE);
			
			aattack_frames = TextureHandle.ApplyFrames(0, 4, 12, 5, SPRITE);

			death_frames = TextureHandle.ApplyFrames(0, 9, 6, 10, SPRITE);
			
			s1_frames = TextureHandle.ApplyFrames(0, 5, 6, 6, SPRITE);
			
			s2_frames = TextureHandle.ApplyFrames(0, 6, 8, 7, SPRITE);
			
			s3_frames = TextureHandle.ApplyFrames(0, 7, 8, 8, SPRITE);
			
			s4_frames = TextureHandle.ApplyFrames(0, 8, 13, 9, SPRITE);
			
			TextureRegion[][] s2_fx = TextureHandle.TextureSplit(getPath(this) + NAME + "_fx_right.png", 160, 80, true, false); //$NON-NLS-1$
			s2_fx_frames = TextureHandle.ApplyFrames(0, 0, 3, 1, s2_fx);

			TextureRegion[][] s4_fx = TextureHandle.TextureSplit(getPath(this) + "lightning_right.png", 170, 415, true, false); //$NON-NLS-1$
			s4_fx_frames = TextureHandle.ApplyFrames(0, 0, 4, 1, s4_fx);
			
			stand_frame = SPRITE[0][0];
			
			jump_frame = SPRITE[3][0];
			
			loadExtraSFX();
			
		}
		
		public void loadExtraSFX() {
			lightning_sfx = SoundUtil.getMusic(getPath(this) + "lightning.wav", SoundUtil.getVolume(0.3f)); //$NON-NLS-1$
			
		}
		
		@Override
		public void applyRunningParticles(PlayerData p) {
			applyRunningParticles(p,1);
		}

		@Override
		public TexturesPacker drawBreath(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, breath_frames, 0.27f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawWalking(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, walk_frames, 0.13f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawRunning(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, run_frames, 0.16f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawAAttack(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, aattack_frames, 0.12f, Animation.PlayMode.LOOP).getKeyFrame(GameTime.getTime(), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill1(Player p) {
			p.addSTimer(0);
			
			playSkill(p, 0, 0);
			
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s1_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(0), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill2(Player p) {
			p.addSTimer(1);
			
			playSkill(p, 1, 400);
			
			float skillSpeed = 0.08f;
			Animation skill = AnimationHandler.getAnimation(p.getPlayer().flip, s2_frames, skillSpeed, Animation.PlayMode.NORMAL);
			if(!skill.isAnimationFinished(p.getSTimer(1))) {
				return new TexturesPacker(new TexturePacker(skill.getKeyFrame(p.getSTimer(1), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
			}
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s2_fx_frames, skillSpeed, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(1) - skillSpeed*s2_frames.length, false), 400, 135, RenderUtility.CenterFX_X(p, WIDTH, s2_fx_width), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill3(Player p) {
			p.addSTimer(2);
			
			playSkill(p, 2, 300);
			
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s3_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(2), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill4(Player p) {
			p.addSTimer(3);
			
			playSkill(p, 3, 0);
			
			float skillspeed = 0.08f;
			TexturesPacker textures = new TexturesPacker();
			textures.add(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s4_frames, skillspeed, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(3), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
			if(p.getSTimer(3) >= 9*skillspeed && p.getSTimer(3) <= 13*skillspeed) {
				textures.add(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s4_fx_frames, skillspeed, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(3) - 9*skillspeed, false), 410, 705, RenderUtility.CenterX(p.getX()-WIDTH-125, 410), RenderUtility.CenterY(p.getY(), 235)));
				textures.add(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s4_fx_frames, skillspeed, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(3) - 9*skillspeed, false), 410, 705, RenderUtility.CenterX(p.getX()+WIDTH+125, 410), RenderUtility.CenterY(p.getY(), 235)));
				if(!lightning_sfx.isPlaying()) {
					SoundUtil.playStereo(lightning_sfx, p.getX());
				}
			}
			return textures;
		}

		@Override
		public TexturesPacker drawDead(Player p) {
			p.addDeathTime();
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, death_frames, 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(p.getDeathTime(), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}
		
	},
	
	Lust("lust", 1000, 200, 135, 80, 80) { //$NON-NLS-1$
		
		private TextureRegion[] walk_frames, run_frames, aattack_frames, breath_frames, death_frames,
								s1_frames, s2_frames;
		private TextureRegion s4_frame;

		@Override
		public void loadSprite() {
			breath_frames = TextureHandle.ApplyFrames(0, 0, 4, 1, SPRITE);
			
			walk_frames = TextureHandle.ApplyFrames(0, 1, 4, 2, SPRITE);
			
			run_frames = TextureHandle.ApplyFrames(0, 2, 3, 3, SPRITE);
			
			aattack_frames = TextureHandle.ApplyFrames(0, 4, 8, 5, SPRITE);

			death_frames = TextureHandle.ApplyFrames(0, 9, 6, 10, SPRITE);
			
			s1_frames = TextureHandle.ApplyFrames(0, 5, 8, 6, SPRITE);
			
			s2_frames = TextureHandle.ApplyFrames(0, 6, 6, 7, SPRITE);
			
			s4_frame = SPRITE[8][0];
			
			stand_frame = SPRITE[0][0];
			
			jump_frame = SPRITE[3][0];
			
		}
		
		@Override
		public void applyRunningParticles(PlayerData p) {
			applyRunningParticles(p,2);
		}

		@Override
		public TexturesPacker drawWalking(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, walk_frames, 0.13f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawRunning(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, run_frames, 0.16f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawBreath(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, breath_frames, 0.2f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}

		@Override
		public TexturesPacker drawDead(Player p) {
			p.addDeathTime();
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, death_frames, 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(p.getDeathTime(), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill1(Player p) {
			p.addSTimer(0);
			
			playSkill(p, 0, 0);
			
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s1_frames, 0.062f, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(0), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill2(Player p) {
			p.addSTimer(1);
			
			playSkill(p, 1, 200);
			
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.getPlayer().flip, s2_frames, 0.062f, Animation.PlayMode.NORMAL).getKeyFrame(p.getSTimer(1), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawSkill3(Player p) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TexturesPacker drawSkill4(Player p) {
			p.addSTimer(3);
			
			playSkill(p, 3, 100);
			
			return new TexturesPacker(new TexturePacker(AnimationHandler.returnAfterCheck(p.getPlayer().flip, s4_frame), WIDTH, HEIGHT, RenderUtility.CenterX(p.getX(), WIDTH), RenderUtility.CenterY(p.getY(), HEIGHT)));
		}

		@Override
		public TexturesPacker drawAAttack(PlayerData p) {
			return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(p.flip, aattack_frames, 0.115f, Animation.PlayMode.LOOP).getKeyFrame(GameTime.getTime(), false), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
		}
		
		@Override
		public void loadSFX() {			
			skill_sfx = new Sound[skills];
			skill_sfx[0] = SoundUtil.getSound(getPath(this) + "skill1.wav"); //$NON-NLS-1$
			skill_sfx[1] = SoundUtil.getSound(getPath(this) + "skill2.wav"); //$NON-NLS-1$
			skill_sfx[3] = SoundUtil.getSound(getPath(this) + "skill4.wav"); //$NON-NLS-1$
		}
		
	};
	
	public final TextureRegion[][] SPRITE;
	public final String NAME;
	public final int MAXHP;
	public final int WIDTH, HEIGHT;
	
	public final int COLS_LENGTH;
	public final int ROWS_LENGTH;
	
	public float timeWalkSteps = 0.5f;
	public float timeRunSteps = 0.3f;
	
	public int AA_SFX_LENGTH;
	public float AA_SFX_DELAY;
	
	public TextureRegion stand_frame, jump_frame;
	public Sound jump_sfx, AA_sfx[], death_sfx, skill_sfx[];
	
	public final static int skills = 4;
	public final static String path = "champs/"; //$NON-NLS-1$
	
	public static float VOLUME = 0.5f;
	
	Champion(String name, int maxhp, int width, int height, int BLOCK_WIDTH, int BLOCK_HEIGHT) {
		this.NAME = name;
		this.MAXHP = maxhp;
		this.WIDTH = width;
		this.HEIGHT = height;
		this.SPRITE = TextureHandle.TextureSplit(getPath(this) + name + "_right.png", BLOCK_WIDTH, BLOCK_HEIGHT, true, false); //$NON-NLS-1$
		this.COLS_LENGTH = SPRITE[0].length;
		this.ROWS_LENGTH = SPRITE.length;
		
		loadSprite();
		loadBasicSFX();
		loadSFX();
	}
	
	public abstract void loadSprite();
	
	public abstract TexturesPacker drawBreath(PlayerData p);
	public abstract TexturesPacker drawWalking(PlayerData p);
	public abstract TexturesPacker drawRunning(PlayerData p);
	
	public abstract TexturesPacker drawAAttack(PlayerData p);
	
	public abstract TexturesPacker drawSkill1(Player p);
	public abstract TexturesPacker drawSkill2(Player p);
	public abstract TexturesPacker drawSkill3(Player p);
	public abstract TexturesPacker drawSkill4(Player p);
	
	public abstract TexturesPacker drawDead(Player p);
	
	public abstract void applyRunningParticles(PlayerData p);
	
	public void loadSFX() {			
		skill_sfx = new Sound[skills];
		skill_sfx[0] = SoundUtil.getSound(getPath(this) + "skill1.wav"); //$NON-NLS-1$
		skill_sfx[1] = SoundUtil.getSound(getPath(this) + "skill2.wav"); //$NON-NLS-1$
		skill_sfx[2] = SoundUtil.getSound(getPath(this) + "skill3.wav"); //$NON-NLS-1$
		skill_sfx[3] = SoundUtil.getSound(getPath(this) + "skill4.wav"); //$NON-NLS-1$
	}
	
	public void loadBasicSFX() {			
		AA_SFX_LENGTH = 2;
		AA_SFX_DELAY = 0.5f;
		
		jump_sfx = SoundUtil.getSound(path + "sfx/jump.wav"); //$NON-NLS-1$
		
		AA_sfx = new Sound[AA_SFX_LENGTH];
		AA_sfx[0] = SoundUtil.getSound(path + "sfx/AA1.wav"); //$NON-NLS-1$
		AA_sfx[1] = SoundUtil.getSound(path + "sfx/AA2.wav"); //$NON-NLS-1$
		
		death_sfx = SoundUtil.getSound(path + "sfx/death1.wav"); //$NON-NLS-1$
	}

	public void playSkill(int i, float x) {
		SoundUtil.playStereo(skill_sfx[i], x);
	}
	
	public void playJump(float x) {
		SoundUtil.playStereo(jump_sfx, x);
		
	}
	public void playAA(int i, float x) {
		SoundUtil.playStereo(AA_sfx[i], x);
		
	}
	public void playDeath(float x) {
		SoundUtil.playStereo(death_sfx, x);
		
	}
	
	public TexturesPacker drawJump(PlayerData p) {
		return new TexturesPacker(new TexturePacker(AnimationHandler.returnAfterCheck(p.flip, jump_frame), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
	}
	public TexturesPacker drawStand(PlayerData p) {
		return new TexturesPacker(new TexturePacker(AnimationHandler.returnAfterCheck(p.flip, stand_frame), WIDTH, HEIGHT, RenderUtility.CenterX(p.posx, WIDTH), RenderUtility.CenterY(p.posy, HEIGHT)));
	}
	
	public void playJumpStep(Player p) {
		PlayerData pd = p.getPlayer();
		if(pd.isJump && pd.onGround && !pd.collidesTop) { // GameSFXManager when you hit the surface after the jump and getting ready for the next, good for design!
			GameSFXManager.moveStepsSFX(p, timeWalkSteps);
			//return drawJump(p); // 	 TO LOOK IF THE JUMP IS SMOOTH ONLY TESTING!@!!!@@@!@!@!@!
		}
	}
	
	public TexturesPacker drawChamp(Player p) {
		
		PlayerData pd = p.getPlayer();
		
		playJumpStep(p);
		
		applyParticles(p);
		
		if(pd.isDead) {
			return drawDead(p);
		} else if(pd.isSkilling) { // Skills GameSFXManager inside of the skill functions below
			if(pd.isSkill1) {
				return drawSkill1(p); // We cannot lock the camera here, we need it to be unlocked.
			} else if(pd.isSkill2) {
				return drawSkill2(p);
			} else if(pd.isSkill3) {
				return drawSkill3(p);
			} else if(pd.isSkill4) {
				return drawSkill4(p);
			}
		} else {
			p.resetSkillPlayed();
			if(!pd.onGround) {
				p.toNextStep = 0;
				p.steps = 1;
				return drawJump(pd);
			} else if(pd.hasControl) {
				if(p.isWalking()) {
					if(pd.isRunning) {
						GameSFXManager.moveStepsSFX(p, timeRunSteps);
						return drawRunning(pd);
					}
					GameSFXManager.moveStepsSFX(p, timeWalkSteps);
					return drawWalking(pd);
				} else if(pd.isAAttack) {
					playAA(p);
					return drawAAttack(pd);
				}
			}
		}
		
		return drawBreath(pd);
	}
	
	public void applyParticles(Player p) {
		applyRunningParticles(p.getPlayer());
		applyHealthParticles(p);
	}
	
	private static void playSkill(Player p, int i, int ms) {
		if(Prefs.isVolume()) {
			if(p.getSTimer(i) >= (float)ms/1000) {
				if(!p.isSkillPlayed(i)) {
					p.getChamp().playSkill(i, p.getX());
					p.isSkillPlayed(i, true);
				}
			} else {
				p.isSkillPlayed(i, false);
			}
		}
	}
	
	private static void playAA(Player p) {
		
		Champion champ = p.getChamp();
		
		p.toNextStep -= Gdx.graphics.getDeltaTime();
		if (p.toNextStep < 0) {
			p.steps = p.steps < champ.AA_SFX_LENGTH ? p.steps + 1 : 1;
			champ.playAA(p.steps-1, p.getX()); // Because its an array here, not a string that you put into hashmap
			p.toNextStep = champ.AA_SFX_DELAY;
		}
	}
	
	private static void applyHealthParticles(Player p) {
		PlayerData pd = p.getPlayer();
		if(p.changedHealth() < 0) {
			TextEffects.add(TextFX.BloodEffect, p); 
			if(p.changedHealth() <= -200) {
				ParticleEffects.add(ParticlesCollection.BloodSplat_Big, pd.posx, pd.posy-pd.height/6, false); 
			} else if(GameMath.nextInt(1, 2) > 1) {
				ParticleEffects.add(ParticlesCollection.BloodSplat_Left, pd.posx, pd.posy-pd.height/6, false); 
			} else {
				ParticleEffects.add(ParticlesCollection.BloodSplat_Right, pd.posx, pd.posy-pd.height/6, false); 
			}
		} else if(p.changedHealth() > 0) {
			TextEffects.add(TextFX.HealEffect, p); 
			ParticleEffects.add(ParticlesCollection.HealthRegen, pd.posx, pd.posy-pd.height/6, false); 
		}
	}
	
	private static void applyRunningParticles(PlayerData p, int trailNumber) {
		if(p.isRunning && !p.isSkilling && p.hasControl && p.velx != 0 && GameMath.nextBoolean(70)) {
			ParticleEffects.add("Trail"+trailNumber, p.posx, p.posy, true); //$NON-NLS-1$
		}
	}
	
	public static String getPath(Champion t) {
		return "champs/" + t.NAME + "/"; //$NON-NLS-1$ //$NON-NLS-2$
	}
	public static String getPath(Player p) {
		return getPath(p.getChamp());
	}
	
	public static boolean contains(String fighter) {
	    for (Champion c : Champion.values()) {
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
