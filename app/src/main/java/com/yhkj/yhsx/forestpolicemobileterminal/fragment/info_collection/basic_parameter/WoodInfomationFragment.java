package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.EditText;
import android.widget.Spinner;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;

import butterknife.BindView;

/**
 * Created by 李伟 on 2017/6/15.
 * 信息采集--基础台账--林地内开采土、石、矿登记--基本信息
 */

public class WoodInfomationFragment extends BaseFragment {


    /**
     * 具体位置
     */
    @BindView(R.id.etSpecificLocation)
    EditText etSpecificLocation;
    /**
     * 开采类型
     */
    @BindView(R.id.spMiningType)
    Spinner spMiningType;
    /**
     * 开采面积（亩）
     */
    @BindView(R.id.etMiningArea)
    EditText etMiningArea;
    /**
     * 破坏林木情况
     */
    @BindView(R.id.etFallimentiDello)
    EditText etFallimentiDello;
    /**
     * 批准部门
     */
    @BindView(R.id.etAllowUnit)
    EditText etAllowUnit;

    @Override
    protected int layoutResID() {
        return R.layout.activity_woodlandmining_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


}
