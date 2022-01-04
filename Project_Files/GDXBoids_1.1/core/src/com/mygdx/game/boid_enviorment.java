package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

	

public class boid_enviorment extends ApplicationAdapter {
	
	public static final int CANVAS_WIDTH   = 800;
	public static final int CANVAS_HEIGHT  = 600;
	private static final Color CLEAR_COLOR = new Color(75f/255f, 165f/255f, 157f/255f, 0.5f);

	Swarm boidSwarm;

	@Override
	public void create () {
		boidSwarm = new Swarm(250, 1, 3, 6 , 50 ,true);
		boidSwarm.addBoid(CANVAS_WIDTH/2, CANVAS_HEIGHT/2, 90, Color.RED);
	}

	@Override
	public void render () {
		ScreenUtils.clear(CLEAR_COLOR);
		

		if(Gdx.input.isKeyPressed(Keys.W)){
			boidSwarm.addBoid(Gdx.input.getX(),CANVAS_HEIGHT - Gdx.input.getY(), 90, Color.WHITE);
        }
		if(Gdx.input.isKeyPressed(Keys.S)){
			boidSwarm.addBoid(Gdx.input.getX(),CANVAS_HEIGHT - Gdx.input.getY(), -90, Color.BLUE);
        }
		if(Gdx.input.isKeyPressed(Keys.D)){
			boidSwarm.addBoid(Gdx.input.getX(),CANVAS_HEIGHT - Gdx.input.getY(), 0, Color.YELLOW);
        }
		if(Gdx.input.isKeyPressed(Keys.A)){
			boidSwarm.addBoid(Gdx.input.getX(),CANVAS_HEIGHT - Gdx.input.getY(), 180, Color.GREEN);
        }

		boidSwarm.update();
		boidSwarm.draw();
	}
	
	@Override
	public void dispose () {
		
	}
}
