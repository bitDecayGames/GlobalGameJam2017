package com.bitdecay.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Created by Monday on 1/22/2017.
 */
public class ParticleEffectComponent extends SelfDrawComponent {

    public ParticleEffect fx;
    public Vector2 offset = Vector2.Zero;

    public ParticleEffectComponent(MyGameObject obj, String particleName, String particleFolder) {
        super(obj);
        reactsToSonar = false;
        fx = new ParticleEffect();
        fx.load(Gdx.files.internal(particleName), Gdx.files.internal(particleFolder));
        fx.start();
    }

    @Override
    public void drawToBatch(SpriteBatch batch) {
        fx.draw(batch);
    }

    @Override
    public String name() {
        return null;
    }
}
