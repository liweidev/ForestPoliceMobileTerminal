package com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.ParentActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.adapter.MainFragmentPagerAdapter;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter.WoodAttachmentFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter.WoodInfomationFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ToastUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 林地内开采沙、土石矿情况登记
 */
public class WoodlandMiningActivity extends ParentActivity implements View.OnClickListener {


    @BindView(R.id.ivAlarm)
    ImageView ivAlarm;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.mytitle)
    RelativeLayout mytitle;
    @BindView(R.id.btn_basic)
    Button btnBasic;
    @BindView(R.id.btn_attachment)
    Button btnAttachment;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.woodland_pager)
    LinearLayout woodlandPager;

    private List<Fragment> pageViews;
    @Override
    protected int layoutResID() {
        return R.layout.activity_woodlandmining;
    }

    @Override
    protected void initView() {
        btnBasic.setOnClickListener(this);
        btnAttachment.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        addFragmentPager();
        setSelector(0);
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

    private void addFragmentPager() {
        pageViews = new ArrayList<>();
        for(int i=0;i<2;i++){
            Fragment fragment=null;
            switch (i){
                case 0:
                    fragment=new WoodInfomationFragment();
                    break;

                case 1:
                    fragment=new WoodAttachmentFragment();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_basic:
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

}
