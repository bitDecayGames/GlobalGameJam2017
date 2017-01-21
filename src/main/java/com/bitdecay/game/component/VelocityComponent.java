package com.bitdecay.game.component;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.util.VectorMath;

/**
 * Created by Luke on 1/21/2017.
 */
public class VelocityComponent extends AbstractComponent {
    public float x = 0;
    public float y = 0;

    public VelocityComponent(MyGameObject obj){super(obj);}
    public VelocityComponent(MyGameObject obj, float x, float y){
        super(obj);
        this.x = x;
        this.y = y;
    }

    /**
     * Immutable
     * @return new Vector2
     */
    public Vector2 toVector2(){
        return new Vector2(x, y);
    }
}
