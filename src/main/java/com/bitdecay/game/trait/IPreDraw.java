package com.bitdecay.game.trait;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Monday on 1/21/2017.
 */
public interface IPreDraw {
    void preDraw(SpriteBatch spriteBatch, Camera camera);
}
