package com.mygdx.GameTwo.Managers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.GameTwo.MainGame;

public class WorldController {
	
	private MainGame mainGame;
	private CollisionManager collisionManager;
	private TiledMap tiledMap;
	private InputManager inputManager;

	public WorldController(MainGame mainGame){
		this.mainGame = mainGame;
		collisionManager = new CollisionManager();
		inputManager = new InputManager();
	}
	
	
	public MainGame getMainGame() { return mainGame; }
	public CollisionManager getCollisionManager() { return collisionManager; }
	
	public TiledMap getTiledMap() { return tiledMap; }
	public void setTiledMap(TiledMap tiledMap) { this.tiledMap = tiledMap; }
	
	public InputManager getInputManager() { return inputManager; }

}
