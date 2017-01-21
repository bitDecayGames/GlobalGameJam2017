package com.bitdecay.game.component;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.util.VectorMath;

/**
 * The component in charge of tracking the desired rotation of the object (0 degrees SHOULD be facing right... don't quote me)
 */
public class DesiredDirectionComponent extends AbstractComponent {
    private float degrees = 0;
    public float rotationSpeed = 1f;

    public DesiredDirectionComponent(MyGameObject obj){super(obj);}

    /**
     *
     * @param obj
     * @param degrees
     * @param rotationSpeed the closer this is to 1 the closer that the rotation will remain to the desired rotation
     */
    public DesiredDirectionComponent(MyGameObject obj, float degrees, float rotationSpeed){
        super(obj);
        this.degrees = degrees;
        this.rotationSpeed =  Math.min(Math.abs(rotationSpeed), 1f); // limit between 0 and 1
    }

    public float toRadians(){
        return (float) Math.toRadians((double) degrees);
    }

    public float toDegrees() { return degrees; }

    public void addDegrees(float degreesToAdd){
        degrees += degreesToAdd;
//        if (degrees > 180) degrees = -180 + (degrees - 180);
//        else if (degrees < -180) degrees = 180 + (degrees + 180);
    }

    public void addRadians(float radians){
        addDegrees((float) Math.toRadians(radians));
    }

    public Vector2 toVector2(){
        return VectorMath.rotatePointByDegreesAroundZero(1, 0, degrees);
    }
}
