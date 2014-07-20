package com.mygdx.GameTwo.Entities.Enemy1;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.GameTwo.Entities.AbstractEntity;
import com.mygdx.GameTwo.Entities.IEntity;
import com.mygdx.GameTwo.Entities.Bullets.IBullet;
import com.mygdx.GameTwo.Entities.Player.Player.PlayerState;
import com.mygdx.GameTwo.Managers.WorldController;

public class Enemy1 extends AbstractEntity implements IEntity {
	
	private Enemy1State state;
	
	private float offsetX;
	private float offsetY;
	private float collisionWidth;
	private float collisionHeight;
	
	private Enemy1Animations enemy1Animations;
	
	private Animation idleRightAnimation;
	private Animation idleLeftAnimation;
	private Animation walkingRightAnimation;
	private Animation walkingLeftAnimation;
	private Animation shootingRightAnimation;
	private Animation shootingLeftAnimation;
	private Animation dyingRightAnimation;
	private Animation dyingLeftAnimation;
	
	private boolean isEnemyFacingRight;
	private float animationTime;
	
	private float shootingAnimationTime;
	private float timeSinceLastShot;
	private boolean hasShot;
	private Array<IBullet> enemy1Bullets;
	
	private float walkingSpeed;
	private float enemy1BulletSpeed;
	
	private boolean isTouchingFloor;
	
	private float tileHeight;
	private TiledMapTileLayer mapLayer;
	
	public Enemy1(Vector2 startPos, WorldController wc) {
		super(wc);
		
		enemy1Animations = new Enemy1Animations();
		pos = startPos;
		vel = new Vector2();
		initAnimations();
		isEnemyFacingRight = false;
		collisionWidth = 63;
		collisionHeight = 23;
		offsetX = 20;
		offsetY = 3;
		width = enemy1Animations.getRegionWidth() - collisionWidth;
		height = enemy1Animations.getRegionHeight() - collisionHeight;
		
		mapLayer = (TiledMapTileLayer) wc.getTiledMap().getLayers().get("Platforms");
		tileHeight = mapLayer.getTileHeight();
		
		state = Enemy1State.WALKING;
		enemy1BulletSpeed = 5f;
		enemy1Bullets = new Array<IBullet>();
		walkingSpeed = 200f;
	}
	
	private void initAnimations() {
		idleRightAnimation = enemy1Animations.getIdleRightAnimation();
		idleLeftAnimation = enemy1Animations.getIdleLeftAnimation();
		walkingRightAnimation =  enemy1Animations.getWalkingRightAnimation();
		walkingLeftAnimation =  enemy1Animations.getWalkingLeftAnimation();
		shootingRightAnimation =  enemy1Animations.getShootingRightAnimation();
		shootingLeftAnimation =  enemy1Animations.getShootingLeftAnimation();
		dyingRightAnimation =  enemy1Animations.getDyingRightAnimation();
		dyingLeftAnimation =  enemy1Animations.getDyingLeftAnimation();
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
		case WALKING:
			break;
		default:
			break;
		}
		
		renderDebug();
		drawEnemy(batch);
		pos.x += vel.x * deltaTime;
		boundsBox.set(pos.x + offsetX, pos.y + offsetY, width, height);
		handlePlatformCollisionX(mapLayer, oldPosX, oldPosY);
		pos.y += vel.y * deltaTime;
		boundsBox.set(pos.x + offsetX, pos.y + offsetY, width, height);
		handlePlatformCollisionY(mapLayer, oldPosX, oldPosY);
	}
	
	private void drawEnemy(SpriteBatch batch) {
		isEnemyFacingRight = vel.x > 0 ? true : false;
		
		if (isEnemyFacingRight) {
			batch.draw(walkingRightAnimation.getKeyFrame(animationTime, true), pos.x, pos.y);
		} else if (!isEnemyFacingRight) {
			batch.draw(walkingLeftAnimation.getKeyFrame(animationTime, true), pos.x - offsetX, pos.y);
		}
	}
	
	@Override
	public void dispose() {
		enemy1Animations.dispose();
	}
	
	public enum Enemy1State{
		WALKING;
	}
}
 