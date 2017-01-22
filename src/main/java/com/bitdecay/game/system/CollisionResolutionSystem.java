package com.bitdecay.game.system;

import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.GameObjectNames;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.GameUtil;

public class CollisionResolutionSystem extends AbstractForEachUpdatableSystem{

    public CollisionResolutionSystem(AbstractRoom room){
        super(room);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(CollidedWithLevelComponent.class, colLevel -> {
            if (gob.hasComponent(ObjectNameComponent.class)) {
                String name = gob.getComponent(ObjectNameComponent.class).get().objectName;
                switch (name) {
                    case GameObjectNames.JELLY:
                        GameUtil.generateDirection(gob);
                        return;
                    default:
                        // no-op
                }
            }

            gob.addComponent(new RemoveNowComponent(gob));
        });

        gob.forEachComponentDo(CollisionResponseComponent.class, colRep ->
            gob.forEachComponentDo(ObjectNameComponent.class, gobName -> {
               if(colRep.collidedWith.isEmpty()) {
                  return;
               }
               for(MyGameObject collidedGob : colRep.collidedWith){
                   collidedGob.forEachComponentDo(ObjectNameComponent.class, nameComp ->{
                       switch (nameComp.objectName){
                           case "mine":
                               //remove mine from the game, ie.SPLOSION!!
                               collidedGob.addComponent(new RemoveNowComponent(collidedGob));
                               //remove player from game ie.death
                               gob.addComponent(new RemoveNowComponent(gob));
                               break;
                           case "torpedo":
//                               //remove torpedo from the game, ie.SPLOSION!!
                               if(gobName.objectName != "ship") {
                                   collidedGob.addComponent(new RemoveNowComponent(collidedGob));
//                               //remove mine from game ie.death
                                   gob.addComponent(new RemoveNowComponent(gob));
                               }
                               break;
                           case GameObjectNames.JELLY:
                               switch (gobName.objectName) {
                                   case GameObjectNames.JELLY:
                                       GameUtil.generateDirection(gob);
                                       break;
                                   case "ship":
                                       gob.forEachComponentDo(CanPingComponent.class, cpc -> cpc.timer = PlayerInputSystem.PING_DELAY);
                                       break;
                                   default:
                                       // no-op
                               }
                               break;
                       }
                   });
               }
            })
        );
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PositionComponent.class, CollisionCirclesComponent.class, CollisionResponseComponent.class) ||
                gob.hasComponent(CollidedWithLevelComponent.class);
    }
}
