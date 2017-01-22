package com.bitdecay.game.system;

import com.bitdecay.game.component.AnimationComponent;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.StaticImageComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * The draw system is one of the few systems that extends AbstractSystem directly.  The reason for this is that there is a call to spritebatch begin and end within the process method.  You will notice however that the forEachComponentDo method is called in between the begin and end calls.  When you extend the AbstractForEachUpdatableSystem, that forEachComponentDo call isn't necessary because it happens behind the scenes in the AbstractForEachUpdatableSystem.process method.
 */
public class ManageAnimationSystem extends AbstractForEachUpdatableSystem {

    public ManageAnimationSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(AnimationComponent.class, animation -> {
            gob.forEachComponentDo(PositionComponent.class, pos -> {
                animation.elapsedTime += delta;
                gob.forEachComponentDo(StaticImageComponent.class, image -> {
                    image.image = animation.animationFrames.getKeyFrame(animation.elapsedTime);
                });
            });
        });
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(StaticImageComponent.class, AnimationComponent.class, PositionComponent.class);
    }
}
