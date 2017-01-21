package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Add this component to a MyGameObject to tell the camera to follow the obj but lead the object based on its direction and speed
 */
public class PredictiveCameraFollowComponent extends AbstractComponent {
    public PredictiveCameraFollowComponent(MyGameObject obj) {
        super(obj);
    }
}
