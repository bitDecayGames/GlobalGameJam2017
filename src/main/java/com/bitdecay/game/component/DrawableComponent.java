package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.gameobject.MyGameObject;

/**
 * This component is for extending other drawable component types.  Ie: animated, static, etc
 */
public abstract class DrawableComponent extends AbstractComponent {

    public boolean reactsToSonar = false;
    public boolean proximityRender = true;

    public DrawableComponent(MyGameObject obj) {
        super(obj);
    }

    public abstract TextureRegion image();

    public DrawableComponent setReactsToSonar(boolean reactsToSonar){
        this.reactsToSonar = reactsToSonar;
        return this;
    }

    public DrawableComponent setProximityRender(boolean proximityRender){
        this.proximityRender = proximityRender;
        return this;
    }
}
