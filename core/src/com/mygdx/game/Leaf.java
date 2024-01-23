package com.mygdx.game;

import java.util.Random;

public class Leaf {
    float x, y;
    float speedX, speedY;
    int width, height;

    Leaf(){
        width = 160;
        height = 160;
        Random rnd = new Random();
        speedX = rnd.nextFloat()*2;
        speedY = -rnd.nextFloat()*5-2;

        x = rnd.nextFloat()*(ShkodGDXGame.SCR_WIDTH+200)-200;
        y = rnd.nextFloat()*(ShkodGDXGame.SCR_HEIGHT+200)-200;
    }

    void move(){
        x += speedX;
        if (x > ShkodGDXGame.SCR_WIDTH) x = -width;
        y += speedY;
        if (y < -height) y = ShkodGDXGame.SCR_HEIGHT;
    }
}
