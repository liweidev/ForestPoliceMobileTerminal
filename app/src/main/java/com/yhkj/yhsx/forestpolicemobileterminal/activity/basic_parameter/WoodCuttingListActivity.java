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
 * 木材采伐场（点）登记
 */
public class WoodCuttingListActivity extends ParentActivity implements View.OnClickListener {


    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.lvWoodCutting_information)
    ListView lvWoodCuttingInformation;
    @BindView(R.id.container)
    LinearLayout container;

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
