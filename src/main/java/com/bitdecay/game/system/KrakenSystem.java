package com.bitdecay.game.system;

import com.bitdecay.game.component.KrakenComponent;
import com.bitdecay.game.component.PlayerInputComponent;
import com.bitdecay.game.component.RelativePositionComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.gameobject.MyGameObjectFactory;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.room.DemoRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;

public class KrakenSystem extends AbstractUpdatableSystem {

    public KrakenSystem(AbstractRoom room){
        super(room);
    }

    private void addKraken() {
        gobs.addAll(MyGameObjectFactory._____RELEASE___THE___KRAKEN_____(DemoRoom.player));
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(KrakenComponent.class) || gob.hasComponent(PlayerInputComponent.class);
    }

    @Override
    public void update(float delta) {
        gobs.stream().filter(gob -> gob.hasComponent(PlayerInputComponent.class)).findFirst().ifPresent(playerObj -> gobs.forEach(gob -> gob.forEachComponentDo(KrakenComponent.class, kraken -> gob.forEachComponentDo(RelativePositionComponent.class, rel ->{
            rel.other = playerObj;
            kraken.player = playerObj;

            if (playerObj)
        }))));
        gobs.forEach(gob -> gob.forEachComponentDo(KrakenComponent.class, kraken ->
                gob.forEachComponentDo(RelativePositionComponent.class, relPos -> {
                    kraken.speed += kraken.acceleration;
                    relPos.y += kraken.speed;
                    if (kraken.direction > 0 && kraken.speed > kraken.maxSpeed) kraken.speed = kraken.maxSpeed;
                    else if(kraken.direction < 0 && kraken.speed < -kraken.maxSpeed) kraken.speed = -kraken.maxSpeed;

                    if (kraken.direction > 0 && relPos.y > kraken.boundary) {
                        kraken.acceleration *= -1;
                        kraken.direction = -1;
                    } else if (kraken.direction < 0 && relPos.y < -kraken.boundary){
                        kraken.direction = 1;
                        kraken.acceleration *= -1;
                    }
                })));
    }
}
