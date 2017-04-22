package com.xiao.pandavmovie.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Rock on 16/11/29.
 */

public class SharedUtil {

    public static final String APP_NAME = "teach";

    public static boolean getFlag(String key){
        SharedPreferences sdf = getSdf();
        return sdf.getBoolean(key, false);

    }

    public static void putFlag(String key,boolean value){
        SharedPreferences sdf = getSdf();
        sdf.edit().putBoolean(key,value).apply();
    }

    private static SharedPreferences getSdf(){
        return Rock.getContext().getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    }




}
