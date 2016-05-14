package com.lowp.kungfuboy.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.lowp.kungfuboy.object.Hero;
import com.lowp.kungfuboy.object.LionMonster;
import com.lowp.kungfuboy.object.Map;
import com.lowp.kungfuboy.object.StoneMonster;
import com.lowp.kungfuboy.object.WoodMonster;
import com.lowp.kungfuboy.object.LionMonster.LION_VIEW_DIRECTION;
import com.lowp.kungfuboy.object.StoneMonster.STONE_VIEW_DIRECTION;
import com.lowp.kungfuboy.object.WoodMonster.WOOD_VIEW_DIRECTION;
import com.lowp.kungfuboy.util.CameraHelper;

/**
 * �ؿ�
 * 
 * @author lowp
 *
 */
public abstract class AbstractGameLevel extends Group {

	public int index = 0;
	public Hero hero;
	public Map map;
	public LionMonster lionMonster;
	public StoneMonster stoneMonster;
	public WoodMonster woodMonster;
	public Array<LionMonster> lionMonsters;// ʨ���˹�������
	public Array<StoneMonster> stoneMonsters;// ʯͷ�˹�������
	public Array<WoodMonster> woodMonsters;// ľ��׮��������
	public CameraHelper cameraHelper;

	public boolean isStartPlot = true;// ��������
	public boolean gameOver = false; // ��Ϸ����
	public boolean isVictory = false;// ʤ��
	public boolean isFail = false;// ʧ��
	public boolean isPause = false;// ��ͣ

	// ��ʼ���ؿ�
	public abstract void init();

	// ������Ϸ����״̬
	public abstract void update(float datleTime);

	// ��Ⱦ��Ϸ����
	public abstract void render(SpriteBatch batch);

	// ����µ�ʨ�ӹ�
	public void addLionMonster() {
		boolean isLeft = MathUtils.randomBoolean();
		LionMonster lionMonster = new LionMonster(hero, map, this);
		lionMonster.direction = isLeft ? LION_VIEW_DIRECTION.LEFT
				: LION_VIEW_DIRECTION.RIGHT;
		lionMonster.position.set(isLeft ? (cameraHelper.getPosition().x - 1)
				: (cameraHelper.getPosition().x
						+ cameraHelper.getCamera().viewportWidth + 1),
				lionMonster.position.y);
		lionMonsters.add(lionMonster);
		addActor(lionMonster);
	}

	// ���ľ��׮����
	public void addWoodMonster() {
		boolean isLeft = MathUtils.randomBoolean();
		WoodMonster woodMonster = new WoodMonster(hero, map, this);
		woodMonster.direction = isLeft ? WOOD_VIEW_DIRECTION.LEFT
				: WOOD_VIEW_DIRECTION.RIGHT;
		woodMonster.position.set(isLeft ? (cameraHelper.getPosition().x - 1)
				: (cameraHelper.getPosition().x
						+ cameraHelper.getCamera().viewportWidth + 1),
				woodMonster.position.y);
		woodMonsters.add(woodMonster);
		addActor(woodMonster);
	}

	// ���ʯͷ�˹���
	public void addStoneMonster() {
		boolean isLeft = MathUtils.randomBoolean();
		StoneMonster stoneMonster = new StoneMonster(hero, map, this);
		stoneMonster.direction = isLeft ? STONE_VIEW_DIRECTION.LEFT
				: STONE_VIEW_DIRECTION.RIGHT;
		stoneMonster.position.set(isLeft ? (cameraHelper.getPosition().x - 1)
				: (cameraHelper.getPosition().x
						+ cameraHelper.getCamera().viewportWidth + 1),
				stoneMonster.position.y);
		stoneMonsters.add(stoneMonster);
		addActor(stoneMonster);
	}
}
