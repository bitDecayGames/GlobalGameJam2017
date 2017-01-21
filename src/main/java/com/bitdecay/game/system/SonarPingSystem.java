package com.bitdecay.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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
    public float propagationSpeed = 6f;
    public float maxSonarRange = 2000f;

    public static ShaderProgram pingShader;

    public SonarPingSystem(AbstractRoom room) {
        super(room);
        String vertexShader = Gdx.files.internal("shader/vertex_passthrough.glsl").readString();
        String fragShader = Gdx.files.internal("shader/frag_sweeper.glsl").readString();
        pingShader = new ShaderProgram(vertexShader, fragShader);
        if (!pingShader.isCompiled()) {
            throw new RuntimeException("Shader does not compile:\n" + pingShader.getLog());
        }
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PositionComponent.class, SonarPingComponent.class);
    }

    @Override
    public void preDraw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        pingShader.begin();
        gobs.forEach(gob -> {
            gob.forEachComponentDo(SonarPingComponent.class, ping -> {
                ping.radius += propagationSpeed;
                if (ping.radius > maxSonarRange){
                    log.debug("Removing PING");
                    gob.addComponent(new RemoveNowComponent(gob));
                }
                pingShader.setUniformf("f_sweepRadius", ping.radius);
            });

            gob.forEachComponentDo(PositionComponent.class, pos -> {
                Vector3 projected = camera.project(new Vector3(pos.x + 20, pos.y, 0));
                pingShader.setUniformf("v_center",projected.x, projected.y);
            });
        });
        pingShader.end();
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        // no op
    }
}
