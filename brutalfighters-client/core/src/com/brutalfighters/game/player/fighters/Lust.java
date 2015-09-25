package com.brutalfighters.game.player.fighters;

import com.badlogic.gdx.audio.Sound;
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

public class Lust extends Fighter {

	private TextureRegion[] s1_frames, s2_frames;
	private TextureRegion s4_frame;
	
	protected Lust(PlayerData pdata) {
		super(pdata);
	}

	@Override
	protected void loadSprite() {
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
	protected TexturesPacker drawBreath() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), breath_frames, 0.2f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawWalking() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), walk_frames, 0.13f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawRunning() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), run_frames, 0.16f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawAAttack() {
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), aattack_frames, 0.115f, Animation.PlayMode.LOOP).getKeyFrame(GameTime.getTime(), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill1() {
		addSkillTimer(0);
		
		playSkill(0, 0);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s1_frames, 0.062f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(0), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill2() {
		addSkillTimer(1);
		
		playSkill(1, 200);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), s2_frames, 0.062f, Animation.PlayMode.NORMAL).getKeyFrame(getSkillTimer(1), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawSkill3() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TexturesPacker drawSkill4() {
		addSkillTimer(3);
		
		playSkill(3, 100);
		
		return new TexturesPacker(new TexturePacker(AnimationHandler.returnAfterCheck(getPlayer().getFlip(), s4_frame), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected TexturesPacker drawDead() {
		addDeathTime();
		return new TexturesPacker(new TexturePacker(AnimationHandler.getAnimation(getPlayer().getFlip(), death_frames, 0.1f, Animation.PlayMode.NORMAL).getKeyFrame(getDeathTimer(), false), getSize().getX(), getSize().getY(), RenderUtility.CenterX(getPlayer().getPos().getX(), getSize().getX()), RenderUtility.CenterY(getPlayer().getPos().getY(), getSize().getY())));
	}

	@Override
	protected void applyRunningParticles() {
		applyRunningParticles(2);
	}
	
	@Override
	public void loadSFX() {			
		setSkillSFX(new Sound[getSkills()]);
		getSkillSFX()[0] = SoundUtil.getSound(getPath() + "skill1.wav"); //$NON-NLS-1$
		getSkillSFX()[1]  = SoundUtil.getSound(getPath() + "skill2.wav"); //$NON-NLS-1$
		getSkillSFX()[3]  = SoundUtil.getSound(getPath() + "skill4.wav"); //$NON-NLS-1$
	}

}
