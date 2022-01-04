package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

	

public class boid_enviorment extends ApplicationAdapter {
	
	public static final int CANVAS_WIDTH   = 1920/2;
	public static final int CANVAS_HEIGHT  = 660/2;
	private static final Color CLEAR_COLOR = new Color(75f/255f, 165f/255f, 157f/255f, 0.5f);

	Swarm boidSwarm;

	@Override
	public void create () {
		boidSwarm = new Swarm(150, 3, 0, 0 , 225 ,true);
		//boidSwarm = new Swarm(true);
	}

	@Override
	public void render () {
		ScreenUtils.clear(CLEAR_COLOR);
		

		if(Gdx.input.isKeyJustPressed(Keys.W)){
			boidSwarm.addBoid(Gdx.input.getX(),CANVAS_HEIGHT - Gdx.input.getY(), 90, Color.WHITE);
        }
		if(Gdx.input.isKeyJustPressed(Keys.S)){
			boidSwarm.addBoid(Gdx.input.getX(),CANVAS_HEIGHT - Gdx.input.getY(), -90, Color.BLUE);
        }
		if(Gdx.input.isKeyJustPressed(Keys.D)){
			boidSwarm.addBoid(Gdx.input.getX(),CANVAS_HEIGHT - Gdx.input.getY(), 0, Color.YELLOW);
        }
		if(Gdx.input.isKeyJustPressed(Keys.A)){
			boidSwarm.addBoid(Gdx.input.getX(),CANVAS_HEIGHT - Gdx.input.getY(), 180, Color.GREEN);
        }

		boidSwarm.update();
		boidSwarm.draw();
	}
	
	@Override
	public void dispose () {
		
	}
}
