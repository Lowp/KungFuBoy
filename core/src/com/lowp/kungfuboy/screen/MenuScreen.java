package com.lowp.kungfuboy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lowp.kungfuboy.Main;
import com.lowp.kungfuboy.util.Assets;
import com.lowp.kungfuboy.util.AudioManager;

public class MenuScreen extends AbstractGameScreen {
	public static final String TAG = MenuScreen.class.getName();
	public Main game;
	public Stage stage;
	public Skin skin;
	public Image bg;
	public Image logo;
	public ImageButton btn_tiaozhan;
	public ImageButton btn_chaungguan;
	public ImageButton btn_setting;
	public ImageButton btn_help;
	public ImageButton btn_miji;
	public ImageButton btn_tuji;

	public MenuScreen(Main game) {
		super(game);
		this.game = game;
	}

	@Override
	public void show() {
		AudioManager.instance.play(Assets.instance.assetMusic.menuScreen, true);

		stage = new Stage(new ExtendViewport(800, 480));
		skin = Assets.instance.gameGUI.skin;
		bg = new Image(Assets.instance.gameGUI.startScreen_bg);
		logo = new Image(Assets.instance.gameGUI.startScreen_logo);
		btn_tiaozhan = new ImageButton(skin, "btn_tiaozhan");
		btn_chaungguan = new ImageButton(skin, "btn_chaungguan");
		btn_setting = new ImageButton(skin, "btn_setting");
		btn_help = new ImageButton(skin, "btn_help");
		btn_miji = new ImageButton(skin, "btn_miji");
		btn_tuji = new ImageButton(skin, "btn_tuji");

		bg.setSize(860, 480);
		logo.setSize(350, 150);
		logo.setPosition(45, 300);

		btn_tiaozhan.setSize(380, 90);
		btn_tiaozhan.setPosition(0, 150);
		btn_tiaozhan.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				AudioManager.instance.play(Assets.instance.assetSound.button);
				game.setScreen(new SelectScreen(game));
			}
		});

		btn_chaungguan.setSize(380, 90);
		btn_chaungguan.setPosition(0, 40);
		btn_chaungguan.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				AudioManager.instance.play(Assets.instance.assetSound.button);
			}
		});

		btn_setting.setSize(70, 70);
		btn_setting.setPosition(470, 10);
		btn_setting.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				AudioManager.instance.play(Assets.instance.assetSound.button);
			}
		});

		btn_miji.setSize(70, 70);
		btn_miji.setPosition(570, 10);
		btn_miji.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				AudioManager.instance.play(Assets.instance.assetSound.button);
			}
		});

		btn_tuji.setSize(70, 70);
		btn_tuji.setPosition(670, 10);
		btn_tuji.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				AudioManager.instance.play(Assets.instance.assetSound.button);
			}
		});

		btn_help.setSize(70, 70);
		btn_help.setPosition(770, 10);
		btn_help.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				AudioManager.instance.play(Assets.instance.assetSound.button);
			}
		});
		

		stage.addActor(bg);
		stage.addActor(logo);
		stage.addActor(btn_tiaozhan);
		stage.addActor(btn_chaungguan);
		stage.addActor(btn_setting);
		stage.addActor(btn_miji);
		stage.addActor(btn_tuji);
		stage.addActor(btn_help);
		
		
		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float deltaTime) {
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// 设置舞台视口的大小
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void hide() {
		stage.dispose();
	}

	@Override
	public InputProcessor getInputProcessor() {
		return null;
	}

}
