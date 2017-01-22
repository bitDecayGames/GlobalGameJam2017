package com.bitdecay.game.system.abstracted;

import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.trait.IDrawWithCamera;

/**
 * A drawable system
 */
public abstract class AbstractPreDrawSystem extends AbstractSystem implements IDrawWithCamera {

    public AbstractPreDrawSystem(AbstractRoom room) {
        super(room);
    }
}
