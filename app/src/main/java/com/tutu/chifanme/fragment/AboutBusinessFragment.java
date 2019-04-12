package com.tutu.chifanme.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutu.chifanme.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于商家的fragment
 *
 * 作者：曹贵生 on 2016/12/3.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class AboutBusinessFragment extends Fragment {

    @BindView(R.id.business_phone)
    TextView tvPhone;

    /**
     * 商家电话
     */
    @OnClick(R.id.rl_business_phone)
    public void callBusiness() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ tvPhone.getText()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View aboutView = inflater.inflate(R.layout.fragment_about_business, container,false);
        ButterKnife.bind(this, aboutView);
        return aboutView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }
}
