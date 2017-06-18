package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.EditText;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 李伟 on 2017/6/16.
 * 信息采集--基础台账--社会情况统计--户数
 */

public class SocialCountFragment extends BaseFragment {
    /**
     * 单位名称
     */
    @BindView(R.id.etUnit)
    EditText etUnit;
    /**
     * 总面积（平方千米）
     */
    @BindView(R.id.etTotalArea)
    EditText etTotalArea;
    /**
     * 户数合计
     */
    @BindView(R.id.etFamilyCount)
    EditText etFamilyCount;
    /**
     * 农业户
     */
    @BindView(R.id.etAgricultureFamily)
    EditText etAgricultureFamily;
    /**
     * 非农户
     */
    @BindView(R.id.etNonAgriculturalFamily)
    EditText etNonAgriculturalFamily;
    /**
     * 重点保护户
     */
    @BindView(R.id.etProtectFamily)
    EditText etProtectFamily;
    /**
     * 外来临时户
     */
    @BindView(R.id.etTemporaryFamily)
    EditText etTemporaryFamily;
    Unbinder unbinder;

    @Override
    protected int layoutResID() {
        return R.layout.activity_socialstatistics_main_one;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


}
