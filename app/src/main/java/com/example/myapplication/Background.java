package com.example.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {
    int X=0,Y=0;
    Bitmap background;

    Background(int scrX, int scrY, Resources res){
        if(Game.getpDif().equals("Easy")) {
            background = BitmapFactory.decodeResource(res, R.drawable.easyback);
            background = Bitmap.createScaledBitmap(background, scrX, scrY, false);
        }
        else if(Game.getpDif().equals("Medium")) {
            background = BitmapFactory.decodeResource(res, R.drawable.mediumback);
            background = Bitmap.createScaledBitmap(background, scrX, scrY, false);
        }
        else {
            background = BitmapFactory.decodeResource(res, R.drawable.hardback);
            background = Bitmap.createScaledBitmap(background, scrX, scrY, false);
        }

    }
}
