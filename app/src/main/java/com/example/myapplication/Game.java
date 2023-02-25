package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;

public class Game extends AppCompatActivity {
    public static final String EXTRA_DIF="difficulty";
    private PlayerView playerView;
    private static String pDifficulty;
    public void setpDifficulty(String dif){
        pDifficulty=dif;
    }
    public static String getpDif() {
        return pDifficulty;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent=getIntent();
        setpDifficulty(intent.getStringExtra(EXTRA_DIF));

        Point point=new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        playerView=new PlayerView(this,point.x,point.y);
        setContentView(playerView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        playerView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerView.resume();
    }
}