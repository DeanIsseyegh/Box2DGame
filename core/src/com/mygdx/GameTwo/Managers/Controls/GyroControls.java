package com.mygdx.GameTwo.Managers.Controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.GameTwo.Managers.InputManager;

public class GyroControls implements Runnable, Disposable {

	private boolean shouldRun;
	private InputManager inputManager;
	
	public GyroControls(InputManager inputManager){
		shouldRun = true;
		this.inputManager = inputManager;
	}
	
	@Override
	public void run() {
		while (shouldRun){
			float accelY = Gdx.input.getAccelerometerY();
			System.out.println(accelY);
			if (accelY > 1.5){
				inputManager.setGoRight(true);
				inputManager.setGoLeft(false);
			} else if (accelY < -1.5){
				inputManager.setGoLeft(true);
				inputManager.setGoRight(false);
			} else { 
				inputManager.setGoLeft(false);
				inputManager.setGoRight(false);
			}
		}
	}

	@Override
	public void dispose() {
		shouldRun = false;
	}

}
