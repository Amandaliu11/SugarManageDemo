package com.example.sugarmanagedemo.activity;

import org.kymjs.aframe.CrashHandler;

import android.app.Application;

public class AppAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.create(this);
    }
}
