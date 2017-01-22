package com.bitdecay.game.component;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.gameobject.MyGameObject;

/**
 * The component in charge of tracking the x and y position of the object relative to some other objects position.
 */
public class RelativePositionComponent extends AbstractComponent {
    public MyGameObject other;
    public float x = 0;
    public float y = 0;

    public RelativePositionComponent(MyGameObject obj, MyGameObject other){this(obj, other, 0, 0);}
    public RelativePositionComponent(MyGameObject obj, MyGameObject other, float x, float y){
        super(obj);
        this.x = x;
        this.y = y;
        this.other = other;
    }

    /**
     * Immutable
     * @return new Vector2
     */
    public Vector2 toVector2(){
        return new Vector2(x, y);
    }
}
