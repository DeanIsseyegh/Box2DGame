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
	
	private float offsetX;
	private float offsetY;
	private float collisionWidth;
	private float collisionHeight;
	
	private Texture idleTexture;
	private Texture walkingTexture;
	
	private TextureRegion[] idleRightFrames;
	private TextureRegion[] idleLeftFrames;
	private TextureRegion[] walkingRightFrames;
	private TextureRegion[] walkingLeftFrames;
	
	private Animation idleRightAnimation;
	private Animation idleLeftAnimation;
	private Animation walkingRightAnimation;
	private Animation walkingLeftAnimation;
	
	private Animation previousAnimation;
	private float animationTime;
	
	private float walkingSpeed;
	private float jumpSpeed;
	
	boolean isTouchingFloor;
	
	private ShapeRenderer shapeRenderer;
	
	public Player(Vector2 startPos, WorldController wc) {
		super(wc);
		
		pos = startPos;
		vel = new Vector2();
		walkingSpeed = 100f;
		jumpSpeed = 150f;
		
		idleTexture = B2DSprite.makeTexture(PLAYER_IDLE_FILEPATH);
		walkingTexture = B2DSprite.makeTexture(PLAYER_WALKING_FILEPATH);
		
		idleRightFrames = B2DSprite.splitSpriteSheetIntoFrames(6, 1, idleTexture);
		idleLeftFrames = B2DSprite.splitAndFlip(6, 1, idleTexture);
		walkingRightFrames = B2DSprite.splitSpriteSheetIntoFrames(5, 3, walkingTexture);
		walkingLeftFrames = B2DSprite.splitAndFlip(5, 3, walkingTexture);
		ArrayUtils.reverse(walkingLeftFrames);
		
		idleRightAnimation = new Animation(0.2f, idleRightFrames);
		idleLeftAnimation = new Animation(0.2f, idleLeftFrames);
		walkingRightAnimation = new Animation(0.2f, walkingRightFrames);
		walkingLeftAnimation = new Animation(0.2f, walkingLeftFrames);
		previousAnimation = walkingRightAnimation;
		
		collisionWidth = 40;
		collisionHeight = 23;
		offsetX = 20;
		width = idleRightFrames[0].getRegionWidth() - collisionWidth;
		height = idleRightFrames[0].getRegionHeight() - collisionHeight;
		pos.x += offsetX;
		
		boundsBox = new Rectangle(pos.x, pos.y, width, height);
		
		//emulate user jumping
		state = PlayerState.DEFAULT;
		
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
		applyGravity(deltaTime);
		switch(state){
		case DEFAULT:	
			break;
		case JUMPING:
			if (vel.y <= 0)
				state = PlayerState.DEFAULT;
			break;
		}
		
		drawPlayer(batch);
		renderDebug();
		handleControls();
		pos.x += vel.x * deltaTime;
		handlePlatformCollisionX((TiledMapTileLayer) wc.getTiledMap().getLayers().get("Platforms"), oldPosX, oldPosY);
		pos.y += vel.y * deltaTime;
		handlePlatformCollisionY((TiledMapTileLayer) wc.getTiledMap().getLayers().get("Platforms"), oldPosX, oldPosY);
		boundsBox.set(pos.x, pos.y, width, height);
		
	}
	
	private void applyGravity(float deltaTime){
		vel.y -= gravity * deltaTime;
	}
	
	private void drawPlayer(SpriteBatch batch){
		boolean isWalking = vel.x == 0 ? false : true;
		if (isWalking && wc.getInputManager().shouldGoRight()) {
			batch.draw(walkingRightAnimation.getKeyFrame(animationTime, true), pos.x, pos.y);
			previousAnimation = walkingRightAnimation;
		} else if (isWalking && wc.getInputManager().shouldGoLeft()) {
			batch.draw(walkingLeftAnimation.getKeyFrame(animationTime, true),  pos.x - offsetX, pos.y);
			previousAnimation = walkingLeftAnimation;
		} else {
			if (previousAnimation == walkingRightAnimation)
				batch.draw(idleRightAnimation.getKeyFrame(animationTime, true), pos.x, pos.y);
			else
				batch.draw(idleLeftAnimation.getKeyFrame(animationTime, true), pos.x - offsetX, pos.y);
		}
	}
	
	private void handleControls(){
		// Handle Y movement
		if (wc.getInputManager().shouldGoJump() && state != PlayerState.JUMPING && isTouchingFloor){
			vel.y = jumpSpeed;
			state = PlayerState.JUMPING;
		}
		
		// Handle X movement
		if (wc.getInputManager().shouldGoLeft()){
			vel.x = -walkingSpeed;
		} else if (wc.getInputManager().shouldGoRight()){
			vel.x = walkingSpeed;
		} else if (!wc.getInputManager().shouldGoLeft()
				|| !wc.getInputManager().shouldGoRight()){
			vel.x = 0;
		} 
	}
	
	private void handlePlatformCollisionY(TiledMapTileLayer collisionLayer, float oldPosX, float oldPosY){
		isTouchingFloor = false;
		
		if (vel.y < 0){ // Going Down
			if (wc.getCollisionManager().collidesPlatformBottom(this, collisionLayer)){
				collidedOnY(oldPosY);
				isTouchingFloor = true;
			} 
		} else if (vel.y > 0){ // Going Up
			if (wc.getCollisionManager().collidesPlatformTop(this, collisionLayer)){
				collidedOnY(oldPosY);
			}
		}
	}
	
	private void handlePlatformCollisionX(TiledMapTileLayer collisionLayer, float oldPosX, float oldPosY){
		if (vel.x < 0){ // Going Left
			if (wc.getCollisionManager().collidesPlatformLeft(this, collisionLayer)){
				collidedOnX(oldPosX);
			}
		} else if (vel.x > 0){ // Going Right
			if (wc.getCollisionManager().collidesPlatformRight(this, collisionLayer)){
				collidedOnX(oldPosX);
			}
		}
	}
	
	private void collidedOnX(float oldPosX){
		vel.x = 0;
		pos.x = oldPosX;
		state = PlayerState.DEFAULT;
	}
	
	private void collidedOnY(float oldPosY){
		vel.y = 0;
		pos.y = oldPosY;
		state = PlayerState.DEFAULT;
	}
	
	private void renderDebug(){
		// Debug
		if (debug == true){
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rect(pos.x, pos.y, width, height);
			shapeRenderer.end();
		}
	}
	@Override
	public void dispose() {
		idleTexture.dispose();
		walkingTexture.dispose();
	}
	
	public enum PlayerState {
		DEFAULT,
		IDLE,
		WALKING,
		JUMPING;
	}
}
