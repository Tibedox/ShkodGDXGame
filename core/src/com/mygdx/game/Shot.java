package com.mygdx.game;

import static com.mygdx.game.ShkodGDXGame.SCR_HEIGHT;

public class Shot extends SpaceObject{

    Shot(float shipX, float shipY){
        width = height = 128;

        vx = 0;
        vy = 8f;

        x = shipX;
        y = shipY;
    }

    @Override
    void move(){
        super.move();

        if (y > SCR_HEIGHT+height/2) y = SCR_HEIGHT/12;
    }
}
