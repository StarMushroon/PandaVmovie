package com.xiao.pandavmovie.config;

/**
 * Created by StarMushroom on 2017/4/17.
 */

public class Netconfig {

    public static String url = "http://flv2.bn.netease.com/videolib3/1704/06/GNzeA0559/SD/GNzeA0559-mobile.mp4";
    public static String VideoUrl = "http://10.0.174.42:8080/MyTomcat/TroubleMaker.mp4";


    /*首页最新顶部轮播图*/
    public static String HOME_NEWEST_BANNER = "http://app.vmoiver.com/apiv3/index/getBanner";

    //首页最新
    public static String HOME_NEWEST_LIST = "http://app.vmoiver.com/apiv3/post/getPostByTab?p=";



    /*****************************************/
    //首页频道
    public static String HOME_CHANNEL = "http://app.vmoiver.com/apiv3/cate/getList";

    //首页频道详情
    public static String HOME_CHANNEL_DETAIL = "http://app.vmoiver.com/apiv3/post/getPostInCate?p=1&cateid=";



    /*************************************/
    //系列
    public static String SERIES = "http://app.vmoiver.com/apiv3/series/getList?p=";

    //系列详情
    public static String SERIES_DETAIL = "http://app.vmoiver.com/apiv3/series/view?seriesid=%1s";


    /*************************************/
    //幕后顶部导航
    public static String BEHIND_TAB = "http://app.vmoiver.com/apiv3/backstage/getCate";

    //幕后内容
    public static String BEHIND_CONTENT = "http://app.vmoiver.com/apiv3/backstage/getPostByCate?cateid=%1$s&p=%2$s";

    //幕后内容详情
    public static String BEHIND_CONTENT_DETAIL = "http://app.vmoiver.com/apiv3/post/view?postid=%1$s";


}

