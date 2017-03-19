package com.aleksa.matejic.app.GameObjects;

import android.graphics.Bitmap;

import java.util.Random;

/**
 * Created by aleksa.matejic on 3/15/2017.
 */

public class Arrow extends Sprite
{
    private int speed = 6;
    private boolean move = false;

    public Arrow(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);
    }

    @Override
    public void init(Bitmap image)
    {
        image = Bitmap.createScaledBitmap(image, (int) (120 * scale), (int) (20 * scale), true);
        super.init(image);

        initPosition(image);
    }

    public void initPosition(Bitmap image)
    {
        Random rnd = new Random();
        setX(getScreenWidth());
        setY(getScreenHeight());
    }

    public void initPosition(float x, float y)
    {
        setX(x);
        setY(y);
    }

    public void update(long elapsed)
    {
        if (isMove())
            move(speed * elapsed);
    }

    private void move(long distance)
    {
        setX(getX() - distance);
    }

    public boolean isMove()
    {
        return move;
    }

    public void setMove(boolean move)
    {
        this.move = move;
    }
}
