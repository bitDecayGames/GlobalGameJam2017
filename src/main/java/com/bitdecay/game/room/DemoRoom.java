package com.bitdecay.game.room;


import com.bitdecay.game.Launcher;
import com.bitdecay.game.gameobject.MyGameObjectFactory;
import com.bitdecay.game.screen.GameScreen;
import com.bitdecay.game.system.*;

/**
 * The demo room is just a super simple example of how to add systems and game objects to a room.
 */
public class DemoRoom extends AbstractRoom {

    public DemoRoom(GameScreen gameScreen) {
        super(gameScreen);

        // ////////////////////////////////////////////////
        // systems must be added before game objects
        // ////////////////////////////////////////////////
        new InitializationSystem(this);
        new TimerSystem(this);
        new CameraUpdateSystem(this);
        new CameraPredictiveSystem(this, 500);
        new RespawnSystem(this, Integer.MIN_VALUE, Integer.MAX_VALUE, -1000, Integer.MAX_VALUE);
        new DespawnSystem(this, Integer.MIN_VALUE, Integer.MAX_VALUE, -1000, Integer.MAX_VALUE);
        new DrawSystem(this);
        new RemovalSystem(this);
        new PlayerInputSystem(this);
        new DesiredDirectionSystem(this);
        new VelocitySystem(this);
        new SonarPingSystem(this);
        new ProximityIlluminationSystem(this);
        new AccelerationSystem(this);
        new RemovalSystem(this);
        new RotationFromVelocitySystem(this);
        new ShapeDrawSystem(this);
        new CollisionCircleSystem(this);
        new CircleLevelCollisionSystem(this);
        new CollisionResolutionSystem(this);
        new RandomOrbitSystem(this);
        new TextDrawSystem(this);
        new GlobalInputSystem(this);
        new ImpulseSystem(this);
        new DragSystem(this);
        new ManageAnimationSystem(this);

        // ////////////////////////////////////////////////
        // put game objects here
        // ////////////////////////////////////////////////
        gobs.add(MyGameObjectFactory.ship());
        gobs.add(MyGameObjectFactory.splashText("GO", 10, 1500, 10, 10));
        gobs.add(MyGameObjectFactory.globalInputListener(this));
        gobs.add(MyGameObjectFactory.mine());
        camera.maxZoom = 0.1f;
        gobs.add(MyGameObjectFactory.jelly());
        gobs.addAll(MyGameObjectFactory.demoBackgrounds(Launcher.conf.getInt("levelSegments.totalNumberOfBackgrounds")));

        // this is required to be at the end here so that the systems have the latest gobs
        systemManager.cleanup();
    }
}
