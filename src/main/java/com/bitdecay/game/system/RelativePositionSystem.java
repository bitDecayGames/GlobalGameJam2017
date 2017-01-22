package com.bitdecay.game.system;

import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.RelativePositionComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

public class RelativePositionSystem extends AbstractForEachUpdatableSystem{

    public RelativePositionSystem(AbstractRoom room){
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(RelativePositionComponent.class, RelativePositionComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(PositionComponent.class, pos -> gob.forEachComponentDo(RelativePositionComponent.class, rel -> rel.other.forEachComponentDo(PositionComponent.class, otherPos -> {
            pos.x = otherPos.x + rel.x;
            pos.y = otherPos.y + rel.y;
        })));
    }
}
