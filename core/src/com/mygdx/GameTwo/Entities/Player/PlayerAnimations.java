package com.mygdx.GameTwo.Entities.Player;

import static com.mygdx.GameTwo.Managers.GlobalVars.PLAYER_IDLE_FILEPATH;
import static com.mygdx.GameTwo.Managers.GlobalVars.PLAYER_SHOOTING_FILEPATH;
import static com.mygdx.GameTwo.Managers.GlobalVars.PLAYER_SLASHING_FILEPATH;
import static com.mygdx.GameTwo.Managers.GlobalVars.PLAYER_WALKING_FILEPATH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.GameTwo.Entities.AbstractEntity;

public class PlayerAnimations implements Disposable {

	private Texture idleTexture;
	private Texture walkingTexture;
	private Texture shootingTexture;
	private Texture slashingTexture;
	
	private TextureRegion[] idleRightFrames;
	private TextureRegion[] idleLeftFrames;
	private TextureRegion[] walkingRightFrames;
	private TextureRegion[] walkingLeftFrames;
	private TextureRegion[] shootingRightFrames;
	private TextureRegion[] shootingLeftFrames;
	private TextureRegion[] slashingLeftFrames;
	private TextureRegion[] slashingRightFrames;
	
	private Animation idleRightAnimation;
	private Animation idleLeftAnimation;
	private Animation walkingRightAnimation;
	private Animation walkingLeftAnimation;
	private Animation shootingRightAnimation;
	private Animation shootingLeftAnimation;
	private Animation slashingLeftAnimation;
	private Animation slashingRightAnimation;
	
	public PlayerAnimations(){
		float animationSpeed = 0.15f;
		
		idleTexture = AbstractEntity.makeTexture(PLAYER_IDLE_FILEPATH);
		walkingTexture = AbstractEntity.makeTexture(PLAYER_WALKING_FILEPATH);
		shootingTexture = AbstractEntity.makeTexture(PLAYER_SHOOTING_FILEPATH);
		slashingTexture = AbstractEntity.makeTexture(PLAYER_SLASHING_FILEPATH);
		
		idleRightFrames = AbstractEntity.splitSpriteSheetIntoFrames(6, 1, idleTexture);
		idleLeftFrames = AbstractEntity.splitAndFlip(6, 1, idleTexture);
		walkingRightFrames = AbstractEntity.splitSpriteSheetIntoFrames(5, 3, walkingTexture);
		walkingLeftFrames = AbstractEntity.splitAndFlip(5, 3, walkingTexture);
		shootingRightFrames = AbstractEntity.splitSpriteSheetIntoFrames(6, 1, shootingTexture);
		shootingLeftFrames = AbstractEntity.splitAndFlip(6, 1, shootingTexture);
		slashingRightFrames = AbstractEntity.splitSpriteSheetIntoFrames(7, 1, slashingTexture);
		slashingLeftFrames = AbstractEntity.splitAndFlip(7, 1, slashingTexture);
		
		idleRightAnimation = new Animation(animationSpeed, idleRightFrames);
		idleLeftAnimation = new Animation(animationSpeed, idleLeftFrames);
		walkingRightAnimation = new Animation(animationSpeed, walkingRightFrames);
		walkingLeftAnimation = new Animation(animationSpeed, walkingLeftFrames);
		shootingRightAnimation = new Animation(animationSpeed, shootingRightFrames);
		shootingLeftAnimation = new Animation(animationSpeed, shootingLeftFrames);
		slashingRightAnimation = new Animation(animationSpeed, slashingRightFrames);
		slashingLeftAnimation = new Animation(animationSpeed, slashingLeftFrames);
	}
	
	@Override
	public void dispose() {
		idleTexture.dispose();
		walkingTexture.dispose();
		shootingTexture.dispose();
		slashingTexture.dispose();
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

	public Animation getSlashingLeftAnimation() {
		return slashingLeftAnimation;
	}

	public Animation getSlashingRightAnimation() {
		return slashingRightAnimation;
	}

}
