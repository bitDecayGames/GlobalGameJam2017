package com.bitdecay.game.component;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.bitdecay.game.gameobject.MyGameObject;

/**
 * The component in charge of tracking the x and y position of the object.
 */
public class AnimationComponent extends AbstractComponent {

    public Texture texture;

    public AnimationComponent(MyGameObject obj, String texturePath){
        super(obj);
        texture = new Texture(Gdx.files.internal(texturePath));
    }

    public void update() {

    }
}
