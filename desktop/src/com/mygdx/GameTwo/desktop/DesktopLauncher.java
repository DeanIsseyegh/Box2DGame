package com.mygdx.GameTwo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.GameTwo.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = MainGame.TITLE;
		cfg.width = MainGame.V_WIDTH * MainGame.SCALE;
		cfg.height = MainGame.V_HEIGHT * MainGame.SCALE;
		new LwjglApplication(new MainGame(), cfg);
	}
}
