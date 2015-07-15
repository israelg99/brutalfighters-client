package com.brutalfighters.game.player;


public class CharSprite {
	
	/*private TextureRegion[] walk_frames, run_frames, aattack_frames, breath_frames, death_frames,
							s1_frames, s2_frames;
	private TextureRegion stand, jump, death;
	
	public final int BLOCK_SIZE_WIDTH = 80;
	public final int BLOCK_SIZE_HEIGHT = 80;
	
	private final int COLS_LENGTH;
	private final int ROWS_LENGTH;
	
	private TextureRegion[][] sheet;
	
	private int width;
	private int height;
	
	private float timeWalkSteps;
	private float timeRunSteps;
	
	public CharSprite(String champ, int sizeW, int sizeH) {
		TextureRegion[][] sheet = TextureHandle.TextureSplit("char_sprites/" + champ + "_right.png", BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, true, false);
		COLS_LENGTH = sheet[0].length;
		ROWS_LENGTH = sheet.length;
		
		walk_frames = TextureHandle.ApplyFrames(4, 0, 8, 1, sheet);
		
		run_frames = TextureHandle.ApplyFrames(0, 2, 3, 3, sheet);
		
		aattack_frames = TextureHandle.ApplyFrames(0, 1, 8, 2, sheet);
		
		breath_frames = TextureHandle.ApplyFrames(0, 0, 4, 1, sheet);
		
		death_frames = TextureHandle.ApplyFrames(0, 3, 6, 4, sheet);
		
		s1_frames = TextureHandle.ApplyFrames(0, 10, 8, 11, sheet);
		
		s2_frames = TextureHandle.ApplyFrames(0, 7, 7, 8, sheet);
		
		stand = sheet[0][0];
		
		jump = sheet[6][2];

		death = sheet[3][5];
		
		width = sizeW;
		height = sizeH;
		
		timeWalkSteps = 0.5f;
		timeRunSteps = 0.3f;
	}
	
	public Animation getWalkAnimation(String flip) {
		return AnimationHandler.getAnimation(flip, walk_frames, 0.13f, Animation.PlayMode.LOOP_PINGPONG);
	}
	public Animation getRunAnimation(String flip) {
		return AnimationHandler.getAnimation(flip, run_frames, 0.16f, Animation.PlayMode.LOOP_PINGPONG);
	}
	public Animation getAAttackAnimation(String flip) {
		return AnimationHandler.getAnimation(flip, aattack_frames, 0.15f, Animation.PlayMode.LOOP);
	}
	public Animation getBreathAnimation(String flip) {
		return AnimationHandler.getAnimation(flip, breath_frames, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
	}
	public Animation getDeathAnimation(String flip) {
		return AnimationHandler.getAnimation(flip, death_frames, 0.1f, Animation.PlayMode.NORMAL);
	}
	public Animation getS1Animation(String flip) {
		return AnimationHandler.getAnimation(flip, s1_frames, 0.062f, Animation.PlayMode.NORMAL);
		
	}
	public Animation getS2Animation(String flip) {
		return AnimationHandler.getAnimation(flip, s2_frames, 0.062f, Animation.PlayMode.NORMAL);
	}
	
	public TextureRegion getStand(String flip) {
		return AnimationHandler.returnAfterCheck(flip, stand);
	}
	public TextureRegion getJump(String flip) {
		return AnimationHandler.returnAfterCheck(flip, jump);
	}
	public TextureRegion getDeath(String flip) {
		return AnimationHandler.returnAfterCheck(flip, death);
	}
	
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public float getWalkSteps() {
		return timeWalkSteps;
	}
	public float getRunSteps() {
		return timeRunSteps;
	}*/
}
