package com.mygdx.GameTwo.Entities.TiledMapItems;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;


public class Coin extends AbstractTiledMapItem implements ITiledMapItem {
	
	private int coinSize;
	
	public Coin(int x, int y, Cell cell){
		super(x, y, cell);
		coinSize = 35;
		boundsBox.set(x * 64, y * 64, coinSize, coinSize);
	}
	
}
