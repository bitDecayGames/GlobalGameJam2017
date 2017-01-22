package com.bitdecay.game.component;


import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Created by Luke on 1/21/2017.
 */
public class RandomOrbitComponent extends AbstractComponent {
    public float orbitX = 0;
    public float orbitY = 0;
    public float radius = 0;

    public double targetX;
    public double targetY;
    public boolean reachedDestination = false;

    public RandomOrbitComponent(MyGameObject obj, float x, float y, float r){
        super(obj);
        orbitX = x;
        orbitY = y;
        radius = r;
    }

    public Vector2 toVector2(){
        return new Vector2(orbitX, orbitY);
    }
}
