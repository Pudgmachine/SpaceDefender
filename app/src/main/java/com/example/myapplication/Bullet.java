package com.example.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Bullet {
    int x,y,height,width;
    Bitmap bullet;

    Bullet(Resources res){
        bullet= BitmapFactory.decodeResource(res,R.drawable.myshot);

        height=bullet.getHeight();
        width=bullet.getWidth();

        bullet=Bitmap.createScaledBitmap(bullet,width/25,height/25,false);
    }

    Rect getCollision(){
        return new Rect(x,y,x+width,y+height);
    }
}
