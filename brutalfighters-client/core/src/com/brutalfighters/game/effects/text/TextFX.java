package com.brutalfighters.game.effects.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brutalfighters.game.HUD.GameFont;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.basic.Update;
import com.brutalfighters.game.player.Player;
import com.brutalfighters.game.resources.Resources;
import com.brutalfighters.game.utility.GameMath;


public enum TextFX {
	BloodEffect(GameFont.Blood,
					300) {
		
		@Override
		public void update(TextEffect te) {
			te.y += ((te.time - time/2) * 2) * Gdx.graphics.getDeltaTime();
			te.x += GameMath.nextInt(-15, 20) * Gdx.graphics.getDeltaTime();
		}
	},
	
	HealEffect(GameFont.Heal,
					300) {
		
		@Override
		public void update(TextEffect te) {
			te.y += ((te.time - time/2) * 2) * Gdx.graphics.getDeltaTime();
			te.x += GameMath.nextInt(-15, 25) * Gdx.graphics.getDeltaTime();
		}
	};

	public final int time;
	public final GameFont gameFont;
	
	TextFX(GameFont font, int time) {
		this.gameFont = font;
		this.time = time;
	}
	
	public void update(TextEffect te) {
		// Nothing lol.
	}
	public void draw(SpriteBatch batch, TextEffect te) {
		gameFont.getFont().draw(batch, te.text, te.x, te.y);
	}
	
	public static void render(SpriteBatch batch, TextEffect te, int i) {
		
		TextFX fx = valueOf(te.name);
		
		fx.update(te);
		if(te.time > 0) {
			fx.draw(batch, te);
		} else {
			Resources.textEffects.remove(i);
		}
		te.time -= Update.getDeltaMS();
	}
	
	public TextEffect getDefault(Player player) {
		return new TextEffect(this.name(), Integer.toString(player.changedHealth()), 
				player.getX() + GameMath.nextInt(-player.getWidth() / 3, 0),
				player.getY() + player.getHeight() / 2 + HUD.barHeight()*2+10,
				time);
	}
	
	public static TextEffect getDefault(String name, Player player) {
		return valueOf(name).getDefault(player);
	}
}
