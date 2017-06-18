package com.yhkj.yhsx.forestpolicemobileterminal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yhkj.yhsx.forestpolicemobileterminal.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 智慧办法Fragment
 */
public class IntelligenceOfficeFragment extends BaseFragment implements View.OnClickListener{


    //新闻公告
    @BindView(R.id.newsBulletin)
    LinearLayout newsBulletin;
    //工作日志
    @BindView(R.id.jobLog1)
    LinearLayout jobLog1;
    //警备便签
    @BindView(R.id.policeNote)
    LinearLayout policeNote;
    //值班表
    @BindView(R.id.watchWatch)
    LinearLayout watchWatch;
    //法律法规查询
    @BindView(R.id.lawQury)
    LinearLayout lawQury;

    @Override
    protected int layoutResID() {
        return R.layout.zhihuibangfragment;
    }

    @Override
    protected void initView() {
        newsBulletin.setOnClickListener(this);
        jobLog1.setOnClickListener(this);
        policeNote.setOnClickListener(this);
        watchWatch.setOnClickListener(this);
        lawQury.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newsBulletin:
                break;
            case R.id.jobLog1:
                break;
            case R.id.policeNote:
                break;
            case R.id.watchWatch:
                break;
            case R.id.lawQury:
                break;
        }
    }
}
