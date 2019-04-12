package com.tutu.chifanme.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.tutu.chifanme.R;


/**
 * 自定义ProgressDialog
 *
 * 作者:李俊贤  on 16/7/21.
 * 邮箱:21194250@qq.com
 * 说明:
 */
public class CustomProgressDialog extends ProgressDialog {


    public CustomProgressDialog(Context context) {
        super(context);
        this.setProgressStyle(R.style.custom_progress_dialog);
    }

    public void showProgressDialog(int message) {
        this.show();
        this.setContentView(R.layout.custom_progress_dialog);
        TextView textView = (TextView) findViewById(R.id.message);
        if (null != textView) textView.setText(message);
    }

    public void hideProgressDialog() {
        this.hide();
    }


    public void onWindowFocusChanged(boolean hasFocus){
        ImageView imageView = (ImageView) findViewById(R.id.progress);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

}
