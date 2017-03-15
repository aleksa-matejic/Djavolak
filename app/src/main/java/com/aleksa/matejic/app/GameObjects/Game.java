package com.aleksa.matejic.app.GameObjects;

/**
 * Created by aleksa.matejic on 3/14/2017.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.aleksa.matejic.app.MainActivity;
import com.aleksa.matejic.app.R;

public class Game
{

    private enum State
    {
        PAUSED, WON, LOST, RUNNING
    }

    private SoundPool soundPool;

    private State state = State.PAUSED;

    private SurfaceHolder holder;
    private Resources resources;

    private Devil devil;
    //private Bat player;   ovde turi svoje objekte djavolak, peca djavolcica
    //private Bat opponent;

    private int screenWidth;
    private int screenHeight;

    private Paint textPaint;
    private Context context;

    private int[] sounds = new int[5];
    private long startTime;

    private int wins;
    private int loses;

    private int difficult;

    public Game(Context context, int width, int height, SurfaceHolder holder, Resources resources)
    {
        this.holder = holder;
        this.resources = resources;
        this.context = context;
        this.screenHeight = height;
        this.screenWidth = width;

        this.soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        devil = new Devil(width, height);
        //player = new Bat(width, height, Bat.Position.LEFT);
        //opponent = new Bat(width, height, Bat.Position.RIGHT);

        textPaint = new Paint();
        textPaint.setTextAlign(Align.CENTER);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(26);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void init()
    {

        Bitmap devilImage = BitmapFactory.decodeResource(resources, R.drawable.devil);

        devil.init(devilImage);

//        sounds[Sounds.START] = soundPool.load(context, R.raw.start, 1);
//        sounds[Sounds.WIN] = soundPool.load(context, R.raw.win, 1);
//        sounds[Sounds.LOSE] = soundPool.load(context, R.raw.lose, 1);
//        sounds[Sounds.BOUNCE1] = soundPool.load(context, R.raw.bounce1, 1);
//        sounds[Sounds.BOUNCE2] = soundPool.load(context, R.raw.bounce2, 1);
//        MainActivity.mp = MediaPlayer.create(context, R.raw.labirinto);
//        MainActivity.mp.setLooping(true);
//        MainActivity.mp.start();

//        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener()
//        {
//            public void onLoadComplete(SoundPool soundPool, int sampleId, int status)
//            {
//                if (sampleId == sounds[Sounds.START])
//                {
//                    soundPool.play(sounds[Sounds.START], 1, 1, 1, 0, 1);
//                }
//            }
//        });
    }

    public void update(long elapsed)
    {
        if (state == State.RUNNING)
        {
//            if ((System.currentTimeMillis() - startTime) > 5000)
//            {
//                ball.speedUp();
//                opponent.speedUp();
//                startTime = System.currentTimeMillis();
//            }
            updateGame(elapsed);
        }
    }

    private void initObjectPositions()
    {
        devil.initPosition();
    }

    public void updateGame(long elapsed)
    {
        devil.update(elapsed);
    }

    private void drawText(Canvas canvas, String text)
    {
        canvas.drawText(text, canvas.getWidth() / 2, canvas.getHeight() / 2, textPaint);
    }

    public void draw()
    {
        Canvas canvas = holder.lockCanvas();

        if (canvas != null)
        {
            canvas.drawColor(Color.WHITE);

            switch (state)
            {
                case LOST:
                    if (loses < 55)
                    {
                        drawText(canvas, "You Lost, try to win 55 times!");
                    }
                    else
                    {
                        drawText(canvas, "You'r NOOB!");
                        wins = 0;
                        loses = 0;
                    }
                    break;
                case PAUSED:
                    drawText(canvas, "Tap screen to start... and try to win 55 times!");
                    break;
                case RUNNING:
                    drawGame(canvas);
                    break;
                case WON:
                    if (wins < 55)
                    {
                        drawText(canvas, "You won, try to win 55 times!");
                    }
                    else
                    {
                        drawText(canvas, "You'r the GOD!");
                        wins = 0;
                        loses = 0;
                    }
                    break;
                default:
                    break;

            }

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawGame(Canvas canvas)
    {
        devil.draw(canvas);
        drawScore(canvas);
    }

    private void drawScore(Canvas canvas)
    {
        canvas.drawText(wins + "", 20, 20, textPaint);
        canvas.drawText(loses + "", canvas.getWidth() - 20, 20, textPaint);
    }

    public void onTouchEvent(MotionEvent event)
    {
        if (state == State.RUNNING)
        {
            if(event.getY() < this.screenHeight / 2)
            {
                devil.moveUp();
            }
            else
            {
                devil.moveDown();
            }
        }
        else
        {
            state = State.RUNNING;
            startTime = System.currentTimeMillis();
        }
    }

}
