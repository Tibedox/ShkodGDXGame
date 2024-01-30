package com.mygdx.game;

import static com.mygdx.game.ShkodGDXGame.SCR_HEIGHT;
import static com.mygdx.game.ShkodGDXGame.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

public class Stars {
    float x, y;
    float vy;
    float width, height;

    Stars(float y){
        width = SCR_WIDTH;
        height = SCR_HEIGHT;

        vy = -2;

        x = 0;
        this.y = y;
    }

    void move(){
        y += vy;
        if (y < -height) y = height;
    }
}
