package com.aleksa.matejic.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends Activity
{
    private RelativeLayout rlMainMenu;
    private RelativeLayout rlCredits;
    private RelativeLayout rlExit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rlMainMenu = (RelativeLayout) findViewById(R.id.rlMainMeny);
        rlCredits = (RelativeLayout) findViewById(R.id.rlCredits);
        rlExit = (RelativeLayout) findViewById(R.id.rlExit);

        rlMainMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(MainActivity.this, MainMenuActivity.class);
                startActivity(myIntent);
            }
        });

        rlCredits.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(MainActivity.this, CreditsActivity.class);
                startActivity(myIntent);
            }
        });

        rlExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Aleksa TODO: implement exit if this is not the best solution
                finish();
            }
        });
    }
}
