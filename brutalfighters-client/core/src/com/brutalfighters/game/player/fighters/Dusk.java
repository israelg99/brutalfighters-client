package com.brutalfighters.game.player.fighters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.utility.rendering.AnimationHandler;
import com.brutalfighters.game.utility.rendering.RenderUtility;
import com.brutalfighters.game.utility.rendering.TextureHandle;
import com.brutalfighters.game.utility.rendering.TexturePacker;
import com.brutalfighters.game.utility.rendering.TexturesPacker;

public class Dusk extends Fighter {

	private TextureRegion[] s1_frames, s2_frames, s3_frames, s4_frames, bats_frames, laser_frames;
	
	private float bats_width = getSize().getX(), laser_width = getSize().getY();
	
	protected Dusk(PlayerData pdata) {
		super(pdata);
	}

	@Override
	protected void loadSprite() {
		breath_frames = TextureHandle.ApplyFrames(0, 0, 4, 1, SPRITE);
		
		walk_frames = TextureHandle.ApplyFrames(0, 1, 3, 2, SPRITE);
		
		run_frames = TextureHandle.ApplyFrames(0, 2, 3, 3, SPRITE);
		
		aattack_frames = TextureHandle.ApplyFrames(0, 4, 8, 5, SPRITE);

		death_frames = TextureHandle.ApplyFrames(0, 9, 6, 10, SPRITE);
		
		s1_frames = TextureHandle.ApplyFrames(0, 5, 5, 6, SPRITE);
		
		s2_frames = TextureHandle.ApplyFrames(0, 6, 10, 7, SPRITE);
		
		s3_frames = TextureHandle.ApplyFrames(0, 7, 4, 8, SPRITE);
		
		s4_frames = TextureHandle.ApplyFrames(0, 8, 6, 9, SPRITE);
		
		TextureRegion[][] bats_fx = TextureHandle.TextureSplit(getPath() + getPlayer().getName() + "_bats_right.png", 160, 80, true, false); //$NON-NLS-1$
		bats_frames = TextureHandle.ApplyFrames(0, 0, 4, 1, bats_fx);
		
		TextureRegion[][] laser_fx = TextureHandle.TextureSplit(getPath() + getPlayer().getName() + "_laser_right.png", 160, 80, true, false); //$NON-NLS-1$
		laser_frames = TextureHandle.ApplyFrames(0, 0, 3, 1, laser_fx);
		
		stand_frame = SPRITE[0][0];
		
		jump_frame = SPRITE[3][0];
		
	}

	@Override
	protected TexturesPacker drawBreath() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), breath_frames, 0.27f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawWalking() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), walk_frames, 0.2f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
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
		
		playSkill(0, 300);
		
		float skillSpeed = 0.08f;
		Animation skill = AnimationHandler.getAnimation(getPlayer().getFlip(), s1_frames, skillSpeed, Animation.PlayMode.NORMAL);
		if(!skill.isAnimationFinished(getSkillTimer(0))) {
			return new TexturesPacker(new TexturePacker(skill.getKeyFrame(getSkillTimer(0), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
		}
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), bats_frames, skillSpeed, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(0) - skillSpeed*s1_frames.length, false), 400, 135, CenterFX_X(getSize().getX(), bats_width), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
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
		
		playSkill(2, 0);
		
		float skillSpeed = 0.08f;
		Animation skill = AnimationHandler.getAnimation(getPlayer().getFlip(), s3_frames, skillSpeed, Animation.PlayMode.NORMAL);
		if(!skill.isAnimationFinished(getSkillTimer(2))) {
			return new TexturesPacker(new TexturePacker(skill.getKeyFrame(getSkillTimer(2), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
		}
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), laser_frames, skillSpeed, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(2) - skillSpeed*s3_frames.length, false), 400, 135, CenterFX_X(getSize().getX(), laser_width), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill4() {
		addSkillTimer(3);
		
		playSkill(3, 0);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s4_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(3), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawDead() {
		addDeathTime();
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), death_frames, 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(getDeathTimer(), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected void applyRunningParticles() {
		applyRunningParticles(4);
	}
}
