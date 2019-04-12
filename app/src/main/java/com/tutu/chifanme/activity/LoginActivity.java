package com.tutu.chifanme.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.tutu.chifanme.app.ActivityManager;
import com.tutu.chifanme.base.activity.BaseBackActivity;
import com.tutu.chifanme.database.DBManager;
import com.tutu.chifanme.utils.UIHelper;

import butterknife.BindView;
import butterknife.OnClick;

import com.tutu.chifanme.R;


/**
 * 登录
 *
 * 作者:陈岚  on 16/7/21.
 * 说明:
 */
public class LoginActivity extends BaseBackActivity {

    protected static final String TAG = LoginActivity.class.getSimpleName();

    public static final String USER = "user";
    public static final String USERNAME = "username";
    public static final String USERNAME_TEMP = "username_temp";
    public static final String PASSWORD = "password";

    private DBManager dbmanager;

    private String phone;
    private String password;

    @BindView(R.id.activity_login_edittext_username)
    EditText edit_username;

    @BindView(R.id.activity_login_edittext_password)
    EditText edit_password;
    private SharedPreferences mPref;

    @OnClick(R.id.iv_login_back)
    public void goBack() {
        finish();
    }

    @OnClick(R.id.activity_login_button_register_now)
    public void jumpToRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }


    @OnClick(R.id.activity_login_screen)
    public void closeKeyBoard() {
        // 点击空白处收起软键盘
        hideInputKeyBoard();
    }

    @OnClick(R.id.activity_login_button_forget_password)
    public void jumpToForgetPassword() {
        UIHelper.showForgetPasswordActivity(LoginActivity.this);
    }

    @OnClick(R.id.activity_login_button_login)
    public void requestLogin() {
        checkLogin();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getAppManager().addActivity(this);
        mPref = getSharedPreferences("userInfo", MODE_PRIVATE);
    }

    // 验证登录
    private void checkLogin() {

        if (prepareSubmit()) {
            String pwd = "";
            int userId = -1;
            dbmanager = new DBManager(this);
            Cursor cursor = dbmanager.queryUserByPhone(this.phone);
            while (cursor.moveToNext()) {
                pwd = cursor.getString(cursor.getColumnIndex("password"));
                userId = cursor.getInt(cursor.getColumnIndex("_id"));
            }
            if (!TextUtils.isEmpty(pwd) && pwd.equals(password)) {
                Toast.makeText(this, "登录成功",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(this, HomeActivity.class));
                // 保存用户登录状态
                mPref.edit().putBoolean("isLogin", true).commit();
                mPref.edit().putInt("userId", userId).commit();
                // 销毁activity
                finish();
            } else {
                Toast.makeText(this, "用户名或密码不正确",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initView() {

        // 如果偏好中有用户的账号信息
        // 则将它们填充到控件上
       /* String username_temp = PreferenceHelper.readString(LoginActivity.this, USER, USERNAME_TEMP);
        if(null != username_temp) {

            edit_username.setText(username_temp);
            // 将光标显示在输入框最后的位置
            edit_username.setSelection(edit_username.length());
        }*/
    }

    /**
     * 判断是否通过字段校验
     * @return
     */
    private boolean prepareSubmit() {

        if(edit_username.getText().length() == 0) {
            edit_username.requestFocus();
            edit_username.setError(getText(R.string.activity_login_prepare_username_empty));
            return false;
        }

        if(edit_password.getText().length() == 0) {
            edit_password.requestFocus();
            edit_password.setError(getText(R.string.activity_login_prepare_password_empty));
            return false;
        }

        phone = edit_username.getText().toString().trim();
        password = edit_password.getText().toString().trim();

        return true;
    }
}