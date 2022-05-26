package com.thoman.tanks;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class GameObject {
    protected ModelInstance instance;
    protected float x;
    protected float y;
    protected float z;

    public GameObject(float x, float y, float z, float scale){
        this.x = x;
        this.z = z;
    }

    public GameObject(Model model, float x, float y, float z, float scale){
        this(x,y,z,scale);
        instance = new ModelInstance(model);
        instance.transform.setToTranslationAndScaling(x,y,z,scale,scale,scale);
    }

    public GameObject(ModelInstance instance, float x, float y, float z, float scale){
        this(x,y,z,scale);
        this.instance = instance;
    }

    public GameObject nearestObject(Class c){
        double minDist = Double.MAX_VALUE;
        GameObject minObject = null;
        for(GameObject m : TanksGame.board.getGameObjectsSafe()){
            if(m == this || m.getClass() != c) continue;
            double dist = Math.sqrt(Math.pow(m.x - this.x,2) + Math.pow(m.z - this.z,0));
            if(dist<minDist) {
                minDist = dist;
                minObject = m;
            }
        }
        return minObject;
    }

public void update(){

}

public void takeAHit(){

}
public void killYourself(){
    TanksGame.objectsToRemove.add(this);
}

    public ModelInstance getInstance(){
        return instance;
    }

}
