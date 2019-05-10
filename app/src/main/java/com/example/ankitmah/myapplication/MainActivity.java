package com.example.ankitmah.myapplication;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ankitmah.myapplication.login.ui.login.LoginActivity;
import com.example.ankitmah.myapplication.myLogin.MyLoginActivity;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MAIN_ACTIVITY";
    EditText mEtIntentServiceInput, mEtForegroundService, mEtJobIntentServiceInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("onCreate:", "called");


        setUpUI();

    }

    void setUpUI() {
        //intent service
        Button btnStartIntentService, btnScheduleJob, btnCancelJob, btnStartForegroundService,
                btnStopForegroundService, btnStartJobIntentService, btnStartLoginActivity,
                btnStartMyLoginActivity;


        btnStartIntentService = findViewById(R.id.btn_start_intent_service);
        mEtIntentServiceInput = findViewById(R.id.et_intent_service);
        btnStartIntentService.setOnClickListener(this);

        btnScheduleJob = findViewById(R.id.btn_schedule_job);
        btnCancelJob = findViewById(R.id.btn_cancel_job);
        btnScheduleJob.setOnClickListener(this);
        btnCancelJob.setOnClickListener(this);

        mEtForegroundService = findViewById(R.id.et_foreground_service);
        btnStartForegroundService = findViewById(R.id.btn_start_foreground_service);
        btnStopForegroundService = findViewById(R.id.btn_stop_foreground_service);
        btnStartForegroundService.setOnClickListener(this);
        btnStopForegroundService.setOnClickListener(this);

        btnStartJobIntentService = findViewById(R.id.btn_start_job_intent_service);
        mEtJobIntentServiceInput = findViewById(R.id.et_job_intent_service);
        btnStartJobIntentService.setOnClickListener(this);

        btnStartLoginActivity = findViewById(R.id.btn_startLoginActivity);
        btnStartLoginActivity.setOnClickListener(this);

        btnStartMyLoginActivity = findViewById(R.id.btn_startMyLoginActivity);
        btnStartMyLoginActivity.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume:", "called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause:", "called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("onStop:", "called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy:", "called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("onRestart:", "called");
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId) {
            case R.id.btn_start_intent_service:

                //startActivity(new Intent(this,TestBottomNavigationActivity.class));
                startIntentService();
                break;

            case R.id.btn_schedule_job:
                scheduleJob();
                break;

            case R.id.btn_cancel_job:
                cancelJob();
                break;

            case R.id.btn_start_foreground_service:
                startServiceInForeground();
                break;

            case R.id.btn_stop_foreground_service:
                stopServiceInForeground();
                break;

            case R.id.btn_start_job_intent_service:
                startJobIntentService();
                break;

            case R.id.btn_startLoginActivity:
                startLoginActivity();
                break;

            case R.id.btn_startMyLoginActivity:
                startMyLoginActivity();
                break;

        }
    }

    private void startMyLoginActivity() {
        startActivity(new Intent(this, MyLoginActivity.class));
    }

    void startIntentService() {
        String input = mEtIntentServiceInput.getText().toString();
        Intent serviceIntent = new Intent(this, IntentServiceExample.class);
        serviceIntent.putExtra("intentServiceInput", input);
        ContextCompat.startForegroundService(this, serviceIntent);
        //startService(serviceIntent);
    }

    void scheduleJob() {
        ComponentName componentName = new ComponentName(this, ExampleJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(12345, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(true)
                .setPeriodic(15 * 60 * 1000 /*15 minutes*/)
                .setPersisted(true)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        int resultCode = jobScheduler.schedule(jobInfo);

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "job scheduled: ");
        } else {
            Log.d(TAG, "job scheduling failed: ");
        }
    }

    void cancelJob() {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(12345);
        Log.d(TAG, "job cancelled ");
    }

    private void startServiceInForeground() {
        Intent intent = new Intent(this, ForegroundService.class);
        intent.putExtra("input", mEtForegroundService.getText().toString());


        /*startService will work while the app is still open, but if you want to start the service
         * while the app itself is in the background then you must use startForegroundService().
         * startForegroundService() tells the system to promote your service to foreground as
         * quickly as possible. and after calling this method you have a time window of 5 seconds to
         * call startForeground() in your service class, if you don't do this within 5 seconds, the
         * system will kill the service immediately.
         * If you try to call startService in the background then the system will throw an
         * IllegalStateException and your app will crash.
         * There are some situations where your app is under temporary whitelist and you can call
         * startService() from the background without a crash but usually when your app is in the
         * background you should use startForegroundService().
         * if you call startService() and don't call startForeground from onStartCommand() then the
         * Request that a given application service be started.
         * The Intent should contain either contain the complete class name of a specific service
         * implementation to start or a specific package name to target.
         * If the Intent is less specified, it log a warning about this and which of the multiple
         * matching services it finds and uses will be undefined. If this service is not already
         * running, it will be instantiated and started (creating a process for it if needed);
         * if it is running then it remains running.
         * Every call to this method will result in a corresponding call to the target service's
         * onStartCommand(Intent, int, int) method, with the intent given here. This provides a convenient
         * way to submit jobs to a service without having to bind and call on to its interface.
         * Using startService() overrides the default service lifetime that is managed by bindService(Intent,
         * ServiceConnection, int): it requires the service to remain running until stopService(Intent)
         * is called, regardless of whether any clients are connected to it. Note that calls to
         * startService() are not nesting: no matter how many times you call startService(), a single call
         * to stopService(Intent) will stop it.
         * The system attempts to keep running services around as much as possible. The only time they should
         * be stopped is if the current foreground application is using so many resources that the service
         * needs to be killed. If any errors happen in the service's process, it will automatically be restarted.
         * This function will throw SecurityException if you do not have permission to start the given service.
         * Note: Each call to startService() results in significant work done by the system to manage service
         * lifecycle surrounding the processing of the intent, which can take multiple milliseconds of CPU time.
         * Due to this cost, startService() should not be used for frequent intent delivery to a service,
         * and only for scheduling significant work. Use bound services for high frequency calls.
         */

        //startService(intent);

        /*
         * Similar to startService(Intent), but with an implicit promise that the Service will call
         * startForeground(int, android.app.Notification) once it begins running.
         * The service is given an amount of time comparable to the ANR interval to do this,
         * otherwise the system will automatically stop the service and declare the app ANR.
         * Unlike the ordinary startService(Intent), this method can be used at any time, regardless of
         * whether the app hosting the service is in a foreground state.
         * */
        ContextCompat.startForegroundService(this, intent);
    }

    private void stopServiceInForeground() {
        Intent intent = new Intent(this, ForegroundService.class);
        stopService(intent);
    }

    void startJobIntentService() {
        Intent jobIntentServiceIntent = new Intent(this, JobIntentServiceExample.class);
        jobIntentServiceIntent.putExtra("inputString", mEtJobIntentServiceInput.getText().toString());
        //ContextCompat.startForegroundService(this,jobIntentServiceIntent);
        JobIntentServiceExample.enqueWork(this, jobIntentServiceIntent);
    }

    void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}

