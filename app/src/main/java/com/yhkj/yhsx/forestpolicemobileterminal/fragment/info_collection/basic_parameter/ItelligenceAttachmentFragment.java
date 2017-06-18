package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.EditText;
import android.widget.GridView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 李伟 on 2017/6/15.
 * 信息采集--基础台账--情报信息登记--意见反馈与附件
 */

public class ItelligenceAttachmentFragment extends BaseFragment {

    /**
     * 意见反馈
     */
    @BindView(R.id.etFeedBacks)
    EditText etFeedBacks;
    /**
     * 附件
     */
    @BindView(R.id.gwAttachments)
    GridView gwAttachments;
    Unbinder unbinder;

    @Override
    protected int layoutResID() {
        return R.layout.feedbacks;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }



}
