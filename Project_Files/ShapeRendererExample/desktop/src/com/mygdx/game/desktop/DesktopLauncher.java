package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.template_class;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "ShapeRendererExample";
		config.width = template_class.canvasWidth;
		config.height = template_class.canvasHeight;
		config.foregroundFPS = 60;
		new LwjglApplication(new template_class(), config);
	}
}
