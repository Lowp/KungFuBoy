package com.lowp.kungfuboy;

import com.badlogic.gdx.assets.AssetManager;
import com.lowp.kungfuboy.screen.DirectedGame;
import com.lowp.kungfuboy.screen.MenuScreen;
import com.lowp.kungfuboy.util.Assets;
import com.lowp.kungfuboy.util.GamePreferences;

public class Main extends DirectedGame {
	public static final String TAG = Main.class.getName();

	@Override
	public void create() {
		// ������Ϸ����
		GamePreferences.instance.load();
		// ������Ϸ��Դ
		Assets.instance.init(new AssetManager());
		// setScreen(new SelectScree n(this));
		setScreen(new MenuScreen(this));
		// setScreen(new GameScreen(this, new Level01()));
	}

}
