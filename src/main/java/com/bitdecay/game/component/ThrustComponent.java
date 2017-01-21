package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * The component in charge of tracking how much thrust an object has
 */
public class ThrustComponent extends AbstractComponent {
    public float power = 0;

    public ThrustComponent(MyGameObject obj){super(obj);}
    public ThrustComponent(MyGameObject obj, float power){
        super(obj);
        this.power = power;
    }
}
