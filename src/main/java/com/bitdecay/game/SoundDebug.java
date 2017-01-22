package com.bitdecay.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bitdecay.game.util.SoundLibrary;
import org.apache.log4j.BasicConfigurator;


/**
 * Created by Maveric on 1/21/2017.
 */
public class SoundDebug extends ApplicationAdapter {

    class MyActor extends Actor {
        Texture texture = new Texture(Gdx.files.internal("img\\standalone\\splash\\bitDecay.png"));
        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(texture, 0, 0);
        }
    }

    Stage stage;
    Skin skin;
    Table table;

    @Override
    public void create () {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());

        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center| Align.top);

        table.setPosition(0, Gdx.graphics.getHeight());

        final TextButton button = new TextButton("Play sound", skin, "default");
        button.setWidth(200);
        button.setHeight(50);


        final SelectBox selectBox = new SelectBox(skin);

        Object[] sfx = SoundLibrary.GetSoundList("sounds.fx").toArray();

        selectBox.setItems(sfx);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                String selectedSound = selectBox.getSelected().toString();

                SoundLibrary.playSound(selectedSound);
            }
        });

        table.add(selectBox);
        stage.addActor(table);
        stage.addActor(button);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        new LwjglApplication(new SoundDebug());
    }
}
