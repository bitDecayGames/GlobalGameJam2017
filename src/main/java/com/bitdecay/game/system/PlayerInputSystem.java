package com.bitdecay.game.system;

import com.badlogic.gdx.Input;
import com.bitdecay.game.component.DesiredRotationComponent;
import com.bitdecay.game.component.PlayerInputComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.game.util.InputHelper;

/**
 * This system will handle player keyboard input to modify the rotation of the player
 */
public class PlayerInputSystem extends AbstractUpdatableSystem {
    private float rotationAmountPerStep = 0;
    public PlayerInputSystem(AbstractRoom room, float rotationAmountPerStep) {
        super(room);
        this.rotationAmountPerStep = rotationAmountPerStep;
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PlayerInputComponent.class, DesiredRotationComponent.class);
    }

    @Override
    public void update(float delta) {
        float rotationDirection = 0;
        if (InputHelper.isKeyPressed(Input.Keys.W, Input.Keys.UP)) rotationDirection = -1;
        else if (InputHelper.isKeyPressed(Input.Keys.S, Input.Keys.DOWN)) rotationDirection = 1;
        if (rotationDirection != 0) {
            final float rotationDirectionFinal = rotationDirection;
            gobs.forEach(gob -> gob.forEachComponentDo(DesiredRotationComponent.class, rot -> rot.degrees += rotationDirectionFinal * rotationAmountPerStep));
        }
    }
}
