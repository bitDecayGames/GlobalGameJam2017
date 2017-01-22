package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.AccelerationComponent;
import com.bitdecay.game.component.VelocityComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * Created by Luke on 1/21/2017.
 */
public class AccelerationSystem extends AbstractForEachUpdatableSystem {
    public AccelerationSystem(AbstractRoom room){
        super(room);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(AccelerationComponent.class, accel ->
                gob.forEachComponentDo(VelocityComponent.class, vel ->  {
                    Vector2 newVel = vel.toVector2().add(accel.toVector2());
                    if (accel.maxVelocity != Float.NEGATIVE_INFINITY && newVel.len() > accel.maxVelocity) {
                        newVel.nor().scl(accel.maxVelocity);
                    }
                    vel.x = newVel.x;
                    vel.y = newVel.y;

                }));
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(VelocityComponent.class, AccelerationComponent.class);
    }
}
