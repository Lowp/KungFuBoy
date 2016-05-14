package com.lowp.kungfuboy.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lowp.kungfuboy.Main;
import com.lowp.kungfuboy.level.Level01;
import com.lowp.kungfuboy.level.Level02;
import com.lowp.kungfuboy.level.Level03;
import com.lowp.kungfuboy.level.Level04;
import com.lowp.kungfuboy.level.Level05;
import com.lowp.kungfuboy.util.Assets;
import com.lowp.kungfuboy.util.AudioManager;

public class SelectScreen extends AbstractGameScreen {
	public Main game;
	public Stage stage;
	public ScrollPane pane;
	public Table table;
	public Image background;
	public Image star01;
	public Image star02;
	public Image star03;
	public Image title;
	public Image tili;
	public Image jinbi;
	public ImageButton btn_back;
	public ImageButton btn_left;
	public ImageButton btn_right;
	public ImageButton btn_tiaozhan;

	Image[] images;

	public SelectScreen(Main game) {
		super(game);
		this.game = game;
	}

	@Override
	public void pause() {
	}

	@Override
	public void show() {
		AudioManager.instance.play(Assets.instance.assetMusic.selectScreen,true);
		stage = new Stage(new ExtendViewport(800, 480));

		table = new Table();
		Image leve01 = new Image(Assets.instance.gameGUI.level01);
		Image leve02 = new Image(Assets.instance.gameGUI.level02);
		Image leve03 = new Image(Assets.instance.gameGUI.level03);
		Image leve04 = new Image(Assets.instance.gameGUI.level04);
		Image leve05 = new Image(Assets.instance.gameGUI.level05);
		images = new Image[5];
		images[0] = leve01;
		images[1] = leve02;
		images[2] = leve03;
		images[3] = leve04;
		images[4] = leve05;

		background = new Image(Assets.instance.gameGUI.select_bg);
		background.setSize(stage.getWidth(), stage.getHeight());

		title = new Image(Assets.instance.gameGUI.select_title);
		title.setPosition(343, stage.getHeight() - 65);
		btn_left = new ImageButton(Assets.instance.gameGUI.skin,
				"btn_select_left");
		btn_left.setPosition(20, 200);
		btn_left.setSize(80, 100);
		btn_left.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				AudioManager.instance.play(Assets.instance.assetSound.btn_select);
				pane.setScrollX(pane.getScrollX() - 240);
			}
		});

		btn_right = new ImageButton(Assets.instance.gameGUI.skin,
				"btn_select_right");
		btn_right.setPosition(740, 200);
		btn_right.setSize(80, 100);
		btn_right.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				AudioManager.instance.play(Assets.instance.assetSound.btn_select);
				pane.setScrollX(pane.getScrollX() + 240);
				Gdx.app.log(TAG, "haha");
			}
		});

		btn_back = new ImageButton(Assets.instance.gameGUI.skin,
				"btn_select_back");
		btn_back.setPosition(80, 400);
		btn_back.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				AudioManager.instance.play(Assets.instance.assetSound.button);
				game.setScreen(new MenuScreen(game));
			}
		});

		tili = new Image(Assets.instance.gameGUI.icon_tili);
		tili.setScale(0.7f);
		tili.setPosition(420, 20);

		jinbi = new Image(Assets.instance.gameGUI.icon_jinbi);
		jinbi.setScale(0.7f);
		jinbi.setPosition(280, 20);

		star01 = new Image(Assets.instance.gameGUI.icon_starLittle);
		star01.setPosition(50, 27);
		star02 = new Image(Assets.instance.gameGUI.icon_starLittle);
		star02.setPosition(120, 27);
		star03 = new Image(Assets.instance.gameGUI.icon_starLittle);
		star03.setPosition(190, 27);

		btn_tiaozhan = new ImageButton(Assets.instance.gameGUI.skin,
				"btn_select_tiaozhan");
		btn_tiaozhan.setSize(200, 80);
		btn_tiaozhan.setPosition(stage.getWidth() - 285, 10);
		btn_tiaozhan.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				AudioManager.instance.play(Assets.instance.assetSound.button);
				for (Image image : images) {
					if (image.getScaleX() == 1) {
						if (image.getName().equals("1")) {
							game.setScreen(new GameScreen(game, new Level01()));
						} else if (image.getName().equals("2")) {
							game.setScreen(new GameScreen(game, new Level02()));
						} else if (image.getName().equals("3")) {
							game.setScreen(new GameScreen(game, new Level03()));
						} else if (image.getName().equals("4")) {
							game.setScreen(new GameScreen(game, new Level04()));
						} else if (image.getName().equals("5")) {
							game.setScreen(new GameScreen(game, new Level05()));
						}

					}
				}
			}
		});

		table.add(new Table()).size(190, 280).pad(33);

		for (int i = 0; i < images.length; i++) {
			images[i].setOrigin(150, 200);
			images[i].setName(i + 1 + "");
			table.add(images[i]).size(300, 400).pad(-30);
		}

		table.add(new Table()).size(190, 280).pad(35);
		// table.setDebug(true);
		pane = new ScrollPane(table);
		pane.setSize(stage.getWidth() - 100, 350);
		pane.setScrollingDisabled(false, true);
		pane.setPosition(45, 80);
		pane.setTouchable(Touchable.disabled);

		stage.addActor(background);
		stage.addActor(title);
		stage.addActor(pane);
		stage.addActor(btn_back);
		stage.addActor(tili);
		stage.addActor(jinbi);
		stage.addActor(star01);
		stage.addActor(star02);
		stage.addActor(star03);
		stage.addActor(btn_left);
		stage.addActor(btn_right);
		stage.addActor(btn_tiaozhan);

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		for (int i = 1; i < 6; i++) {
			if (table.getChildren().get(i).getX() - pane.getScrollX() < (230)) {
				table.getChildren()
						.get(i)
						.setScale(
								(table.getChildren().get(i).getX() - pane
										.getScrollX()) / (220));
			} else if (table.getChildren().get(i).getX() - pane.getScrollX() >= (240)) {
				table.getChildren()
						.get(i)
						.setScale(
								(240) / (table.getChildren().get(i).getX() - pane
										.getScrollX()));
			} else if (table.getChildren().get(i).getX() - pane.getScrollX() >= (220)
					&& table.getChildren().get(i).getX() - pane.getScrollX() < (240)) {
				table.getChildren().get(i).setScale(1f);
			} else {
				table.getChildren().get(i).setScale(0.7f);
			}
			if (table.getChildren().get(i).getScaleX() < 0.7f) {
				table.getChildren().get(i).setScale(0.7f);
			} else if (table.getChildren().get(i).getScaleX() > 1) {
				table.getChildren().get(i).setScale(1f);
			}
		}

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
