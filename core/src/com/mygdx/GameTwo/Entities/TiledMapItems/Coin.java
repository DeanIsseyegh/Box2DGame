package com.mygdx.GameTwo.Entities.TiledMapItems;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;


public class Coin extends AbstractTiledMapItem implements ITiledMapItem {
	
	private int coinSize;
	
	public Coin(int x, int y, Cell cell, float tileWidth, float tileHeight){
		super(x, y, cell, tileWidth, tileHeight);
		coinSize = 35;
		// Make offset so collision with coin feels tighter as in some of its frame its extremely thin
		int offsetX = 20;
		boundsBox.set((x * tileWidth) + offsetX, y * tileHeight, coinSize - offsetX, coinSize);
	}
	
}
