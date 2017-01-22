package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Created by jacob on 1/21/17.
 */
public class CanShootComponent extends AbstractComponent {
    public float timer = 0;

    public CanShootComponent(MyGameObject obj) {
        super(obj);
    }
}
