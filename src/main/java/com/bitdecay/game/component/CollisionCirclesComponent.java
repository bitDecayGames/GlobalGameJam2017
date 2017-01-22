package com.bitdecay.game.component;

import com.badlogic.gdx.math.Circle;
import com.bitdecay.game.gameobject.MyGameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monday on 1/21/2017.
 */
public class CollisionCirclesComponent extends AbstractComponent {

    public List<Circle> collisionCircles = new ArrayList<>();

    public CollisionCirclesComponent(MyGameObject obj) {
        super(obj);
    }
}
