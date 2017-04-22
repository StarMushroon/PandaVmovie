package com.xiao.pandavmovie.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by StarMushroom on 2017/4/22.
 */

public class TimeUtil {

    /**
     * 时间格式化
     * @param time
     * @param pattern
     * @return
     */
    public static String getFormatTime(long time, String pattern) {
        return new SimpleDateFormat(pattern).format( new Date(time) );
    }


}
