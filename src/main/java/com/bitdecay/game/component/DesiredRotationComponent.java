package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * The component in charge of tracking the desired rotation of the object (0 degrees SHOULD be facing right... don't quote me)
 */
public class DesiredRotationComponent extends AbstractComponent {
    public float degrees = 0;
    public float rotationSpeed = 1f;

    public DesiredRotationComponent(MyGameObject obj){super(obj);}

    /**
     *
     * @param obj
     * @param degrees
     * @param rotationSpeed the closer this is to 1 the closer that the rotation will remain to the desired rotation
     */
    public DesiredRotationComponent(MyGameObject obj, float degrees, float rotationSpeed){
        super(obj);
        this.degrees = degrees;
        this.rotationSpeed =  Math.min(Math.abs(rotationSpeed), 1f); // limit between 0 and 1
    }

    public float toRadians(){
        return (float) Math.toRadians((double) degrees);
    }
}
