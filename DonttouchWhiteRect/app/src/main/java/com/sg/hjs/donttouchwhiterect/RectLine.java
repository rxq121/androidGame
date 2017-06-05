package com.sg.hjs.donttouchwhiterect;

import java.util.ArrayList;
import java.util.List;

import top.yunp.androidgame2d.display.Container;

/**
 * Created by hjs on 17-6-5.
 */

public class RectLine extends Container {

    // 用来 存放每一行的情况
    private List<Rect> rects = new ArrayList<>();

    public RectLine() {
        Rect r;
        //在行中添加 4 个方块
        for (int i = 0; i < 4; i++) {
            r = new Rect(false);
            // 设置方块之间的间隙
            r.setBorderWidth(5);
            r.setX(Config.getCardWidth() * i);
            add(r);
            rects.add(r);
        }

        // 随机将其中一个方块设置为黑色
        rects.get((int) (Math.random() * rects.size())).setBlack(true);
    }
}
