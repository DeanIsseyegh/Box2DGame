package com.mygdx.GameTwo.Managers;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputManager {
	
	private boolean goRight = false;

	private boolean goLeft = false;
	
	public boolean shouldGoRight(){
		return goRight;
	}
	
	public boolean shouldGoLeft(){
		return goLeft;
	}
	
	public void setControls(){
		ApplicationType platform = Gdx.app.getType();
		switch (platform){
		case Desktop:
			Gdx.input.setInputProcessor(new DesktopControls(this));
			break;
		case Android:
			break;
		}
	}
	

	public boolean isGoRight() { return goRight; }
	public void setGoRight(boolean goRight) { this.goRight = goRight; }

	public boolean isGoLeft() { return goLeft; }
	public void setGoLeft(boolean goLeft) { this.goLeft = goLeft; }
	
	class DesktopControls implements InputProcessor {
		
		private InputManager inputManager;
		
		public DesktopControls(InputManager inputManager){
			this.inputManager = inputManager;
		}
		
		@Override
		public boolean keyDown(int keycode) {
			switch (keycode){
			case Keys.A:
			case Keys.LEFT:
				inputManager.setGoLeft(true);
				break;
			case Keys.D:
			case Keys.RIGHT:
				inputManager.setGoRight(true);
				break;
			}
			return true;
		}

		@Override
		public boolean keyUp(int keycode) {
			switch (keycode){
			case Keys.A:
			case Keys.LEFT:
				inputManager.setGoLeft(false);
				break;
			case Keys.D:
			case Keys.RIGHT:
				inputManager.setGoRight(false);
				break;
			}
			return true;
		}

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
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
}
