package com.bitdecay.game.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;

/**
 * The draw system is one of the few systems that extends AbstractSystem directly.  The reason for this is that there is a call to spritebatch begin and end within the process method.  You will notice however that the forEachComponentDo method is called in between the begin and end calls.  When you extend the AbstractForEachUpdatableSystem, that forEachComponentDo call isn't necessary because it happens behind the scenes in the AbstractForEachUpdatableSystem.process method.
 */
public class DrawSystem extends AbstractDrawableSystem {
    public DrawSystem(AbstractRoom room) { super(room); }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(DrawableComponent.class, PositionComponent.class, SizeComponent.class);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        gobs.forEach(gob ->
                gob.forEachComponentDo(DrawableComponent.class, drawable ->
                        gob.forEachComponentDo(PositionComponent.class, pos ->
                                gob.forEachComponentDo(SizeComponent.class, size ->{
                                    Vector3 drawPos = new Vector3();
                                    gob.forEachComponentDo(OriginComponent.class, org -> drawPos.add(size.w * org.x, size.h * org.y, 0));
                                    gob.forEachComponentDo(RotationComponent.class, rot -> drawPos.z = rot.degrees);
                                    spriteBatch.draw(drawable.image(), pos.x - drawPos.x, pos.y - drawPos.y, drawPos.x, drawPos.y, size.w, size.h, 1, 1, drawPos.z);
                                }))));
        spriteBatch.end();
    }
}
