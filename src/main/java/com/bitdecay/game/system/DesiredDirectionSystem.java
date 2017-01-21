package com.bitdecay.game.system;


import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.DesiredDirectionComponent;
import com.bitdecay.game.component.VelocityComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.VectorMath;

/**
 * This system is in charge of updating the rotation of an object to be closer to the desired rotation
 */
public class DesiredDirectionSystem extends AbstractForEachUpdatableSystem {

    public DesiredDirectionSystem(AbstractRoom room) { super(room); }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(DesiredDirectionComponent.class, VelocityComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(DesiredDirectionComponent.class, desired ->
                gob.forEachComponentDo(VelocityComponent.class, velocity-> {
                    Vector2 currentDirection = velocity.toVector2().nor();
                    float rotationDifference = VectorMath.angleInDegrees(currentDirection, desired.toVector2());
                    float rotationToAdd = rotationDifference * desired.rotationSpeed;
                    Vector2 newDirection = VectorMath.rotatePointByDegreesAroundZero(currentDirection, rotationToAdd).scl(velocity.toVector2().len());
                    velocity.x = newDirection.x;
                    velocity.y = newDirection.y;
                }));
    }

}
