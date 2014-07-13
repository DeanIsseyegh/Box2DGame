package com.mygdx.GameTwo.Entities.Bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.GameTwo.Entities.AbstractEntity;
import com.mygdx.GameTwo.Managers.GlobalVars;
import com.mygdx.GameTwo.Managers.WorldController;

public class GeneralBullet extends AbstractEntity implements IBullet {
	
	private Texture bulletTexture;
	private TextureRegion[] bulletFrames;
	private Animation bulletAnimation;
	private int cols;
	private int rows;
	private boolean isFacingRight;
	
	/**
	 * Creates a generic bullet where you can specify the speed, position it should be moving
	 * and facing, its texture etc.
	 * 
	 * @param wc
	 * @param startPos
	 * @param bulletTextureFile
	 * @param cols
	 * @param rows
	 * @param animationSpeed
	 * @param isFacingRight
	 * @param speed
	 */
	public GeneralBullet(WorldController wc, Vector2 startPos, String bulletTextureFile, boolean isFacingRight, float speed) {
		super(wc);
		pos = startPos;
		vel = new Vector2(speed,0);
		this.isFacingRight = isFacingRight;
		handleSpriteSheetSpecifics(bulletTextureFile);
		initAnimation(bulletTextureFile);
	}
	
	private void handleSpriteSheetSpecifics(String texture){
		switch (texture){
		case (GlobalVars.PLAYER_BULLET_FILEPATH):
			cols = 4;
			rows = 1;
			animationSpeed = 0.15f;
			break;
		default:
			try {
				throw new Exception("Wrong/Unkown bullet texture detected");
			} catch (Exception e) {
				e.printStackTrace();
				Gdx.app.exit();
			}
		}
	}
	
	private void initAnimation(String bulletTextureFile){
		animationSpeed = 0.15f;
		
		bulletTexture = AbstractEntity.makeTexture(bulletTextureFile);
		if (isFacingRight){
			bulletFrames = AbstractEntity.splitSpriteSheetIntoFrames(cols, rows, bulletTexture);
		} else {
			bulletFrames = AbstractEntity.splitAndFlip(cols, rows, bulletTexture);
		}
		bulletAnimation = new Animation(animationSpeed, bulletFrames);
	}

	@Override
	public void update(float deltaTime, SpriteBatch batch) {
		time += deltaTime;
		
		batch.draw(bulletAnimation.getKeyFrame(time, false), pos.x, pos.y);
		
		if (isFacingRight){
			pos.add(vel);
		} else {
			pos.add(-vel.x, vel.y);
		}
	}

	@Override
	public void dispose() {
		bulletTexture.dispose();
	}
	

}
