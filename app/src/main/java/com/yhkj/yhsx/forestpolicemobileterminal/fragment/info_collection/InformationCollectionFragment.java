package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.Constant;

import butterknife.BindView;

/**
 * 信息采集Fragment
 */
public class InformationCollectionFragment extends BaseFragment implements View.OnClickListener{

    //基础台账
    @BindView(R.id.basisAccou)
    LinearLayout basisAccou;
    //治安管理
    @BindView(R.id.securityManage)
    LinearLayout securityManage;

    private Context ct;
    @Override
    protected int layoutResID() {
        return R.layout.gatherinformationfragment;
    }

    @Override
    protected void initView() {
        basisAccou.setOnClickListener(this);
        securityManage.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        ct=getActivity();
    }


    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){
            case R.id.basisAccou:
                ActivityUtils.getInstance().showDialogMenu(getActivity(),Constant.BASIC_ACCOUNT);
                break;
            case R.id.securityManage://治安管理
                ActivityUtils.getInstance().showDialogMenu(getActivity(),Constant.SAFET_PRECAUTIONS);
                break;

        }
    }
}
