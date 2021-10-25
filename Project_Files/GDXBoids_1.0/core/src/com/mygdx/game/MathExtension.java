package com.mygdx.game;


public abstract class MathExtension {

    public static double findAngleToPoint(Point origin, Point goal, int currentHeading){
        double deltaX = (goal.x-origin.x);
        double deltaY = (goal.y-origin.y);
        return Math.toDegrees(Math.atan2(deltaY, deltaX)-Math.toRadians(currentHeading));
    }
    
    
    public static double applyTurnSpeedToRotation(double degrees, double turnSpeed){
        //Find Rotation Driection Sign
        int sign = (int)(degrees/Math.abs(degrees));
        //Check for Degree Sign Flip
        if(Math.abs(degrees) > 180){
            sign = -sign;
        }
        //Moderate Current turning speed
        degrees = turnSpeed  * (sign);
        
        //Return Final change in rotation in degrees
        return degrees;
    }

    public static double inverseAngle(double angle){
        if(angle == 0){
            return 180;
        }else{
            return angle < 0 ? angle + 180 : angle - 180;
        }
    }
}
