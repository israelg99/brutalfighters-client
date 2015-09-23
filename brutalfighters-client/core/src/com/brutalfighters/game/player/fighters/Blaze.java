package com.brutalfighters.game.player.fighters;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.sound.SoundUtil;
import com.brutalfighters.game.utility.ServerInfo;
import com.brutalfighters.game.utility.rendering.AnimationHandler;
import com.brutalfighters.game.utility.rendering.RenderUtility;
import com.brutalfighters.game.utility.rendering.TextureHandle;
import com.brutalfighters.game.utility.rendering.TexturePacker;
import com.brutalfighters.game.utility.rendering.TexturesPacker;

public class Blaze extends Fighter {

	private TextureRegion[] s1_frames, s2_frames, s3_frames, s4_frames, s1_fx_frames;
	
	private Music phoenix_sfx; // Sound will run like 1000x times! in Music you have the isPlaying Method!
	private float skill1_fx_width = getSize().getX() * 3; // It should be getSize().getX() * 4 in total, one getSize().getX() is the fighter itself.
	
	protected Blaze(PlayerData pdata) {
		super(pdata);
	}

	@Override
	protected void loadSprite() {
		breath_frames = TextureHandle.ApplyFrames(0, 0, 4, 1, SPRITE);
		
		walk_frames = TextureHandle.ApplyFrames(0, 1, 4, 2, SPRITE);
		
		run_frames = TextureHandle.ApplyFrames(0, 2, 3, 3, SPRITE);
		
		aattack_frames = TextureHandle.ApplyFrames(0, 4, 13, 5, SPRITE);
	
		death_frames = TextureHandle.ApplyFrames(0, 9, 6, 10, SPRITE);
		
		s1_frames = TextureHandle.ApplyFrames(0, 5, 8, 6, SPRITE);
		
		s2_frames = TextureHandle.ApplyFrames(0, 6, 7, 7, SPRITE);
		
		s3_frames = TextureHandle.ApplyFrames(0, 7, 10, 8, SPRITE);
		
		s4_frames = TextureHandle.ApplyFrames(0, 8, 10, 9, SPRITE);
		
		TextureRegion[][] s1_fx = TextureHandle.TextureSplit(getPath() + getPlayer().getName() + "_fx_right.png", 380, 80, true, false); //$NON-NLS-1$
		s1_fx_frames = TextureHandle.ApplyFrames(0, 0, 1, 4, s1_fx);
		
		stand_frame = SPRITE[0][0];
		
		jump_frame = SPRITE[3][0];
		
		loadExtraSFX();
	}
	
	public void loadExtraSFX() {
		phoenix_sfx = SoundUtil.getMusic(getPath() + "phoenix.wav", SoundUtil.getVolume(0.3f)); //$NON-NLS-1$
	}

	@Override
	protected TexturesPacker drawBreath() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), breath_frames, 0.27f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawWalking() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), walk_frames, 0.12f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawRunning() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), run_frames, 0.16f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawAAttack() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), aattack_frames, 0.1f, Animation.PlayMode.LOOP).getKeyFrame(GameTime.getTime(), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill1() {
		addSkillTimer(0);
		
		playSkill(0, 100);
		
		float skillSpeed = 0.08f;
		Animation skill = AnimationHandler.getAnimation(getPlayer().getFlip(), s1_frames, skillSpeed, Animation.PlayMode.NORMAL);
		if(!skill.isAnimationFinished(getSkillTimer(0))) {
			return new TexturesPacker(new TexturePacker(skill.getKeyFrame(getSkillTimer(0), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
		}
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s1_fx_frames, skillSpeed, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(0) - skillSpeed*s1_frames.length, false), 800, 135, RenderUtility.CenterFX_X(getPlayer(), getSize().getX(), skill1_fx_width), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill2() {
		addSkillTimer(1);
		
		playSkill(1, 0);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s2_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(1), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill3() {
		addSkillTimer(2);
		
		playSkill(2, 100);
		
		if(getPlayer().getSkillCD()[2] <= ServerInfo.getFPS() * 8 && 
				!phoenix_sfx.isPlaying()) {
			SoundUtil.playStereo(phoenix_sfx, getPlayer().getPos().getX());
		}
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s3_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(2), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill4() {
		addSkillTimer(3);
		
		if(getPlayer().getSkillCD()[3] / ServerInfo.getFPS() % 3 == 0) {
			SoundUtil.playStereo(getSkillSFX()[3], getPlayer().getPos().getX());
			isSkillPlayed(3, true);
		} else {
			isSkillPlayed(3, false);
		}
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s4_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(3), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawDead() {
		addDeathTime();
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), death_frames, 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(getDeathTimer(), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected void applyRunningParticles() {
		applyRunningParticles(5);
	}
}
