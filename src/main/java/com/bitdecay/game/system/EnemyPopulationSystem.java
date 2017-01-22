package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.gameobject.MyGameObjectFactory;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Maveric on 1/22/2017.
 */
public class EnemyPopulationSystem extends AbstractUpdatableSystem {

    HashSet<Integer> populatedSegments = new HashSet<>();
    int enemiesPerSegment = 20;

    public EnemyPopulationSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(LevelImageComponent.class, PositionComponent.class, StaticImageComponent.class) || gob.hasComponent(PlayerInputSystem.class);
    }

    @Override
    public void update(float delta) {
        Vector2 subPos = new Vector2();
        ArrayList<StaticImageComponent> levelSegments = new ArrayList<>();

        room.gobs.forEach(gob -> gob.forEachComponentDo(PlayerInputComponent.class, pi -> {
            gob.forEachComponentDo(PositionComponent.class, subPosCmp -> {
                subPos.set(subPosCmp.toVector2());
            });
        }));

        room.gobs.forEach(gob -> gob.forEachComponentDo(LevelImageComponent.class, level -> {
            gob.forEachComponentDo(StaticImageComponent.class, levelImageComponent -> {
                levelSegments.add(levelImageComponent);
            });
        }));

        int segmentAheadOfSub = findImageIndexAheadOfSub((int) subPos.x);
        if (checkIfAllowedToPopulateEnemies(segmentAheadOfSub)) {
            StaticImageComponent levelSegment = levelSegments.get(segmentAheadOfSub);
            cleanupOldEnemies(segmentAheadOfSub, levelSegment);
            for (int i = 0; i < enemiesPerSegment; i++) {
                Vector2 coordinatesToAddEnemy = getValidSegmentCoordinates(segmentAheadOfSub, levelSegment);
                System.out.println("Spawning jelly at (" + coordinatesToAddEnemy.x + ", " + coordinatesToAddEnemy.y + ")");
                addEnemyToRoom(coordinatesToAddEnemy);
            }
        }
    }

    public int findImageIndexAheadOfSub(int subPosX) {
        int segmentWidth = 600;
        int subSegmentLocation = subPosX / segmentWidth;
        return subSegmentLocation+1;
    }

    public boolean checkIfAllowedToPopulateEnemies(int subSegment) {

        int segmentToPopulate = subSegment;

        if(!populatedSegments.contains(segmentToPopulate)) {
            populatedSegments.add(segmentToPopulate);
            return true;
        }
        return false;
    }

    public void addEnemyToRoom(Vector2 coordinatesToAddEnemy) {
        MyGameObject jelly = MyGameObjectFactory.jelly();
        jelly.cleanup();
        jelly.forEachComponentDo(PositionComponent.class, pos -> {
          pos.x = coordinatesToAddEnemy.x;
          pos.y = coordinatesToAddEnemy.y;
        });
        room.gobs.add(jelly);
    }

    public Vector2 getValidSegmentCoordinates(int segmentIndex, StaticImageComponent levelSegment) {
        double x = Math.random() * levelSegment.image().getRegionWidth();
        double y = Math.random() * levelSegment.image().getRegionHeight();

        x = x + (segmentIndex * levelSegment.image().getRegionWidth());

        return new Vector2((float)x, (float)y);
    }

    public void cleanupOldEnemies(int segmentIndex, StaticImageComponent levelSegment) {
        double xCutoff = (segmentIndex-1) * levelSegment.image().getRegionWidth();

        room.gobs.forEach(gob -> {
            gob.forEachComponentDo(ObjectNameComponent.class, obName -> {
                gob.forEachComponentDo(PositionComponent.class, pos -> {
                    if(obName.objectName.equals("jelly")){
                        System.out.println("Found a jelly! Comparing its location");
                        System.out.println("Location: " + pos.x + ". Cutoff: " + xCutoff);
                        if (pos.x <= xCutoff) {
                            System.out.println("Removing jelly from location (" + pos.x + ", " + pos.y + ")");
                            gob.addComponent(new RemoveNowComponent(gob));
                        }
                    }
                });
            });
        });
    }
}
