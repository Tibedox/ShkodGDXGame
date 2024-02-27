package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;

public class Fragment extends SpaceObject{
    int img;

    public Fragment(SpaceObject object) {
        img = MathUtils.random(0, 15);
        x = object.x;
        y = object.y;
        width = object.width/4;
        height = object.height/4;
        vx = MathUtils.random(-7f, 7f);
        vy = MathUtils.random(-7f, 7f);
    }
}
