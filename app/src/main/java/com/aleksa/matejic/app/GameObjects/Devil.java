package com.aleksa.matejic.app.GameObjects;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by aleksa.matejic on 3/15/2017.
 */

public class Devil extends Sprite
{
    private int move;

    public Devil(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);
        move = 0;
    }

    @Override
    public void init(Bitmap image)
    {
        image = Bitmap.createScaledBitmap(image, (int) (80 * scale), (int) (80 * scale), true);
        super.init(image);

        initPosition();
    }

    public void initPosition()
    {
        setY(getScreenHeight() / 2 - getRect().centerY());
    }

    public void update(long elapsed)
    {
        if (move == 1)
            moveUp(elapsed);
        if (move == -1)
            moveDown(elapsed);
    }

    public void moveUp(long elapsed)
    {
        if (this.getY() > 0)
        {
            setY(getY() - 6 * elapsed);
        }
        //moveStop();
    }

    public void moveDown(long elapsed)
    {
        if (this.getY() < getScreenHeight())
        {
            setY(getY() + 6 * elapsed);
        }
        //moveStop();
    }

    public void moveUp()
    {
        move = 1;
    }

    public void moveDown()
    {
        move = -1;
    }

    public void moveStop()
    {
        move = -0;
    }

    // Aleksa TODO: change method return value to integer (or cloud type) because of difference in cloud type
    public boolean cloudsCollisionDetection(LinkedList<Cloud> clouds)
    {
        Cloud cloud;
        Iterator<Cloud> iterator;

        iterator = clouds.iterator();
        while (iterator.hasNext())
        {
            cloud = iterator.next();
            if (getScreenRect().contains(cloud.getScreenRect().left, cloud.getScreenRect().centerY()) ||
                    getScreenRect().contains(cloud.getScreenRect().right, cloud.getScreenRect().centerY()) //||
                //devil.getScreenRect().contains((int)cloud.getY(), cloud.getScreenRect().centerX()) ||
                //devil.getScreenRect().contains((int)cloud.getY()+cloud.getScreenRect().height(), cloud.getScreenRect().centerX())
                    )
            {
                if (cloud.getType() == Cloud.Type.BLACK)
                {
                    // TODO: game over
                    // return 1 or black
                    Log.d("cloud", "black");
                }
                else if (cloud.getType() == Cloud.Type.WHITE)
                {
                    // TODO: slow
                    // return 2 or white
                    Log.d("cloud", "white");
                }
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public boolean arrowCollisionDetection(Arrow arrow)
    {
        // if arrow points are in touch with devil
        if (getScreenRect().contains(arrow.getScreenRect().left, arrow.getScreenRect().centerY()) ||
                getScreenRect().contains(arrow.getScreenRect().right, arrow.getScreenRect().centerY()))
        {
            return true;
        }
        return false;
    }

    public boolean angelCollisionDetection(Angel angel)
    {
        if (getScreenRect().contains(angel.getScreenRect().left, angel.getScreenRect().centerY()) ||
                getScreenRect().contains(angel.getScreenRect().right, angel.getScreenRect().centerY()) // ||
                // angel.getScreenRect().contains((int) angel.getY(), angel.getScreenRect().centerX()) ||
                // angel.getScreenRect().contains((int) angel.getY() + angel.getScreenRect().height(), angel.getScreenRect().centerX())
        )
        {
            return true;

        }
        return false;
    }
}
