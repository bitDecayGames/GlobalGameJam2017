package com.bitdecay.game.system;

import com.badlogic.gdx.Input;
import com.bitdecay.game.component.GlobalInputComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.gameobject.MyGameObjectFactory;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.game.util.InputHelper;

/**
 * This system will handle player keyboard input to modify the rotation of the player
 */
public class GlobalInputSystem extends AbstractUpdatableSystem {

    public GlobalInputSystem(AbstractRoom room) { super(room); }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(GlobalInputComponent.class);
    }

    @Override
    public void update(float delta) {
        if (InputHelper.isKeyJustPressed(Input.Keys.P)) {
            room.gobs.add(MyGameObjectFactory.splashText("P Text!", 3, 1000));
        }
    }
}
