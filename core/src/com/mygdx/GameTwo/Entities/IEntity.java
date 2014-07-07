package com.mygdx.GameTwo.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public interface IEntity extends Disposable {
	
	float getX();
	float getY();
	
	float getWidth();
	float getHeight();
	
	Rectangle getBounds();
	
	public void update(float deltaTime, SpriteBatch batch);
}
