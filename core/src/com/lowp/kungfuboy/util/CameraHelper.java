package com.lowp.kungfuboy.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.lowp.kungfuboy.level.AbstractGameLevel;
import com.lowp.kungfuboy.object.AbstractGameObject;

/**
 * 相机帮助类
 * 
 * @author lowp
 *
 */
public class CameraHelper {
	// 位置
	private Vector2 position;
	// 跟随的目标对象
	private AbstractGameObject target;
	private AbstractGameLevel level;
	private OrthographicCamera camera;

	public CameraHelper() {
		this(null);
	}
	
	
	public CameraHelper(AbstractGameLevel level) {
		this.level = level;
		setTarget(level.hero);
		position = new Vector2();
	}

	public void setX(float x) {
		position.x = x;
	}

	public void setY(float y) {
		position.y = y;
	}

	public void setPosition(float x, float y) {
		position.set(x, x);
	}

	public Vector2 getPosition() {
		return position;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	// 当相机设置跟随对象后，更新相机状态
	public void update(float deltaTime) {
		if (!hasTarget())
			return;

		// 当主角到达屏幕中间，移动相机
		if (camera != null
				&& target.position.x > camera.viewportWidth / 2f - 1.0f) {
			position.x = target.position.x - camera.viewportWidth / 2f + 1.0f;
		}

		// 防止相机移动到游戏世界外
		if (camera != null) {
			position.clamp(0, level.map.size.x - camera.viewportWidth);
		}

	}

	public void setTarget(AbstractGameObject target) {
		this.target = target;
	}

	public boolean hasTarget() {
		return target != null;
	}

	public boolean hasTarget(AbstractGameObject target) {
		return hasTarget() && this.target.equals(target);

	}

	// 绑定相机
	public void applyTo(OrthographicCamera camera) {
		this.camera = camera;
		camera.position.x = camera.viewportWidth / 2f + position.x;
		camera.position.y = camera.viewportHeight / 2f + position.y;
		camera.update();
	}

}
