package com.yhkj.yhsx.forestpolicemobileterminal.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.TextView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ToastUtility;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 欢迎页Activity
 */
public class WelcomeActivity extends ParentActivity {

    @BindView(R.id.tvVersion)
    TextView tvVersion;


    private Intent it;


    @Override
    protected int layoutResID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (null != tvVersion) {
            tvVersion.setText("Version "+ActivityUtils.getInstance().getVersionCode());
        }

        mHandler.sendMessageDelayed(Message.obtain(), 2000);


        /*if (ActivityUtils.getInstance().getUserID() == 0 || ActivityUtils.getInstance().getUserID() == Integer.MIN_VALUE) {
        } else {
            //  DownloadConstantsDataUtils.getBaseInfoByUserId(mContext, null);
        }*/
    }

    @Override
    protected void initData() {

    }


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            enterIn();

        }

    };

    private void enterIn() {
         /*if (NetUtils.getInstance().getServerApi(ct).length() < 10) {
            it = new Intent(ct, ApiSettingActivity.class);
            startActivity(it);
            finish();
        } else if (ActivityUtils.isLogin(ct)) {
               it = new Intent(ct, MyService.class);
                Bundle bundle = new Bundle();
                bundle.putString("userId", ActivityUtils.getUseId(ct) + "");
                it.putExtras(bundle);
                // 启动服务
                startService(it);

                if (NetUtil.init(ct).isNetworkAvalible(ct)) {
                    MqttService.actionStart(ct);
                }*/
        it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
        /*} else if (!ActivityUtils.isLogin(ct)) {
                *//*it = new Intent(ct, LoginActivity.class);
                startActivity(it);
                finish();*//*
        }*/
        if (null != it) {
            startActivity(it);
            ActivityUtils.getInstance().starAnimForActivity(this);
            finish();
        }
    }

}
