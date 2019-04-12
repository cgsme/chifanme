package com.tutu.chifanme.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tutu.chifanme.R;

/**
 * 左边ListView的adapter
 *
 * 作者：曹贵生 on 2016/12/7.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class LeftAdapter extends BaseAdapter {

    private Context context;
    String data[] = { "销量排行", "招牌明星", "奶茶果汁" };
    private int currentPosition;

    public LeftAdapter(Context context) {
        super();
        this.context = context;
    }

    /**
     * 这个方法是用来设置字体颜色的
     *
     * @param position
     */
    public void updateData(int position) {
        currentPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
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
            convertView = View.inflate(context, R.layout.item_order_left, null);
            vh = new ViewHold();
            convertView.setTag(vh);
            vh.tv_left = (TextView) convertView.findViewById(R.id.tv_left);
        } else {
            vh = (ViewHold) convertView.getTag();
        }
        // 在这里改变颜色
        if (position == currentPosition) {
            vh.tv_left.setTextColor(Color.RED);
        } else {
            vh.tv_left.setTextColor(Color.BLACK);
        }
        vh.tv_left.setText(data[position]);
        return convertView;
    }

    public class ViewHold {
        TextView tv_left;
    }
}