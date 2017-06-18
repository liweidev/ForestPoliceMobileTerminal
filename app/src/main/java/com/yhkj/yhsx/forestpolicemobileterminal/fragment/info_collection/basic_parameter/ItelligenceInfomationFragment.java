package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.EditText;
import android.widget.Spinner;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.widget.DateAndTimeInputEditText;
import com.yhkj.yhsx.forestpolicemobileterminal.widget.DateInputEditText;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 李伟 on 2017/6/15.
 * 信息采集--基础台账--情报信息登记--基本信息
 */

public class ItelligenceInfomationFragment extends BaseFragment {


    /**
     * 收集时间
     */
    @BindView(R.id.etItelligenceCollectTime)
    DateAndTimeInputEditText etItelligenceCollectTime;
    /**
     * 收集民警
     */
    @BindView(R.id.spItelligencePolice)
    Spinner spItelligencePolice;
    /**
     * 信息来源
     */
    @BindView(R.id.etItelligenceSource)
    EditText etItelligenceSource;
    /**
     * 信息类别
     */
    @BindView(R.id.spItelligenceType)
    Spinner spItelligenceType;
    /**
     * 信息摘要
     */
    @BindView(R.id.etItelligenceAbstract)
    EditText etItelligenceAbstract;
    /**
     * 上报单位
     */
    @BindView(R.id.etItelligenceReportUnit)
    EditText etItelligenceReportUnit;
    /**
     * 上报时间
     */
    @BindView(R.id.etItelligenceReportTime)
    DateInputEditText etItelligenceReportTime;
    Unbinder unbinder;

    @Override
    protected int layoutResID() {
        return R.layout.activity_itelligence_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }



}
