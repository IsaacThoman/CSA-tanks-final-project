package com.thoman.tanks;

import com.badlogic.gdx.graphics.g3d.Model;

import java.util.ArrayList;

public class Projectile extends GameObject{

    private double direction;
    private double displayDirection;
    private static float scale = 0.07f;
    private static float startingY = 1f;
    private double speed;

    private boolean active = true;

    float addedX;
    float addedY;
    float addedZ;

    public Projectile(Model model, float x, float z, double direction, double speed){
        super(model,x, 2f, z , scale);
        this.direction = direction;
        this.speed = speed;
        update();
    }

    private int frameOn;


    public void update(){
        frameOn++;

        double freq = 0.1;
        double amp = 0.2;

        addedY = (float)(Math.sin(frameOn*freq)*amp);
        addedZ = (float)(Math.cos(frameOn*freq)*amp);
        addedX = (float)(Math.sin(frameOn*freq)*amp);


        this.z-=Math.cos(direction)*speed;
        this.x-=Math.sin(direction)*speed;

        super.instance.transform.setToRotationRad(0,1f,0,(float)(displayDirection));
        super.instance.transform.scale(scale,scale,scale);
        super.instance.transform.setTranslation(super.x+addedX, startingY+addedY, super.z+addedZ);

        updateDisplayDirection();

        if(magicBanananDistFromNearestObject()<0.4 &&active ){
            GameObject tank = nearestObject(Tank.class);
            if(tank!=null)
                tank.takeAHit();
            killYourself();
            active = false;
        }
        if(Math.sqrt(Math.pow(this.x,2) + Math.pow(this.z,2))>1000)
            killYourself();

    }

    public double magicBanananDistFromNearestObject(){
        GameObject closest = nearestObject(Tank.class);
        if(closest==null) return 100;
        double dist = Math.sqrt(Math.pow(super.x + addedX - closest.x, 2) + Math.pow(super.y + addedY - closest.y, 2) + Math.pow(super.z + addedZ - closest.z, 2));
        return dist;
    }

    public String toString(){
        String out = "x:"+x + "y:"+y+"z"+z;
        return out;
    }

    private void updateDisplayDirection(){
            displayDirection = direction;
    }

}
