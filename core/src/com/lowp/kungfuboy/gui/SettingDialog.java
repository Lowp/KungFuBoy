package com.lowp.kungfuboy.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.lowp.kungfuboy.util.Assets;

public class SettingDialog {
	public Skin skin;
	public Image darkBg;
	public Image bg;
	public Slider slider_music;
	public Slider slider_sound;
	public ImageButton check_music;
	public ImageButton check_sound;
	public ImageButton btn_savaSeting;

	public SettingDialog() {
		skin = Assets.instance.gameGUI.skin;
		darkBg = new Image(Assets.instance.gameGUI.game_gameOverBg);
		darkBg.setSize(850, 480);
		darkBg.setColor(0, 0, 0, 0.5f);
		bg = new Image(Assets.instance.gameGUI.dialog_bg);
		bg.setSize(600, 450);
		bg.setPosition(425 - 300, 240 - 225);
		slider_music = new Slider(0.0f, 1.0f, 0.0000001f, false, skin,
				"slider_music");
		slider_music.setSize(300, 3);
		slider_music.setPosition(bg.getX() + 225, bg.getY()+295);
		
		slider_sound = new Slider(0.0f, 1.0f, 0.0000001f, false, skin,
				"slider_music");
		slider_sound.setSize(300, 3);
		slider_sound.setPosition(bg.getX() + 225, bg.getY()+220);
		
		
	}
}
