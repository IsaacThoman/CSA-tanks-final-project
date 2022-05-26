package com.thoman.tanks;

import com.badlogic.gdx.graphics.g3d.Model;

import java.util.ArrayList;

public class Tower extends GameObject{


    private double direction;
    private double displayDirection;
    private static float scale = 0.005f;
    private static float y = 0.55f;
    private int framesTillShot;
    private int shotDelay = 350;
    private double projectileSpeed = 3.5;
    private ArrayList<TurningPoint> turningPoints;
    public Tower(Model model, int x, int z, double direction){
        super(model,x, y, z , scale);
        System.out.println("I love salamanders!!");
        this.direction = direction;
        update();
    }



    public void update(){

        GameObject nearest = nearestObject(Tank.class);
        if(nearest!=null)
            direction = Math.atan2(this.x - nearest.x, this.z - nearest.z);




       // System.out.println(nearest==null);
        super.instance.transform.setToRotationRad(0,1f,0,(float)(displayDirection));
        super.instance.transform.scale(scale,scale,scale);
        super.instance.transform.setTranslation(super.x, y, super.z);

        updateDisplayDirection();

        framesTillShot--;
        if(framesTillShot<=0 && nearest!=null){
            framesTillShot = shotDelay;
            TanksGame.addProjectile(x,z,direction,projectileSpeed);
        }

    }
    private void updateDisplayDirection(){
        if(direction<0.0872 || direction > 6.195)
            displayDirection = direction;
            else
            displayDirection+= (direction-displayDirection)/10;

    }

}
