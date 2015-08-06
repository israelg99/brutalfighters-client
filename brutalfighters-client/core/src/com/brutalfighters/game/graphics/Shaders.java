package com.brutalfighters.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.brutalfighters.game.basic.Render;

public class Shaders {
	
	// Everything is public because their variables may change on the fly.
	
	private static final float MAX_BLUR = 3f;
	private static final float blueDivide = 50000f;
	
	private static int FBO_SIZE;
	
	private static float blurRadius;
	
	private static ShaderProgram motionBlur;
	private static ShaderProgram ppBlur;
	private static ShaderProgram defShader;
	
	private static FrameBuffer FBO_A, FBO_B;

	public static void Load() {
		ShaderProgram.pedantic = false;

		setBlurRadius(8.0f);
		
		setFBO_SIZE(Render.getResX() > Render.getResY() ? Render.getResX() : Render.getResY());
		
		setMotionBlur(loadShaders("shaders/pass.vsh", "shaders/motionBlur.fsh")); //$NON-NLS-1$ //$NON-NLS-2$
		setPPBlur(loadShaders("shaders/pass.vsh", "shaders/ppBlur.fsh")); //$NON-NLS-1$ //$NON-NLS-2$
		setDefaultShader(SpriteBatch.createDefaultShader());
		
		getPPBlur().begin();
		getPPBlur().setUniformf("dir", 0f, 0f); //direction of blur //$NON-NLS-1$
		getPPBlur().setUniformf("resolution", FBO_SIZE); //size of FBO texture //$NON-NLS-1$
		getPPBlur().setUniformf("radius", blurRadius); //radius of blur //$NON-NLS-1$
		getPPBlur().end();
		
		setFBO_A(new FrameBuffer(Pixmap.Format.RGBA8888, Render.getResX(), Render.getResY(), false));
		setFBO_B(new FrameBuffer(Pixmap.Format.RGBA8888, Render.getResX(), Render.getResY(), false));
	}

	private static ShaderProgram loadShaders(String p1, String p2) {
		return new ShaderProgram(Gdx.files.internal(p1), Gdx.files.internal(p2));
	}
	
	public static int getFBO_SIZE() {
		return FBO_SIZE;
	}
	public static void setFBO_SIZE(int fBO_SIZE) {
		FBO_SIZE = fBO_SIZE;
	}

	public static float getBlurRadius() {
		return blurRadius;
	}
	public static void setBlurRadius(float blurRadius) {
		Shaders.blurRadius = blurRadius;
	}

	public static ShaderProgram getMotionBlur() {
		return motionBlur;
	}
	public static void setMotionBlur(ShaderProgram motionBlur) {
		Shaders.motionBlur = motionBlur;
	}

	public static ShaderProgram getPPBlur() {
		return ppBlur;
	}
	public static void setPPBlur(ShaderProgram ppBlur) {
		Shaders.ppBlur = ppBlur;
	}

	public static ShaderProgram getDefaultShader() {
		return defShader;
	}
	public static void setDefaultShader(ShaderProgram defShader) {
		Shaders.defShader = defShader;
	}

	public static FrameBuffer getFBO_A() {
		return FBO_A;
	}
	public static void setFBO_A(FrameBuffer fBO_A) {
		FBO_A = fBO_A;
	}

	public static FrameBuffer getFBO_B() {
		return FBO_B;
	}
	public static void setFBO_B(FrameBuffer fBO_B) {
		FBO_B = fBO_B;
	}

	public static float getMaxBlur() {
		return MAX_BLUR;
	}

	public static float getBluedivide() {
		return blueDivide;
	}

	public static float getBlur(float speed) {
		return speed / blueDivide;
	}
	
	public static void dispose() {
		FBO_A.dispose();
		FBO_B.dispose();
	}
}
