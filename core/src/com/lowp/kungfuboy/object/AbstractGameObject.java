package com.lowp.kungfuboy.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.lowp.kungfuboy.level.AbstractGameLevel;
import com.lowp.kungfuboy.util.Assets;

public abstract class AbstractGameObject extends Actor implements Disposable{
	// 大小
	public Vector2 size;
	// 位置
	public Vector2 position;
	// 中心
	public Vector2 origin;
	// 缩放
	public Vector2 scale;
	// 旋转
	public float rotation;

	// 状态动画
	public float stateTime;
	public Animation animation;
	// 影子动画
	public Animation animShadow;
	// 影子偏移
	public Vector2 shadowOffset;
	// 碰撞矩形
	public Rectangle rectangle;
	// 被追逐的游戏对象
	public AbstractGameObject targer;
	// 当前关卡
	public AbstractGameLevel level;

	public Map map;
	public Hero hero;
	public LionMonster lionMonster;
	public WoodMonster woodMonster;
	public StoneMonster stoneMonster;

	// 血条
	public Texture HPBar_Bg;
	public Texture HPBar_red;

	// 掉落分数
	public int scores;
	// 处于准备状态
	public boolean isReading = false;

	// 是否死亡
	public boolean isDie = false;
	
	public AbstractGameObject() {
		size = new Vector2(1, 1);
		position = new Vector2();
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0f;
		animShadow = Assets.instance.gameObject.animShadow;
		shadowOffset = new Vector2();
		rectangle = new Rectangle();
		HPBar_Bg = new Texture("gui/bar/monster-bar-bg.png");
		HPBar_red = new Texture("gui/bar/monster-bar-red.png");
		HPBar_Bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		HPBar_red.setFilter(TextureFilter.Linear, TextureFilter.Linear);

	}

	public void act(float datleTime) {
		stateTime += datleTime;
		setY(position.y);
		rectangle.set(position.x, position.y, size.x, size.y);
		scores = MathUtils.random(1000, 2000);
	}

	public void setTarger(AbstractGameObject targer) {
		this.targer = targer;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;

	}
	
	@Override
	public void dispose() {
		HPBar_Bg.dispose();
		HPBar_red.dispose();
	}
	
}
