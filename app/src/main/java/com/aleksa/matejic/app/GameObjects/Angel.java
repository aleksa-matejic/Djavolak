package com.aleksa.matejic.app.GameObjects;

import android.graphics.Bitmap;

import java.util.Random;

/**
 * Created by aleksa.matejic on 3/15/2017.
 */

public class Angel extends Sprite
{
    private int speed = 6;
    private int way = 0;
    private long distance = 1000;

    public Angel(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);
    }

    @Override
    public void init(Bitmap image)
    {
        image = Bitmap.createScaledBitmap(image, (int) (160 * scale), (int) (110 * scale), true);
        super.init(image);

        initPosition(image);
    }

    public void initPosition(Bitmap image)
    {
        Random rnd = new Random();
        setX(distance);
        setY(rnd.nextInt(getScreenHeight() - image.getHeight()));
    }

    public void update(long elapsed)
    {
        if (way == 1)
            move(speed * elapsed);
        if (way == -1)
            move(-speed * elapsed);
    }

    private void move(long distance)
    {
        setX(getX() - distance);
    }

    public int getWay()
    {
        return way;
    }

    public void setWay(int way)
    {
        this.way = way;
    }
}
