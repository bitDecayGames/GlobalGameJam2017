package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.util.PixmapCache;

/**
 * This component extends the drawable component and it only draws a single static image.
 */
public class LevelImageComponent extends AbstractComponent {

    public LevelImageComponent(MyGameObject obj) {
        super(obj);
    }
}
