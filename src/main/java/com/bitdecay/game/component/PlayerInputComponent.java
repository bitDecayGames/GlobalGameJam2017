package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * This component marks an object as controllable by the player.
 */
public class PlayerInputComponent extends AbstractComponent {

    public float rotationAmountPerStep = 0;
    public float maxDegrees = 0;
    public float minDegrees = 0;

    public PlayerInputComponent(MyGameObject obj, float rotationAmountPerStep, float maxDegrees, float minDegrees){
        super(obj);
        this.rotationAmountPerStep = rotationAmountPerStep;
        this.maxDegrees = maxDegrees;
        this.minDegrees = minDegrees;
    }
}
