package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Currently this is not used for anything.
 */
public class TextComponent extends AbstractComponent {

    public String text;
    public int durationMs;
    public long timeOfInitialization;
    public float fade;
    public int sizeMultiplier;

    public TextComponent(MyGameObject obj, String text, int textSizeMultiplier, int durationMs){
        super(obj);
        this.text = text;
        this.sizeMultiplier = textSizeMultiplier;
        this.durationMs = durationMs;
        this.timeOfInitialization = System.currentTimeMillis();
    }

    public void update() {
        long totalTimeSpentOnScreenMs = System.currentTimeMillis() - timeOfInitialization;
        long floatLong = Math.min(durationMs - totalTimeSpentOnScreenMs, 1000);
        fade = convertMilisecondsToSeconds(floatLong);
        if (fade <= 0) {
            obj.addComponent(new RemoveNowComponent(obj));
        }
    }

    private float convertMilisecondsToSeconds(long miliseconds) {
        return (float)miliseconds/1000f;
    }
}
