package com.aleksa.matejic.app.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by aleksa.matejic on 3/20/2017.
 */

public class Statistics
{
    private String playerName;
    private long devilDistance;
    private int avoidedBlackClouds;
    private int avoidedWhiteClouds;
    private int avoidedArrows;
    private int score;
    private Paint fillPaint;
    private Paint strokePaint;
    // Aleksa TODO: get scale from sprite
    private double scale;

    public Statistics(String playerName)
    {
        this.playerName = playerName;
        this.score = 0;
        scale = 2.2;

        fillPaint = new Paint();
        fillPaint.setTextAlign(Paint.Align.CENTER);
        fillPaint.setAntiAlias(true);
        fillPaint.setColor(Color.YELLOW);
        fillPaint.setTextSize((int) (40 * scale));
        fillPaint.setTypeface(Typeface.DEFAULT_BOLD);

        strokePaint = new Paint();
        strokePaint.setTextAlign(Paint.Align.CENTER);
        strokePaint.setAntiAlias(true);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setTextSize((int) (40 * scale));
        strokePaint.setTypeface(Typeface.DEFAULT_BOLD);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(6);

    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public long getDevilDistance()
    {
        return devilDistance;
    }

    public void setDevilDistance(long devilDistance)
    {
        this.devilDistance = devilDistance;
    }

    public int getAvoidedBlackClouds()
    {
        return avoidedBlackClouds;
    }

    public void setAvoidedBlackClouds(int avoidedBlackClouds)
    {
        this.avoidedBlackClouds = avoidedBlackClouds;
    }

    public int getAvoidedWhiteClouds()
    {
        return avoidedWhiteClouds;
    }

    public void setAvoidedWhiteClouds(int avoidedWhiteClouds)
    {
        this.avoidedWhiteClouds = avoidedWhiteClouds;
    }

    public int getAvoidedArrows()
    {
        return avoidedArrows;
    }

    public void setAvoidedArrows(int avoidedArrows)
    {
        this.avoidedArrows = avoidedArrows;
    }

    public int getScore()
    {
        return avoidedArrows;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public void generateScore()
    {
        // Aleksa TODO: implementation
    }

    public void update()
    {
        // Aleksa TODO: implementation
        score += 50;
    }

    public void draw(Canvas canvas)
    {
        // Aleksa TODO: set position a little bit better
        canvas.drawText(playerName, canvas.getWidth() / 2, (int) (50 * scale), fillPaint);
        canvas.drawText(playerName, canvas.getWidth() / 2, (int) (50 * scale), strokePaint);

        canvas.drawText(String.valueOf(score), canvas.getWidth() / 2, canvas.getHeight() - (int) (10 * scale), fillPaint);
        canvas.drawText(String.valueOf(score), canvas.getWidth() / 2, canvas.getHeight() - (int) (10 * scale), strokePaint);
    }
}
