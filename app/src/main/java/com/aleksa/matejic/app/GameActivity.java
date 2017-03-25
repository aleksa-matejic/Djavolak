package com.aleksa.matejic.app;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.aleksa.matejic.app.utils.SharedPreferencesStore;

public class GameActivity extends Activity
{
    public static MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mp.release();
    }
}
