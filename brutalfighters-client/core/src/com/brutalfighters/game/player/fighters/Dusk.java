package com.brutalfighters.game.player.fighters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.sound.GameSFX;
import com.brutalfighters.game.utility.rendering.AnimationHandler;
import com.brutalfighters.game.utility.rendering.RenderUtility;
import com.brutalfighters.game.utility.rendering.TextureHandle;
import com.brutalfighters.game.utility.rendering.TexturePacker;
import com.brutalfighters.game.utility.rendering.TexturesPacker;

public class Dusk extends Fighter {

	private static TextureRegion[] s1_frames, s2_frames, s3_frames, s4_frames, bats_frames, laser_frames;
	
	private float bats_width = getSize().getX(), laser_width = getSize().getX();
	
	public static void load() {
		setSkill1Frames(FighterFactory.Dusk.getSprites(0,5,5,6));
		setSkill2Frames(FighterFactory.Dusk.getSprites(0,6,10,7));
		setSkill3Frames(FighterFactory.Dusk.getSprites(0,7,4,8));
		setSkill4Frames(FighterFactory.Dusk.getSprites(0,8,6,9));
		
		TextureRegion[][] bats_fx = TextureHandle.TextureSplit(FighterFactory.Dusk.getFighterPath() + FighterFactory.Dusk.name().toLowerCase() + "_bats_right.png", 160, 80, true, false); //$NON-NLS-1$
		setBatsFrames(TextureHandle.ApplyFrames(0, 0, 4, 1, bats_fx));

		TextureRegion[][] laser_fx = TextureHandle.TextureSplit(FighterFactory.Dusk.getFighterPath() + FighterFactory.Dusk.name().toLowerCase() + "_laser_right.png", 160, 80, true, false); //$NON-NLS-1$
		setLaserFrames(TextureHandle.ApplyFrames(0, 0, 3, 1, laser_fx));
	}
	
	private static TextureRegion[] getSkill1Frames() {
		return s1_frames;
	}
	private static void setSkill1Frames(TextureRegion[] frames) {
		Dusk.s1_frames = frames;
	}
	
	private static TextureRegion[] getSkill2Frames() {
		return s2_frames;
	}
	private static void setSkill2Frames(TextureRegion[] frames) {
		Dusk.s2_frames = frames;
	}
	
	private static TextureRegion[] getSkill3Frames() {
		return s3_frames;
	}
	private static void setSkill3Frames(TextureRegion[] frames) {
		Dusk.s3_frames = frames;
	}
	
	private static TextureRegion[] getSkill4Frames() {
		return s4_frames;
	}
	private static void setSkill4Frames(TextureRegion[] frames) {
		Dusk.s4_frames = frames;
	}
	
	private static TextureRegion[] getBatsFrames() {
		return bats_frames;
	}
	private static void setBatsFrames(TextureRegion[] frames) {
		Dusk.bats_frames = frames;
	}
	
	private static TextureRegion[] getLaserFrames() {
		return laser_frames;
	}
	private static void setLaserFrames(TextureRegion[] frames) {
		Dusk.laser_frames = frames;
	}
	
	protected Dusk(PlayerData pdata) {
		super(pdata);
	}
	
	private float getBatsWidth() {
		return bats_width;
	}
	
	private float getLaserWidth() {
		return laser_width;
	}
	
	@Override
	protected TexturesPacker drawBreath() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Dusk.getBreathFrames(), 0.27f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawWalking() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Dusk.getWalkFrames(), 0.2f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawRunning() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Dusk.getRunFrames(), 0.16f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawAAttack() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Dusk.getAAttackFrames(), 0.1f, Animation.PlayMode.LOOP).getKeyFrame(GameTime.getTime(), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill1() {
		addSkillTimer(0);
		
		playSkill(GameSFX.Bats, 0, 300);
		
		float skillSpeed = 0.08f;
		Animation skill = AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill1Frames(), skillSpeed, Animation.PlayMode.NORMAL);
		if(!skill.isAnimationFinished(getSkillTimer(0))) {
			return new TexturesPacker(new TexturePacker(skill.getKeyFrame(getSkillTimer(0), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
		}
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), getBatsFrames(), skillSpeed, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(0) - skillSpeed*getSkill1Frames().length, false), 400, 135, CenterFX_X(getSize().getX(), getBatsWidth()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill2() {
		addSkillTimer(1);
		
		playSkill(GameSFX.DarkTeleport, 1, 0);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill2Frames(), 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(1), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill3() {
		addSkillTimer(2);
		
		playSkill(GameSFX.LaserCharge, 2, 0);
		
		float skillSpeed = 0.08f;
		Animation skill = AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill3Frames(), skillSpeed, Animation.PlayMode.NORMAL);
		if(!skill.isAnimationFinished(getSkillTimer(2))) {
			return new TexturesPacker(new TexturePacker(skill.getKeyFrame(getSkillTimer(2), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
		}
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), getLaserFrames(), skillSpeed, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(2) - skillSpeed*getSkill3Frames().length, false), 400, 135, CenterFX_X(getSize().getX(), getLaserWidth()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill4() {
		addSkillTimer(3);
		
		playSkill(GameSFX.MagicSwarm, 3, 0);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill4Frames(), 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(3), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawDead() {
		addDeathTime();
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Dusk.getDeathFrames(), 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(getDeathTimer(), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected void applyRunningParticles() {
		applyRunningParticles(4);
	}
}
