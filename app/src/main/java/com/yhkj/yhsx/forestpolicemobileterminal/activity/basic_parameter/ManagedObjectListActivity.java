package com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.ParentActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ToastUtility;

import butterknife.BindView;

/**
 * 李伟 2017.6.18
 * 信息采集--基础台账--重点列管人（单位）情况登记
 */
public class ManagedObjectListActivity extends ParentActivity implements View.OnClickListener {



    /**
     * 添加重点列管人员
     */
    @BindView(R.id.llManagedObjectAdd)
    LinearLayout llManagedObjectAdd;
    /**
     * 姓名或单位名称
     */
    @BindView(R.id.etManagedObjectSearchName)
    EditText etManagedObjectSearchName;
    /**
     * 选择列管原因
     */
    @BindView(R.id.spManagedObjectCause)
    Spinner spManagedObjectCause;
    /**
     * 搜索
     */
    @BindView(R.id.btnManagedObjectSearch)
    Button btnManagedObjectSearch;

    @BindView(R.id.spManagedObjectSearch)
    Spinner spManagedObjectSearch;
    /**
     * 搜索附近
     */
    @BindView(R.id.btnManagedObjectSearchNearby)
    Button btnManagedObjectSearchNearby;
    /**
     * 搜索结果列表
     */
    @BindView(R.id.lvManagedObjectSearchResults)
    ListView lvManagedObjectSearchResults;

    private Context ct;
    @Override
    protected int layoutResID() {
        return R.layout.activity_managed_object;
    }

    @Override
    protected void initView() {
        llManagedObjectAdd.setOnClickListener(this);
        btnManagedObjectSearchNearby.setOnClickListener(this);
        btnManagedObjectSearch.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        ct=this;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llManagedObjectAdd:
                ToastUtility.showToast("添加");
                if (ActivityUtils.judgeSimState(ct) == 1) {
                    return;
                }
                Intent it = new Intent(ct, ManagedObjectAddActivity.class);
                startActivity(it);
                break;

            case R.id.btnManagedObjectSearchNearby:
                ToastUtility.showToast("搜索附近");
                break;

             case R.id.btnManagedObjectSearch:
                ToastUtility.showToast("搜索");
                break;


        }
    }
}
