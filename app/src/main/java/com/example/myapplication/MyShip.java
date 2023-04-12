package com.example.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class MyShip {
    public boolean isGoingUp=false,shoot=false;
    int x, y, width, height;
    Bitmap ship,dead;
    private PlayerView view;

    MyShip(PlayerView view,int scrY, Resources res) {
        ship = BitmapFactory.decodeResource(res, R.drawable.myship);
        dead = BitmapFactory.decodeResource(res, R.drawable.dead);

        this.view=view;
        width = ship.getWidth();
        height = ship.getHeight();

        width /= 12;
        height /= 12;

        ship = Bitmap.createScaledBitmap(ship, width, height, false);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);


        x = 10;
        y = scrY/2;
    }

    Bitmap getShip(){
      if(shoot==true){
        shoot=false;
            view.newBullet();
            return ship;
        }

        return ship;
    }


    Rect getCollision(){
        return new Rect(x,y,x+width,y+height);
    }

    Bitmap getDead(){
        return dead;
    }
}


