package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * This component marks an object as controllable by the player.
 */
public class PlayerInputComponent extends AbstractComponent {

    public float rotationAmountPerStep = 0;

    public PlayerInputComponent(MyGameObject obj, float rotationAmountPerStep){
        super(obj);
        this.rotationAmountPerStep = rotationAmountPerStep;
    }
}
