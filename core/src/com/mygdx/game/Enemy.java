package com.mygdx.game;

import static com.mygdx.game.ShkodGDXGame.*;

import com.badlogic.gdx.math.MathUtils;

public class Enemy extends SpaceObject{
    static float scaleSpeed = 1;

    Enemy(){
        type = 1;
        width = height = 128;
        vx = MathUtils.random(-2f, 2f);
        vy = MathUtils.random(-7f, -2f) * scaleSpeed;
        x = MathUtils.random(width/2, SCR_WIDTH-width/2);
        y = MathUtils.random(SCR_HEIGHT, SCR_HEIGHT*2);
    }

    @Override
    void move(){
        super.move();
        changePhase();
    }

    boolean outOfScreen() {
        if (x < width/2 | x>SCR_WIDTH-width/2) vx = -vx;
        if (y < -height/2) return true;
        return false;
    }
}
