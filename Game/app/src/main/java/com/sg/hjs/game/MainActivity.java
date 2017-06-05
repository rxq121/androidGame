package com.sg.hjs.game;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import top.yunp.androidgame2d.display.GameView;

public class MainActivity extends Activity {

    private MainView mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainView = new MainView(this);
        setContentView(mMainView);
    }

}
