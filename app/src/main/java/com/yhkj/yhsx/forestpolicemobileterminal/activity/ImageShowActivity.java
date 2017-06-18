package com.yhkj.yhsx.forestpolicemobileterminal.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.widget.NetworkedCacheableImageView;

/**
 * 图片显示
 * 
 * @author xingyimin
 * 
 */
public class ImageShowActivity extends ActionBarActivity {

	private NetworkedCacheableImageView ivDetails;

	private Intent intent;

	private String filepath;

	private Context context;

	private AlertDialog builder;

	private Class cl;

	private int stateID, hostPoliceID, coPoliceID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_show);

		context = this;

		intent = getIntent();

		filepath = intent.getStringExtra("filepath");

		cl = (Class) intent.getSerializableExtra("class");

		stateID = intent.getIntExtra("stateID", -1);

		hostPoliceID = intent.getIntExtra("hostPoliceID", -1);

		coPoliceID = intent.getIntExtra("coPoliceID", -1);

		ivDetails = (NetworkedCacheableImageView) findViewById(R.id.ivDetails);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);

	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (filepath.indexOf("http://") != -1) {
			ivDetails.loadImage(filepath, false, null);
			// ImageManager2.from(context).displayImage(ivDetails, filepath,
			// -1);
		} else {
			/*
			 * fis = new FileInputStream(filepath.get(index)); Bitmap bitmap =
			 * BitmapFactory.decodeStream(fis); if (bitmap == null) {
			 * ivDetails.loadImage(filepath.get(index), false, null); }else{
			 * ivDetails.setImageBitmap(bitmap); }
			 */
			ivDetails.setImageURI(Uri.parse(filepath));
		}
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		if ((stateID == 2 || stateID == 5)
				&& (ActivityUtils.getUseId(context) == hostPoliceID || ActivityUtils
						.getUseId(context) == coPoliceID)) {
			getMenuInflater().inflate(R.menu.image_show, menu);
		}
		return true;
	}

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_del) {
			builder = new AlertDialog.Builder(this).setTitle("提示")
					.setMessage("确定删除吗？")
					.setPositiveButton("是", new OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							builder.dismiss();
							// filepath.remove(index);
							// Intent it = new Intent(context,cl);
							// Bundle bundle = new Bundle();
							// bundle.putSerializable("filepath", (Serializable)
							// filepath);
							// it.putExtra("filepath",bundle);
							setResult(Activity.RESULT_OK, intent);
							finish();
						}
					}).setNegativeButton("否", null).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
*/
	private class MyAsycTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			ActivityUtils.showProgress(context, "请稍候……");
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Intent it = new Intent(context, cl);
			/*
			 * Bundle bundle = new Bundle(); bundle.putSerializable("filepath",
			 * (Serializable) filepath);
			 */
			intent.putExtra("filepath", filepath);
			setResult(Activity.RESULT_OK, intent);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			ActivityUtils.hideProgress();
			finish();
		}

	}

}
