package com.thoman.tanks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class Board {

    private BoardSquare[][] boardSquares;
    private ArrayList<GameObject> gameObjects;
    private ArrayList<GameObject> gameObjectsToAdd;



    public Board(int w, int h){
        gameObjects = new ArrayList<GameObject>();
        gameObjectsToAdd = new ArrayList<GameObject>();
        boardSquares = new BoardSquare[Math.max(w,1)][Math.max(h,1)];
        for(int i = 0; i<width(); i++)
            for(int j = 0; j<height(); j++) {
                boardSquares[i][j] = new BoardSquare();
                boardSquares[i][j].setColor(Color.FOREST);
            }
    }

    public ArrayList<TurningPoint> level1TurningPoints(){
        ArrayList<TurningPoint> out = new ArrayList<TurningPoint>();
        out.add(new TurningPoint(0,-2,0, 0.01));
        out.add(new TurningPoint(0,2,90, 0.01));
        out.add(new TurningPoint(5,2,90, 0.01));

        return out;
    }

    public Board(){
        this(10,10);
        for(int i = 0; i<5; i++) {
            boardSquares[i][3].setColor(Color.BROWN);
            boardSquares[5][i+3].setColor(Color.BROWN);
            boardSquares[Math.min(i+5,10)][7].setColor(Color.BROWN);
        }
    //    boardSquares[5][3].setColor(Color.RED);
   //     boardSquares[5][7].setColor(Color.RED);
    }



    public int[] closestSquare(double x, double y, double z){
        double min = Double.MAX_VALUE;
        int[] out = new int[2];
        for(int i = 0; i<boardSquares.length; i++)
            for(int j = 0; j<boardSquares[i].length; j++){
                double dist = Math.sqrt(Math.pow(x-boardSquares[i][j].getX(),2) + Math.pow(y-boardSquares[i][j].getY(),2) + Math.pow(z-boardSquares[i][j].getZ(),2));
                if(dist<min){
                    min = dist;
                    out[0] = i;
                    out[1] = j;
                }
            }
        return out;
    }


    public void setMaterial(int x, int y, Color color){
        boardSquares[x][y].setColor(color);
    }

    public void fillObjectInstances(ArrayList<ModelInstance> groundTiles, ArrayList<ModelInstance> gameObjects){
        groundTiles.clear();
        gameObjects.clear();
        for(int i = 0; i<width(); i++)
            for(int j = 0; j<height(); j++){
                boardSquares[i][j].getInstance().transform.setToTranslation(i-width()/2,0,j-width()/2);
                groundTiles.add(boardSquares[i][j].getInstance());
            }

        for(GameObject m : this.gameObjects)
            gameObjects.add(m.getInstance());

    }

    public void addGameObject(GameObject object, int x, int z){
        gameObjects.add(object);
        boardSquares[x][z].associatedObject = object;
    }

    public void addGameObject(GameObject object){
        gameObjectsToAdd.add(object);
    }

    public void removeGameObject(GameObject o){
        int toRemove = gameObjects.indexOf(o);
        System.out.println(toRemove);
        if(gameObjects.size()>0)
        gameObjects.remove(toRemove);

    }

    public void update(){
        for(GameObject m : gameObjects)
            m.update();
        for(int i = gameObjectsToAdd.size()-1; i>=0; i--)
            gameObjects.add(gameObjectsToAdd.remove(i));
    }

    public ArrayList<GameObject> getGameObjectsSafe(){
        ArrayList<GameObject> out = new ArrayList<GameObject>();
        for(GameObject m : gameObjects)
            out.add(m);
        return out;
    }

    public BoardSquare getSquare(int x, int z){
        return boardSquares[x][z];
    }


    public int width(){return boardSquares.length;}
    public int height(){return boardSquares[0].length;}

}
