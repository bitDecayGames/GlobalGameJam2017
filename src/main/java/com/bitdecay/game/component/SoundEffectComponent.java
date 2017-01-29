package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Created by jacob on 1/22/17.
 */
public class SoundEffectComponent extends AbstractComponent{
    public String name;
    public float volumeScale;
    public long duration;
    public boolean played = false;

    public SoundEffectComponent(MyGameObject obj, String name, float volumeScale, long duration) {
        super(obj);
        this.name = name;
        this.volumeScale = volumeScale;
        this.duration = duration;
    }
}
