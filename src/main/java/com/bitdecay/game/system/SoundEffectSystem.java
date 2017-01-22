package com.bitdecay.game.system;

import com.bitdecay.game.component.RemovableTimerComponent;
import com.bitdecay.game.component.SoundEffectComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.SoundLibrary;

/**
 * Created by jacob on 1/22/17.
 */
public class SoundEffectSystem extends AbstractForEachUpdatableSystem{

    public SoundEffectSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(SoundEffectComponent.class, sound -> {
            if(gob.hasComponent(SoundEffectComponent.class) && !sound.played) {
                SoundLibrary.playSound(sound.name);
                sound.played = true;
                gob.addComponent(new RemovableTimerComponent(gob, sound.duration, myGameObject ->
                myGameObject.removeComponent(sound)));
            }
        });
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(SoundEffectComponent.class);
    }
}
