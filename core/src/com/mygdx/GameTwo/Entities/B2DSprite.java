package com.mygdx.GameTwo.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.GameTwo.Managers.WorldController;

public abstract class B2DSprite implements IEntity {
	
	protected float width;
	protected float height;
	
	protected float time;
	
	protected Vector2 pos;
	protected Vector2 vel;
	protected Rectangle boundsBox;
	
	protected WorldController wc;
	
	public B2DSprite(WorldController wc){
		this.wc = wc;
	}
	
	/**
	 * A helper method because I'm lazy to type out new Texture all the time
	 * 
	 * @param filePath
	 * @return Texture
	 */
	public static Texture makeTexture(String filePath){
		return new Texture(Gdx.files.internal(filePath));	
	}
	
	/**
	 * Will take in a spriteSheet image and return it as an TextureRegion Array which can be animated.
	 * 
	 * @param cols - Number of columns in spriteSheet
	 * @param rows = Number of rows in spriteSheet
	 * @param spriteSheet - The Texture object containing the spriteSheet
	 * @return aniFrames - an array of Animation Frames
	 */
	public static TextureRegion[] splitSpriteSheetIntoFrames(int cols, int rows, Texture spriteSheet){
		TextureRegion[][] tmpAniFrames = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / cols, spriteSheet.getHeight() / rows);
		TextureRegion[] aniFrames = new TextureRegion[cols * rows];
		
		int index = 0;
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				aniFrames[index++] = tmpAniFrames[i][j];
		
		
		return aniFrames;
	}
	
	@Override
	public float getX() { return pos.x; }
	
	@Override
	public float getY() { return pos.y; }
	
	@Override
	public float getWidth() { return width; }

	@Override
	public float getHeight() { return height; }

	@Override
	public Rectangle getBounds() { return boundsBox; }
}
