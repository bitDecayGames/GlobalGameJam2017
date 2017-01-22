package com.bitdecay.game.room;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.Launcher;
import com.bitdecay.game.camera.FollowOrthoSnapHeightCamera;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.gameobject.MyGameObjectFactory;
import com.bitdecay.game.screen.GameScreen;
import com.bitdecay.game.system.*;

/**
 * The demo room is just a super simple example of how to add systems and game objects to a room.
 */
public class DemoRoom extends AbstractRoom {

    public DemoRoom(GameScreen gameScreen) {
        super(gameScreen, new FollowOrthoSnapHeightCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), (float) Launcher.conf.getDouble("resolution.camera.zoom"), 600, 0, (float) Launcher.conf.getDouble("resolution.camera.snapSpeed")));

        // ////////////////////////////////////////////////
        // systems must be added before game objects
        // ////////////////////////////////////////////////
        new RemovalSystem(this);

        new InitializationSystem(this);
        new TimerSystem(this);
        new CameraUpdateSystem(this);
        new CameraPredictiveSystem(this, 400);
        new RespawnSystem(this);
        new DespawnSystem(this, Integer.MIN_VALUE, Integer.MAX_VALUE, -1000, Integer.MAX_VALUE);
        new DrawSystem(this);
        new PlayerInputSystem(this);
        new DesiredDirectionSystem(this);
        new VelocitySystem(this);
        new SonarPingSystem(this);
        new ProximityIlluminationSystem(this);
        new AccelerationSystem(this);
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
        new EnemyPopulationSystem(this);
        new RelativePositionSystem(this);
//        new KrakenSystem(this);
        new ParticleSystem(this);
        new DebugOutputSystem(this);

        // ////////////////////////////////////////////////
        // put game objects here
        // ////////////////////////////////////////////////
        gobs.addAll(MyGameObjectFactory.demoBackgrounds(Launcher.conf.getInt("levelSegments.totalNumberOfBackgrounds")));
        MyGameObject player = MyGameObjectFactory.ship(this, new Vector2(Launcher.conf.getInt("player.startingPosition.x"), Launcher.conf.getInt("player.startingPosition.y")));
        gobs.add(player);
//        gobs.addAll(MyGameObjectFactory._____RELEASE___THE___KRAKEN_____(player));
        gobs.add(MyGameObjectFactory.splashText("GO", 10, 1500, 10, 10));
        gobs.add(MyGameObjectFactory.globalInputListener(this));
        gobs.add(MyGameObjectFactory.jelly(300,300));

        // this is required to be at the end here so that the systems have the latest gobs
        systemManager.cleanup();
    }
}
