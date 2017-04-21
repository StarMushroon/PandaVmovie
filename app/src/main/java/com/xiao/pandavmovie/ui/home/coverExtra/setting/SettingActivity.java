package com.xiao.pandavmovie.ui.home.coverExtra.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.xiao.pandavmovie.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
    }
}
