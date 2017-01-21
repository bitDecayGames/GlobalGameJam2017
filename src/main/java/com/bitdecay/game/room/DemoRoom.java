package com.bitdecay.game.room;


import com.bitdecay.game.gameobject.MyGameObject;
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
        new ShapeDrawSystem(this);
        new DrawSystem(this);
        new TextDrawSystem(this);
        new RemovalSystem(this);
        new PlayerInputSystem(this);
        new DesiredRotationSystem(this);
        new ConstantThrustSystem(this);
        new GlobalInputSystem(this);

        // ////////////////////////////////////////////////
        // put game objects here
        // ////////////////////////////////////////////////
        this.gobs.add(MyGameObjectFactory.ship());
        this.gobs.add(MyGameObjectFactory.splashText("GO", 10, 1500));
        this.gobs.add(MyGameObjectFactory.globalInputListener(this));

        // this is required to be at the end here so that the systems have the latest gobs
        systemManager.cleanup();
    }
}
