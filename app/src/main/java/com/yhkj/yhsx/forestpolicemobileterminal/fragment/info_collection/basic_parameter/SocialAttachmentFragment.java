package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.GridView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by qianhaohong on 2017/6/16.
 * 信息采集--基础台账--社会情况统计--附件
 */

public class SocialAttachmentFragment extends BaseFragment {
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
