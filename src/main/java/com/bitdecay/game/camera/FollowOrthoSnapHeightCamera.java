package com.bitdecay.game.camera;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * The FollowOrthoCamera will try and follow a list of points.  As the points spread out, the camera will zoom out.  As they get closer together, the camera will zoom in.  You have to give the camera a new list of points to follow every update step.
 */
public class FollowOrthoSnapHeightCamera extends AbstractFollowOrthoCamera {
    private float snapSpeed = 1f;

    private final float aspectRatio;
    private final float worldCameraHeightDiv2;
    private final float worldCameraHeightTopSnap;
    private final float worldCameraHeightBottomSnap;

    private List<Vector2> pointsToFollow;

    public FollowOrthoSnapHeightCamera(float screenWidth, float screenHeight, float zoom, float worldCameraHeightTopSnap, float worldCameraHeightBottomSnap, float snapSpeed){
        super(screenWidth, screenHeight);
        this.zoom = zoom;
        this.update(); // call this to apply the zoom
        aspectRatio = screenWidth / screenHeight;

        Vector3 topLeftWorld = unproject(new Vector3(0, 0, 0));
        Vector3 bottomRightWorld = unproject(new Vector3(screenWidth, screenHeight, 0));

        worldCameraHeightDiv2 = Math.abs(topLeftWorld.y - bottomRightWorld.y) / 2f;
        System.out.println("Height : " + (worldCameraHeightDiv2 * 2));

        this.worldCameraHeightTopSnap = worldCameraHeightTopSnap;
        this.worldCameraHeightBottomSnap = worldCameraHeightBottomSnap;

        this.snapSpeed = snapSpeed;

        resetPositionToWithinBoundaries();
    }

    @Override
    public void addFollowPoint(Vector2 point){
        pointsToFollow.add(point);
    }

    @Override
    public void update(){
        update(1f / 60f);
    }

    @Override
    public void update(float delta){
        super.update();
        if (pointsToFollow == null) pointsToFollow = new ArrayList<>();

        if (pointsToFollow.size() > 0) {
            travelToTarget(targetFromAveragePointPosition());
            pointsToFollow.clear();
        }
        resetPositionToWithinBoundaries();
    }

    private void resetPositionToWithinBoundaries(){
        if (tooFarUp()) translate(0, worldCameraHeightTopSnap - (position.y + worldCameraHeightDiv2));
        else if (tooFarDown()) translate(0, worldCameraHeightBottomSnap - (position.y - worldCameraHeightDiv2));
    }

    private void travelToTarget(Vector2 target){
        float transX = (target.x - position.x) * snapSpeed;
        float transY = (target.y - position.y) * snapSpeed;
        //if (tooFarUp(transY) || tooFarDown(transY)) transY = 0;
        translate(transX, transY);
    }

    private Vector2 targetFromAveragePointPosition(){
        Vector2 sum = new Vector2(0, 0);
        pointsToFollow.forEach(sum::add);
        return sum.scl(1f / pointsToFollow.size());
    }

    private boolean tooFarUp(float addition){
        return addition + position.y + worldCameraHeightDiv2 > worldCameraHeightTopSnap;
    }

    private boolean tooFarUp(){return tooFarUp(0);}

    private boolean tooFarDown(float addition){
        return addition + position.y - worldCameraHeightDiv2 < worldCameraHeightBottomSnap;
    }

    private boolean tooFarDown(){return tooFarDown(0);}
}