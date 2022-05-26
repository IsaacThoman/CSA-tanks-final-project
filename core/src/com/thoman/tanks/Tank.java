package com.thoman.tanks;

import com.badlogic.gdx.graphics.g3d.Model;

import java.util.ArrayList;

public class Tank extends GameObject{

    private double speed = 0.015;
    private double direction;
    private double displayDirection;
    private ArrayList<TurningPoint> turningPoints;
    public Tank(Model model, int x, int z, ArrayList<TurningPoint> turningPoints, double direction,double speed){
        super(model,x, 0.49f, z , 0.03f);
        //this.turningPoints = turningPoints;
        this.turningPoints = new ArrayList<TurningPoint>();
        for(TurningPoint i : turningPoints)
            this.turningPoints.add(i);
        this.direction = direction;
        this.speed = speed;

        update();
    }

    public void update(){
        if(turningPoints.size()>0) {
            direction = Math.atan2(turningPoints.get(0).getX() - this.x, turningPoints.get(0).getZ() - this.z) / Math.PI * 180;
            double distance = Math.sqrt(Math.pow(turningPoints.get(0).getX() - super.x, 2) + Math.pow(turningPoints.get(0).getZ() - super.z, 2));
            if (distance < turningPoints.get(0).getSize())
                turningPoints.remove(0);
            }else{
            killYourself();
        }
        super.x+=Math.sin(direction*3.14/180)*speed;
        super.z+=Math.cos(direction*3.14/180)*speed;
        super.instance.transform.setToRotationRad(0,1f,0,(float)(displayDirection*3.14/180));
        super.instance.transform.scale(0.03f,0.03f,0.03f);
        super.instance.transform.setTranslation(super.x, 0.49f, super.z);
//        for(TurningPoint p : turningPoints){
//
//            if(distance<p.getSize())
//                direction = p.getAngle();
//        }
    //System.out.println(super.x+", "+super.z);

        updateDisplayDirection();

//       // super.instance.transform.rotateRad(1,0,1,3.14f);
 //       Quaternion rotation = super.instance.transform.getRotation(new Quaternion());
  //      super.instance.transform.rotate(0,1f,0,(float)(direction-rotation.getYaw()));

    }
private void updateDisplayDirection(){
  //      if(Math.abs(displayDirection-direction)<3) return;
    displayDirection+= (direction-displayDirection)/15;

}

public void takeAHit(){
        killYourself();
}

}
