package com.brutalfighters.game.basic;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.brutalfighters.game.InputControls;
import com.brutalfighters.game.HUD.HUD;
import com.brutalfighters.game.effects.particles.ParticleEffects;
import com.brutalfighters.game.effects.text.TextEffects;
import com.brutalfighters.game.graphics.Shaders;
import com.brutalfighters.game.math.Vec2;
import com.brutalfighters.game.player.ClientPlayer;
import com.brutalfighters.game.player.Player;
import com.brutalfighters.game.resources.Assets;
import com.brutalfighters.game.resources.Prefs;

public class Render {
	
	private static SpriteBatch batch;
	private static ShapeRenderer shapeRenderer;
	private static OrthogonalTiledMapRenderer otmr;
	
	private static Vec2 res;
	
	private static FPSLogger fps;
	
	private static OrthographicCamera fixedCam;
	
	public static void load() {
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
		otmr = new OrthogonalTiledMapRenderer(Assets.map.getMap(), Assets.map.getScaled(), batch);
	}
	
	public static SpriteBatch getSpriteBatch() {
		return batch;
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
		drawRectAfterBegin(color, x, y, width, height);
        
	}
	public static void drawRectFilled(Color color, float x, float y, int width, int height) {
		shapeRenderer.begin(ShapeType.Filled);
		drawRectAfterBegin(color, x, y, width, height);
		
	}
	private static void drawRectAfterBegin(Color color, float x, float y, int width, int height) {
		shapeRenderer.setColor(color);
		shapeRenderer.rect(x, y, width, height);
		shapeRenderer.end();
	}

	private static void renderTutorial() {
		if (Gdx.input.isKeyPressed(InputControls.TUTORIAL)) {
			HUD.drawTutorial();
		}
	}
	
	private static void clear() {
		Gdx.gl.glClearColor(1F, 1F, 1F, 1F);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	private static void renderMap() {
		batch.end();
		otmr.render();
		batch.begin();
	}
	private static void renderTeleports() {
		for(int i = 0; i < Assets.map.getTeleports().size(); i++) {
			Assets.map.getTeleports().get(i).drawTeleport();
		}
	}
	private static void renderBackground() {
		batch.disableBlending();
		batch.setShader(Shaders.getMotionBlur());
			Assets.bg.render();
		batch.setShader(Shaders.getDefaultShader());
		batch.enableBlending();
	}
	private static void renderFlags() {
		Assets.flags.renderFlags();
	}
	
	private static void fpsLog() {
		fps.log();
	}
	
	private static void renderPlayer(Player p) {
		p.draw();
	}
	private static void renderClientPlayer() {
		Assets.player.draw();
	}
	
	private static void renderPlayers() {
		for(int i = 0; i < Assets.players.getLength(); i++) {
			Assets.players.getPlayer(i).draw();
		}
		renderClientPlayer();
	}
	
	private static void renderParticles(boolean before) {
		ParticleEffects.render(before);
	}
	
	private static void renderProjectiles() {
		Assets.projectiles.render();
	}
	
	private static void renderTextEffects() {
		TextEffects.renderTextEffects();
	}
	
	private static void renderHUD() {
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
	
	private static void draw() {
		
		// CLEAN
		clear();
		
		// SCENE
		// ---------
		
		drawScene();
		
		// HUD
		// ---------
		
		drawHUD();
		
	}
	
	private static void drawScene() {
		
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

	private static void drawHUD() {
		
		// BATCH BEGINS HERE
		batch.begin();
		
		// CAMERA : Setting the fixed camera
		setFixedCamera();
		
		// Render Escape Menu
		renderHUD();
		
		// Logging the FPS
		//fpsLog();

		// FLUSHES THE BATCH
		batch.flush();
		batch.end(); // ENDS THE BATCH
	}
	
	private static void renderFBO() {
		//Bind FBO target A
		Shaders.getFBO_A().begin();
		
		//Clear FBO A with an opaque color to minimize blending issues
		clear();
		
		drawScene();
		
		Shaders.getFBO_A().end();
	}
	
	private static void ppBlurFBO() {
		
		
		// We need it to perform at fixed position, fixed camera
		setFixedCamera();
		
		//swap the shaders
		//this will send the batch's (FBO-sized) projection matrix to our blur shader
		batch.setShader(Shaders.getPPBlur());

		//ensure the direction is along the X-axis only
		Shaders.getPPBlur().setUniformf("dir", 1f, 0f); //$NON-NLS-1$
		
		//start rendering to target B
		Shaders.getFBO_A().begin();
		
		batch.begin();

		//no need to clear since targetA has an opaque background
		//render target A (the scene) using our horizontal blur shader
		//it will be placed into target A
		TextureRegion temp = new TextureRegion(Shaders.getFBO_A().getColorBufferTexture());
		temp.flip(false, true);
		
		batch.draw(temp, 0, 0);

		//flush the batch before ending target A
		batch.flush();

		//finish rendering target A
		Shaders.getFBO_A().end();
		
		//apply the blur only along Y-axis
		Shaders.getPPBlur().setUniformf("dir", 0f, 1f); //$NON-NLS-1$
		
		//draw the horizontally-blurred FBO B to the screen, applying the vertical blur as we go
		temp = new TextureRegion(Shaders.getFBO_A().getColorBufferTexture());
		temp.flip(false, true);
		batch.draw(temp, 0, 0);
		
		batch.setShader(Shaders.getDefaultShader());
		
		batch.end();
	}
	
	public static void drawMiddle(Sprite sprite) {
		batch.draw(sprite, getResX()/2-sprite.getWidth()/2, getResY()/2-sprite.getHeight()/2, sprite.getWidth(), sprite.getHeight());
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
	private static void setPlayerCamera() {
		setCamera(Assets.client);
	}
	private static void setFixedCamera() {
		setCamera(fixedCam);
	}
}
