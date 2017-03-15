package com.aleksa.matejic.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;

/**
 * Created by aleksa.matejic on 3/6/2017.
 */

public class SharedPreferencesStore
{
    // Aleksa TODO: find better name
    private static final String STORE_NAME = "sharedPreferencesStore";

    public static final String CURRENT_PLAYER = "currentPlayer";
    public static final String VIBRATION = "vibration";
    public static final String SOUNDS = "sounds";

    private Context context = null;

    private static SharedPreferencesStore instance = null;

    private SharedPreferencesStore(Context context)
    {
        this.context = context;
    }

    public static SharedPreferencesStore getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new SharedPreferencesStore(context);
        }
        return instance;
    }

    public boolean saveString(String key, String value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);

        return editor.commit();
    }

    public String readString(String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public Boolean saveBoolean(String key, Boolean value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);

        return editor.commit();
    }

    public Boolean readBoolean(String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }
}
