package com.lowp.kungfuboy.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

/**
 * 本类用来保存游戏菜单的设置
 * 
 * @author lowp
 *
 */
public class GamePreferences {
	public static final String TAG = GamePreferences.class.getName();
	// 单例
	public static final GamePreferences instance = new GamePreferences();

	public boolean sound;
	public boolean music;
	public float volSound;
	public float volMusic;
	private Preferences prefs;

	private GamePreferences() {
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
	}

	// 载入游戏配置
	public void load() {
		sound = prefs.getBoolean("sound", true);
		music = prefs.getBoolean("music", true);
		/**
		 * MathUtils.clamp(float a,float b,float c)
		 * a的值必须在b和c之间，如果a<b则返回b,a>c则返回c,否则返回b
		 */
		volSound = MathUtils
				.clamp(prefs.getFloat("volSound", 0.8f), 0.0f, 1.0f);
		Gdx.app.log(TAG, volSound+"");
		volMusic = MathUtils
				.clamp(prefs.getFloat("volMusic", 0.8f), 0.0f, 1.0f);
	}

	// 保存游戏配置
	public void save() {
		prefs.putBoolean("sound", sound);
		prefs.putBoolean("music", music);
		prefs.putFloat("volSound", volSound);
		prefs.putFloat("volMusic", volMusic);
		// 将变量的值写入配置文件，确保数据持久化
		prefs.flush();
	}
}
