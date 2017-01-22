package com.bitdecay.game.gameobject;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.Launcher;
import com.bitdecay.game.component.*;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.util.GameUtil;
import com.bitdecay.game.util.VectorMath;
import com.typesafe.config.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The idea here is to provide a single place for you to add your game objects.  You know that the "Player" game object will have a PositionComponent, a SizeComponent, and a CameraFollowComponent.  So in a static method (maybe called buildPlayer) you want to create a generic MyGameObject and populate it with the correct components.
 */
public final class MyGameObjectFactory {
    private MyGameObjectFactory(){}

    public static MyGameObject ship(AbstractRoom room, Vector2 position){
        Config conf = Launcher.conf.getConfig("player");
        MyGameObject t = new MyGameObject();
        PositionComponent positionComp = new PositionComponent(t, position);
        t.addComponent(new PlayerInputComponent(t, (float) conf.getDouble("desiredDegreeRotationAmountPerStep"), (float) conf.getDouble("maxDegrees"), (float) conf.getDouble("minDegrees")));
        t.addComponent(positionComp);
        t.addComponent(new RotationComponent(t));
        t.addComponent(new DesiredDirectionComponent(t, 0, (float) conf.getDouble("actualRotationSpeedScalar")));
        t.addComponent(new SizeComponent(t, conf.getInt("size.w"), conf.getInt("size.h")));
        t.addComponent(new ObjectNameComponent(t,GameObjectNames.SHIP));
        CollisionCirclesComponent collision = new CollisionCirclesComponent(t);
        collision.collisionCircles.add(new Circle(16, 1.5f, 8));
        collision.collisionCircles.add(new Circle(4, 0, 11));
        collision.collisionCircles.add(new Circle(-10, -1, 8));
        t.addComponent(collision);
        t.addComponent(new CollisionResponseComponent(t));
        t.addComponent(new OriginComponent(t));
        t.addComponent(new CameraFollowComponent(t));
        t.addComponent(new PredictiveCameraFollowComponent(t)); // need two of these
        t.addComponent(new PredictiveCameraFollowComponent(t)); // need two of these
        t.addComponent(new StaticImageComponent(t, conf.getString("imagePath")).setReactsToSonar(true));
        t.addComponent(new VelocityComponent(t, (float) conf.getDouble("moveSpeed"), 0));
        t.addComponent(new CollisionComponent(t));
        t.addComponent(new CanPingComponent(t));
        t.addComponent(new ProximityIlluminationComponent(t));
        t.addComponent(new AccelerationComponent(t));
        t.addComponent(new CanShootComponent(t));
        t.addComponent(new RespawnRecorderComponent(t, 5));

        return t;
    }

    public static MyGameObject mine(int x, int y) {
        MyGameObject t = new MyGameObject();
        t.addComponent(new ObjectNameComponent(t, GameObjectNames.MINE));
        t.addComponent(new DebugCircleComponent(t, com.badlogic.gdx.graphics.Color.GREEN, 25));
        t.addComponent(new PositionComponent(t, x,y ));
        t.addComponent(new SizeComponent(t, 12, 14 ));
        CollisionCirclesComponent collision = new CollisionCirclesComponent(t);
        collision.collisionCircles.add(new Circle(0, 0, 7));
        t.addComponent(collision);
        t.addComponent(new CollisionResponseComponent(t));
        t.addComponent(new OriginComponent(t));
        t.addComponent(new StaticImageComponent(t, "enemies/mine/mine").setReactsToSonar(true));
        t.addComponent(new CollisionComponent(t));
        t.addComponent(new RandomOrbitComponent(t, x, y , 2.5f ));
        t.addComponent(new VelocityComponent(t));
        t.addComponent(new AccelerationComponent(t));
        return t;
    }

    public static MyGameObject jelly(int x, int y) {
        MyGameObject t = new MyGameObject();
        t.addComponent(new ObjectNameComponent(t, GameObjectNames.JELLY));
        t.addComponent(new DebugCircleComponent(t, com.badlogic.gdx.graphics.Color.GREEN, 25));
        t.addComponent(new PositionComponent(t, x, y));
        t.addComponent(new SizeComponent(t, 27, 20));
        CollisionCirclesComponent collision = new CollisionCirclesComponent(t);
        collision.collisionCircles.add(new Circle(0, 4, 5));
        t.addComponent(collision);
        t.addComponent(new CollisionResponseComponent(t));
        t.addComponent(new OriginComponent(t));
        t.addComponent(new StaticImageComponent(t, "enemies/jelly/0").setReactsToSonar(true));
        t.addComponent(new AnimationComponent(t, "enemies/jelly", 0.2f, Animation.PlayMode.LOOP));
        t.addComponent(new CollisionComponent(t));
        t.addComponent(new AccelerationComponent(t));

        GameUtil.generateDirection(t);

        return t;
    }

    public static MyGameObject splashText(String text, int textSizeMultiplier, int durationMs, int x, int y) {
        MyGameObject t = new MyGameObject();
        t.addComponent(new PositionComponent(t, x, y));
        t.addComponent(new TextComponent(t, text, textSizeMultiplier, durationMs));
        return t;
    }

    public static MyGameObject globalInputListener(AbstractRoom room) {
        MyGameObject t = new MyGameObject();
        t.addComponent(new GlobalInputComponent(t, room));
        return t;
    }

    public static MyGameObject ping(Vector2 startPos) {
        MyGameObject pingObj = new MyGameObject();
        pingObj.addComponent(new PositionComponent(pingObj, startPos.x, startPos.y));
        pingObj.addComponent(new SonarPingComponent(pingObj));
//        pingObj.addComponent(new DebugCircleComponent(pingObj, com.badlogic.gdx.graphics.Color.GREEN, 25));
        pingObj.addComponent(new RotationComponent(pingObj, 0));

        return pingObj;
    }

    public static List<MyGameObject> demoBackgrounds(int numberOfBackgrounds) {
        List<MyGameObject> gobs = new ArrayList<>();
        List<String> newbNames = new ArrayList<>();
        List<String> names = new ArrayList<>();
        Config conf = Launcher.conf.getConfig("levelSegments");
        int maxA = conf.getInt("maxA");
        int maxAB = conf.getInt("maxAB");
        int maxB = conf.getInt("maxB");
        int maxBA = conf.getInt("maxBA");
        int maxBC = conf.getInt("maxBC");
        int maxC = conf.getInt("maxC");
        int maxCB = conf.getInt("maxCB");
        int maxNewb = conf.getInt("maxNewb");
        int actualNumberOfBackgrounds = Math.max(maxNewb, numberOfBackgrounds);
        for (int i = 0; i < maxA; i++) names.add("A/" + i);
        for (int i = 0; i < maxAB; i++) names.add("AB/" + i);
        for (int i = 0; i < maxB; i++) names.add("B/" + i);
        for (int i = 0; i < maxBA; i++) names.add("BA/" + i);
        for (int i = 0; i < maxBC; i++) names.add("BC/" + i);
        for (int i = 0; i < maxC; i++) names.add("C/" + i);
        for (int i = 0; i < maxCB; i++) names.add("CB/" + i);
        Collections.shuffle(names);
        // last newb needs to be a specific image
        for (int i = 0; i < maxNewb - 1; i++) newbNames.add("Newb/" + i);
        newbNames.add("Newb/10");

        String name = "";
        for (int i = 0; i < actualNumberOfBackgrounds; i ++){
            MyGameObject o = new MyGameObject();
            o.addComponent(new PositionComponent(o, 600 * i, 0));
            o.addComponent(new SizeComponent(o, 600, 600));
            o.addComponent(new ImageCollisionComponent(o));

            if (maxNewb > 0) {
                maxNewb--;
                name = newbNames.get(i);
            } else if (name.startsWith("Newb/")) name = names.stream().filter(s -> s.startsWith("A/")).findFirst().get();
            else if (name.startsWith("A/")) name = names.stream().filter(s -> s.startsWith("A/") || s.startsWith("AB/")).findFirst().get();
            else if (name.startsWith("AB/")) name = names.stream().filter(s -> s.startsWith("B/")).findFirst().get();
            else if (name.startsWith("B/")) name = names.stream().filter(s -> s.startsWith("B/") || s.startsWith("BA/") || s.startsWith("BC/")).findFirst().get();
            else if (name.startsWith("BA/")) name = names.stream().filter(s -> s.startsWith("A/")).findFirst().get();
            else if (name.startsWith("BC/")) name = names.stream().filter(s -> s.startsWith("C/")).findFirst().get();
            else if (name.startsWith("C/")) name = names.stream().filter(s -> s.startsWith("C/") || s.startsWith("CB/")).findFirst().get();
            else if (name.startsWith("CB/")) name = names.stream().filter(s -> s.startsWith("B/")).findFirst().get();
            o.addComponent(new StaticImageComponent(o, "levelSegments/" + name).prepareData().setReactsToSonar(true));
            o.addComponent(new LevelImageComponent(o));
            gobs.add(o);
            Collections.shuffle(names);
        }
        return gobs;
    }

    public static MyGameObject torpedo (float x, float y, float rot) {
        MyGameObject t = new MyGameObject();
        t.addComponent(new ObjectNameComponent(t,GameObjectNames.TORPEDO));
        Vector2 direction = VectorMath.degreesToVector2(rot).nor();
        Vector2 perp = new Vector2(direction.y, -direction.x);
//        t.addComponent(new DebugCircleComponent(t, com.badlogic.gdx.graphics.Color.RED, 25));
        t.addComponent(new DespawnableComponent(t));
        t.addComponent(new PositionComponent(t, x, y));
        t.addComponent(new SizeComponent(t, 21, 4));
        StaticImageComponent imageComponent = new StaticImageComponent(t, "player/torpedo/0");
        imageComponent.reactsToSonar = true;
        t.addComponent(imageComponent);
        t.addComponent(new AnimationComponent(t, "player/torpedo", .1f, Animation.PlayMode.LOOP));
        RotationComponent rotationComponent = new RotationComponent(t, rot);
        rotationComponent.rotationFromVelocity = false;
        t.addComponent(rotationComponent);
        t.addComponent(new VelocityComponent(t, 0.1f, 0f));
        t.addComponent(new TimerComponent(t, 0.25f, myGameObject ->
            myGameObject.forEachComponentDo(RotationComponent.class, rotat -> {
                myGameObject.addComponent(new AccelerationComponent(myGameObject, rotat.toVector2().scl(0.45f), 5f));
                myGameObject.addComponent(new ImpulseComponent(myGameObject, perp.cpy().scl(-2.5f)));
                ParticleEffectComponent particle = new ParticleEffectComponent(myGameObject, "particle/bubbleJet2.p", "particle");
                particle.fx.scaleEffect(.1f);
                particle.offset = new Vector2(-20, 0);
                myGameObject.addComponent(particle);
            }
        )));
        CollisionCirclesComponent collision = new CollisionCirclesComponent(t);
        collision.collisionCircles.add(new Circle(17, 2, 4));
        t.addComponent(collision);
        t.addComponent(new CollisionResponseComponent(t));
        t.addComponent(new DragComponent(t, 0.09f, 0.4f));
        t.addComponent(new ImpulseComponent(t, perp.cpy().scl(4)));


        return t;
    }

    public static MyGameObject shipExplode (Vector2 position) {
        MyGameObject t = new MyGameObject();
        t.addComponent(new ObjectNameComponent(t,GameObjectNames.SHIP_EXPLOSION));
        t.addComponent(new DespawnableComponent(t));
        t.addComponent(new PositionComponent(t, position));
        SizeComponent size = new SizeComponent(t, 57 * 2, 103 * 2);
        size.addSelfToGameObject();
        t.addComponent(new OriginComponent(t, 0.5f, 0.3f));
        t.addComponent(new AnimationComponent(t, "player/playerExplode", .07f, Animation.PlayMode.NORMAL));
        t.addComponent(new StaticImageComponent(t, "player/playerExplode/0"));
        t.addComponent(new TimerComponent(t, 1, obj -> obj.addComponent(new RemoveNowComponent(obj))));

        return t;
    }

    public static List<MyGameObject> _____RELEASE___THE___KRAKEN_____(MyGameObject player){
        MyGameObject o = new MyGameObject();
        KrakenComponent k = new KrakenComponent(o);
        k.addSelfToGameObject();
        k.player = player;
        new PositionComponent(o).addSelfToGameObject();
        new RelativePositionComponent(o, k.player, 300, 0).addSelfToGameObject();

        // face
        k.face = new MyGameObject();
        new PositionComponent(k.face).addSelfToGameObject();
        new OriginComponent(k.face).addSelfToGameObject();
        new RelativePositionComponent(k.face, o).addSelfToGameObject();
        new StaticImageComponent(k.face, "enemies/krakken/face/0").setReactsToSonar(true).addSelfToGameObject();
        new SizeComponent(k.face, 142, 187).addSelfToGameObject();

        // left eye
        k.leftEye = new MyGameObject();
        new PositionComponent(k.leftEye).addSelfToGameObject();
        new OriginComponent(k.leftEye).addSelfToGameObject();
        new RelativePositionComponent(k.leftEye, o, -45, 20).addSelfToGameObject();
        new StaticImageComponent(k.leftEye, "enemies/krakken/lEye/0").setReactsToSonar(true).addSelfToGameObject();
        new SizeComponent(k.leftEye, 31, 34).addSelfToGameObject();

        // right eye
        k.rightEye = new MyGameObject();
        new PositionComponent(k.rightEye).addSelfToGameObject();
        new OriginComponent(k.rightEye).addSelfToGameObject();
        new RelativePositionComponent(k.rightEye, o, 45, 20).addSelfToGameObject();
        new StaticImageComponent(k.rightEye, "enemies/krakken/rEye/0").setReactsToSonar(true).addSelfToGameObject();
        new SizeComponent(k.rightEye, 33, 35).addSelfToGameObject();

        // head
        k.head = new MyGameObject();
        new PositionComponent(k.head).addSelfToGameObject();
        new OriginComponent(k.head).addSelfToGameObject();
        new RelativePositionComponent(k.head, o, 30, 245).addSelfToGameObject();
        new StaticImageComponent(k.head, "enemies/krakken/head/0").setReactsToSonar(true).addSelfToGameObject();
        new SizeComponent(k.head, 277, 399).addSelfToGameObject();

        // left tentacle
        k.leftTentacle = new MyGameObject();
        new PositionComponent(k.leftTentacle).addSelfToGameObject();
        new OriginComponent(k.leftTentacle).addSelfToGameObject();
        new RelativePositionComponent(k.leftTentacle, o, -113, -14).addSelfToGameObject();
        new StaticImageComponent(k.leftTentacle, "enemies/krakken/lTentacle/0").setReactsToSonar(true).addSelfToGameObject();
        new SizeComponent(k.leftTentacle, 97, 232).addSelfToGameObject();

        // right tentacle
        k.rightTentacle = new MyGameObject();
        new PositionComponent(k.rightTentacle).addSelfToGameObject();
        new OriginComponent(k.rightTentacle).addSelfToGameObject();
        new RelativePositionComponent(k.rightTentacle, o, 94, -8).addSelfToGameObject();
        new StaticImageComponent(k.rightTentacle, "enemies/krakken/rTentacle/0").setReactsToSonar(true).addSelfToGameObject();
        new SizeComponent(k.rightTentacle, 119, 275).addSelfToGameObject();

        // short tentacles
        k.shortTentacles = new MyGameObject();
        new PositionComponent(k.shortTentacles).addSelfToGameObject();
        new OriginComponent(k.shortTentacles).addSelfToGameObject();
        new RelativePositionComponent(k.shortTentacles, o, -33, -105).addSelfToGameObject();
        new StaticImageComponent(k.shortTentacles, "enemies/krakken/squiglies/0").setReactsToSonar(true).addSelfToGameObject();
        new SizeComponent(k.shortTentacles, 121, 101).addSelfToGameObject();

        return Arrays.asList(o, k.head, k.shortTentacles, k.leftTentacle, k.rightTentacle, k.face, k.leftEye, k.rightEye);
    }
}
