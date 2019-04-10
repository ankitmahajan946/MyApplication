package com.example.ankitmah.myapplication;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import static com.example.ankitmah.myapplication.MyApplication.CHANNEL_ID;

public class ForegroundService extends Service {
    private static final String TAG = "FOREGROUND_SERVICE";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: foreground service");
    }

    public ForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("input");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this, 0,notificationIntent,0);


        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground service Notification")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);

        return START_NOT_STICKY;
    }

    void doFakeBackgroundWork(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: foreground service");
    }
}
