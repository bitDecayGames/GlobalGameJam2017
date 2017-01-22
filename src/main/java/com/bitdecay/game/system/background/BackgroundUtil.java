package com.bitdecay.game.system.background;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.MyGame;

public class BackgroundUtil {
    public static TextureRegion getBackground(String name) {
        return MyGame.ATLAS.findRegion(name);
    }
}