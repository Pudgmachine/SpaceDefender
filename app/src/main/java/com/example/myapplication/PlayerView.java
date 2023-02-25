package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class PlayerView extends SurfaceView implements Runnable {

    private Thread thread;
    private int scrX,scrY;
    private boolean isPlaying;
    private Paint paint;
    private Background background_1,background_2;

    public PlayerView(Context context,int scrX,int scrY) {
        super(context);

        this.scrX=scrX;
        this.scrY=scrY;

        background_1=new Background(scrX,scrY,getResources());
        background_2=new Background(scrX,scrY,getResources());

        background_2.Y=scrY;//x
        paint=new Paint();
    }

    @Override
    public void run() {
        while(isPlaying){
            updateScreen();
            draw();
            sleep();
        }
    }

    private void updateScreen(){
        background_1.Y-=5;
        background_2.Y-=5;

        if(background_1.Y+background_1.background.getHeight()<0){
            background_1.Y=scrY;
        }
        if(background_2.Y+background_2.background.getHeight()<0){
            background_2.Y=scrY;
        }
    }

    private void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas= getHolder().lockCanvas();

            canvas.drawBitmap(background_1.background,background_1.X,background_1.Y,paint);
            canvas.drawBitmap(background_2.background,background_2.X,background_2.Y,paint);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep(){
        try {
            thread.sleep(15);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void resume(){
        isPlaying=true;
        thread=new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            isPlaying=false;
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
