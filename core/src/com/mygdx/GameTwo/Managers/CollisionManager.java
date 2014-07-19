package com.mygdx.GameTwo.Managers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.Array;
import com.mygdx.GameTwo.Entities.IEntity;
import com.mygdx.GameTwo.Entities.Bullets.IBullet;
import com.mygdx.GameTwo.Entities.TiledMapItems.Coin;
import com.mygdx.GameTwo.Entities.TiledMapItems.ITiledMapItem;

public class CollisionManager {

	private Array<IBullet> playerBullets;
	private Array<ITiledMapItem> coins;
	
	public CollisionManager(){
		playerBullets = new Array<IBullet>();
		coins = new Array<>();
	}
	
	public void handle(IEntity player) {
		// Handle player bullet hitting enemy
		
		handleCoinCollision(player, coins);

		removeUneededObjects();
	}
	
	private void handleCoinCollision(IEntity player, Array<ITiledMapItem> coins) {
		for(ITiledMapItem coin : coins){
			if (player.getBounds().overlaps(coin.getBoundsBox())){
				coin.setActive(false);
			}
		}
	}

	private void removeUneededObjects() {
		// Psuedo code
		// If bullet position is more than width of map then remove from array and dispose it.
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
	
	public void hookCoins(Array<ITiledMapItem> coins) { 
		this.coins = coins; 
	}

}
