package com.mygdx.GameTwo.Managers.Controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.GameTwo.Managers.InputManager;

public class AndroidControls implements GestureListener, Disposable {

	private InputManager inputManager;
	private GyroControls gyroControls;
	private boolean isAccAvail;
	
	public AndroidControls(InputManager inputManager){
		this.inputManager = inputManager;
		isAccAvail = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
		if (isAccAvail){
			gyroControls = new GyroControls(inputManager);
			Thread thread = new Thread(gyroControls);
			thread.start();
		}
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if (velocityY < -1000f){
			inputManager.setGoJump(true);
		} 
		
		if (inputManager.shouldGoJump() == true){
			inputManager.setGoJump(false);
		}
		return true;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dispose() {
		if (gyroControls != null){
			gyroControls.dispose();
		}
	}

}
