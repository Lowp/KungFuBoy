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
	// ��С
	public Vector2 size;
	// λ��
	public Vector2 position;
	// ����
	public Vector2 origin;
	// ����
	public Vector2 scale;
	// ��ת
	public float rotation;

	// ״̬����
	public float stateTime;
	public Animation animation;
	// Ӱ�Ӷ���
	public Animation animShadow;
	// Ӱ��ƫ��
	public Vector2 shadowOffset;
	// ��ײ����
	public Rectangle rectangle;
	// ��׷�����Ϸ����
	public AbstractGameObject targer;
	// ��ǰ�ؿ�
	public AbstractGameLevel level;

	public Map map;
	public Hero hero;
	public LionMonster lionMonster;
	public WoodMonster woodMonster;
	public StoneMonster stoneMonster;

	// Ѫ��
	public Texture HPBar_Bg;
	public Texture HPBar_red;

	// �������
	public int scores;
	// ����׼��״̬
	public boolean isReading = false;

	// �Ƿ�����
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
