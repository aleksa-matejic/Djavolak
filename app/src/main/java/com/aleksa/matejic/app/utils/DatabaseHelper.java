package com.aleksa.matejic.app.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aleksa.matejic.app.GameObjects.Statistics;

/**
 * Created by aleksa.matejic on 3/21/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "DBstatistics.db";
    public static final String TABLE_NAME = "statistics";
    public static final String idStatistics = "idStatistics";
    public static final String playerName = "playerName";
    public static final String avoidedWhiteClouds = "avoidedWhiteClouds";
    public static final String avoidedBlackClouds = "avoidedBlackClouds";
    public static final String avoidedArrows = "avoidedArrows";
    public static final String distance = "distance";
    public static final String score = "score";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (idStatistics INTEGER PRIMARY KEY AUTOINCREMENT, playerName TEXT, avoidedWhiteClouds INTEGER, avoidedBlackClouds INTEGER, avoidedArrows INTEGER, distance INTEGER, score INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Aleksa TODO: call this method on game won
    public boolean insertData(Statistics statistics)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(playerName, statistics.getPlayerName());
        contentValues.put(avoidedWhiteClouds, statistics.getAvoidedWhiteClouds());
        contentValues.put(avoidedBlackClouds, statistics.getAvoidedBlackClouds());
        contentValues.put(avoidedArrows, statistics.getAvoidedArrows());
        contentValues.put(distance, statistics.getDevilDistance());
        contentValues.put(score, statistics.getScore());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
        {
            return false;
        }
        return true;
    }

    // Aleksa TODO: call this method from statistics activity
    // while result.moveToNext();
    // result.getString(0);
    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY score", null);

        return result;
    }

    public Cursor getAllPlayers()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery("SELECT DISTINCT playerName FROM " + TABLE_NAME, null);

        return result;
    }
}
