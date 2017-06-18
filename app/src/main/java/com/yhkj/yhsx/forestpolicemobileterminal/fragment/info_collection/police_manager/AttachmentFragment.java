package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.police_manager;

import android.widget.EditText;
import android.widget.GridView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;

import butterknife.BindView;

/**
 * Created by 李伟 on 2017/6/15.
 * 信息采集--治安管理--备注与附件
 */

public class AttachmentFragment extends BaseFragment {


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
