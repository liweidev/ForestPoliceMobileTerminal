package com.yhkj.yhsx.forestpolicemobileterminal.fragment;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.patrol_route.PatrolRouteInformationActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.services.OnPatrolRegisterService;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ToastUtility;

import java.util.List;

import butterknife.BindView;



/**
 * 巡防管理Fragment
 */
public class PatrolManageFragment extends BaseFragment implements View.OnClickListener {


    //开始巡逻
    @BindView(R.id.startPatro)
    LinearLayout startPatro;
    //路线管理
    @BindView(R.id.routeMnage)
    LinearLayout routeMnage;
    //轨迹查询
    @BindView(R.id.xunluo_manager)
    LinearLayout xunluoManager;

    //kaishi1
    @BindView(R.id.kaishixl)
    TextView kaishixl;

    //startimage
    @BindView(R.id.startimg)
    ImageView startImg;

    private Context ct;

    private List<ActivityManager.RunningServiceInfo> serviceList;
    private ActivityManager activityManager;
    private WifiManager wifiManager=null;
    @Override
    protected int layoutResID() {
        return R.layout.watchmanmanagerfragment;
    }

    @Override
    protected void initView() {
        startPatro.setOnClickListener(this);
        routeMnage.setOnClickListener(this);
        xunluoManager.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        ct = getActivity();
        activityManager = (ActivityManager) getActivity().getSystemService(ct.ACTIVITY_SERVICE);
        /**
         * 获取WIFI服务
         */
        wifiManager=(WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
    }


    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()) {
            case R.id.startPatro://开始巡逻
                //ToastUtility.showToast("开始巡逻");
                boolean isPatrolRouteManagementServiceStart = false;
                serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
                int serviceListSize = serviceList.size();
                for (int i = 0; i < serviceListSize; i++) {
                    if (serviceList.get(i).service.getClassName().equals(OnPatrolRegisterService.class.getName())) {
                        //ToastUtility.showToast("已经启动了OnPatrolRegisterService");
                        isPatrolRouteManagementServiceStart = true;
                        break;
                    }
                }
                if (!isPatrolRouteManagementServiceStart) {
                    //没有指定服务启动
                    new AlertDialog.Builder(getActivity()).setMessage("确定开始巡逻吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    if (ActivityUtils.isWifi(ct)) {
                                        wifiManager.setWifiEnabled(false);
                                        //Toast.makeText(getActivity(), "wifi已关闭，开始记录路线",Toa).show();
                                        ToastUtility.showToast("wifi已关闭，开始记录路线");
                                    }
                                    Intent serivceIntent = new Intent(ct, OnPatrolRegisterService.class);
                                    ct.startService(serivceIntent);
                                    kaishixl.setText("结束巡逻");
                                    kaishixl.setTextColor(Color.parseColor("#ff0000"));// .........
                                    //startImg.setBackgroundColor(android.graphics.Color.parseColor("#ff0000"));
                                    startImg.setImageDrawable(getResources().getDrawable(R.drawable.stopimg));
                                    //startImg.setImageResource(R.drawable.stopimg);
//						ct.bindService(intent1, conn, Service.BIND_AUTO_CREATE);
                                    serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
                                }
                            }).setNegativeButton("取消", null).create().show();
                } else {
                    new AlertDialog.Builder(getActivity()).setMessage("确定关闭巡逻吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
//						getAllLocation();
                                    if (!ActivityUtils.isWifi(ct)) {
                                        wifiManager.setWifiEnabled(true);
                                    }
                                    Intent intent = new Intent(ct, OnPatrolRegisterService.class);
                                    ct.stopService(intent);
                                    //startImg.setBackgroundColor(android.graphics.Color.parseColor("#9966e9"));
                                    kaishixl.setText("开始巡逻");
                                    startImg.setImageDrawable(getResources().getDrawable(R.drawable.xunluo));
                                    kaishixl.setTextColor(android.graphics.Color.parseColor("#151515"));// ........
                                    serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
                                }
                            }).setNegativeButton("取消", null).create().show();
                }
                break;
            case R.id.routeMnage://路线管理
                //ToastUtility.showToast("路线管理");
                intent = new Intent(ct, PatrolRouteInformationActivity.class);
                break;
            case R.id.xunluo_manager://轨迹查询
                ToastUtility.showToast("轨迹查询");
                break;
        }
        if (null != intent) {
            ct.startActivity(intent);
            ((Activity) ct).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }
}
