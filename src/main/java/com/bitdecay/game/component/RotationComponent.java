package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * The component in charge of tracking the rotation of the object (0 degrees SHOULD be facing right... don't quote me)
 */
public class RotationComponent extends AbstractComponent {
    public float degrees = 0;

    public RotationComponent(MyGameObject obj){super(obj);}
    public RotationComponent(MyGameObject obj, float degrees){
        super(obj);
        this.degrees = degrees;
    }

    public float toRadians(){
        return (float) Math.toRadians((double) degrees);
    }
}
