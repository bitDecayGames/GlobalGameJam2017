package com.bitdecay.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;

/**
 * The draw system is one of the few systems that extends AbstractSystem directly.  The reason for this is that there is a call to spritebatch begin and end within the process method.  You will notice however that the forEachComponentDo method is called in between the begin and end calls.  When you extend the AbstractForEachUpdatableSystem, that forEachComponentDo call isn't necessary because it happens behind the scenes in the AbstractForEachUpdatableSystem.process method.
 */
public class ManageAnimationSystem extends AbstractDrawableSystem {

    public ManageAnimationSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(AnimationComponent.class, PositionComponent.class);
    }

    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        gobs.forEach(gob ->
            gob.forEachComponentDo(AnimationComponent.class, animation -> {
                gob.forEachComponentDo(PositionComponent.class, pos -> {
                    animation.elapsedTime += Gdx.graphics.getDeltaTime();
                    animation.update();
                });
            })
        );
        spriteBatch.end();

    }
}
