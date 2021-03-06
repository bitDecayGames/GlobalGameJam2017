package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.MyGameObject;

import java.util.function.Consumer;

/**
 * The timer component will execute a given function (with a ref to this component's game object) when a given number of seconds has gone by.  You can reset the timer within the function by setting 'done' = false and setting the 'seconds' value to something > 0.  You can also remove the component by calling obj.removeComponent.
 */
public class TimerComponent extends AbstractComponent {
    public boolean done = false;
    public float seconds;
    protected Consumer<MyGameObject> func;

    public TimerComponent(MyGameObject obj, float seconds, Consumer<MyGameObject> func) {
        super(obj);
        this.seconds = seconds;
        this.func = func;
    }

    public void execute(){
        func.accept(obj);
    }
}
