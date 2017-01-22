package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.GameObjectNames;
import com.bitdecay.game.gameobject.MyGameObject;

/**
 * Created by Luke on 1/21/2017.
 */
public class ObjectNameComponent extends AbstractComponent{
    public GameObjectNames objectName;

    public ObjectNameComponent(MyGameObject obj){
        super(obj);
    }
    public ObjectNameComponent(MyGameObject obj, GameObjectNames name) {
        super(obj);
        objectName = name;
    }
}
