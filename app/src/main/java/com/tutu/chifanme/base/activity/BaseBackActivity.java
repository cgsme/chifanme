package com.tutu.chifanme.base.activity;

import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 带返回按钮的基础Activity
 *
 * 作者:李俊贤  on 16/7/20.
 * 邮箱:21194250@qq.com
 * 说明:
 */
public abstract class BaseBackActivity extends BaseActivity {

    @Override
    protected void initWindow() {
        super.initWindow();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // 点击返回按钮时关闭软键盘
        hideInputKeyBoard();
        finish();
        return super.onSupportNavigateUp();
    }

    // 关闭软键盘
    public void hideInputKeyBoard() {

        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
