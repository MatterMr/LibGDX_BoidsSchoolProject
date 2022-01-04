package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Swarm {
    private ShapeRenderer boidRenderer;

    private static final int MAX_BOID_GOVENOR = 1000;

    public static final int boidSize = 12;
    public double speed = 225;
    public ArrayList<Boid> boidCollection = new ArrayList<Boid>();
    
    
    private double seperationMaxRange = boidSize * 3;
    private double seperationMinRange = 0.5;

    private boolean applyWallConstraints = false;
    private int     sightRange           = 50;
    private double  cohesion             = 1;
    private double  alignment            = 3;
    private double  seperation           = 5;

    public Swarm(boolean applyWallConstraints){
        boidRenderer              = new ShapeRenderer();
        this.applyWallConstraints = applyWallConstraints;
    }

    public Swarm(double speed, double cohesion, double alignment, double seperation, int sightRange,  boolean applyWallConstraints){
        this(applyWallConstraints);
        this.speed      = speed;
        this.sightRange = sightRange;
        this.cohesion   = cohesion;
        this.alignment  = alignment;
        this.seperation = seperation;
    }

    public Swarm(double speed, double cohesion, double alignment, double seperation, int sightRange,
    boolean applyWallConstraints, double seperationMaxRange, double seperationMinRange){
        this(speed, cohesion, alignment, seperation, sightRange, applyWallConstraints);
        this.seperationMaxRange = seperationMaxRange;
        this.seperationMinRange = seperationMinRange;
    }

    public void update(){
        //Remove Exess Boids
        if(boidCollection.size() >= MAX_BOID_GOVENOR){
            boidCollection.remove(0);
        }
        //Boid Logic
        for(Boid b : boidCollection){
            b.update(speed, applyWallConstraints, sightRange, seperationMinRange, seperationMaxRange, cohesion, alignment, seperation);
        }

    }
    public void draw(){
        if(!boidCollection.isEmpty()){
            //Boid Drawing Loop
            boidRenderer.begin(ShapeType.Filled);
            
            for(Boid b : boidCollection){
                b.draw(boidRenderer);
            }
            boidRenderer.end();
        }
    }
    public void addBoid(int x, int y, int heading, Color color){
        new Boid(x, y, heading, color, this); 
    }
}
