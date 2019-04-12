package com.tutu.chifanme.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.tutu.chifanme.R;
import com.tutu.chifanme.app.ActivityManager;
import com.tutu.chifanme.base.activity.BaseBackActivity;
import com.tutu.chifanme.beans.Person;
import com.tutu.chifanme.database.DBManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册
 *
 * 作者： 曹贵生 on 2016/7/22.
 * 邮箱：1595143088@qq.com
 * 说明：
 */
public class RegisterActivity extends BaseBackActivity {

    private DBManager dbManager;

    // 手机号码
    private String phone;
    // 密码
    private String password;

    protected static final String TAG = RegisterActivity.class.getSimpleName();

    // 文本监听
    private TextWatcher textWatcher;

    // 标记该电话号码是否获取校验码
    private static boolean isSendCode = true;

    // 手机号码输入框
    @BindView(R.id.activity_register_edit_phone)
    EditText edit_phone;

    // 密码输入框
    @BindView(R.id.activity_register_edit_password)
    EditText edit_password;

    // 注册页屏幕
    @OnClick(R.id.activity_register_screen)
    public void closeKeyBoard() {
        // 点击空白处收起软键盘
        hideInputKeyBoard();
    }

    @OnClick(R.id.iv_register_back)
    public void goBack() {
        finish();
    }


    //点击注册按钮
    @OnClick(R.id.activity_register_button)
    public void requestRegister() {
        if (prepareToRegister()) {
            if (isHaveUser()){
                Toast.makeText(this, "sorry,该用户已存在~", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "恭喜你,注册成功", Toast.LENGTH_SHORT).show();
                // 将注册信息保存到数据库
                List<Person> personList = new ArrayList<>();
                Person person = new Person(phone, password);
                personList.add(person);
                dbManager.add(personList);
                // 跳至主界面
                /*Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("phone", edit_phone.getText().toString());
                intent.putExtra("password", edit_password.getText().toString().trim());
                startActivity(intent);*/
                // 销毁当前页
                finish();
            }
        }
    }

    private boolean isHaveUser() {

        Cursor cursor = dbManager.queryUserByPhone(phone);
        if (cursor.getCount() != 0) {
            cursor.close();
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getAppManager().addActivity(this);
        dbManager = new DBManager(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected boolean initBundle(Bundle bundle) {
        return super.initBundle(bundle);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //文本监听
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 防止用户发完验证码后又修改手机号码
                isSendCode = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        edit_phone.addTextChangedListener(textWatcher);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private boolean prepareToRegister() {

        //手机号码为空
        if (edit_phone.length() == 0) {
            edit_phone.setError(getText(R.string.activity_register_prepare_phone_empty));
            edit_phone.requestFocus();
            return false;
        }

        //密码为空
        if (edit_password.length() == 0) {

            edit_password.setError(getText(R.string.activity_register_password_prepare_password_empty));
            edit_password.requestFocus();
            return false;

        } else if (edit_password.getText().toString().trim().length() == 0) {

            edit_password.setError(getText(R.string.activity_register_prepare_phone_all_space));
            edit_password.requestFocus();
            return false;
        }

        phone = edit_phone.getText().toString().trim();
        password = edit_password.getText().toString().trim();

        return true;
    }
}
