package com.tutu.chifanme.activity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tutu.chifanme.R;
import com.tutu.chifanme.adapter.FragmentAdapter;
import com.tutu.chifanme.app.ActivityManager;
import com.tutu.chifanme.database.DBManager;
import com.tutu.chifanme.fragment.AboutBusinessFragment;
import com.tutu.chifanme.fragment.MakeCommentFragment;
import com.tutu.chifanme.fragment.MakeOrderFragment;
import com.tutu.chifanme.fragment.MakeOrderFragment2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商铺页面
 *
 * 作者：曹贵生 on 2016/12/6.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class BusinessDetailActivity extends FragmentActivity{

    @BindView(R.id.tv_bnName)
    TextView tvBnName;

    // 点餐的tab
    @BindView(R.id.id_chat_tv)
    TextView mTabHomeTv;

    // 评价的tab
    @BindView(R.id.id_friend_tv)
    TextView mTabOrdersTv;

    // 商家介绍的tab
    @BindView(R.id.id_contacts_tv)
    TextView mTabMineTv;

    // Tab的那个引导线
    @BindView(R.id.id_tab_line_iv)
    ImageView mTabLineIv;

    @BindView(R.id.id_page_vp)
    ViewPager mPageVp;

    @OnClick(R.id.iv_bn_detail_back)
    public void goBack() {
        finish();
    }

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;

    // Fragment
    private MakeOrderFragment mMakeOrderFg;
    private MakeCommentFragment mMakeCommentFg;
    private AboutBusinessFragment mAboutBusinessFg;

    private DBManager dbManager;

    // ViewPager的当前选中页
    private int currentIndex;

    // 屏幕的宽度
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bn_detail);
        ActivityManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        init();
        initTabLineWidth();
    }


    // 点击tab选择页面
    private void chosePage() {

        mTabHomeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageVp.setCurrentItem(0);
            }
        });

        mTabOrdersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageVp.setCurrentItem(1);
            }
        });

        mTabMineTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageVp.setCurrentItem(2);
            }
        });
    }

    private void init() {

        mMakeOrderFg = new MakeOrderFragment();
        mMakeCommentFg = new MakeCommentFragment();
        mAboutBusinessFg = new AboutBusinessFragment();
        mFragmentList.add(mMakeOrderFg);
        mFragmentList.add(mMakeCommentFg);
        mFragmentList.add(mAboutBusinessFg);

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
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
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                }
                mTabLineIv.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        mTabHomeTv.setTextColor(Color.parseColor("#FF4081"));
                        break;
                    case 1:
                        mTabOrdersTv.setTextColor(Color.parseColor("#FF4081"));
                        break;
                    case 2:
                        mTabMineTv.setTextColor(Color.parseColor("#FF4081"));
                        break;
                }
                currentIndex = position;
            }

        });

        // 点击tab选择
        chosePage();
        // 初始化数据
        initDate();

    }

    // 初始化数据
    private void initDate() {
        dbManager = new DBManager(this);
        String name = null;
        String bnName = getIntent().getStringExtra("bnName");  // 给fragment传数据
        int id = getIntent().getIntExtra("bId", -1);    // 给fragment传数据
        Cursor cursor = dbManager.queryBusinessById(id + "");
        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex("name"));
        }
        // 设置店名
        tvBnName.setText(name);
    }

    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        lp.width = screenWidth / 3;
        mTabLineIv.setLayoutParams(lp);
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        mTabHomeTv.setTextColor(Color.BLACK);
        mTabOrdersTv.setTextColor(Color.BLACK);
        mTabMineTv.setTextColor(Color.BLACK);
    }
}
