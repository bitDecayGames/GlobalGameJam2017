package com.bitdecay.game.system;

import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.PredictiveCameraFollowComponent;
import com.bitdecay.game.component.RotationComponent;
import com.bitdecay.game.component.ThrustComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.VectorMath;

/**
 * This system is in charge of updating the camera with the list of points to follow each step.
 */
public class CameraPredictiveSystem extends AbstractForEachUpdatableSystem {
    private float leadDistance = 0;
    public CameraPredictiveSystem(AbstractRoom room, float leadDistance) {
        super(room);
        this.leadDistance = leadDistance;
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PredictiveCameraFollowComponent.class, PositionComponent.class, RotationComponent.class, ThrustComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        // room.camera.addFollowPoint(pos.toVector2())
        gob.forEachComponentDo(PositionComponent.class, pos -> gob.forEachComponentDo(RotationComponent.class, rot -> gob.forEachComponentDo(ThrustComponent.class, thrust -> room.camera.addFollowPoint(VectorMath.rotatePointByDegreesAroundZero(1, 0, rot.degrees).scl(thrust.power * leadDistance).add(pos.toVector2())))));
    }

}
