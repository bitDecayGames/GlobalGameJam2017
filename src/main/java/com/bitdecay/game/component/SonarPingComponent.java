package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 */
public class SonarPingComponent extends AbstractComponent {
    // the radius from the center that the forward edge of the ping is
    public float radius = 0;

    public SonarPingComponent(MyGameObject obj){super(obj);}
}