package com.aleksa.matejic.app.GameObjects;

import android.graphics.Bitmap;

/**
 * Created by aleksa.matejic on 3/15/2017.
 */

public class Devil extends Sprite
{
    public Devil(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);
    }

    @Override
    public void init(Bitmap image)
    {
        super.init(image);

        initPosition();
    }

    public void initPosition()
    {
        setY(getScreenHeight() / 2 - getRect().centerY());
    }

    public void update(long elapsed)
    {
        // Aleksa TODO:
    }

    public void moveUp()
    {
        if (this.getY() > 0)
        {
            setY(getY() - 1);
        }
    }

    public void moveDown()
    {
        if (this.getY() < getScreenHeight())
        {
            setY(getY() + 1);
        }
    }
}
