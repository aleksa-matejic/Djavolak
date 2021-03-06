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
        if ((image.getHeight() + getY()) < getScreenHeight())
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

}
