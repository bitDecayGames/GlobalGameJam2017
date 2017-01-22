package com.bitdecay.game.room;


import com.badlogic.gdx.Screen;
import com.bitdecay.game.gameobject.MyGameObjectFactory;
import com.bitdecay.game.screen.CutsceneScreen;
import com.bitdecay.game.system.DrawSystem;
import com.bitdecay.game.system.ManageAnimationSystem;
import com.bitdecay.game.system.RemovalSystem;
import com.bitdecay.game.system.TimerSystem;

/**
 * This room is for showing cutscenes
 */
public class CutsceneRoom extends AbstractRoom {

    public CutsceneRoom(CutsceneScreen screen) {
        super(screen);

        // ////////////////////////////////////////////////
        // systems must be added before game objects
        // ////////////////////////////////////////////////
        new RemovalSystem(this);
        new TimerSystem(this);
        new DrawSystem(this, true);
        new ManageAnimationSystem(this);

        // ////////////////////////////////////////////////
        // put game objects here
        // ////////////////////////////////////////////////
        gobs.addAll(MyGameObjectFactory.cutscenes(screen));

        // this is required to be at the end here so that the systems have the latest gobs
        systemManager.cleanup();
    }

    @Override
    public void setScreen(Screen screen) {
    }
}
