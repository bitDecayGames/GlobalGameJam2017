package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;

/**
 * This component extends the drawable component and it only draws a single static image.
 */
public class GlobalInputComponent extends AbstractComponent {

    AbstractRoom room;

    public GlobalInputComponent(MyGameObject obj, AbstractRoom room) {
        super(obj);
        this.room = room;
    }
}
