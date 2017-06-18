package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.EditText;
import android.widget.Spinner;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;

import butterknife.BindView;

/**
 * Created by 李伟 on 2017/6/15.
 * 信息采集--基础台账--涉林重点行业情况登记--涉林重点行业情况
 */

public class ForestryInfomationFragment extends BaseFragment {


    /**
     * 单位名称
     */
    @BindView(R.id.etForestryKeyIndustriesUnitName)
    EditText etForestryKeyIndustriesUnitName;
    /**
     * 地址
     */
    @BindView(R.id.etForestryKeyIndustriesAddress)
    EditText etForestryKeyIndustriesAddress;
    /**
     * 证照
     */
    @BindView(R.id.etForestryKeyIndustriesLicense)
    Spinner etForestryKeyIndustriesLicense;
    /**
     * 经营行业、种类、名称
     */
    @BindView(R.id.etForestryKeyIndustriesOperateType)
    Spinner etForestryKeyIndustriesOperateType;
    /**
     * 负责人及联系方式
     */
    @BindView(R.id.etForestryKeyIndustriesPrincipal)
    EditText etForestryKeyIndustriesPrincipal;
    /**
     * 管理人及联系方式
     */
    @BindView(R.id.etForestryKeyIndustriesContactInfromation)
    EditText etForestryKeyIndustriesContactInfromation;
    /**
     * 品种来源
     */
    @BindView(R.id.etForestryKeyIndustriesVarietieesSourcess)
    Spinner etForestryKeyIndustriesVarietieesSourcess;
    /**
     * 保护级别
     */
    @BindView(R.id.spForestryKeyIndustriesProtectionLevel)
    Spinner spForestryKeyIndustriesProtectionLevel;
    /**
     * 行业人员数量
     */
    @BindView(R.id.etForestryKeyIndustriesPractitionerNumber)
    EditText etForestryKeyIndustriesPractitionerNumber;

    @Override
    protected int layoutResID() {
        return R.layout.fragment_forestry_key_industries;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


}
