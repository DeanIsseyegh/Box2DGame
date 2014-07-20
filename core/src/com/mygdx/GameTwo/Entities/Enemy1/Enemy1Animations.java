package com.mygdx.GameTwo.Entities.Enemy1;

import static com.mygdx.GameTwo.Managers.GlobalVars.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.GameTwo.Entities.AbstractEntity;

public class Enemy1Animations implements Disposable{

	private Texture idleTexture;
	private Texture walkingTexture;
	private Texture shootingTexture;
	private Texture dyingTexture;
	
	private TextureRegion[] idleRightFrames;
	private TextureRegion[] idleLeftFrames;
	private TextureRegion[] walkingRightFrames;
	private TextureRegion[] walkingLeftFrames;
	private TextureRegion[] shootingRightFrames;
	private TextureRegion[] shootingLeftFrames;
	private TextureRegion[] dyingLeftFrames;
	private TextureRegion[] dyingRightFrames;
	
	private Animation idleRightAnimation;
	private Animation idleLeftAnimation;
	private Animation walkingRightAnimation;
	private Animation walkingLeftAnimation;
	private Animation shootingRightAnimation;
	private Animation shootingLeftAnimation;
	private Animation dyingLeftAnimation;
	private Animation dyingRightAnimation;
	
	public Enemy1Animations() {
		float animationSpeed = 0.15f;
		
		idleTexture = AbstractEntity.makeTexture(ENEMY1_IDLE_FILEPATH);
		walkingTexture = AbstractEntity.makeTexture(ENEMY1_WALKING_FILEPATH);
		shootingTexture = AbstractEntity.makeTexture(ENEMY1_SHOOTING_FILEPATH);
		dyingTexture = AbstractEntity.makeTexture(ENEMY1_DYING_FILEPATH);
		walkingTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		idleRightFrames = AbstractEntity.splitSpriteSheetIntoFrames(6, 1, idleTexture);
		idleLeftFrames = AbstractEntity.splitAndFlip(6, 1, idleTexture);
		walkingRightFrames = AbstractEntity.splitSpriteSheetIntoFrames(9, 1, walkingTexture);
		walkingLeftFrames = AbstractEntity.splitAndFlip(9, 1, walkingTexture);
		shootingRightFrames = AbstractEntity.splitSpriteSheetIntoFrames(4, 1, shootingTexture);
		shootingLeftFrames = AbstractEntity.splitAndFlip(4, 1, shootingTexture);
		dyingRightFrames = AbstractEntity.splitSpriteSheetIntoFrames(5, 1, dyingTexture);
		dyingLeftFrames = AbstractEntity.splitAndFlip(5, 1, dyingTexture);
		
		idleRightAnimation = new Animation(animationSpeed, idleRightFrames);
		idleLeftAnimation = new Animation(animationSpeed, idleLeftFrames);
		walkingRightAnimation = new Animation(animationSpeed, walkingRightFrames);
		walkingLeftAnimation = new Animation(animationSpeed, walkingLeftFrames);
		shootingRightAnimation = new Animation(animationSpeed, shootingRightFrames);
		shootingLeftAnimation = new Animation(animationSpeed, shootingLeftFrames);
		dyingRightAnimation = new Animation(animationSpeed, dyingRightFrames);
		dyingLeftAnimation = new Animation(animationSpeed, dyingLeftFrames);
	}
	
	@Override
	public void dispose() {
		idleTexture.dispose();
		walkingTexture.dispose();
		shootingTexture.dispose();
		dyingTexture.dispose();
	}
	
	public float getRegionWidth(){
		return idleRightFrames[0].getRegionWidth();
	}
	
	public float getRegionHeight(){
		return idleRightFrames[0].getRegionHeight();
	}

	public Animation getIdleRightAnimation() {
		return idleRightAnimation;
	}

	public Animation getIdleLeftAnimation() {
		return idleLeftAnimation;
	}

	public Animation getWalkingRightAnimation() {
		return walkingRightAnimation;
	}

	public Animation getWalkingLeftAnimation() {
		return walkingLeftAnimation;
	}

	public Animation getShootingRightAnimation() {
		return shootingRightAnimation;
	}

	public Animation getShootingLeftAnimation() {
		return shootingLeftAnimation;
	}

	public Animation getDyingLeftAnimation() {
		return dyingLeftAnimation;
	}

	public Animation getDyingRightAnimation() {
		return dyingRightAnimation;
	}

}
