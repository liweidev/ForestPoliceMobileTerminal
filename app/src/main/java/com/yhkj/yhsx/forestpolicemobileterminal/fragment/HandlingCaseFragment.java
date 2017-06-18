package com.yhkj.yhsx.forestpolicemobileterminal.fragment;

import android.view.View;
import android.widget.LinearLayout;

import com.yhkj.yhsx.forestpolicemobileterminal.R;

import butterknife.BindView;

/**
 * 执法办案Fragment
 */
public class HandlingCaseFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.handCases)
    LinearLayout handCases;


    @Override
    protected int layoutResID() {
        return R.layout.zhifabafragment;
    }

    @Override
    protected void initView() {
        handCases.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.handCases:
                break;
        }
    }
}
