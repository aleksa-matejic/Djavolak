package com.aleksa.matejic.app.GameObjects;

/**
 * Created by aleksa.matejic on 3/14/2017.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite
{

    private float x;
    private float y;

    private int screenWidth;
    private int screenHeight;

    private Bitmap image;

    private Rect bounds;

    public Sprite(int screenWidth, int screenHeight)
    {
        this.x = 0;
        this.y = 0;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void init(Bitmap image)
    {
        this.image = image;

        bounds = new Rect(0, 0, image.getWidth(), image.getHeight());
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
    }

    public Rect getRect()
    {
        return bounds;
    }

    public Rect getScreenRect()
    {
        return new Rect((int) x, (int) y, (int) x + getRect().width(), (int) y + getRect().height());
    }

    public int getScreenWidth()
    {
        return screenWidth;
    }

    public int getScreenHeight()
    {
        return screenHeight;
    }

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

}