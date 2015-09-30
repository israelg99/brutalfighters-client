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

public class Surge extends Fighter {

	private static TextureRegion[] s1_frames, s2_frames, s3_frames, s4_frames, s2_fx_frames, s4_fx_frames;
	
	private float s2_fx_width;
	private boolean isLightningPlayed;
	
	public static void load() {
		setSkill1Frames(FighterFactory.Surge.getSprites(0,5,6,6));
		setSkill2Frames(FighterFactory.Surge.getSprites(0,6,8,7));
		setSkill3Frames(FighterFactory.Surge.getSprites(0,7,8,8));
		setSkill4Frames(FighterFactory.Surge.getSprites(0,8,13,9));
		
		TextureRegion[][] s2_fx = TextureHandle.TextureSplit(FighterFactory.Surge.getFighterPath() + FighterFactory.Surge.name().toLowerCase() + "_fx_right.png", 160, 80, true, false); //$NON-NLS-1$
		setSkill2FX(TextureHandle.ApplyFrames(0, 0, 3, 1, s2_fx));

		TextureRegion[][] s4_fx = TextureHandle.TextureSplit(FighterFactory.Surge.getFighterPath() + "lightning_right.png", 170, 415, true, false); //$NON-NLS-1$
		setSkill4FX(TextureHandle.ApplyFrames(0, 0, 4, 1, s4_fx));
	}

	private static TextureRegion[] getSkill1Frames() {
		return s1_frames;
	}
	private static void setSkill1Frames(TextureRegion[] frames) {
		Surge.s1_frames = frames;
	}
	
	private static TextureRegion[] getSkill2Frames() {
		return s2_frames;
	}
	private static void setSkill2Frames(TextureRegion[] frames) {
		Surge.s2_frames = frames;
	}
	
	private static TextureRegion[] getSkill3Frames() {
		return s3_frames;
	}
	private static void setSkill3Frames(TextureRegion[] frames) {
		Surge.s3_frames = frames;
	}
	
	private static TextureRegion[] getSkill4Frames() {
		return s4_frames;
	}
	private static void setSkill4Frames(TextureRegion[] frames) {
		Surge.s4_frames = frames;
	}
	
	private static TextureRegion[] getSkill2FX() {
		return s2_fx_frames;
	}
	private static void setSkill2FX(TextureRegion[] frames) {
		Surge.s2_fx_frames = frames;
	}
	
	private static TextureRegion[] getSkill4FX() {
		return s4_fx_frames;
	}
	private static void setSkill4FX(TextureRegion[] frames) {
		Surge.s4_fx_frames = frames;
	}

	protected Surge(PlayerData pdata) {
		super(pdata);
		s2_fx_width = getSize().getX(); // 2*getSize().getX() total, but this variable stores only the FX width, fighter not included.
		setLightningPlayed(false);
	}
	
	public boolean isLightningPlayed() {
		return isLightningPlayed;
	}
	public void setLightningPlayed(boolean isLightiningPlayed) {
		this.isLightningPlayed = isLightiningPlayed;
	}

	public float getSkill2FXWidth() {
		return s2_fx_width;
	}
	public void setSkill2FXWidth(float s2_fx_width) {
		this.s2_fx_width = s2_fx_width;
	}

	@Override
	protected TexturesPacker drawBreath() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Surge.getBreathFrames(), 0.27f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawWalking() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Surge.getWalkFrames(), 0.13f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawRunning() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Surge.getRunFrames(), 0.16f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawAAttack() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Surge.getAAttackFrames(), 0.12f, Animation.PlayMode.LOOP).getKeyFrame(GameTime.getTime(), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill1() {
		addSkillTimer(0);
		
		playSkill(GameSFX.Electricity, 0, 0);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill1Frames(), 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(0), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill2() {
		addSkillTimer(1);
		
		playSkill(GameSFX.MagicSwarm, 1, 400);
		
		float skillSpeed = 0.08f;
		Animation skill = AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill2Frames(), skillSpeed, Animation.PlayMode.NORMAL);
		if(!skill.isAnimationFinished(getSkillTimer(1))) {
			return new TexturesPacker(new TexturePacker(skill.getKeyFrame(getSkillTimer(1), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
		}
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill2FX(), skillSpeed, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(1) - skillSpeed*getSkill2Frames().length, false), 400, 135, CenterFX_X(getSize().getX(), getSkill2FXWidth()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill3() {
		addSkillTimer(2);
		
		playSkill(GameSFX.FastBlock, 2, 300);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill3Frames(), 0.08f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(2), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill4() {
		addSkillTimer(3);
		
		playSkill(GameSFX.StrongCharge, 3, 0);
		
		float skillspeed = 0.08f;
		TexturesPacker textures = new TexturesPacker();
		textures.add(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill4Frames(), skillspeed, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(3), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
		if(getSkillTimer(3) >= 9*skillspeed && getSkillTimer(3) <= 13*skillspeed) {
			textures.add(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill4FX(), skillspeed, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(3) - 9*skillspeed, false), 410, 705, RenderUtility.CenterX(getPlayer().getPos().getX()-getSize().getX()-125, 410), RenderUtility.CenterY(getPlayer().getPos().getY(), 235)));
			textures.add(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), getSkill4FX(), skillspeed, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(3) - 9*skillspeed, false), 410, 705, RenderUtility.CenterX(getPlayer().getPos().getX()+getSize().getX()+125, 410), RenderUtility.CenterY(getPlayer().getPos().getY(), 235)));
			if(!isLightningPlayed()) {
				GameSFX.Lightning.playStereo(getPlayer().getPos().getX());
				setLightningPlayed(true);
			}
		} else {
			setLightningPlayed(false);
		}
		return textures;
	}

	@Override
	protected TexturesPacker drawDead() {
		addDeathTime();
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), FighterFactory.Surge.getDeathFrames(), 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(getDeathTimer(), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected void applyRunningParticles() {
		applyRunningParticles(1);
	}
}
