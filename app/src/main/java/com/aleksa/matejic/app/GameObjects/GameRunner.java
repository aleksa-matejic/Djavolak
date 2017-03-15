package com.aleksa.matejic.app.GameObjects;

/**
 * Created by aleksa.matejic on 3/14/2017.
 */

import android.util.Log;

public class GameRunner extends Thread
{

    private volatile boolean running = true;
    private Game game;

    public GameRunner(Game game)
    {
        this.game = game;
    }

    public void run()
    {

        game.init();

        long lastTime = System.currentTimeMillis();

        while (running)
        {
            // Log.d("MP", "Thread running...");

            long now = System.currentTimeMillis();
            long elapsed = now - lastTime;
            // Log.d("MP", "elapsed1 "+elapsed);
            if (elapsed < 100 && elapsed > 0)
            {
                Log.d("MP", "elapsed2 " + elapsed);
                game.update(elapsed);
                game.draw();
                lastTime = System.currentTimeMillis();
            }

        }

    }

    public void shutDown()
    {
        running = false;
    }

}