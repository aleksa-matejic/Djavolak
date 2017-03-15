package com.aleksa.matejic.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aleksa.matejic.app.utils.SharedPreferencesStore;

public class NewProfileActivity extends Activity
{
    private RelativeLayout rlPlayGame;
    private EditText etInputName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);

        rlPlayGame = (RelativeLayout) findViewById(R.id.rlPlayGame);
        etInputName = (EditText) findViewById(R.id.etInputName);

        rlPlayGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(etInputName.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Input your name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // boolean result = SharedPreferencesStore.getInstance(NewProfileActivity.this).saveString(SharedPreferencesStore.getInstance(NewProfileActivity.this).CURRENT_PLAYER, etInputName.getText().toString());
                // if(!result)
                // {
                //     System.out.println("Current player name not saved to pref store!");
                // }

                Intent myIntent = new Intent(NewProfileActivity.this, GameActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
