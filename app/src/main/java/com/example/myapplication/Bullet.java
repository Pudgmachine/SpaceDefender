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

        height/=25;
        width/=25;

        bullet=Bitmap.createScaledBitmap(bullet,width,height,false);
    }

    Rect getCollision(){
        return new Rect(x,y,x+width,y+height);
    }
}
