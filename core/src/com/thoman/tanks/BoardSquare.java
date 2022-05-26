package com.thoman.tanks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class BoardSquare {

    private ModelInstance instance;
    public GameObject associatedObject;


    private static ModelBuilder builder = new ModelBuilder();

    public BoardSquare(){
        Material material = new Material(ColorAttribute.createDiffuse(Color.BROWN));
        Model ground = builder.createBox(1f,1f,1f, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(ground);
    }

    public void setColor(Color color){
        instance.materials.get(0).set(ColorAttribute.createDiffuse(color));
    }

    public ModelInstance getInstance(){
        return instance;
    }

    public double getX(){
        return (double)instance.transform.val[12];
    }
    public double getY(){
        return (double)instance.transform.val[13];
    }
    public double getZ(){
        return (double)instance.transform.val[14];
    }

}
