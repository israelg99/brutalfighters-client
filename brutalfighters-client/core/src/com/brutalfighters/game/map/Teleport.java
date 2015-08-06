package com.brutalfighters.game.map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.effects.particles.ParticleEffects;
import com.brutalfighters.game.effects.particles.ParticlesCollection;
import com.brutalfighters.game.math.Vec2;
import com.brutalfighters.game.resources.Assets;
import com.brutalfighters.game.utility.rendering.AnimationHandler;
import com.brutalfighters.game.utility.rendering.RenderUtility;
import com.brutalfighters.game.utility.rendering.TextureHandle;
import com.brutalfighters.game.utility.rendering.TexturePacker;

public class Teleport {
	private static TextureRegion[] teleport_frames;
	private static int WIDTH, HEIGHT;
	private static int GROUND_ADD;
	
	private Vec2 pos, targetPos;
	
	public static void load() {
		
		WIDTH = 180;
		HEIGHT = 250;
		GROUND_ADD = 35;
		
		TextureRegion[][] fullSprite = TextureHandle.TextureSplit("teleport/teleport.png", 240, 300, true, false); //$NON-NLS-1$
		teleport_frames = TextureHandle.ApplyFrames(0, 0, 3, 1, fullSprite);
	}
	
	public Teleport(Vec2 pos, Vec2 targetPos) {
		this.pos = pos;
		this.targetPos = targetPos;
		
		ParticleEffects.add(ParticlesCollection.TP_Spark, pos.getX(), pos.getY()+35, true);
	}
	
	public Teleport(int x, int y, int tx, int ty) { // x,y = pos,   tx,ty = targetPos
		this(new Vec2(x,y), new Vec2(tx,ty));
	}
	
	public Teleport(int x, int y, String tpos) {
		this(new Vec2(Assets.map.toPixelX(x), Assets.map.toPixelY(y)), new Vec2(Assets.map.toPixelX(Integer.parseInt(tpos.split(",")[0])), Assets.map.toPixelY(Integer.parseInt(tpos.split(",")[1])))); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public Vec2 getPos() {
		return pos;
	}
	public Vec2 getTargetPos() {
		return targetPos;
	}
	
	public static TexturePacker getDraw(Vec2 pos) {
		return new TexturePacker(AnimationHandler.getAnimation("right", teleport_frames, 0.1f, Animation.PlayMode.LOOP_PINGPONG).getKeyFrame(GameTime.getTime(), true), WIDTH, HEIGHT, RenderUtility.CenterX(pos.getX(), WIDTH), RenderUtility.CenterY(pos.getY(), HEIGHT)); //$NON-NLS-1$
	}
	
	public void drawTeleport() {
		TexturePacker sprite = getDraw(pos);
		Render.getSpriteBatch().draw(sprite.getTexture(), sprite.getX(), sprite.getY() + GROUND_ADD, sprite.getWidth(), sprite.getHeight());
	}
}
