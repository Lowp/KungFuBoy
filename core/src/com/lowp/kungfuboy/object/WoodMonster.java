package com.lowp.kungfuboy.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.lowp.kungfuboy.level.AbstractGameLevel;
import com.lowp.kungfuboy.util.Assets;
import com.lowp.kungfuboy.util.AudioManager;

/**
 * 木头人怪物
 * 
 * @author lowp
 *
 */
public class WoodMonster extends AbstractGameObject {
	public static final String TAG = LionMonster.class.getName();

	public WOOD_VIEW_DIRECTION direction;
	public WOOD_STATE state;

	// 生命值，拥有的怒气值，攻击力,当前生命值，
	public float HP, havaMP, ATK, currentHP;
	public int havaScore = 1;
	// 是否具有有效攻击
	public boolean isATK = false;
	// 状态切换时间
	private float stillTime;
	private float mStateTime;
	public boolean isTurnRound;

	public WoodMonster(Hero hero, Map map, AbstractGameLevel level) {
		this.map = map;
		this.hero = hero;
		this.level = level;
		init();
	}

	// 视角方向
	public enum WOOD_VIEW_DIRECTION {
		LEFT, RIGHT
	}

	// 状态
	public enum WOOD_STATE {
		Normal, WALK, ATTACK, HURT, DIE
	}

	private void init() {
		HP = 400;
		currentHP = 400;
		havaMP = 10;
		ATK = 0.5f;
		direction = WOOD_VIEW_DIRECTION.LEFT;
		state = WOOD_STATE.WALK;
		if (map.reg_right != null) {
			position.set(map.size.x / 2 - 2, map.size.y / 4);
		} else {
			position.set(map.size.x - 2, map.size.y / 4);
		}
		scale.set(0.008f, 0.005f);
		setTarger(hero);
	}

	@Override
	public void act(float datleTime) {
		super.act(datleTime);
		mStateTime += datleTime;

		// 判断是否转身
		if (hero.position.x > position.x) {
			direction = WOOD_VIEW_DIRECTION.RIGHT;
		} else {
			direction = WOOD_VIEW_DIRECTION.LEFT;
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
		// 纠正影子位置
		updataShadow(datleTime);
	}

	private void updataShadow(float datleTime) {
		if (direction == WOOD_VIEW_DIRECTION.LEFT && state == WOOD_STATE.WALK) {
			shadowOffset.set(0.12f, 0);
		} else if (direction == WOOD_VIEW_DIRECTION.LEFT
				&& state == WOOD_STATE.ATTACK) {
			shadowOffset.set(0.5f, 0);
		} else if (direction == WOOD_VIEW_DIRECTION.RIGHT
				&& state == WOOD_STATE.ATTACK) {
			shadowOffset.set(0.3f, 0);
		} else if (state == WOOD_STATE.HURT) {
			shadowOffset.set(0.5f, 0);
		} else {
			shadowOffset.set(0, 0);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		batch.draw(HPBar_Bg, position.x - 0.15f, position.y + 1.0f, 1.0f, 0.08f);
		batch.draw(HPBar_red, position.x - 0.13f, position.y + 1.018f,
				0.96f * (currentHP / HP), 0.05f);

		TextureRegion reg1 = animShadow.getKeyFrame(
				state == WOOD_STATE.WALK ? mStateTime : 0, true);

		batch.draw(reg1.getTexture(), position.x - 0.05f + shadowOffset.x,
				position.y - 0.05f + shadowOffset.y, origin.x, origin.y,
				reg1.getRegionWidth(), reg1.getRegionHeight(), scale.x,
				scale.y, rotation, reg1.getRegionX(), reg1.getRegionY(),
				reg1.getRegionWidth(), reg1.getRegionHeight(), false, false);

		if (animation != null) {
			TextureRegion reg2 = animation.getKeyFrame(stateTime, true);

			batch.draw(reg2.getTexture(), position.x, position.y, origin.x,
					origin.y, reg2.getRegionWidth(), reg2.getRegionHeight(),
					scale.x, scale.y, rotation, reg2.getRegionX(),
					reg2.getRegionY(), reg2.getRegionWidth(),
					reg2.getRegionHeight(),
					direction == WOOD_VIEW_DIRECTION.RIGHT, false);
		}

	}

	private void updateState(float datleTime) {
		if (!level.isStartPlot) {
			stillTime += datleTime;
		}

		// 当PH小于0，标记为死亡状态
		if (currentHP < 0) {
			currentHP = 0;
			state = WOOD_STATE.DIE;

		}

		switch (state) {
		case Normal:
			setAnimation(Assets.instance.woodMonster.animNormal);
			if (!level.isStartPlot) {
				state = WOOD_STATE.WALK;
			}
			break;
		case WALK:
			setAnimation(Assets.instance.woodMonster.animWalk);
			// 追逐target
			float random = MathUtils.random(0.008f, 0.013f);
			if (!level.isStartPlot) {
				position.lerp(targer.position, random);
			}

			if (stillTime > 6 - random * 300) {
				stillTime = 0;
				state = WOOD_STATE.ATTACK;
				isATK = true;
				AudioManager.instance.play(Assets.instance.assetSound.monster_att);
			}
			break;
		case ATTACK:
			setAnimation(Assets.instance.woodMonster.animAttack);
			isATK = false;
			if (stillTime > 1) {
				stillTime = 0;
				state = WOOD_STATE.WALK;
				
			}
			break;
		case HURT:
			setAnimation(Assets.instance.woodMonster.animHurt);
			break;
		case DIE:
			setAnimation(Assets.instance.woodMonster.animDie);
			if (!isDie) {
				AudioManager.instance.play(Assets.instance.assetSound.wooddie);
				isDie = true;
			}
			break;

		default:
			break;
		}

		if (animation != null && animation.isAnimationFinished(stateTime)) {
			stateTime = 0;
			if (state == WOOD_STATE.HURT) {
				state = WOOD_STATE.WALK;
			} else if (state == WOOD_STATE.DIE) {
				setVisible(false);
				level.removeActor(this);
			}


		}

	}

}
