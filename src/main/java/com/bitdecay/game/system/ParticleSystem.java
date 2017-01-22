package com.bitdecay.game.system;

import com.bitdecay.game.component.ParticleEffectComponent;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.RotationComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * Created by Monday on 1/22/2017.
 */
public class ParticleSystem extends AbstractForEachUpdatableSystem {
    public ParticleSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(ParticleEffectComponent.class, particle -> {
            gob.forEachComponentDo(PositionComponent.class, position -> {
                gob.forEachComponentDo(RotationComponent.class, rotation -> {
                    particle.fx.setPosition(position.x + particle.offset.x, position.y + particle.offset.y);
                    particle.fx.update(delta);
                });
            });
        });
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(RotationComponent.class, PositionComponent.class, ParticleEffectComponent.class);
    }
}
