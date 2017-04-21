package com.xiao.pandavmovie.ui.behind.behindContent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.xiao.pandavmovie.R;

/**
 * 幕后内容详情的web界面
 */
public class BehindContentWebActivity extends AppCompatActivity {

    private WebView mWebview;
    private String mWebUrl;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_behind_content_web);
        mWebUrl = getIntent().getStringExtra("webUrl");   //获取网址
        Log.d("web", "onCreate: "+mWebUrl);
        mTitle = getIntent().getStringExtra("title");
        initWebview();

    }

    private void initWebview() {
        mWebview = (WebView) findViewById(R.id.webview_activity_behind_content_web);
        mWebview.loadUrl(mWebUrl);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebview.loadUrl(url);
                return true;
            }
        });
        mWebview.setWebChromeClient(new WebChromeClient() );

        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
    }
}
