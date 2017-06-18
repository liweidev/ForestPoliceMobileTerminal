package com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.ParentActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.adapter.MainFragmentPagerAdapter;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter.ObjectAttachmentFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter.ObjectInfomationFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ToastUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 李伟 2017.6.18
 * 信息采集--基础台账--重点列管人（单位）情况登记--新增重点列管人员
 */
public class ManagedObjectAddActivity extends ParentActivity implements View.OnClickListener {

    /**
     * 保存
     */
    @BindView(R.id.tv_save)
    TextView tvSave;
    /**
     * 重点列管人员单位情况
     */
    @BindView(R.id.btn_object)
    Button btnObject;
    /**
     * 附件
     */
    @BindView(R.id.btn_attachment)
    Button btnAttachment;

    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<Fragment> pageViews;
    @Override
    protected int layoutResID() {
        return R.layout.activity_managed_object_add;
    }

    @Override
    protected void initView() {
        btnObject.setOnClickListener(this);
        btnAttachment.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        addFragmentPager();
        setSelector(0);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_object:
                setSelector(0);
                break;

            case R.id.btn_attachment:
                setSelector(1);
                break;

            case R.id.tv_save:
                ToastUtility.showToast("保存");
                break;
        }
    }

    private void addFragmentPager() {
        pageViews = new ArrayList<>();
        for(int i=0;i<2;i++){
            Fragment fragment=null;
            switch (i){
                case 0://重点列管人Fragment
                    fragment=new ObjectInfomationFragment();
                    break;

                case 1://附件Fragment
                    fragment=new ObjectAttachmentFragment();
                    break;
            }
            pageViews.add(fragment);
        }


        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(),pageViews));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelector(position);
                //Log.d("TAG","position="+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void setSelector(int id) {
        if(id==1){
            tvSave.setEnabled(true);
            tvSave.setTextColor(Color.WHITE);
        }else{
            tvSave.setEnabled(false);
            tvSave.setTextColor(Color.parseColor("#cfc3c3"));
        }
        switch (id){
            case 0:
                view1.setBackgroundColor(Color.parseColor("#2d9ded"));
                view2.setBackgroundColor(Color.parseColor("#182330"));
                break;
            case 1:
                view1.setBackgroundColor(Color.parseColor("#182330"));
                view2.setBackgroundColor(Color.parseColor("#2d9ded"));
                break;

        }
        viewPager.setCurrentItem(id);
    }
}
