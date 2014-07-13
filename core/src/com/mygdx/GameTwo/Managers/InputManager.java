package com.mygdx.GameTwo.Managers;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.mygdx.GameTwo.Managers.Controls.AndroidControls;
import com.mygdx.GameTwo.Managers.Controls.DesktopControls;

public class InputManager {
	
	private boolean goRight = false;
	private boolean goLeft = false;
	private boolean goJump = false;
	private boolean goShoot = false;
	private boolean goSlash = false;
	
	public void setControls(){
		ApplicationType platform = Gdx.app.getType();
		switch (platform){
		case Desktop:
			Gdx.input.setInputProcessor(new DesktopControls(this));
			break;
		case Android:
			Gdx.input.setInputProcessor(new GestureDetector(new AndroidControls(this))); 
			break;
		default:
			try {
				throw new Exception("Unrecongized platform");
			} catch (Exception e) {
				e.printStackTrace();
				Gdx.app.exit();
			}
			break;
		}
	}

	public boolean shouldGoRight(){ return goRight; }
	public boolean shouldGoLeft(){ return goLeft; }
	public boolean shouldGoJump(){ return goJump; }
	public boolean shouldGoShoot(){ return goShoot; }
	public boolean shouldGoSlash(){ return goSlash; }
	
	public void setGoRight(boolean goRight) { this.goRight = goRight; }
	public void setGoLeft(boolean goLeft) { this.goLeft = goLeft; }
	public void setGoJump(boolean goJump) { this.goJump = goJump; }
	public void setGoShoot(boolean goShoot){ this.goShoot = goShoot; }
	public void setGoSlash(boolean goSlash) { this.goSlash = goSlash; }
	
}
