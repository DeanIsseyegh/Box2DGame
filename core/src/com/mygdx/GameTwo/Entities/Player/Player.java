package com.mygdx.GameTwo.Entities.Player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
	
	private PlayerAnimations playerAnimations;
	
	private Animation idleRightAnimation;
	private Animation idleLeftAnimation;
	private Animation walkingRightAnimation;
	private Animation walkingLeftAnimation;
	private Animation shootingRightAnimation;
	private Animation shootingLeftAnimation;
	private Animation slashingRightAnimation;
	private Animation slashingLeftAnimation;
	
	private boolean isPlayerFacingRight;
	private float animationTime;
	
	private float shootingAnimationTime;
	private float timeSinceLastShot;
	private float maxNumOfShotsPerSec;
	private boolean hasShot;
	private Array<IBullet> playerBullets;
	
	private float slashingAnimationTime;
	private float timeSinceLastSlash;
	private float maxNumOfSlashPerSec;
	private boolean hasSlashed;
	
	private float walkingSpeed;
	private float jumpSpeed;
	private float blueBulletSpeed;
	
	private float tileHeight;
	private TiledMapTileLayer mapLayer;
	
	public Player(Vector2 startPos, WorldController wc) {
		super(wc);
		
		playerAnimations = new PlayerAnimations();
		pos = startPos;
		vel = new Vector2();
		initAnimations();
		isPlayerFacingRight = true;
		collisionWidth = 53;
		collisionHeight = 23;
		offsetX = 20;
		offsetY = 3;
		width = playerAnimations.getRegionWidth() - collisionWidth;
		height = playerAnimations.getRegionHeight() - collisionHeight;
	
		mapLayer = (TiledMapTileLayer) wc.getTiledMap().getLayers().get("Platforms");
		tileHeight = mapLayer.getTileHeight();
		
		state = PlayerState.DEFAULT;
		maxNumOfShotsPerSec = 1.5f;
		maxNumOfSlashPerSec = 1.5f;
		blueBulletSpeed = 5f;
		playerBullets = new Array<IBullet>();
		walkingSpeed = 300f;
		jumpSpeed = 450f;
	} 
	
	private void initAnimations(){
		idleRightAnimation = playerAnimations.getIdleRightAnimation();
		idleLeftAnimation = playerAnimations.getIdleLeftAnimation();
		walkingRightAnimation =  playerAnimations.getWalkingRightAnimation();
		walkingLeftAnimation =  playerAnimations.getWalkingLeftAnimation();
		shootingRightAnimation =  playerAnimations.getShootingRightAnimation();
		shootingLeftAnimation =  playerAnimations.getShootingLeftAnimation();
		slashingRightAnimation =  playerAnimations.getSlashingRightAnimation();
		slashingLeftAnimation =  playerAnimations.getSlashingLeftAnimation();
	}
	
	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		animationTime += deltaTime;
		timeSinceLastShot += deltaTime;
		timeSinceLastSlash += deltaTime;
		
		float oldPosX = pos.x;
		float oldPosY = pos.y;
		
		applyGravity(deltaTime);
		
		switch(state){
		case WALKING_SHOOTING:
			handleShootingState(PlayerState.DEFAULT, deltaTime);
		case DEFAULT:	
			break;
		case JUMPING_SHOOTING:
			handleShootingState(PlayerState.JUMPING, deltaTime);
		case JUMPING:
			wc.getInputManager().setGoJump(false);
			if (vel.y <= 0)
				state = PlayerState.DEFAULT;
			break;
		case WALKING_SLASHING:
			handleSlashingState(PlayerState.DEFAULT, deltaTime);
			break;
		case JUMPING_SLASHING:
			handleSlashingState(PlayerState.JUMPING, deltaTime);
			break;
		default:
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
		wc.getCameraHelper().followPlayer(pos.x - GameLevel.WIDTH/2 + width , pos.y - tileHeight * 2);
		
		renderBullets(deltaTime, batch);
	}
	
	private void handleSlashingState(PlayerState postState, float deltaTime){
		slashingAnimationTime += deltaTime;
		if (!hasSlashed){
			hasSlashed = true;
		}
		if (slashingAnimationTime > 0.75f){
			state = postState;
			slashingAnimationTime = 0;
			timeSinceLastSlash = 0;
			hasSlashed = false;
		}
	}
	
	private void handleShootingState(PlayerState postState, float deltaTime){
		shootingAnimationTime += deltaTime;
		if (!hasShot){
			shootBlueBullet();
			hasShot = true;
		}
		if (shootingAnimationTime > 0.3f){
			state = postState;
			shootingAnimationTime = 0;
			timeSinceLastShot = 0;
			hasShot = false;
		}
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
	
	private void drawPlayer(SpriteBatch batch){
		boolean isWalking = vel.x == 0 ? false : true;
		
		if (shouldSlashRightAnimation()) {
			batch.draw(slashingRightAnimation.getKeyFrame(slashingAnimationTime + 0.3f, true), pos.x, pos.y);
			isPlayerFacingRight = true;
		} else if (shouldSlashLeftAnimation()){
			batch.draw(slashingLeftAnimation.getKeyFrame(slashingAnimationTime + 0.3f, true), pos.x - offsetX, pos.y);
			isPlayerFacingRight = false;
		} else if (shouldShootRightAnimation()) {
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
	
	private boolean isPlayerStateSlashing(){
		return (state == PlayerState.JUMPING_SLASHING) || state == PlayerState.WALKING_SLASHING;
	}
	
	private boolean shouldSlashRightAnimation(){
		if ((isPlayerStateSlashing() && isMovingRight())
			|| (isPlayerStateSlashing() && (isNotMoving() && isPlayerFacingRight == true))){
			return true;
		} else {
			return false;
		}
	}
	
	private boolean shouldSlashLeftAnimation(){
		if ((isPlayerStateSlashing() && isMovingLeft())
		|| (isPlayerStateSlashing() && (isNotMoving() && isPlayerFacingRight == false))){
			return true;
		} else {
			return false;
		}
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
		if (timeSinceLastShot > maxNumOfShotsPerSec && !isPlayerStateSlashing()){
			if (wc.getInputManager().shouldGoShoot() && state == PlayerState.JUMPING){
				state = PlayerState.JUMPING_SHOOTING;
			} else if (wc.getInputManager().shouldGoShoot()){
				state = PlayerState.WALKING_SHOOTING;
			}
		}
		
		// Handle Slashing
		if (timeSinceLastSlash > maxNumOfSlashPerSec && !isPlayerStateShooting()){
			if (wc.getInputManager().shouldGoSlash() && state == PlayerState.JUMPING){
				state = PlayerState.JUMPING_SLASHING;
			} else if (wc.getInputManager().shouldGoSlash())
				state = PlayerState.WALKING_SLASHING;
		}
	}
	
	@Override
	public void dispose() {
		playerAnimations.dispose();
	}
	
	public enum PlayerState {
		DEFAULT,
		IDLE,
		JUMPING,
		JUMPING_SHOOTING,
		WALKING_SHOOTING,
		JUMPING_SLASHING,
		WALKING_SLASHING;
	}

}
