package com.brutalfighters.game.objects.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.basic.GameTime;
import com.brutalfighters.game.utility.ServerInfo;
import com.brutalfighters.game.utility.rendering.TexturePacker;

public class Projectile {
	private ProjectileData projectile;
	
	public Projectile(ProjectileData p) {
		this.projectile = p;
	}
	
	public void update() {
		getProjectile().update(this);
	}
	
	public ProjectileData getData() {
		return projectile;
	}
	
	public void updateVelocities() {
		getData().getPos().addX(getData().getVel().getX() * ServerInfo.getDIVIDE() * Gdx.graphics.getDeltaTime());
		getData().getPos().addY(getData().getVel().getY() * ServerInfo.getDIVIDE() * Gdx.graphics.getDeltaTime());		
	}
	
	public Animation getAnimation() {
		return getProjectile().getAnimation(this);
	}
	public TextureRegion getFrame() {
		return getAnimation().getKeyFrame(GameTime.getTime(), true);
	}
	public  TexturePacker getSprite() {
		return new TexturePacker(getFrame(), this);
	}
	public float getWidth() {
		return getProjectile().getSize().getX();
	}
	public float getHeight() {
		return getProjectile().getSize().getY();
	}
	public ProjectilesEnum getProjectile() {
		return ProjectilesEnum.valueOf(getData().getName());
	}
}