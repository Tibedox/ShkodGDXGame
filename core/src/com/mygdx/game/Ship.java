package com.mygdx.game;

import static com.mygdx.game.ShkodGDXGame.*;

public class Ship extends SpaceObject{
    int lives = SHIP_LIVES;
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
        changePhase();
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
        vx = 0;
        x = SCR_WIDTH/2;
        y = SCR_HEIGHT/12;
    }
}
