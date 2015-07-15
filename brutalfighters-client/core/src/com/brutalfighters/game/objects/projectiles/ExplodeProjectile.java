package com.brutalfighters.game.objects.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ExplodeProjectile extends Projectile {

	private float timer;
	
	public ExplodeProjectile(ProjectileData p) {
		super(p);
		timer = 0;
	}
	
	@Override
	public Animation getAnimation() {
		return getProjectile().getExplodedAnimation(this);
	}
	@Override
	public TextureRegion getFrame() {
		return getAnimation().getKeyFrame(getTimer(), true);
	}
	
	public float getTimer() {
		return timer;
	}
	public void addTimer() {
		timer += Gdx.graphics.getDeltaTime();
	}
	
}
