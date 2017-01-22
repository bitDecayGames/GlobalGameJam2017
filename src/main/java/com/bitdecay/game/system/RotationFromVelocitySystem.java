package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.RotationComponent;
import com.bitdecay.game.component.VelocityComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.VectorMath;

/**
 * This system is in charge of updating the rotation from the velocity
 */
public class RotationFromVelocitySystem extends AbstractForEachUpdatableSystem {
    public RotationFromVelocitySystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(VelocityComponent.class, RotationComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(VelocityComponent.class, vel ->
                gob.forEachComponentDo(RotationComponent.class, rot -> {
                    if(rot.rotationFromVelocity){
                        rot.degrees = VectorMath.angleInDegrees(new Vector2(1, 0), vel.toVector2().nor());
                    }
                }));
    }

}
