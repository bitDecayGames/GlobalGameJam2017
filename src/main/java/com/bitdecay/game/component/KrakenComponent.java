package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

/**
 * This component contains Kraken state
 */
public class KrakenComponent extends AbstractComponent {

    public MyGameObject player;
    public MyGameObject face;
    public MyGameObject leftEye;
    public MyGameObject rightEye;
    public MyGameObject head;
    public MyGameObject leftTentacle;
    public MyGameObject rightTentacle;
    public MyGameObject shortTentacles;

    public float acceleration = 0.01f;
    public float direction = acceleration;
    public float maxSpeed = 1;
    public float speed = maxSpeed;
    public float boundary = 20;

    public KrakenComponent(MyGameObject obj){super(obj);}
}
