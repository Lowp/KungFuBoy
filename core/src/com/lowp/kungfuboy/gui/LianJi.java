package com.lowp.kungfuboy.gui;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lowp.kungfuboy.util.Assets;

/**
 * 连击效果
 * 
 * @author lowp
 *
 */
public class LianJi extends Actor {

	private Animation animLianJi;
	private float stateTime;
	// 闪烁效果
	public boolean isFlash = false;
	public float visibleTime = 3;

	public int score = 0;
	private int offsetX;
	public LianJi() {
		animLianJi = Assets.instance.gameGUI.animLianJi;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		stateTime += delta;
		
		if(score>=100){
			offsetX = -50;
		}else if(score>=10){
			offsetX = 0;
		}else if(score < 10){
			offsetX = 50;
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		TextureRegion region = null;
		region = animLianJi.getKeyFrame(isFlash ? stateTime : 0, true);
		batch.draw(region.getTexture(), getX(), getY(), 0, 0, getWidth(),
				getHeight(), 1, 1, 0, region.getRegionX(), region.getRegionY(),
				region.getRegionWidth(), region.getRegionHeight(), false, false);

		Assets.instance.assetFonts.lianjiFont.draw(batch, score + "",
				offsetX + getX(), getY() + 100);

	}

}
