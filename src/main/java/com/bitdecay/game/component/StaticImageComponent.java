package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.util.PixmapCache;

/**
 * This component extends the drawable component and it only draws a single static image.
 */
public class StaticImageComponent extends DrawableComponent {

    public TextureRegion image;
    public Pixmap pixmap;
    public String name;

    public StaticImageComponent(MyGameObject obj, String name) {
        super(obj);
        this.name = name;
        image = MyGame.ATLAS.findRegion(name);
    }

    @Override
    public TextureRegion image() {
        return image;
    }

    @Override
    public String name() {
        return name;
    }

    // USE WITH CAUTION YA SCALLYWAGS
    public StaticImageComponent prepareData() {
        pixmap = PixmapCache.init(image.getTexture());
        return this;
    }
}
