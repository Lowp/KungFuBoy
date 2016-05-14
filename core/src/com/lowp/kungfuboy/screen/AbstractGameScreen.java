package com.lowp.kungfuboy.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public abstract class AbstractGameScreen implements Screen {
	public static final String TAG = AbstractGameScreen.class.getName();
	
	public AbstractGameScreen(DirectedGame game) {
		
	}

	public abstract void show();

	public abstract void render(float deltaTime);

	public abstract void resize(int width, int height);

	public abstract void hide();

	public abstract InputProcessor getInputProcessor();
	
	
	public void resume(){
		
	}
	
	
	public void dispose(){
		
	}
}
