package com.brutalfighters.game.utility.rendering;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brutalfighters.game.objects.projectiles.ExplodeProjectile;
import com.brutalfighters.game.objects.projectiles.Projectile;
import com.brutalfighters.game.player.fighters.Fighter;

public class TexturePacker {
	private TextureRegion texture;
	private float width, height;
	private float x,y;
	
	public TexturePacker(TextureRegion tex, float width, float height, float x, float y) {
		this.setTexture(tex);
		this.setWidth(width);
		this.setHeight(height);
		this.setX(x);
		this.setY(y);
	}
	public TexturePacker(TextureRegion tex, Fighter p) {
		this(tex, p.getSize().getX(), p.getSize().getY(), p.getPlayer().getPos().getX(), p.getPlayer().getPos().getY());
	}
	public TexturePacker(TextureRegion tex, Projectile p) {
		this(tex, p.getWidth(), p.getHeight(), p.getData().x-p.getWidth()/2, p.getData().y-p.getHeight()/2);
	}
	public TexturePacker(TextureRegion tex, ExplodeProjectile p) { // Doesn't really matter because ExplodeProjectile inherit Projectile, and we have a constructor for Projectile already.
		this(tex, p.getWidth(), p.getHeight(), p.getData().x-p.getWidth()/2, p.getData().y-p.getHeight()/2);
	}
	
	public TextureRegion getTexture() {
		return texture;
	}
	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}
	
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
}
