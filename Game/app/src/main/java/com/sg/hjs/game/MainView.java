package com.sg.hjs.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PointF;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import top.yunp.androidgame2d.display.Display;
import top.yunp.androidgame2d.display.GameView;
import top.yunp.androidgame2d.events.TouchEvent;
import top.yunp.lib.java.event.EventListener;

/**
 * Created by hjs on 17-6-2.
 */

public class MainView extends GameView {

    public MainView(Context context) {
        super(context);

//        for (int i = 1; i < 9; i++) {
//            NumberCard nc = new NumberCard(8);
//            nc.setX(150);
//            nc.setY(50);
//            add(nc);
//
//
//            nc.touchDown.add(new EventListener<TouchEvent, Display>() {
//                @Override
//                public boolean onReceive(TouchEvent event, Display display) {
//
//                    NumberCard c = (NumberCard) display;
//
//                    if (c.getRecto().isVisible()) {
//                        c.turnToVerso();
//                    } else {
//                        c.turnToRecto();
//                    }
//                    return false;
//                }
//            });
//        }
        startGame();
    }

    private int currentNum = 1;
    private List<PointF> points = new ArrayList<>();  // 用来存储坐标点
    private List<NumberCard> cards = new ArrayList<>(); // 存储卡片

    /**
     * 启动游戏
     */
    private void startGame() {
        currentNum = 1;
        addCards();
    }

    /**
     * 向视图中添加卡片
     */
    private void addCards() {
        float globalOffsetX = Config.WIDTH / 2;
        float globalOffsetY = Config.HEIGHT / 2;

        // 重置坐标点数组
        points.clear();
        // 生成可用坐标点
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 16; j++) {
                points.add(new PointF(i * Config.WIDTH + globalOffsetX, j * Config.HEIGHT + globalOffsetY));
            }
        }

        PointF p;
        NumberCard nc;
        for (int i = 1; i <= 10; i++) {
            p = points.remove((int) (points.size() * Math.random()));
            nc = new NumberCard(i);
            nc.setX(p.x);
            nc.setY(p.y);

            add(nc);
            cards.add(nc);
            nc.touchDown.add(ncTouchDownHandler);
        }
    }

    EventListener<TouchEvent, Display> ncTouchDownHandler = new EventListener<TouchEvent, Display>() {
        @Override
        public boolean onReceive(TouchEvent event, Display display) {
            if (display instanceof NumberCard) {
                if (((NumberCard) display).getNumber() == currentNum) {
                    //System.out.println(((NumberCard) display).getNumber());
                    remove(display);
                    cards.remove(display);

                    // 将所有卡片翻转
//                    if (currentNum == 1) {
//                        for (NumberCard c : cards) {
//                            c.turnToVerso();
//                        }
//                    }

                    currentNum++;

                    // 当卡片个数小于等于 0 , 游戏结束
                    if (cards.size() <= 0) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("提醒")
                                .setMessage("GOOD  你赢了\n是否重来")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startGame();
                                    }
                                })
                                .show();
                    }

                } else {
                    Toast.makeText(getContext(), "点错了", Toast.LENGTH_SHORT).show();
                }
            }
            return false;
        }
    };
}
