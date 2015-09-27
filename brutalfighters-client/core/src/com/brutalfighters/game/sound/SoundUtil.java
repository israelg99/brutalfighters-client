package com.brutalfighters.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.brutalfighters.game.player.PlayerData;
import com.brutalfighters.game.resources.Assets;
import com.brutalfighters.game.resources.Prefs;

public class SoundUtil {
	
	private static int SOUND_RANGE = 3000;
	
	public static int getSoundRange() {
		return SOUND_RANGE;
	}
	public static float getVolume(float vol) {
		return vol * Prefs.getVolume();
	}
	
	private static Music getMusicRaw(String path, float volume) {
		Music temp = Gdx.audio.newMusic(Gdx.files.internal(path));
		temp.setVolume(volume);
		
		return temp;
	}
	public static Music getMusic(String path, float volume) {
		return getMusicRaw(path, getVolume(volume));
	}
	public static Music getMusic(String path) {
		return getMusicRaw(path, Prefs.getVolume());
	}
	
	public static Sound getSound(String path) {
		return Gdx.audio.newSound(Gdx.files.internal(path));
	}
	
	public static void playSound(Music play) {
		if(toPlay(play)) {
			play.play();
		}
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
	
	private static void playStereoRaw(Sound play, float x, float vol) {
		if(toPlay(play)) {
			PlayerData client = Assets.player.getPlayer();
			
			if(checkSoundRange(x, client.getPos().getX())) {
				play.setPan(play.play(), getPan(x, client.getPos().getX()), vol);
			}
		}
	}
	public static void playStereo(Sound play, float x, float vol) {
		playStereoRaw(play, x, getVolume(vol));
	}
	public static void playStereo(Sound play, float x) {
		playStereoRaw(play, x, Prefs.getVolume());
	}
	
	private static void playSoundRaw(Sound play, float vol) {
		if(toPlay(play)) {
			play.setVolume(play.play(), vol);
		}
	}
	public static void playSound(Sound play, float vol) {
		playSoundRaw(play, getVolume(vol));
	}
	public static void playSound(Sound play) {
		playSoundRaw(play, Prefs.getVolume());
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
