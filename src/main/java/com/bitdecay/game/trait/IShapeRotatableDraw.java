package com.bitdecay.game.trait;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Allows the object to draw a shape at a given location with a rotation
 */
public interface IShapeRotatableDraw {
    void draw(ShapeRenderer shapeRenderer, Vector2 position, float rotation);
}
