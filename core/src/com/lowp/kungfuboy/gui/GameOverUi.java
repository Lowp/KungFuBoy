package com.lowp.kungfuboy.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lowp.kungfuboy.util.Assets;

public class GameOverUi extends Actor {
	public Image bg; // ±³¾°
	public Image rotationLight; // Ðý×ª¹âÊø
	public Image star_01; // ÐÇÐÇ
	public Image star_02;
	public Image star_03;
	public Image victory;
	public Image tipsNext;
	public Image before;

	public GameOverUi() {
		bg = new Image(Assets.instance.gameGUI.game_gameOverBg);
		before = new Image(Assets.instance.gameGUI.game_gameOverBg);
		rotationLight = new Image(Assets.instance.gameGUI.game_rotationLight);
		star_01 = new Image(Assets.instance.gameGUI.icon_starLittle);
		star_02 = new Image(Assets.instance.gameGUI.icon_starLittle);
		star_03 = new Image(Assets.instance.gameGUI.icon_starLittle);
		tipsNext = new Image(Assets.instance.gameGUI.game_tipsNext);
		victory = new Image(Assets.instance.gameGUI.game_victory);
		init();
	}

	public void init() {
		bg.setColor(0, 0, 0, 0.9f);
		before.setColor(0, 0, 0, 0.0f);
		
		rotationLight.setSize(500, 500);
		rotationLight.setOrigin(250, 250);
		rotationLight.setPosition(175, 90);
		
		
		
		victory.setSize(250, 120);
		victory.setPosition(310, 310);
		
		tipsNext.setSize(380, 40);
		tipsNext.setPosition(230, 80);

		star_01.setSize(300, 300);
		star_01.setOrigin(150, 150);
		star_01.setPosition(125, 100);
		star_01.addAction(Actions.scaleTo(0.3f, 0.3f, 0.1f));

		
		star_02.setSize(300, 300);
		star_02.setOrigin(150, 150);
		star_02.setPosition(278, 100);
		star_02.addAction(Actions.scaleTo(0.3f, 0.3f, 0.3f));

		
		star_03.setSize(300, 300);
		star_03.setOrigin(150, 150);
		star_03.setPosition(428, 100);
		star_03.addAction(Actions.scaleTo(0.3f, 0.3f, 0.5f));
		
	}

	int i = 0;
	@Override
	public void act(float delta) {
		super.act(delta);
		i++;
		if(i%6 == 0){
			rotationLight.rotateBy(-20f);
		}
		
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

	}

}
