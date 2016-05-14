package com.lowp.kungfuboy.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

/**
 * ��������������Ϸ�˵�������
 * 
 * @author lowp
 *
 */
public class GamePreferences {
	public static final String TAG = GamePreferences.class.getName();
	// ����
	public static final GamePreferences instance = new GamePreferences();

	public boolean sound;
	public boolean music;
	public float volSound;
	public float volMusic;
	private Preferences prefs;

	private GamePreferences() {
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
	}

	// ������Ϸ����
	public void load() {
		sound = prefs.getBoolean("sound", true);
		music = prefs.getBoolean("music", true);
		/**
		 * MathUtils.clamp(float a,float b,float c)
		 * a��ֵ������b��c֮�䣬���a<b�򷵻�b,a>c�򷵻�c,���򷵻�b
		 */
		volSound = MathUtils
				.clamp(prefs.getFloat("volSound", 0.8f), 0.0f, 1.0f);
		Gdx.app.log(TAG, volSound+"");
		volMusic = MathUtils
				.clamp(prefs.getFloat("volMusic", 0.8f), 0.0f, 1.0f);
	}

	// ������Ϸ����
	public void save() {
		prefs.putBoolean("sound", sound);
		prefs.putBoolean("music", music);
		prefs.putFloat("volSound", volSound);
		prefs.putFloat("volMusic", volMusic);
		// ��������ֵд�������ļ���ȷ�����ݳ־û�
		prefs.flush();
	}
}
