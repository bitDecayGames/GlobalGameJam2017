package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * The component in charge of tracking the desired rotation of the object (0 degrees SHOULD be facing right... don't quote me)
 */
public class DesiredRotationComponent extends AbstractComponent {
    public float degrees = 0;

    public DesiredRotationComponent(MyGameObject obj){super(obj);}
    public DesiredRotationComponent(MyGameObject obj, float degrees){
        super(obj);
        this.degrees = degrees;
    }

    public float toRadians(){
        return (float) Math.toRadians((double) degrees);
    }
}
