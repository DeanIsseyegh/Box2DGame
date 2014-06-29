package com.mygdx.GameTwo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.GameTwo.GameTwo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = GameTwo.TITLE;
		cfg.width = GameTwo.V_WIDTH * GameTwo.SCALE;
		cfg.height = GameTwo.V_HEIGHT * GameTwo.SCALE;
		new LwjglApplication(new GameTwo(), cfg);
	}
}
