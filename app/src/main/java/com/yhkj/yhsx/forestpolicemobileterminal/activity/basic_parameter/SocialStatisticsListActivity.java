package com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.ParentActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ToastUtility;

import butterknife.BindView;

/**
 * 社会情况查询
 */
public class SocialStatisticsListActivity extends ParentActivity implements View.OnClickListener {


    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.lvSocialStatistics_information)
    ListView lvSocialStatisticsInformation;
    @BindView(R.id.container)
    LinearLayout container;

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
