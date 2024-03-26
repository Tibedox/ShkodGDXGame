package com.mygdx.game;

import static com.mygdx.game.ShkodGDXGame.SCR_WIDTH;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class ShkodButton {
    String text;
    BitmapFont font;
    float x, y;
    float width, height;

    public ShkodButton(String text, float x, float y, BitmapFont font) {
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y;
        GlyphLayout layout = new GlyphLayout(font, text);
        width = layout.width;
        height = layout.height;
    }

    public ShkodButton(String text, float y, BitmapFont font) {
        this.text = text;
        this.font = font;
        this.y = y;
        GlyphLayout layout = new GlyphLayout(font, text);
        width = layout.width;
        height = layout.height;
        x = SCR_WIDTH/2 - width/2;
    }

    boolean hit(float tx, float ty){
        return x < tx & tx < x+width & y-height < ty & ty < y;
    }
}
