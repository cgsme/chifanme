package com.tutu.chifanme.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tutu.chifanme.R;
import com.tutu.chifanme.app.ActivityManager;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 关于我们的Activity
 *
 * 作者：林翠丽 on 2016/8/27.
 * 邮箱：
 * 说明：
 */
public class AboutUsActivity extends Activity{

    @OnClick(R.id.about_us_back)
    public void goBack() {
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ActivityManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
    }

}
