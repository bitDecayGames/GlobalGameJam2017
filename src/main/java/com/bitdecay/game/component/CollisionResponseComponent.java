package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Monday on 1/21/2017.
 */
public class CollisionResponseComponent extends AbstractComponent {
    public Set<MyGameObject> collidedWith = new HashSet<>();

    public CollisionResponseComponent(MyGameObject obj) {
        super(obj);
    }
}
