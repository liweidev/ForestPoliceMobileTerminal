package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.EditText;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.widget.DateInputEditText;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 李伟 on 2017/6/15.
 * 信息采集--基础台账--木材采伐场（点）登记--基本信息
 */

public class WoodCutInfomationFragment extends BaseFragment {


    /**
     * 采伐单位或个人
     */
    @BindView(R.id.etCuttingUnit)
    EditText etCuttingUnit;
    /**
     * 采伐地点
     */
    @BindView(R.id.etCuttingLocation)
    EditText etCuttingLocation;
    /**
     * 采伐证号
     */
    @BindView(R.id.etCuttingCardID)
    EditText etCuttingCardID;
    /**
     * 批准采伐数量
     */
    @BindView(R.id.etAllowCount)
    EditText etAllowCount;
    /**
     * 采伐树种
     */
    @BindView(R.id.etCuttingSpecies)
    EditText etCuttingSpecies;
    /**
     * 采伐方式
     */
    @BindView(R.id.etCuttingMode)
    EditText etCuttingMode;
    /**
     * 进入林区采伐人员数
     */
    @BindView(R.id.etCuttingCount)
    EditText etCuttingCount;
    /**
     * 开始采伐时间
     */
    @BindView(R.id.etCuttingBeginTime)
    DateInputEditText etCuttingBeginTime;
    /**
     * 采伐结束时间
     */
    @BindView(R.id.etCuttingEndTime)
    DateInputEditText etCuttingEndTime;
    Unbinder unbinder;

    @Override
    protected int layoutResID() {
        return R.layout.activity_woodcutting_mian;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }



}
