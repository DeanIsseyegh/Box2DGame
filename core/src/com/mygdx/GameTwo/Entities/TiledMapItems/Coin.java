package com.mygdx.GameTwo.Entities.TiledMapItems;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;


public class Coin extends AbstractTiledMapItem implements ITiledMapItem {
	
	private int coinSize;
	
	public Coin(int x, int y, Cell cell, float f, float g){
		super(x, y, cell, f, g);
		System.out.println(f);
		System.out.println(g);
		coinSize = 35;
		// Make offset so collision with coin feels tighter as in some of its frame its extremely thin
		int offsetX = 20;
		boundsBox.set((x * f) + offsetX, y * g, coinSize - offsetX, coinSize);
	}
	
}
