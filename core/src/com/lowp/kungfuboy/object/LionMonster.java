package com.lowp.kungfuboy.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.lowp.kungfuboy.level.AbstractGameLevel;
import com.lowp.kungfuboy.util.Assets;
import com.lowp.kungfuboy.util.AudioManager;

/**
 * 狮人怪物
 * 
 * @author lowp
 *
 */
public class LionMonster extends AbstractGameObject {
	public static final String TAG = LionMonster.class.getName();

	public LION_VIEW_DIRECTION direction;
	public LION_STATE state;

	// 生命值，拥有的怒气值，攻击力,当前生命值，
	public float HP, havaMP, ATK, currentHP;
	public int havaScore = 1;
	// 是否具有有效攻击
	public boolean isATK = false;
	// 状态切换时间
	private float stillTime;
	private float mStateTime;
	public boolean isTurnRound;

	public LionMonster(Hero hero, Map map, AbstractGameLevel level) {
		this.map = map;
		this.hero = hero;
		this.level = level;
		init();
	}

	// 视角方向
	public enum LION_VIEW_DIRECTION {
		LEFT, RIGHT
	}

	// 状态
	public enum LION_STATE {
		Normal, WALK, ATTACK, HURT, DIE
	}

	private void init() {
		HP = 200;
		currentHP = 200;
		havaMP = 5;
		ATK = 1f;
		direction = LION_VIEW_DIRECTION.LEFT;
		state = LION_STATE.WALK;
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
			direction = LION_VIEW_DIRECTION.RIGHT;
		} else {
			direction = LION_VIEW_DIRECTION.LEFT;
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
		TextureRegion reg1 = null;
		TextureRegion reg2 = null;

		// 血条
		batch.draw(HPBar_Bg, position.x - 0.15f, position.y + 1.0f, 1.0f, 0.08f);
		batch.draw(HPBar_red, position.x - 0.13f, position.y + 1.018f,
				0.96f * (currentHP / HP), 0.05f);

		// 影子
		reg1 = animShadow.getKeyFrame(
				state == LION_STATE.WALK ? mStateTime : 0, true);

		batch.draw(reg1.getTexture(), position.x - 0.05f, position.y - 0.05f,
				origin.x, origin.y, reg1.getRegionWidth(),
				reg1.getRegionHeight(), scale.x, scale.y, rotation,
				reg1.getRegionX(), reg1.getRegionY(), reg1.getRegionWidth(),
				reg1.getRegionHeight(), false, false);

		if (animation != null) {
			// 状态动画
			reg2 = animation.getKeyFrame(stateTime, true);
			batch.draw(reg2.getTexture(), position.x, position.y, origin.x,
					origin.y, reg2.getRegionWidth(), reg2.getRegionHeight(),
					scale.x, scale.y, rotation, reg2.getRegionX(),
					reg2.getRegionY(), reg2.getRegionWidth(),
					reg2.getRegionHeight(),
					direction == LION_VIEW_DIRECTION.RIGHT, false);
		}

	}

	private void updateState(float datleTime) {
		if (!level.isStartPlot) {
			stillTime += datleTime;
		}

		// 当PH小于0，标记为死亡状态
		if (currentHP <= 0) {
			currentHP = 0;
			state = LION_STATE.DIE;

		}

		switch (state) {
		case Normal:
			setAnimation(Assets.instance.lionMonster.animNormal);
			if (!level.isStartPlot) {
				state = LION_STATE.WALK;
			}
			break;
		case WALK:
			setAnimation(Assets.instance.lionMonster.animWalk);

			// 追逐target
			float random = MathUtils.random(0.001f, 0.008f);
			if (!level.isStartPlot) {
				position.lerp(targer.position, random);
			}

			if (stillTime > 6 - random * 200) {
				stillTime = 0;
				state = LION_STATE.ATTACK;
				AudioManager.instance.play(Assets.instance.assetSound.monster_att);
				isATK = true;
			}
			break;
		case ATTACK:
			setAnimation(Assets.instance.lionMonster.animAttack);
			isATK = false;
			if (stillTime > 2) {
				stillTime = 0;
				state = LION_STATE.WALK;
			}
			break;
		case HURT:
			setAnimation(Assets.instance.lionMonster.animHurt);
			break;
		case DIE:
			setAnimation(Assets.instance.lionMonster.animDie);
			if (!isDie) {
				AudioManager.instance.play(Assets.instance.assetSound.liondie);
				isDie = true;
			}
			break;

		default:
			break;
		}

		if (animation != null && animation.isAnimationFinished(stateTime)) {
			stateTime = 0;
			if (state == LION_STATE.HURT) {
				state = LION_STATE.WALK;
			} else if (state == LION_STATE.DIE) {
				setVisible(false);
				level.removeActor(this);
			}

		}

	}

}
