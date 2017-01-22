package com.bitdecay.game.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitdecay.game.component.DesiredDirectionComponent;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.RotationComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;
import com.bitdecay.game.trait.IShapeDraw;
import com.bitdecay.game.trait.IShapeRotatableDraw;

/**
 * This system is in charge of providing a position to the IShapeDrawComponents so that they are able to correctly draw themselves.
 */
public class ShapeDrawSystem extends AbstractDrawableSystem {
    public ShapeDrawSystem(AbstractRoom room) {
        super(room);
        room.shapeRenderer.setAutoShapeType(true);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(IShapeDraw.class, PositionComponent.class) || gob.hasComponents(IShapeRotatableDraw.class, PositionComponent.class, RotationComponent.class);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        room.shapeRenderer.setProjectionMatrix(camera.combined);
        room.shapeRenderer.begin();
        gobs.forEach(gob -> gob.forEachComponentDo(PositionComponent.class, positionComponent -> {
            gob.forEachComponentDo(IShapeDraw.class, drawable -> drawable.draw(room.shapeRenderer, positionComponent.toVector2()));
            gob.forEachComponentDo(IShapeRotatableDraw.class, drawable -> {
                gob.forEachComponentDo(RotationComponent.class, rotationComponent ->
                        drawable.draw(room.shapeRenderer, positionComponent.toVector2(), rotationComponent.degrees));
                gob.forEachComponentDo(DesiredDirectionComponent.class, des -> drawable.draw(room.shapeRenderer, positionComponent.toVector2(), des.toDegrees()));
            });
        }));
        room.shapeRenderer.end();
    }
}
