package com.bitdecay.game.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.*;
import com.bitdecay.game.util.VectorMath;

import java.util.ArrayList;
import java.util.List;

/**
 * The idea here is to provide a single place for you to add your game objects.  You know that the "Player" game object will have a PositionComponent, a SizeComponent, and a CameraFollowComponent.  So in a static method (maybe called buildPlayer) you want to create a generic MyGameObject and populate it with the correct components.
 */
public final class MyGameObjectFactory {
    private MyGameObjectFactory(){}

    public static MyGameObject demoThing(){
        MyGameObject t = new MyGameObject();
        t.addComponent(new PositionComponent(t, 0, 0));
        t.addComponent(new SizeComponent(t, 10, 10));
        return t;
    }

    public static MyGameObject ship(){
        MyGameObject t = new MyGameObject();
        t.addComponent(new PlayerInputComponent(t, 0.75f));
//        t.addComponent(new DebugCircleComponent(t, com.badlogic.gdx.graphics.Color.GREEN, 25));
        t.addComponent(new PositionComponent(t, 10, 20));
        t.addComponent(new RotationComponent(t, 0));
        t.addComponent(new DesiredDirectionComponent(t, 0, 0.01f));
        t.addComponent(new SizeComponent(t, 49, 26));
        t.addComponent(new OriginComponent(t));
        t.addComponent(new CameraFollowComponent(t));
        t.addComponent(new PredictiveCameraFollowComponent(t)); // need two of these
        t.addComponent(new PredictiveCameraFollowComponent(t)); // need two of these
        t.addComponent(new VelocityComponent(t,0.3f,0));
        t.addComponent(new StaticImageComponent(t, "player/sub").setReactsToSonar(true));
        t.addComponent(new CollisionComponent(t));
        t.addComponent(new ProximityIlluminationComponent(t));
        t.addComponent(new AccelerationComponent(t));
        return t;
    }

    public static MyGameObject mine(){
        MyGameObject t = new MyGameObject();
        t.addComponent(new DebugCircleComponent(t, com.badlogic.gdx.graphics.Color.GREEN, 25));
        t.addComponent(new PositionComponent(t, 50, 20));
        t.addComponent(new SizeComponent(t, 12, 14 ));
        t.addComponent(new OriginComponent(t));
        t.addComponent(new StaticImageComponent(t, "enemies/mine/mine").setReactsToSonar(true));
        t.addComponent(new CollisionComponent(t));
        t.addComponent(new RandomOrbitComponent(t, 50, 20 , 2.5f ));
        t.addComponent(new VelocityComponent(t));
        t.addComponent(new AccelerationComponent(t));
        return t;
    }

    public static MyGameObject ping(Vector2 startPos) {
        MyGameObject pingObj = new MyGameObject();
        pingObj.addComponent(new PositionComponent(pingObj, startPos.x, startPos.y));
        pingObj.addComponent(new SonarPingComponent(pingObj));
//        pingObj.addComponent(new DebugCircleComponent(pingObj, com.badlogic.gdx.graphics.Color.GREEN, 25));
        pingObj.addComponent(new RotationComponent(pingObj, 0));

        return pingObj;
    }

    public static List<MyGameObject> demoBackgrounds(){
        List<MyGameObject> gobs = new ArrayList<>();
        for (int i = 0; i < 11; i ++){
            MyGameObject o = new MyGameObject();
            o.addComponent(new PositionComponent(o, 600 * i, 0));
            o.addComponent(new SizeComponent(o, 600, 600));
            o.addComponent(new StaticImageComponent(o, "levelSegments/A/" + i).setReactsToSonar(true));
            gobs.add(o);
        }
        return gobs;
    }
    public static MyGameObject torpedo(float x, float y, float rot){
        MyGameObject t = new MyGameObject();
        Vector2 direction = VectorMath.degreesToVector2(rot).nor();
        Vector2 perp = new Vector2(direction.y, -direction.x);
        System.out.println("Perper: " + perp + "  direciton: " + direction);
        t.addComponent(new DebugCircleComponent(t, com.badlogic.gdx.graphics.Color.RED, 25));
        t.addComponent(new DespawnableComponent(t));
        t.addComponent(new PositionComponent(t, x, y));
        t.addComponent(new SizeComponent(t, 21, 4));
        StaticImageComponent imageComponent = new StaticImageComponent(t, "player/torpedo/0");
        imageComponent.reactsToSonar = true;
        t.addComponent(imageComponent);
        RotationComponent rotationComponent = new RotationComponent(t, rot);
        rotationComponent.rotationFromVelocity = false;
        t.addComponent(rotationComponent);
        t.addComponent(new VelocityComponent(t, 0.1f, 0f));
        t.addComponent(new TimerComponent(t, 0.25f, myGameObject ->
            myGameObject.forEachComponentDo(RotationComponent.class, rotat -> {
                myGameObject.addComponent(new AccelerationComponent(myGameObject, rotat.toVector2().scl(0.45f)));
                myGameObject.addComponent(new ImpulseComponent(myGameObject, perp.cpy().scl(-2.5f)));
                    }
        )));
        t.addComponent(new DragComponent(t, 0.09f, 0.4f));
        t.addComponent(new ImpulseComponent(t, perp.cpy().scl(4)));

        return t;
    }
}
