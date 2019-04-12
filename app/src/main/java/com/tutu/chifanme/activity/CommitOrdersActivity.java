package com.tutu.chifanme.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tutu.chifanme.R;
import com.tutu.chifanme.app.ActivityManager;
import com.tutu.chifanme.database.DBManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 提交订单的activity
 *
 * 作者：曹贵生 on 2016/12/11.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class CommitOrdersActivity extends Activity{

    @BindView(R.id.commit_order_lv)
    ListView lvOrder;

    @BindView(R.id.commit_now_btn)
    TextView tvPay;

    @BindView(R.id.tv_orders_business_name)
    TextView tvBusinessName;

    private String bName;    // 商家名称
    private EditText etUsername;
    private EditText etPassword;

    @OnClick(R.id.iv_commit_back)
    public void goBack() {
        finish();
    }

    private ArrayList<String> selectList;  // 提交的所有商品
    private String cost;    // 总价
    private ArrayList<String> costList;   // 商品单价集合
    private SharedPreferences mPref;   // sp
    private DBManager dbManager;
    private Intent intent;

    // 用户名和密码
    private String phone;
    private String password;

    /**
     * 提交订单
     */
    @OnClick(R.id.commit_now_btn)
    public void commit() {
        mPref = getSharedPreferences("userInfo", MODE_PRIVATE);
        int userId = mPref.getInt("userId", -1);
        // 判断用户是否登录
        if (userId != -1) {    // 用户已登录
            // 保存订单到数据库
            dbManager = new DBManager(this);
            // 保存订单到数据库
            dbManager.saveOrders(selectList, costList, userId, bName);
            // 销毁本activity
            finish();
            // 销毁商家activity
            ActivityManager.getAppManager().finishActivity(BusinessDetailActivity.class);
            ActivityManager.getAppManager().finishActivity(SearchActivity.class);
            Toast.makeText(this, "订单已提交...", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, HomeActivity.class);
            startActivity(intent);

        } else {    // 用户未登录，显示登录对话框

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final AlertDialog dialog = builder.create();
            View view = View.inflate(this, R.layout.activity_login, null);
            etUsername = (EditText) view.findViewById(R.id.activity_login_edittext_username);
            etPassword = (EditText) view.findViewById(R.id.activity_login_edittext_password);
            TextView tvLogin = (TextView) view.findViewById(R.id.activity_login_button_login);
            TextView tvForgetPassword = (TextView) view.findViewById(R.id.activity_login_button_forget_password);
            TextView tvRegisterNow = (TextView) view.findViewById(R.id.activity_login_button_register_now);
            ImageView ivBack = (ImageView) view.findViewById(R.id.iv_login_back);  // 返回键

            /**
             * 点击登录按钮
             */
            tvLogin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (prepareSubmit()) {
                        String pwd = "";
                        int userId = -1;
                        dbManager = new DBManager(CommitOrdersActivity.this);
                        Cursor cursor = dbManager.queryUserByPhone(CommitOrdersActivity.this.phone);
                        while (cursor.moveToNext()) {
                            pwd = cursor.getString(cursor.getColumnIndex("password"));
                            userId = cursor.getInt(cursor.getColumnIndex("_id"));
                        }
                        if (!TextUtils.isEmpty(pwd) && pwd.equals(password)) {
                            Toast.makeText(CommitOrdersActivity.this, "登录成功",Toast.LENGTH_SHORT).show();
                            // 保存用户登录状态
                            mPref.edit().putBoolean("isLogin", true).commit();
                            mPref.edit().putInt("userId", userId).commit();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(CommitOrdersActivity.this, "用户名或密码不正确",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            // 点击忘记密码
            tvForgetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            // 点击立即注册
            tvRegisterNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CommitOrdersActivity.this, RegisterActivity.class));
                }
            });

            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.setView(view);
            dialog.show();
            Toast.makeText(this, "请先登录哦~", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_orders);
        ButterKnife.bind(this);
        initData();
        initView();
        lvOrder.setAdapter(new OrderAdapter());
    }

    private void initView() {
        tvPay.setText("提交订单  " + cost);
        tvBusinessName.setText(bName);
    }

    private void initData() {
        selectList = new ArrayList<String>();
        costList = new ArrayList<String>();
        intent = getIntent();
        selectList = intent.getStringArrayListExtra("selectList");
        costList = intent.getStringArrayListExtra("costList");
        cost = intent.getStringExtra("cost");
        bName = intent.getStringExtra("bName");
    }

    /**
     * 判断是否通过字段校验
     * @return
     */
    private boolean prepareSubmit() {

        if(etUsername.getText().length() == 0) {
            etUsername.requestFocus();
            etUsername.setError(getText(R.string.activity_login_prepare_username_empty));
            return false;
        }

        if(etPassword.getText().length() == 0) {
            etPassword.requestFocus();
            etPassword.setError(getText(R.string.activity_login_prepare_password_empty));
            return false;
        }

        phone = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        return true;
    }

    public class OrderAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return selectList.size();
        }

        @Override
        public Object getItem(int i) {
            return selectList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(getBaseContext(), R.layout.item_commit_list, null);
            TextView tvName = (TextView) view.findViewById(R.id.tv_order_name);
            TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvName.setText(selectList.get(i));
            tvPrice.setText(costList.get(i));
            return view;
        }
    }
}
