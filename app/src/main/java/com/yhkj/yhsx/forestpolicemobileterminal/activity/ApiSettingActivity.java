package com.yhkj.yhsx.forestpolicemobileterminal.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ToastUtility;

import butterknife.BindView;

/**
 * HTTP接口设置Activity
 */
public class ApiSettingActivity extends ParentActivity implements View.OnClickListener {


    @BindView(R.id.etWServerIp)
    EditText etWServerIp;
    @BindView(R.id.etwebport)
    EditText etwebport;
    @BindView(R.id.etapiport)
    EditText etapiport;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;

    private Intent intent;

    @Override
    protected int layoutResID() {
        return R.layout.activity_api_setting;
    }

    @Override
    protected void initView() {
        btnConfirm.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:
                if (isNullString(etWServerIp.getText().toString())) {
                    ToastUtility.showToast("服务器地址不能为空!");
                    return;
                }
                if (isNullString(etwebport.getText().toString())) {
                    ToastUtility.showToast("服务器端口不能为空!");
                    return;
                }

                if (Integer.parseInt(etwebport.getText().toString()) > 65535) {
                    ToastUtility.showToast("服务器端口不能大于65535，请重新输入!");
                    return;
                }

                if (isNullString(etapiport.getText().toString())) {
                    ToastUtility.showToast("消息推送端口不能为空!");
                    return;
                }

                if (Integer.parseInt(etapiport.getText().toString()) > 65535) {
                    ToastUtility.showToast("消息推送端口不能大于65535，请重新输入!");
                    return;
                }
                ActivityUtils.getInstance().setServerUrl(etWServerIp.getText().toString());
                ActivityUtils.getInstance().setServerPort(etwebport.getText().toString());
                ActivityUtils.getInstance().setMqttPort(etapiport.getText().toString());
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                ActivityUtils.getInstance().starAnimForActivity(this);
                finish();
                break;
        }
    }
}
