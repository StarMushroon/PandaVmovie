package com.xiao.pandavmovie.ui.home.home_Newest.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.VideoView;


import com.xiao.pandavmovie.R;


public class NewestDetailActivity extends AppCompatActivity {

    private String mRequestUrl;
    private VideoView mVideo;
    private WebView mWebview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_newest_detail);

        mRequestUrl = getIntent().getExtras().getString("request_url");
        initVideiView();
        initWebview();
    }


    private void initWebview() {
        mWebview = (WebView) findViewById(R.id.webview_activity_newest_detail);
        mWebview.loadUrl(mRequestUrl);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebview.loadUrl(url);
                return true;
            }
        });

        WebSettings webSettings = mWebview.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
    }

    private void initVideiView() {
        String url = "http://flv2.bn.netease.com/videolib3/1704/06/GNzeA0559/SD/GNzeA0559-mobile.mp4";
        mVideo = (VideoView) findViewById(R.id.videoview_activity_newest_detail);
        mVideo.setVideoPath(url);
        mVideo.start();
    }

}
