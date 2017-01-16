package com.example.anders.breakyourphone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
//    private Button button3;
//    private Button button4;
    FloatingActionButton floatingActionButton;
    TextView textView;
    ColorStateList colorStateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material);

        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
//        button3 = (Button)findViewById(R.id.button3);
//        button4 = (Button)findViewById(R.id.button4);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
//        final Button button5 = (Button)findViewById(R.id.button5);
//        final Button button6 = (Button)findViewById(R.id.button6);
        textView = (TextView)findViewById(R.id.textView);
        colorStateList = textView.getTextColors();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("stalin did nothing wrong", Context.MODE_PRIVATE);
        String packageName1 = sharedPreferences.getString("1", "");
        String packageName2 = sharedPreferences.getString("2", "");
//        String packageName3 = sharedPreferences.getString("3", "");
//        String packageName4 = sharedPreferences.getString("4", "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(packageName1.equals("")) {
            editor.putString("1", "");
        }
        if(packageName2.equals("")) {
            editor.putString("2", "");
        }
//        if(packageName3.equals("")) {
//            editor.putString("3", "");
//        }
//        if(packageName4.equals("")) {
//            editor.putString("4", "");
//        }
        editor.commit();
        PackageManager packageManager = getApplicationContext().getPackageManager();
        try {
            button.setText((String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName1, PackageManager.GET_META_DATA)));
            button2.setText((String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName2, PackageManager.GET_META_DATA)));
//            button3.setText((String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName3, PackageManager.GET_META_DATA)));
//            button4.setText((String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName4, PackageManager.GET_META_DATA)));
        } catch(Exception e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("kulaks deserved it", Context.MODE_PRIVATE);
        int running = sharedPreferences2.getInt("running", 0);
        if(running == 1) {
//            button5.setEnabled(false);
            setFabPause();
        } else {
//            button6.setEnabled(false);
            setFabPlay();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppListViewActivity.class);
                intent.putExtra("action", 1);
                startActivityForResult(intent, 0);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppListViewActivity.class);
                intent.putExtra("action", 2);
                startActivityForResult(intent, 0);
            }
        });
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AppListViewActivity.class);
//                intent.putExtra("action", 3);
//                startActivityForResult(intent, 0);
//            }
//        });
//        button4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AppListViewActivity.class);
//                intent.putExtra("action", 4);
//                startActivityForResult(intent, 0);
//            }
//        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("stalin did nothing wrong", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("1", "");
                editor.commit();
                button.setText("");
                return true;
            }
        });
        button2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("stalin did nothing wrong", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("2", "");
                editor.commit();
                button2.setText("");
                return true;
            }
        });
//        button3.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("stalin did nothing wrong", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("3", "");
//                editor.commit();
//                button3.setText("");
//                return true;
//            }
//        });
//        button4.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("stalin did nothing wrong", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("4", "");
//                editor.commit();
//                button4.setText("");
//                return true;
//            }
//        });
//        button5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startService(new Intent(getApplicationContext(), BatteryDrainer.class));
//                button5.setEnabled(false);
//                button6.setEnabled(true);
//            }
//        });
//        button6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                stopService(new Intent(getApplicationContext(), BatteryDrainer.class));
//                button5.setEnabled(true);
//                button6.setEnabled(false);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK && data != null) {
            int action = data.getIntExtra("action", 0);
            if(action == 0) {
                Toast.makeText(getApplication().getBaseContext(), "invalid action", Toast.LENGTH_LONG).show();
            }
            String appName = data.getStringExtra("appName");
            switch(action) {
                case(1):
                    button.setText(appName);
                    break;
                case(2):
                    button2.setText(appName);
                    break;
//                case(3):
//                    button3.setText(appName);
//                    break;
//                case(4):
//                    button4.setText(appName);
//                    break;
            }
        }
    }

    private void setFabPlay() {
        floatingActionButton.setImageResource(android.R.drawable.ic_media_play);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pressed", "hi");
                startService(new Intent(getApplicationContext(), BatteryDrainer.class));
                textView.setText("Listening...");
                textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                setFabPause();
            }
        });
    }

    private void setFabPause() {
        floatingActionButton.setImageResource(android.R.drawable.ic_media_pause);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pressed", "bye");
                stopService(new Intent(getApplicationContext(), BatteryDrainer.class));
                textView.setText("Inactive");
                textView.setTextColor(colorStateList);
                setFabPlay();
            }
        });
    }
}
