package com.yhkj.yhsx.forestpolicemobileterminal.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.yhkj.yhsx.forestpolicemobileterminal.services.MqttService;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.NetUtils;

public class ConnectionBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (!NetUtils.getInstance().isNetworkAvalible(context)) {
			Toast.makeText(context,
					"您的网络已经断开，请检查网络!", Toast.LENGTH_LONG)
					.show();
//			MqttService.actionStop(context);
		}else{
			Toast.makeText(context,
					"您的网络已经连接!", Toast.LENGTH_LONG)
					.show();
			MqttService.actionStart(context);
		}
	}

}
