package com.lowp.kungfuboy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lowp.kungfuboy.gui.GameOverFlash;
import com.lowp.kungfuboy.gui.GameOverUi;
import com.lowp.kungfuboy.gui.GamePause;
import com.lowp.kungfuboy.gui.LianJi;
import com.lowp.kungfuboy.level.Level01;
import com.lowp.kungfuboy.level.Level02;
import com.lowp.kungfuboy.level.Level03;
import com.lowp.kungfuboy.level.Level04;
import com.lowp.kungfuboy.level.Level05;
import com.lowp.kungfuboy.object.Hero.HERO_STATE;
import com.lowp.kungfuboy.screen.GameScreen;
import com.lowp.kungfuboy.screen.MenuScreen;
import com.lowp.kungfuboy.screen.SelectScreen;
import com.lowp.kungfuboy.util.Assets;
import com.lowp.kungfuboy.util.AudioManager;
import com.lowp.kungfuboy.util.Constants;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * 负责游戏世界的渲染
 * 
 * @author lowp
 *
 */
public class WorldRenderer implements Disposable {
	private static final String TAG = WorldRenderer.class.getName();
	private WorldController worldController;
	public Main game;
	public SpriteBatch batch;
	public OrthographicCamera camera;

	float w, h;

	/**
	 * GUI
	 */
	public Stage stageGUI;
	public Skin guiSkin;
	protected Touchpad touchpad; // 轨迹球
	protected ImageButton btn_fist; // 拳攻击按钮
	protected ImageButton btn_foot; // 腿攻击按钮
	protected ImageButton btn_jump; // 跳跃按钮
	protected ImageButton btn_skills_fist; // 拳_必杀技按钮
	protected ImageButton btn_skills_dao; // 刀_必杀技按钮
	protected ImageButton btn_skills_gun; // 棍_必杀技按钮
	protected ImageButton btn_pause;
	protected Image hero_bar;
	protected Image hero_bar_hp;
	protected Image hero_bar_mp;
	protected LianJi lianJi; // 连击效果
	protected GamePause gamePause; // 游戏暂停界面
	protected GameOverFlash overFlash;// 游戏结束闪烁
	protected GameOverUi gameOverUi;// 游戏结束

	public WorldRenderer(WorldController worldController, Main main) {
		this.worldController = worldController;
		this.game = main;
		worldController.worldRenderer = this;
		init();
	}

	public void init() {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
				Constants.VIEWPORT_HEIGHT * (h / w));
		camera.position.set(camera.viewportWidth / 2f,
				camera.viewportHeight / 2f, 0);
		camera.update();
		buildStage();
	}

	public void render(SpriteBatch batch) {
		renderWorld(batch);
		if (!worldController.level.isStartPlot) {
			renderGUI(batch);
		}

	}

	// 绘制游戏世界
	private void renderWorld(SpriteBatch batch) {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.flush();
		worldController.level.render(batch);
		// 掉落分数
		batch.end();
	}

	// 绘制GUI
	private void renderGUI(SpriteBatch batch) {
		stageGUI.act();
		stageGUI.draw();
	}

	private void buildStage() {
		stageGUI = new Stage(new ExtendViewport(Constants.GUI_WIDTH,
				Constants.GUI_HEIGHT));
		guiSkin = Assets.instance.gameGUI.skin;

		// 轨迹球
		touchpad = new Touchpad(25, guiSkin, "trackball");
		touchpad.setPosition(20, 30);
		stageGUI.addActor(touchpad);

		// 暂停按钮
		btn_pause = new ImageButton(guiSkin, "btn_pause");
		btn_pause.setPosition(750, 390);
		btn_pause.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				AudioManager.instance.play(Assets.instance.assetSound.btn_select);
				worldController.level.isPause = true;
				stageGUI.addActor(gamePause);
				stageGUI.addActor(gamePause.bg);
				stageGUI.addActor(gamePause.btn_restart);
				stageGUI.addActor(gamePause.btn_next_disable);
				stageGUI.addActor(gamePause.btn_play);
				stageGUI.addActor(gamePause.btn_home);
				stageGUI.addActor(gamePause.btn_Sound_on);
				stageGUI.addActor(gamePause.btn_Sound_off);
			}
		});
		stageGUI.addActor(btn_pause);

		// 拳攻击按钮
		btn_fist = new ImageButton(guiSkin, "btn_fist");
		btn_fist.setSize(btn_fist.getWidth() * 0.7f,
				btn_fist.getHeight() * 0.7f);
		btn_fist.setPosition(stageGUI.getWidth() - btn_fist.getWidth() - 20, 30);
		stageGUI.addActor(btn_fist);
		btn_fist.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				if (worldController.level.hero.state != HERO_STATE.ATTACK_SKILL
						&& worldController.level.hero.state != HERO_STATE.JUMP_UP
						&& worldController.level.hero.state != HERO_STATE.JUMP_DOWN
						&& worldController.level.hero.state != HERO_STATE.HURT) {
					worldController.level.hero.isFist = true;
					worldController.level.hero.isAttack = true;
					worldController.level.hero.stateTime = 0;
					worldController.level.hero.state = HERO_STATE.ATTACK_Normal;

				}

				return true;
			}
		});

		// 腿攻击按钮
		btn_foot = new ImageButton(guiSkin, "btn_foot");
		btn_foot.setSize(btn_foot.getWidth() * 0.55f,
				btn_foot.getHeight() * 0.55f);
		btn_foot.setPosition(stageGUI.getWidth() - 100, 140);
		stageGUI.addActor(btn_foot);
		btn_foot.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (worldController.level.hero.state != HERO_STATE.ATTACK_SKILL
						&& worldController.level.hero.state != HERO_STATE.JUMP_UP
						&& worldController.level.hero.state != HERO_STATE.JUMP_DOWN
						&& worldController.level.hero.state != HERO_STATE.HURT) {

					worldController.level.hero.isFoot = true;
					worldController.level.hero.isAttack = true;
					worldController.level.hero.stateTime = 0;
					worldController.level.hero.state = HERO_STATE.ATTACK_Normal;

				}
				return true;
			}
		});

		// 跳跃按钮
		btn_jump = new ImageButton(guiSkin, "btn_jump");
		btn_jump.setSize(btn_jump.getWidth() * 0.6f,
				btn_jump.getHeight() * 0.6f);
		btn_jump.setPosition(stageGUI.getWidth() - 205, 30);
		stageGUI.addActor(btn_jump);
		btn_jump.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				if (worldController.level.hero.state != HERO_STATE.ATTACK_SKILL
						&& worldController.level.hero.state != HERO_STATE.ATTACK_Normal
						&& worldController.level.hero.state != HERO_STATE.HURT
						&& worldController.level.hero.state != HERO_STATE.JUMP_UP
						&& worldController.level.hero.state != HERO_STATE.JUMP_DOWN) {

					worldController.level.hero.isJump_up = true;
					worldController.level.hero.isAttack = true;
					worldController.level.hero.stateTime = 0;
					worldController.level.hero.state = HERO_STATE.JUMP_UP;

				}

				return true;
			}
		});

		// 拳_必杀技按钮
		btn_skills_fist = new ImageButton(guiSkin, "btn_skills_fist");
		btn_skills_fist.setSize(btn_skills_fist.getWidth() * 0.6f,
				btn_skills_fist.getHeight() * 0.6f);
		btn_skills_fist.setPosition(stageGUI.getWidth() - 190, 120);
		stageGUI.addActor(btn_skills_fist);
		btn_skills_fist.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (worldController.level.hero.state != HERO_STATE.ATTACK_SKILL
						&& worldController.level.hero.state != HERO_STATE.JUMP_UP
						&& worldController.level.hero.state != HERO_STATE.JUMP_DOWN
						&& worldController.level.hero.state != HERO_STATE.HURT) {
					AudioManager.instance
							.play(Assets.instance.assetSound.hero_jineng1);
					worldController.level.hero.isSkills_Fist = true;
					worldController.level.hero.isAttack = true;
					worldController.level.hero.stateTime = 0;
					worldController.level.hero.state = HERO_STATE.ATTACK_SKILL;

				}

				return true;
			}
		});

		// 刀_必杀技按钮
		btn_skills_dao = new ImageButton(guiSkin, "btn_skills_dao");
		btn_skills_dao.setSize(btn_skills_dao.getWidth() * 0.5f,
				btn_skills_dao.getHeight() * 0.5f);
		btn_skills_dao.setPosition(stageGUI.getWidth() - 255, 100);
		stageGUI.addActor(btn_skills_dao);
		btn_skills_dao.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (worldController.level.hero.state != HERO_STATE.ATTACK_SKILL
						&& worldController.level.hero.state != HERO_STATE.JUMP_UP
						&& worldController.level.hero.state != HERO_STATE.JUMP_DOWN
						&& worldController.level.hero.state != HERO_STATE.HURT) {
					AudioManager.instance
							.play(Assets.instance.assetSound.hero_jineng2);
					worldController.level.hero.isSkills_Dao = true;
					worldController.level.hero.isAttack = true;
					worldController.level.hero.stateTime = 0;
					worldController.level.hero.state = HERO_STATE.ATTACK_SKILL;
				}

				return true;
			}
		});

		// 棍_必杀技按钮
		btn_skills_gun = new ImageButton(guiSkin, "btn_skills_gun");
		btn_skills_gun.setSize(btn_skills_gun.getWidth() * 0.6f,
				btn_skills_gun.getHeight() * 0.6f);
		btn_skills_gun.setPosition(stageGUI.getWidth() - 300, 30);
		stageGUI.addActor(btn_skills_gun);
		btn_skills_gun.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (worldController.level.hero.state != HERO_STATE.ATTACK_SKILL
						&& worldController.level.hero.state != HERO_STATE.JUMP_UP
						&& worldController.level.hero.state != HERO_STATE.JUMP_DOWN
						&& worldController.level.hero.state != HERO_STATE.HURT) {
					AudioManager.instance
							.play(Assets.instance.assetSound.hero_jineng1);
					worldController.level.hero.isSkills_Gun = true;
					worldController.level.hero.isAttack = true;
					worldController.level.hero.stateTime = 0;
					worldController.level.hero.state = HERO_STATE.ATTACK_SKILL;

				}

				return true;
			}
		});

		// hero_bar
		hero_bar = new Image(new Texture("gui/bar/hero-bar-bg.png"));
		hero_bar.setPosition(20, stageGUI.getHeight() - 90);
		hero_bar.setSize(380, 70);

		hero_bar_hp = new Image(new Texture("gui/bar/hero-hp-bar.png"));
		hero_bar_hp.setPosition(60, stageGUI.getHeight() - 65);
		hero_bar_hp.setSize(287, 8);// 287

		hero_bar_mp = new Image(new Texture("gui/bar/hero-mp-bar.png"));
		hero_bar_mp.setPosition(60, stageGUI.getHeight() - 73);
		hero_bar_mp.setSize(258, 4);// 258

		stageGUI.addActor(hero_bar);
		stageGUI.addActor(hero_bar_hp);
		stageGUI.addActor(hero_bar_mp);

		// 连击效果
		lianJi = new LianJi();
		lianJi.setSize(200, 100);
		lianJi.setPosition(stageGUI.getWidth() - 210, 300);
		lianJi.setVisible(false);
		stageGUI.addActor(lianJi);
		// 暂停界面
		gamePause = new GamePause();
		gamePause.btn_restart.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				AudioManager.instance.play(Assets.instance.assetSound.btn_select);
				switch (worldController.level.index) {
				case 1:
					game.setScreen(new GameScreen(game, new Level01()));
					break;
				case 2:
					game.setScreen(new GameScreen(game, new Level02()));
					break;
				case 3:
					game.setScreen(new GameScreen(game, new Level03()));
					break;
				case 4:
					game.setScreen(new GameScreen(game, new Level04()));
					break;
				case 5:
					game.setScreen(new GameScreen(game, new Level05()));
					break;

				default:
					break;
				}
				pauseOver();
			}
		});
		gamePause.btn_play.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				AudioManager.instance.play(Assets.instance.assetSound.btn_select);
				worldController.level.isPause = false;
				pauseOver();
			}
		});
		gamePause.btn_home.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				AudioManager.instance.play(Assets.instance.assetSound.btn_select);
				game.setScreen(new MenuScreen(game));
			}
		});

		// 游戏结束时闪屏
		overFlash = new GameOverFlash();
		overFlash.setSize(stageGUI.getWidth(), stageGUI.getHeight());
		overFlash.setPosition(0, 0);
		overFlash.setVisible(false);
		overFlash.bg.setVisible(false);
		overFlash.bg.setSize(stageGUI.getWidth(), stageGUI.getHeight());
		overFlash.bg.setPosition(0, 0);
		stageGUI.addActor(overFlash.bg);
		stageGUI.addActor(overFlash);

		// 游戏结束界面
		gameOverUi = new GameOverUi();
		gameOverUi.bg.setSize(stageGUI.getWidth(), stageGUI.getHeight());
		gameOverUi.before.setSize(stageGUI.getWidth(), stageGUI.getHeight());
		gameOverUi.before.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				game.setScreen(new SelectScreen(game));
			}
		});
		// stageGUI.addActor(gameOverUi);
		// stageGUI.addActor(gameOverUi.bg);
		// stageGUI.addActor(gameOverUi.rotationLight);
		// stageGUI.addActor(gameOverUi.victory);
		// stageGUI.addActor(gameOverUi.star_01);
		// stageGUI.addActor(gameOverUi.star_02);
		// stageGUI.addActor(gameOverUi.star_03);
		// stageGUI.addActor(gameOverUi.tipsNext);
		// stageGUI.addActor(gameOverUi.before);

		Gdx.input.setInputProcessor(stageGUI);
	}

	public void pauseOver() {
		gamePause.remove();
		gamePause.bg.remove();
		gamePause.btn_restart.remove();
		gamePause.btn_next_disable.remove();
		gamePause.btn_play.remove();
		gamePause.btn_home.remove();
		gamePause.btn_Sound_on.remove();
		gamePause.btn_Sound_off.remove();
	}

	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();

		// 设置舞台视口的大小
		stageGUI.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		batch.dispose();
		stageGUI.dispose();
	}
}
