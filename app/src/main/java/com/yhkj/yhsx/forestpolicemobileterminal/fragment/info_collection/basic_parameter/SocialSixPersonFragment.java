package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.EditText;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 李伟 on 2017/6/16.
 * 信息采集--基础台账--社会情况统计--六种人
 */

public class SocialSixPersonFragment extends BaseFragment {
    /**
     * 承包人
     */
    @BindView(R.id.etContractor)
    EditText etContractor;
    /**
     * 学校校长
     */
    @BindView(R.id.etPrincipal)
    EditText etPrincipal;
    /**
     * 放牧人员
     */
    @BindView(R.id.etGrazingPersonnel)
    EditText etGrazingPersonnel;
    /**
     * 雇主
     */
    @BindView(R.id.etEmployer)
    EditText etEmployer;
    /**
     * 坟主
     */
    @BindView(R.id.etGraveMaster)
    EditText etGraveMaster;
    /**
     * 呆傻监护人
     */
    @BindView(R.id.etMoronismManager)
    EditText etMoronismManager;
    Unbinder unbinder;

    @Override
    protected int layoutResID() {
        return R.layout.activity_socialstatistics_main_four;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


}
