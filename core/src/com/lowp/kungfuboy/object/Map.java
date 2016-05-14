package com.lowp.kungfuboy.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * ”Œœ∑≥°æ∞
 * 
 * @author lowp
 *
 */
public class Map extends AbstractGameObject {
	public static final String TAG = Map.class.getName();
	public TextureRegion reg_left;
	public TextureRegion reg_right;

	public Map(AtlasRegion reg_left, AtlasRegion reg_right) {
		this.reg_left = reg_left;
		this.reg_right = reg_right;
		init();
	}

	public void init() {
		scale.set(0.011f, 0.0062f);

		if (reg_left != null && reg_right == null) {
			size.set(reg_left.getRegionWidth() * scale.x,
					reg_left.getRegionHeight() * scale.y);
		} else if (reg_left != null && reg_right != null) {
			size.set(reg_left.getRegionWidth() * 2 * scale.x,
					reg_left.getRegionHeight() * scale.y);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (reg_left != null) {
			batch.draw(reg_left.getTexture(), position.x, position.y - 0.1f,
					origin.x, origin.y, reg_left.getRegionWidth(),
					reg_left.getRegionHeight(), scale.x, scale.y, rotation,
					reg_left.getRegionX(), reg_left.getRegionY(),
					reg_left.getRegionWidth(), reg_left.getRegionHeight(),
					false, false);
		}

		if (reg_right != null) {
			batch.draw(reg_right.getTexture(), position.x + size.x / 2f,
					position.y - 0.1f, origin.x, origin.y,
					reg_right.getRegionWidth(), reg_right.getRegionHeight(),
					scale.x, scale.y, rotation, reg_right.getRegionX(),
					reg_right.getRegionY(), reg_right.getRegionWidth(),
					reg_right.getRegionHeight(), false, false);
		}

	}

}
