package com.bitdecay.game.system.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.component.BackgroundComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * System for the background of the game.
 */
public class BackgroundSystem extends AbstractDrawableSystem {
    // Controls distortion of parallax on backgrounds.
    private static final float PARALLAX_EFFECT = 0.2f;

    private List<BackgroundLayer> layers;

    // Camera to render parallax effect.
    private OrthographicCamera backgroundCamera;

    public BackgroundSystem(AbstractRoom room) {
        super(room);

        initDimensions();

        // First backgrounds are further back and move slower than later entries
        // when the character this is watching moves.
        layers = new ArrayList<>();
        // Space
        layers.add(
                BackgroundLayer.create()
                        .addBackground("background1")
//                        .addBackground("space")
//        );
//        // Mountains
//        layers.add(
//                BackgroundLayer.create()
//                        .addBackground("hillsFar")
//        );
//        layers.add(
//                BackgroundLayer.create()
//                        .addBackground("hillsMiddle")
//        );
//        layers.add(
//                BackgroundLayer.create()
//                        .addBackground("hillsClose")
//        );
//        // Underground
//        layers.add(
//                BackgroundLayer.create()
//                        .setVerticalOffsetIndex(-1)
//                        .setDirection(BackgroundLayerDirection.DOWN)
//                        .setVerticalOffset(300 * PARALLAX_EFFECT)
//                        .addBackground("startUnderground")
//                        .addBackground("underground")
        );
    }

    // Sets initial layer specs.
    private void initDimensions() {
        TextureRegion mainTile = BackgroundUtil.getBackground("background1");
        BackgroundLayer.setTileWidth(mainTile.getRegionWidth());
        BackgroundLayer.setTileHeight(mainTile.getRegionHeight());
        BackgroundLayer.setHorizontalSize(10);
    }

    @Override
    public void preDraw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        backgroundCamera = new OrthographicCamera(camera.viewportWidth, camera.viewportHeight);
        backgroundCamera.position.set(
                camera.position.x * PARALLAX_EFFECT + Gdx.graphics.getWidth() / 3,
                camera.position.y * PARALLAX_EFFECT + Gdx.graphics.getHeight() / 3,
                0);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        final float parallax = 1 + PARALLAX_EFFECT;

        spriteBatch.begin();

        for (BackgroundLayer l : layers) {
            // Increase move value as we move from background to foreground.
            backgroundCamera.position.set(
                    backgroundCamera.position.x * parallax,
                    backgroundCamera.position.y * parallax,
                    0);
            backgroundCamera.update();

            spriteBatch.setProjectionMatrix(backgroundCamera.combined);
            l.draw(spriteBatch);
        }

        spriteBatch.end();
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(BackgroundComponent.class);
    }
}