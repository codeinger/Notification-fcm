package com.codeinger.notification_fcm.utils;

import android.app.Application;

import androidx.multidex.MultiDex;

public class App extends Application {
    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
    }
}
