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
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.aleksa.matejic.app.GameActivity;
import com.aleksa.matejic.app.MainActivity;
import com.aleksa.matejic.app.OptionsActivity;
import com.aleksa.matejic.app.R;
import com.aleksa.matejic.app.utils.DatabaseHelper;
import com.aleksa.matejic.app.utils.SharedPreferencesStore;

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

    private Bitmap whiteCloudImage;
    private Bitmap blackCloudImage;
    private Bitmap pecaImage;
    private Bitmap pecaImageNoArrow;
    private Bitmap arrowImage;

    private int screenWidth;
    private int screenHeight;

    private Paint textPaint;
    private Paint strokePaint;
    private Context context;

    private int[] sounds = new int[5];
    private long startTime;
    private long cloudTime;
    private long pecaTime;
    private long slowDownTime;

    Vibrator vibrator;

    boolean isSoundOn;
    boolean isVibratorOn;

    private int cloudShowUpSpeed;
    private int cloudShowUp;
    private int rndTime;

    private int wins;
    private int loses;
    private int hit;

    private int difficult;
    private int score;

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
        db = new DatabaseHelper(context);
        vibrator = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
        rnd = new Random();
        hit = 0;

        textPaint = new Paint();
        textPaint.setTextAlign(Align.CENTER);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLUE);

        textPaint.setTextSize((int) (50 * 2.2));
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);


        textPaint = new Paint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.YELLOW);
        // Aleksa TODO: get scale from sprite
        textPaint.setTextSize((int) (40 * 2.2));
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        strokePaint = new Paint();
        strokePaint.setTextAlign(Paint.Align.CENTER);
        strokePaint.setAntiAlias(true);
        strokePaint.setColor(Color.BLACK);
        // Aleksa TODO: get scale from sprite
        strokePaint.setTextSize((int) (40 * 2.2));
        strokePaint.setTypeface(Typeface.DEFAULT_BOLD);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(6);

        isSoundOn = SharedPreferencesStore.getInstance(context).readBoolean(SharedPreferencesStore.getInstance(context).SOUNDS);
        isVibratorOn = SharedPreferencesStore.getInstance(context).readBoolean(SharedPreferencesStore.getInstance(context).VIBRATION);

        GameActivity.mp = MediaPlayer.create(context, R.raw.labirinto);
        GameActivity.mp.setLooping(true);
        GameActivity.mp.setVolume(0.5f,0.5f);
        if(isSoundOn)
            GameActivity.mp.start();
    }

    public void init()
    {
        String currentPlayerStored = SharedPreferencesStore.getInstance(context).readString(SharedPreferencesStore.getInstance(context).CURRENT_PLAYER);
        statistics = new Statistics(currentPlayerStored);
        //statistics = new Statistics("Player Name HC");

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

        sounds[Sounds.START] = soundPool.load(context, R.raw.start, 1);
        sounds[Sounds.WIN] = soundPool.load(context, R.raw.win, 1);
        sounds[Sounds.LOSE] = soundPool.load(context, R.raw.lose, 1);
        sounds[Sounds.BOUNCE1] = soundPool.load(context, R.raw.bounce1, 1);
        sounds[Sounds.BOUNCE2] = soundPool.load(context, R.raw.bounce2, 1);

        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener()
        {
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status)
            {
                if (sampleId == sounds[Sounds.START] && isSoundOn)
                {
                    soundPool.play(sounds[Sounds.START], 1, 1, 1, 0, 1);
                }
            }
        });
    }

    public void update(long elapsed)
    {
        if (state == State.RUNNING)
        {
            if (System.currentTimeMillis() - cloudTime > 60)
            {
                if (rnd.nextInt(cloudShowUpSpeed) < cloudShowUp)
                {
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

            if (hit >= 0)
                hit--;
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

        // put here clouds update and collision detection
        cloudsUpdateAndCollisionDetection(elapsed);

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
                peca.initPosition();
            }

        }

        // put here arrow collision detection
        if (arrowCollisionDetection())
        {
            Log.d("arrow collision", "true");
            arrow.setMove(false);
            // TODO: game over
            if(isSoundOn)
                soundPool.play(sounds[Sounds.LOSE], 1, 1, 1, 0, 1);
            if(isVibratorOn)
                vibrator.vibrate(700);
            state = State.LOST;
            resetGame();
        }

        // put here angel collision detection
        if (angelCollisionDetection())
        {
            Log.d("angel collision", "true");
            // TODO: game won
            if(isSoundOn)
                soundPool.play(sounds[Sounds.WIN], 1, 1, 1, 0, 1);
            if(isVibratorOn)
                vibrator.vibrate(1200);
            state = State.WON;
            score = statistics.getScore();
            db.insertData(statistics);
            resetGame();
        }
    }

    private void drawText(Canvas canvas, String text)
    {
        canvas.drawText(text, canvas.getWidth() / 2, (canvas.getHeight() / 2) + (textPaint.getTextSize() / 2), textPaint);
        canvas.drawText(text, canvas.getWidth() / 2, (canvas.getHeight() / 2) + (textPaint.getTextSize() / 2), strokePaint);
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
                    drawText(canvas, "Game over!");
                    break;
                case PAUSED:
                    drawText(canvas, "Tap screen to start...");
                    break;
                case RUNNING:
                    drawGame(canvas);
                    break;
                case WON:
                    drawText(canvas, "Your score is " + score);
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
        statistics.draw(canvas);
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
                if (event.getX() < this.screenWidth - this.screenWidth / 4 && event.getX() > this.screenWidth / 2 && hit <= 0)
                {
                    slowDown();
                }
                if (event.getX() > this.screenWidth - this.screenWidth / 4 && event.getX() > this.screenWidth / 2 && hit <= 0)
                {
                    speedUp();
                }
            }
            else if (event.getAction() == android.view.MotionEvent.ACTION_UP)
            {
                devil.moveStop();
                if (hit <= 0)
                    normalSpeed();
            }
        }
        else if(event.getX() > screenWidth/2 - 300 && event.getX() < screenWidth/2 + 300 && event.getY() > screenHeight/2 - 50 && event.getY() < screenHeight/2 + 50 )
        {
            state = State.RUNNING;
            startTime = System.currentTimeMillis();
            cloudTime = System.currentTimeMillis();
            pecaTime = System.currentTimeMillis();
            cloudShowUpSpeed = 30000;
            cloudShowUp = 2000;
            score=0;
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

    // Aleksa TODO: consider return type
    public void cloudsUpdateAndCollisionDetection(long elapsed)
    {
        Cloud cloud;

        iterator = clouds.iterator();
        while (iterator.hasNext())
        {
            cloud = iterator.next();

            // if cloud did not left screen
            if (cloud.getX() > ((-cloud.getScreenRect().width())))
            {
                // update its position
                cloud.update(elapsed);

                // if devil collided with cloud
                if (devil.getScreenRect().contains(cloud.getScreenRect().left, cloud.getScreenRect().centerY()) ||
                        devil.getScreenRect().contains(cloud.getScreenRect().right, cloud.getScreenRect().centerY()) //||
                    //devil.getScreenRect().contains((int)cloud.getY(), cloud.getScreenRect().centerX()) ||
                    //devil.getScreenRect().contains((int)cloud.getY()+cloud.getScreenRect().height(), cloud.getScreenRect().centerX())
                        )
                {
                    if (cloud.getType() == Cloud.Type.BLACK)
                    {
                        // TODO: game over
                        state = State.LOST;
                        if(isSoundOn)
                            soundPool.play(sounds[Sounds.LOSE], 1, 1, 1, 0, 1);
                        if(isVibratorOn)
                            vibrator.vibrate(700);
                        resetGame();
                        Log.d("cloud", "black");
                        break;
                    }
                    else if (cloud.getType() == Cloud.Type.WHITE)
                    {
                        // TODO: slow down
                        slowDown();
                        hit = 400;
                        if(isSoundOn)
                            soundPool.play(sounds[Sounds.BOUNCE2], 1, 1, 1, 0, 1);
                        if(isVibratorOn)
                            vibrator.vibrate(200);
                        Log.d("cloud", "white");
                    }
                    iterator.remove();
                }
            }
            else
            {
                // otherwise remove cloud and update statistics
                if (cloud.getType() == Cloud.Type.BLACK)
                {
                    statistics.setAvoidedBlackClouds(statistics.getAvoidedBlackClouds() + 1);
                }

                if (cloud.getType() == Cloud.Type.WHITE)
                {
                    statistics.setAvoidedWhiteClouds(statistics.getAvoidedWhiteClouds() + 1);
                }

                iterator.remove();
            }
        }
    }

    public boolean arrowCollisionDetection()
    {
        // if devil collided with arrow
        if (devil.getScreenRect().contains(arrow.getScreenRect().left, arrow.getScreenRect().centerY()) ||
                devil.getScreenRect().contains(arrow.getScreenRect().right, arrow.getScreenRect().centerY()))
        {
            return true;
        }
        return false;
    }

    public boolean angelCollisionDetection()
    {
        // if devil collided with angel
        if (devil.getScreenRect().contains(angel.getScreenRect().left, angel.getScreenRect().centerY()) ||
                devil.getScreenRect().contains(angel.getScreenRect().right, angel.getScreenRect().centerY()) // ||
            // angel.getScreenRect().contains((int) angel.getY(), angel.getScreenRect().centerX()) ||
            // angel.getScreenRect().contains((int) angel.getY() + angel.getScreenRect().height(), angel.getScreenRect().centerX())
                )
        {
            return true;

        }
        return false;
    }

    public void resetGame()
    {
        init();
        clouds.clear();
        hit = 0;
    }
}
