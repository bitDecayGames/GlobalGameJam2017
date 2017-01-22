package com.bitdecay.game.component;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.gameobject.MyGameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This tracks a list of positions
 */
public class RespawnRecorderComponent extends AbstractComponent {
    public int positionsToTrack = 1;
    public List<Vector2> positions = new ArrayList<>();

    public RespawnRecorderComponent(MyGameObject obj){super(obj);}
    public RespawnRecorderComponent(MyGameObject obj, int positionsToTrack){
        super(obj);
        this.positionsToTrack = positionsToTrack;
    }

    public RespawnRecorderComponent addPoint(Vector2 pos){
        positions.add(pos);
        if (positions.size() > positionsToTrack) positions.remove(0);
        return this;
    }

    public Vector2 last(){
        return positions.get(0);
    }
}
