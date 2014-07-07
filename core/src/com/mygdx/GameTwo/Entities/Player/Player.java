package com.mygdx.GameTwo.Entities.Player;

import static com.mygdx.GameTwo.Managers.GlobalVars.PLAYER_IDLE_FILEPATH;
import static com.mygdx.GameTwo.Managers.GlobalVars.PLAYER_WALKING_FILEPATH;

import org.apache.commons.lang3.ArrayUtils;

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
	private TextureRegion[] walkingRightFrames;
	private TextureRegion[] walkingLeftFrames;
	
	private Animation idleAnimation;
	private Animation walkingRightAnimation;
	private Animation walkingLeftAnimation;
	
	private float animationTime;
	
	private float speed = 100f;
	
	private ShapeRenderer shapeRenderer;
	
	public Player(Vector2 startPos, WorldController wc) {
		super(wc);
		
		pos = startPos;
		vel = new Vector2();
		
		idleTexture = B2DSprite.makeTexture(PLAYER_IDLE_FILEPATH);
		walkingTexture = B2DSprite.makeTexture(PLAYER_WALKING_FILEPATH);
		
		idleFrames = B2DSprite.splitSpriteSheetIntoFrames(6, 1, idleTexture);
		walkingRightFrames = B2DSprite.splitSpriteSheetIntoFrames(5, 3, walkingTexture);
		walkingLeftFrames = B2DSprite.splitAndFlip(5, 3, walkingTexture);
		ArrayUtils.reverse(walkingLeftFrames);
		
		idleAnimation = new Animation(0.2f, idleFrames);
		walkingRightAnimation = new Animation(0.2f, walkingRightFrames);
		walkingLeftAnimation = new Animation(0.2f, walkingLeftFrames);
		
		width = idleFrames[0].getRegionWidth();
		height = idleFrames[0].getRegionHeight();
		
		boundsBox = new Rectangle(pos.x, pos.y, width, height);
		
		//emulate user jumping
		vel.y = 150;
		vel.x = 50;
		state = PlayerState.JUMPING;
		
		debug = false;
		if (debug == true)
			shapeRenderer = new ShapeRenderer();
	} 
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		animationTime += deltaTime;
		
		float oldPosX = pos.x;
		float oldPosY = pos.y;
		
		switch(state){
		case DEFAULT:	
			break;
		case JUMPING:
			vel.y -= gravity * deltaTime; //Make jumping more realistic/smoother emulate gravity
			if (vel.y <= 0.15f)
				state = PlayerState.FALLING;
			break;
		case FALLING:
			vel.y -= gravity * deltaTime; //Make jumping more realistic/smoother emulate gravity
		}
		
		boolean isWalking = vel.x == 0 ? false : true;
		if (isWalking && wc.getInputManager().shouldGoRight()) {
			batch.draw(walkingRightAnimation.getKeyFrame(animationTime, true), pos.x, pos.y);
		} else if (isWalking && wc.getInputManager().shouldGoLeft()) {
			batch.draw(walkingLeftAnimation.getKeyFrame(animationTime, true), pos.x, pos.y);
		} else {
			batch.draw(idleAnimation.getKeyFrame(animationTime, true), pos.x, pos.y);
		}
		
		handleControls();
		pos.x += vel.x * deltaTime;
		handlePlatformCollisionX((TiledMapTileLayer) wc.getTiledMap().getLayers().get("Platforms"), oldPosX, oldPosY);
		pos.y += vel.y * deltaTime;
		handlePlatformCollisionY((TiledMapTileLayer) wc.getTiledMap().getLayers().get("Platforms"), oldPosX, oldPosY);
		
		boundsBox.set(pos.x, pos.y, width, height);
		
		// Debug
		if (debug == true){
			shapeRenderer.begin(ShapeType.Filled);
	        shapeRenderer.setColor(Color.RED);
	        shapeRenderer.rect(pos.x, pos.y, width, height);
	        shapeRenderer.end();
		}
	}
	
	private void handleControls(){
		if (wc.getInputManager().shouldGoLeft()){
			vel.x = -speed;
		} else if (wc.getInputManager().shouldGoRight()){
			vel.x = speed;
		} else if (!wc.getInputManager().shouldGoLeft()
				|| !wc.getInputManager().shouldGoRight()){
			vel.x = 0;
		} 
	}
	
	private void handlePlatformCollisionY(TiledMapTileLayer collisionLayer, float oldPosX, float oldPosY){
		if (vel.y < 0){ // Going Down
			if (wc.getCollisionManager().collidesPlatformBottom(this, collisionLayer)){
				vel.y = 0;
				pos.y = oldPosY;
				state = PlayerState.DEFAULT;
			}
		} else if (vel.y > 0){ // Going Up
			if (wc.getCollisionManager().collidesPlatformTop(this, collisionLayer)){
				vel.y = 0;
				pos.y = oldPosY;
				state = PlayerState.DEFAULT;
			}
		}
	}
	
	private void handlePlatformCollisionX(TiledMapTileLayer collisionLayer, float oldPosX, float oldPosY){
		if (vel.x < 0){ // Going Left
			if (wc.getCollisionManager().collidesPlatformLeft(this, collisionLayer)){
				vel.x = 0;
				pos.x = oldPosX;
				state = PlayerState.DEFAULT;
			}
		} else if (vel.x > 0){ // Going Right
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
