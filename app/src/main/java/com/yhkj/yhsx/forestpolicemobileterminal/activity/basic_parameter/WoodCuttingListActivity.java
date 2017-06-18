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
 * 信息采集--基础台账--木材采伐场（点）登记
 */
public class WoodCuttingListActivity extends ParentActivity implements View.OnClickListener {

    /**
     * 添加
     */
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    /**
     * 木材采伐场信息列表
     */
    @BindView(R.id.lvWoodCutting_information)
    ListView lvWoodCuttingInformation;

    private Context context;
    @Override
    protected int layoutResID() {
        return R.layout.activity_woodcutting_information;
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
                }
                Intent it = new Intent(context, WoodCuttingActivity.class);
                startActivity(it);
                break;
        }
    }
}
