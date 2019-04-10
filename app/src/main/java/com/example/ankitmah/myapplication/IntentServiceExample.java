package com.example.ankitmah.myapplication;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.example.ankitmah.myapplication.MyApplication.CHANNEL_ID;

/**
 * Created by cybage on 08-Feb-19.
 */
public class IntentServiceExample extends IntentService {

    private static final String TAG = "INTENT_SERVICE_EXAMPLE";

    /*
     * A wake lock is a mechanism to indicate that your application needs to have the device stay on.
     * Any application using a WakeLock must request the android.permission.WAKE_LOCK permission in an
     * <uses-permission> element of the application's manifest. Obtain a wake lock by calling
     * newWakeLock(int, String).
     * Call acquire() to acquire the wake lock and force the device to stay on at the level that was
     * requested when the wake lock was created.
     * Call release() when you are done and don't need the lock anymore. It is very important to do this
     * as soon as possible to avoid running down the device's battery excessively.
     * */
    private PowerManager.WakeLock wakeLock;


    public IntentServiceExample() {
        super("IntentServiceExample");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        /*Activates a WakeLock as soon as the service starts.
         * The WakeLock will keep the CPU of the phone running when the screen is turned off,
         * which makes sense if you know that your service could keep running when the user locks the phone
         */
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        assert powerManager != null;
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "WakeLock" +
                ": powermanager");

        /*
         * Acquires the wake lock with a timeout.
         * Ensures that the device is on at the level requested when the wake lock was created.
         * The lock will be released after the given timeout expires.
         * Parameters
         * timeout
         * long: The timeout after which to release the wake lock, in milliseconds.
         * */
        wakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);
        Log.d(TAG, "wakeLock Acquired: ");
        createNotification();
    }

    @TargetApi(Build.VERSION_CODES.O)
    void createNotification() {
        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Intent Service Example Title")
                .setContentText("Running Intent Service")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build();

        startForeground(1, notification);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent");

        assert intent != null;
        String input = intent.getStringExtra("intentServiceInput");

        for (int i = 0; i < 10; i++) {
            Log.d(TAG, input + " - " + i);
            SystemClock.sleep(1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        wakeLock.release();
        Log.d(TAG, "wakelock released: ");
    }
}
