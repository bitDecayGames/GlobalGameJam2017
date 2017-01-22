package com.bitdecay.game.util;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.VelocityComponent;
import com.bitdecay.game.gameobject.MyGameObject;

import java.util.Random;

public class GameUtil {
    public static void generateDirection(MyGameObject gob) {
        if (gob.hasComponent(VelocityComponent.class)) {
            VelocityComponent velComp = gob.getComponent(VelocityComponent.class).get();
            velComp.x *= -1;
            velComp.y *= -1;
        } else {
            Vector2 targetV = VectorMath.degreesToVector2(generateRandomDegrees()).scl(0.5f);
            gob.addComponent(new VelocityComponent(gob, targetV.x, targetV.y));
        }
    }

    private static int generateRandomDegrees() {
        Random r = new Random();
        int low = 0;
        int high = 360;
        return r.nextInt(high-low) + low;
    }
}
