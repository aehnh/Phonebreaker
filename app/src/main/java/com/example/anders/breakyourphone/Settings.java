package com.example.anders.breakyourphone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Switch switch1 = (Switch)findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("i will make stalin look like an anarchist", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(isChecked) {
                    editor.putInt("permission", 1);
                } else {
                    editor.putInt("permission", 0);
                }
                editor.commit();
            }
        });
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("i will make stalin look like an anarchist", Context.MODE_PRIVATE);
        int permission = sharedPreferences.getInt("permission", 0);
        if(permission == 1) {
            switch1.setChecked(true);
        } else {
            switch1.setChecked(false);
        }
    }
}
