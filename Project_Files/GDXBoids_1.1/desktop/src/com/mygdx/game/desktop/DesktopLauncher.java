package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.boid_enviorment;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Boids";
		config.width = boid_enviorment.canvasWidth;
		config.height = boid_enviorment.canvasHeight;
		config.foregroundFPS = 60;
		new LwjglApplication(new boid_enviorment(), config);
	}
}
