package com.mygdx.GameTwo.Managers.Controls;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.GameTwo.Managers.InputManager;

public class DesktopControls implements InputProcessor {
	
	private InputManager inputManager;
	
	public DesktopControls(InputManager inputManager){
		this.inputManager = inputManager;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		handleKeys(keycode, true);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		handleKeys(keycode, false);
		return true;
	}
	
	private void handleKeys(int keycode, boolean shouldDo){
		switch (keycode){
		case Keys.A:
		case Keys.LEFT:
			inputManager.setGoLeft(shouldDo);
			break;
		case Keys.D:
		case Keys.RIGHT:
			inputManager.setGoRight(shouldDo);
			break;
		case Keys.SPACE:
		case Keys.W:
		case Keys.UP:
			inputManager.setGoJump(shouldDo);
			break;
		case Keys.CONTROL_LEFT:
			inputManager.setGoShoot(shouldDo);
			break;
		}
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		handleMouse(button, true);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		handleMouse(button, false);
		return false;
	}
	
	private void handleMouse(int button, boolean shouldDo){
		switch(button){
		case 0:
			inputManager.setGoShoot(shouldDo);
			break;
		case 1:
			inputManager.setGoSlash(shouldDo);
			break;
		}
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
