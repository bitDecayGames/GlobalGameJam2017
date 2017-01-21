package com.bitdecay.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.bitdecay.game.Launcher;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.gameobject.MyGameObjectFactory;
import com.bitdecay.game.trait.ICanSetRoom;
import com.bitdecay.game.trait.ICanSetScreen;
import com.bitdecay.game.trait.IHasScreenSize;
import com.bitdecay.game.util.SoundLibrary;
import com.bitdecay.game.util.Tilesets;
import com.bitdecay.jump.collision.BitWorld;
import com.bitdecay.jump.gdx.level.EditorIdentifierObject;
import com.bitdecay.jump.gdx.level.RenderableLevelObject;
import com.bitdecay.jump.level.Level;
import com.bitdecay.jump.leveleditor.EditorHook;

import java.util.Collections;
import java.util.List;

/**
 * The game screen used to be the main source of game logic.  It is now more just like any other screen.  It allows for the game to switch to it, but the main logic is moved into the Room class.  In the same way you can switch from screen to screen with a reference to the MyGame object, you can switch from room to room with the GameScreen object.
 */
public class GameScreen implements Screen, EditorHook, IHasScreenSize, ICanSetScreen, ICanSetRoom {

    private MyGame game;

    private com.bitdecay.game.room.AbstractRoom room;

    private Texture edgeTestImage;
    private String vertexShader;
    private String fragShader;
    private ShaderProgram shader;

    private SpriteBatch batch = new SpriteBatch();

    public GameScreen(MyGame game) {
        this.game = game;
//        setRoom(new DemoRoom(this, FileUtils.loadFileAs(Level.class, Gdx.files.classpath("level/simple.level").readString())));
    }

    public GameScreen(MyGame game, com.bitdecay.game.room.AbstractRoom room) {
        this.game = game;
//        setRoom(room);
    }

    @Override
    public void show() {
        SoundLibrary.stopMusic(Launcher.conf.getString("splash.music"));
        edgeTestImage = new Texture(Gdx.files.internal("shader/EdgeTest.png"));
        vertexShader = Gdx.files.internal("shader/vertex_passthrough.glsl").readString();
        fragShader = Gdx.files.internal("shader/frag_sweeper.glsl").readString();
        shader = new ShaderProgram(vertexShader, fragShader);
        if (!shader.isCompiled()) {
            throw new GdxRuntimeException("Couldn't compile shader: " + shader.getLog());
        }
    }

    Vector2 center = new Vector2(.4f, .35f);
    float largeRadius = 0;
    float smallRadius = 0;
    float fullColorStopRadius = 0;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float sweepStepSize = 0.01f;

        float maxSweepRange = 3f;

        float sweepFadeDistance = 1f;
        float colorFadeDistance = 0.2f;

        largeRadius += sweepStepSize;

        if (largeRadius > maxSweepRange) {
            largeRadius = 0;
            center.x = MathUtils.random(0f, 1f);
            center.y = MathUtils.random(0f, 1f);
        }

        shader.begin();
        shader.setUniformf("v_center", center.x, center.y);
        shader.setUniformf("f_sweepRadius", largeRadius);
        shader.setUniformf("f_delta", .002f);
        shader.setUniformf("f_sweepFadeDistance", sweepFadeDistance);
        shader.setUniformf("f_colorFadeDistance", colorFadeDistance);
        shader.end();
        batch.setShader(shader);
        batch.begin();
        batch.draw(edgeTestImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        if (room != null) room.dispose();
    }

    @Override
    public Vector2 screenSize() {
        return null; // TODO: need to implement a way to change the screen size
    }

    @Override
    public void setScreen(Screen screen) {
        game.setScreen(screen);
    }

    @Override
    public void setRoom(com.bitdecay.game.room.AbstractRoom room) {
        if (this.room != null) this.room.dispose();
        this.room = room;
    }

    // ////////////////////////////////////
    // Level editor hook methods
    // ////////////////////////////////////

    @Override
    public void update(float delta) {
        if (room != null) room.update(delta);
    }

    @Override
    public void render(OrthographicCamera orthographicCamera) {
        if (room != null) room.render(orthographicCamera);
    }

    @Override
    public BitWorld getWorld() {
        if (room != null) return room.getWorld();
        else return null;
    }

    @Override
    public List<EditorIdentifierObject> getTilesets() {
        return Tilesets.editorTilesets();
    }

    @Override
    public List<RenderableLevelObject> getCustomObjects() {
        return MyGameObjectFactory.allLevelObjects();
    }

    @Override
    public List<EditorIdentifierObject> getThemes() {
        return Collections.emptyList();
    }

    @Override
    public void levelChanged(Level level) {
        if (room != null) room.levelChanged(level);
    }
}
