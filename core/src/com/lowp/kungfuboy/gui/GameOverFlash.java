package com.lowp.kungfuboy.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lowp.kungfuboy.util.Assets;


/**
 * ”Œœ∑Ω· ¯…¡À∏
 * 
 * @author lowp
 *
 */
public class GameOverFlash extends Actor {
	private Animation animation;
	private float stateTime;
	public Image bg;
	
	public GameOverFlash() {
		animation = Assets.instance.gameGUI.gameOverFlash;
		bg = new Image(Assets.instance.gameGUI.game_flashBg);
	}

	@Override
	public void act(float delta) {
		stateTime += Gdx.graphics.getDeltaTime();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		TextureRegion region = null;
		region = animation.getKeyFrame(stateTime / 2.5f, true);
		batch.draw(region.getTexture(), getX(), getY(), getOriginX(),
				getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation(), region.getRegionX(),
				region.getRegionY(), region.getRegionWidth(),
				region.getRegionHeight(), false, false);
	}
	
}
