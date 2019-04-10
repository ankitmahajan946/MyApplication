package com.example.ankitmah.myapplication;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class JobIntentServiceExample extends JobIntentService {
    private static final String TAG = "JobIntentServiceExample";

    static void enqueWork(Context context, Intent work) {
        enqueueWork(context, JobIntentServiceExample.class, 123, work);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork: ");


        for (int i = 0; i < 10; i++) {
            Log.d(TAG, intent.getStringExtra("inputString") + "  i : " + i);
            if (isStopped()) return;
            SystemClock.sleep(500);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public boolean onStopCurrentWork() {
        return super.onStopCurrentWork();
    }
}
