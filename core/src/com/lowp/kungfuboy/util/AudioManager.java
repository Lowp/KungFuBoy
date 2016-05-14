package com.lowp.kungfuboy.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * ”Œœ∑…˘“Ùπ‹¿Ì
 * 
 * @author lowp
 *
 */
public class AudioManager {
	public static final AudioManager instance = new AudioManager();
	private Music playingMusic;
	public Sound sound;

	private AudioManager() {
	}

	public void play(Sound sound) {
		play(sound, 1);
	}

	public void play(Sound sound, float volume) {
		play(sound, volume, 1);
	}

	public void play(Sound sound, float volume, float pitch) {
		play(sound, volume, pitch, 0);
	}

	public void play(Sound sound, float volume, float pitch, float pan) {
		if (!GamePreferences.instance.sound)
			return;
		sound.play(GamePreferences.instance.volSound * volume, pitch, pan);
	}

	public void play(Sound sound, boolean isLooping) {
		if (!GamePreferences.instance.sound)
			return;
		if (isLooping) {
			this.sound = sound;
			sound.play(GamePreferences.instance.volSound, 1, 0);
			sound.loop();
		}

	}

	public void play(Music music, boolean isLooping) {
		stopMusic();
		playingMusic = music;
		if (GamePreferences.instance.music) {
			music.setLooping(isLooping);
			music.setVolume(GamePreferences.instance.volMusic);
			music.play();
		}
	}

	public void stopMusic() {
		if (playingMusic != null)
			playingMusic.stop();
	}

	public void onSettingsUpdated() {
		if (playingMusic == null)
			return;
		playingMusic.setVolume(GamePreferences.instance.volMusic);
		if (GamePreferences.instance.music) {
			if (!playingMusic.isPlaying())
				playingMusic.play();
		} else {
			playingMusic.pause();
		}
	}

}
