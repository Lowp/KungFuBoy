package com.lowp.kungfuboy.screen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public abstract class DirectedGame implements ApplicationListener {
	protected AbstractGameScreen screen;
	Game game;
	public void setScreen(AbstractGameScreen screen) {

		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();

		if (this.screen != null) {
			this.screen.hide();
		}
		
		this.screen = screen;
		
		if (this.screen != null) {
			this.screen.show();
			this.screen.resize(w, h);
		}
		
	}

	public AbstractGameScreen getScreen() {
		return this.screen;
	}

	@Override
	public void resize(int width, int height) {
		if (screen != null)
			screen.resize(width, height);

	}

	@Override
	public void dispose() {
		if (screen != null)
			screen.hide();
	}

	@Override
	public void pause() {
		if (screen != null)
			screen.pause();
	}

	@Override
	public void resume() {
		if (screen != null)
			screen.resume();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// 使时间增量的上限为1/60s，得到一个稳定的时间步
		float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 60.0f);
		if (screen != null)
			screen.render(deltaTime);
	}

}
