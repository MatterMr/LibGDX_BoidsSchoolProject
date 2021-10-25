package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class template_class extends ApplicationAdapter {
	//Globals For Window Size and Drawing
	public static final int canvasWidth = 400;
	public static final int canvasHeight = 400;

	private ShapeRenderer renderer;

	//Shape Pulse Variables

	private float radius = 0;
	private final int maxRadius = 200;
	private final int minRadius = 0;
	private final int speed = 100;
	private boolean pulseOut = true;

	
	public void create () {
		renderer = new ShapeRenderer();
		renderer.setAutoShapeType(true);
	}

	
	public void render () {

		float speed = this.speed * Gdx.graphics.getDeltaTime();
		if(radius < maxRadius& pulseOut){
			radius += speed;
		}else if(radius > minRadius){
			radius -= speed;
		}
		if(radius <= minRadius || radius >= maxRadius){
			pulseOut = !pulseOut;
		}


		ScreenUtils.clear(0, 0, 0, 1);
		renderer.begin();

		renderer.circle(canvasWidth / 2, canvasHeight / 2, radius);
		renderer.rect(canvasWidth / 2 - radius,  canvasHeight/2 - radius, radius*2, radius*2);
		renderer.triangle(canvasWidth / 2 - radius, canvasHeight/2 -radius, canvasWidth / 2 + radius, canvasHeight/2 -radius, canvasWidth/2,  canvasHeight/2 +radius);

		renderer.end();
	}
	

	public void dispose () {
		renderer.dispose();
	}
}
