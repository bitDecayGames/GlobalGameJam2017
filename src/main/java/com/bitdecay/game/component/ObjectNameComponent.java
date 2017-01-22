package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Created by Luke on 1/21/2017.
 */
public class ObjectNameComponent extends AbstractComponent{
    public String objectName;

    public ObjectNameComponent(MyGameObject obj){
        super(obj);
    }
    public ObjectNameComponent(MyGameObject obj, String name) {
        super(obj);
        objectName = name;
    }
}
