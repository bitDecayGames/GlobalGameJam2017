package com.bitdecay.game.system;

import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.RandomOrbitComponent;
import com.bitdecay.game.component.VelocityComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * Created by Luke on 1/21/2017.
 */
public class RandomOrbitSystem extends AbstractForEachUpdatableSystem {

    public RandomOrbitSystem(AbstractRoom room) {super(room);}

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(RandomOrbitComponent.class, VelocityComponent.class, PositionComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {

    }
}
