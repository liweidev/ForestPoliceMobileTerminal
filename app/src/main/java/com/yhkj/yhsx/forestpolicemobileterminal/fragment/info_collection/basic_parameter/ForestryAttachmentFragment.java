package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.EditText;
import android.widget.GridView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 李伟 on 2017/6/15.
 * 信息采集--基础台账--涉林重点行业情况登记--备注与附件
 */

public class ForestryAttachmentFragment extends BaseFragment {
    /**
     * 备注
     */
    @BindView(R.id.etNote)
    EditText etNote;
    /**
     * 附件
     */
    @BindView(R.id.gwAttachment)
    GridView gwAttachment;
    Unbinder unbinder;

    @Override
    protected int layoutResID() {
        return R.layout.remarks;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


}
