package com.lowp.kungfuboy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lowp.kungfuboy.Main;
import com.lowp.kungfuboy.WorldController;
import com.lowp.kungfuboy.WorldRenderer;
import com.lowp.kungfuboy.level.AbstractGameLevel;
import com.lowp.kungfuboy.util.Assets;
import com.lowp.kungfuboy.util.AudioManager;

public class GameScreen extends AbstractGameScreen {
	public static final String TAG = GameScreen.class.getName();
	public Main main;
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	private AbstractGameLevel level;
	private SpriteBatch batch;
	
	// ≈–∂œ”Œœ∑ ±∫Ú‘›Õ£
	public boolean paused;
	
	public GameScreen(Main main) {
		this(main,null);
	}
	
	public GameScreen(Main main,AbstractGameLevel level) {
		super(main);
		this.main = main;
		this.level = level;
	}

	

	@Override
	public void show() {
		AudioManager.instance.play(Assets.instance.assetMusic.gameScreen,true);
		
		batch = new SpriteBatch();
		worldController = new WorldController(level,main);
		worldRenderer = new WorldRenderer(worldController,main);
		paused = false;


	}

	@Override
	public void render(float deltaTime) {
		if (!paused) {
			worldController.update(deltaTime);
		}

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		worldRenderer.render(batch);
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void resume() {
//		super.resume();
		paused = false;
	}
	
	@Override
	public void hide() {
		batch.dispose();
	}

	@Override
	public InputProcessor getInputProcessor() {
		return null;
	}

}
