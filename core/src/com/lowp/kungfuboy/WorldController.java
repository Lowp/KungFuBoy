package com.lowp.kungfuboy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Disposable;
import com.lowp.kungfuboy.level.AbstractGameLevel;
import com.lowp.kungfuboy.object.LionMonster;
import com.lowp.kungfuboy.object.StoneMonster;
import com.lowp.kungfuboy.object.Hero.HERO_STATE;
import com.lowp.kungfuboy.object.Hero.HERO_VIEW_DIRECTION;
import com.lowp.kungfuboy.object.LionMonster.LION_STATE;
import com.lowp.kungfuboy.object.LionMonster.LION_VIEW_DIRECTION;
import com.lowp.kungfuboy.object.StoneMonster.STONE_STATE;
import com.lowp.kungfuboy.object.StoneMonster.STONE_VIEW_DIRECTION;
import com.lowp.kungfuboy.object.WoodMonster;
import com.lowp.kungfuboy.object.WoodMonster.WOOD_STATE;
import com.lowp.kungfuboy.object.WoodMonster.WOOD_VIEW_DIRECTION;
import com.lowp.kungfuboy.util.Assets;
import com.lowp.kungfuboy.util.AudioManager;
import com.lowp.kungfuboy.util.CameraHelper;

/**
 * 负责游戏世界的逻辑更新和输入事件
 * 
 * @author lowp
 *
 */
public class WorldController extends InputAdapter implements Disposable {
	public static final String TAG = WorldController.class.getName();
	public Main main;
	public AbstractGameLevel level;
	public CameraHelper cameraHelper;
	public WorldRenderer worldRenderer;
	public boolean gameOverUiShow = true;

	public WorldController(AbstractGameLevel level, Main main) {
		this.level = level;
		this.main = main;
		init();
	}

	public void init() {
		cameraHelper = new CameraHelper(level);
		level.cameraHelper = cameraHelper;
	}

	public void update(float deltaTime) {
		cameraHelper.update(deltaTime);
		if (!level.isPause) {
			level.update(deltaTime
					* ((level.gameOver || level.hero.isSkills) ? 0.5f : 1));

		}

		if (!level.isStartPlot && !level.isPause) {
			updateHero();
		}else{
			if (AudioManager.instance.sound != null) {
				AudioManager.instance.sound.stop();
			}
		}

		updateLevel();

	}

	private void updateHero() {
		
		
		// 脚步声
		if (worldRenderer.touchpad.isTouched()
				&& level.hero.state == HERO_STATE.RUN) {
			if (!level.hero.isRun) {
				level.hero.isRun = true;
				AudioManager.instance.play(Assets.instance.assetSound.hero_run,
						true);
			}
		}else{
			level.hero.isRun = false;
			if (AudioManager.instance.sound != null) {
				AudioManager.instance.sound.stop();
			}
		}

		// 更新hero当前血量条
		worldRenderer.hero_bar_hp.setSize(
				(287 * (level.hero.currentHP / level.hero.HP)) - 10, 8);

		// 控制主角移动
		if (level.hero.state != HERO_STATE.ATTACK_Normal
				&& level.hero.state != HERO_STATE.HURT && !level.isPause
				&& !level.gameOver) {

			level.hero.position.x += worldRenderer.touchpad.getKnobPercentX() * 0.05f;

			if (level.hero.state != HERO_STATE.JUMP_UP
					&& level.hero.state != HERO_STATE.JUMP_DOWN
					&& level.hero.state != HERO_STATE.HURT) {

				level.hero.position.y += worldRenderer.touchpad
						.getKnobPercentY() * 0.03f;
			}

		}

		// 控制主角方向
		if (level.hero.state != HERO_STATE.ATTACK_Normal
				&& level.hero.state != HERO_STATE.ATTACK_SKILL
				&& level.hero.state != HERO_STATE.JUMP_UP
				&& level.hero.state != HERO_STATE.JUMP_DOWN
				&& level.hero.state != HERO_STATE.HURT
				&& worldRenderer.touchpad.getKnobPercentX() < 0) {

			level.hero.direction = HERO_VIEW_DIRECTION.LEFT;
			level.hero.state = HERO_STATE.RUN;

		} else if (level.hero.state != HERO_STATE.ATTACK_Normal
				&& level.hero.state != HERO_STATE.ATTACK_SKILL
				&& level.hero.state != HERO_STATE.JUMP_UP
				&& level.hero.state != HERO_STATE.JUMP_DOWN
				&& level.hero.state != HERO_STATE.HURT
				&& worldRenderer.touchpad.getKnobPercentX() > 0) {

			level.hero.direction = HERO_VIEW_DIRECTION.RIGHT;
			level.hero.state = HERO_STATE.RUN;

		} else if (level.hero.state != HERO_STATE.ATTACK_Normal
				&& level.hero.state != HERO_STATE.ATTACK_SKILL
				&& level.hero.state != HERO_STATE.JUMP_UP
				&& level.hero.state != HERO_STATE.JUMP_DOWN
				&& level.hero.state != HERO_STATE.HURT
				&& worldRenderer.touchpad.getKnobPercentX() == 0) {

			level.hero.state = HERO_STATE.Normal;
		}

		// 相机抖动效果
		if (level.hero.state == HERO_STATE.ATTACK_SKILL && level.hero.isShake) {
			float offsetY = MathUtils.random(-0.015f, 0.015f);
			cameraHelper.setY(offsetY);
		}

		if (level.gameOver) {
			level.hero.state = HERO_STATE.Normal;
		}

	}

	public void updateLevel() {

		// 游戏结束
		if (level.gameOver && gameOverUiShow) {
			worldRenderer.overFlash.setVisible(true);
			worldRenderer.overFlash.bg.setVisible(true);
			worldRenderer.overFlash.bg.addAction(new SequenceAction(Actions
					.fadeOut(5), Actions.run(new Runnable() {
				@Override
				public void run() {
					AudioManager.instance.play(
							Assets.instance.assetMusic.victory, false);
					worldRenderer.stageGUI.addActor(worldRenderer.gameOverUi);
					worldRenderer.stageGUI
							.addActor(worldRenderer.gameOverUi.bg);
					worldRenderer.stageGUI
							.addActor(worldRenderer.gameOverUi.rotationLight);
					worldRenderer.stageGUI
							.addActor(worldRenderer.gameOverUi.victory);
					worldRenderer.stageGUI
							.addActor(worldRenderer.gameOverUi.star_01);
					worldRenderer.stageGUI
							.addActor(worldRenderer.gameOverUi.star_02);
					worldRenderer.stageGUI
							.addActor(worldRenderer.gameOverUi.star_03);
					worldRenderer.stageGUI
							.addActor(worldRenderer.gameOverUi.tipsNext);
					worldRenderer.stageGUI
							.addActor(worldRenderer.gameOverUi.before);
				}
			})));
			gameOverUiShow = false;
		}

		// 连击效果
		if (level.hero.isHit) {
			worldRenderer.lianJi.score = level.hero.lianjiTimes;
			if (!level.isPause) {
				worldRenderer.lianJi.isFlash = true;
			} else {
				worldRenderer.lianJi.isFlash = false;
			}

			worldRenderer.lianJi.setVisible(true);
			worldRenderer.lianJi.visibleTime = 1.5f;
		} else {
			worldRenderer.lianJi.isFlash = false;
			float delta = Gdx.graphics.getDeltaTime();
			// 连击结束，效果1.5s后消失
			if (worldRenderer.lianJi.isVisible()) {
				worldRenderer.lianJi.visibleTime -= delta;
				if (worldRenderer.lianJi.visibleTime < 0) {
					worldRenderer.lianJi.visibleTime = 1.5f;
					worldRenderer.lianJi.setVisible(false);
					level.hero.lianjiTimes = 0;
				}
			}
		}

		// 改变actor在group中的深度
		for (int i = 0; i < level.getChildren().size; i++) {
			for (int j = 0; j < level.getChildren().size; j++) {
				if (level.getChildren().get(i).getY() > level.getChildren()
						.get(j).getY()) {
					level.getChildren().get(i)
							.setZIndex(level.getChildren().get(j).getZIndex());
				}
			}
		}

		/**
		 * 碰撞检测
		 */
		collisionHeroWithLinoMonster();
		collisionHeroWithWoodMonster();
		collisionHeroWithStoneMonster();

	}

	// 碰撞检测Hero<-->LionMonster
	private void collisionHeroWithLinoMonster() {

		for (LionMonster lionMonster : level.lionMonsters) {
			// 如果对象被标记为死亡状态，则移除该对象
			if (lionMonster.state == LION_STATE.DIE) {
				level.lionMonsters.removeValue(lionMonster, false);
			}

			if (level.hero.rectangle.overlaps(lionMonster.rectangle)
					&& Math.abs(level.hero.position.y - lionMonster.position.y) <= 0.2f) {

				if (((level.hero.state == HERO_STATE.ATTACK_Normal || level.hero.state == HERO_STATE.ATTACK_SKILL))
						&& (level.hero.isAttack && level.hero.isValidAttack)
						&& lionMonster.state != LION_STATE.ATTACK
						&& lionMonster.state != LION_STATE.HURT) {

					if (level.hero.isSkills_Gun
							|| (level.hero.direction == HERO_VIEW_DIRECTION.LEFT && lionMonster.direction == LION_VIEW_DIRECTION.RIGHT)
							|| (level.hero.direction == HERO_VIEW_DIRECTION.RIGHT && lionMonster.direction == LION_VIEW_DIRECTION.LEFT)) {

						level.hero.lianjiTimes++;
						lionMonster.state = LION_STATE.HURT;
						lionMonster.stateTime = 0;
						lionMonster.currentHP -= level.hero.ATK;
						level.hero.isHit = true;
						if (!level.hero.isSkills_Dao) {
							AudioManager.instance
									.play(Assets.instance.assetSound.hero_isHit);
						} else {
							AudioManager.instance
									.play(Assets.instance.assetSound.hero_isHit2);
						}

					}
				}

				if (lionMonster.state == LION_STATE.ATTACK
						&& level.hero.state != HERO_STATE.ATTACK_SKILL
						&& level.hero.state != HERO_STATE.JUMP_DOWN
						&& level.hero.state != HERO_STATE.JUMP_UP
						&& lionMonster.isATK) {

					level.hero.currentHP -= lionMonster.ATK;
					level.hero.state = HERO_STATE.HURT;
					heroBack();
					AudioManager.instance
							.play(Assets.instance.assetSound.hero_isHit);

					if (lionMonster.direction == LION_VIEW_DIRECTION.RIGHT) {
						level.hero.direction = HERO_VIEW_DIRECTION.LEFT;
					} else if (lionMonster.direction == LION_VIEW_DIRECTION.LEFT) {
						level.hero.direction = HERO_VIEW_DIRECTION.RIGHT;
					}

				}

			}

		}

	}

	// 碰撞检测hero<-->WoodMonster
	private void collisionHeroWithWoodMonster() {
		for (WoodMonster woodMonster : level.woodMonsters) {
			// 如果对象被标记为死亡状态，则移除该对象
			if (woodMonster.state == WOOD_STATE.DIE) {
				level.woodMonsters.removeValue(woodMonster, false);
			}

			if (level.hero.rectangle.overlaps(woodMonster.rectangle)
					&& Math.abs(level.hero.position.y - woodMonster.position.y) <= 0.2f) {

				if (((level.hero.state == HERO_STATE.ATTACK_Normal || level.hero.state == HERO_STATE.ATTACK_SKILL))
						&& (level.hero.isAttack && level.hero.isValidAttack)
						&& woodMonster.state != WOOD_STATE.ATTACK
						&& woodMonster.state != WOOD_STATE.HURT) {

					if (level.hero.isSkills_Gun
							|| (level.hero.direction == HERO_VIEW_DIRECTION.LEFT && woodMonster.direction == WOOD_VIEW_DIRECTION.RIGHT)
							|| (level.hero.direction == HERO_VIEW_DIRECTION.RIGHT && woodMonster.direction == WOOD_VIEW_DIRECTION.LEFT)) {

						level.hero.lianjiTimes++;
						woodMonster.state = WOOD_STATE.HURT;
						woodMonster.stateTime = 0;
						woodMonster.currentHP -= level.hero.ATK;
						level.hero.isHit = true;
						if (!level.hero.isSkills_Dao) {
							AudioManager.instance
									.play(Assets.instance.assetSound.hero_isHit);
						} else {
							AudioManager.instance
									.play(Assets.instance.assetSound.hero_isHit2);
						}
					}
				}

				if (woodMonster.state == WOOD_STATE.ATTACK
						&& level.hero.state != HERO_STATE.ATTACK_SKILL
						&& level.hero.state != HERO_STATE.JUMP_DOWN
						&& level.hero.state != HERO_STATE.JUMP_UP
						&& woodMonster.isATK) {

					level.hero.currentHP -= woodMonster.ATK;
					level.hero.state = HERO_STATE.HURT;
					heroBack();
					AudioManager.instance
							.play(Assets.instance.assetSound.hero_isHit);

					if (woodMonster.direction == WOOD_VIEW_DIRECTION.RIGHT) {
						level.hero.direction = HERO_VIEW_DIRECTION.LEFT;
					} else if (woodMonster.direction == WOOD_VIEW_DIRECTION.LEFT) {
						level.hero.direction = HERO_VIEW_DIRECTION.RIGHT;
					}

				}

			}

		}
	}

	// 碰撞检测hero<-->stoneMonster
	private void collisionHeroWithStoneMonster() {
		for (StoneMonster stoneMonster : level.stoneMonsters) {
			// 如果对象被标记为死亡状态，则移除该对象
			if (stoneMonster.state == STONE_STATE.DIE) {
				level.stoneMonsters.removeValue(stoneMonster, false);
			}

			if (level.hero.rectangle.overlaps(stoneMonster.rectangle)
					&& Math.abs(level.hero.position.y - stoneMonster.position.y) <= 0.2f) {

				if (((level.hero.state == HERO_STATE.ATTACK_Normal || level.hero.state == HERO_STATE.ATTACK_SKILL))
						&& (level.hero.isAttack && level.hero.isValidAttack)
						&& stoneMonster.state != STONE_STATE.ATTACK
						&& stoneMonster.state != STONE_STATE.HURT) {

					if (level.hero.isSkills_Gun
							|| level.hero.isSkills_Fist
							|| (level.hero.direction == HERO_VIEW_DIRECTION.LEFT && stoneMonster.direction == STONE_VIEW_DIRECTION.RIGHT)
							|| (level.hero.direction == HERO_VIEW_DIRECTION.RIGHT && stoneMonster.direction == STONE_VIEW_DIRECTION.LEFT)) {

						level.hero.lianjiTimes++;
						stoneMonster.state = STONE_STATE.HURT;
						stoneMonster.stateTime = 0;
						stoneMonster.currentHP -= level.hero.ATK;
						level.hero.isHit = true;
						if (!level.hero.isSkills_Dao) {
							AudioManager.instance
									.play(Assets.instance.assetSound.hero_isHit);
						} else {
							AudioManager.instance
									.play(Assets.instance.assetSound.hero_isHit2);
						}

					}
				}

				if (stoneMonster.state == STONE_STATE.ATTACK
						&& level.hero.state != HERO_STATE.ATTACK_SKILL
						&& level.hero.state != HERO_STATE.JUMP_DOWN
						&& level.hero.state != HERO_STATE.JUMP_UP
						&& stoneMonster.isATK) {

					level.hero.currentHP -= stoneMonster.ATK;
					level.hero.state = HERO_STATE.HURT;
					heroBack();
					AudioManager.instance
							.play(Assets.instance.assetSound.hero_isHit);

					if (stoneMonster.direction == STONE_VIEW_DIRECTION.RIGHT) {
						level.hero.direction = HERO_VIEW_DIRECTION.LEFT;
					} else if (stoneMonster.direction == STONE_VIEW_DIRECTION.LEFT) {
						level.hero.direction = HERO_VIEW_DIRECTION.RIGHT;
					}

				}

			}

		}
	}

	// hero受伤时后退
	private void heroBack() {
		if (level.hero.direction == HERO_VIEW_DIRECTION.RIGHT) {
			level.hero.position.x -= 0.03f;
		} else if (level.hero.direction == HERO_VIEW_DIRECTION.LEFT) {
			level.hero.position.x += 0.03f;
		}
	}

	@Override
	public void dispose() {

	}

}
