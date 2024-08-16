package com.example.petpals.Utilities;

import android.app.Application;

import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharePreferencesManagerV3.init(this);
        SignalManager.init(this);
//        PeriodicWorkRequest notificationWork = new PeriodicWorkRequest.Builder(NotificationWorker.class, 1, TimeUnit.HOURS)
//                .build();
//        WorkManager.getInstance(this).enqueue(notificationWork);

        //test the notifications:
        WorkManager.getInstance(this).enqueue(new OneTimeWorkRequest.Builder(NotificationWorker.class).build());

    }
}