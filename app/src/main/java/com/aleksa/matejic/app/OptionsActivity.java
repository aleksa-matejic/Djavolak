package com.aleksa.matejic.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.aleksa.matejic.app.utils.SharedPreferencesStore;

public class OptionsActivity extends Activity
{
    // CheckBox cbSounds;
    // CheckBox cbVibration;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        // cbSounds = (CheckBox) findViewById(R.id.cbSounds);
        // cbVibration = (CheckBox) findViewById(R.id.cbVibration);

        // boolean checked = SharedPreferencesStore.getInstance(OptionsActivity.this).readBoolean(SharedPreferencesStore.getInstance(OptionsActivity.this).SOUNDS);
        // cbSounds.setChecked(checked);
        // checked = SharedPreferencesStore.getInstance(this).readBoolean(SharedPreferencesStore.getInstance(this).VIBRATION);
        // cbVibration.setChecked(checked);

        // cbSounds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        // {
        //     @Override
        //     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        //     {
        //         SharedPreferencesStore.getInstance(OptionsActivity.this).saveBoolean(SharedPreferencesStore.getInstance(OptionsActivity.this).SOUNDS, isChecked);
        //     }
        // });

        // cbVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        // {
        //     @Override
        //     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        //     {
        //         SharedPreferencesStore.getInstance(OptionsActivity.this).saveBoolean(SharedPreferencesStore.getInstance(OptionsActivity.this).VIBRATION, isChecked);
        //     }
        // });
    }
}
