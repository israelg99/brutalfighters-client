package com.brutalfighters.game.HUD;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brutalfighters.game.math.ScreenPosHandler;
import com.brutalfighters.game.resources.Assets;
import com.brutalfighters.game.utility.rendering.TextureHandle;

public class EscapeMenu {
	private static Sprite BG;
		
	private static BitmapFont font_text_button;
	private static BitmapFont font_text_button_hover;

	public static final int WIDTH = 550;
	public static final int HEIGHT = 400;
	
	private static final int CENTER_X = ScreenPosHandler.getCenterScreenX(WIDTH);
	private static final int CENTER_Y = ScreenPosHandler.getCenterScreenY(HEIGHT);
	
	private static final int textW = 220;
	private static final int textH = 70;
	
	private static final int textYADD = 0;
	
	private static int idSelect = 1;
	private static final int idMax = 4;
	
	public static void load() {
		BG = TextureHandle.getSprite("menu/ui/menugamebg1.png"); //$NON-NLS-1$
		font_text_button = GameFont.EscapeButton.getFont();
		font_text_button_hover = GameFont.EscapeButtonHover.getFont();
	}
	
	public static void draw(SpriteBatch batch) {
		batch.draw(BG, CENTER_X, CENTER_Y - HEIGHT, WIDTH, HEIGHT);
		drawCheck(batch, 1, "Resume Game"); //$NON-NLS-1$
		drawCheck(batch, 2, "Settings Menu"); //$NON-NLS-1$
		drawCheck(batch, 3, "Main Menu"); //$NON-NLS-1$
		drawCheck(batch, 4, "Quit Game"); //$NON-NLS-1$
	}
	
	private static void drawCheck(SpriteBatch batch, int id, String txt) {
		if(id == idSelect) {
			font_text_button.draw(batch, txt, ScreenPosHandler.getPosCenterX(CENTER_X,WIDTH, textW), CENTER_Y - (textH * id + textYADD));
		} else {
			font_text_button_hover.draw(batch, txt, ScreenPosHandler.getPosCenterX(CENTER_X,WIDTH, textW), CENTER_Y - (textH * id + textYADD));
		}
	}
	
	public static void addID() {
		idSelect = idSelect == idMax ? idMax : idSelect + 1;
	}
	public static void subID() {
		idSelect = idSelect == 1 ? 1 : idSelect - 1;
	}
	public static void setID(int i) {
		idSelect = i;
	}
	
	public static void click() {
		EscapeOption.values()[idSelect - 1].trigger();
	}
	
	public static void disableEM() {
		HUD.showEscapeMenu(false);
		Assets.client.controlHimself(true);
		setID(1);
	}
}
