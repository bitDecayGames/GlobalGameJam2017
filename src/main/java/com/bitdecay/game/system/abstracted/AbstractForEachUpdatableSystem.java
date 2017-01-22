package com.bitdecay.game.system.abstracted;

import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;

/**
 * All this class does is add one more layer for systems that don't need to have access to the full list of gobs when they are doing their processing.  In most cases, you will extend this class instead of AbstractSystem directly.  See the DrawSystem for an example of when you DO extend AbstractSystem directly.
 */
public abstract class AbstractForEachUpdatableSystem extends AbstractUpdatableSystem {

    public AbstractForEachUpdatableSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    public void update(float delta) {
        before();
        gobs.forEach(gob -> forEach(delta, gob));
        after();
    }

    public void before() {
        // override to implement
    }

    public void after() {
        // override to implement
    }

    protected abstract void forEach(float delta, MyGameObject gob);
}
