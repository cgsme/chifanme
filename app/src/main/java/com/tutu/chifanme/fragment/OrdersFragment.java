package com.tutu.chifanme.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tutu.chifanme.R;
import com.tutu.chifanme.activity.LoginActivity;
import com.tutu.chifanme.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单fragment
 *
 * 作者：曹贵生 on 2016/12/3.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class OrdersFragment extends Fragment {

    // 所有订单的tab
    @BindView(R.id.tv_all_orders)
    TextView mTabAllOrderTv;

    // 待评价的tab
    @BindView(R.id.tv_wait)
    TextView mTabWaitTv;

    // Tab的那个引导线
    @BindView(R.id.id_tab_line_iv)
    ImageView mTabLineIv;

    @BindView(R.id.id_page_vp)
    ViewPager mPageVp;

    // 登录后显示的布局
    @BindView(R.id.orders_layout_Login)
    LinearLayout loginedLayout;

    // 未登录时显示的布局
    @BindView(R.id.orders_layout_unLogin)
    LinearLayout unLoginLayout;

    /**
     * 跳转到登录界面
     */
    @OnClick(R.id.orders_login_now_btn)
    public void jumpToLogin() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    private SharedPreferences mPref;

    // Fragment
    private AllOrdersFragment mAllOrderFg;
    private WaitEvaluateFragment mWaitEvaluateFg;

    // ViewPager的当前选中页
    private int currentIndex;

    // 屏幕的宽度
    private int screenWidth;
    private int userId;   // 用户id

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View ordersView = inflater.inflate(R.layout.fragment_orders, container,false);
        ButterKnife.bind(this, ordersView);
        return ordersView;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 根据用户登录状态显示相应布局
     */
    public void showLayout() {
        if (isUserLogin()) {
            unLoginLayout.setVisibility(View.GONE);
            loginedLayout.setVisibility(View.VISIBLE);
        } else {
            loginedLayout.setVisibility(View.GONE);
            unLoginLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 判断用户是否登录
     * @return
     */
    public boolean isUserLogin() {
        mPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId = mPref.getInt("userId", -1);
        if (userId != -1 && userId > 0) {
            return true;
        }
        return false;
    }

    // 点击tab选择页面
    private void chosePage() {

        mTabAllOrderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageVp.setCurrentItem(0);
            }
        });

        mTabWaitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageVp.setCurrentItem(1);
            }
        });
    }

    private void init() {

        initTabLineWidth();
        showLayout();

        mAllOrderFg = new AllOrdersFragment();
        mWaitEvaluateFg = new WaitEvaluateFragment();
        mFragmentList.add(mAllOrderFg);
        mFragmentList.add(mWaitEvaluateFg);

        mFragmentAdapter = new FragmentAdapter(getChildFragmentManager(), mFragmentList);
        mPageVp.setAdapter(mFragmentAdapter);
        mPageVp.setCurrentItem(0);

        // 监听页面改变
        mPageVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }

            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset,
                                       int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                        .getLayoutParams();

                Log.e("offset:", offset + "");
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景：
                 * 记3个页面,
                 * 从左到右分别为0,1,2
                 * 0->1; 1->2; 2->1; 1->0
                 */
                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currentIndex
                            * (screenWidth / 2));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 2) + currentIndex
                            * (screenWidth / 2));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currentIndex
                            * (screenWidth / 2));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 2) + currentIndex
                            * (screenWidth / 2));
                }
                mTabLineIv.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        mTabAllOrderTv.setTextColor(Color.parseColor("#FF4081"));
                        break;
                    case 1:
                        mTabWaitTv.setTextColor(Color.parseColor("#FF4081"));
                        break;

                }
                currentIndex = position;
            }

        });
        // 点击tab选择
        chosePage();
    }


    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getActivity().getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        lp.width = screenWidth / 2;
        mTabLineIv.setLayoutParams(lp);
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        mTabAllOrderTv.setTextColor(Color.BLACK);
        mTabWaitTv.setTextColor(Color.BLACK);
    }
}
