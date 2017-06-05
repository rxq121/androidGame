package com.sg.hjs.another;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by hjs on 17-6-2.
 */

public class Config {
    private static int cardWidthInPx, cardHeightInPx, textSize;

    public static void initConfig(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        cardWidthInPx = (int) (displayMetrics.density * CARD_WIDTH_IN_DP);
        cardHeightInPx = (int) (displayMetrics.density * CARD_HEIGHT_IN_DP);
        textSize = (int) (0.8 * cardHeightInPx);
    }

    public static final int CARD_WIDTH_IN_DP = 60;
    public static final int CARD_HEIGHT_IN_DP = 60;


    public static int getCardWidthInPx() {
        return cardWidthInPx;
    }

    public static int getCardHeightInPx() {
        return cardHeightInPx;
    }

    public static int getTextSize() {
        return textSize;
    }
}
