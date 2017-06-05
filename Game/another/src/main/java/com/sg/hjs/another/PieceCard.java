package com.sg.hjs.another;

import android.graphics.Color;
import android.graphics.Path;

import top.yunp.androidgame2d.display.Shape;
import top.yunp.androidgame2d.display.Sprite;
import top.yunp.androidgame2d.display.TextLine;
import top.yunp.androidgame2d.events.TweenEvent;
import top.yunp.androidgame2d.tween.ScaleTween;
import top.yunp.androidgame2d.tween.Tween;
import top.yunp.lib.java.event.EventListener;

/**
 * Created by hjs on 17-6-2.
 */

public class PieceCard extends Sprite {

    private int number = 0;
    private Sprite recto;//  正面
    private Shape verso; //  反面
    private ScaleTween scaleX1To0 = new ScaleTween(null, 1, 1, 0, 1);
    private ScaleTween scaleX0To1 = new ScaleTween(null, 0, 1, 1, 1);
    private boolean tweenRunning = false;


    public PieceCard(int number) {
        this.number = number;
        buildCard();
        buildTweens();
    }

    private void buildTweens() {
        scaleX1To0.setFrames(5);
        scaleX0To1.setFrames(5);

        scaleX1To0.tweenEnd.add(new EventListener<TweenEvent, Tween>() {
            @Override
            public boolean onReceive(TweenEvent event, Tween tween) {
                if (getRecto().isVisible()) {
                    getVerso().setScaleX(0);
                    showVerso();
                    scaleX0To1.setTarget(getVerso()).start();
                } else {
                    getRecto().setScaleX(0);
                    showRecto();
                    scaleX0To1.setTarget(getRecto()).start();
                }
                return false;
            }
        });

        scaleX0To1.tweenEnd.add(new EventListener<TweenEvent, Tween>() {
            @Override
            public boolean onReceive(TweenEvent event, Tween tween) {
                tweenRunning = false;
                return false;
            }
        });
    }

    private void buildCard() {

        //卡片的半宽 半高
        int halfWidth = Config.getCardWidthInPx() / 2;
        int halfHeight = Config.getCardHeightInPx() / 2;
        int margin = 5;

        // build recto
        recto = new Sprite();

        // build recto background
        Shape rectoBg = new Shape();
        // set up recto background color
        rectoBg.getPaint().setColor(Color.BLACK);
        rectoBg.getPath().addRect(-halfWidth + margin, -halfHeight + margin, halfWidth - margin, halfHeight - margin, Path.Direction.CW);
        // add rectoBg to recro
        recto.add(rectoBg);
        // add shape to recto
        add(recto);

        TextLine tl = new TextLine(getNumber() + "");
        //设置文字大小
        tl.setSize(Config.getTextSize());
        //将文字添加到正面图形中
        recto.add(tl);
        // 设置文字颜色
        tl.getPaint().setColor(Color.WHITE);
        // 计算文字的高度
        int lineHeight = tl.getLineHeight();
        // 将文字相对于卡片垂直居中
        tl.setY(lineHeight / 2);
        // 将文字相对于卡片水平居中
        tl.setX(-tl.getBounds().width());


        //build verso
        verso = new Shape();
        verso.getPaint().setColor(Color.BLUE);
        verso.getPath().addRect(-halfWidth, -halfHeight, halfWidth, halfHeight, Path.Direction.CW);
        add(verso);

        // show recto
        showRecto();

    }

    /**
     * show recto
     */
    public void showRecto() {
        recto.setVisible(true);
        verso.setVisible(false);
    }


    /**
     * evert card to recto
     */
    public void turnToRecto() {
        if (!tweenRunning && getVerso().isVisible()) {
            scaleX1To0.setTarget(getVerso()).start();
            tweenRunning = true;
        }
    }

    /**
     * evert card to verso
     */
    public void turnToVerso() {
        if (!tweenRunning && getRecto().isVisible()) {
            scaleX1To0.setTarget(getRecto()).start();
            tweenRunning = true;
        }
    }

    /**
     * show verso
     */
    public void showVerso() {
        recto.setVisible(false);
        verso.setVisible(true);
    }

    /**
     * Obtain card recto
     *
     * @return
     */
    public Sprite getRecto() {
        return recto;
    }

    /**
     * Obtain card verso
     *
     * @return
     */
    public Shape getVerso() {
        return verso;
    }

    public int getNumber() {
        return number;
    }
}
