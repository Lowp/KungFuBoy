package com.lowp.kungfuboy.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.lowp.kungfuboy.object.Hero;
import com.lowp.kungfuboy.object.LionMonster;
import com.lowp.kungfuboy.object.Map;
import com.lowp.kungfuboy.object.StoneMonster;
import com.lowp.kungfuboy.object.WoodMonster;
import com.lowp.kungfuboy.object.Hero.HERO_STATE;
import com.lowp.kungfuboy.object.LionMonster.LION_STATE;
import com.lowp.kungfuboy.object.WoodMonster.WOOD_STATE;
import com.lowp.kungfuboy.util.Assets;

public class Level05 extends AbstractGameLevel {
	public static final String TAG = Level01.class.getName();
	private Array<Actor> actors;
	// 怪物出现的次数
	public int lion_times = 1;
	public int wood_times = 2;
	public int stone_times = 1;

	public Level05() {
		index = 5;
		lionMonsters = new Array<LionMonster>();
		woodMonsters = new Array<WoodMonster>();
		stoneMonsters = new Array<StoneMonster>();
		init();
	}

	@Override
	public void init() {
		map = new Map(Assets.instance.maps.level05_left, Assets.instance.maps.level05_right);
		hero = new Hero(map);

		LionMonster lionMonster1 = new LionMonster(hero, map, this);
		LionMonster lionMonster2 = new LionMonster(hero, map, this);

		lionMonster1.position.y = lionMonster1.position.y + 0.2f;
		lionMonster2.position.y = lionMonster2.position.y - 0.2f;

		lionMonsters.add(lionMonster1);
		lionMonsters.add(lionMonster2);

		WoodMonster woodMonster1 = new WoodMonster(hero, map, this);
		woodMonsters.add(woodMonster1);

		addActor(hero);
		for (LionMonster lionMonster : lionMonsters) {
			this.lionMonster = lionMonster;
			addActor(lionMonster);
		}

		for (WoodMonster woodMonster : woodMonsters) {
			this.woodMonster = woodMonster;
			addActor(woodMonster);
		}
	}

	@Override
	public void update(float datleTime) {
		map.act(datleTime);
		act(datleTime);

		// 开场剧情
		if (isStartPlot) {
			levelPlot();
		}

		/**
		 * 产生新的怪物
		 */
		if (lionMonsters.size == 1 && lion_times > 0) {
			addLionMonster();
			lion_times--;
		}

		if (woodMonsters.size == 0 && wood_times > 0) {
			addWoodMonster();
			if (wood_times == 2 && stone_times > 0) {
				addStoneMonster();
				stone_times--;
			}
			wood_times--;
		}

		// 判断游戏时候结束
		if (lionMonsters.size == 0 && woodMonsters.size == 0
				&& stoneMonsters.size == 0 && !gameOver) {
			gameOver = true;
		}
	}

	// 开场剧情
	private void levelPlot() {
		hero.state = HERO_STATE.RUN;
		hero.position.x += 0.05;
		if (hero.position.x > 1.8f) {
			hero.position.x = 1.8f;
			hero.state = HERO_STATE.Normal;

			for (LionMonster lionMonster : lionMonsters) {
				lionMonster.position.x -= 0.025f;
				if (lionMonster.position.x < 7f) {
					lionMonster.position.x = 7f;
					lionMonster.state = LION_STATE.Normal;
				}
			}

			for (WoodMonster woodMonster : woodMonsters) {
				woodMonster.position.x -= 0.025f;
				if (woodMonster.position.x < 5.5f) {
					woodMonster.position.x = 5.5f;
					woodMonster.state = WOOD_STATE.Normal;
					isStartPlot = false;
				}
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		map.draw(batch, 1.0f);
		draw(batch, 1.0f);
	}

}
