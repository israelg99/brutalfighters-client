package com.brutalfighters.game.player.fighters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.sound.GameSFX;
import com.brutalfighters.game.utility.ServerInfo;
import com.brutalfighters.game.utility.rendering.AnimationHandler;
import com.brutalfighters.game.utility.rendering.RenderUtility;
import com.brutalfighters.game.utility.rendering.TextureHandle;
import com.brutalfighters.game.utility.rendering.TexturePacker;
import com.brutalfighters.game.utility.rendering.TexturesPacker;

public class Blaze extends Fighter {

	private static TextureRegion[] s1_frames, s2_frames, s3_frames, s4_frames, s1_fx_frames;
	
	private float skill1_fx_width = getSize().getX() * 3; // It should be getSize().getX() * 4 in total, one getSize().getX() is the fighter itself.
	private boolean isPhoenixPlayed;
	
	public static void load() {
		setSkill1Frames(FighterFactory.Blaze.getSprites(0,5,8,6));
		setSkill2Frames(FighterFactory.Blaze.getSprites(0,6,7,7));
		setSkill3Frames(FighterFactory.Blaze.getSprites(0,7,10,8));
		setSkill4Frames(FighterFactory.Blaze.getSprites(0,8,10,9));
		
		TextureRegion[][] s1_fx = TextureHandle.TextureSplit(FighterFactory.Blaze.getFighterPath() + FighterFactory.Blaze.name().toLowerCase() + "_fx_right.png", 380, 80, true, false); //$NON-NLS-1$
		setSkill1FX(TextureHandle.ApplyFrames(0, 0, 1, 4, s1_fx));
	}
	
	private static TextureRegion[] getSkill1Frames() {
		return s1_frames;
	}
	private static void setSkill1Frames(TextureRegion[] frames) {
		Blaze.s1_frames = frames;
	}
	
	private static TextureRegion[] getSkill2Frames() {
		return s1_frames;
	}
	private static void setSkill2Frames(TextureRegion[] frames) {
		Blaze.s2_frames = frames;
	}
	
	private static TextureRegion[] getSkill3Frames() {
		return s3_frames;
	}
	private static void setSkill3Frames(TextureRegion[] frames) {
		Blaze.s3_frames = frames;
	}
	
	private static TextureRegion[] getSkill4Frames() {
		return s4_frames;
	}
	private static void setSkill4Frames(TextureRegion[] frames) {
		Blaze.s4_frames = frames;
	}
	
	private static TextureRegion[] getSkill1FX() {
		return s1_fx_frames;
	}
	private static void setSkill1FX(TextureRegion[] frames) {
		Blaze.s1_fx_frames = frames;
	}
	
	protected Blaze(PlayerData pdata) {
		super(pdata);
		setPhoenixPlayed(false);
	}
	
	public boolean isPhoenixPlayed() {
		return isPhoenixPlayed;
	}
	public void setPhoenixPlayed(boolean isPhoenixPlayed) {
		this.isPhoenixPlayed = isPhoenixPlayed;
	}

	public float getSkill1FXWidth() {
		return skill1_fx_width;
	}
	public void getSkill1FXWidth(float skill1_fx_width) {
		this.skill1_fx_width = skill1_fx_width;
	}

	@Override
	protected TexturesPacker drawBreath() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Blaze.getBreathFrames(), 0.27f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawWalking() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Blaze.getWalkFrames(), 0.12f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawRunning() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Blaze.getRunFrames(), 0.16f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawAAttack() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Blaze.getAAttackFrames(), 0.1f, Animation.PlayMode.LOOP).getKeyFrame(GameTime.getTime(), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill1() {
		addSkillTimer(0);
		
		playSkill(GameSFX.BloodSkulls, 0, 100);
		
		float skillSpeed = 0.08f;
		Animation skill = AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill1Frames(), skillSpeed, Animation.PlayMode.NORMAL);
		if(!skill.isAnimationFinished(getSkillTimer(0))) {
			return new TexturesPacker(new TexturePacker(skill.getKeyFrame(getSkillTimer(0), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
		}
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill1FX(), skillSpeed, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(0) - skillSpeed*getSkill1Frames().length, false), 800, 135, CenterFX_X(getSize().getX(), getSkill1FXWidth()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill2() {
		addSkillTimer(1);
		
		playSkill(GameSFX.Fireball, 1, 0);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill2Frames(), 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(1), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill3() {
		addSkillTimer(2);
		
		playSkill(GameSFX.BigFireball, 2, 100);
		
		if(getPlayer().getSkillCD()[2] <= ServerInfo.getFPS() * 8) {
			if(!isPhoenixPlayed()) {
				GameSFX.Phoenix.playStereo(getPlayer().getPos().getX());
				setPhoenixPlayed(true);
			}
		} else {
			setPhoenixPlayed(false);
		}
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill3Frames(), 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(2), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill4() {
		addSkillTimer(3);
		
		if(getPlayer().getSkillCD()[3] / ServerInfo.getFPS() % 3 == 0) {
			GameSFX.Project2.playStereo(getPlayer().getPos().getX());
			isSkillPlayed(3, true);
		} else {
			isSkillPlayed(3, false);
		}
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill4Frames(), 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(3), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawDead() {
		addDeathTime();
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Blaze.getDeathFrames(), 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(getDeathTimer(), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected void applyRunningParticles() {
		applyRunningParticles(5);
	}
}
