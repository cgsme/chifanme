package com.tutu.chifanme.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tutu.chifanme.R;

import java.util.ArrayList;

/**
 * 点菜右边ListView的adapter
 *
 * 作者：曹贵生 on 2016/12/7.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class RightAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> data = new ArrayList();
    private String titleName;
    private TextView title;

    private String titles[] = { "销量排行", "招牌明星", "奶茶果汁", "销量排行", "招牌明星", "奶茶果汁", "销量排行",
            "招牌明星", "奶茶果汁", "销量排行", "招牌明星", "奶茶果汁", "销量排行", "招牌明星", "奶茶果汁",
            "销量排行", "招牌明星", "奶茶果汁", "销量排行", "招牌明星", "奶茶果汁" };
    /**
     * 这个方法是用来更新数据源
     *
     * @param context
     */
    public RightAdapter(Context context) {
        super();
        this.context = context;
    }

    // 更新数据
    public void updateData(ArrayList list) {
        data.clear();
        data.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_order_right, null);
//            title = (TextView) convertView.findViewById(R.id.tv_right_title);
//            title.setText(titles[position]);
            vh = new ViewHold();
            convertView.setTag(vh);
            vh.tv_content = (TextView) convertView
                    .findViewById(R.id.tv_content);
        } else {
            vh = (ViewHold) convertView.getTag();
        }
        vh.tv_content.setText(data.get(position));
        return convertView;
    }

    // 初始化数据
    public void initData(String t) {
        title.setText(t);
    }

    public class ViewHold {
        TextView tv_content;
    }



}