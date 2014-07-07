package com.mygdx.GameTwo.Managers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.GameTwo.Entities.IEntity;
import com.sun.xml.internal.stream.Entity;

public class CollisionManager {

	public void handle() {

	}

	public boolean collidesPlatformRight(IEntity entity, TiledMapTileLayer mapLayer) {
		for (float step = 0; step < entity.getHeight(); step += mapLayer.getTileHeight() / 2)
			if (isCellBlocked(entity.getX() + entity.getWidth(), entity.getY() + step, mapLayer))
				return true;
		return false;
	}

	public boolean collidesPlatformLeft(IEntity entity, TiledMapTileLayer mapLayer) {
		for (float step = 0; step < entity.getHeight(); step += mapLayer.getTileHeight() / 2)
			if (isCellBlocked(entity.getX(), entity.getY() + step, mapLayer))
				return true;
		return false;
	}

	public boolean collidesPlatformTop(IEntity entity, TiledMapTileLayer mapLayer) {
		for (float step = 0; step < entity.getWidth(); step += mapLayer.getTileWidth() / 2)
			if (isCellBlocked(entity.getX() + step, entity.getY() + entity.getHeight(), mapLayer))
				return true;
		return false;
	}

	public boolean collidesPlatformBottom(IEntity entity, TiledMapTileLayer mapLayer) {
		for (float step = 0; step < entity.getWidth(); step += mapLayer.getTileWidth() / 2)
			if (isCellBlocked(entity.getX() + step, entity.getY(), mapLayer))
				return true;
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
