package com.brutalfighters.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.brutalfighters.game.basic.Render;

public class Shaders {
	
	// Everything is public because their variables may change on the fly.
	public static ShaderProgram motionBlur;
	public static ShaderProgram ppBlur;
	public static ShaderProgram defShader;
	public static FrameBuffer FBO_A, FBO_B;
	public static int FBO_SIZE;
	public static final float MAX_BLUR = 3f;
	public static final float blueDivide = 50000f;
	public static float blurRadius;

	public static void Load() {
		ShaderProgram.pedantic = false;

		blurRadius = 8f;
		
		FBO_SIZE = Render.getResX() > Render.getResY() ? Render.getResX() : Render.getResY();
		
		motionBlur = loadShaders("shaders/pass.vsh", "shaders/motionBlur.fsh"); //$NON-NLS-1$ //$NON-NLS-2$
		ppBlur = loadShaders("shaders/pass.vsh", "shaders/ppBlur.fsh"); //$NON-NLS-1$ //$NON-NLS-2$
		defShader = SpriteBatch.createDefaultShader();
		
		ppBlur.begin();
		ppBlur.setUniformf("dir", 0f, 0f); //direction of blur; nil for now //$NON-NLS-1$
		ppBlur.setUniformf("resolution", FBO_SIZE); //size of FBO texture //$NON-NLS-1$
		ppBlur.setUniformf("radius", blurRadius); //radius of blur //$NON-NLS-1$
		ppBlur.end();
		
		FBO_A = new FrameBuffer(Pixmap.Format.RGBA8888, Render.getResX(), Render.getResY(), false);
		FBO_B = new FrameBuffer(Pixmap.Format.RGBA8888, Render.getResX(), Render.getResY(), false);
	}

	private static ShaderProgram loadShaders(String p1, String p2) {
		return new ShaderProgram(Gdx.files.internal(p1), Gdx.files.internal(p2));
	}
	
	public static float getBlur(float speed) {
		return speed / blueDivide;
	}
	
	public static void dispose() {
		FBO_A.dispose();
		FBO_B.dispose();
	}
}
