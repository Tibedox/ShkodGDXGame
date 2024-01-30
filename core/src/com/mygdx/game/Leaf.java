package com.mygdx.game;

import static com.mygdx.game.ShkodGDXGame.*;

import com.badlogic.gdx.math.MathUtils;

public class Leaf {
    float x, y;
    float vx, vy;
    int width, height;

    Leaf(){
        width = height = MathUtils.random(100, 200);

        vx = MathUtils.random(0, 2f);
        vy = MathUtils.random(-7, -2);

        x = MathUtils.random(0, SCR_WIDTH-width);
        y = MathUtils.random(0, SCR_HEIGHT-height);
    }

    void move(){
        x += vx;
        if (x > SCR_WIDTH) x = -width;
        y += vy;
        if (y < -height) y = SCR_HEIGHT;
    }
}
