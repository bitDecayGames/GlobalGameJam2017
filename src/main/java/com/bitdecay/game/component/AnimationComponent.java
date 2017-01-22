package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bytebreakstudios.animagic.texture.AnimagicTextureRegion;

/**
 * The component in charge of tracking the x and y position of the object.
 */
public class AnimationComponent extends AbstractComponent {

    public Texture texture;
    public TextureRegion[] animationTextureRegion;
    public Animation animationFrames;
    public float elapsedTime;
    public Animation.PlayMode playMode;

    public AnimationComponent(MyGameObject obj, String texturePath, float secondsPerFrame, Animation.PlayMode mode){
        super(obj);
        playMode = mode;
        Array<AnimagicTextureRegion> regions = MyGame.ATLAS.findRegions(texturePath);
        animationTextureRegion = new TextureRegion[regions.size];
        for (int i = 0; i < regions.size; i++) {
            animationTextureRegion[i] = regions.get(i);
        }
        animationFrames = new Animation(secondsPerFrame, animationTextureRegion);
        animationFrames.setPlayMode(playMode);
    }
}
