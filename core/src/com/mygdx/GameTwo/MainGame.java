package com.mygdx.GameTwo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.GameTwo.Managers.WorldController;

public class MainGame extends Game {
	private static final String TAG = GameLevel.class.getName();
	
	private WorldController wc;
	
	@Override
	public void create() {
		WorldController wc=  new WorldController(this);
		GameLevel gameLevel = new GameLevel(wc);
		
		setScreen(gameLevel);
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	
	}
	
}
