package com.example.ankitmah.myapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;


public class ExampleJobService extends JobService {
    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled;

    /**
     * if the task is small and everything can be executed in the scope of this method
     * then we have to return false otherwise true.
     * This tells the system that our job is over when the method is over.
     * Returning true tells the system to keep the device awake so the service can finish executing
     * its work.
     * But we also need to tell the system when we are finished.
     * The job service runs on UI thread.
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: job started");
        doFakeBackgroundWork(params);
        return true;
    }

    void doFakeBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.d(TAG, "run: " + i);
                    if(jobCancelled)
                        return;

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "job finished: ");
                jobFinished(params, false);
            }
        }).start();

    }

    /**
     * This method is called if the system has determined that you must stop execution of your job even
     * before you've had a chance to call jobFinished(JobParameters, boolean).
     * This will happen if the requirements specified at schedule time are no longer met. For example you
     * may have requested WiFi with JobInfo.Builder.setRequiredNetworkType(int), yet while your job was
     * executing the user toggled WiFi. Another example is if you had specified
     * JobInfo.Builder.setRequiresDeviceIdle(boolean), and the phone left its idle maintenance window.
     * You are solely responsible for the behavior of your application upon receipt of this message;
     * your app will likely start to misbehave if you ignore it.
     * Once this method returns, the system releases the wakelock that it is holding on behalf of the job.
     * Specified by: onStopJob in class JobService
     * Parameters: params - The parameters identifying this job, as supplied to the job in the onStartJob(JobParameters) callback.
     * Returns: true to indicate to the JobManager whether you'd like to reschedule this job based on the retry
     * criteria provided at job creation-time; or false to end the job entirely. Regardless of the value returned,
     * your job must stop executing.
     */
    @Override
    public boolean onStopJob(JobParameters params)
    {
        Log.d(TAG, "onStopJob: job cancelled before completion");
        jobCancelled = true;
        return false;
    }


}
