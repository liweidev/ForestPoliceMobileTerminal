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
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter.SocialAttachmentFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter.SocialCountFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter.SocialObjectFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter.SocialPersonFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.basic_parameter.SocialSixPersonFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ToastUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 社会情况统计
 */
public class SocialStatisticsActivity extends ParentActivity implements View.OnClickListener {

    @BindView(R.id.ivAlarm)
    ImageView ivAlarm;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.mytitle)
    RelativeLayout mytitle;
    /**
     * 户数
     */
    @BindView(R.id.btn_count)
    Button btnCount;
    /**
     * 人口
     */
    @BindView(R.id.btn_person)
    Button btnPerson;
    /**
     * 重点单位
     */
    @BindView(R.id.btn_object)
    Button btnObject;
    /**
     * 六种人
     */
    @BindView(R.id.btn_six_person)
    Button btnSixPerson;
    /**
     * 附件
     */
    @BindView(R.id.btn_attachment)
    Button btnAttachment;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.view5)
    View view5;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.social_pager)
    LinearLayout socialPager;
    private List<Fragment> pageViews;
    @Override
    protected int layoutResID() {
        return R.layout.activity_socialstatistics;
    }

    @Override
    protected void initView() {
        btnCount.setOnClickListener(this);
        btnPerson.setOnClickListener(this);
        btnObject.setOnClickListener(this);
        btnSixPerson.setOnClickListener(this);
        btnAttachment.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        addFragmentPager();
        setSelector(0);
    }
    private void setSelector(int id) {
        if(id==4){
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
                view3.setBackgroundColor(Color.parseColor("#182330"));
                view4.setBackgroundColor(Color.parseColor("#182330"));
                view5.setBackgroundColor(Color.parseColor("#182330"));
                break;
            case 1:
                view1.setBackgroundColor(Color.parseColor("#182330"));
                view2.setBackgroundColor(Color.parseColor("#2d9ded"));
                view3.setBackgroundColor(Color.parseColor("#182330"));
                view4.setBackgroundColor(Color.parseColor("#182330"));
                view5.setBackgroundColor(Color.parseColor("#182330"));
                break;
            case 2:
                view1.setBackgroundColor(Color.parseColor("#182330"));
                view2.setBackgroundColor(Color.parseColor("#182330"));
                view3.setBackgroundColor(Color.parseColor("#2d9ded"));
                view4.setBackgroundColor(Color.parseColor("#182330"));
                view5.setBackgroundColor(Color.parseColor("#182330"));
                break;
            case 3:
                view1.setBackgroundColor(Color.parseColor("#182330"));
                view2.setBackgroundColor(Color.parseColor("#182330"));
                view3.setBackgroundColor(Color.parseColor("#182330"));
                view4.setBackgroundColor(Color.parseColor("#2d9ded"));
                view5.setBackgroundColor(Color.parseColor("#182330"));
                break;
            case 4:
                view1.setBackgroundColor(Color.parseColor("#182330"));
                view2.setBackgroundColor(Color.parseColor("#182330"));
                view3.setBackgroundColor(Color.parseColor("#182330"));
                view4.setBackgroundColor(Color.parseColor("#182330"));
                view5.setBackgroundColor(Color.parseColor("#2d9ded"));
                break;
        }
        viewPager.setCurrentItem(id);
    }

    private void addFragmentPager() {
        pageViews = new ArrayList<>();
        for(int i=0;i<5;i++){
            Fragment fragment=null;
            switch (i){
                case 0:
                    fragment=new SocialCountFragment();
                    break;
                case 1:
                    fragment=new SocialPersonFragment();
                    break;
                case 2:
                    fragment=new SocialObjectFragment();
                    break;
                case 3:
                    fragment=new SocialSixPersonFragment();
                    break;
                case 4:
                    fragment=new SocialAttachmentFragment();
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
            case R.id.btn_count:
                setSelector(0);
                break;
            case R.id.btn_person:
                setSelector(1);
                break;
            case R.id.btn_object:
                setSelector(2);
                break;
            case R.id.btn_six_person:
                setSelector(3);
                break;
            case R.id.btn_attachment:
                setSelector(4);
                break;
            case R.id.tv_save:
                ToastUtility.showToast("保存");
                break;

        }

    }
}
