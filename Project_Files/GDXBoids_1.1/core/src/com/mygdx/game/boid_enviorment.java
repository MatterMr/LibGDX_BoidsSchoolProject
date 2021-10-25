package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

	

public class boid_enviorment extends ApplicationAdapter {
	
	public static final int canvasWidth = 1000;
	public static final int canvasHeight = 700;

	Swarm boidSwarm;

	@Override
	public void create () {
		boidSwarm = new Swarm(true);
		boidSwarm.addBoid(100, 100, 0, Color.RED);
	}

	@Override
	public void render () {
		ScreenUtils.clear(75f/255f, 165f/255f, 157f/255f, 0.5f);
		

		if(Gdx.input.isKeyPressed(Keys.W)){
			boidSwarm.addBoid(Gdx.input.getX(),boid_enviorment.canvasHeight-Gdx.input.getY(), 90, Color.WHITE);
        }
		if(Gdx.input.isKeyPressed(Keys.S)){
			boidSwarm.addBoid(Gdx.input.getX(),boid_enviorment.canvasHeight-Gdx.input.getY(), -90, Color.BLUE);
        }
		if(Gdx.input.isKeyPressed(Keys.D)){
			boidSwarm.addBoid(Gdx.input.getX(),boid_enviorment.canvasHeight-Gdx.input.getY(), 0, Color.YELLOW);
        }
		if(Gdx.input.isKeyPressed(Keys.A)){
			boidSwarm.addBoid(Gdx.input.getX(),boid_enviorment.canvasHeight-Gdx.input.getY(), 180, Color.GREEN);
        }

		boidSwarm.update();
		boidSwarm.draw();
	}
	
	@Override
	public void dispose () {
		
	}
}
