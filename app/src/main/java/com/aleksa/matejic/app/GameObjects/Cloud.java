package com.aleksa.matejic.app.GameObjects;

import android.graphics.Bitmap;

import java.util.Random;

/**
 * Created by aleksa.matejic on 3/15/2017.
 */

public class Cloud extends Sprite
{
    private int speed = 10;
    public Cloud(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);
    }

    @Override
    public void init(Bitmap image)
    {
        super.init(image);

        initPosition(image);
    }

    public void initPosition(Bitmap image)
    {
        Random rnd = new Random();
        setX(getScreenWidth());
        setY(rnd.nextInt(getScreenHeight() - image.getHeight()));
    }

    public void update(long elapsed)
    {
        move(speed*elapsed);
    }

    private void move(long distance){
        setX(getX()-distance);
    }
}
