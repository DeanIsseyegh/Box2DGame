package com.mygdx.GameTwo.Managers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.Array;
import com.mygdx.GameTwo.Entities.IEntity;
import com.mygdx.GameTwo.Entities.Bullets.IBullet;

public class CollisionManager {

	private Array<IBullet> playerBullets;
	
	public CollisionManager(){
		playerBullets = new Array<IBullet>();
	}
	
	public void handle() {
		// Handle player bullet hitting enemy
		
		removeUneededObjects();
	}

	private void removeUneededObjects(){
		// Psuedo code
		// If bullet position is more than width of map then remove from array and dipose it.
	}
	
	public void hookPlayerBullet(IBullet bullet){
		playerBullets.add(bullet);
	}
	
	// Player platform collisions
	public boolean collidesPlatformRight(IEntity entity, TiledMapTileLayer mapLayer) {
		for (float step = 0; step < entity.getBounds().getHeight(); step += mapLayer.getTileHeight() / 2)
			if (isCellBlocked(entity.getBounds().getX() + entity.getBounds().getWidth(), entity.getBounds().getY() + step, mapLayer))
				return true;
		return false;
	}

	public boolean collidesPlatformLeft(IEntity entity, TiledMapTileLayer mapLayer) {
		for (float step = 0; step < entity.getBounds().getHeight(); step += mapLayer.getTileHeight() / 2)
			if (isCellBlocked(entity.getBounds().getX(), entity.getBounds().getY() + step, mapLayer))
				return true;
		return false;
	}

	public boolean collidesPlatformTop(IEntity entity, TiledMapTileLayer mapLayer) {
		for (float step = 0; step < entity.getBounds().getWidth(); step += mapLayer.getTileWidth() / 2)
			if (isCellBlocked(entity.getBounds().getX() + step, entity.getBounds().getY() + entity.getHeight(), mapLayer))
				return true;
		return false;
	}

	public boolean collidesPlatformBottom(IEntity entity, TiledMapTileLayer mapLayer) {
		for (float step = 0; step < entity.getBounds().getWidth(); step += mapLayer.getTileWidth() / 2){
			if (isCellBlocked(entity.getBounds().getX() + step, entity.getBounds().getY(), mapLayer))
				return true;
		}
		return false;
	}

	private boolean isCellBlocked(float xPos, float yPos, TiledMapTileLayer mapLayer) {
		boolean isCollision = false;

		// Check if current position is in collidable tile
		Cell cell = mapLayer.getCell((int) (xPos / mapLayer.getTileWidth()), (int) (yPos / mapLayer.getTileHeight()));
		
		if ( cell != null && cell.getTile() != null )
			isCollision = true;
		
		return isCollision;
	}

}
