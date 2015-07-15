package com.brutalfighters.game.objects.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.basic.Update;
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
		getData().x += getData().velx * ServerInfo.getDIVIDE() * Gdx.graphics.getDeltaTime();
		getData().y += getData().vely * ServerInfo.getDIVIDE() * Gdx.graphics.getDeltaTime();		
	}
	
	public Animation getAnimation() {
		return getProjectile().getAnimation(this);
	}
	public TextureRegion getFrame() {
		return getAnimation().getKeyFrame(Update.getTime(), true);
	}
	public  TexturePacker getSprite() {
		return new TexturePacker(getFrame(), this);
	}
	public int getWidth() {
		return getProjectile().WIDTH;
	}
	public int getHeight() {
		return getProjectile().HEIGHT;
	}
	public ProjectilesEnum getProjectile() {
		return ProjectilesEnum.valueOf(getData().name);
	}
}