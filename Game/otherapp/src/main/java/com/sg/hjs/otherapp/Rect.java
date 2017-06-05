package com.sg.hjs.otherapp;

import top.yunp.androidgame2d.display.Rectangle;

/**
 * Created by hjs on 17-6-5.
 */

public class Rect extends Rectangle {
    private boolean black = false;

    public Rect(boolean isBlack) {
        super(Config.getCardWidth(), Config.getCardHeight(), isBlack ? 0xff000000 : 0xffffffff);
    }


    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
        setColor(black ? 0xff000000 : 0xffffffff);
    }
}
