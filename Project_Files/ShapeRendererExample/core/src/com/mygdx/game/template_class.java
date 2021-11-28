package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;

public class template_class extends ApplicationAdapter {
	//Globals For Window Size and Drawing
	public static final int canvasWidth = 400;
	public static final int canvasHeight = 400;

	ShapeRenderer shapeRenderer; // Don't create the ShapeRenderer object here!!!

	@Override
	public void create () {
		//You should create the Shaprenderer here during runtime instead of compiletime. 
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);// Clears the screen each cycle

		shapeRenderer.begin(ShapeType.Line);// Begins the shape batch

		shapeRenderer.triangle(200, 400, 0, 0, 400, 0);// Adds triangle shape to batch

		shapeRenderer.end();// Ends the batch and draws all shapes to the screen
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
