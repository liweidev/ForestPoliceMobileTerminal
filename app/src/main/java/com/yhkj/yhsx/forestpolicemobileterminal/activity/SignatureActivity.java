package com.yhkj.yhsx.forestpolicemobileterminal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.widget.SignatureView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

/**
 * 签名笔迹
 * 
 * @author liupeng
 * 
 */

public class SignatureActivity extends Activity implements OnClickListener {

	private Button btnSignatureClear;

	private TextView tvSignatureSave;

	private SignatureView sv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.signature);

		sv = (SignatureView) findViewById(R.id.signature_view);

		btnSignatureClear = (Button) findViewById(R.id.btnSignature_clear);

		tvSignatureSave = (TextView) findViewById(R.id.tvSignature_save);

		btnSignatureClear.setOnClickListener(this);
		tvSignatureSave.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btnSignature_clear:
			sv.clear();
			break;
		case R.id.tvSignature_save:
			SignatureSaveToFileTask sstft = new SignatureSaveToFileTask(this);
			sstft.execute();
			break;
		default:
			break;
		}
	}

//	private String filePath;
	String filePath = Environment.getExternalStorageDirectory().getPath() + "/forestPolice/";
	private class SignatureSaveToFileTask extends
			AsyncTask<Void, Integer, String> {

		private Activity context;

		public SignatureSaveToFileTask(Activity ct) {
			// TODO Auto-generated constructor stub
			context = ct;
		}

		/**
		 * 运行在UI线程中，在调用doInBackground()之前执行
		 */
		@Override
		protected void onPreExecute() {
			ActivityUtils.showProgress(context, "正在保存签名！请稍候……");
		}

		/**
		 * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
		 */
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub

			// Make sure the Pictures directory exists.
			File path = new File(filePath);
			if (!path.exists()) {
				path.mkdirs();
			}
			Calendar c = Calendar.getInstance();
			String name = "" + c.get(Calendar.YEAR) + c.get(Calendar.MONTH)
					+ c.get(Calendar.DAY_OF_MONTH)
					+ c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE)
					+ c.get(Calendar.SECOND) + ".jpg";
			filePath = filePath  + name;
			File file = new File(filePath);
			if (!file.exists()){
				try {
					boolean isSuccess = file.createNewFile();
					if (isSuccess) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								try {
									sv.saveToFile(filePath);
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
							}
						});

						return filePath;
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

		/**
		 * 运行在ui线程中，在doInBackground()执行完毕后执行
		 */
		@Override
		protected void onPostExecute(String bool) {
			ActivityUtils.hideProgress();
			if (!TextUtils.isEmpty(bool)) {
				Intent intent = new Intent(context, getIntent().getClass());
				intent.putExtra("result", bool);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}else{
				Toast.makeText(context, "保存签名失败！", Toast.LENGTH_SHORT).show();
				if (null != sv) {
					sv.clear();
				}
			}
		}
	}
}
