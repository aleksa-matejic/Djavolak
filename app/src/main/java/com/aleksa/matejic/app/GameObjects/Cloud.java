package com.aleksa.matejic.app.GameObjects;

import android.graphics.Bitmap;

import java.util.Random;

/**
 * Created by aleksa.matejic on 3/15/2017.
 */

public class Cloud extends Sprite
{
    private static int speed = 10;
    private Type type;

    public enum Type
    {
        WHITE,
        BLACK
    }

    public Cloud(int screenWidth, int screenHeight, Type type)
    {
        super(screenWidth, screenHeight);
        this.type = type;
    }

    @Override
    public void init(Bitmap image)
    {
        image = Bitmap.createScaledBitmap(image, (int) (150 * scale), (int) (100 * scale), true);
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
        move(speed * elapsed);
    }

    private void move(long distance)
    {
        setX(getX() - distance);
    }

    public static int getSpeed()
    {
        return speed;
    }

    public static void setSpeed(int speed)
    {
        Cloud.speed = speed;
    }

    public Type getType()
    {
        return type;
    }
}
