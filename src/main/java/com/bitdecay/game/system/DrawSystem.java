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

    public static ShaderProgram proximityShader;
    public static ShaderProgram pingShader;


    FrameBuffer sonarBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    FrameBuffer proximityBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

    public DrawSystem(AbstractRoom room) {
        super(room);
        String vertexShader = Gdx.files.internal("shader/vertex_passthrough.glsl").readString();
        String fragShader = Gdx.files.internal("shader/frag_proximity.glsl").readString();
        proximityShader = new ShaderProgram(vertexShader, fragShader);
        if (!proximityShader.isCompiled()) {
            throw new RuntimeException("Shader does not compile:\n" + proximityShader.getLog());
        }

        fragShader = Gdx.files.internal("shader/frag_sweeper.glsl").readString();
        pingShader = new ShaderProgram(vertexShader, fragShader);
        if (!pingShader.isCompiled()) {
            throw new RuntimeException("Shader does not compile:\n" + pingShader.getLog());
        }
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(DrawableComponent.class, PositionComponent.class, SizeComponent.class) || gob.hasComponents(AnimationComponent.class, PositionComponent.class, SizeComponent.class);
    }

    @Override
    public void preDraw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.enableBlending();
        spriteBatch.setBlendFunction(Gdx.gl.GL_BLEND_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);


        sonarBuffer.begin();
        Gdx.gl.glClearColor(0.5f, 0.f, 0.0f, 0f); //transparent black
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT | Gdx.gl.GL_DEPTH_BUFFER_BIT); //clear the color buffer
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
                                    gob.forEachComponentDo(AnimationComponent.class, anim -> {
                                        Vector3 drawPosAnim = new Vector3();
                                        gob.forEachComponentDo(OriginComponent.class, org -> drawPosAnim.add(size.w * org.x, size.h * org.y, 0));
                                        gob.forEachComponentDo(RotationComponent.class, rot -> drawPosAnim.z = rot.degrees);
                                        spriteBatch.draw(anim.animationFrames.getKeyFrame(anim.elapsedTime, true), pos.x - drawPosAnim.x, pos.y - drawPosAnim.y, drawPosAnim.x, drawPosAnim.y, size.w, size.h, 1, 1, drawPosAnim.z);
                                    });
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

                                        gob.forEachComponentDo(AnimationComponent.class, anim -> {
                                        Vector3 drawPosAnim = new Vector3();
                                        gob.forEachComponentDo(OriginComponent.class, org -> drawPosAnim.add(size.w * org.x, size.h * org.y, 0));
                                        gob.forEachComponentDo(RotationComponent.class, rot -> drawPosAnim.z = rot.degrees);
                                        spriteBatch.draw(anim.animationFrames.getKeyFrame(anim.elapsedTime, true), pos.x - drawPosAnim.x, pos.y - drawPosAnim.y, drawPosAnim.x, drawPosAnim.y, size.w, size.h, 1, 1, drawPosAnim.z);
                                    });
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


        renderSonarBuffer(spriteBatch);

        renderProximityBuffer(spriteBatch);

    }

    private void renderProximityBuffer(SpriteBatch spriteBatch) {
        Sprite proximitySprite = new Sprite(proximityBuffer.getColorBufferTexture());
        proximitySprite.flip(false, true);

        spriteBatch.begin();
        spriteBatch.setShader(proximityShader);
        proximitySprite.draw(spriteBatch);
        spriteBatch.end();
        spriteBatch.setShader(null);
    }

    private void renderSonarBuffer(SpriteBatch spriteBatch) {
        Sprite sonarSprite = new Sprite(sonarBuffer.getColorBufferTexture());
        sonarSprite.flip(false, true);

        spriteBatch.begin();
        spriteBatch.setShader(getConfiguredShader());
        sonarSprite.draw(spriteBatch);
        spriteBatch.end();
        spriteBatch.setShader(null);
    }

    public ShaderProgram getConfiguredShader() {
        pingShader.begin();
        pingShader.setUniformf("f_deltaX", 1f / Gdx.graphics.getWidth());
        pingShader.setUniformf("f_deltaY", 1f / Gdx.graphics.getHeight());
        pingShader.setUniformf("f_sweepFadeDistance", sweepFadeDistance);
        pingShader.setUniformf("f_colorFadeDistance", colorFadeDistance);
        pingShader.end();
        return pingShader;
    }
}
