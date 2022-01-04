package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Boid{
    
    //Object Vars
    private Point center;
    private Point[] points;
    public int heading = 0;
    private Swarm boidSwarm;
    private int size = 12;
    private Color color = Color.RED;

    public Boid(int x, int y, int heading, Color color, Swarm swarm){
        this.size = Swarm.boidSize;
        this.points = new Point[]{new Point(x+size/2.0, y), new Point(x-size/2.0,y+size/3.0), new Point(x-size/2.0,y-size/3.0)};
        this.heading = heading;
        this.color = color;
        this.boidSwarm = swarm;
        setCenter();
        initRotate(heading);
        swarm.boidCollection.add(this);
    }
    

    public void update(double speed, boolean applyWallConstraints, int sightRange, double seperationMinRange, double seperationMaxRange, double cohesion, double alignment, double seperation){
        double finalRotation = 0;
            
        ArrayList<Boid> boidsInRange = findBoidsInRange(sightRange, 0);

            //Check for boids, if not do not apply rules
            Point thrustVector = getThrustVector(speed);
            if(!boidsInRange.isEmpty()){
                finalRotation += MathExtension.applyTurnSpeedToRotation(seperation(seperationMaxRange, seperationMinRange), seperation);
                finalRotation += MathExtension.applyTurnSpeedToRotation(MathExtension.findAngleToPoint(center, cohesion(boidsInRange), heading), cohesion);
                finalRotation +=  MathExtension.applyTurnSpeedToRotation(alignment(boidsInRange), alignment);
            }
            
            if(applyWallConstraints){
                finalRotation += constrain(20, 0);
                finalRotation += constrain(70, 10);
                constrainBoid();
            }else{
                constrainBoid();
            }

            applyRotation((int)finalRotation);
            applyVector(thrustVector);
    }
    public void draw(ShapeRenderer boidRenderer){
        
        boidRenderer.setColor(color);
        boidRenderer.triangle((float)points[0].x, (float)points[0].y, (float)points[1].x,
            (float)points[1].y, (float)points[2].x, (float)points[2].y);
        //Draw thrust Vectors
        // Point thrustVector = getThrustVector(boidSwarm.speed*4);
        // boidRenderer.setColor(Color.BLACK);
        // boidRenderer.line((float)center.x, (float)center.y, (float)center.x+(float)thrustVector.x, (float)center.y+(float)thrustVector.y);
    }



    //Object Methods------------------------------------------------------------------------------
    private void offsetPosition(int x, int y){
        for(Point p : points){
            p.add(new Point(x, y));
        }
        setCenter();
    }
    private void setCenter(){
        this.center = new Point(((points[0].x+points[1].x+points[2].x)/3.0), ((points[0].y+points[1].y+points[2].y))/3.0);
    }
    public void constrainBoid(){
        
        if(center.x < 0 ){
            offsetPosition(boid_enviorment.CANVAS_WIDTH, 0);
        }
        if(center.x > boid_enviorment.CANVAS_WIDTH){
            offsetPosition(-boid_enviorment.CANVAS_WIDTH, 0);
        }
        if(center.y < 0 ){
            offsetPosition(0, boid_enviorment.CANVAS_HEIGHT);
        }
        if(center.y > boid_enviorment.CANVAS_HEIGHT){
            offsetPosition(0, -boid_enviorment.CANVAS_HEIGHT);
        }
    }
    public double constrain(int reppelDistance, int turnSpeed){
        double reversedHeading = 0;
        int width = boid_enviorment.CANVAS_WIDTH;
        int height = boid_enviorment.CANVAS_HEIGHT;
        
        if(center.x > width-reppelDistance){

            reversedHeading = repellFromPoint(new Point(width,center.y), turnSpeed);
        }
        if(center.x < reppelDistance){
            
            reversedHeading = repellFromPoint(new Point(0,center.y), turnSpeed);
        }
        if(center.y < reppelDistance){
            reversedHeading = repellFromPoint(new Point(center.x,0), turnSpeed);
        }
        if(center.y > height-reppelDistance){
            reversedHeading = repellFromPoint(new Point(center.x,height), turnSpeed);
        }
        return reversedHeading;
    }

    private double repellFromPoint(Point p, int turnSpeed){
        double angleToPoint = MathExtension.findAngleToPoint(this.center, p, this.heading);
        return (turnSpeed == 0) 
        ? MathExtension.inverseAngle(angleToPoint) 
        : MathExtension.applyTurnSpeedToRotation(MathExtension.inverseAngle(angleToPoint), turnSpeed);
    }

    private void applyRotation(int degrees ){
        //Add degree change to heading
        heading += degrees;

        if(heading > 180){heading = -180 + (heading - 180);}
        if(heading < -180){heading = 180 - (heading + 180);}

        

        this.points = new Point[]{new Point(center.x+(size/2), center.y), new Point(center.x-size/2.0,center.y+size/3.0), new Point(center.x-size/2.0,center.y-size/3.0)};
        
        for(int i = 0; i<points.length;i++){
            points[i] = Point.rotatePoint(points[i], center, heading);
        }
    }

    /* Method initRotate()
        Initialize Rotation
    */
    private void initRotate(double degrees)
    {
        heading = (int)degrees;
        
        for(int i = 0; i<points.length;i++){
            points[i] = Point.rotatePoint(points[i], center, degrees);
        }
        setCenter();
    }

    /* Method getThrustVector()
        get the vector of thust based on current heading and speed value
    */
    private Point getThrustVector(double speed){
        Double thrustMultiplyer = Gdx.graphics.getDeltaTime()*(speed+(size*10));
        Point thrustVector = Point.getPointFromAngle(heading);
        thrustVector.mult(new Point(thrustMultiplyer));
        return thrustVector;
    }

    /* Method ApplyVector()
        Apply a forceVector to the Boids Position
    */
    private void applyVector(Point vector){
        for(int i = 0; i<points.length;i++){
            points[i].add(vector);
        }
        setCenter();
    }

    

    private ArrayList<Boid> findBoidsInRange(double maxRange, double minRange){
        ArrayList<Boid> closebyBoids = new ArrayList<Boid>();

        for(Boid b : boidSwarm.boidCollection){
            if(b != this){
                double distance = Point.distance(center, b.center);
                if(Math.abs(distance) < maxRange && Math.abs(distance) >= minRange){
                    closebyBoids.add(b);
                }
            }
        }
        return closebyBoids;
    }

    //Rules Of Boids--------------------------------------------------------------------------------
    private Point cohesion(ArrayList<Boid> boidsInRange){
        Point average = new Point(0, 0);

        for(Boid  b : boidsInRange)
        {
            average.add(b.center);
        }
        average.div(new Point(boidsInRange.size()));
        return average;
        
    }
    private double alignment(ArrayList<Boid> boidsInRange){
        double x = 0.0;
        double y = 0.0;
 
        for (Boid b : boidsInRange) {
            double angleR = Math.toRadians(b.heading);
            x += Math.cos(angleR);
            y += Math.sin(angleR);
        }
        double avgR = Math.atan2(y / boidsInRange.size(), x / boidsInRange.size());
        return Math.toDegrees(avgR) - heading;
    }

    private double seperation(double seperationMaxRange, double seperationMinRange ){
        ArrayList<Boid>  boidsInRange = findBoidsInRange(seperationMaxRange, seperationMinRange);
        return MathExtension.findAngleToPoint(center, Point.rotatePoint(cohesion(boidsInRange), center, 180), heading);
    }
    
}
