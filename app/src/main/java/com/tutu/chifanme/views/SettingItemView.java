package com.tutu.chifanme.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tutu.chifanme.R;


/**
 * 自定义设置中心的item
 *
 * 作者：曹贵生 on 2016/11/10.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class SettingItemView extends RelativeLayout {


    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private TextView tvTitle;
    private TextView tvDesc;
    private CheckBox cbStatus;
    private String mDecOn;
    private String mDescOff;
    private String mTitle;


    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        LayoutInflater.from(context).inflate(R.layout.view_setting_item, this, true);

       /* int attributeCount = attrs.getAttributeCount();
        for (int i=0; i<attributeCount; i++) {
            String attributeValue = attrs.getAttributeValue(i);
            String attributeName = attrs.getAttributeName(i);
            System.out.println(attributeName+"="+attributeValue);
        }*/

        // 通过属性名称，动态获取属性值
        mTitle = attrs.getAttributeValue(NAMESPACE, "title");
        mDecOn = attrs.getAttributeValue(NAMESPACE, "desc_on");
        mDescOff = attrs.getAttributeValue(NAMESPACE, "desc_off");

        initView();

    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    /**
     * 初始化控件
     */
    private void initView() {

        // 将定义好的布局文件设置给当前SettingItemView
        View.inflate(getContext(), R.layout.item_view_setting, this);
        // 此时的this是RelativeLayout，所以可以直接findViewById();
        tvTitle =  (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        cbStatus = (CheckBox) findViewById(R.id.cb_status);

        setTitle(mTitle);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setDesc(String desc) {
        tvDesc.setText(desc);
    }

    public void setCheck(boolean check) {
        cbStatus.setChecked(check);
        if (check) {
            setDesc(mDecOn);
        } else {
            setDesc(mDescOff);
        }
    }

    /**
     * 返回勾选状态
     *
     * @return
     */
    public boolean isCheck() {
        return cbStatus.isChecked();
    }

}
