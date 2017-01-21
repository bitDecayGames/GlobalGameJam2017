package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.trait.IShapeRotatableDraw;
import com.bitdecay.game.util.VectorMath;
import com.typesafe.config.Config;

/**
 * This component, when added to a game object, will draw a circle at the current position of the game object.
 */
public class DebugCircleComponent extends AbstractComponent implements IShapeRotatableDraw {
    public final Color color;
    public final float radius;

    public DebugCircleComponent(MyGameObject obj, Color color, float radius){
        super(obj);
        this.color = color.cpy();
        this.radius = radius;
    }

    public DebugCircleComponent(MyGameObject obj, Config conf) {
        super(obj, conf);
        this.radius = (float) conf.getDouble("radius");
        Config colorConf = conf.getConfig("color");
        this.color = new Color(
                (float) colorConf.getDouble("r"),
                (float) colorConf.getDouble("g"),
                (float) colorConf.getDouble("b"),
                (float) colorConf.getDouble("a")
        );
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Vector2 pos, float rotation) {
        shapeRenderer.setColor(color);
        shapeRenderer.circle(pos.x, pos.y, radius);
        shapeRenderer.line(pos, VectorMath.rotatePointByDegreesAroundZero(1, 0, rotation).scl(radius).add(pos));
    }
}
