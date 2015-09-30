package com.brutalfighters.game.effects.text.effects;

import com.badlogic.gdx.Gdx;
import com.brutalfighters.game.HUD.GameFont;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.math.Vec2;
import com.brutalfighters.game.player.fighters.Fighter;
import com.brutalfighters.game.utility.GameMath;

public class TextHeal extends TextEffect {

	public TextHeal(String text, Vec2 pos, int time, int MAX_TIME) {
		super(text, pos, time, MAX_TIME, GameFont.Heal);
	}
	public TextHeal(String text, Vec2 pos, int time) {
		this(text, pos, time, 300);
	}
	public TextHeal(String text, Vec2 pos) {
		this(text, pos, 300);
	}
	public TextHeal(Fighter fighter) {
		this(Integer.toString((int)fighter.changedHealth()), 
				new Vec2(fighter.getPlayer().getPos().getX() + GameMath.nextFloat(-fighter.getPlayer().getSize().getX() / 3, 0),
				fighter.getPlayer().getPos().getY() + fighter.getPlayer().getSize().getY() / 2 + HUD.barHeight()*2+10));
	}

	@Override
	public void update() {
		getPos().addY(((getMaxTime() - getTime()/2) * 2) * Gdx.graphics.getDeltaTime());
		getPos().addX(GameMath.nextInt(-15, 25) * Gdx.graphics.getDeltaTime());
	}

}
