package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.gameobject.MyGameObjectFactory;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Maveric on 1/22/2017.
 */
public class DebugOutputSystem extends AbstractUpdatableSystem {

    long lastPrint;

    public DebugOutputSystem(AbstractRoom room) {
        super(room);
        lastPrint = 0;
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return false;
    }

    @Override
    public void update(float delta) {

        HashMap<String, Integer> mapObjectCount = new HashMap<>();

        if (System.currentTimeMillis() - lastPrint < 3000) {
            return;
        }

        lastPrint = System.currentTimeMillis();

        room.gobs.forEach(gob -> gob.forEachComponentDo(ObjectNameComponent.class, name -> {
            Integer count = mapObjectCount.get(name.objectName.name());
            if (count == null) count = 0;
            mapObjectCount.put(name.objectName.name(), count+1);
        }));

        printMap(mapObjectCount);
    }

    public void printMap(HashMap<String, Integer> objectMap) {
        for (Map.Entry<String, Integer> entry : objectMap.entrySet()) {
            System.out.println("Object Type = " + entry.getKey() + ", Count = " + entry.getValue());
        }
    }
}
