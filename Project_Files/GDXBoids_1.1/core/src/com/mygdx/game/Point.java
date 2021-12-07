package com.mygdx.game;

import java.lang.Math;

public class Point {
    protected double x;
    protected double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Point(){
        this(0, 0);
    }
    public Point(double num){
        this(num, num);
    }

    public void setPoint(double x, double y){
        this.x = x; 
        this.y = y;

    }
    public void add(Point p){
        x += p.x;
        y += p.y;
    }
    public void sub(Point p){
        x -= p.x;
        y -= p.y;
    }
    public void mult(Point p){
        x *= p.x;
        y *= p.y;
    }
    public void div(Point p){
        x /= p.x;
        y /= p.y;
    }

    public static Point rotatePoint(Point p, Point o, double degrees){
        double angle = Math.toRadians(degrees - 360);
        double x = Math.cos(angle) * (p.x - o.x) - Math.sin(angle) * (p.y - o.y) + o.x;
        double y = Math.sin(angle) * (p.x - o.x) + Math.cos(angle) * (p.y - o.y) + o.y;

        return new Point( (double) x, (double) y);
    }
    public static Point getPointFromAngle(double degree){
        double x =  (Math.cos(Math.toRadians((int)degree)));
        double y =  (Math.sin(Math.toRadians((int)degree)));
        return new Point(x, y);
    }
    
    public static double distance(Point one, Point two)
    {
        return Math.sqrt(Math.pow(two.x-one.x, 2)+Math.pow(two.y-one.y, 2));
    }

}
