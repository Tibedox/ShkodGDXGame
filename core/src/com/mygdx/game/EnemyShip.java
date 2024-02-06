package com.mygdx.game;

import static com.mygdx.game.ShkodGDXGame.*;

import com.badlogic.gdx.math.MathUtils;

public class EnemyShip extends SpaceObject{

    EnemyShip(){
        width = height = 128;

        vx = MathUtils.random(-2f, 2f);
        vy = MathUtils.random(-7f, -2f);

        x = MathUtils.random(width/2, SCR_WIDTH-width/2);
        y = MathUtils.random(SCR_HEIGHT, SCR_HEIGHT*2);
    }

    @Override
    void move(){
        super.move();

        if (x < width/2 | x>SCR_WIDTH-width/2) vx = -vx;
        if (y < -height) y = MathUtils.random(SCR_HEIGHT, SCR_HEIGHT*2);
    }
}
