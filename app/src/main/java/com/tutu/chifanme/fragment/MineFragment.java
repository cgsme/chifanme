package com.tutu.chifanme.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tutu.chifanme.R;
import com.tutu.chifanme.activity.HomeActivity;
import com.tutu.chifanme.activity.LoginActivity;
import com.tutu.chifanme.activity.RegisterActivity;
import com.tutu.chifanme.activity.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * “我的” fragment
 *
 * 作者：陈岚，林翠丽 on 2016/12/3.
 * 邮箱：
 * 说明：
 */

public class MineFragment extends Fragment {

    @BindView(R.id.login_show)
    LinearLayout mine_logined;

    @BindView(R.id.unlogin_show)
    RelativeLayout mine_unlogin;

    @OnClick(R.id.activity_mine_setting)
    public void jumpToSetting() {
        Intent intent = new Intent(getContext(), SettingActivity.class);
        startActivity(intent);
    }

    // 拿到sp
    private SharedPreferences userInfo;

    @OnClick(R.id.activity_welcome_button_login)
    public void jumpToLogin() {

        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.activity_welcome_button_register)
    public void jumpToRegister() {
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 收货地址
     */
    /*@OnClick(R.id.activity_mine_receiver_item)
    public void receiver() {
        Toast.makeText(getContext(), "lalala", Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_mine, container,false);
        ButterKnife.bind(this, view);
        return view;
    }

    // 初始化控件
    private void initView() {

        if (isLogin()) {
            mine_unlogin.setVisibility(View.GONE);
            mine_logined.setVisibility(View.VISIBLE);
        } else {
            mine_logined.setVisibility(View.GONE);
            mine_unlogin.setVisibility(View.VISIBLE);
        }
    }

    // 判断用户是否登录
    private boolean isLogin() {

        userInfo = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        boolean isLogin = userInfo.getBoolean("isLogin", false);
        if (isLogin) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }
}
