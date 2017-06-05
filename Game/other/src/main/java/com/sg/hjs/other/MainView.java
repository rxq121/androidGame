package com.sg.hjs.other;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import top.yunp.androidgame2d.display.GameView;

/**
 * Created by hjs on 17-6-5.
 */

public class MainView extends GameView {

    /**
     * 方块被选中时的事件监听
     */
    private RectLine.OnRectSelected rectSelectedHandler = new RectLine.OnRectSelected() {
        @Override
        public void onSelect(Rect rect, RectLine target) {
//            System.out.println(rect + ">>>>>>>" + target);

            // 如果 点中的是黑色方块，则在最上边添加一行，游戏继续，否则可提示用户游戏结束
            if (rect.isBlack()) {
                // 只有在所有动画效果结束后才能执行新的动画效果
                if (isAllTweenEnded()) {
                    // 在最上边添加一行
                    addNewLineAtIndex(-1);
                    //将所有方块向下移一行
                    moveAllLinesDown();
                }
            } else {

                new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("重新再来")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startGame();
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        })
                        .show();
                Toast.makeText(getContext(), "点错了", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 方块向下移动动画执行结束时的事件处理
     */
    private RectLine.OnLineMoveDownTweenEnd lineMoveTweenEndHandler = new RectLine.OnLineMoveDownTweenEnd() {
        @Override
        public void onEnd(RectLine target) {
            //如果当前结束动画的方块行的索引是4,则意味着该行已经超出屏幕下边,则将该行移除
            if (target.getPositionIndex() >= 4) {
                rectLines.remove(target);
                remove(target);
            }
        }
    };


    //用于存放所有方块的数组
    private List<RectLine> rectLines = new ArrayList<>();

    public MainView(Context context) {
        super(context);

        //设置游戏刷新的帧频为 50
        setFps(50);
        //设置游戏背景颜色为灰色
        setGameViewBackground(0xffcccccc);
        // 呈现对话框 用于启动游戏
        shouldStartGame();
    }

    private void shouldStartGame() {
        new AlertDialog.Builder(getContext())
                .setTitle("欢迎")
                .setMessage("是否开始游戏")
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
        //初始化一些属性，这些属性将是游戏中元素的重要数据,如根据屏幕动态获取卡片的宽高
        initProperties();

        //在屏幕上添加4*4方块矩阵
        addRectLines();
    }

    private void addRectLines() {

        for (int i = 0; i < 4; i++) {
            addNewLineAtIndex(i);
        }
    }

    /**
     * 将所有行往下移一行
     */
    private void moveAllLinesDown() {
        for (RectLine rl : rectLines) {
            // 将位置索引自加 1
            rl.setPositionIndex(rl.getPositionIndex() + 1);
            // 根据位置索引将行移动到特定位置
            rl.moveToTargetPositionByIndex();
        }
    }

    /**
     * 在特定的索引位置添加一行方块 ，每行方块数量是4个
     *
     * @param i
     */
    private void addNewLineAtIndex(int i) {
        RectLine line = new RectLine();
        line.setOnRectSelected(rectSelectedHandler);
        line.setPositionIndex(i);

        //根据索引位置确定它的坐标位置
        line.setPositionYByIndex();
        line.setOnLineMoveDownTweenEnd(lineMoveTweenEndHandler);

        //将行添加到GameView视图中
        add(line);
        //在数组中记录已被添加到视图中的方块对象
        rectLines.add(line);
    }

    /**
     * 检测是否所有的方块都已停止动画
     *
     * @return
     */
    private boolean isAllTweenEnded() {
        for (RectLine l : rectLines) {
            if (l.isTweenRunning()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 初始化属性
     */
    private void initProperties() {
        Config.setScreenWidth(getWidth());
        Config.setScreenHeight(getHeight());

        Config.setCardWidth(getWidth() / 4);
        Config.setCardHeight(getHeight() / 4);
    }
}
