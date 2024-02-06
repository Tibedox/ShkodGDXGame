package com.mygdx.game;

import static com.mygdx.game.ShkodGDXGame.SCR_HEIGHT;
import static com.mygdx.game.ShkodGDXGame.SCR_WIDTH;

public class Ship extends SpaceObject{

    Ship(){
        width = height = 128;
        x = SCR_WIDTH/2;
        y = SCR_HEIGHT/12;
    }

    @Override
    void move(){
        super.move();

        if (x < width/2 | x>SCR_WIDTH-width/2) vx = -vx;
    }
}
