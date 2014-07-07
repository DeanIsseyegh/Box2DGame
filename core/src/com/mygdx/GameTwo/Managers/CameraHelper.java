package com.mygdx.GameTwo.Managers;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraHelper {

	private OrthographicCamera cam;
	private WorldController wc;
	
	public CameraHelper(OrthographicCamera cam, WorldController wc){
		this.cam = cam;
		this.wc = wc;
	}
	
	public void handlePlayerCamMovement(){
		
	}

	public void followPlayer(float oldPosX, float oldPosY, float x, float y) {
		cam.translate(x - oldPosX, y - oldPosY);
	}
	
}
