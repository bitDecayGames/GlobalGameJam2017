package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.RotationComponent;
import com.bitdecay.game.component.ThrustComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.VectorMath;

/**
 * This system is in charge of updating the position based on the rotation and thrust
 */
public class ConstantThrustSystem extends AbstractForEachUpdatableSystem {

    public ConstantThrustSystem(AbstractRoom room) {super(room);}

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PositionComponent.class, RotationComponent.class, ThrustComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(PositionComponent.class, pos -> gob.forEachComponentDo(RotationComponent.class, rot -> gob.forEachComponentDo(ThrustComponent.class, thrust -> {
            Vector2 newPos = VectorMath.rotatePointByDegreesAroundZero(1, 0, rot.degrees).scl(thrust.power).add(pos.toVector2());
            pos.x = newPos.x;
            pos.y = newPos.y;
        })));
    }

}
