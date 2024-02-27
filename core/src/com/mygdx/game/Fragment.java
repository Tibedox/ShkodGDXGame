package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;

public class Fragment extends SpaceObject{
    int img;
    float rotation, speedRotation;


    public Fragment(SpaceObject object) {
        img = MathUtils.random(0, 15);
        x = object.x;
        y = object.y;
        width = object.width/4;
        height = object.height/4;
        float v, a;
        v = MathUtils.random(1f, 8f);
        a = MathUtils.random(0f, 360f);
        vx = v*MathUtils.sin(a);
        vy = v*MathUtils.cos(a);
        speedRotation = MathUtils.random(-5f, 5f);
    }

    @Override
    void move() {
        super.move();
        rotation += speedRotation;
    }
}
