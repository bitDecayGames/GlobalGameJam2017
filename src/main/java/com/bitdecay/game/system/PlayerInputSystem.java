package com.bitdecay.game.system;

import com.badlogic.gdx.Input;
import com.bitdecay.game.component.*;
import com.badlogic.gdx.utils.Array;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.gameobject.MyGameObjectFactory;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.game.util.InputHelper;

/**
 * This system will handle player keyboard input to modify the rotation of the player
 */
public class PlayerInputSystem extends AbstractUpdatableSystem {

    public PlayerInputSystem(AbstractRoom room) { super(room); }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PlayerInputComponent.class, DesiredDirectionComponent.class);
    }

    @Override
    public void update(float delta) {
        float rotationDirection = 0;
        if (InputHelper.isKeyPressed(Input.Keys.W, Input.Keys.UP, Input.Keys.RIGHT)) rotationDirection = -1;
        else if (InputHelper.isKeyPressed(Input.Keys.S, Input.Keys.DOWN, Input.Keys.LEFT)) rotationDirection = 1;
        if (rotationDirection != 0) {
            final float rotationDirectionFinal = rotationDirection;
            gobs.forEach(gob -> gob.forEachComponentDo(PlayerInputComponent.class, pi ->
                    gob.forEachComponentDo(DesiredDirectionComponent.class, rot ->
                            rot.addDegrees(rotationDirectionFinal * pi.rotationAmountPerStep))));
        }

        if (InputHelper.isKeyJustPressed(Input.Keys.SPACE, Input.Keys.ENTER)) {
            // TODO: add trigger to sonar ping
            // maybe something like:
            // gobs.forEach(gob -> gob.addComponent(SonarPingComponent))
            // which then gets removed when the SonarPingSystem finds it and triggers the ping?
            log.debug("ADDING PING");
            gobs.forEach(gob -> gob.forEachComponentDo(PositionComponent.class, pos -> {
                this.room.getGameObjects().add(MyGameObjectFactory.ping(pos.toVector2()));
            }));
        }

        if (InputHelper.isKeyJustPressed(Input.Keys.CONTROL_RIGHT)){
            float[] coords = new float[5];
            gobs.forEach(gob -> gob.forEachComponentDo(PlayerInputComponent.class, pi -> {
                gob.forEachComponentDo(PositionComponent.class, pos -> {
                    coords[0] = pos.x - 2;
                    coords[1] = pos.y - 5;
                });
                gob.forEachComponentDo(RotationComponent.class, rota ->
                    coords[2] = rota.degrees);

                }));

            this.room.getGameObjects().add(MyGameObjectFactory.torpedo(coords[0], coords[1], coords[2]));
        }
    }
}
