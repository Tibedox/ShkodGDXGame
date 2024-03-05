package com.mygdx.game;

public class SpaceObject {
    float x, y;
    float vx, vy;
    float width, height;
    int type;

    void move(){
        x += vx;
        y += vy;
    }

    float getX(){
        return x-width/2;
    }

    float getY(){
        return y-height/2;
    }

    boolean overlap(SpaceObject object) {
        return Math.abs(x-object.x)<width/2+object.width/2 & Math.abs(y-object.y)<height/3+object.height/3;
    }
}
