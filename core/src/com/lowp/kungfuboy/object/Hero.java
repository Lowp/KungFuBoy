package com.lowp.kungfuboy.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lowp.kungfuboy.util.Assets;
import com.lowp.kungfuboy.util.AudioManager;

public class Hero extends AbstractGameObject {
	public static final String TAG = Hero.class.getName();
	private Animation animHeadMark;
	private float mStateTime;
	private Map map;
	public HERO_VIEW_DIRECTION direction;
	public HERO_STATE state;

	// ����ֵ��ŭ��ֵ��������,��ǰ����ֵ����ǰŭ��ֵ
	public float HP, MP, ATK, currentHP, currentMP;
	public int lianjiTimes = 0; // ��������
	public boolean isAttack = false;// �Ƿ񷢶�����
	public boolean isHit = false;// �Ƿ���й���
	public boolean isValidAttack = false;// �Ƿ������Ч����
	public boolean isShake = false;// �������Ч��
	public boolean isSkills = false; // ����ǰ������
	public boolean isTurnRound = false;// �Ƿ�ת��
	public boolean isFist = false;// ȭ����
	public boolean isFoot = false;// �ȹ���
	public boolean isSkills_Fist = false;// ȭ_��ɱ��
	public boolean isSkills_Dao = false;// ��_��ɱ��
	public boolean isSkills_Gun = false;// ��_��ɱ��
	public boolean isJump_up = false; // ��Ծ
	public boolean isJump_down = false;
	public boolean isRun = false;

	// �����ӽǷ���
	public enum HERO_VIEW_DIRECTION {
		LEFT, RIGHT
	}

	// ����״̬
	public enum HERO_STATE {
		Normal, RUN, JUMP_UP, JUMP_DOWN, ATTACK_Normal, ATTACK_SKILL, HURT;
	}

	public Hero(Map map) {
		this.map = map;
		init();

	}

	public void init() {
		HP = 100;
		MP = 50;
		ATK = 20;
		currentHP = 100;
		currentMP = 0;
		direction = HERO_VIEW_DIRECTION.RIGHT;
		state = HERO_STATE.Normal;
		scale.set(0.008f, 0.005f);
		size.set(1.2f, 1.2f);
		position.set(-1f, 0.8f);
		mStateTime = Gdx.graphics.getDeltaTime();
		animHeadMark = Assets.instance.hero.animHeadMark;
		setAnimation(Assets.instance.hero.animNormal);
	}

	@Override
	public void act(float dalteTime) {
		super.act(dalteTime);
		mStateTime += dalteTime;

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
			if (state != HERO_STATE.JUMP_UP && state != HERO_STATE.JUMP_DOWN
					&& position.y > 1.5f) {
				position.y = 1.5f;
			}
			// �±߽�
			if (position.y < 0.2f) {
				position.y = 0.2f;
			}
		}

		// ��������״̬
		updateState(dalteTime);
		// ����Ӱ��λ��
		updataShadow(dalteTime);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		TextureRegion reg1 = animHeadMark.getKeyFrame(mStateTime, true);
		TextureRegion reg2 = animShadow.getKeyFrame(
				state == HERO_STATE.RUN ? mStateTime : 0, true);
		TextureRegion reg3 = animation.getKeyFrame(stateTime, true);

		batch.draw(reg1.getTexture(), position.x + 0.5f, position.y + 1.25f,
				origin.x, origin.y, reg1.getRegionWidth(),
				reg1.getRegionHeight(), scale.x, scale.y, rotation,
				reg1.getRegionX(), reg1.getRegionY(), reg1.getRegionWidth(),
				reg1.getRegionHeight(), false, false);

		batch.draw(
				reg2.getTexture(),
				position.x + shadowOffset.x,
				state == HERO_STATE.JUMP_UP || state == HERO_STATE.JUMP_DOWN ? currentY
						+ shadowOffset.y
						: position.y + shadowOffset.y, origin.x, origin.y, reg2
						.getRegionWidth(), reg2.getRegionHeight(),
				scale.x * 1.2f, scale.y * 1.2f, rotation, reg2.getRegionX(),
				reg2.getRegionY(), reg2.getRegionWidth(), reg2
						.getRegionHeight(), false, false);

		batch.draw(reg3.getTexture(), position.x, position.y, origin.x,
				origin.y, reg3.getRegionWidth(), reg3.getRegionHeight(),
				scale.x, scale.y, rotation, reg3.getRegionX(),
				reg3.getRegionY(), reg3.getRegionWidth(),
				reg3.getRegionHeight(), direction == HERO_VIEW_DIRECTION.LEFT,
				false);

	}

	// ��ǰy����
	float currentY = 0;

	private void updateState(float dalteTime) {
		if (currentHP < 0) {
			currentHP = 0;
		}

		// ��������״̬
		switch (state) {
		case Normal:
			setAnimation(Assets.instance.hero.animNormal);
			break;
		case RUN:
			setAnimation(Assets.instance.hero.animRun);

			break;
		case JUMP_UP:
			if (isJump_up) {
				setAnimation(Assets.instance.hero.animJump_up);
				isJump_down = true;
				isJump_up = false;
				currentY = position.y;
				AudioManager.instance
						.play(Assets.instance.assetSound.hero_jump);
			}
			// x = 1/2at*t + vt
			position.y += (1 / 2) * -30 * 0.1f * 0.1f + 0.4f * 0.1f;
			if (position.y > currentY + 0.8f) {
				position.y = currentY + 0.8f;
			}
			break;
		case JUMP_DOWN:
			if (isJump_down) {
				setAnimation(Assets.instance.hero.animJump_down);
				isJump_down = false;
			}

			position.y -= (1 / 2) * -30 * 0.1f * 0.1f + 0.6f * 0.1f;
			if (position.y < currentY) {
				position.y = currentY;
			}
			break;

		case ATTACK_Normal:
			if (isFist) {
				size.set(1.4f, 1.2f);
				setAnimation(Assets.instance.hero.animAttack_hand);
				if (stateTime < animation.getFrameDuration() / 2) {
					isValidAttack = true;
					AudioManager.instance
							.play(Assets.instance.assetSound.hero_att);

				} else {
					isValidAttack = false;
				}

			} else if (isFoot) {
				size.set(1.4f, 1.2f);
				setAnimation(Assets.instance.hero.animAttack_leg);
				if (stateTime < animation.getFrameDuration() / 2) {
					isValidAttack = true;
					AudioManager.instance
							.play(Assets.instance.assetSound.hero_log);
				} else {
					isValidAttack = false;
				}

			}

			break;
		case ATTACK_SKILL:

			if (isSkills_Fist) {
				isShake = true;
				setAnimation(Assets.instance.hero.animSkill_combo);
				if (animation.getKeyFrameIndex(stateTime) < 4) {
					size.set(0f, 0f);
					isSkills = true;
				} else {
					size.set(2.0f, 1.2f);
					isSkills = false;
				}

				if ((int) (stateTime * 100) % 6 == 0) {
					isValidAttack = true;
					if (!isSkills) {
						AudioManager.instance
								.play(Assets.instance.assetSound.hero_att);
					}
				} else {
					isValidAttack = false;
				}

			} else if (isSkills_Dao) {
				setAnimation(Assets.instance.hero.animSkill_bakandao);
				if ((int) (stateTime * 100) % 8 == 0) {
					isValidAttack = true;
					AudioManager.instance
					.play(Assets.instance.assetSound.hero_dao);
				} else {
					isValidAttack = false;
				}

			} else if (isSkills_Gun) {
				isShake = true;
				setAnimation(Assets.instance.hero.animSkill_gun);

				if (animation.getKeyFrameIndex(stateTime) < 7) {
					size.set(0, 0);
					isSkills = true;
					
				} else {
					isSkills = false;
					size.set(2.5f, 1.2f);
				}

				if ((int) (stateTime * 100) % 6 == 0) {
					if (!isSkills) {
						AudioManager.instance
								.play(Assets.instance.assetSound.hero_gun);
					}
					isValidAttack = true;
				} else {
					isValidAttack = false;
				}

			}

			break;
		case HURT:
			setAnimation(Assets.instance.hero.animHurt);
			break;
		default:
			break;
		}

		// �ж϶����Ƿ񲥷����
		if (animation.isAnimationFinished(stateTime)) {
			stateTime = 0;

			isFoot = false;
			isFist = false;
			isSkills = false;
			isSkills_Fist = false;
			isSkills_Dao = false;
			isSkills_Gun = false;
			isShake = false;
			isAttack = false;
			isValidAttack = false;
			isHit = false;

			if (state == HERO_STATE.JUMP_UP) {
				state = HERO_STATE.JUMP_DOWN;
			} else if (state == HERO_STATE.RUN) {

			} else {
				state = HERO_STATE.Normal;
				size.set(1.2f, 1.2f);
			}

		}

	}

	// ����Ӱ��λ��
	private void updataShadow(float dalteTime) {
		if (state == HERO_STATE.Normal
				&& direction == HERO_VIEW_DIRECTION.RIGHT) {
			shadowOffset.set(0.05f, 0);
		} else if (state == HERO_STATE.Normal
				&& direction == HERO_VIEW_DIRECTION.LEFT) {
			shadowOffset.set(0.07f, 0);
		} else if (state == HERO_STATE.RUN
				&& direction == HERO_VIEW_DIRECTION.RIGHT) {
			shadowOffset.set(0.05f + 0.07f, 0);
		} else if (state == HERO_STATE.RUN
				&& direction == HERO_VIEW_DIRECTION.LEFT) {
			shadowOffset.set(0.05f + 0.07f + 0.05f, 0);
		} else if (state == HERO_STATE.ATTACK_SKILL && isSkills_Fist
				&& direction == HERO_VIEW_DIRECTION.RIGHT) {
			shadowOffset.set(0.05f + 0.07f + 0.2f, 0);
		} else if (state == HERO_STATE.ATTACK_SKILL && isSkills_Fist
				&& direction == HERO_VIEW_DIRECTION.LEFT) {
			shadowOffset.set(0.05f + 0.07f + 0.8f, 0);
		} else if (state == HERO_STATE.ATTACK_SKILL && isSkills_Gun
				&& direction == HERO_VIEW_DIRECTION.RIGHT) {

			if (animation.getKeyFrameIndex(stateTime) == 2) {
				shadowOffset.set(0.5f, 0);
			} else if (animation.getKeyFrameIndex(stateTime) == 39) {
				shadowOffset.set(2.4f, 0);
			}

		} else if (state == HERO_STATE.ATTACK_SKILL && isSkills_Gun
				&& direction == HERO_VIEW_DIRECTION.LEFT) {

			if (animation.getKeyFrameIndex(stateTime) == 2) {
				shadowOffset.set(1.2f, 0);
			} else if (animation.getKeyFrameIndex(stateTime) == 7) {
				shadowOffset.set(1.9f, 0);
			} else if (animation.getKeyFrameIndex(stateTime) == 13) {
				shadowOffset.set(2.5f, 0);
			} else if (animation.getKeyFrameIndex(stateTime) == 17) {
				shadowOffset.set(1.9f, 0);
			} else if (animation.getKeyFrameIndex(stateTime) == 23) {
				shadowOffset.set(2.5f, 0);
			} else if (animation.getKeyFrameIndex(stateTime) == 27) {
				shadowOffset.set(1.9f, 0);
			} else if (animation.getKeyFrameIndex(stateTime) == 33) {
				shadowOffset.set(2.5f, 0);
			} else if (animation.getKeyFrameIndex(stateTime) == 37) {
				shadowOffset.set(1.9f, 0);
			} else if (animation.getKeyFrameIndex(stateTime) == 39) {
				shadowOffset.set(0.4f, 0);
			}
		}
	}
}