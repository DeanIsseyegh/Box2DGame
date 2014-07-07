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
	protected final float gravity = 150;
	
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
	
	public static TextureRegion[] splitSpriteSheetIntoFrames(int cols, int rows, Texture spriteSheet){
		return split(cols, rows, spriteSheet, false);
	} 
	
	public static TextureRegion[] splitAndFlip(int cols, int rows, Texture spriteSheet){
		return split(cols, rows, spriteSheet, true);
	} 
	
	public static TextureRegion[] split(int cols, int rows, Texture spriteSheet, boolean shouldFlip){
		TextureRegion[][] tmpAniFrames = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / cols, spriteSheet.getHeight() / rows);
		TextureRegion[] aniFrames = new TextureRegion[cols * rows];
		
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				aniFrames[index++] = tmpAniFrames[i][j];
				if (shouldFlip)
					aniFrames[index - 1].flip(true, false);
			}
		}
		
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
