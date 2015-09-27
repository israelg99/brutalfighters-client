package com.brutalfighters.game.sound;

import com.badlogic.gdx.audio.Music;

public enum BGM {

	Blank(getBGM("blank.mp3")), //$NON-NLS-1$
	HotHeat(getBGM("hotheat.mp3")), //$NON-NLS-1$
	LastDawn(getBGM("lastdawn.mp3")), //$NON-NLS-1$
	MagicMarker(getBGM("magicmarker.mp3")), //$NON-NLS-1$
	Payday(getBGM("payday.mp3")), //$NON-NLS-1$
	WaterLily(getBGM("waterlily.mp3")), //$NON-NLS-1$
	Chill(getBGM("chillbgm.mp3")), //$NON-NLS-1$
	CopyCat(getBGM("copycat.mp3")), //$NON-NLS-1$
	HighEnergy(getBGM("highenergy.mp3")), //$NON-NLS-1$
	MelanBeats(getBGM("melanbeats.mp3")), //$NON-NLS-1$
	SeasonsForever(getBGM("seasonsforever.mp3")), //$NON-NLS-1$
	Fun(getBGM("funbgm.mp3")), //$NON-NLS-1$
	FunOne(getBGM("bgmfun1.mp3")), //$NON-NLS-1$
	FunTwo(getBGM("funbgm2.mp3")); //$NON-NLS-1$
	
	private static final float DEFAULT_VOLUME = 0.15f;
	
	private Music BGM;
	private float volume;
	
	private static float getDefaultVolume() {
		return DEFAULT_VOLUME;
	}
	
	private BGM(Music BGM, float volume) {
		this.BGM = BGM;
		this.volume = volume;
	}
	private BGM(Music BGM) {
		this(BGM, getDefaultVolume());
	}
	
	public Music getBGM() {
		return BGM;
	}
	public void play() {
		getBGM().play();
	}
	
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		getBGM().setVolume(SoundUtil.getVolume(volume));
	}
	public void setVolume() {
		getBGM().setVolume(SoundUtil.getVolume(getVolume()));
	}

	
	private final static String path = "bgm"; //$NON-NLS-1$
	
	private static Music getBGM(String name) {
		return SoundUtil.getMusic("bgm/" + name); //$NON-NLS-1$
	}
	
	public static void init() {
		values();
	}
	
	public static void disposeAll() {
		for (BGM bgm : values()) {
			bgm.getBGM().stop();
		    bgm.getBGM().dispose();
		}
	}

}
