package com.tutu.chifanme.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.tutu.chifanme.R;
import com.tutu.chifanme.base.activity.BaseBackActivity;
import com.tutu.chifanme.database.DBManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索的activity
 *
 * 作者：曹贵生 on 2016/12/16.
 * 邮箱：1595143088@qq.com
 * 说明：
 */
public class SearchActivity extends BaseBackActivity {

    @BindView(R.id.sv_search)
    SearchView mSearch;

    @BindView(R.id.lv_search_result)
    ListView lvResult;

    @BindView(R.id.tv_no_result)
    TextView tvNoResult;

    @OnClick(R.id.activity_search_screen)
    public void closeKeyBoard() {
        // 点击空白处收起软键盘
        hideInputKeyBoard();
    }

    private DBManager dbManager;
    private List<String> resultList;
    private List<Integer> idList;
    private ArrayAdapter resultAdapter;

    /**
     * 点击返回
     */
    @OnClick(R.id.activity_search_back)
    public void goBack() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        dbManager = new DBManager(this);
        search();
        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, BusinessDetailActivity.class);
                intent.putExtra("bId", idList.get(i));
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    /**
     * 搜索
     */
    private void search() {

        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // 点击提交（搜索）时
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            // 文本改变时
            @Override
            public boolean onQueryTextChange(String s) {
                resultList = new ArrayList<String>();
                idList = new ArrayList<Integer>();
//                resultList.clear();
                // 若查询条件不为空
                if (!TextUtils.isEmpty(s)) {
                    Cursor menuCursor = dbManager.queryMenusByLike(s);
                    Cursor businessCursor = dbManager.queryBusinessByLike(s);
                    // 查询商家
                    while (businessCursor.moveToNext()) {
                        // 设置布局是否可见
                        tvNoResult.setVisibility(View.GONE);
                        lvResult.setVisibility(View.VISIBLE);
                        resultList.add(businessCursor.getString(businessCursor.getColumnIndex("name")));
                        idList.add(businessCursor.getInt(businessCursor.getColumnIndex("_id")));
                    }
                    // 查询商品
                    while (menuCursor.moveToNext()) {
                        tvNoResult.setVisibility(View.GONE);
                        lvResult.setVisibility(View.VISIBLE);
                        resultList.add(menuCursor.getString(menuCursor.getColumnIndex("menu_name")));
                        idList.add(menuCursor.getInt(menuCursor.getColumnIndex("business_id")));
                    }
                    // 设置适配器
                    resultAdapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, resultList);
                    lvResult.setAdapter(resultAdapter);
                    if (resultList.size() == 0) {
                        lvResult.setVisibility(View.GONE);
                        tvNoResult.setVisibility(View.VISIBLE);
                    }
                // 当查询条件为空时
                } else {
                    resultAdapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, resultList);
                    lvResult.setAdapter(resultAdapter);
                }
                return true;
            }
        });
    }


}
