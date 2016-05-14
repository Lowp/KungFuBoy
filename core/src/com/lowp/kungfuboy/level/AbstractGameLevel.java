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
 * 关卡
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
	public Array<LionMonster> lionMonsters;// 狮子人怪物数组
	public Array<StoneMonster> stoneMonsters;// 石头人怪物数组
	public Array<WoodMonster> woodMonsters;// 木人桩怪物数组
	public CameraHelper cameraHelper;

	public boolean isStartPlot = true;// 开场剧情
	public boolean gameOver = false; // 游戏结束
	public boolean isVictory = false;// 胜利
	public boolean isFail = false;// 失败
	public boolean isPause = false;// 暂停

	// 初始化关卡
	public abstract void init();

	// 更新游戏对象状态
	public abstract void update(float datleTime);

	// 渲染游戏对象
	public abstract void render(SpriteBatch batch);

	// 添加新的狮子怪
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

	// 添加木人桩怪物
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

	// 添加石头人怪物
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
