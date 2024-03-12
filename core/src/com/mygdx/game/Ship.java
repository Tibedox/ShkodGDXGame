package com.mygdx.game;

import static com.mygdx.game.ShkodGDXGame.SCR_HEIGHT;
import static com.mygdx.game.ShkodGDXGame.SCR_WIDTH;

public class Ship extends SpaceObject{
    int lives = 1;
    boolean isAlive;

    Ship(){
        type = 0;
        width = height = 128;
        respawn();
    }

    @Override
    void move(){
        super.move();
        outOfScreen();
    }

    void hit(float tx){
        vx = (tx-x)/10;
    }

    void outOfScreen(){
        if (x < width/2){
            vx = 0;
            x = width/2;
        }
        if(x > SCR_WIDTH-width/2){
            vx = 0;
            x = SCR_WIDTH-width/2;
        }
    }

    void respawn() {
        isAlive = true;
        x = SCR_WIDTH/2;
        y = SCR_HEIGHT/12;
    }
}
