package com.bitdecay.game.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.RemoveNowComponent;
import com.bitdecay.game.component.SonarPingComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;

/**
 * Created by Monday on 1/21/2017.
 */
public class SonarPingSystem extends AbstractDrawableSystem {
    public float propagationSpeed = 7f;
    public float maxSonarRange = 4000f;

    public SonarPingSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PositionComponent.class, SonarPingComponent.class);
    }

    @Override
    public void preDraw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        DrawSystem.pingShader.begin();
        gobs.forEach(gob -> {
            gob.forEachComponentDo(SonarPingComponent.class, ping -> {
                ping.radius += propagationSpeed;
                if (ping.radius <= maxSonarRange){
                    DrawSystem.pingShader.setUniformf("f_sweepRadius", ping.radius);
                } else {
                    gob.addComponent(new RemoveNowComponent(gob));
                    DrawSystem.pingShader.setUniformf("f_sweepRadius", 0);
                }
            });

            gob.forEachComponentDo(PositionComponent.class, pos -> {
                Vector3 projected = camera.project(new Vector3(pos.x, pos.y, 0));
                DrawSystem.pingShader.setUniformf("v_center",projected.x, projected.y);
            });
        });
        DrawSystem.pingShader.end();
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        // no op
    }
}
