package com.thoman.tanks;

public class TurningPoint {
    private double x;
    private double z;
    private double desiredAngle;
    private double size;
    public TurningPoint(double x, double z, double desiredAngle, double size){
        this.x = x;
        this.z = z;
        this.desiredAngle = desiredAngle;
        this.size = size;
    }

    public double getX(){return x;}
    public double getZ(){return z;}
    public double getAngle(){return desiredAngle;}
    public double getSize(){return size;}
}
