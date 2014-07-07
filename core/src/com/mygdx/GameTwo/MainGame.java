package com.mygdx.GameTwo;

import com.badlogic.gdx.Game;
import com.mygdx.GameTwo.Managers.InputManager;
import com.mygdx.GameTwo.Managers.WorldController;

public class MainGame extends Game {
	private static final String TAG = GameLevel.class.getName();
	
	public static final String TITLE = "The Defender";
	public static final int SCALE = 2;
	public static final int V_WIDTH = 320 * SCALE;
	public static final int V_HEIGHT = 240 * SCALE;
	
	private WorldController wc;
	
	@Override
	public void create() {
		WorldController wc=  new WorldController(this);
		GameLevel gameLevel = new GameLevel(wc);
		
		setScreen(gameLevel);
	}

	@Override
	public void resize(int width, int height) {
	
	}

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
