package com.bitdecay.game.system;

import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.gameobject.MyGameObjectFactory;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.room.DemoRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * This system is in charge of updating the position of an object when the object goes out of the window and must respawn
 */
public class RespawnSystem extends AbstractForEachUpdatableSystem {

    float secondTimer = 0;

    public RespawnSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        if (gob.hasComponents(RespawnRecorderComponent.class, RemoveNowComponent.class)){
               gob.forEachComponentDo(RespawnRecorderComponent.class, rec -> {
                   if (rec.lastPos() != null) {
                       MyGameObject player = MyGameObjectFactory.ship(room, rec.lastPos(), rec.lastVel(), rec.lastDesiredRotation());
                       DemoRoom.player = player;
                       room.getGameObjects().add(player);
                   }
            });

        }
        return gob.hasComponents(RespawnRecorderComponent.class, PositionComponent.class); // just no op
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        secondTimer += delta;
        if (secondTimer > 1) {
            gob.forEachComponentDo(PositionComponent.class, pos ->
                    gob.forEachComponentDo(RespawnRecorderComponent.class, rec ->
                            gob.forEachComponentDo(VelocityComponent.class, vel -> gob.forEachComponentDo(RotationComponent.class, rot ->
                                    rec.addPoint(pos.toVector2(), vel.toVector2(), rot.degrees)))));
            secondTimer = 0;
        }
    }

}
