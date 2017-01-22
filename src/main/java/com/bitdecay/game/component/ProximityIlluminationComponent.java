package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Created by Monday on 1/21/2017.
 */
public class ProximityIlluminationComponent extends AbstractComponent {
    public float fullBrightnessRadius = 10;
    public float fadeRange = 150;

    public ProximityIlluminationComponent(MyGameObject obj) {
        super(obj);
    }
}
