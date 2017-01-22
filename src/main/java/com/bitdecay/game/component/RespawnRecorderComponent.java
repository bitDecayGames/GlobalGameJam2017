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
    public List<Vector2> velocities = new ArrayList<>();
    public List<Float> desiredRotations = new ArrayList<>();

    public RespawnRecorderComponent(MyGameObject obj){super(obj);}
    public RespawnRecorderComponent(MyGameObject obj, int positionsToTrack){
        super(obj);
        this.positionsToTrack = positionsToTrack;
    }

    public RespawnRecorderComponent addPoint(Vector2 pos, Vector2 vel, float desiredRotation){
        positions.add(pos);
        velocities.add(vel);
        desiredRotations.add(desiredRotation);
        if (positions.size() > positionsToTrack) positions.remove(0);
        if (velocities.size() > positionsToTrack) velocities.remove(0);
        if (desiredRotations.size() > positionsToTrack) desiredRotations.remove(0);
        return this;
    }

    public Vector2 lastPos(){
        return positions.size() > 0 ? positions.get(0) : null;
    }

    public Vector2 lastVel(){
        return velocities.get(0);
    }

    public float lastDesiredRotation(){
        return desiredRotations.get(0);
    }
}
