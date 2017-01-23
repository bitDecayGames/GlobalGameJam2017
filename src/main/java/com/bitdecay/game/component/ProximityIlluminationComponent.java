package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

public class ProximityIlluminationComponent extends AbstractComponent {
    public float fullBrightnessRadius = 50;
    public float fadeRange = 600;

    public ProximityIlluminationComponent(MyGameObject obj) {
        super(obj);
    }

    public ProximityIlluminationComponent(MyGameObject obj, float clear, float fade) {
        super(obj);
        fullBrightnessRadius = clear;
        fadeRange = fade;
    }
}
