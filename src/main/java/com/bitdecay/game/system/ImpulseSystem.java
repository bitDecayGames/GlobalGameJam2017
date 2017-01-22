package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.ImpulseComponent;
import com.bitdecay.game.component.VelocityComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * Created by jacob on 1/21/17.
 */
public class ImpulseSystem extends AbstractForEachUpdatableSystem {

    public ImpulseSystem(AbstractRoom room) {super(room);}

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(ImpulseComponent.class, imp ->
            gob.forEachComponentDo(VelocityComponent.class, vel -> {
                Vector2 newVel = vel.toVector2().add(imp.toVector2());
                vel.x = newVel.x;
                vel.y = newVel.y;
                gob.removeComponent(ImpulseComponent.class);
            }));

    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(ImpulseComponent.class, VelocityComponent.class);
    }
}
