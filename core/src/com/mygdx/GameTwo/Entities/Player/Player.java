package com.mygdx.GameTwo.Entities.Player;

import static com.mygdx.GameTwo.Managers.GlobalVars.PLAYER_IDLE_FILEPATH;
import static com.mygdx.GameTwo.Managers.GlobalVars.PLAYER_SHOOTING_FILEPATH;
import static com.mygdx.GameTwo.Managers.GlobalVars.PLAYER_WALKING_FILEPATH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.GameTwo.GameLevel;
import com.mygdx.GameTwo.Entities.AbstractEntity;
import com.mygdx.GameTwo.Entities.IEntity;
import com.mygdx.GameTwo.Entities.Bullets.GeneralBullet;
import com.mygdx.GameTwo.Entities.Bullets.IBullet;
import com.mygdx.GameTwo.Managers.GlobalVars;
import com.mygdx.GameTwo.Managers.WorldController;

public class Player extends AbstractEntity implements IEntity {
	
	private PlayerState state;
	
	private float offsetX;
	private float offsetY;
	private float collisionWidth;
	private float collisionHeight;
	
	private Texture idleTexture;
	private Texture walkingTexture;
	private Texture shootingTexture;
	
	private TextureRegion[] idleRightFrames;
	private TextureRegion[] idleLeftFrames;
	private TextureRegion[] walkingRightFrames;
	private TextureRegion[] walkingLeftFrames;
	private TextureRegion[] shootingRightFrames;
	private TextureRegion[] shootingLeftFrames;
	
	private Animation idleRightAnimation;
	private Animation idleLeftAnimation;
	private Animation walkingRightAnimation;
	private Animation walkingLeftAnimation;
	private Animation shootingRightAnimation;
	private Animation shootingLeftAnimation;
	
	private boolean isPlayerFacingRight;
	private float animationTime;
	private float shootingAnimationTime;
	private float timeSinceLastShot;
	private float maxNumOfShotsPerSec;
	private Array<IBullet> playerBullets;
	
	private float walkingSpeed;
	private float jumpSpeed;
	private float blueBulletSpeed;
	
	private boolean isTouchingFloor;
	
	private float tileHeight;
	private TiledMapTileLayer mapLayer;
	
	public Player(Vector2 startPos, WorldController wc) {
		super(wc);
		
		pos = startPos;
		vel = new Vector2();
		initAnimations();
		
		collisionWidth = 53;
		collisionHeight = 23;
		offsetX = 20;
		width = idleRightFrames[0].getRegionWidth() - collisionWidth;
		height = idleRightFrames[0].getRegionHeight() - collisionHeight;
	
		mapLayer = (TiledMapTileLayer) wc.getTiledMap().getLayers().get("Platforms");
		tileHeight = mapLayer.getTileHeight();
		
		state = PlayerState.DEFAULT;
		maxNumOfShotsPerSec = 1.5f;
		blueBulletSpeed = 5f;
		playerBullets = new Array<IBullet>();
		walkingSpeed = 150f;
		jumpSpeed = 200f;
	} 
	
	private void initAnimations(){
		animationSpeed = 0.15f;
		
		idleTexture = AbstractEntity.makeTexture(PLAYER_IDLE_FILEPATH);
		walkingTexture = AbstractEntity.makeTexture(PLAYER_WALKING_FILEPATH);
		shootingTexture = AbstractEntity.makeTexture(PLAYER_SHOOTING_FILEPATH);
		
		idleRightFrames = AbstractEntity.splitSpriteSheetIntoFrames(6, 1, idleTexture);
		idleLeftFrames = AbstractEntity.splitAndFlip(6, 1, idleTexture);
		walkingRightFrames = AbstractEntity.splitSpriteSheetIntoFrames(5, 3, walkingTexture);
		walkingLeftFrames = AbstractEntity.splitAndFlip(5, 3, walkingTexture);
		shootingRightFrames = AbstractEntity.splitSpriteSheetIntoFrames(6, 1, shootingTexture);
		shootingLeftFrames = AbstractEntity.splitAndFlip(6, 1, shootingTexture);
		
		idleRightAnimation = new Animation(animationSpeed, idleRightFrames);
		idleLeftAnimation = new Animation(animationSpeed, idleLeftFrames);
		walkingRightAnimation = new Animation(animationSpeed, walkingRightFrames);
		walkingLeftAnimation = new Animation(animationSpeed, walkingLeftFrames);
		shootingRightAnimation = new Animation(animationSpeed, shootingRightFrames);
		shootingLeftAnimation = new Animation(animationSpeed, shootingLeftFrames);
		isPlayerFacingRight = true;
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		animationTime += deltaTime;
		timeSinceLastShot += deltaTime;
		
		float oldPosX = pos.x;
		float oldPosY = pos.y;
		
		applyGravity(deltaTime);
		
		switch(state){
		case WALKING_SHOOTING:
			shootingAnimationTime += deltaTime;
			if (shootingAnimationTime > 0.3f){
				state = PlayerState.DEFAULT;
				shootingAnimationTime = 0;
				timeSinceLastShot = 0;
				shootBlueBullet();
			}
		case DEFAULT:	
			break;
		case JUMPING_SHOOTING:
			shootingAnimationTime += deltaTime;
			if (shootingAnimationTime > 0.3f){
				state = PlayerState.JUMPING;
				shootingAnimationTime = 0;
				timeSinceLastShot = 0;
				shootBlueBullet();
			}
		case JUMPING:
			if (vel.y <= 0)
				state = PlayerState.DEFAULT;
			break;
		default:
			try {
				throw new Exception("Player in invalid state");
			} catch (Exception e) {
				e.printStackTrace();
				Gdx.app.exit();
			}
			break;
		}
		
		drawPlayer(batch);
		renderDebug();
		handleControls();
		
		pos.x += vel.x * deltaTime;
		boundsBox.set(pos.x + offsetX, pos.y + offsetY, width, height);
		handlePlatformCollisionX(mapLayer, oldPosX, oldPosY);
		pos.y += vel.y * deltaTime;
		boundsBox.set(pos.x + offsetX, pos.y + offsetY, width, height);
		handlePlatformCollisionY(mapLayer, oldPosX, oldPosY);
		wc.getCameraHelper().followPlayer(pos.x - GameLevel.WIDTH/2 + width , pos.y - tileHeight);
		
		renderBullets(deltaTime, batch);
	}
	
	private void shootBlueBullet(){
		Vector2 bulletStartingPos = new Vector2();
		if (isPlayerFacingRight){
			bulletStartingPos = new Vector2(pos.x + width * 0.8f, pos.y + height/3);
		} else {
			bulletStartingPos = new Vector2(pos.x - width * 0.25f, pos.y + height/3);
		}
		IBullet bullet = new GeneralBullet(wc, bulletStartingPos, GlobalVars.PLAYER_BULLET_FILEPATH, isPlayerFacingRight, blueBulletSpeed);
		playerBullets.add(bullet);
		wc.getCollisionManager().hookPlayerBullet(bullet);
	}
	
	private void renderBullets(float deltaTime, SpriteBatch batch){
		for (IBullet bullet : playerBullets){
			bullet.update(deltaTime, batch);
		}
	}
	
	private void applyGravity(float deltaTime){
		vel.y -= gravity * deltaTime;
	}
	
	private void drawPlayer(SpriteBatch batch){
		boolean isWalking = vel.x == 0 ? false : true;

		if (shouldShootRightAnimation()) {
			batch.draw(shootingRightAnimation.getKeyFrame(animationTime, false), pos.x, pos.y);
			isPlayerFacingRight = true;
		} else if (shouldShootLeftAnimation()) {
			batch.draw(shootingLeftAnimation.getKeyFrame(animationTime, false), pos.x - offsetX, pos.y);
			isPlayerFacingRight = false;
		} else if (isWalking && isMovingRight()) {
			batch.draw(walkingRightAnimation.getKeyFrame(animationTime, true), pos.x, pos.y);
			isPlayerFacingRight = true;
		} else if (isWalking && isMovingLeft()) {
			batch.draw(walkingLeftAnimation.getKeyFrame(animationTime, true),  pos.x - offsetX, pos.y);
			isPlayerFacingRight = false;
		} else {
			if (isPlayerFacingRight == true){
				batch.draw(idleRightAnimation.getKeyFrame(animationTime, true), pos.x, pos.y);
				isPlayerFacingRight = true;
			} else {
				batch.draw(idleLeftAnimation.getKeyFrame(animationTime, true), pos.x - offsetX, pos.y);
				isPlayerFacingRight = false;;
			}
		}
		
	}
	
	private boolean isMovingRight(){
		return vel.x > 0;
	}
	
	private boolean isMovingLeft(){
		return vel.x < 0;
	}
	
	private boolean isNotMoving(){
		return vel.x == 0;
	}
	
	private boolean isPlayerStateShooting(){
		return (state == PlayerState.JUMPING_SHOOTING) || state == PlayerState.WALKING_SHOOTING;
	}
	
	private boolean shouldShootRightAnimation(){
		if ((isPlayerStateShooting() && isMovingRight())
			|| (isPlayerStateShooting() && (isNotMoving() && isPlayerFacingRight == true))){
			return true;
		} else {
			return false;
		}
	}
	
	private boolean shouldShootLeftAnimation(){
		if ((isPlayerStateShooting() && isMovingLeft())
		|| (isPlayerStateShooting() && (isNotMoving() && isPlayerFacingRight == false))){
			return true;
		} else {
			return false;
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
		
		// Handle Shooting
		if (timeSinceLastShot > maxNumOfShotsPerSec){
			if (wc.getInputManager().shouldGoShoot() && state == PlayerState.JUMPING){
				state = PlayerState.JUMPING_SHOOTING;
			} else if (wc.getInputManager().shouldGoShoot()){
				state = PlayerState.WALKING_SHOOTING;
			}
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
	}
	
	private void collidedOnY(float oldPosY){
		vel.y = 0;
		pos.y = oldPosY;
	}
	
	@Override
	public void dispose() {
		idleTexture.dispose();
		walkingTexture.dispose();
		shootingTexture.dispose();
	}
	
	public enum PlayerState {
		DEFAULT,
		IDLE,
		JUMPING,
		JUMPING_SHOOTING,
		WALKING_SHOOTING;
	}

}
