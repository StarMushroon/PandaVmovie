package com.xiao.pandavmovie.utils;

import android.annotation.SuppressLint;
import android.app.Application;


public class Rock{

    @SuppressLint("StaticFieldLeak")
    private static Application sApp;

    /**
     * 初始化方法
     */
    public static void init(Application application){
        sApp = application;

    }

    public static Application getContext(){
        if (sApp == null) {
            throw new NullPointerException("Rock需要初始化");
        }
        return sApp;
    }


}
