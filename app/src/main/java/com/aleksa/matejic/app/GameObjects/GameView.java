package com.aleksa.matejic.app.GameObjects;

/**
 * Created by aleksa.matejic on 3/14/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Callback
{

    private Bitmap button;
    private GameRunner runner;
    private Game game;

    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        SurfaceHolder holder = getHolder();

        holder.addCallback(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        game.onTouchEvent(event);
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        Log.d("MP", "created");
        game = new Game(this.getContext(), getWidth(), getHeight(), holder, getResources());
        runner = new GameRunner(game);
        runner.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        Log.d("MP", "changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        Log.d("MP", "Destroyed");
        if (runner != null)
        {
            runner.shutDown();
            while (runner != null)
            {
                try
                {
                    runner.join();
                    runner = null;
                }
                catch (InterruptedException e)
                {
                }

            }
        }
    }

}
