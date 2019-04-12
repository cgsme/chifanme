package com.tutu.chifanme.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tutu.chifanme.R;
import com.tutu.chifanme.adapter.LeftAdapter;
import com.tutu.chifanme.adapter.RightAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 点菜fragment
 *
 * 作者：曹贵生 on 2016/12/3.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class MakeOrderFragment2 extends Fragment {

    private List<ArrayList<String>> lists;
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;

    @BindView(R.id.lv_left)
    ListView lv_left;

    @BindView(R.id.lv_Right)
    ListView lv_right;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View makeOrderView = inflater.inflate(R.layout.fragment_make_order2, container,false);
        ButterKnife.bind(this, makeOrderView);
        initView();
        initData();
        return makeOrderView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    private void initData() {
        lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("萝卜");
        list1.add("大葱");
        list1.add("茄子");
        list1.add("大蒜");
        list1.add("生姜");
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("苹果");
        list2.add("梨");
        list2.add("香蕉");
        list2.add("橘子");
        list2.add("西瓜");
        ArrayList<String> list3 = new ArrayList<String>();
        list3.add("郑一");
        list3.add("王二");
        list3.add("伊三");
        list3.add("荆四");
        list3.add("汤五");

        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
       /* for (int i = 0; i < 7; i++) {
            lists.add(list1);
            lists.add(list2);
            lists.add(list3);
        }*/

    }

    //初始化view
    private void initView() {

        leftAdapter = new LeftAdapter(getContext());
        lv_left.setAdapter(leftAdapter);

        rightAdapter = new RightAdapter(getContext());
        lv_right.setAdapter(rightAdapter);

//        rightAdapter.updateData(lists.get(0));//设置初始值
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                leftAdapter.updateData(arg2);
                rightAdapter.updateData(lists.get(arg2));
            }
        });


    }
}
