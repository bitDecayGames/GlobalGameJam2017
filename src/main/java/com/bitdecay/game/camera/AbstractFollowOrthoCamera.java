package com.bitdecay.game.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractFollowOrthoCamera extends OrthographicCamera {

    public AbstractFollowOrthoCamera(float screenWidth, float screenHeight){super(screenWidth, screenHeight);}

    public abstract void addFollowPoint(Vector2 point);

    public abstract void update(float delta);
}
