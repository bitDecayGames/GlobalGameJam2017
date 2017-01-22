package com.bitdecay.game.system;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.CollisionCirclesComponent;
import com.bitdecay.game.component.CollisionResponseComponent;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.RotationComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monday on 1/21/2017.
 */
public class CollisionCircleSystem extends AbstractForEachUpdatableSystem {
    List<MyGameObject> collisionObjects = new ArrayList<>();

    public CollisionCircleSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    public void before() {
        collisionObjects.clear();
//        room.shapeRenderer.setProjectionMatrix(room.camera.combined);
//        room.shapeRenderer.begin();
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        collisionObjects.add(gob);
        gob.forEachComponentDo(CollisionResponseComponent.class, colRep ->colRep.collidedWith.clear());
    }

    @Override
    public void after() {
        for (MyGameObject object1 : collisionObjects) {
            for (MyGameObject object2 : collisionObjects) {
                if (object1 != object2) {
                    if (checkCollision(object1, object2)) {
                        object1.forEachComponentDo(CollisionResponseComponent.class, colRep -> colRep.collidedWith.add(object2));
                        object2.forEachComponentDo(CollisionResponseComponent.class, colRep -> colRep.collidedWith.add(object1));
                    }
                }
            }
        }
//        room.shapeRenderer.end();
    }

    private boolean checkCollision(MyGameObject object1, MyGameObject object2) {

        boolean collisionFound = false;

        PositionComponent pos1;
        PositionComponent pos2;

        RotationComponent rot1 = null;
        RotationComponent rot2 = null;

        CollisionCirclesComponent geom1;
        CollisionCirclesComponent geom2;

        pos1 = object1.getComponent(PositionComponent.class).get();
        pos2 = object2.getComponent(PositionComponent.class).get();

        if (object1.hasComponent(RotationComponent.class)) {
            rot1 = object1.getComponent(RotationComponent.class).get();
        }

        if (object2.hasComponent(RotationComponent.class)) {
            rot2 = object2.getComponent(RotationComponent.class).get();
        }

        geom1 = object1.getComponent(CollisionCirclesComponent.class).get();
        geom2 = object2.getComponent(CollisionCirclesComponent.class).get();

        for (Circle circle1 : geom1.collisionCircles) {
            circle1 = new Circle(circle1);
            if (rot1 != null) {
                Vector2 rotated = getRotatedPoint(circle1.x, circle1.y, rot1.toRadians(), Vector2.Zero);
                circle1.x = rotated.x;
                circle1.y = rotated.y;
            }
            circle1.x += pos1.x;
            circle1.y += pos1.y;

//            room.shapeRenderer.circle(circle1.x, circle1.y, circle1.radius);

            for (Circle circle2 : geom2.collisionCircles) {
                circle2 = new Circle(circle2);
                if (rot2 != null) {
                    Vector2 rotated = getRotatedPoint(circle2.x, circle2.y, rot2.toRadians(), Vector2.Zero);
                    circle2.x = rotated.x;
                    circle2.y = rotated.y;
                }
                circle2.x += pos2.x;
                circle2.y += pos2.y;

//                room.shapeRenderer.circle(circle2.x, circle2.y, circle2.radius);

                if (new Vector2(circle1.x, circle1.y).sub(circle2.x, circle2.y).len() < circle1.radius + circle2.radius) {
                    collisionFound = true;
                }
            }
        }
        return collisionFound;
    }

    /**
     * Given a working point and a reference point, rotate the working point by
     * the given angle around the reference point.
     */
    private static Vector2 getRotatedPoint(float x, float y, float angle, Vector2 around) {
        Vector2 rotated = new Vector2(x, y);
        double s = Math.sin(angle);
        double c = Math.cos(angle);

        // translate point back to origin:
        rotated.x -= around.x;
        rotated.y -= around.y;

        // rotate point
        double xnew = rotated.x * c - rotated.y * s;
        double ynew = rotated.x * s + rotated.y * c;

        // translate point back:
        rotated.x = (float) (xnew + around.x);
        rotated.y = (float) (ynew + around.y);
        return rotated;
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PositionComponent.class, CollisionCirclesComponent.class, CollisionResponseComponent.class);
    }
}
