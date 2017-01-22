package com.bitdecay.game.system;

import com.badlogic.gdx.graphics.g2d.Animation;
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
            gob.forEachComponentDo(ObjectNameComponent.class, objectNameComponent -> {
                switch (objectNameComponent.objectName) {
                    case GameObjectNames.JELLY:
                       GameUtil.generateDirection(gob);
                       break;
                    case "ship":
                       explodePlayer(gob);
                       break;
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
                               explodePlayer(gob);
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

    private void explodePlayer(MyGameObject gob) {
        gob.removeComponent(AnimationComponent.class);
        gob.addComponent(new AnimationComponent(gob, "player/playerExplode", .05f, Animation.PlayMode.NORMAL));
        // this is changing the size of the ship. If we need to restart the level, make sure it's a brand new ship
        gob.forEachComponentDo(SizeComponent.class, size -> size.h *= 1.5);

        gob.removeComponent(CollisionCirclesComponent.class);
        gob.removeComponent(CollidedWithLevelComponent.class);
        gob.removeComponent(VelocityComponent.class);
        gob.removeComponent(PlayerInputComponent.class);

        gob.addComponent(new TimerComponent(gob, 2, gameobj -> gameobj.addComponent(new RemoveNowComponent(gameobj))));
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PositionComponent.class, CollisionCirclesComponent.class, CollisionResponseComponent.class) ||
                gob.hasComponent(CollidedWithLevelComponent.class);
    }
}
