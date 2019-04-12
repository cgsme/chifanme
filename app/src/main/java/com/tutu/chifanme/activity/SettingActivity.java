package com.tutu.chifanme.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.tutu.chifanme.R;
import com.tutu.chifanme.views.SettingItemView;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 设置中心页面
 *
 * 作者：林翠丽 on 2016/11/10.
 * 邮箱：
 * 说明：
 */

public class SettingActivity extends Activity {

    private SettingItemView siv_Update;
    private SharedPreferences mPref;

    @OnClick(R.id.setting_about_us_item)
    public void showAboutUs() {
        startActivity(new Intent(this, AboutUsActivity.class));
    }


    /**
     * 退出当前账号
     */
    @OnClick(R.id.setting_logout)
    public void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("退出后无法查看订单，重新登录后即可查看");
        builder.setTitle("提示...");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPref.edit().clear().commit();
                finish();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
            }
        });
        builder.show();

    }

    /**
     * 返回
     */
    @OnClick(R.id.setting_back)
    public void goBack() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mPref = getSharedPreferences("userInfo", MODE_PRIVATE);


//        siv_Update = (SettingItemView) findViewById(R.id.siv_update);
//        siv_Update.setTitle("自动更新设置");
/*
        boolean auto_update = mPref.getBoolean("auto_update", true);
        if (auto_update) {
//            siv_Update.setDesc("自动更新已开启");
            siv_Update.setCheck(true);
        } else {
//            siv_Update.setDesc("自动更新已关闭");
            siv_Update.setCheck(false);
        }

        siv_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 判断当前checkBox状态
                if (siv_Update.isCheck()) {
                    // 设置不勾选
                    siv_Update.setCheck(false);
//                    siv_Update.setDesc("自动更新已关闭");

                    // 更新sp
                    mPref.edit().putBoolean("auto_update", false).commit();

                } else {
                    // 设置勾选
                    siv_Update.setCheck(true);
//                    siv_Update.setDesc("自动更新已开启");

                    // 更新sp
                    mPref.edit().putBoolean("auto_update", true).commit();

                }
            }
        });*/
    }
}
