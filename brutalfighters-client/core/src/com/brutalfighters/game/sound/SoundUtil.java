package com.brutalfighters.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.resources.Assets;
import com.brutalfighters.game.resources.Prefs;

public class SoundUtil {
	
	private static int SOUND_RANGE = 3000;
	private static float VOLUME = 0.5f;
	
	public static float getVolume(float vol) {
		return vol * Prefs.getVolume();
	}
	public static float getVolume() {
		return getVolume(VOLUME);
	}
	public static int getSoundRange() {
		return SOUND_RANGE;
	}
	
	public static void setVolume(float vol) {
		VOLUME = vol;
	}
	
	public static Music getMusic(String path, float volume) {
		Music temp = Gdx.audio.newMusic(Gdx.files.internal(path));
		temp.setVolume(getVolume(volume));
		
		return temp;
	}
	public static Music getMusic(String path) {
		Music temp = Gdx.audio.newMusic(Gdx.files.internal(path));
		temp.setVolume(getVolume(VOLUME));
		
		return temp;
	}
	public static Sound getSound(String path) {
		return Gdx.audio.newSound(Gdx.files.internal("sfx/" + path)); //$NON-NLS-1$
	}
	
	public static void playStereo(Music play, float x) {
		if(toPlay(play)) {
			PlayerData client = Assets.player.getPlayer();
			
			if(checkSoundRange(x, client.getPos().getX())) {
				play.setPan(getPan(x, client.getPos().getX()), play.getVolume());
				play.play();
			}
		}
	}
	public static void playStereo(Sound play, float x, float vol) {
		if(toPlay(play)) {
			PlayerData client = Assets.player.getPlayer();
			
			if(checkSoundRange(x, client.getPos().getX())) {
				play.setPan(play.play(), getPan(x, client.getPos().getX()), getVolume(vol));
			}
		}
	}
	public static void playStereo(Sound play, float x) {
		playStereo(play,x,getVolume());
	}
	
	public static void playSound(Sound play, float vol) {
		if(toPlay(play)) {
			play.setVolume(play.play(), SoundUtil.getVolume(vol));
		}
	}
	public static void playSound(Sound play) {
		if(toPlay(play)) {
			playSound(play, VOLUME);
		}
	}
	
	private static float getPan(float sound_x, float client_x) {
		// FORMULA : (play.x - client.x) / SOUND-RANGE
		return (sound_x - client_x) / getSoundRange();
	}
	private static boolean checkSoundRange(float sound_x, float client_x) {
		return client_x > sound_x - getSoundRange() && client_x < sound_x + getSoundRange();
	}
	private static boolean toPlay(Sound play) {
		return Prefs.isVolume() && play != null;
	}
	private static boolean toPlay(Music play) {
		return Prefs.isVolume() && play != null;
	}
}
