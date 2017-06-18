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
 * 林地内开采沙、土、石、矿情况查新
 */
public class WoodlandMiningListActivity extends ParentActivity implements View.OnClickListener {

    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.lvWoodland_information)
    ListView lvWoodlandInformation;
    @BindView(R.id.container)
    LinearLayout container;

    private Context context;
    @Override
    protected int layoutResID() {
        return R.layout.activity_woodlandmining_information;
    }

    @Override
    protected void initView() {
        btnAdd.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        context=this;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                ToastUtility.showToast("添加");
                if (ActivityUtils.judgeSimState(context) == 1) {
                }
                Intent it = new Intent(context, WoodlandMiningActivity.class);
                startActivity(it);
                break;
        }
    }
}
