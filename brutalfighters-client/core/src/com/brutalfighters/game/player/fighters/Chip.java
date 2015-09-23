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

public class Chip extends Fighter {
	
	private TextureRegion[] s1_frames, s2_frames, s3_frames, s4_frames;
	
	protected Chip(PlayerData pdata) {
		super(pdata);
	}

	@Override
	protected void loadSprite() {
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
	protected TexturesPacker drawBreath() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), breath_frames, 0.27f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getX(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getX())));
	}

	@Override
	protected TexturesPacker drawWalking() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), walk_frames, 0.13f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getX(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getX())));
	}

	@Override
	protected TexturesPacker drawRunning() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), run_frames, 0.16f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getX(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getX())));
	}

	@Override
	protected TexturesPacker drawAAttack() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), aattack_frames, 0.12f, Animation.PlayMode.LOOP).getKeyFrame(GameTime.getTime(), false), getSize().getX(), getSize().getX(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getX())));
	}

	@Override
	protected TexturesPacker drawSkill1() {
		addSkillTimer(0);
		
		playSkill(0, 450);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s1_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(0), false), getSize().getX(), getSize().getX(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getX())));
	}

	@Override
	protected TexturesPacker drawSkill2() {
		addSkillTimer(1);
		
		playSkill(1, 0);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s2_frames, 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(1), false), getSize().getX(), getSize().getX(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getX())));
	}

	@Override
	protected TexturesPacker drawSkill3() {
		addSkillTimer(2);
		
		playSkill(2, 180);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s3_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(2), false), getSize().getX(), getSize().getX(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getX())));
	}

	@Override
	protected TexturesPacker drawSkill4() {
		addSkillTimer(3);
		
		playSkill(3, 250);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s4_frames, 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(3), false), getSize().getX(), getSize().getX(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getX())));
	}

	@Override
	protected TexturesPacker drawDead() {
		addDeathTime();
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), death_frames, 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(getDeathTimer(), false), getSize().getX(), getSize().getX(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getX())));
	}

	@Override
	protected void applyRunningParticles() {
		applyRunningParticles(3);
	}
}
