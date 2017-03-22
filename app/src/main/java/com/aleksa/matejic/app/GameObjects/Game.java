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
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.aleksa.matejic.app.MainActivity;
import com.aleksa.matejic.app.R;
import com.aleksa.matejic.app.utils.DatabaseHelper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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

    private Background background;
    private Devil devil;
    private LinkedList<Cloud> clouds;
    private Iterator<Cloud> iterator;
    private GuardianAngel peca;
    private Arrow arrow;
    private Angel angel;
    private Statistics statistics;
    private DatabaseHelper db;
    //private Bat player;   ovde turi svoje objekte djavolak, peca djavolcica
    //private Bat opponent;

    Bitmap whiteCloudImage;
    Bitmap blackCloudImage;
    Bitmap pecaImage;
    Bitmap pecaImageNoArrow;
    Bitmap arrowImage;

    private int screenWidth;
    private int screenHeight;

    private Paint textPaint;
    private Context context;

    private int[] sounds = new int[5];
    private long startTime;
    private long cloudTime;
    private long pecaTime;

    private int cloudShowUpSpeed;
    private int cloudShowUp;
    private int rndTime;

    private int wins;
    private int loses;

    private int difficult;

    private Random rnd;

    public Game(Context context, int width, int height, SurfaceHolder holder, Resources resources)
    {
        this.holder = holder;
        this.resources = resources;
        this.context = context;
        this.screenHeight = height;
        this.screenWidth = width;

        this.soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        background = new Background(screenWidth, screenHeight);
        devil = new Devil(width, height);
        clouds = new LinkedList<>();
        peca = new GuardianAngel(width, height);
        arrow = new Arrow(width, height);
        angel = new Angel(width, height);
        statistics = new Statistics("Player Name HC");
        db = new DatabaseHelper(context);
        rnd = new Random();
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
        Bitmap backgroundImage = BitmapFactory.decodeResource(resources, R.drawable.game_background);
        whiteCloudImage = BitmapFactory.decodeResource(resources, R.drawable.white_cloud);
        blackCloudImage = BitmapFactory.decodeResource(resources, R.drawable.black_cloud);
        pecaImage = BitmapFactory.decodeResource(resources, R.drawable.guardian_angel);
        pecaImageNoArrow = BitmapFactory.decodeResource(resources, R.drawable.guardian_angel_without_arrow);
        arrowImage = BitmapFactory.decodeResource(resources, R.drawable.arrow);
        Bitmap angelImage = BitmapFactory.decodeResource(resources, R.drawable.angel);

        rndTime = rnd.nextInt(3000);

        background.init(backgroundImage);
        devil.init(devilImage);
        peca.init(pecaImage);
        arrow.init(arrowImage);
        angel.init(angelImage);

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
            if (System.currentTimeMillis() - cloudTime > 60)
            {
                if (rnd.nextInt(cloudShowUpSpeed) < cloudShowUp)
                {
                    //ball.speedUp();
                    //opponent.speedUp();
                    if (clouds.size() < 6)
                    {
                        Cloud cloud;
                        if (rnd.nextInt(2) == 0)
                        {
                            cloud = new Cloud(screenWidth, screenHeight, Cloud.Type.BLACK);
                            cloud.init(blackCloudImage);
                        }
                        else
                        {
                            cloud = new Cloud(screenWidth, screenHeight, Cloud.Type.WHITE);
                            cloud.init(whiteCloudImage);
                        }
                        clouds.add(cloud);
                    }
                }
                cloudTime = System.currentTimeMillis();
            }
            //if()
            updateGame(elapsed);
        }
    }

    private void initObjectPositions()
    {
        devil.initPosition();
    }

    public void updateGame(long elapsed)
    {
        background.update(15);
        devil.update(elapsed);
        angel.update(elapsed);
        arrow.update(elapsed);
        statistics.update();

        // put here clouds update
        Cloud cloud;
        iterator = clouds.iterator();
        while (iterator.hasNext())
        {
            cloud = iterator.next();
            if (cloud.getX() > ((-cloud.getScreenRect().width())))
            {
                cloud.update(elapsed);
            }
            else
            {
                iterator.remove();
            }
        }

        // put here guardian angel update
        if ((System.currentTimeMillis() - pecaTime) > 7000 + rndTime)
        {
            peca.update(elapsed);
            if (peca.getX() < screenWidth - 300 && peca.getWay() == 1)
            {
                peca.setImage(pecaImageNoArrow);
                peca.setWay(-1);
                arrow.initPosition(peca.getX(), peca.getY() - 50, peca.image.getHeight());
                arrow.setMove(true);
            }
            if (peca.getX() > screenWidth && peca.getWay() == -1)
            {
                peca.setImage(pecaImage);
                peca.setWay(1);
                pecaTime = System.currentTimeMillis();
                rndTime = rnd.nextInt(3000);
            }

        }

        // put here arrow collision detection
        if (devil.arrowCollisionDetection(arrow))
        {
            Log.d("arrow collision", "true");
            arrow.setMove(false);
        }

        // put here clouds collision detection
        if (devil.cloudsCollisionDetection(clouds))
        {
            Log.d("cloud collision", "true");
        }

        // put here angel collision detection
        if(devil.angelCollisionDetection(angel))
        {
            Log.d("angel collision", "true");
        }

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
        background.draw(canvas);
        devil.draw(canvas);
        for (Cloud cloud : clouds)
        {
            cloud.draw(canvas);
        }
        if ((System.currentTimeMillis() - pecaTime) > 7000 + rndTime)
        {
            peca.draw(canvas);
        }
        if (arrow.isMove())
            arrow.draw(canvas);
        if (angel.getX() < screenWidth && angel.getX() > -angel.getRect().width())
            angel.draw(canvas);
        drawScore(canvas);
        statistics.draw(canvas);
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
            if (event.getAction() == android.view.MotionEvent.ACTION_DOWN || event.getAction() == android.view.MotionEvent.ACTION_MOVE)
            {

                if (event.getY() < this.screenHeight / 2 && event.getX() < this.screenWidth / 2)
                {
                    devil.moveUp();
                }
                if (event.getY() > this.screenHeight / 2 && event.getX() < this.screenWidth / 2)
                {
                    devil.moveDown();
                }
                if (event.getX() < this.screenWidth - this.screenWidth / 4 && event.getX() > this.screenWidth / 2)
                {
                    slowDown();
                }
                if (event.getX() > this.screenWidth - this.screenWidth / 4 && event.getX() > this.screenWidth / 2)
                {
                    speedUp();
                }
            }
            else if (event.getAction() == android.view.MotionEvent.ACTION_UP)
            {
                devil.moveStop();
                normalSpeed();
            }
        }
        else
        {
            state = State.RUNNING;
            startTime = System.currentTimeMillis();
            cloudTime = System.currentTimeMillis();
            pecaTime = System.currentTimeMillis();
            cloudShowUpSpeed = 30000;
            cloudShowUp = 2000;
        }
    }

    private void speedUp()
    {
        background.setSpeed(100);
        Cloud.setSpeed(20);
        angel.setWay(1);
    }

    private void slowDown()
    {
        background.setSpeed(20);
        Cloud.setSpeed(5);
        angel.setWay(-1);
    }

    private void normalSpeed()
    {
        background.setSpeed(50);
        Cloud.setSpeed(10);
        angel.setWay(0);
    }

}
