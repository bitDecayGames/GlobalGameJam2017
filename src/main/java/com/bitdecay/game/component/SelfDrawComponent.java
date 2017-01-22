package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Created by Monday on 1/22/2017.
 */
public abstract class SelfDrawComponent extends DrawableComponent {
    public SelfDrawComponent(MyGameObject obj) {
        super(obj);
    }

    @Override
    public TextureRegion image() {
        return null;
    }

    public abstract void drawToBatch(SpriteBatch batch);
}
