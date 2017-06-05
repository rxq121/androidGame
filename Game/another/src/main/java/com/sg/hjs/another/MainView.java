package com.sg.hjs.another;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PointF;

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

    private int currentNum = 1;
    private PieceCard mPieceCard;
    private List<PointF> mPointFs = new ArrayList<>();// 存储 坐标点
    private List<PieceCard> mCards = new ArrayList<>(); // 存储 卡片
    private int level = 3, topLevel = 6;


    public MainView(Context context) {
        super(context);
        Config.initConfig(context);
        shouldStartGame();
    }

    private void shouldStartGame() {
        new AlertDialog.Builder(getContext())
                .setMessage("开始游戏")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartGame();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .show();
    }

    private void startGame() {
        currentNum = 1;
        cleanGameData();
        addCards();
    }

    private void restartGame() {
        level = 3;
        startGame();
    }

    private void cleanGameData() {
        // 重置坐标点数组 防止重复
        mPointFs.clear();
    }

    private void addCards() {

        int hCount = getWidth() / Config.getCardWidthInPx();
        int vCount = getHeight() / Config.getCardHeightInPx();

        float cardsMapWidth = hCount * Config.getCardWidthInPx();
        float cardsMapHeight = vCount * Config.getCardHeightInPx();

        float xRemain = getWidth() - cardsMapWidth;
        float yRemain = getHeight() - cardsMapHeight;

        float globalOffsetX = Config.getCardWidthInPx() / 2 + xRemain / 2;
        float globalOffsetY = Config.getCardHeightInPx() / 2 + yRemain / 2;


//        mPointFs.clear();
        // 生成可用的坐标点
        for (int i = 1; i < hCount; i++) {
            for (int j = 1; j < vCount; j++) {
                mPointFs.add(new PointF(Config.CARD_WIDTH_IN_DP * i + globalOffsetX, globalOffsetY + Config.CARD_HEIGHT_IN_DP * j));
            }
        }

        for (int i = 0; i <= level; i++) {
            mPieceCard = new PieceCard(i);
            // 将存储的坐标点 移除 防止生成的卡片重叠
            PointF pointF = mPointFs.remove((int) (mPointFs.size() * Math.random()));
            mPieceCard.setY(pointF.y);
            mPieceCard.setX(pointF.x);

            add(mPieceCard);
            mCards.add(mPieceCard);
            mPieceCard.touchDown.add(pcTouch);

        }
//        mPieceCard = new PieceCard();
//        mPieceCard.setX(150);
//        mPieceCard.setY(150);
//        add(mPieceCard);
//
//        mPieceCard.touchDown.add(new EventListener<TouchEvent, Display>() {
//            @Override
//            public boolean onReceive(TouchEvent event, Display display) {
//
//                if (mPieceCard.getRecto().isVisible()) {
//                    mPieceCard.turnToVerso();
//                } else {
//                    mPieceCard.turnToRecto();
//                }
//                return false;
//            }
//        });
    }

    EventListener<TouchEvent, Display> pcTouch = new EventListener<TouchEvent, Display>() {
        @Override
        public boolean onReceive(TouchEvent event, Display display) {
            System.out.println(mPieceCard.getNumber());
//            if (mPieceCard.getRecto().isVisible()) {
//                mPieceCard.turnToVerso();
//            } else {
//                mPieceCard.turnToRecto();
//            }

            if (display instanceof PieceCard) {
                if (((PieceCard) display).getNumber() == currentNum) {
                    remove(display);
                    mCards.remove(display);

                    currentNum++;

                    if (mCards.size() <= 0) {
                        if (level < topLevel) {
                            showGoToNextLevelDialog();
                        } else {
                            showWinDialog();
                        }
                    } else {
                        showFailDialog();
                    }
                }
            }
            return false;
        }
    };

    private void showFailDialog() {
        new AlertDialog.Builder(getContext())
                .setMessage("重来")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartGame();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .show();
    }

    private void showWinDialog() {

        new AlertDialog.Builder(getContext())
                .setMessage("你贏了,再來")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartGame();
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                }).show();
    }

    private void showGoToNextLevelDialog() {
        new AlertDialog.Builder(getContext())
                .setMessage("下一关")
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nextLevel();
                    }
                }).show();
    }

    private void nextLevel() {
        level++;
        if (level <= topLevel) {
            startGame();
        }
    }

    public MainActivity getActivity() {
        return (MainActivity) getContext();
    }
}
