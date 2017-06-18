package com.yhkj.yhsx.forestpolicemobileterminal.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.adapter.PublicSecurityFragmentPagerAdapter;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.police_manager.AttachmentFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.police_manager.InspectUnitFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.police_manager.ProblemFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ToastUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 派出所治安管理检查记录.表--添加
 *
 * @author Administrator
 *
 */
public class PublicSecurityManagementActivity extends ParentActivity implements View.OnClickListener {

    /***
     * 保存
     */
    @BindView(R.id.tv_save)
    TextView tv_save;

    /**
     * 被检单位
     */
    @BindView(R.id.btn_unit)
    Button btn_unit;

    /**
     * 问题建议
     */
    @BindView(R.id.btn_proble)
    Button btn_proble;

    /**
     * 备注附件
     */
    @BindView(R.id.btn_attachment)
    Button btn_attachment;


    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.view1)
    View view1;

    @BindView(R.id.view2)
    View view2;

    @BindView(R.id.view3)
    View view3;





    private List<Fragment> pageViews;// 页面

    private static ArrayList<String> imagePathList=new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.public_security_management, menu);
        return true;
    }
    @Override
    protected int layoutResID() {
        return R.layout.activity_public_security_management;
    }

    @Override
    protected void initView() {
        tv_save.setOnClickListener(this);
        btn_unit.setOnClickListener(this);
        btn_proble.setOnClickListener(this);
        btn_attachment.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        addFragmentPager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_save:
                ToastUtility.showToast("保存");
                break;
            case R.id.btn_attachment://备注附件
                ToastUtility.showToast("备注附件");
                setSelector(2);
                break;
            case R.id.btn_proble://问题建议
                ToastUtility.showToast("问题建议");
                setSelector(1);
                break;
            case R.id.btn_unit://被检单位
                ToastUtility.showToast("被检单位");
                setSelector(0);
                break;
        }
    }


    /**
     * 添加主页菜单Fragment
     */
    private void addFragmentPager(){
        pageViews = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Fragment fragment = null;
            switch (i) {
                case 0:
                    fragment = new InspectUnitFragment();
                    break;
                case 1:
                    fragment = new ProblemFragment();
                    break;
                case 2:
                    fragment = new AttachmentFragment();
                    break;
                default:
                    break;
            }
            pageViews.add(fragment);
        }
        Log.d("TAG",pageViews.size()+"");
        viewPager.setAdapter(new PublicSecurityFragmentPagerAdapter(getSupportFragmentManager(),pageViews));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelector(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /***
     * 选中效果
     */
    public void setSelector(int id) {
        if(id==2){
            tv_save.setEnabled(true);
            tv_save.setTextColor(Color.WHITE);
        }else{
            tv_save.setEnabled(false);
            tv_save.setTextColor(Color.parseColor("#cfc3c3"));
        }
        switch (id){
            case 0:
                view1.setBackgroundColor(Color.parseColor("#2d9ded"));
                view2.setBackgroundColor(Color.parseColor("#182330"));
                view3.setBackgroundColor(Color.parseColor("#182330"));
                break;
            case 1:
                view1.setBackgroundColor(Color.parseColor("#182330"));
                view2.setBackgroundColor(Color.parseColor("#2d9ded"));
                view3.setBackgroundColor(Color.parseColor("#182330"));
                break;
            case 2:
                view1.setBackgroundColor(Color.parseColor("#182330"));
                view2.setBackgroundColor(Color.parseColor("#182330"));
                view3.setBackgroundColor(Color.parseColor("#2d9ded"));
                break;

        }
        viewPager.setCurrentItem(id);

    }


    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        FileOutputStream fos = null;
        Bitmap bitmap = null;
        View view=LayoutInflater.from(this).inflate(R.layout.smir_form2,null,false);
        ImageView ivSignatureAndDate= (ImageView) view.findViewById(R.id.ivSmir_signature);
        if (arg1 == Activity.RESULT_OK) {
            switch (arg0) {
                case 100:
                    String filepath = arg2.getExtras().getString("result");
                    try {
                        bitmap = BitmapFactory.decodeFile(filepath);
                        fos = new FileOutputStream(new File(filepath));
                        bitmap.compress(Bitmap.CompressFormat.PNG, 10, fos);
                        bitmap = Bitmap.createBitmap(bitmap,
                                bitmap.getWidth() / 2 - 88,
                                bitmap.getHeight() / 2 - 88, 176, 176);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (null != fos) {
                                fos.flush();
                                fos.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    ivSignatureAndDate
                            .setBackgroundResource(R.drawable.image_add_bg);
                    ivSignatureAndDate.setTag(filepath);
                    ivSignatureAndDate.setImageBitmap(bitmap);
                    ivSignatureAndDate.setAdjustViewBounds(true);
                    ivSignatureAndDate.setScaleType(ImageView.ScaleType.FIT_XY);
                    ivSignatureAndDate.setPadding(10, 10, 10, 10);
                    break;
                case 4:
                    if (!arg2.getBooleanExtra("isDel", true)) {
                        ivSignatureAndDate
                                .setImageResource(android.R.drawable.ic_input_add);
                    }
                    break;
                /*case 10:
                    String path = arg2.getStringExtra("filepath");
                    imagePathList.remove(path);
                    imageList.setAdapter(accessoryAdapter);
                    break;*/

                default:
				/*
				 * imagePathList.add(ActivityUtils.saveAccessoryToFile(activity,
				 * arg0, arg2));
				 */
                    imagePathList = ActivityUtils.saveAccessoryToFile(
                            imagePathList, this, arg0, arg2);
                    break;
            }
        }

    }


}
