package com.bitdecay.game.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.ProximityIlluminationComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;

/**
 * Created by Monday on 1/21/2017.
 */
public class ProximityIlluminationSystem extends AbstractDrawableSystem {
    public ProximityIlluminationSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PositionComponent.class, ProximityIlluminationComponent.class);
    }

    @Override
    public void preDraw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        gobs.forEach(gob -> {
            gob.forEachComponentDo(PositionComponent.class, pos -> {
                gob.forEachComponentDo(ProximityIlluminationComponent.class, prox -> {
                    DrawSystem.proximityShader.begin();
                    Vector3 projected = camera.project(new Vector3(pos.x + 20, pos.y, 0));
                    DrawSystem.proximityShader.setUniformf("v_center", projected.x, projected.y);
                    DrawSystem.proximityShader.setUniformf("f_clearRadius", prox.fullBrightnessRadius);
                    DrawSystem.proximityShader.setUniformf("f_fadeRange", prox.fadeRange);
                    DrawSystem.proximityShader.end();
                });
            });
        });
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        // no op
    }

}
