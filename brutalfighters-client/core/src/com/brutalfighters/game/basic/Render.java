package com.brutalfighters.game.basic;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.brutalfighters.game.InputControls;
import com.brutalfighters.game.HUD.GameFont;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.effects.particles.Particles;
import com.brutalfighters.game.graphics.Shaders;
import com.brutalfighters.game.math.Vec2;
import com.brutalfighters.game.player.ClientPlayer;
import com.brutalfighters.game.player.Player;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.resources.Prefs;
import com.brutalfighters.game.resources.Resources;

public class Render {
	
	public static SpriteBatch batch;
	private static FPSLogger fps;
	private static OrthogonalTiledMapRenderer otmr;
	private static OrthographicCamera fixedCam;
	private static ShapeRenderer shapeRenderer;
	private static BitmapFont font_details;
	
	private static Vec2 res;
	
	public static void Load() {
		System.out.println("Initializing the Batch"); //$NON-NLS-1$
		batch = new SpriteBatch();
		
		System.out.println("Initializing the Game Resolution"); //$NON-NLS-1$
		
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//res = new Vec2((int)screenSize.getWidth(), (int)screenSize.getHeight());
		
		res = new Vec2(1920,1080); // SO IT WILL BE THE SAME FOR EVERYONE, USING NATIVE RESOLUTION IS BAD! BECAUSE FOR SOME THE GAME AND MAP ETC WILL BE BIGGER OR SMALLER!!
		
		System.out.println("Initializing the ShapeRenderer"); //$NON-NLS-1$
		shapeRenderer = new ShapeRenderer();
		
		System.out.println("Initializing the FPSLogger"); //$NON-NLS-1$
		fps = new FPSLogger();
		
		System.out.println("Initializing the Fixed Camera"); //$NON-NLS-1$
		fixedCam = new OrthographicCamera();
		fixedCam.setToOrtho(false, getResX(), getResY());
		
		System.out.println("Initializing the OrthogonalTiledMapRenderer"); //$NON-NLS-1$
		otmr = new OrthogonalTiledMapRenderer(Resources.map.getMap(), Resources.map.getScaled(), batch);
		
		System.out.println("Creating the Details Font"); //$NON-NLS-1$
		font_details = GameFont.Info.getFont();
	}
	
	public static void setCamera(OrthographicCamera cam) {
		batch.setProjectionMatrix(cam.combined);
		otmr.setView(cam);
		shapeRenderer.setProjectionMatrix(cam.combined);
	}
	public static void setCamera(ClientPlayer pl) {
		setCamera(pl.getCamera());
	}
	
	public static void drawRect(Color color, float x, float y, int width, int height) {
		shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
	}
	public static void drawRectFilled(Color color, float x, float y, int width, int height) {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(x, y, width, height);
		shapeRenderer.end();
	}
	
	public static void renderInfo() {
		PlayerData p = Resources.player.getPlayer();
		if (Gdx.input.isKeyPressed(InputControls.showInfo)) {
			font_details.draw(batch, "Version : Alpha || FPS - " + Gdx.graphics.getFramesPerSecond(), 50, Gdx.graphics.getHeight() - 30); //$NON-NLS-1$
		    font_details.draw(batch, "Camera Pos X : " + Resources.client.getCamera().position.x + " Pos Y : " + Resources.client.getCamera().position.y, 50, Gdx.graphics.getHeight() - 80); //$NON-NLS-1$ //$NON-NLS-2$
			font_details.draw(batch, "Player Pos X : " + p.posx + " Pos Y : " + p.posy, 50, Gdx.graphics.getHeight() - 130); //$NON-NLS-1$ //$NON-NLS-2$
			font_details.draw(batch, "Player Vel X : " + p.velx + " Vel Y : " + p.vely, 50, Gdx.graphics.getHeight() - 180); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
	public static void renderTutorial() {
		if (Gdx.input.isKeyPressed(InputControls.TUTORIAL)) {
			HUD.drawTutorial();
		}
	}
	
	public static void clear() {
		Gdx.gl.glClearColor(1F, 1F, 1F, 1F);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public static void renderMap() {
		batch.end();
		otmr.render();
		batch.begin();
	}
	public static void renderTeleports() {
		for(int i = 0; i < Resources.map.getTeleports().size(); i++) {
			Resources.map.getTeleports().get(i).drawTeleport();
		}
	}
	public static void renderBackground() {
		batch.disableBlending();
		batch.setShader(Shaders.motionBlur);
			Resources.bg.render();
		batch.setShader(Shaders.defShader);
		batch.enableBlending();
	}
	public static void renderFlags() {
		Resources.flags.renderFlags();
	}
	
	public static void fpsLog() {
		fps.log();
	}
	
	public static void renderPlayer(Player p) {
		p.draw();
	}
	public static void renderClientPlayer() {
		Resources.player.draw();
	}
	
	public static void renderPlayers() {
		for(int i = 0; i < Resources.players.getLength(); i++) {
			Resources.players.getPlayer(i).draw();
		}
		renderClientPlayer();
	}
	
	public static void renderParticles(boolean before) {
		Particles.render(before);
	}
	
	public static void renderProjectiles() {
		Resources.projectiles.render();
	}
	
	private static void renderTextEffects() {
		Resources.textEffects.renderTextEffects();
	}
	
	public static void renderHUD() {
		HUD.drawHUD();
	}
	
	public static void render() {
		if(Prefs.getPPEffects()) {
			
			if(HUD.toBlur()) {
				renderFBO();
				ppBlurFBO();
			} else {
				draw();
			}
			
			drawHUD();
			
		} else {
			draw();
		}
	}
	
	public static void draw() {
		
		// CLEAN
		clear();
		
		// SCENE
		// ---------
		
		drawScene();
		
		// HUD
		// ---------
		
		drawHUD();
		
	}
	
	public static void drawScene() {
		
		// BATCH BEGINS HERE
		batch.begin();
		
		// CAMERA : Setting the fixed camera
		setFixedCamera();
		
		// Rendering the Background
		renderBackground();
		
		// CAMERA : Setting player's camera 
		setPlayerCamera();
		
		// Rendering the map
		renderMap();
		
		// Rendering the teleports
		renderTeleports();
		
		// Rendering the particles before the player
		renderParticles(true);
		
		// Rendering the flags
		renderFlags();
		
		// Rendering the players
		renderPlayers();
		
		// Rendering the particles after the player
		renderParticles(false);
		
		// Rendering the Projectiles
		renderProjectiles();
		
		// Rendering the Text Effects
		renderTextEffects();
		
		// FLUSHES THE BATCH
		batch.flush();
		batch.end(); // ENDS THE BATCH
	}

	public static void drawHUD() {
		
		// BATCH BEGINS HERE
		batch.begin();
		
		// CAMERA : Setting the fixed camera
		setFixedCamera();
		
		// Render Escape Menu
		renderHUD();
		
		// Render Tutorial
		renderTutorial();
		
		// Rendering Info (Debug)
		renderInfo();
		
		// Logging the FPS
		//fpsLog();

		// FLUSHES THE BATCH
		batch.flush();
		batch.end(); // ENDS THE BATCH
	}
	
	public static void renderFBO() {
		//Bind FBO target A
		Shaders.FBO_A.begin();
		
		//Clear FBO A with an opaque color to minimize blending issues
		clear();
		
		drawScene();
		
		Shaders.FBO_A.end();
	}
	
	public static void ppBlurFBO() {
		
		
		// We need it to perform at fixed position, fixed camera
		setFixedCamera();
		
		//swap the shaders
		//this will send the batch's (FBO-sized) projection matrix to our blur shader
		batch.setShader(Shaders.ppBlur);

		//ensure the direction is along the X-axis only
		Shaders.ppBlur.setUniformf("dir", 1f, 0f); //$NON-NLS-1$
		
		//start rendering to target B
		Shaders.FBO_A.begin();
		
		batch.begin();

		//no need to clear since targetA has an opaque background
		//render target A (the scene) using our horizontal blur shader
		//it will be placed into target A
		TextureRegion temp = new TextureRegion(Shaders.FBO_A.getColorBufferTexture());
		temp.flip(false, true);
		
		batch.draw(temp, 0, 0);

		//flush the batch before ending target A
		batch.flush();

		//finish rendering target A
		Shaders.FBO_A.end();
		
		//apply the blur only along Y-axis
		Shaders.ppBlur.setUniformf("dir", 0f, 1f); //$NON-NLS-1$
		
		//draw the horizontally-blurred FBO B to the screen, applying the vertical blur as we go
		temp = new TextureRegion(Shaders.FBO_A.getColorBufferTexture());
		temp.flip(false, true);
		batch.draw(temp, 0, 0);
		
		batch.setShader(Shaders.defShader);
		
		batch.end();
	}
	
	public static void drawMiddle(Sprite sprite) {
		Render.batch.draw(sprite, Render.getResX()/2-sprite.getWidth()/2, Render.getResY()/2-sprite.getHeight()/2, sprite.getWidth(), sprite.getHeight());
	}
	
	public static void dispose() {
		System.out.println("Disposing The Render class"); //$NON-NLS-1$
		batch.dispose();
		otmr.dispose();
	}
	
	public static int getResX() {
		return res.getX();
	}
	public static int getResY() {
		return res.getY();
	}
	public static void setPlayerCamera() {
		setCamera(Resources.client);
	}
	public static void setFixedCamera() {
		setCamera(fixedCam);
	}
}
