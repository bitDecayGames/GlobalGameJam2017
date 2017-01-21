package com.bitdecay.game.room;


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
        new CameraPredictiveSystem(this, 100);
        new RespawnSystem(this, Integer.MIN_VALUE, Integer.MAX_VALUE, -1000, Integer.MAX_VALUE);
        new DespawnSystem(this, Integer.MIN_VALUE, Integer.MAX_VALUE, -1000, Integer.MAX_VALUE);
        new DrawSystem(this);
        new PlayerInputSystem(this);
        new DesiredDirectionSystem(this);
        new VelocitySystem(this);
        new SonarPingSystem(this);
        new AccelerationSystem(this);
        new RotationFromVelocitySystem(this);
        new ShapeDrawSystem(this);

        // ////////////////////////////////////////////////
        // put game objects here
        // ////////////////////////////////////////////////
        this.gobs.add(MyGameObjectFactory.ship());
        this.gobs.add(MyGameObjectFactory.mine());


        // this is required to be at the end here so that the systems have the latest gobs
        systemManager.cleanup();
    }
}
