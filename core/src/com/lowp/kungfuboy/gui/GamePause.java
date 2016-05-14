package com.lowp.kungfuboy.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lowp.kungfuboy.util.Assets;
import com.lowp.kungfuboy.util.AudioManager;

/**
 * ”Œœ∑‘›Õ£ΩÁ√Ê
 * 
 * @author lowp
 *
 */
public class GamePause extends Actor {
	public Skin skin;
	public Image bg;
	public ImageButton btn_restart;
	public ImageButton btn_next;
	public ImageButton btn_next_disable;
	public ImageButton btn_play;
	public ImageButton btn_home;
	public ImageButton btn_Sound_on;
	public ImageButton btn_Sound_off;

	public GamePause() {
		skin = Assets.instance.gameGUI.skin;
		bg = new Image(Assets.instance.gameGUI.pause_bg);
		btn_restart = new ImageButton(skin, "btn_restart");
		btn_next = new ImageButton(skin, "btn_next");
		btn_next_disable = new ImageButton(skin, "btn_next_disable");
		btn_play = new ImageButton(skin, "btn_play");
		btn_home = new ImageButton(skin, "btn_home");
		btn_Sound_on = new ImageButton(skin, "btn_Sound_on");
		btn_Sound_off = new ImageButton(skin, "btn_Sound_off");

		bg.setSize(860, 480);
		bg.setColor(0, 0,0, 0.8f);

		btn_next_disable.setSize(70, 70);
		btn_next_disable.setPosition(230, 220);

		btn_restart.setSize(70, 70);
		btn_restart.setPosition(100, 220);

		btn_play.setSize(150, 150);
		btn_play.setPosition(360, 180);

		btn_home.setSize(70, 70);
		btn_home.setPosition(570, 220);

		btn_Sound_on.setSize(70, 70);
		btn_Sound_on.setPosition(690, 220);
		btn_Sound_on.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				btn_Sound_on.setVisible(false);
				btn_Sound_off.setVisible(true);
				

			}

		});

		btn_Sound_off.setSize(70, 70);
		btn_Sound_off.setPosition(690, 220);
		btn_Sound_off.setVisible(false);
		btn_Sound_off.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				btn_Sound_off.setVisible(false);
				btn_Sound_on.setVisible(true);

			}

		});

	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

}
