package com.yhkj.yhsx.forestpolicemobileterminal.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yhkj.yhsx.forestpolicemobileterminal.services.MyService;

/**
 * 启动服务
 * 
 * @author xingyimin
 * 
 */
public class AutoStartReceiver extends BroadcastReceiver {

	private Intent intent;

	public AutoStartReceiver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		intent = new Intent(arg0, MyService.class);
		// Bundle bundle = new Bundle();
		// bundle.putString("userId", ActivityUtils.getUseId(arg0) + "");
		// intent.putExtras(bundle);
		// 启动服务
		arg0.startService(intent);
	}

}
