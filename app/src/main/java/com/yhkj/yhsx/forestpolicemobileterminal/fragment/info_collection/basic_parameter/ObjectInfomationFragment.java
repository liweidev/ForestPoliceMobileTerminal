package com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter;

import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.BaseFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.widget.DateInputEditText;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by qianhaohong on 2017/6/15.
 * 信息采集--基础台账--重点列管人（单位）情况--重点列管人员（单位）情况
 */

public class ObjectInfomationFragment extends BaseFragment {
    /**
     * 姓名或单位名称
     */
    @BindView(R.id.etManagedObjectUnitName)
    EditText etManagedObjectUnitName;
    /**
     * 性别
     */
    @BindView(R.id.spManagedObjectGender)
    Spinner spManagedObjectGender;
    /**
     * 户籍地
     */
    @BindView(R.id.etManagedObjectDomicilePlace)
    EditText etManagedObjectDomicilePlace;
    /**
     * 身份证或执照号码
     */
    @BindView(R.id.etManagedObjectCardID)
    EditText etManagedObjectCardID;
    /**
     * 现住址或单位
     */
    @BindView(R.id.etManagedObjectAddressOrUnit)
    EditText etManagedObjectAddressOrUnit;
    /**
     * 职业
     */
    @BindView(R.id.spManagedObjectCareer)
    Spinner spManagedObjectCareer;
    /**
     * 列管原因
     */
    @BindView(R.id.spManagedObjectManagedCause)
    Spinner spManagedObjectManagedCause;
    /**
     * 何时何因被何部门给何处罚
     */
    @BindView(R.id.etManagedObjectSpecificCauses)
    EditText etManagedObjectSpecificCauses;
    /**
     * 列管时间
     */
    @BindView(R.id.dietManagedObjectManagedTime)
    DateInputEditText dietManagedObjectManagedTime;
    /**
     * 负责民警
     */
    @BindView(R.id.etManagedObjectResponsibilityPolice)
    Spinner etManagedObjectResponsibilityPolice;
    @BindView(R.id.scrollView1)
    ScrollView scrollView1;
    Unbinder unbinder;

    @Override
    protected int layoutResID() {
        return R.layout.fragment_managed_object_add;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


}
