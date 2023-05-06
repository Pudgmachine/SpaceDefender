package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerView extends SurfaceView implements Runnable {

    private Thread thread;
    private int score=0,sound;
    private final int scrX,scrY;
    private boolean isPlaying,gameOver=false;
    private final Paint paint;
    private final Background background_1,background_2;
    private final MyShip ship;
    private final List<Bullet> shots;
    private final Meteor[] meteors;
    int maxSpeed;
    private final Random random;
    private SoundPool soundPool;


    public PlayerView(Context context,int scrX,int scrY) {
        super(context);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            AudioAttributes audioAttributes=new AudioAttributes.Builder().
                    setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).
                    setUsage(AudioAttributes.USAGE_GAME).
                    build();

            soundPool=new SoundPool.Builder().
                    setAudioAttributes(audioAttributes).
                    build();

        }
        else{
            soundPool=new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }
        sound= soundPool.load(context,R.raw.soundsh,1);

        if(Game.getpDif().equals("Easy")) {
            maxSpeed=12;

        }
        else if(Game.getpDif().equals("Medium")) {
            maxSpeed=16;

        }
        else {
            maxSpeed=20;
        }

        this.scrX=scrX;
        this.scrY=scrY;

        background_1=new Background(scrX,scrY,getResources());
        background_2=new Background(scrX,scrY,getResources());

        ship=new MyShip(this,scrY,getResources());
        shots=new ArrayList<>();

        meteors=new Meteor[4];
        for(int i=0;i<4;i++){
            Meteor meteor=new Meteor(getResources());
            meteors[i]=meteor;
            meteors[i].speed=maxSpeed;
        }

        background_2.X=scrX;
        paint=new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);


        random= new Random();

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


        background_1.X-=5;
        background_2.X-=5;

        if(background_1.X+background_1.background.getWidth()<0){
            background_1.X=scrX;
        }
        if(background_2.X+background_2.background.getWidth()<0){
            background_2.X=scrX;
        }

        if(ship.isGoingUp){
            ship.y-=18;
        }
        else{
            ship.y+=18;
        }
        if(ship.y<0){
            ship.y=0;
        }
        if(ship.y>scrY-ship.height){
            ship.y= (int) (scrY-ship.height);
        }

        List<Bullet>away=new ArrayList<>();
        for (Bullet bullet:shots){
            if(bullet.x>scrX){
                away.add(bullet);
            }
            bullet.x+=50;

            for(Meteor met:meteors){

                    if (Rect.intersects(met.getCollision(), bullet.getCollision())) {
                        score++;
                        met.x = -500;
                        bullet.x = scrX + 400;
                        met.getShot = true;
                    }

            }
        }
        for (Bullet bullet:away){
            shots.remove(bullet);
        }

        for(Meteor met:meteors){

            met.x -= met.speed;
            if(met.x+met.width<0){

                if(!met.getShot){
                   gameOver=true;
                   return;
               }
                met.speed=random.nextInt(maxSpeed);

                if(met.speed<6){
                    met.speed=6;
                }
                met.x=scrX;
                met.y=met.height+random.nextInt(scrY-met.height*2);


                met.getShot=false;
            }

            if(Rect.intersects(met.getCollision(),ship.getCollision())){
                gameOver=true;
                return;
            }

        }

    }

    private void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas= getHolder().lockCanvas();


            canvas.drawBitmap(background_1.background,background_1.X,background_1.Y,paint);
            canvas.drawBitmap(background_2.background,background_2.X,background_2.Y,paint);

            canvas.drawBitmap(ship.getShip(),ship.x,ship.y,paint);
            canvas.drawText(score+"",scrX/2f,160,paint);

            for(Meteor met:meteors){
                canvas.drawBitmap(met.getMet(),met.x,met.y,paint);
            }

            if(gameOver){
                isPlaying=false;
                canvas.drawBitmap(ship.getDead(),ship.x,ship.y,paint);
                getHolder().unlockCanvasAndPost(canvas);
                return;
            }


            for(Bullet bullet:shots){
                canvas.drawBitmap(bullet.bullet,bullet.x,bullet.y,paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep(){
        try {
            thread.sleep(10);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX()<(float)scrX/5){
                    ship.isGoingUp=true;
                }
                break;
            case MotionEvent.ACTION_UP:
                ship.isGoingUp=false;
                if(event.getX()>(float)scrX/5){
                    ship.shoot=true;
                }
                break;
        }

        return true;
    }


    public void newBullet() {
        soundPool.play(sound,1,1,0,0,1);
        Bullet bullet=new Bullet(getResources());
        bullet.x=ship.x+ship.width;
        bullet.y=ship.y+ship.height/2;
        shots.add(bullet);
    }
}
