package com.tutu.chifanme.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tutu.chifanme.widget.CustomProgressDialog;

import butterknife.ButterKnife;


/**
 * 基类Activity
 *
 * 作者:李俊贤  on 16/7/20.
 * 邮箱:21194250@qq.com
 * 说明:
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected static final String TAG = BaseActivity.class.getSimpleName();

    // 自定义ProgressDialog
    private CustomProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (initBundle(getIntent().getExtras())) {

            setContentView(getContentView());

            initWindow();

            ButterKnife.bind(this);
            initWidget();
            initView();
            initData();
        }
        else finish();
    }

    protected abstract int getContentView();

    protected boolean initBundle(Bundle bundle) {
        return true;
    }

    protected void initWindow() {

    }

    protected void initWidget() {

    }

    protected void initView() {

    }

    protected void initData() {

    }

    public void showProgressDialog(Context context, int message) {
        if(null == progressDialog) progressDialog = new CustomProgressDialog(context);
        progressDialog.showProgressDialog(message);
    }

    public void hideProgressDialog() {
        if(null != progressDialog) progressDialog.dismiss();
    }

    protected void requestSuccessHandler() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog = null;
    }



}
