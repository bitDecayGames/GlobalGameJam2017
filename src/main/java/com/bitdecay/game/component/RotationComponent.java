package com.bitdecay.game.component;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.util.VectorMath;

/**
 * The component in charge of tracking the rotation of the object (0 degrees SHOULD be facing right... don't quote me)
 */
public class RotationComponent extends AbstractComponent {
    public float degrees = 0;
    public boolean rotationFromVelocity = true;

    public RotationComponent(MyGameObject obj){super(obj);}
    public RotationComponent(MyGameObject obj, float degrees){
        super(obj);
        this.degrees = degrees;
    }

    public float toRadians(){
        return (float) Math.toRadians((double) degrees);
    }

    public Vector2 toVector2(){
        return VectorMath.degreesToVector2(degrees);
    }

}
