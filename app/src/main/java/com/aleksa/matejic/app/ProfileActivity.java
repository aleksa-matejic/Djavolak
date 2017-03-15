package com.aleksa.matejic.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aleksa.matejic.app.utils.SharedPreferencesStore;

public class ProfileActivity extends AppCompatActivity
{
    // private TextView tvPlayerName;
    // private RelativeLayout rlPlayGame;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // tvPlayerName = (TextView) findViewById(R.id.tvPlayerName);
        // rlPlayGame = (RelativeLayout) findViewById(R.id.rlPlayGame);

        // tvPlayerName.setText(SharedPreferencesStore.getInstance(this).readString(SharedPreferencesStore.getInstance(this).CURRENT_PLAYER));

        // rlPlayGame.setOnClickListener(new View.OnClickListener()
        // {
        // @Override
        // public void onClick(View v)
        // {
        // Intent myIntent = new Intent(ProfileActivity.this, GameActivity.class);
        // startActivity(myIntent);
        // }
        // });
    }
}
