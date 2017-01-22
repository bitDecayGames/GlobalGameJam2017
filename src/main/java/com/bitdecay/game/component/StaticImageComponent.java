package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.gameobject.MyGameObject;

/**
 * This component extends the drawable component and it only draws a single static image.
 */
public class StaticImageComponent extends DrawableComponent {

    private TextureRegion image;
    public Pixmap pixmap;

    public StaticImageComponent(MyGameObject obj, String name) {
        super(obj);
        image = MyGame.ATLAS.findRegion(name);
    }

    @Override
    public TextureRegion image() {
        return image;
    }

    // USE WITH CAUTION YA SCALLYWAGS
    public StaticImageComponent prepareData() {
        TextureData textureData = image.getTexture().getTextureData();
        textureData.prepare();
        pixmap = textureData.consumePixmap();
        return this;
    }
}
