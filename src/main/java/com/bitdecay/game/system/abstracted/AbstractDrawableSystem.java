package com.bitdecay.game.system.abstracted;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.trait.IDrawWithCamera;

/**
 * A drawable system
 */
public abstract class AbstractDrawableSystem extends AbstractSystem implements IDrawWithCamera {

    public AbstractDrawableSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    public void preDraw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        // override to implement
    }

    @Override
    public void postDraw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        // override to implement
    }
}
