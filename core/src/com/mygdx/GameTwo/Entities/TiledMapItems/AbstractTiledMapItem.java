package com.mygdx.GameTwo.Entities.TiledMapItems;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;

public class AbstractTiledMapItem implements ITiledMapItem {

	protected int x;
	protected int y;
	protected Cell cell;
	protected Rectangle boundsBox;
	protected boolean active;
	
	public AbstractTiledMapItem(int x, int y, Cell cell){
		this.x = x;
		this.y = y;
		this.cell = cell;
		boundsBox = new Rectangle();
		this.active = true;
	}
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public Cell getCell() {
		return cell;
	}
	
	@Override
	public Rectangle getBoundsBox() {
		return boundsBox;
	}
	@Override
	public boolean isActive(boolean active) {
		return active;
	}
	@Override
	public void setActive(boolean active) {
		this.active = active;
		if (active == false) {
			cell.setTile(null);
		}
	}
	
	
}
