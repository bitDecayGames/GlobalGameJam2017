package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.VelocityComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.VectorMath;

/**
 * Created by Luke on 1/21/2017.
 */
public class VelocitySystem extends AbstractForEachUpdatableSystem{

    public VelocitySystem(AbstractRoom room){
        super(room);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(PositionComponent.class, pos -> gob.forEachComponentDo(VelocityComponent.class, vel ->  {
            Vector2 newPos = pos.toVector2().add(vel.toVector2());
            pos.x = newPos.x;
            pos.y = newPos.y;
        }));
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PositionComponent.class, VelocityComponent.class);
    }
}
