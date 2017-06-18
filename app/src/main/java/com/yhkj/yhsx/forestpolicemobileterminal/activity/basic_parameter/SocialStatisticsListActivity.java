package com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.ParentActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ToastUtility;

import butterknife.BindView;

/**
 * 李伟 2017.6.18
 * 信息采集--基础台账--社会情况统计
 */
public class SocialStatisticsListActivity extends ParentActivity implements View.OnClickListener {

    /**
     * 添加
     */
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    /**
     * 社会情况信息列表
     */
    @BindView(R.id.lvSocialStatistics_information)
    ListView lvSocialStatisticsInformation;

    private Context context;
    @Override
    protected int layoutResID() {
        return R.layout.activity_socialstatistics_information;
    }

    @Override
    protected void initView() {
        context=this;
        btnAdd.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                ToastUtility.showToast("添加");
                if (ActivityUtils.judgeSimState(context) == 1) {
                    return ;
                }
                Intent it = new Intent(context, SocialStatisticsActivity.class);
                startActivity(it);
                break;
        }
    }
}
