package com.bitdecay.game.system;

import com.bitdecay.game.component.DesiredRotationComponent;
import com.bitdecay.game.component.RotationComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * This system is in charge of updating the rotation of an object to be closer to the desired rotation
 */
public class DesiredRotationSystem extends AbstractForEachUpdatableSystem {

    public DesiredRotationSystem(AbstractRoom room) { super(room); }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(DesiredRotationComponent.class, RotationComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(DesiredRotationComponent.class, desired -> gob.forEachComponentDo(RotationComponent.class, rot -> rot.degrees += desired.rotationSpeed * (desired.degrees - rot.degrees)));
    }

}
