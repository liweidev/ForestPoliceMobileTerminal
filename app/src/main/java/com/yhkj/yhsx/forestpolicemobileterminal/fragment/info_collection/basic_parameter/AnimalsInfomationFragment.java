package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.EditText;
import android.widget.Spinner;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 李伟 on 2017/6/15.
 * 信息采集--基础台账--野生动物情况登记--基本信息
 */

public class AnimalsInfomationFragment extends BaseFragment {
    /**
     * 责任单位
     */
    @BindView(R.id.spAowr_AccountabilityUnit)
    EditText spAowrAccountabilityUnit;
    /**
     * 分布具体范围及地点名称
     */
    @BindView(R.id.etAowr_DrAndPn)
    EditText etAowrDrAndPn;
    /**
     * 选择动物名称
     */
    @BindView(R.id.spAowr_AnimalName)
    Spinner spAowrAnimalName;
    /**
     * 选择保护级别
     */
    @BindView(R.id.spAowr_ProtectionLevel)
    Spinner spAowrProtectionLevel;
    /**
     * 选择保护方式
     */
    @BindView(R.id.spAowr_ProtectTheWay)
    Spinner spAowrProtectTheWay;
    /**
     * 保护档案编号
     */
    @BindView(R.id.etAowr_ProtectFileNum)
    EditText etAowrProtectFileNum;
    /**
     * 管护人员
     */
    @BindView(R.id.etAowr_MapoPersonnel)
    EditText etAowrMapoPersonnel;
    /**
     * 联系方式
     */
    @BindView(R.id.etAowr_Phone)
    EditText etAowrPhone;

    Unbinder unbinder;

    @Override
    protected int layoutResID() {
        return R.layout.aowr_form;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


}
