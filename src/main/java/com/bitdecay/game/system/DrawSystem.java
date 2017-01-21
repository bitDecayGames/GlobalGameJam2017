package com.bitdecay.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;

/**
 * The draw system is one of the few systems that extends AbstractSystem directly.  The reason for this is that there is a call to spritebatch begin and end within the process method.  You will notice however that the forEachComponentDo method is called in between the begin and end calls.  When you extend the AbstractForEachUpdatableSystem, that forEachComponentDo call isn't necessary because it happens behind the scenes in the AbstractForEachUpdatableSystem.process method.
 */
public class DrawSystem extends AbstractDrawableSystem {
    public float sweepFadeDistance = 600f;
    public float colorFadeDistance = 100;


    FrameBuffer sonarBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    FrameBuffer proximityBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

    public DrawSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(DrawableComponent.class, PositionComponent.class, SizeComponent.class);
    }

    @Override
    public void preDraw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        spriteBatch.setProjectionMatrix(camera.combined);

        sonarBuffer.begin();
        Gdx.gl.glClearColor(0.0f, 0.f, 0.0f, 0f); //transparent black
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT); //clear the color buffer
        spriteBatch.begin();
        gobs.forEach(gob ->
                gob.forEachComponentDo(DrawableComponent.class, drawable -> {

                    if (drawable.reactsToSonar) {
                        gob.forEachComponentDo(PositionComponent.class, pos ->
                                gob.forEachComponentDo(SizeComponent.class, size -> {
                                    Vector3 drawPos = new Vector3();
                                    gob.forEachComponentDo(OriginComponent.class, org -> drawPos.add(size.w * org.x, size.h * org.y, 0));
                                    gob.forEachComponentDo(RotationComponent.class, rot -> drawPos.z = rot.degrees);
                                    spriteBatch.draw(drawable.image(), pos.x - drawPos.x, pos.y - drawPos.y, drawPos.x, drawPos.y, size.w, size.h, 1, 1, drawPos.z);
                                })
                        );
                    }
                })
        );
        spriteBatch.end();
        sonarBuffer.end();

        proximityBuffer.begin();
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f); //transparent black
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT); //clear the color buffer
        spriteBatch.begin();
        gobs.forEach(gob ->
                gob.forEachComponentDo(DrawableComponent.class, drawable -> {
                    if (drawable.proximityRender) {
                        gob.forEachComponentDo(PositionComponent.class, pos ->
                                gob.forEachComponentDo(SizeComponent.class, size -> {
                                    Vector3 drawPos = new Vector3();
                                    gob.forEachComponentDo(OriginComponent.class, org -> drawPos.add(size.w * org.x, size.h * org.y, 0));
                                    gob.forEachComponentDo(RotationComponent.class, rot -> drawPos.z = rot.degrees);
                                    spriteBatch.draw(drawable.image(), pos.x - drawPos.x, pos.y - drawPos.y, drawPos.x, drawPos.y, size.w, size.h, 1, 1, drawPos.z);
                                })
                        );
                    }
                })
        );
        spriteBatch.end();
        proximityBuffer.end();
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        Matrix4 defaultProjMatrix = new Matrix4();
        defaultProjMatrix.setToOrtho2D(0.0F, 0.0F, (float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight());
        spriteBatch.setProjectionMatrix(defaultProjMatrix);

        Sprite proximitySprite = new Sprite(proximityBuffer.getColorBufferTexture());
        proximitySprite.flip(false, true);
        spriteBatch.setShader(null);
        spriteBatch.begin();
        proximitySprite.draw(spriteBatch);
        spriteBatch.end();

        spriteBatch.setShader(getConfiguredShader());
        Sprite sonarSprite = new Sprite(sonarBuffer.getColorBufferTexture());
        sonarSprite.flip(false, true);

        spriteBatch.begin();
        sonarSprite.draw(spriteBatch);
        spriteBatch.end();
    }

    public ShaderProgram getConfiguredShader() {
        ShaderProgram pingShader = SonarPingSystem.pingShader;
        pingShader.begin();
        pingShader.setUniformf("f_deltaX", 1f / Gdx.graphics.getWidth());
        pingShader.setUniformf("f_deltaY", 1f / Gdx.graphics.getHeight());
        pingShader.setUniformf("f_sweepFadeDistance", sweepFadeDistance);
        pingShader.setUniformf("f_colorFadeDistance", colorFadeDistance);
        pingShader.end();
        return pingShader;
    }
}
