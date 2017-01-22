package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.RandomOrbitComponent;
import com.bitdecay.game.component.VelocityComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.VectorMath;

/**
 * Created by Luke on 1/21/2017.
 */
public class RandomOrbitSystem extends AbstractForEachUpdatableSystem {


    public RandomOrbitSystem(AbstractRoom room) {super(room);}

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(RandomOrbitComponent.class, VelocityComponent.class, PositionComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(RandomOrbitComponent.class, Roc->
                gob.forEachComponentDo(VelocityComponent.class, velocity->
                    gob.forEachComponentDo(PositionComponent.class,pos-> {
                        if(Roc.reachedDestination || (velocity.x == 0 && velocity.y == 0)) {
                            double angle = Math.random()*Math.PI*2;
                            Roc.targetX = Roc.orbitX + (Math.cos(angle)*Roc.radius);
                            Roc.targetY = Roc.orbitY + (Math.sin(angle)*Roc.radius);
                            velocity.x = (float) ((Roc.targetX - pos.x) * .02);
                            velocity.y = (float) ((Roc.targetY - pos.y) * .02);
                            Roc.reachedDestination = false;
                        } else if(Math.abs(pos.x - Roc.targetX) <= 1 && Math.abs(pos.y- Roc.targetY) <= 1){
                            Roc.reachedDestination = true;
                        }
                })));
    }
}
