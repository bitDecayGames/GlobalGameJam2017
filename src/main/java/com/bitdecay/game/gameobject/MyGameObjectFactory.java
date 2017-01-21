package com.bitdecay.game.gameobject;

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
        t.addComponent(new PlayerInputComponent(t));
        t.addComponent(new DebugCircleComponent(t, com.badlogic.gdx.graphics.Color.GREEN, 20));
        t.addComponent(new PositionComponent(t, 10, 20));
        t.addComponent(new RotationComponent(t, 0));
        t.addComponent(new DesiredRotationComponent(t, 0));
        t.addComponent(new SizeComponent(t, 20, 20));
        t.addComponent(new OriginComponent(t));
        t.addComponent(new CameraFollowComponent(t));
        t.addComponent(new PredictiveCameraFollowComponent(t));
        t.addComponent(new ThrustComponent(t, 1f));
        t.addComponent(new StaticImageComponent(t, "player/sub"));
        return t;
    }
}
