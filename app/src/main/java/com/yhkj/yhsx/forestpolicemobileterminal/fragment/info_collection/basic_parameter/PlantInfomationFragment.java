package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.EditText;
import android.widget.Spinner;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 李伟 on 2017/6/15.
 * 信息采集--基础台账--重点保护植物登记情况--基本信息
 */

public class PlantInfomationFragment extends BaseFragment {
    /**
     * 选择责任单位
     */
    @BindView(R.id.spTaoppr_CompetentAuthorities)
    Spinner spTaopprCompetentAuthorities;
    /**
     * 具体分布地点或林班号小班号
     */
    @BindView(R.id.etTaoppr_TreeComOrSLNumbers)
    EditText etTaopprTreeComOrSLNumbers;
    /**
     * 植物名称
     */
    @BindView(R.id.spTaoppr_plantNames)
    Spinner spTaopprPlantNames;

    @BindView(R.id.spTaoppr_treeAge)
    Spinner spTaopprTreeAge;
    /**
     * 树龄
     */
    @BindView(R.id.et_treeAge)
    EditText etTreeAge;
    /**
     * 选择保护级别
     */
    @BindView(R.id.spTaoppr_ProtectionLevel)
    Spinner spTaopprProtectionLevel;
    /**
     * 选择现状
     */
    @BindView(R.id.spTaoppr_Status)
    Spinner spTaopprStatus;
    /**
     * 档案编号
     */
    @BindView(R.id.etTaoppr_TreeProtectFileNum)
    EditText etTaopprTreeProtectFileNum;
    /**
     * 护管人员
     */
    @BindView(R.id.etTaoppr_TreeMapoPersonnel)
    EditText etTaopprTreeMapoPersonnel;
    /**
     * 联系方式
     */
    @BindView(R.id.etTaoppr_TreePhone)
    EditText etTaopprTreePhone;
    Unbinder unbinder;

    @Override
    protected int layoutResID() {
        return R.layout.taoppr_form;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


}
