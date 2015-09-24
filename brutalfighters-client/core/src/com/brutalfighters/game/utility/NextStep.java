package com.brutalfighters.game.utility;

import com.badlogic.gdx.Gdx;

public class NextStep {
	
	private float toNextStep;
	
	public NextStep(float nextStep) {
		set(nextStep);
	}
	public NextStep() {
		this(0);
	}
	
	public final float get() {
		return toNextStep;
	}
	public final boolean isTime() {
		return toNextStep < 0;
	}
	public final void set(float toNextStep) {
		this.toNextStep = toNextStep;
	}
	public final void reset() {
		set(0);
	}
	public final void sub() {
		this.toNextStep -= Gdx.graphics.getDeltaTime();
	}
}
