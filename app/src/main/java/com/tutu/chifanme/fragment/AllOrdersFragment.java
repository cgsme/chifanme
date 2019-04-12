package com.tutu.chifanme.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tutu.chifanme.R;
import com.tutu.chifanme.database.DBManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 所有订单的 fragment
 *
 * 作者：王捷 on 2016/12/13.
 * 邮箱：
 * 说明：
 */

public class AllOrdersFragment extends Fragment {

    @BindView(R.id.shop_name)
    TextView tvShopName;

    @BindView(R.id.text_foot1)
    TextView tvFood1;

    @BindView(R.id.text_foot2)
    TextView tvFood2;

    @BindView(R.id.text_foot3)
    TextView tvFood3;

    private DBManager dbManager;
    private SharedPreferences mPref;
    private int uId;

    private ArrayList<String> ordersList;   // 用来保存所有订单
    private ArrayList<String> bNameList;   // 用来保存所有商店名

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View ordersView = inflater.inflate(R.layout.fragment_all_orders, container,false);
        ButterKnife.bind(this, ordersView);
        initTabLineWidth();
        return ordersView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        if (ordersList.size() != 0) {
            tvFood1.setText(ordersList.get(0));
//            tvFood2.setText(ordersList.get(1));
        }
    }

    private void initTabLineWidth() {

    }

    /**
     * 初始化数据
     */
    private void initData() {

        mPref = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        dbManager = new DBManager(getContext());
        ordersList = new ArrayList<String>();
        uId = mPref.getInt("userId", -1);
        if (uId != -1 && uId > 0) {
            Cursor allOrdersCursor = dbManager.queryAllOrders(uId + "");
            while (allOrdersCursor.moveToNext()) {
                String orderName = allOrdersCursor.getString(allOrdersCursor.getColumnIndex("order_name"));
                ordersList.add(orderName);
            }
        }
    }


}
