package com.bitdecay.game.component;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Created by jacob on 1/21/17.
 */
public class ImpulseComponent extends AbstractComponent {
    public float x;
    public float y;

    public ImpulseComponent(MyGameObject obj) {super(obj);}
    public ImpulseComponent(MyGameObject obj, float x, float y){
        super(obj);
        this.x = x;
        this.y = y;
    }
    public ImpulseComponent(MyGameObject obj, Vector2 vector2) {
        this(obj, vector2.x, vector2.y);
    }

    public Vector2 toVector2(){
        return new Vector2(x, y);
    }
}
