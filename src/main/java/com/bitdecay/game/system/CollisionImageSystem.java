package com.bitdecay.game.system;

import com.bitdecay.game.component.CollisionResponseComponent;
import com.bitdecay.game.component.ImageCollisionComponent;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monday on 1/21/2017.
 */
public class CollisionImageSystem extends AbstractForEachUpdatableSystem {
    List<MyGameObject> collisionObjects = new ArrayList<>();

    public CollisionImageSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    public void before() {
        collisionObjects.clear();
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        collisionObjects.add(gob);
    }

    @Override
    public void after() {
        super.after();

        for (MyGameObject object1 : collisionObjects) {
            for (MyGameObject object2 : collisionObjects) {
                if (object1 != object2) {
                }
            }
        }
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PositionComponent.class, ImageCollisionComponent.class, CollisionResponseComponent.class);

    }
}
