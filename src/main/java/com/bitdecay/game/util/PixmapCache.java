package com.bitdecay.game.util;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;

import java.util.HashMap;

/**
 * Created by Monday on 1/21/2017.
 */
public class PixmapCache {
    public static HashMap<Texture, Pixmap> cache = new HashMap<>();

    public static Pixmap init(Texture texture) {
        if (!cache.containsKey(texture)) {
            TextureData textureData = texture.getTextureData();
            textureData.prepare();
            cache.put(texture, textureData.consumePixmap());
            System.out.println("LOADED NEW PIXMAP. NOW HAVE: " + cache.size());
        }
        return cache.get(texture);
    }
}
