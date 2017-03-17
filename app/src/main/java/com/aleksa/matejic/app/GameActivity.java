package com.aleksa.matejic.app;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.aleksa.matejic.app.utils.SharedPreferencesStore;

public class GameActivity extends Activity
{
    // private TextView currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // currentPlayer = (TextView) findViewById(R.id.currentPlayer);
        // String currentPlayerStored = SharedPreferencesStore.getInstance(this.getApplicationContext()).readString(SharedPreferencesStore.getInstance(this).CURRENT_PLAYER);
        // currentPlayer.setText(currentPlayerStored);
    }
}
