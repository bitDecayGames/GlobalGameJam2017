package com.bitdecay.game.system;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.GeomUtils;
import com.bitdecay.game.util.VectorMath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monday on 1/21/2017.
 */
public class CircleLevelCollisionSystem extends AbstractForEachUpdatableSystem {
    List<MyGameObject> imageColliders = new ArrayList<>();
    List<MyGameObject> circleColliders = new ArrayList<>();


    public CircleLevelCollisionSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    public void before() {
        imageColliders.clear();
        circleColliders.clear();
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        if (isImageCollider(gob)) {
            imageColliders.add(gob);
        }

        if (isCircleCollider(gob)) {
            circleColliders.add(gob);
        }
    }

    @Override
    public void after() {
        for (MyGameObject imageGob : imageColliders) {
            PositionComponent imagePosition = imageGob.getComponent(PositionComponent.class).get();
            SizeComponent imageSize = imageGob.getComponent(SizeComponent.class).get();
            StaticImageComponent imageComp = imageGob.getComponent(StaticImageComponent.class).get();

            for (MyGameObject circleGob : circleColliders) {
                if (checkCircleGobCollision(imageComp.pixmap, imagePosition, imageSize, imageComp, circleGob)) {
                    circleGob.addComponent(new CollidedWithLevelComponent(circleGob));
                }
            }
        }
    }

    private boolean checkCircleGobCollision(Pixmap pixmap, PositionComponent imagePosition, SizeComponent imageSize, StaticImageComponent imageComp, MyGameObject circleGob) {
        PositionComponent circleGobPos;
        RotationComponent rot;
        CollisionCirclesComponent circleComp = circleGob.getComponent(CollisionCirclesComponent.class).get();
        circleGobPos = circleGob.getComponent(PositionComponent.class).get();
        for (Circle circle : circleComp.collisionCircles) {
            Circle workingCircle = new Circle(circle);
            if (circleGob.hasComponent(RotationComponent.class)) {
                rot = circleGob.getComponent(RotationComponent.class).get();
                Vector2 rotated = VectorMath.getRotatedPoint(workingCircle.x, workingCircle.y, rot.toRadians(), Vector2.Zero);
                workingCircle.x = rotated.x;
                workingCircle.y = rotated.y;
            }
            workingCircle.x += circleGobPos.x;
            workingCircle.y += circleGobPos.y;

            Rectangle circleFit = new Rectangle(workingCircle.x - workingCircle.radius, workingCircle.y - workingCircle.radius, 2 * workingCircle.radius, 2 * workingCircle.radius);
            Rectangle imageRect = new Rectangle(imagePosition.x, imagePosition.y, imageSize.w, imageSize.h);
            Rectangle intersect = new Rectangle();
            if (GeomUtils.intersect(circleFit, imageRect, intersect)) {
                for (int x = 0; x < intersect.width; x++) {
                    for (int y = 0; y < intersect.height; y++) {
                        Vector2 worldPixelCoords = new Vector2(x + intersect.x, y + intersect.y);
                        float distance = new Vector2(workingCircle.x, workingCircle.y).sub(worldPixelCoords).len();
                        if (distance < workingCircle.radius) {
                            // this pixel is worth checking
                            Vector2 imageWorldCoords = worldPixelCoords.cpy().sub(new Vector2(imagePosition.x, imagePosition.y));
                            Vector2 imagePercentages = imageWorldCoords.cpy().scl( 1f / imageSize.w, 1f / imageSize.h);
                            int color = getImageColorAtPercentage(pixmap, imageComp.image(), imagePercentages);
                            if ((color & 0xFF) == 255) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private int getImageColorAtPercentage(Pixmap pixmap, TextureRegion image, Vector2 imagePercentages) {
        int textureX = (int) (image.getRegionX() + image.getRegionWidth() * imagePercentages.x);
        int textureY = (int) (image.getRegionY() + image.getRegionHeight() * (1 - imagePercentages.y) );
        return pixmap.getPixel(textureX, textureY);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return isImageCollider(gob) || isCircleCollider(gob);
    }

    private boolean isImageCollider(MyGameObject gob) {
        return gob.hasComponents(ImageCollisionComponent.class, StaticImageComponent.class);
    }

    private boolean isCircleCollider(MyGameObject gob) {
        return gob.hasComponents(CollisionCirclesComponent.class, PositionComponent.class);
    }
}
