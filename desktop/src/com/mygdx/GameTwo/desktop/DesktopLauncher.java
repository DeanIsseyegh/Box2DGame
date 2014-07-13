package com.mygdx.GameTwo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.GameTwo.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		final int SCALE = 2;
		cfg.title = "The Defender";
		cfg.width = 480 * SCALE;
		cfg.height = 320 * SCALE;
		new LwjglApplication(new MainGame(), cfg);
	}
}
