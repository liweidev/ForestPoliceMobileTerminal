package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.EditText;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 李伟 on 2017/6/16.
 * 信息采集--基础台账--社会情况统计--重点单位
 */

public class SocialObjectFragment extends BaseFragment {
    /**
     * 重点单位合计
     */
    @BindView(R.id.etEmphasisUnitCount)
    EditText etEmphasisUnitCount;
    /**
     * 木材加工
     */
    @BindView(R.id.etWoodProcessingUnit)
    EditText etWoodProcessingUnit;
    /**
     * 木材收购
     */
    @BindView(R.id.etWoodPurchaseUnit)
    EditText etWoodPurchaseUnit;
    /**
     * 采石
     */
    @BindView(R.id.etQuarryingUnit)
    EditText etQuarryingUnit;
    /**
     * 煤矿
     */
    @BindView(R.id.etCollieryUnit)
    EditText etCollieryUnit;
    /**
     * 石板
     */
    @BindView(R.id.etFlaystoneUnit)
    EditText etFlaystoneUnit;
    /**
     * 野生动物经营
     */
    @BindView(R.id.etWildAnimalUnit)
    EditText etWildAnimalUnit;
    /**
     * 旅游区
     */
    @BindView(R.id.etTravelUnit)
    EditText etTravelUnit;
    Unbinder unbinder;

    @Override
    protected int layoutResID() {
        return R.layout.activity_socialstatistics_main_three;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


}
