package com.aleksa.matejic.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.aleksa.matejic.app.utils.SharedPreferencesStore;

public class StartGameActivity extends Activity
{
    private RelativeLayout rlCreateNew;
    private ListView lvPreviousProfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        // Aleksa TODO: read profiles from cache
        String[] previousProfilesTmp = {"Djavolko", "Vragolan", "Aleksa"};
        ListAdapter adapterTmp = new ArrayAdapter<>(StartGameActivity.this, android.R.layout.simple_list_item_1, previousProfilesTmp);

        lvPreviousProfiles = (ListView) findViewById(R.id.lvPreviousProfiles);
        lvPreviousProfiles.setAdapter(adapterTmp);

        lvPreviousProfiles.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                /*Intent intent = new Intent(MainActivity.this, SendMessage.class);
                String message = "abc";
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);*/

                //                String playerName = lvPreviousProfiles.getItemAtPosition(position).toString();
                //                boolean result = SharedPreferencesStore.getInstance(StartGameActivity.this).saveString(SharedPreferencesStore.getInstance(StartGameActivity.this).CURRENT_PLAYER, playerName);
                //                if(!result)
                //                {
                //                    System.out.println("Current player name not saved to pref store!");
                //                }

                Intent myIntent = new Intent(StartGameActivity.this, ProfileActivity.class);
                startActivity(myIntent);
            }
        });

        rlCreateNew = (RelativeLayout) findViewById(R.id.rlCreateNew);

        rlCreateNew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(StartGameActivity.this, NewProfileActivity.class);
                startActivity(myIntent);
            }
        });


    }
}
