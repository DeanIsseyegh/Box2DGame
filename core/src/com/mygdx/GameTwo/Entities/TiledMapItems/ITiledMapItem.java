package com.mygdx.GameTwo.Entities.TiledMapItems;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;

public interface ITiledMapItem {
	int getX();
	int getY();
	Cell getCell();
	Rectangle getBoundsBox();
	boolean isActive(boolean active);
	void setActive(boolean active);
}
