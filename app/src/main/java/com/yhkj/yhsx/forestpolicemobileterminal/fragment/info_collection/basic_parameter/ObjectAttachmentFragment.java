package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.GridView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 李伟 on 2017/6/15.
 * 信息采集--基础台账--重点列管人（单位）情况--附件
 */

public class ObjectAttachmentFragment extends BaseFragment {
    /**
     * 附件
     */
    @BindView(R.id.gwWoodAttachment)
    GridView gwWoodAttachment;
    Unbinder unbinder;

    @Override
    protected int layoutResID() {
        return R.layout.woods;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


}
