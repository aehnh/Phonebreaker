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
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout2;
    private ImageView imageView;
    private ImageView imageView2;
//    private TextView textView2;
//    private TextView textView3;
//    private Button button3;
//    private Button button4;
    FloatingActionButton floatingActionButton;
    TextView textView;
    ColorStateList colorStateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material);

        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
        linearLayout2 = (LinearLayout)findViewById(R.id.linearLayout2);
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
//        textView2 = (TextView)findViewById(R.id.textView2);
//        textView3 = (TextView)findViewById(R.id.textView3);
//        button = (Button)findViewById(R.id.button);
//        button2 = (Button)findViewById(R.id.button2);
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
            imageView.setImageDrawable(packageManager.getApplicationInfo(packageName1, PackageManager.GET_META_DATA).loadIcon(packageManager));
            imageView2.setImageDrawable(packageManager.getApplicationInfo(packageName2, PackageManager.GET_META_DATA).loadIcon(packageManager));
//            textView2.setText((String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName1, PackageManager.GET_META_DATA)));
//            textView3.setText((String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName2, PackageManager.GET_META_DATA)));
        } catch(Exception e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("kulaks deserved it", Context.MODE_PRIVATE);
        int running = sharedPreferences2.getInt("running", 0);
        if(running == 1) {
            textView.setText("Listening...");
            textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            setFabPause();
        } else {
            textView.setText("Inactive");
            textView.setTextColor(colorStateList);
            setFabPlay();
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppListViewActivity.class);
                intent.putExtra("action", 1);
                startActivityForResult(intent, 0);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppListViewActivity.class);
                intent.putExtra("action", 2);
                startActivityForResult(intent, 0);
            }
        });

        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("stalin did nothing wrong", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("1", "");
                editor.commit();
                imageView.setImageResource(android.R.drawable.ic_menu_add);
//                textView2.setText("");
                SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("kulaks deserved it", Context.MODE_PRIVATE);
                int running = sharedPreferences2.getInt("running", 0);
                if(running == 1) {
                    stopService(new Intent(getApplicationContext(), BatteryDrainer.class));
                    startService(new Intent(getApplicationContext(), BatteryDrainer.class));
                    Toast.makeText(getApplicationContext(), "Service updated", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        linearLayout2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("stalin did nothing wrong", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("2", "");
                editor.commit();
                imageView2.setImageResource(android.R.drawable.ic_menu_add);
//                textView3.setText("");
                SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("kulaks deserved it", Context.MODE_PRIVATE);
                int running = sharedPreferences2.getInt("running", 0);
                if(running == 1) {
                    stopService(new Intent(getApplicationContext(), BatteryDrainer.class));
                    startService(new Intent(getApplicationContext(), BatteryDrainer.class));
                    Toast.makeText(getApplicationContext(), "Service updated", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK && data != null) {
            int action = data.getIntExtra("action", 0);
            if(action == 0) {
                Toast.makeText(getApplication().getBaseContext(), "invalid action", Toast.LENGTH_LONG).show();
            }
            String packageName = data.getStringExtra("packageName");
            switch(action) {
                case(1):
                    try {
                        imageView.setImageDrawable(getPackageManager().getApplicationIcon(packageName));
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
//                    button.setText(appName);
                    break;
                case(2):
                    try {
                        imageView2.setImageDrawable(getPackageManager().getApplicationIcon(packageName));
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
//                    button2.setText(appName);
                    break;
//                case(3):
//                    button3.setText(appName);
//                    break;
//                case(4):
//                    button4.setText(appName);
//                    break;
            }
            SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("kulaks deserved it", Context.MODE_PRIVATE);
            int running = sharedPreferences2.getInt("running", 0);
            if(running == 1) {
                stopService(new Intent(getApplicationContext(), BatteryDrainer.class));
                startService(new Intent(getApplicationContext(), BatteryDrainer.class));
                Toast.makeText(getApplicationContext(), "Service updated", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Listening started", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Listening stopped", Toast.LENGTH_SHORT).show();
                textView.setText("Inactive");
                textView.setTextColor(colorStateList);
                setFabPlay();
            }
        });
    }
}
