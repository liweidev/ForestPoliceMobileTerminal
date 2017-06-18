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
 * 信息采集--基础台账--涉林重点行业情况登记
 */
public class ForestryKeyIndustriesQueryActivity extends ParentActivity implements View.OnClickListener {


    /**
     * 添加
     */
    @BindView(R.id.btn_add)
    ImageView btnAdd;

    /**
     * 涉林重点行业列表
     */
    @BindView(R.id.lvForestryKeyIndustriesList)
    ListView lvForestryKeyIndustriesList;

    private Context ct;

    @Override
    protected int layoutResID() {
        return R.layout.activity_forestry_key_industries_query;
    }

    @Override
    protected void initView() {
        btnAdd.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        ct=this;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                ToastUtility.showToast("添加");
                if (ActivityUtils.judgeSimState(ct) == 1) {
                    return ;
                }
                Intent it = new Intent(ct, ForestryKeyIndustriesAddActivity.class);
                startActivity(it);

                break;


        }
    }
}
