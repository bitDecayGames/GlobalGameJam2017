package com.bitdecay.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.bitdecay.game.Launcher;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.util.InputHelper;

/**
 * This is the generic controls screen.  Almost everything in the controls is populated from the /resources/conf/controls.conf file.  The only reason you should be making changes to this file is to adjust the position or speed of the text.
 */
public class ControlScreen implements Screen {

    private MyGame game;
    private Stage stage = new Stage();

    private Image background;
    private Label lblTitle;
    private Label lblBack;
    private Label lblControls;

    public ControlScreen(MyGame game) {
        this.game = game;

        Skin skin = new Skin(Gdx.files.classpath(Launcher.conf.getString("controls.skin")));

        background = new Image(new Texture(Gdx.files.classpath(Launcher.conf.getString("controls.background"))));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        lblTitle = new Label("     Controls", skin);
        lblTitle.setFontScale(10);
        lblTitle.setFillParent(true);
        lblTitle.setAlignment(Align.top, Align.left);
        lblTitle.setColor(Color.GREEN);

        lblBack = new Label("Press ESC/B/SPACE to go back", skin);
        lblBack.setFontScale(2);
        lblBack.setFillParent(true);
        lblBack.setAlignment(Align.top, Align.center);
        lblBack.setColor(Color.GREEN);

        lblControls = new Label("\n\n\n\n\n\n\n\n\n\n\n" +
                "Move Sub Up:   'S' or '<-'             \n" +
                "Move Sub Down: 'W' or '->'             \n" +
                "Use  Sonar:    'SPACE'               \n" +
                " Fire  Torpedo:  'CTRL'                 \n", skin);
        lblControls.setFontScale(3);
        lblControls.setFillParent(true);
        lblControls.setAlignment(Align.top, Align.center);
        lblControls.setColor(Color.BLACK);

        // ///////////////////////////////////////////////
        // Add new sections to the controls here
        // ///////////////////////////////////////////////

        stage.addActor(background);
        stage.addActor(lblTitle);
        stage.addActor(lblBack);
        stage.addActor(lblControls);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if (InputHelper.isKeyJustPressed(Input.Keys.SPACE, Input.Keys.ESCAPE, Input.Keys.B, Input.Keys.BACKSPACE)) nextScreen();
    }

    public void nextScreen(){
        game.setScreen(new MainMenuScreen(game));
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
        stage.dispose();
    }
}
