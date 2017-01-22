package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.PredictiveCameraFollowComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * This system is in charge of updating the camera with the list of points to follow each step.
 */
public class CameraPredictiveSystem extends AbstractForEachUpdatableSystem {
    private float leadDistance = 0;
    private Vector2 right = new Vector2(1, 0);
    public CameraPredictiveSystem(AbstractRoom room, float leadDistance) {
        super(room);
        this.leadDistance = leadDistance;
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PredictiveCameraFollowComponent.class, PositionComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(PositionComponent.class, pos -> room.camera.addFollowPoint(right.cpy().scl(leadDistance).add(pos.toVector2())));
    }

}
