package com.bitdecay.game.system.background;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.ludum.dare.LudumDareGame;

public class BackgroundUtil {
    public static TextureRegion getBackground(String name) {
        return LudumDareGame.atlas.findRegion("bg/" + name);
    }
}