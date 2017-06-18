package com.yhkj.yhsx.forestpolicemobileterminal.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.MD5;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.NetUtils;

import butterknife.BindView;

/**
 * 登录并检查更新
 * 
 * @author xingyimin
 * 
 */
public class LoginActivity extends ParentActivity {

	private Context context;

	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] { "foo@example.com:hello",
			"bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	//private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	@BindView(R.id.email)
	 EditText mEmailView;

	@BindView(R.id.password)
	 EditText mPasswordView;

	@BindView(R.id.login_form)
	 View mLoginFormView;

	@BindView(R.id.login_status)
	 View mLoginStatusView;

	@BindView(R.id.login_status_message)
	 TextView mLoginStatusMessageView;



	@Override
	protected int layoutResID() {
		return R.layout.activity_login;
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void initData() {
		context = this;

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		//mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		//mPasswordView = (EditText) findViewById(R.id.password);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
			mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
					//用户按下软键盘回车键时调用此方法
					if (id == R.id.login || id == EditorInfo.IME_NULL) {
						//尝试登录
						attemptLogin();
						return true;
					}
					return false;
				}
			});
		}

		//mLoginFormView = findViewById(R.id.login_form);
		//mLoginStatusView = findViewById(R.id.login_status);
		//mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});
		findViewById(R.id.ivSetting).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent it = new Intent(context, ApiSettingActivity.class);
				startActivity(it);
			}
		});

		showProgress(false);

	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	private void attemptLogin() {
		if (!NetUtils.getInstance().isNetworkAvalible(context)) {//当前网络不可用
			Toast.makeText(context, "您的网络已经断开，请检查网络!", Toast.LENGTH_LONG).show();
			//进入系统设置界面
			Intent intent = new Intent(Settings.ACTION_SETTINGS);// ACTION_WIRELESS_SETTINGS
																	// ACTION_SETTINGS
			context.startActivity(intent);
			return;
		}
		// if (mAuthTask != null) {
		// return;
		// }

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();//用户名
		mPassword = mPasswordView.getText().toString();//密码

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {//密码非空判断
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} /*
		 * else if (mPassword.length() < 4) {
		 * mPasswordView.setError(getString(R.string.error_invalid_password));
		 * focusView = mPasswordView; cancel = true; }
		 */

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {//用户名非空判断
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} /*
		 * else if (!mEmail.contains("@")) {
		 * mEmailView.setError(getString(R.string.error_invalid_email));
		 * focusView = mEmailView; cancel = true; }
		 */
		if (cancel) {//如果用户名或者密码为空，提示用户
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			// mAuthTask = new UserLoginTask();
			// mAuthTask.execute((Void) null);
			login();
		}
	}

	/**
	 * 登录
	 */
	private void login() {
		mEmail = mEmailView.getText().toString(); // 用户名
		mPassword = MD5.Md5(mPassword).toUpperCase(); // 密码
		//
		/*AjaxParams params = new AjaxParams();
		params.put("account", mEmail);//存储用户名
		params.put("password", mPassword);//存储密码
		NetworkConnections.init(context).callNetworkInterfaceByPost(context, "Users/Get_users",
				params, new MyAjaxCallback("Users/Get_users", null) {
					@Override
					public void onSuccess(String t) {
						if (ActivityUtils.ISDEBUG) {
						System.out.println("Users/Get_users:---" + t);
						}
						try {
							JSONObject json = new JSONObject(t);
							if (json.getInt("Error") == 0 && !json.isNull("UserList")
									&& json.getJSONArray("UserList").length() > 0) {
								Toast.makeText(context, "登录成功！", Toast.LENGTH_LONG).show();
								SharedPreferences sp = context.getSharedPreferences("USER",
										Activity.MODE_PRIVATE);
								Editor editor = sp.edit();
								JSONArray ja = json.getJSONArray("UserList");
								JSONObject jo = ja.getJSONObject(0);
								editor.putString("name", jo.getString("name"));
								editor.putInt("userID", jo.getInt("usersID"));
								editor.commit();
								Intent intent = new Intent(context, MyService.class);
								Bundle bundle = new Bundle();
								bundle.putString("userId", ActivityUtils.getUseId(context) + "");
								intent.putExtras(bundle);
								// 启动服务
								startService(intent);

								if (NetUtil.init(context).isNetworkAvalible(context)) {
									MqttService.actionStart(context);
								}
								//先看上面2个service的onCreate方法执行逻辑 TODO
								MqttService.actionChangeUser(getApplicationContext());
								Intent it = new Intent(context, MainActivity1.class);//....................
								startActivity(it);
								finish();
							} else {
								mEmailView.setError(getString(R.string.error_incorrect_password));
								mPasswordView
										.setError(getString(R.string.error_incorrect_password));
								// mPasswordView.requestFocus();
								showProgress(false);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Toast.makeText(context, "连接服务器异常，请检查您的服务器连接配置！", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(context, SettingActivity.class);
							startActivity(intent);
							showProgress(false);
						}
					}

					@Override
					public void onFailure(Throwable t, int errorNo, String strMsg) {
						// TODO Auto-generated method stub
//						super.onFailure(t, errorNo, strMsg);
						
						if (strMsg.contains("timed out")) {
							Toast.makeText(context, "登录超时，请确认服务器连接配置正确和网络条件良好后登录!", Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(context, "连接服务器异常，请检查您的服务器连接配置！", Toast.LENGTH_LONG).show();
							Intent intent = new Intent(context, SettingActivity.class);
							startActivity(intent);
						}
						showProgress(false);
					}

				});*/
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	/*private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			mEmail = mEmailView.getText().toString(); // 用户名
			mPassword = MD5.Md5(mPassword).toUpperCase(); // 密码

			String result = NetUtil.init(context).Login(mEmail, mPassword);

			if (result != null && !result.equals("anyType{}")) {
				SharedPreferences sp = context.getSharedPreferences("USER", Activity.MODE_PRIVATE);
				Editor editor = sp.edit();
				JSONArray ja;
				try {
					ja = new JSONArray(result);
					JSONObject jo = ja.getJSONObject(0);
					editor.putString("name", jo.getString("name"));
					editor.putInt("userID", jo.getInt("usersID"));
					editor.commit();
					MqttService.actionChangeUser(getApplicationContext());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			} else {
				return false;
			}
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				Toast.makeText(context, "登录成功！", Toast.LENGTH_SHORT).show();
				Intent it = new Intent(context, MainActivity1.class);
				startActivity(it);
				finish();
			} else {
				mPasswordView.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}*/

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		showProgress(false);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		showProgress(false);
	}
}
