package com.mygdx.game;

import static com.mygdx.game.ShkodGDXGame.*;

import com.badlogic.gdx.math.MathUtils;

public class EnemyShip {
    float x, y;
    float vx, vy;
    float width, height;

    EnemyShip(){
        width = height = 200;

        vx = MathUtils.random(-2, 2);
        vy = MathUtils.random(-7, -2);

        x = MathUtils.random(width/2, SCR_WIDTH-width/2);
        y = MathUtils.random(SCR_HEIGHT, SCR_HEIGHT*2);
    }

    void move(){
        x += vx;
        y += vy;

        if (x < width/2 | x>SCR_WIDTH-width/2) vx = -vx;
        if (y < -height) y = MathUtils.random(SCR_HEIGHT, SCR_HEIGHT*2);
    }

    float getX(){
        return x-width/2;
    }

    float getY(){
        return y-height/2;
    }
}
