package com.example.anders.breakyourphone;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class BatteryDrainer extends Service implements SensorEventListener {

    private NotificationManager mNM;
    private final IBinder mBinder = new LocalBinder();

    private Intent appIntent1 = null;
    private Intent appIntent2 = null;
//    private Intent appIntent3 = null;
//    private Intent appIntent4 = null;

    Timer timer;
    int count = 0;

    public class LocalBinder extends Binder {
        BatteryDrainer getService() {
            return BatteryDrainer.this;
        }
    }

    @Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        showNotification();

        PackageManager packageManager = getPackageManager();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("stalin did nothing wrong", Context.MODE_PRIVATE);
        String packageName1 = sharedPreferences.getString("1", "");
        String packageName2 = sharedPreferences.getString("2", "");
//        String packageName3 = sharedPreferences.getString("3", "");
//        String packageName4 = sharedPreferences.getString("4", "");
        if(!packageName1.equals("")) {
            appIntent1 = packageManager.getLaunchIntentForPackage(packageName1);
        }
        if(!packageName2.equals("")) {
            appIntent2 = packageManager.getLaunchIntentForPackage(packageName2);
        }
//        if(!packageName3.equals("")) {
//            appIntent3 = packageManager.getLaunchIntentForPackage(packageName3);
//        }
//        if(!packageName4.equals("")) {
//            appIntent4 = packageManager.getLaunchIntentForPackage(packageName4);
//        }

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("kulaks deserved it", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("running", 1);
        editor.commit();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mNM.cancel(1917);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("kulaks deserved it", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("running", 0);
        editor.commit();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void showNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("ticker")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Phonebreaker")
                .setContentText("Running Batterydrainer")
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
        mNM.notify(1917, notification);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(final SensorEvent event) {
        Sensor sensor = event.sensor;
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (Math.sqrt(Math.pow(event.values[0],2)+Math.pow(event.values[1],2)+Math.pow(event.values[2],2)) > 50) {
                count = 0;
                timer = new Timer();
                timer.schedule(new TimerTask() {
                                   @Override
                                   public void run() {
                                       count++;
                                       if (count < 100) {
                                           if (Math.sqrt(Math.pow(event.values[0],2)+Math.pow(event.values[1],2)+Math.pow(event.values[2],2)) > 50) {
                                                timer.cancel();
                                                Log.d("OPENING", "1");
                                                event.values[0] = 0;
                                                event.values[1] = 0;
                                                event.values[2] = 0;
                                                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                                                v.vibrate(500);
                                                PowerManager.WakeLock wakeLock = ((PowerManager)getSystemService(POWER_SERVICE)).newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG1");
                                                wakeLock.acquire();
                                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("i will make stalin look like an anarchist", Context.MODE_PRIVATE);
                                                int permission = sharedPreferences.getInt("permission", 0);
                                                if(permission == 1) {
                                                    KeyguardManager.KeyguardLock keyguardLock = ((KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE)).newKeyguardLock("TAG2");
                                                    keyguardLock.disableKeyguard();
                                                } else {
                                                    KeyguardManager.KeyguardLock keyguardLock = ((KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE)).newKeyguardLock("TAG2");
                                                    keyguardLock.reenableKeyguard();
                                                }
                                                startActivity(appIntent1);
                                                SystemClock.sleep(500);
                                           }
                                       }
                                   }
                               },
                        300, 100);
            }
            /*
            if(Math.abs(event.values[0]) > 50 || Math.abs(event.values[1]) > 50 || Math.abs(event.values[2]) > 50){
                Toast.makeText(this, "action1", Toast.LENGTH_SHORT).show();
                if(appIntent1 != null) {
                    if(!isScreenOn()) {
                        Log.d("CHECKED", "OFF");
                    } else {
                        Log.d("CHEKCED", "ON");
                    }
                    Log.d("OPENING", "1");
                    startActivity(appIntent1);
                    try {
                        Thread.sleep(1000);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            */
        } else if(sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            if (Math.abs(event.values[0]) > 12 || Math.abs(event.values[1]) > 12) {
                count = 0;
                timer = new Timer();
                timer.schedule(new TimerTask() {
                                   @Override
                                   public void run() {
                                       count++;
                                       if (count < 50) {
                                           if (Math.abs(event.values[0]) > 12 || Math.abs(event.values[1]) > 12) {
                                               timer.cancel();
                                               event.values[0] =0;
                                               event.values[1] =0;
                                               event.values[2] =0;
                                               Log.d("OPENING", "2");
                                               Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                                               v.vibrate(500);
                                               PowerManager.WakeLock wakeLock = ((PowerManager)getSystemService(POWER_SERVICE)).newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG1");
                                               wakeLock.acquire();
                                               SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("i will make stalin look like an anarchist", Context.MODE_PRIVATE);
                                               int permission = sharedPreferences.getInt("permission", 0);
                                               if(permission == 1) {
                                                   KeyguardManager.KeyguardLock keyguardLock = ((KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE)).newKeyguardLock("TAG2");
                                                   keyguardLock.disableKeyguard();
                                               } else {
                                                   KeyguardManager.KeyguardLock keyguardLock = ((KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE)).newKeyguardLock("TAG2");
                                                   keyguardLock.reenableKeyguard();
                                               }
                                               startActivity(appIntent2);
                                               SystemClock.sleep(500);
                                           }
                                       }
                                   }
                               },
                        150, 20);
            }
/*
            if(Math.abs(event.values[0]) > 20 || Math.abs(event.values[1]) > 20 || Math.abs(event.values[2]) > 20) {
                Toast.makeText(this, "action2", Toast.LENGTH_SHORT).show();
                if(appIntent2 != null) {
                    Log.d("OPENING", "2");
                    startActivity(appIntent2);
                    try {
                        Thread.sleep(1000);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        */
        }
    }

    private boolean isScreenOn() {
        DisplayManager dm = (DisplayManager)getSystemService(Context.DISPLAY_SERVICE);
        for (Display display : dm.getDisplays()) {
            if (display.getState() != Display.STATE_OFF) {
                return true;
            }
        }
        return false;
    }
}
