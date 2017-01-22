package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Created by Monday on 1/21/2017.
 */
public class ProximityIlluminationComponent extends AbstractComponent {
    public float fullBrightnessRadius = 50;
    public float fadeRange = 600;

    public ProximityIlluminationComponent(MyGameObject obj) {
        super(obj);
    }
}
