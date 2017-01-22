package com.bitdecay.game.component;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.gameobject.MyGameObject;

/**
 * The component in charge of tracking the x and y position of the object.
 */
public class AnimationComponent extends AbstractComponent {

    public Texture texture;
    public TextureRegion[] animationTextureRegion;
    public Animation animationFrames;
    public float elapsedTime;
    public boolean reactsToSonar = false;

    public AnimationComponent(MyGameObject obj, String texturePath){
        super(obj);
        texture = new Texture(Gdx.files.internal(texturePath));

        TextureRegion[][] tmpFrames = TextureRegion.split(texture,49, 26);

        animationTextureRegion = new TextureRegion[2];
        int index = 0;

        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 1; j++) {
                animationTextureRegion[index++] = tmpFrames[j][i];
            }
        }

        animationFrames = new Animation(1f, animationTextureRegion);
    }

    public AnimationComponent setReactsToSonar(boolean reactsToSonar){
        this.reactsToSonar = reactsToSonar;
        return this;
    }

    public void update() {

    }
}
