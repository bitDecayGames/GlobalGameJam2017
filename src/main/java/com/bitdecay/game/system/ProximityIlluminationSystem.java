package com.bitdecay.game.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.ProximityIlluminationComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;

import java.util.ArrayList;
import java.util.List;

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
        DrawSystem.proximityShader.begin();
        List<Float> points = new ArrayList<>();
        List<Float> clearRadii = new ArrayList<>();
        List<Float> fadeRadii = new ArrayList<>();
        gobs.forEach(gob -> {
            gob.forEachComponentDo(PositionComponent.class, pos -> {
                gob.forEachComponentDo(ProximityIlluminationComponent.class, prox -> {
                    Vector3 projected = camera.project(new Vector3(pos.x + 20, pos.y, 0));
//                    DrawSystem.proximityShader.setUniformf("v_center", projected.x, projected.y);
                    points.add(projected.x);
                    points.add(projected.y);

                    clearRadii.add(prox.fullBrightnessRadius);
                    fadeRadii.add(prox.fadeRange);
                });
            });
        });

        // we can track up to 20 lights
        int numLights = Math.min(clearRadii.size(), 20);

        // each light has an x and a y
        int numPoints = numLights * 2;

        DrawSystem.proximityShader.setUniformi("i_lightsPassed", numLights);

        DrawSystem.proximityShader.setUniform1fv("f_points[0]", toFloatArray(points, numPoints), 0, numPoints);

        DrawSystem.proximityShader.setUniform1fv("f_clearRadii[0]", toFloatArray(clearRadii, numLights), 0, numLights);
        DrawSystem.proximityShader.setUniform1fv("f_fadeRadii[0]", toFloatArray(fadeRadii, numLights), 0, numLights);

        DrawSystem.proximityShader.end();
    }

    private float[] toFloatArray(List<Float> list, int numElements) {
        int index = 0;
        float[] pointsAsArray = new float[numElements];
        for (Float point : list) {
            if (index >= numElements) {
                break;
            }

            pointsAsArray[index++] = point;
        }
        return pointsAsArray;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        // no op
    }

}
