package com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.ParentActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ToastUtility;

import butterknife.BindView;

/**
 * 李伟 2017.6.18
 * 信息采集--基础台账--野生动物情况登记
 */
public class AnimalInformationDetailActivity extends ParentActivity
        implements OnClickListener {
    /**
     * 标题文本
     */
    @BindView(R.id.tvTitleText)
    TextView tvTitleText;
    /**
     * 重点保护动物添加
     */
    @BindView(R.id.llAdd)
    LinearLayout llAdd;
    /**
     * 动物名称选择
     */
    @BindView(R.id.spSearchContent)
    Spinner spSearchContent;
    /**
     * 搜索
     */
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.spSearch)
    Spinner spSearch;
    /**
     * 搜索附近
     */
    @BindView(R.id.btnSearchNearby)
    Button btnSearchNearby;
    /**
     * 动物结果列表
     */
    @BindView(R.id.lvSearchResults)
    ListView lvSearchResults;
    private Context ct;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llAdd:
                if (ActivityUtils.judgeSimState(ct) == 1) {
                    return;
                }
                ToastUtility.showToast("添加");
                Intent it = new Intent(ct, AnimalsAddActivity.class);
                startActivity(it);
                break;
            case R.id.btnSearch:
                ToastUtility.showToast("搜索");
                break;

            case R.id.btnSearchNearby:
                ToastUtility.showToast("搜索附近");
                break;

        }
    }

    @Override
    protected int layoutResID() {
        return R.layout.activity_animal_information_detail;
    }

    @Override
    protected void initView() {
        llAdd.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnSearchNearby.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        ct=this;
    }

}
