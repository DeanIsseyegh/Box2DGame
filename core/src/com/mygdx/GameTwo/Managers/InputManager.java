package com.mygdx.GameTwo.Managers;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputManager {
	
	private boolean goRight = false;
	private boolean goLeft = false;
	private boolean goJump = false;
	
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
	

	public boolean shouldGoRight(){ return goRight; }
	public boolean shouldGoLeft(){ return goLeft; }
	public boolean shouldGoJump(){ return goJump; }
	
	public void setGoRight(boolean goRight) { this.goRight = goRight; }
	public void setGoLeft(boolean goLeft) { this.goLeft = goLeft; }
	public void setGoJump(boolean goJump) { this.goJump = goJump; }
	
	class DesktopControls implements InputProcessor {
		
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
			}
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
