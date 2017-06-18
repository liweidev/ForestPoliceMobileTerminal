package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.EditText;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 李伟 on 2017/6/16.
 * 信息采集--基础台账--社会情况统计--人口
 */

public class SocialPersonFragment extends BaseFragment {
    /**
     * 人口合计
     */
    @BindView(R.id.etPopulationCount)
    EditText etPopulationCount;
    /**
     * 农业人口
     */
    @BindView(R.id.etArriculturePopulation)
    EditText etArriculturePopulation;
    /**
     * 非农业人口
     */
    @BindView(R.id.etNonArriculturealPopulation)
    EditText etNonArriculturealPopulation;
    /**
     * 流动人口
     */
    @BindView(R.id.etTemporaryPopulation)
    EditText etTemporaryPopulation;
    Unbinder unbinder;

    @Override
    protected int layoutResID() {
        return R.layout.activity_socialstatistics_main_two;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


}
