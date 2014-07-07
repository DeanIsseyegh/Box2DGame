package com.mygdx.GameTwo.Managers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.GameTwo.MainGame;

public class WorldController {
	
	private MainGame mainGame;
	private CollisionManager collisionManager;
	private TiledMap tiledMap;

	public WorldController(MainGame mainGame){
		this.mainGame = mainGame;
		collisionManager = new CollisionManager();
	}
	
	
	public MainGame getMainGame() { return mainGame; }
	public CollisionManager getCollisionManager() { return collisionManager; }
	
	public TiledMap getTiledMap() { return tiledMap; }
	public void setTiledMap(TiledMap tiledMap) { this.tiledMap = tiledMap; }

}
