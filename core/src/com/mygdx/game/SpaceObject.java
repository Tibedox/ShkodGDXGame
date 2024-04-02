package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;

public class SpaceObject {
    float x, y;
    float vx, vy;
    float width, height;
    int type;
    int phase, nPhases = 12;
    long timeLastPhase, timeChangePhaseInterval = 30;

    void move(){
        x += vx;
        y += vy;
    }

    void changePhase() {
        if(TimeUtils.millis() > timeLastPhase+timeChangePhaseInterval) {
            if (++phase == nPhases) phase = 0;
            timeLastPhase = TimeUtils.millis();
        }
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
