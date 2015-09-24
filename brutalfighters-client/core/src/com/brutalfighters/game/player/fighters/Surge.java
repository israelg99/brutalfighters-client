package com.brutalfighters.game.player.fighters;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.sound.SoundUtil;
import com.brutalfighters.game.utility.rendering.AnimationHandler;
import com.brutalfighters.game.utility.rendering.RenderUtility;
import com.brutalfighters.game.utility.rendering.TextureHandle;
import com.brutalfighters.game.utility.rendering.TexturePacker;
import com.brutalfighters.game.utility.rendering.TexturesPacker;

public class Surge extends Fighter {

	private TextureRegion[] s1_frames, s2_frames, s3_frames, s4_frames, s2_fx_frames, s4_fx_frames;
	
	private float s2_fx_width = getSize().getX(); // 2*getSize().getX() total, but this variable stores only the FX width, fighter not included.
	
	private Music lightning_sfx; // Music because Sound will run like 1000x times and Music has isPlaying() to stop it.
	
	protected Surge(PlayerData pdata) {
		super(pdata);
	}

	@Override
	protected void loadSprite() {
		breath_frames = TextureHandle.ApplyFrames(0, 0, 4, 1, SPRITE);
		
		walk_frames = TextureHandle.ApplyFrames(0, 1, 4, 2, SPRITE);
		
		run_frames = TextureHandle.ApplyFrames(0, 2, 3, 3, SPRITE);
		
		aattack_frames = TextureHandle.ApplyFrames(0, 4, 12, 5, SPRITE);

		death_frames = TextureHandle.ApplyFrames(0, 9, 6, 10, SPRITE);
		
		s1_frames = TextureHandle.ApplyFrames(0, 5, 6, 6, SPRITE);
		
		s2_frames = TextureHandle.ApplyFrames(0, 6, 8, 7, SPRITE);
		
		s3_frames = TextureHandle.ApplyFrames(0, 7, 8, 8, SPRITE);
		
		s4_frames = TextureHandle.ApplyFrames(0, 8, 13, 9, SPRITE);
		
		TextureRegion[][] s2_fx = TextureHandle.TextureSplit(getPath() + getPlayer().getName() + "_fx_right.png", 160, 80, true, false); //$NON-NLS-1$
		s2_fx_frames = TextureHandle.ApplyFrames(0, 0, 3, 1, s2_fx);

		TextureRegion[][] s4_fx = TextureHandle.TextureSplit(getPath() + "lightning_right.png", 170, 415, true, false); //$NON-NLS-1$
		s4_fx_frames = TextureHandle.ApplyFrames(0, 0, 4, 1, s4_fx);
		
		stand_frame = SPRITE[0][0];
		
		jump_frame = SPRITE[3][0];
		
		loadExtraSFX();
	}
	
	public void loadExtraSFX() {
		lightning_sfx = SoundUtil.getMusic(getPath() + "lightning.wav", SoundUtil.getVolume(0.3f)); //$NON-NLS-1$
	}

	@Override
	protected TexturesPacker drawBreath() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), breath_frames, 0.27f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getY(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawWalking() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), walk_frames, 0.13f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getY(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawRunning() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), run_frames, 0.16f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getY(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawAAttack() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), aattack_frames, 0.12f, Animation.PlayMode.LOOP).getKeyFrame(GameTime.getTime(), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getY(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill1() {
		addSkillTimer(0);
		
		playSkill(0, 0);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s1_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(0), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill2() {
		addSkillTimer(1);
		
		playSkill(1, 400);
		
		float skillSpeed = 0.08f;
		Animation skill = AnimationHandler.getAnimation(getPlayer().getFlip(), s2_frames, skillSpeed, Animation.PlayMode.NORMAL);
		if(!skill.isAnimationFinished(getSkillTimer(1))) {
			return new TexturesPacker(new TexturePacker(skill.getKeyFrame(getSkillTimer(1), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
		}
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s2_fx_frames, skillSpeed, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(1) - skillSpeed*s2_frames.length, false), 400, 135, CenterFX_X(getSize().getX(), s2_fx_width), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill3() {
		addSkillTimer(2);
		
		playSkill(2, 300);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s3_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(2), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill4() {
		addSkillTimer(3);
		
		playSkill(3, 0);
		
		float skillspeed = 0.08f;
		TexturesPacker textures = new TexturesPacker();
		textures.add(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s4_frames, skillspeed, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(3), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
		if(getSkillTimer(3) >= 9*skillspeed && getSkillTimer(3) <= 13*skillspeed) {
			textures.add(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s4_fx_frames, skillspeed, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(3) - 9*skillspeed, false), 410, 705, RenderUtility.CenterX(getPlayer().getPos().getX()-getSize().getX()-125, 410), RenderUtility.CenterY(getPlayer().getPos().getY(), 235)));
			textures.add(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s4_fx_frames, skillspeed, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(3) - 9*skillspeed, false), 410, 705, RenderUtility.CenterX(getPlayer().getPos().getX()+getSize().getX()+125, 410), RenderUtility.CenterY(getPlayer().getPos().getY(), 235)));
			if(!lightning_sfx.isPlaying()) {
				SoundUtil.playStereo(lightning_sfx, getPlayer().getPos().getX());
			}
		}
		return textures;
	}

	@Override
	protected TexturesPacker drawDead() {
		addDeathTime();
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), death_frames, 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(getDeathTimer(), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected void applyRunningParticles() {
		applyRunningParticles(1);
	}

}
