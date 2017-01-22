package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Created by Monday on 1/21/2017.
 */
public class ImageCollisionComponent extends AbstractComponent {

    private TextureRegion image;

    public ImageCollisionComponent(MyGameObject obj, String name) {
        super(obj);
        image = MyGame.ATLAS.findRegion(name);
    }
}
