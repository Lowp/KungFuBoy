package com.lowp.kungfuboy.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.lowp.kungfuboy.level.AbstractGameLevel;
import com.lowp.kungfuboy.util.Assets;
import com.lowp.kungfuboy.util.AudioManager;

/**
 * 石头人怪物
 * 
 * @author lowp
 *
 */
public class StoneMonster extends AbstractGameObject {

	public static final String TAG = LionMonster.class.getName();

	public STONE_VIEW_DIRECTION direction;
	public STONE_STATE state;

	// 生命值，拥有的怒气值，攻击力,当前生命值，
	public float HP, havaMP, ATK, currentHP;
	public int havaScore = 1;
	// 是否具有有效攻击
	public boolean isATK = false;
	// 状态切换时间
	private float stillTime;
	private float mStateTime;
	public boolean isTurnRound;

	public StoneMonster(Hero hero, Map map, AbstractGameLevel level) {
		this.map = map;
		this.hero = hero;
		this.level = level;
		init();
	}

	// 视角方向
	public enum STONE_VIEW_DIRECTION {
		LEFT, RIGHT
	}

	// 状态
	public enum STONE_STATE {
		Normal, WALK, ATTACK, HURT, DIE
	}

	private void init() {
		HP = 400;
		currentHP = 400;
		havaMP = 10;
		ATK = 1.5f;
		size.set(2, 1.5f);
		direction = STONE_VIEW_DIRECTION.LEFT;
		state = STONE_STATE.WALK;
		position.set(map.size.x - 2, map.size.y / 4);
		scale.set(0.008f, 0.005f);
		setTarger(hero);
	}

	@Override
	public void act(float datleTime) {
		super.act(datleTime);

		mStateTime += datleTime;

		// 判断是否转身
		if (hero.position.x > position.x) {
			direction = STONE_VIEW_DIRECTION.RIGHT;
		} else {
			direction = STONE_VIEW_DIRECTION.LEFT;
		}

		// 主角不能移动到游戏世界外
		if (map != null) {
			// 右边界
			if (position.x + 1 > map.size.x) {
				position.x = map.size.x - 1;
			}
			// 左边界
			if (position.x + 1 < 0) {
				position.x = -1;
			}
			// 上边界
			if (position.y > 1.5f) {
				position.y = 1.5f;
			}
			// 下边界
			if (position.y < 0.2f) {
				position.y = 0.2f;
			}
		}

		// 更新状态
		updateState(datleTime);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		batch.draw(HPBar_Bg, position.x + 0.35f, position.y + 1.35f, 1.0f,
				0.08f);
		batch.draw(HPBar_red, position.x + 0.36f, position.y + 1.368f,
				0.96f * (currentHP / HP), 0.05f);

		TextureRegion reg1 = animShadow.getKeyFrame(
				state == STONE_STATE.WALK ? mStateTime : 0, true);

		batch.draw(reg1.getTexture(),
				(direction == STONE_VIEW_DIRECTION.LEFT ? 0.5f : -0.2f)
						+ position.x, position.y - 0.05f, origin.x, origin.y,
				reg1.getRegionWidth(), reg1.getRegionHeight(), scale.x * 2.0f,
				scale.y * 2.0f, rotation, reg1.getRegionX(), reg1.getRegionY(),
				reg1.getRegionWidth(), reg1.getRegionHeight(), false, false);

		if (animation != null) {
			TextureRegion reg2 = animation.getKeyFrame(stateTime, true);
			batch.draw(reg2.getTexture(), position.x, position.y, origin.x,
					origin.y, reg2.getRegionWidth(), reg2.getRegionHeight(),
					scale.x, scale.y, rotation, reg2.getRegionX(),
					reg2.getRegionY(), reg2.getRegionWidth(),
					reg2.getRegionHeight(),
					direction == STONE_VIEW_DIRECTION.RIGHT, false);
		}

	}

	private void updateState(float datleTime) {
		if (!level.isStartPlot) {
			stillTime += datleTime;
		}

		// 当PH小于0，标记为死亡状态
		if (currentHP < 0) {
			currentHP = 0;
			state = STONE_STATE.DIE;

		}

		switch (state) {
		case Normal:
			setAnimation(Assets.instance.stoneMonster.animWalk);
			if (!level.isStartPlot) {
				state = STONE_STATE.WALK;

			}
			break;
		case WALK:
			setAnimation(Assets.instance.stoneMonster.animWalk);
			// 追逐target
			float random = MathUtils.random(0.001f, 0.016f);
			if (!level.isStartPlot) {
				position.lerp(targer.position, random);
			}

			if (stillTime > 4 - random * 200) {
				stillTime = 0;
				state = STONE_STATE.ATTACK;
				AudioManager.instance
						.play(Assets.instance.assetSound.monster_att);

			}
			break;
		case ATTACK:
			isATK = false;
			setAnimation(Assets.instance.stoneMonster.animAttack);
			if (stillTime > 1) {
				stillTime = 0;
				state = STONE_STATE.WALK;
				isATK = true;
			}

			break;
		case HURT:
			setAnimation(Assets.instance.stoneMonster.animHurt);
			break;
		case DIE:
			setAnimation(Assets.instance.stoneMonster.animDie);
			if (!isDie) {
				AudioManager.instance.play(Assets.instance.assetSound.stonedie);
				isDie = true;
			}
			break;

		default:
			break;
		}

		if (animation != null && animation.isAnimationFinished(stateTime)) {
			stateTime = 0;
			if (state == STONE_STATE.HURT) {
				state = STONE_STATE.WALK;
			} else if (state == STONE_STATE.DIE) {
				setVisible(false);
				level.removeActor(this);
			}

		}

	}
}
