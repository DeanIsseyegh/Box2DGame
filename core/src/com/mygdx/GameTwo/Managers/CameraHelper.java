package com.mygdx.GameTwo.Managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.GameTwo.MainGame;

public class CameraHelper {

	private OrthographicCamera cam;
	private WorldController wc;
	
	public CameraHelper(OrthographicCamera cam, WorldController wc){
		this.cam = cam;
		this.wc = wc;
	}
	
	public void handlePlayerCamMovement(){
		
	}

	public void followPlayer(float x, float y) {
		cam.position.set(x + MainGame.V_WIDTH / 2, y + MainGame.V_HEIGHT / 2, 0);
	}
	
}
