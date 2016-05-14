package com.lowp.kungfuboy.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.lowp.kungfuboy.level.AbstractGameLevel;
import com.lowp.kungfuboy.object.AbstractGameObject;

/**
 * ���������
 * 
 * @author lowp
 *
 */
public class CameraHelper {
	// λ��
	private Vector2 position;
	// �����Ŀ�����
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

	// ��������ø������󣬸������״̬
	public void update(float deltaTime) {
		if (!hasTarget())
			return;

		// �����ǵ�����Ļ�м䣬�ƶ����
		if (camera != null
				&& target.position.x > camera.viewportWidth / 2f - 1.0f) {
			position.x = target.position.x - camera.viewportWidth / 2f + 1.0f;
		}

		// ��ֹ����ƶ�����Ϸ������
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

	// �����
	public void applyTo(OrthographicCamera camera) {
		this.camera = camera;
		camera.position.x = camera.viewportWidth / 2f + position.x;
		camera.position.y = camera.viewportHeight / 2f + position.y;
		camera.update();
	}

}
