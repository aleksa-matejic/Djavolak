package com.aleksa.matejic.app.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by aleksa.matejic on 3/17/2017.
 */

public class Background extends Sprite
{
    private Bitmap bitmapReversed;
    private boolean reversedFirst;
    float speed;
    private int xClip;
    private Paint paint;

    public Background(int screenWidth, int screenHeight)
    {
        super(screenWidth, screenHeight);

        paint = new Paint();
        reversedFirst = false;
        xClip = 0;
        speed = 50;
    }

    public void init(Bitmap image)
    {
        super.init(image);

        // scale the background image to fit screen height
        this.image = Bitmap.createScaledBitmap(this.image, this.image.getWidth(), getScreenHeight(), true);

        // create a mirror image of the background (horizontal flip)
        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        bitmapReversed = Bitmap.createBitmap(this.image, 0, 0, this.image.getWidth(), getScreenHeight(), matrix, true);
    }

    public void update(float dx)
    {
        // move the clipping position and reverse if necessary
        xClip -= speed / dx;
        if (xClip >= image.getWidth())
        {
            xClip = 0;
            reversedFirst = !reversedFirst;
        }
        else if (xClip <= 0)
        {
            xClip = image.getWidth();
            reversedFirst = !reversedFirst;

        }
    }

    public void draw(Canvas canvas)
    {
        // for the regular bitmap
        Rect fromRect1 = new Rect(0, 0, image.getWidth() - xClip, getScreenHeight());
        Rect toRect1 = new Rect(xClip, 0, image.getWidth(), getScreenHeight());

        // for the reversed background
        Rect fromRect2 = new Rect(image.getWidth() - xClip, 0, image.getWidth(), getScreenHeight());
        Rect toRect2 = new Rect(0, 0, xClip, getScreenHeight());

        // draw the two background bitmaps
        if (reversedFirst)
        {
            canvas.drawBitmap(image, fromRect1, toRect1, paint);
            canvas.drawBitmap(bitmapReversed, fromRect2, toRect2, paint);
        }
        else
        {
            canvas.drawBitmap(image, fromRect2, toRect2, paint);
            canvas.drawBitmap(bitmapReversed, fromRect1, toRect1, paint);
        }
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
