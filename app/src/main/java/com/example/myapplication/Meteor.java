package com.example.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Meteor {

    int x=0,y,width,height,speed;
    Bitmap meteor;
    boolean getShot=true;

    Meteor(Resources res){

        meteor= BitmapFactory.decodeResource(res,R.drawable.met1);


        width=meteor.getWidth();
        height=meteor.getHeight();

        width/=20;
        height/=20;

        meteor=Bitmap.createScaledBitmap(meteor,width,height,false);


        y=-height;
    }

    Rect getCollision(){
        return new Rect(x,y,x+width,y+height);
    }

    Bitmap getMet (){
        return meteor;
    }

}
