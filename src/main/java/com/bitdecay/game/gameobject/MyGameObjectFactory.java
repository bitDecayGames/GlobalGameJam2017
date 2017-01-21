package com.bitdecay.game.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.component.*;

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
        t.addComponent(new DebugCircleComponent(t, com.badlogic.gdx.graphics.Color.GREEN, 25));
        t.addComponent(new PositionComponent(t, 10, 20));
        t.addComponent(new RotationComponent(t, 0));
        t.addComponent(new DesiredRotationComponent(t, 0, 0.01f));
        t.addComponent(new SizeComponent(t, 49, 26));
        t.addComponent(new OriginComponent(t));
        t.addComponent(new CameraFollowComponent(t));
        t.addComponent(new PredictiveCameraFollowComponent(t));
        t.addComponent(new ThrustComponent(t, 1f));
        t.addComponent(new StaticImageComponent(t, "player/sub"));
        t.addComponent(new CollisionComponent(t));
        return t;
    }

    public static MyGameObject mine(){
        MyGameObject t = new MyGameObject();
        t.addComponent(new DebugCircleComponent(t, com.badlogic.gdx.graphics.Color.GREEN, 25));
        t.addComponent(new PositionComponent(t, 50, 20));
        t.addComponent(new SizeComponent(t, 12, 14 ));
        t.addComponent(new OriginComponent(t));
        t.addComponent(new StaticImageComponent(t, "enemies/mine/mine"));
        t.addComponent(new CollisionComponent(t));
        t.addComponent(new ThrustComponent(t, 1f));
        return t;
    }
}
