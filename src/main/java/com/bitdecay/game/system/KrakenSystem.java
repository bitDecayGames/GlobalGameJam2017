package com.bitdecay.game.system;

import com.bitdecay.game.component.KrakenComponent;
import com.bitdecay.game.component.RelativePositionComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

public class KrakenSystem extends AbstractForEachUpdatableSystem{

    public KrakenSystem(AbstractRoom room){
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(KrakenComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(KrakenComponent.class, kraken ->
                gob.forEachComponentDo(RelativePositionComponent.class, relPos -> {
                    kraken.speed += kraken.acceleration;
                    relPos.y += kraken.speed;
//                    System.out.println("" + relPos.y + " " + kraken.direction + " " + kraken.speed);
                    if (kraken.direction > 0 && kraken.speed > kraken.maxSpeed) kraken.speed = kraken.maxSpeed;
                    else if(kraken.direction < 0 && kraken.speed < -kraken.maxSpeed) kraken.speed = -kraken.maxSpeed;

                    if (kraken.direction > 0 && relPos.y > kraken.boundary) {
                        kraken.acceleration *= -1;
                        kraken.direction = -1;
                    } else if (kraken.direction < 0 && relPos.y < -kraken.boundary){
                        kraken.direction = 1;
                        kraken.acceleration *= -1;
                    }
                }));
    }
}
