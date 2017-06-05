package com.sg.hjs.otherapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import top.yunp.androidgame2d.display.GameView;
import top.yunp.androidgame2d.display.Rectangle;

/**
 * Created by hjs on 17-6-5.
 */

public class MainView extends GameView {
    public MainView(Context context) {
        super(context);
        shouldStartGame();
    }

    private void shouldStartGame()
    {
        new AlertDialog.Builder(getContext())
                .setTitle("欢迎")
                .setMessage("开始游戏")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .show();
    }

    public MainActivity getActivity() {
        return (MainActivity) getContext();
    }

    private void startGame() {
        // 初始化属性 （卡片的宽高）
        initProperties();

        addRectLines();
    }

    private void addRectLines() {
        RectLine line = new RectLine();
        add(line);
    }

    /**
     * 初始化一些属性
     */
    private void initProperties() {
        Config.setScreenWidth(getWidth());
        Config.setScreenHeight(getHeight());

        Config.setCardWidth(getWidth() / 4);
        Config.setCardHeight(getHeight() / 4);
    }
}
