package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.DragComponent;
import com.bitdecay.game.component.VelocityComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * Created by jacob on 1/21/17.
 */
public class DragSystem extends AbstractForEachUpdatableSystem{

    public DragSystem(AbstractRoom room) {super(room);}

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(DragComponent.class, drag ->
            gob.forEachComponentDo(VelocityComponent.class, vel -> {
                Vector2 newVel = vel.toVector2().scl(-drag.drag);
                vel.x += newVel.x;
                vel.y += newVel.y;
                drag.time -= delta;
                if (drag.time <= 0){
                    gob.removeComponent(DragComponent.class);
                }
            }));

    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(DragComponent.class, VelocityComponent.class);
    }
}
