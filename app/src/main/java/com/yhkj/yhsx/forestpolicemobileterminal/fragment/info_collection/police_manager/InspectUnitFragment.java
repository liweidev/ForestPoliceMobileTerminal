package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.police_manager;

import android.widget.EditText;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.widget.DateInputEditText;

import butterknife.BindView;

/**
 * Created by 李伟 on 2017/6/15.
 * 信息采集--治安管理--被检单位信息
 */

public class InspectUnitFragment extends BaseFragment {
    /**
     * 单位名称
     */
    @BindView(R.id.etSmir_UnitName)
    EditText etSmirUnitName;
    /**
     * 经营地点
     */
    @BindView(R.id.etSmir_UnitAdd)
    EditText etSmirUnitAdd;
    /**
     * 法人代表
     */
    @BindView(R.id.etSmir_LegalRepresentative)
    EditText etSmirLegalRepresentative;
    /**
     * 负责人
     */
    @BindView(R.id.etSmir_Head)
    EditText etSmirHead;
    /**
     * 经营项目
     */
    @BindView(R.id.etSmir_BusinessProject)
    EditText etSmirBusinessProject;
    /**
     * 经营方式
     */
    @BindView(R.id.etSmir_BusinessPractice)
    EditText etSmirBusinessPractice;
    /**
     * 检查时间
     */
    @BindView(R.id.etSmir_CheckTheDate)
    DateInputEditText etSmirCheckTheDate;
    /**
     * 检查人员
     */
    @BindView(R.id.etSmir_Inspectors)
    EditText etSmirInspectors;

    @Override
    protected int layoutResID() {
        return R.layout.smir_form1;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }


}
