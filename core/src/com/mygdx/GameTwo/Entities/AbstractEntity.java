package com.mygdx.GameTwo.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.GameTwo.Managers.WorldController;

public abstract class AbstractEntity implements IEntity {
	
	protected float width;
	protected float height;
	
	protected float time;
	protected float animationSpeed;
	
	protected Vector2 pos;
	protected Vector2 vel;
	protected Rectangle boundsBox;
	
	protected WorldController wc;
	protected final float gravity = 400;
	
	// Debug stuff
	boolean debug;
	protected ShapeRenderer shapeRenderer;
	
	public AbstractEntity(WorldController wc){
		this.wc = wc;
		boundsBox = new Rectangle();
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
	
	/**
	 * This is a utility method that is used so we can reverse an Array using libgdx's
	 * implementation of an Array which is more efficient than the ArrayUtils method.
	 * @param oldTexRegionArr
	 * @return
	 */
	public static TextureRegion[] reverseArray(TextureRegion[] oldTexRegionArr){
		Array<TextureRegion> reversableTexRegion = new Array<TextureRegion>();
		reversableTexRegion.addAll(oldTexRegionArr);
		reversableTexRegion.reverse();
		
		TextureRegion[] newTexRegion = new TextureRegion[oldTexRegionArr.length];
		for (int i = 0; i < oldTexRegionArr.length; i++){
			newTexRegion[i] = reversableTexRegion.get(i);
		}
		
		return newTexRegion;
	}
	
	protected void renderDebug(){
		if (debug == true){
			if (shapeRenderer == null){
				shapeRenderer = new ShapeRenderer();
			}
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.setProjectionMatrix(wc.getCameraHelper().getCam().combined);
			shapeRenderer.rect(boundsBox.getX(), boundsBox.getY(), boundsBox.getWidth(), boundsBox.getHeight());
			shapeRenderer.end();
		}
	}
}
