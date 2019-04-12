package com.tutu.chifanme.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tutu.chifanme.R;

import butterknife.ButterKnife;

/**
 * 待评价的 fragment
 *
 * 作者：王捷 on 2016/12/13.
 * 邮箱：
 * 说明：
 */

public class WaitEvaluateFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View ordersView = inflater.inflate(R.layout.fragment_wait_evaluate, container,false);
        ButterKnife.bind(this, ordersView);
        init();
        initTabLineWidth();
        return ordersView;
    }

    private void initTabLineWidth() {

    }

    private void init() {

    }
}
