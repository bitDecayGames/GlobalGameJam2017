package com.bitdecay.game.system.abstracted;

import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.trait.IPreDraw;

/**
 * A drawable system
 */
public abstract class AbstractPreDrawSystem extends AbstractSystem implements IPreDraw {

    public AbstractPreDrawSystem(AbstractRoom room) {
        super(room);
    }
}
