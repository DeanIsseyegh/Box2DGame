package com.mygdx.GameTwo.Entities.Player;

import static com.mygdx.GameTwo.Managers.GlobalVars.PLAYER_IDLE_FILEPATH;
import static com.mygdx.GameTwo.Managers.GlobalVars.PLAYER_WALKING_FILEPATH;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.GameTwo.Entities.B2DSprite;
import com.mygdx.GameTwo.Entities.IEntity;
import com.mygdx.GameTwo.Managers.WorldController;

public class Player extends B2DSprite implements IEntity {

	boolean debug;
	
	private PlayerState state;
	
	private Texture idleTexture;
	private Texture walkingTexture;
	
	private TextureRegion[] idleFrames;
	private TextureRegion[] walkingFrames;
	
	private Animation idleAnimation;
	private Animation walkingAnimation;
	
	private float animationTime;
	
	private ShapeRenderer shapeRenderer;
	
	public Player(Vector2 startPos, WorldController wc) {
		super(wc);
		
		pos = startPos;
		vel = new Vector2();
		
		idleTexture = B2DSprite.makeTexture(PLAYER_IDLE_FILEPATH);
		walkingTexture = B2DSprite.makeTexture(PLAYER_WALKING_FILEPATH);
		
		idleFrames = B2DSprite.splitSpriteSheetIntoFrames(6, 1, idleTexture);
		walkingFrames = B2DSprite.splitSpriteSheetIntoFrames(8, 2, walkingTexture);
		
		idleAnimation = new Animation(0.2f, idleFrames);
		walkingAnimation = new Animation(0.2f, walkingFrames);
		
		width = idleFrames[0].getRegionWidth();
		height = idleFrames[0].getRegionHeight();
		
		boundsBox = new Rectangle(pos.x, pos.y, width, height);
		
		//emulate user jumping
		vel.y = 150;
		state = PlayerState.JUMPING;
		
		debug = true;
		if (debug == true)
			shapeRenderer = new ShapeRenderer();
	} 
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		animationTime += deltaTime;
		
		float oldPosX = pos.x;
		float oldPosY = pos.y;
		boolean isWalking = false;
		
		switch(state){
		case DEFAULT:
			if (isWalking)
				batch.draw(walkingAnimation.getKeyFrame(animationTime, true), pos.x, pos.y);
			else
				batch.draw(idleAnimation.getKeyFrame(animationTime, true), pos.x, pos.y);
			break;
			
		case JUMPING:
			batch.draw(idleAnimation.getKeyFrame(animationTime, true), pos.x, pos.y);
			vel.y -= gravity * deltaTime; //Make jumping more realistic/smoother emulate gravity
			if (vel.y <= 0.15f)
				state = PlayerState.FALLING;
			break;
			
		case FALLING:
			batch.draw(idleAnimation.getKeyFrame(animationTime, true), pos.x, pos.y);
			vel.y -= gravity * deltaTime; //Make jumping more realistic/smoother emulate gravity
		}
		
		handlePlatformCollision((TiledMapTileLayer) wc.getTiledMap().getLayers().get("Platforms"), oldPosX, oldPosY);
		
		System.out.println(vel.y * deltaTime);
		pos.y += vel.y * deltaTime;
		pos.x += vel.x * deltaTime;
		boundsBox.set(pos.x, pos.y, width, height);
		
		// Debug
		if (debug == true){
			shapeRenderer.begin(ShapeType.Filled);
	        shapeRenderer.setColor(Color.RED);
	        shapeRenderer.rect(pos.x, pos.y, width, height);
	        shapeRenderer.end();
		}
		
		//System.out.println(pos.y);
	}
	
	private void handlePlatformCollision(TiledMapTileLayer collisionLayer, float oldPosX, float oldPosY){
		if (vel.y < 0){ // Going Down
			if (wc.getCollisionManager().collidesPlatformBottom(this, collisionLayer)){
				vel.y = 0;
			//	System.out.println(pos.y);
				//pos.y = oldPosY;
				state = PlayerState.DEFAULT;
			}
		}
		
		if (vel.y > 0){ // Going Up
			if (wc.getCollisionManager().collidesPlatformTop(this, collisionLayer)){
				vel.y = 0;
				pos.y = oldPosY;
				state = PlayerState.DEFAULT;
			}
		}
		
		if (vel.x < 0){ // Going Left
			if (wc.getCollisionManager().collidesPlatformLeft(this, collisionLayer)){
				vel.x = 0;
				pos.x = oldPosX;
				state = PlayerState.DEFAULT;
			}
		}
		
		if (vel.x > 0){ // Going Right
			if (wc.getCollisionManager().collidesPlatformRight(this, collisionLayer)){
				vel.x = 0;
				pos.x = oldPosX;
				state = PlayerState.DEFAULT;
			}
		}
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public enum PlayerState {
		DEFAULT,
		IDLE,
		WALKING,
		JUMPING,
		FALLING;
	}
}
