package com.brutalfighters.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.brutalfighters.game.HUD.GameFont;
import com.brutalfighters.game.sound.BGM;

public class MenuUtils {
	public static float logoPadBot;
	public static float GLIDE_DURATION;
	public static float FADE_DURATION;
	
	public static String companyLOGO;
	public static String gameLOGO;
	public static String MenuBG;
	
	public static Image gameLogo;
	public static Image bg;
	
	public static Skin skin;
	public static BitmapFont buttonText;
	public static TextButtonStyle blueButton;
	public static TextureAtlas buttonAtlas;
	public static TextureAtlas faceAtlas;
	
	public static void Load() {
		
		logoPadBot = 40f;
		GLIDE_DURATION = 0.35f;
		FADE_DURATION = 0.05f;
		
		companyLOGO = "logos/logo.png"; //$NON-NLS-1$
		gameLOGO = "logos/logo.png"; //$NON-NLS-1$
		MenuBG = "menu/bg/bg3.jpg"; //$NON-NLS-1$
		
		// Logo
		gameLogo = new Image(new Texture(Gdx.files.internal(MenuUtils.gameLOGO)));
		
		// Menu Background
		bg = new Image(new Texture(Gdx.files.internal(MenuUtils.MenuBG)));
		bg.setWidth(Gdx.graphics.getWidth());
		bg.setHeight(Gdx.graphics.getHeight());
		
		// Skin
		skin = new Skin();
		
		// Font
		buttonText = GameFont.TheBoldFont.getFont();
		
		// Creating Buttons
		buttonAtlas = new TextureAtlas(Gdx.files.internal("menu/buttons/mainMenuButton.atlas")); //$NON-NLS-1$
		skin.addRegions(buttonAtlas);
		blueButton = new TextButtonStyle(skin.getDrawable("blueButton"), skin.getDrawable("blueButton_down"), skin.getDrawable("blueButton_hover"), buttonText); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		blueButton.over = skin.getDrawable("blueButton_hover"); //$NON-NLS-1$
		
		// Getting Fighter Faces Buttons Skin
		faceAtlas = new TextureAtlas(Gdx.files.internal("menu/fighters/fighters_faces.atlas")); //$NON-NLS-1$
		MenuUtils.skin.addRegions(faceAtlas);
	}

	public static void reset() {
		Color color = gameLogo.getColor();
		gameLogo.setColor(color.r, color.g, color.b, 1);
		
		color = bg.getColor();
		bg.setColor(color.r, color.g, color.b, 1);
	}
	
	public static void disposeExit() {
		System.err.println("Disposing MenuUtils, which only happens when game exits!"); //$NON-NLS-1$
		BGM.disposeAll();
		skin.dispose();
	}
}
