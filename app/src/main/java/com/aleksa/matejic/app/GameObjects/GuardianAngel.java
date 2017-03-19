package com.aleksa.matejic.app.GameObjects;

import android.graphics.Bitmap;

import java.util.Random;

/**
 * Created by aleksa.matejic on 3/15/2017.
 */

public class GuardianAngel extends Sprite
{
    private int speed = 6;
    private int way = 1;

    public GuardianAngel(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);
    }

    @Override
    public void init(Bitmap image)
    {
        image = Bitmap.createScaledBitmap(image, (int) (160 * scale), (int) (120 * scale), true);
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
        if (way == 1)
            move(speed * elapsed);
        else
            move(-speed * elapsed);
    }

    private void move(long distance)
    {
        setX(getX() - distance);
    }

    public void setImage(Bitmap image)
    {
        image = Bitmap.createScaledBitmap(image, (int) (160 * scale), (int) (120 * scale), true);
        super.init(image);
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
