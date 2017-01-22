package com.bitdecay.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.RotationComponent;
import com.bitdecay.game.component.TextComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;
import com.bitdecay.game.trait.IShapeRotatableDraw;

/**
 * This system is in charge of providing a position to the IShapeDrawComponents so that they are able to correctly draw themselves.
 */
public class TextDrawSystem extends AbstractDrawableSystem {

    BitmapFont font;

    public TextDrawSystem(AbstractRoom room) {
        super(room);
        font = new BitmapFont(Gdx.files.internal("font/bit.fnt"));
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PositionComponent.class, TextComponent.class) || gob.hasComponents(IShapeRotatableDraw.class, PositionComponent.class, RotationComponent.class);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
            gobs.forEach(gob -> gob.forEachComponentDo(TextComponent.class, splashText -> {
                gob.forEachComponentDo(PositionComponent.class, text -> {
                    splashText.update();
                    font.setColor(1, 1, 1, splashText.fade);
                    font.getData().setScale(splashText.sizeMultiplier);
                    font.draw(spriteBatch, splashText.text, text.x, text.y);
                });
            }));
        spriteBatch.end();
    }
}
