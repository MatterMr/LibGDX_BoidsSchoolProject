package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class Boid {
    //Type Vars
    private static ShapeRenderer boidRenderer = new ShapeRenderer();                           
    private static ArrayList<Boid> boidCollection = new ArrayList<Boid>(); 
    private static SpriteBatch textBatch = new SpriteBatch();
    private static BitmapFont font = new BitmapFont();
    private static int size = 12;          
    private static double speed = 150;                                 
    private static int maxBoidGovenor = 1000;

    private static boolean applyWallConstraints = true;
    

    private static int sightRange = 500;
    private static double cohesion = 1;
    private static double alignment = 3;
    private static double seperation = 6;
    private static double seperationMaxRange = size*1.3 ;
    private static double seperationMinRange = 0;

    //Object Vars
    private Point center;
    private Point[] points;
    private int heading = 0;
    
    private Color color = Color.RED;

    


    public Boid(int x, int y, int heading, Color color){
        this.points = new Point[]{new Point(x+size/2.0, y), new Point(x-size/2.0,y+size/3.0), new Point(x-size/2.0,y-size/3.0)};
        setCenter();
        this.heading = heading;
        this.color = color;
        initRotate(heading);

        boidCollection.add(this);
    }
   
    //Static Meathods--------------------------------------------------------------------------------
    public static void update(){
        //Remove Exess Boids
        if(boidCollection.size() >= maxBoidGovenor){
            boidCollection.remove(0);
        }
        
        
        //Boid Logic
        
        for(Boid b : boidCollection){
            double finalRotation = 0;
            
            ArrayList<Boid> boidsInRange = b.findBoidsInRange(sightRange, 0);

            //Check for boids, if not do not apply rules
            Point thrustVector = b.getThrustVector(speed);
            if(boidsInRange.size() != 0){
                finalRotation += MathExtension.applyTurnSpeedToRotation(MathExtension.findAngleToPoint(b.center, b.cohesion(boidsInRange), b.heading), cohesion);
                finalRotation += MathExtension.applyTurnSpeedToRotation(b.alignment(boidsInRange), alignment);
                finalRotation += MathExtension.applyTurnSpeedToRotation(b.seperation(seperationMaxRange, seperationMinRange), seperation);
                
            }
            
            if(applyWallConstraints){
                finalRotation += b.constrain(20, 0);
                finalRotation += b.constrain(70, 10);
                b.constrainBoid();
            }else{
                b.constrainBoid();
            }

            b.applyRotation((int)finalRotation);
            b.applyVector(thrustVector);
        }

        

    }
    public static void draw(){

        textBatch.begin();
        font.setColor(Color.BLACK);
        font.draw(textBatch, "" +boidCollection.get(0).heading, 10,boid_enviorment.canvasHeight-10 );
        textBatch.end();

        
        //Boid Drawing Loop
        boidRenderer.begin(ShapeType.Filled);
        
        
        for(Boid b : boidCollection){
            Point thrustVector = b.getThrustVector(b.speed);
            
            boidRenderer.setColor(b.color);
            boidRenderer.triangle((float)b.points[0].x, (float)b.points[0].y, (float)b.points[1].x,
             (float)b.points[1].y, (float)b.points[2].x, (float)b.points[2].y);
            //  boidRenderer.setColor(Color.BLACK);
            //  boidRenderer.line((float)b.center.x, (float)b.center.y, (float)b.center.x+(float)thrustVector.x, (float)b.center.y+(float)thrustVector.y);
        }
        boidRenderer.end();
    }
    public static void dispose(){
        boidRenderer.dispose();
        boidCollection.clear();
    }





    //Object Methods------------------------------------------------------------------------------
    private void offsetBoid(int x, int y){
        for(Point p : points){
            p.add(new Point(x, y));
        }
        setCenter();
    }
    private void setCenter(){
        this.center = new Point(((points[0].x+points[1].x+points[2].x)/3.0), ((points[0].y+points[1].y+points[2].y))/3.0);
    }
    private void constrainBoid(){
        
        if(center.x < 0 ){
            offsetBoid(boid_enviorment.canvasWidth, 0);
        }
        if(center.x > boid_enviorment.canvasWidth){
            offsetBoid(-boid_enviorment.canvasWidth, 0);
        }
        if(center.y < 0 ){
            offsetBoid(0, boid_enviorment.canvasHeight);
        }
        if(center.y > boid_enviorment.canvasHeight){
            offsetBoid(0, -boid_enviorment.canvasHeight);
        }
    }
    private double constrain(int reppelDistance, int turnSpeed){
        double reversedHeading = 0;
        int width = boid_enviorment.canvasWidth;
        int height = boid_enviorment.canvasHeight;
        
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
        double deltaDegree = (turnSpeed == 0) 
        ? MathExtension.inverseAngle(angleToPoint) 
        : MathExtension.applyTurnSpeedToRotation(MathExtension.inverseAngle(angleToPoint), turnSpeed);
        return deltaDegree;
    }

    /* Method ApplyRotation()
        Apply the final calculated change in direction to boid
    */
    private void applyRotation(int degrees ){
        //Add degree change to heading
        heading += degrees;

        if(heading > 180){heading = -180 + (heading - 180);}
        if(heading < -179){heading = 179 - (heading + 179);}

        

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
        Double thrustMultiplyer = (double)Gdx.graphics.getDeltaTime()*(speed+(size*10));
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

    

    private ArrayList<Boid> findBoidsInRange(double maxRange, double minRange)
    {
        ArrayList<Boid> closebyBoids = new ArrayList<Boid>();

        for(Boid b : boidCollection){
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
        double averageHeading = 0;
        for(Boid b : boidsInRange)
        {
            averageHeading += b.heading;
        }
        return (averageHeading / (boidsInRange.size())-heading);
    }

    private double seperation(double seperationMaxRange, double seperationMinRange ){
        ArrayList<Boid>  boidsInRange = findBoidsInRange(seperationMaxRange, seperationMinRange );

        double sumOfDegrees = 0;
        double averageHeadingInverse;

        for(Boid b : boidsInRange){
            sumOfDegrees += MathExtension.findAngleToPoint(this.center, b.center, this.heading);
        }
        double averageHeading = (sumOfDegrees/boidsInRange.size());

        if(averageHeading == 0){
            averageHeadingInverse = 180;
        }else{
            averageHeadingInverse = averageHeading < 0 ? averageHeading + 180 : averageHeading - 180;
        }
        return averageHeadingInverse;
    }
    
}
