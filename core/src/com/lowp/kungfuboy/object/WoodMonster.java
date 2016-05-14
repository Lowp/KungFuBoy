package com.lowp.kungfuboy.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.lowp.kungfuboy.level.AbstractGameLevel;
import com.lowp.kungfuboy.util.Assets;
import com.lowp.kungfuboy.util.AudioManager;

/**
 * ľͷ�˹���
 * 
 * @author lowp
 *
 */
public class WoodMonster extends AbstractGameObject {
	public static final String TAG = LionMonster.class.getName();

	public WOOD_VIEW_DIRECTION direction;
	public WOOD_STATE state;

	// ����ֵ��ӵ�е�ŭ��ֵ��������,��ǰ����ֵ��
	public float HP, havaMP, ATK, currentHP;
	public int havaScore = 1;
	// �Ƿ������Ч����
	public boolean isATK = false;
	// ״̬�л�ʱ��
	private float stillTime;
	private float mStateTime;
	public boolean isTurnRound;

	public WoodMonster(Hero hero, Map map, AbstractGameLevel level) {
		this.map = map;
		this.hero = hero;
		this.level = level;
		init();
	}

	// �ӽǷ���
	public enum WOOD_VIEW_DIRECTION {
		LEFT, RIGHT
	}

	// ״̬
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

		// �ж��Ƿ�ת��
		if (hero.position.x > position.x) {
			direction = WOOD_VIEW_DIRECTION.RIGHT;
		} else {
			direction = WOOD_VIEW_DIRECTION.LEFT;
		}

		// ���ǲ����ƶ�����Ϸ������
		if (map != null) {
			// �ұ߽�
			if (position.x + 1 > map.size.x) {
				position.x = map.size.x - 1;
			}
			// ��߽�
			if (position.x + 1 < 0) {
				position.x = -1;
			}
			// �ϱ߽�
			if (position.y > 1.5f) {
				position.y = 1.5f;
			}
			// �±߽�
			if (position.y < 0.2f) {
				position.y = 0.2f;
			}
		}

		// ����״̬
		updateState(datleTime);
		// ����Ӱ��λ��
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

		// ��PHС��0�����Ϊ����״̬
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
			// ׷��target
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
