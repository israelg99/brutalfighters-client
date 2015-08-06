package com.brutalfighters.game.objects.projectiles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.utility.rendering.TexturePacker;

public class Projectiles {
	private ArrayList<Projectile> projectiles;
	private ArrayList<ExplodeProjectile> expProjectiles;
	
	public Projectiles() {
		projectiles = new ArrayList<Projectile>();
		expProjectiles = new ArrayList<ExplodeProjectile>();
	}
	
	public void setNew(ProjectileData[] arr) {
		projectiles = new ArrayList<Projectile>();
		for(int i = 0; i < arr.length; i++) {
			ProjectileData p = arr[i];
			if(!p.mode.equals("explode")) { //$NON-NLS-1$
				projectiles.add(new Projectile(p));
			} else {
				expProjectiles.add(new ExplodeProjectile(p));
				ProjectilesEnum.valueOf(p.name).playExplode(p.x);
			}
		}
	}
	
	public Projectile get(int i) {
		return projectiles.get(i);
	}
	public ExplodeProjectile getExp(int i) {
		return expProjectiles.get(i);
	}
	
	public void removeExp(int i) {
		expProjectiles.remove(i);
	}
	
	public ArrayList<Projectile> get() {
		return projectiles;
	}
	public ArrayList<ExplodeProjectile> getExp() {
		return expProjectiles;
	}

	public void render() {
		renderProjectiles();
		renderExpProjectiles();
	}
	
	// ALSO UPDATES TO PREVENT ANOTHER LOOP, IT'S AN INTERPOLATION UPDATE
	public void renderProjectiles() {
		for(int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			TexturePacker sprite = p.getSprite();
			
			p.update();
			
			Render.getSpriteBatch().draw(sprite.getTexture(), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		}
	}
	public void renderExpProjectiles() {
		for(int i = 0; i < expProjectiles.size(); i++) {
			ExplodeProjectile p = expProjectiles.get(i);
			
			p.addTimer();
			
			if(!p.getAnimation().isAnimationFinished(p.getTimer())) {
				TexturePacker sprite = p.getSprite();
				Render.getSpriteBatch().draw(sprite.getTexture(), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
			} else {
				removeExp(i);
			}
		}
	}
	
	public Animation getAnimation(int i) {
		return projectiles.get(i).getAnimation();
	}
	public Animation getExpAnimation(int i) {
		return projectiles.get(i).getAnimation();
	}
}
