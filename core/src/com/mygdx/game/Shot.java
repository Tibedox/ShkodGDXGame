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

    boolean outOfScreen(){
        return y > SCR_HEIGHT+height/2;
    }

    boolean overlap(Enemy enemy) {
        return Math.abs(x-enemy.x)<width/2+enemy.width/2 & Math.abs(y-enemy.y)<height/4+enemy.height/4;
    }
}
