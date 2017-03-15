package com.aleksa.matejic.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainMenuActivity extends Activity
{
    RelativeLayout rlStartGame;
    RelativeLayout rlStatistics;
    RelativeLayout rlOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        rlStartGame = (RelativeLayout) findViewById(R.id.rlStartGame);
        rlStatistics = (RelativeLayout) findViewById(R.id.rlStatistics);
        rlOptions = (RelativeLayout) findViewById(R.id.rlOptions);

        rlStartGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(MainMenuActivity.this, StartGameActivity.class);
                startActivity(myIntent);
            }
        });

        rlStatistics.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(MainMenuActivity.this, StatisticsActivity.class);
                startActivity(myIntent);
            }
        });

        rlOptions.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(MainMenuActivity.this, OptionsActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
