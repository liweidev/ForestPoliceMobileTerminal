package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.EditText;
import android.widget.GridView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 李伟 on 2017/6/15.
 * 信息采集--基础台账--木材采伐场（点）登记--检查情况与附件
 */

public class WoodCutAttachmentFragment extends BaseFragment {


    /**
     * 检查情况
     */
    @BindView(R.id.etInspection)
    EditText etInspection;
    /**
     * 附件
     */
    @BindView(R.id.gwWoodCutAttachments)
    GridView gwWoodCutAttachments;
    Unbinder unbinder;

    @Override
    protected int layoutResID() {
        return R.layout.inspections;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }



}
