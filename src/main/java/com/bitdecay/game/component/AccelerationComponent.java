package com.bitdecay.game.component;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.gameobject.MyGameObject;

public class AccelerationComponent extends AbstractComponent {
    public float x = 0;
    public float y = 0;
    public float maxVelocity = Float.NEGATIVE_INFINITY;

    public AccelerationComponent(MyGameObject obj){super(obj);}
    public AccelerationComponent(MyGameObject obj, float x, float y){
        super(obj);
        this.x = x;
        this.y = y;
    }
    public AccelerationComponent(MyGameObject obj, Vector2 vector2, float maxVelocity){
        this(obj, vector2.x, vector2.y);
        this.maxVelocity = maxVelocity;
    }

    /**
     * Immutable
     * @return new Vector2
     */
    public Vector2 toVector2(){
        return new Vector2(x, y);
    }
}
