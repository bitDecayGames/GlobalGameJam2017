package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.gameobject.MyGameObject;
import com.typesafe.config.Config;

/**
 * This component extends the drawable component and it only draws a single static image.
 */
public class StaticImageComponent extends DrawableComponent {

    private TextureRegion image;

    public StaticImageComponent(MyGameObject obj, Config conf) {
        super(obj);
        image = MyGame.ATLAS.findRegion(conf.getString("path"));
    }

    @Override
    public TextureRegion image() {
        return image;
    }
}
