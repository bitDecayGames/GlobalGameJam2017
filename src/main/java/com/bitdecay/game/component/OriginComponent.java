package com.bitdecay.game.component;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.gameobject.MyGameObject;

/**
 * The component in charge of tracking the x and y position of the object's origin (between 0 and 1).
 */
public class OriginComponent extends AbstractComponent {
    public float x = 0.5f;
    public float y = 0.5f;

    public OriginComponent(MyGameObject obj){super(obj);}
    public OriginComponent(MyGameObject obj, float x, float y){
        super(obj);
        this.x = Math.min(Math.abs(x), 1f);
        this.y = Math.min(Math.abs(y), 1f);
    }


    /**
     * Immutable
     * @return new Vector2
     */
    public Vector2 toVector2(){
        return new Vector2(x, y);
    }
}
