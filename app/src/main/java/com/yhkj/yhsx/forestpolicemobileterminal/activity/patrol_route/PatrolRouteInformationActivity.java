package com.yhkj.yhsx.forestpolicemobileterminal.activity.patrol_route;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.MapActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.ParentActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.OptionEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.PatrolRouteManagementEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.services.PatrolRouteManagementService;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.LanUtil;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.NetUtil;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import db.Database.PatrolRouteManagement;
import db.OptionDao;

/**
 * 巡防线路管理--查询
 * 
 * @author xingyimin
 * 
 */
public class PatrolRouteInformationActivity extends ParentActivity {

	@BindView(R.id.lvPatrolRouteInformationList)
	 ListView lvPatrolRouteList;

	private Context activity;
	private List<PatrolRouteManagementEntity> patrolList;
	private PatrolRouteAdapter prAdapter;
	private ArrayList<OptionEntity> typeList, adviseList, weatherList;
	private SharedPreferences settings;
	private boolean isCheck;
	private ProgressDialog dialog;

	@Override
	protected int layoutResID() {
		return R.layout.activity_patrol_route_information;
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void initData() {
		activity = this;
		//lvPatrolRouteList = (ListView) findViewById(R.id.lvPatrolRouteInformationList);
		OptionDao dao = new OptionDao(activity);
		ArrayList<OptionEntity> options = dao
				.getOptionByFormMainId(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TABLE_ID);
		Log.d("TAG","options.size():"+options.size());
		if (options.size() > 0 && options != null) {
			typeList = new ArrayList<OptionEntity>();
			adviseList = new ArrayList<OptionEntity>();
			typeList.add(0, new OptionEntity(0, "线路类型……"));
			adviseList.add(0, new OptionEntity(0, "行驶建议……"));
			weatherList = new ArrayList<OptionEntity>();
			weatherList.add(0, new OptionEntity(0, "天气……"));
			for (int i = 0; i < options.size(); i++) {
				switch (options.get(i).getCtrlValue()) {
					case 1:
						typeList.add(new OptionEntity(options.get(i).getDictValue(), options.get(i)
								.getDictName()));
						break;
					case 3:
						adviseList.add(new OptionEntity(options.get(i).getDictValue(), options.get(i)
								.getDictName()));
						break;
					case 5:
						weatherList.add(new OptionEntity(options.get(i).getDictValue(), options.get(i)
								.getDictName()));
						break;
					default:
						break;
				}
			}
		} else {
			Toast.makeText(activity, R.string.update_date_under_wifi, Toast.LENGTH_SHORT).show();
			//finish();
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!ActivityUtils.isLogin(activity)) {
			ActivityUtils.showLogin(activity);
		} else {
			if (NetUtils.getInstance().isNetworkAvalible(activity)) {
				/*
				 * PatrolRouteTask at = new PatrolRouteTask(this); at.execute();
				 */
				getData("");
			} else {
				Toast.makeText(activity, getString(R.string.toast_message_netException),
						Toast.LENGTH_LONG).show();
			}
			
			if (null != menu) {
				ActivityManager activityManager = (ActivityManager) activity
						.getSystemService(ACTIVITY_SERVICE);
				List<ActivityManager.RunningServiceInfo> serviceList = activityManager
						.getRunningServices(Integer.MAX_VALUE);
				boolean isServiceStart = false;
				for (int i = 0; i < serviceList.size(); i++) {
					if (serviceList.get(i).service.getClassName().equals(PatrolRouteManagementService.class.getName())) {
						isServiceStart = true;
						break;
					}
				}
				
				
				if (isServiceStart) {
					menu.findItem(R.id.menu_new).setVisible(false);
					menu.findItem(R.id.menu_new_stop).setVisible(true);
				} else {
					menu.findItem(R.id.menu_new).setVisible(true);
					menu.findItem(R.id.menu_new_stop).setVisible(false);
				}
			}
		
			
		}
		
	}
	
	private Menu menu;

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patrol_route_information, menu);
		this.menu = menu;

		ActivityManager activityManager = (ActivityManager) activity
				.getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(Integer.MAX_VALUE);
		boolean isServiceStart = false;
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(PatrolRouteManagementService.class.getName())) {
				isServiceStart = true;
				break;
			}
		}
		
		
		if (isServiceStart) {
			menu.findItem(R.id.menu_new).setVisible(false);
			menu.findItem(R.id.menu_new_stop).setVisible(true);
		} else {
			menu.findItem(R.id.menu_new).setVisible(true);
			menu.findItem(R.id.menu_new_stop).setVisible(false);
		}
		
		//menu.add(Menu.NONE,Menu.NONE,Menu.NONE,"OK");// .setIcon(drawable ID)
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.menu_new || id == R.id.menu_new_stop) {
			if (ActivityUtils.judgeSimState(activity) == 1) {
				return false;
			}
			Intent it = new Intent(activity, PatrolRouteManagementActivity.class);
			startActivity(it);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 查询所有地图路线
	 * */
	private void getData(String strwhere) {
		/*if (NetUtil.init(activity).netState(activity)) {
			dialog = ProgressDialog.show(activity, "", getString(R.string.dialog_message_loading),false,true);
			AjaxParams params = new AjaxParams();
			params.put("strwhere", strwhere);
			NetworkConnections.init(activity).callNetworkInterfaceByPost(activity,
					"PatrolRouteManagement/GetPatrolRouteManagement", params,
					new MyAjaxCallback("PatrolRouteManagement/GetPatrolRouteManagement", dialog) {
						@Override
						public void onSuccess(String t) {
							dialog.dismiss();
							ActivityUtils.print(t, Environment.getExternalStorageDirectory()
									.getPath() + "/tmp.txt");
							try {
								JSONObject json = new JSONObject(t);
								if (json.getInt("Error") == 0) {
									patrolList = PatrolRouteManagementEntity.getObject(json
											.getJSONArray("PatrolRouteManagementList").toString());
									if (null == patrolList) {
										Toast.makeText(activity,
												getString(R.string.toast_message_Abnormal_server),
												Toast.LENGTH_LONG).show();
										lvPatrolRouteList.removeAllViewsInLayout();
									} else {

										if (patrolList.size() == 0) {
											Toast.makeText(activity,
													getString(R.string.toast_message_no_data),
													Toast.LENGTH_LONG).show();
										} else {
											prAdapter = new PatrolRouteAdapter();
											prAdapter.notifyDataSetChanged();
											lvPatrolRouteList.setAdapter(prAdapter);
										}
									}
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
			if (dialog != null) {
				dialog.dismiss();
			}
		}*/
	}

	/*
	 * 轻量级的异步类
	 */
	private class PatrolRouteTask extends AsyncTask<Void, Void, Boolean> {

		private Activity activity;

		public PatrolRouteTask(Activity activity) {
			super();
			// TODO Auto-generated constructor stub
			this.activity = activity;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			ActivityUtils.showProgress(activity, getString(R.string.dialog_message_loading));
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			settings = getSharedPreferences("HOLD", Context.MODE_PRIVATE);
			isCheck = settings.getBoolean("isCheck", false);
			if (isCheck) {
				String json = LanUtil.getJSONString("GetJson_Patrol_route_management", null, null);
				if (json == null) {
					Toast.makeText(activity, getString(R.string.toast_message_Abnormal_server),
							Toast.LENGTH_LONG).show();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							lvPatrolRouteList.removeAllViewsInLayout();
						}
					});

				} else if (json.equals("anyType{}")) {
					Toast.makeText(activity, getString(R.string.toast_message_no_data),
							Toast.LENGTH_LONG).show();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							lvPatrolRouteList.removeAllViewsInLayout();
						}
					});
				} else {
					try {
						patrolList = PatrolRouteManagementEntity.getObject(json);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}
				}
			} else {
				String Json = NetUtil.init(activity).getJSONString(
						"GetJson_Patrol_route_management", null, null);
				if (Json != null && !Json.equals("anyType{}")) {
					try {
						patrolList = PatrolRouteManagementEntity.getObject(Json);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}
				} else if (Json.equals("anyType{}")) {

				}
			}

			if (patrolList != null && patrolList.size() > 0) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			if (!result) {
				if (patrolList == null) {
					Toast.makeText(activity, getString(R.string.toast_message_Abnormal_server),
							Toast.LENGTH_LONG).show();
					lvPatrolRouteList.removeAllViewsInLayout();
				} else if (patrolList.size() == 0) {
					Toast.makeText(activity, getString(R.string.toast_message_no_data),
							Toast.LENGTH_LONG).show();
				}
			} else {
				if (patrolList != null && patrolList.size() > 0) {
					prAdapter = new PatrolRouteAdapter();
					lvPatrolRouteList.setAdapter(prAdapter);

				} else {

				}
				// lvPatrolRouteList
				// .setOnItemClickListener(new OnItemClickListener() {
				//
				// @Override
				// public void onItemClick(AdapterView<?> arg0,
				// View arg1, int arg2, long arg3) {
				// // TODO Auto-generated method stub
				// ListView listView = (ListView) arg0;
				// listView.getItemAtPosition(arg2);
				// Bundle bundle = new Bundle();
				// bundle.putSerializable("PR",
				// patrolList.get(arg2));
				// Intent it = new Intent(
				// activity,
				// PatrolRouteInformationDetailActivity.class);
				// it.putExtra("PR", patrolList.get(arg2));
				// startActivity(it);
				// }
				// });
			}
			ActivityUtils.hideProgress();
		}

	}

	private class PatrolRouteAdapter extends BaseAdapter {
		LayoutInflater listContainer; // 视图容器工厂

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return patrolList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return patrolList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			long startTime = System.currentTimeMillis();
			if (ActivityUtils.ISDEBUG) {
				System.out.println("position = " + position + ",startTime = " + startTime
						+ ",convertView = " + convertView);
			}
			if (null == convertView) {
				listContainer = LayoutInflater.from(activity); // 创建视图容器工厂并设置上下文
				// if (convertView == null) {
				convertView = listContainer.inflate(R.layout.patrol_route_information_list_item,
						null);
				// convertView.setTag(patrolList.get(position).getPatrolId());
			}

			// }
			TextView tvTitle = (TextView) convertView.findViewById(R.id.tvPatrolName);
			tvTitle.setText(patrolList.get(position).getName());
			TextView tvDistance = (TextView) convertView.findViewById(R.id.tvPatrolDistance);
			double distance = patrolList.get(position).getDistance();
			// DecimalFormat decimalf = new DecimalFormat(".##");
			DecimalFormat decimalf = new DecimalFormat("0.00000");
			tvDistance.setText("距离：" + decimalf.format(distance / 1000) + "公里");

			TextView tvTime = (TextView) convertView.findViewById(R.id.tvPatrolTime);
			String route = patrolList.get(position).getRoute();
			int minu = 0;
			try {
				if (null != route) {
					List<Loaction> locationList = Loaction.getEntityList(new JSONArray(route));
					String start = locationList.get(0).getTIME();
					String end = locationList.get(locationList.size() - 1).getTIME();
					DateFormat datef = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Calendar calendar1 = datef.getCalendar();
					Date d1 = (Date) datef.parse(start);
					Date d2 = (Date) datef.parse(end);
					long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别
					if (diff >= 3600000) {
						minu = (int) (diff / 1000 / 60 / 60);
						tvTime.setText("时间：" + minu + "小时");
					} else {

						tvTime.setText("时间：" + (int) (diff / 1000 / 60) + "分钟");
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			TextView tvPatrolWeather = (TextView) convertView.findViewById(R.id.tvPatrolWeather);
			tvPatrolWeather.setText("天气："
					+ ActivityUtils.getDictNameByDictValue(weatherList, patrolList.get(position)
							.getWeather()));
			TextView tvAdvise = (TextView) convertView.findViewById(R.id.tvPatrolAdvise);
			tvAdvise.setText("行驶建议："
					+ ActivityUtils.getDictNameByDictValue(adviseList, patrolList.get(position)
							.getTravelAdviceId()));
			TextView tvType = (TextView) convertView.findViewById(R.id.tvPatrolType);
			// tvType.setText("线路类型："
			// + typeList.get(patrolList.get(position).getTypeId()));
			tvType.setText("线路类型："
					+ ActivityUtils.getDictNameByDictValue(typeList, patrolList.get(position)
							.getTypeId()));
			ImageView ivLocation = (ImageView) convertView.findViewById(R.id.ivPatrolMap);
			if (patrolList.get(position).getRoute() != null) {
				ivLocation.setImageResource(R.drawable.ic_dialog_map);
				ivLocation.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String rate = patrolList.get(position).getRoute();
						List<Loaction> locationList;
						try {
							// 获取记录线路的点数集合，当集合中大于等于2个点的时候跳转地图界面
							locationList = Loaction.getEntityList(new JSONArray(rate));
							if (locationList.size() >= 2 && locationList.size() <= 10000) {
								Intent it = new Intent(activity, MapActivity.class);
								/*
								 * it.putExtra("route", patrolList.get(position)
								 * .getRoute());
								 */
								// 用单例传递参数
								Route route = Route.getRoute();
								route.setStrroute(rate);
								startActivity(it);
							} else if (locationList.size() > 10000) {
								Toast.makeText(PatrolRouteInformationActivity.this, "记录时间太长！",
										Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(PatrolRouteInformationActivity.this, "记录时间太短！",
										Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			} else {
				ivLocation.setImageResource(R.drawable.ic_menu_mylocation);
			}
			long endTime = System.currentTimeMillis();
			if (ActivityUtils.ISDEBUG) {
				System.out.println("position = " + position + ",entTime = " + endTime
						+ ",diffTime = " + (endTime - startTime));
			}
			return convertView;
		}
	}

	/**
	 * json字符串超过了40k Intent不能传递所以这个类 为MapActivity传递json字符串
	 * */
	public static class Route {
		private String strroute;
		private static Route route;

		private Route() {

		}

		public void setStrroute(String strroute) {
			this.strroute = strroute;
		}

		public String getStrroute() {
			return strroute;
		}

		public static Route getRoute() {
			if (route == null) {
				route = new Route();
			}
			return route;
		}
	}
}
