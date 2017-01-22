package com.bitdecay.game.component;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Created by jacob on 1/21/17.
 */
public class DragComponent extends AbstractComponent {
    public float drag;
    public float time;

    public DragComponent(MyGameObject obj) {super(obj);}
    public DragComponent(MyGameObject obj, float drag, float time){
        super(obj);
        this.drag = drag;
        this.time = time;
    }
}
