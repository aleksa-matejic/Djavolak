package com.aleksa.matejic.app.GameObjects;

import android.graphics.Bitmap;

/**
 * Created by aleksa.matejic on 3/15/2017.
 */

public class Devil extends Sprite
{
    public Devil(int screenWidth, int screenHeight, Position position)
    {
        super(screenWidth, screenHeight);
        this.position = position;
    }

    @Override
    public void init(Bitmap image, Bitmap shadow)
    {
        super.init(image, shadow);

        initPosition();
    }

    public void initPosition()
    {
        setY(getScreenHeight() / 2 - getRect().centerY());
    }


}
