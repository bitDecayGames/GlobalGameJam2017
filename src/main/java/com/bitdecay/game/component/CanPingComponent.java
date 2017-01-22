package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Created by Luke on 1/22/2017.
 */
public class CanPingComponent extends AbstractComponent {
    public float timer = 0;
    public boolean justLostSonar = false;

    public CanPingComponent(MyGameObject obj) {
        super(obj);
    }
}
