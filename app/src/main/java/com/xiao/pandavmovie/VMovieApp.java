package com.xiao.pandavmovie;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.xiao.pandavmovie.utils.Rock;


public class VMovieApp extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化
        Rock.init(this);
        sContext = this;
    }



    public static Context getContext() {
        return sContext;
    }
}
