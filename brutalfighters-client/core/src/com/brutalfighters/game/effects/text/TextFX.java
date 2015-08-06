package com.brutalfighters.game.effects.text;

import com.badlogic.gdx.Gdx;
import com.brutalfighters.game.HUD.GameFont;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.basic.Render;
import com.brutalfighters.game.player.Player;
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
	public void draw(TextEffect te) {
		gameFont.getFont().draw(Render.getSpriteBatch(), te.text, te.x, te.y);
	}

	public TextEffect getDefault(Player player) {
		return new TextEffect(this, Integer.toString(player.changedHealth()), 
				player.getX() + GameMath.nextInt(-player.getWidth() / 3, 0),
				player.getY() + player.getHeight() / 2 + HUD.barHeight()*2+10,
				time);
	}
	
	
	public static void init() {
		values();
	}
}
