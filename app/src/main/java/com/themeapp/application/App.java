package com.themeapp.application;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.themeapp.utils.HttpInitUtils;

public class App  extends Application {
    public static RequestQueue mRequestQueue;
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = HttpInitUtils.getRequestQueue(this);
        context  = getApplicationContext();
    }
}
