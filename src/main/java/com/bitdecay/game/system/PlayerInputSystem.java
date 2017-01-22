package com.bitdecay.game.system;

import com.badlogic.gdx.Input;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.gameobject.MyGameObjectFactory;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.game.util.InputHelper;

import java.util.List;

/**
 * This system will handle player keyboard input to modify the rotation of the player
 */
public class PlayerInputSystem extends AbstractUpdatableSystem {

    public PlayerInputSystem(AbstractRoom room) { super(room); }

    private boolean canPing(List<MyGameObject> gobs) {
        return ! gobs.stream().anyMatch(gameObj -> gameObj.hasComponent(SonarPingComponent.class));
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PlayerInputComponent.class, DesiredDirectionComponent.class) || gob.hasComponent(SonarPingComponent.class);
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

        if (canPing(gobs)) {
            gobs.forEach(gob -> gob.forEachComponentDo(PlayerInputComponent.class, pos -> {
                if (gob.hasComponent(AnimationComponent.class)) {
                    gob.removeComponent(AnimationComponent.class);
                }
            }));
        }

        if (InputHelper.isKeyJustPressed(Input.Keys.SPACE, Input.Keys.ENTER)) {
            // TODO: add trigger to sonar ping
            // maybe something like:
            // gobs.forEach(gob -> gob.addComponent(SonarPingComponent))
            // which then gets removed when the SonarPingSystem finds it and triggers the ping?
            if (canPing(gobs)) {
                gobs.forEach(gob -> gob.forEachComponentDo(PositionComponent.class, pos -> {
                    room.getGameObjects().add(MyGameObjectFactory.ping(pos.toVector2()));
                    gob.addComponent(new AnimationComponent(gob, "player/charge", 0.5f));

                }));
            }
        }

        if (InputHelper.isKeyJustPressed(Input.Keys.CONTROL_RIGHT)){
            float[] coords = new float[3];
            gobs.forEach(gob -> gob.forEachComponentDo(PlayerInputComponent.class, pi -> {
                gob.forEachComponentDo(CanShootComponent.class, shooter -> {
                    gob.forEachComponentDo(PositionComponent.class, pos -> {
                        coords[0] = pos.x - 2;
                        coords[1] = pos.y - 5;
                    });
                    gob.forEachComponentDo(RotationComponent.class, rota ->
                            coords[2] = rota.degrees);
                    this.room.getGameObjects().add(MyGameObjectFactory.torpedo(coords[0], coords[1], coords[2]));
                    gob.removeComponent(CanShootComponent.class);
                    gob.addComponent(new TimerComponent (gob, 2f, myGameObject -> {
                        myGameObject.addComponent(new CanShootComponent(myGameObject));
                        myGameObject.removeComponent(TimerComponent.class);
                    }));
                });
            }));
//            this.room.gobs.add(MyGameObjectFactory.splashText("Torpedo!!", 4, 1000, (int)coords[0], (int)coords[1]));
        }
    }
}
