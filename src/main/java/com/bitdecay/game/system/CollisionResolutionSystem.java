package com.bitdecay.game.system;

import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * Created by Luke on 1/21/2017.
 */
public class CollisionResolutionSystem extends AbstractForEachUpdatableSystem{

    public CollisionResolutionSystem(AbstractRoom room){
        super(room);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(CollidedWithLevelComponent.class, colLevel -> {
            gob.addComponent(new RemoveNowComponent(gob));
        });

        gob.forEachComponentDo(CollisionResponseComponent.class, colRep ->{
               if(colRep.collidedWith.isEmpty()) {
                  return;
               }
               for(MyGameObject collidedGob :colRep.collidedWith ){
                   collidedGob.forEachComponentDo(ObjectNameComponent.class, nameComp ->{
                       switch (nameComp.objectName){
                           case "mine":
                               //remove mine from the game, ie.SPLOSION!!
                               collidedGob.addComponent(new RemoveNowComponent(collidedGob));
                               //remove player from game ie.death
                               gob.addComponent(new RemoveNowComponent(gob));
                               break;
//                           case "torpedo":
//                               //remove torpedo from the game, ie.SPLOSION!!
//                               collidedGob.addComponent(new RemoveNowComponent(collidedGob));
//                               //remove mine from game ie.death
//                               gob.addComponent(new RemoveNowComponent(gob));
//                               break;
                       }
                   });
               };
            }
        );
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PositionComponent.class, CollisionCirclesComponent.class, CollisionResponseComponent.class) ||
                gob.hasComponent(CollidedWithLevelComponent.class);
    }
}
