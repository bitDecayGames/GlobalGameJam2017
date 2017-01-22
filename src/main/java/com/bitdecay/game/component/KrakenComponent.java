package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * This component contains Kraken state
 */
public class KrakenComponent extends AbstractComponent {

    public MyGameObject player;
    public MyGameObject face;
    public MyGameObject head;
    public MyGameObject leftTentacle;
    public MyGameObject rightTentacle;
    public MyGameObject shortTentacles;

    public KrakenComponent(MyGameObject obj){super(obj);}
}
