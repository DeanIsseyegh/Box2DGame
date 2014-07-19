package com.mygdx.GameTwo.Managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.GameTwo.GameLevel;

public class CameraHelper {

	private OrthographicCamera cam;
	private WorldController wc;
	
	public CameraHelper(OrthographicCamera cam, WorldController wc){
		this.cam = cam;
		this.wc = wc;
	}
	
	public OrthographicCamera getCam() {
		return cam;
	}
	public void followPlayer(float x, float y) {
		cam.position.set(x + GameLevel.WIDTH / 2, y + GameLevel.HEIGHT / 2, 0);
	}
	
}
