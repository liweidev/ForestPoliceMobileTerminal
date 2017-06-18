package com.yhkj.yhsx.forestpolicemobileterminal.activity.patrol_route;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.LocationInfoEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.OptionEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.PatrolRouteManagementEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.services.PatrolRouteManagementService;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import db.Database.PatrolRouteManagement;
import db.OptionDao;
import db.PatrolRouteManagementDao;
import db.RouteInfoDao;

/**
 * 巡防线路管理---添加
 * 
 * @author xingyimin
 * 
 */
public class PatrolRouteManagementActivity extends ActionBarActivity implements OnClickListener {

	private EditText etPrmName;// 目的地名称
	private Spinner spPrmType;// 线路类型
	private Spinner spPrmTravelAdvice;// 行驶建议
	private Spinner spPrmRate;// 行驶方式
	private Spinner spPrmWeather;// 记录天气
	private TextView tvName;
	private LinearLayout lvBtnPrmStartAndEnd;
	private Context activity;
	private ArrayList<OptionEntity> typeList, adviseList, routeList, weatherList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patrol_route_management);
		activity = this;
		etPrmName = (EditText) findViewById(R.id.etPatrolRouteManagementName);
		spPrmType = (Spinner) findViewById(R.id.spPatrolRouteManagementType);
		spPrmRate = (Spinner) findViewById(R.id.spPatrolRouteManagementRate);
		spPrmTravelAdvice = (Spinner) findViewById(R.id.spPatrolRouteManagementTravelAdvice);
		tvName = (TextView) findViewById(R.id.tvPatrolRouteManagementStartAndEnd);
		spPrmWeather = (Spinner) findViewById(R.id.spPatrolRouteManagementWeather);
		lvBtnPrmStartAndEnd = (LinearLayout) findViewById(R.id.ivBtnPatrolRouteManagementStartAndEnd);
		lvBtnPrmStartAndEnd.setOnClickListener(this);
		OptionDao dao = new OptionDao(activity);
		ArrayList<OptionEntity> options = dao
				.getOptionByFormMainId(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TABLE_ID);
		if (options.size() > 0 && options != null) {
			typeList = new ArrayList<OptionEntity>();
			adviseList = new ArrayList<OptionEntity>();
			routeList = new ArrayList<OptionEntity>();
			weatherList = new ArrayList<OptionEntity>();
			typeList.add(0, new OptionEntity(0, "线路类型……"));
			adviseList.add(0, new OptionEntity(0, "行驶建议……"));
			routeList.add(0, new OptionEntity(0, "行驶方式……"));
			routeList.add(new OptionEntity(0, "步行"));
			routeList.add(new OptionEntity(0, "驾车"));
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
			ActivityUtils.showSpinnerAdapter(activity, typeList, spPrmType);
			ActivityUtils.showSpinnerAdapter(activity, routeList, spPrmRate);
			ActivityUtils.showSpinnerAdapter(activity, adviseList, spPrmTravelAdvice);
			ActivityUtils.showSpinnerAdapter(activity, weatherList, spPrmWeather);
			ActivityManager activityManager = (ActivityManager) activity
					.getSystemService(ACTIVITY_SERVICE);
			List<ActivityManager.RunningServiceInfo> serviceList = activityManager
					.getRunningServices(Integer.MAX_VALUE);

			for (int i = 0; i < serviceList.size(); i++) {
				if (serviceList.get(i).service.getClassName().equals(PatrolRouteManagementService.class.getName())) {
					lvBtnPrmStartAndEnd.setBackgroundResource(R.drawable.fire_prevention_btn);
					tvName.setText(getString(R.string.stop));
					SharedPreferences sp = getSharedPreferences("RATE", Activity.MODE_PRIVATE);
					try {
						JSONObject ja = new JSONObject(sp.getString("PATROL", ""));
						if (!ja.isNull("NAME")) {
							etPrmName.setText(ja.getString("NAME"));
						}
						if (!ja.isNull("TYPE")) {
							for (int j = 0; j < typeList.size(); j++) {
								if (ja.getInt("TYPE") == typeList.get(j).getDictValue()) {
									spPrmType.setSelection(j);
									spPrmType.setClickable(false);
								}
							}
						}
						if (!ja.isNull("ADVICE")) {
							for (int j = 0; j < adviseList.size(); j++) {
								if (ja.getInt("ADVICE") == adviseList.get(j).getDictValue()) {
									spPrmTravelAdvice.setSelection(j);
									spPrmTravelAdvice.setClickable(false);
								}
							}
						}
						if (!ja.isNull("WEATHER")) {
							for (int j = 0; j < weatherList.size(); j++) {
								if (ja.getInt("WEATHER") == weatherList.get(j).getDictValue()) {
									spPrmWeather.setSelection(j);
									spPrmWeather.setClickable(false);
								}
							}
						}
						if (!ja.isNull("RATE")) {
							for (int j = 0; j < routeList.size(); j++) {
								if (ja.getInt("RATE") == routeList.get(j).getDictValue()) {
									spPrmRate.setSelection(j);
									spPrmRate.setClickable(false);
								}
							}
						}
						if (!ja.isNull("GUID")) {
							guid = ja.getString("GUID");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
			if (PatrolRouteManagementService.FALG_PAUSE == PatrolRouteManagementService.pauseServiceFlag) {
				tvName.setText(R.string.continue_to);
				lvBtnPrmStartAndEnd.setBackgroundResource(R.drawable.security_btn);
			} 
		} else {
			Toast.makeText(activity, R.string.update_date_under_wifi, Toast.LENGTH_SHORT).show();
			finish();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patrol_route_management, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		// if (id == R.id.menu_route_save) {
		// PatrolRouteManagementEntity patrol = new
		// PatrolRouteManagementEntity();
		// if (etPrmName != null) {
		// patrol.setName(etPrmName.getText().toString());
		// } else {
		// patrol.setName("");
		// }
		// if (spPrmType != null) {
		// patrol.setTypeId(spPrmType.getSelectedItemPosition());
		// }
		// if (spPrmTravelAdvice != null) {
		// patrol.setTravelAdviceId(spPrmTravelAdvice
		// .getSelectedItemPosition());
		// }
		// if (spPrmWeather != null) {
		// patrol.setWeather(spPrmWeather.getSelectedItemPosition());
		// }
		// SharedPreferences sp = getSharedPreferences("RATE",
		// Activity.MODE_PRIVATE);
		// patrol.setRoute(sp.getString("location", ""));
		// try {
		// patrol.setDistance(getDistance(sp.getString("location", "")));
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// SubmitTask submit = new SubmitTask(activity, patrol,
		// PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TABLE_ID);
		// submit.execute();
		//
		// return true;
		// }
		return super.onOptionsItemSelected(item);
	}

//	/**
//	 * 通过定位信息集合获取距离
//	 * 
//	 * @param str
//	 * @return
//	 * @throws JSONException
//	 */
//	private double getDistance(String str) throws JSONException {
//		double distance = 0;
//		JSONArray ja = new JSONArray(str);
//		for (int i = 0; i < ja.length() - 1; i++) {
//			LatLng point = new LatLng(ja.getJSONObject(i).getDouble(Location.LATITUDE), ja
//					.getJSONObject(i).getDouble(Location.LONGITUDE));
//			LatLng point1 = new LatLng(ja.getJSONObject(i + 1).getDouble(Location.LATITUDE), ja
//					.getJSONObject(i + 1).getDouble(Location.LONGITUDE));
//			// distance += GetShortDistance(point.longitude, point.latitude,
//			// point1.longitude, point1.latitude);
//			distance += DistanceUtil.getDistance(point, point1);
//		}
//
//		return distance;
//	}

	/**
	 * 通过定位信息集合获取距离
	 * 
	 * @param
	 * @return
	 * @throws JSONException
	 */
	private double getDistance(List<LocationInfoEntity> lists) throws JSONException {
		double distance = 0;
		if (null != lists && 1 < lists.size()) {
			int size = lists.size();
			for (int i = 0; i < size - 1; i++) {
				LatLng point = new LatLng(lists.get(i).getLatitude(), lists.get(i).getLongitude());
				LatLng point1 = new LatLng(lists.get(i + 1).getLatitude(), lists.get(i + 1).getLongitude());
				distance += DistanceUtil.getDistance(point, point1);
			}
		}
		return distance;
	}
	
	private double getDistance(String guid){
		RouteInfoDao dao = new RouteInfoDao(this);
		try {
			return getDistance(dao.getAllInfo(guid));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// public double GetShortDistance(double lon1, double lat1, double lon2,
	// double lat2) {
	// double DEF_PI = 3.14159265359; // PI
	// double DEF_2PI = 6.28318530712; // 2*PI
	// double DEF_PI180 = 0.01745329252; // PI/180.0
	// double DEF_R = 6370693.5; // radius of earth
	// double ew1, ns1, ew2, ns2;
	// double dx, dy, dew;
	// double distance;
	// // 角度转换为弧度
	// ew1 = lon1 * DEF_PI180;
	// ns1 = lat1 * DEF_PI180;
	// ew2 = lon2 * DEF_PI180;
	// ns2 = lat2 * DEF_PI180;
	// // 经度差
	// dew = ew1 - ew2;
	// // 若跨东经和西经180 度，进行调整
	// if (dew > DEF_PI)
	// dew = DEF_2PI - dew;
	// else if (dew < -DEF_PI)
	// dew = DEF_2PI + dew;
	// dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
	// dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
	// // 勾股定理求斜边长
	// distance = Math.sqrt(dx * dx + dy * dy);
	// return distance;
	// }

	private String guid;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.ivBtnPatrolRouteManagementStartAndEnd) {
			final Intent intent = new Intent(activity.getApplicationContext(),PatrolRouteManagementService.class);
			if (tvName.getText().equals(getString(R.string.start))) {
				guid = ActivityUtils.randomUUID();//重新生成新的UUID
				if (spPrmRate != null) {
					switch (spPrmRate.getSelectedItemPosition()) {
					case 1:// 步行
//						Bundle bundle = new Bundle();
//						bundle.putInt("rate", 30000);
//						bundle.putString("GUID", guid);
//						intent.putExtras(bundle);
						PatrolRouteManagementService.rate = 30000;
						PatrolRouteManagementService.guid = guid;
						PatrolRouteManagementService.pauseServiceFlag = PatrolRouteManagementService.FALG_START;
						startService(intent);
						break;
					case 2:// 驾车
//						Bundle bundle1 = new Bundle();
//						bundle1.putInt("rate", 10000);
//						bundle1.putString("GUID", guid);
//						intent.putExtras(bundle1);
						PatrolRouteManagementService.rate = 10000;
						PatrolRouteManagementService.guid = guid;
						PatrolRouteManagementService.pauseServiceFlag = PatrolRouteManagementService.FALG_START;
						startService(intent);
						break;
					default:
						Toast.makeText(activity, "请选择行驶方式", Toast.LENGTH_LONG).show();
						return;
					}
					SharedPreferences sp = getSharedPreferences("RATE", Activity.MODE_PRIVATE);
					SharedPreferences.Editor editor = sp.edit();
					JSONObject ja = new JSONObject();
					try {
						if (etPrmName.getText() != null) {
							ja.put("NAME", etPrmName.getText().toString());
						}
						if (spPrmType != null) {
							ja.put("TYPE", spPrmType.getSelectedView().getTag());
						}
						if (spPrmTravelAdvice != null) {
							ja.put("ADVICE", spPrmTravelAdvice.getSelectedView().getTag());
						}
						if (spPrmWeather != null) {
							ja.put("WEATHER", spPrmWeather.getSelectedView().getTag());
						}
						if (spPrmRate != null) {
							ja.put("RATE", spPrmRate.getSelectedView().getTag());
						}
						ja.put("GUID", guid);
						editor.putString("PATROL", ja.toString());
						editor.commit();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lvBtnPrmStartAndEnd.setBackgroundResource(R.drawable.fire_prevention_btn);
					tvName.setText(R.string.stop);
				}
			} else if (tvName.getText().equals(getString(R.string.continue_to))) {
				switch (spPrmRate.getSelectedItemPosition()) {
				case 1:// 步行
//					Bundle bundle = new Bundle();
//					bundle.putInt("rate", 30000);
//					bundle.putString("GUID", guid);
//					intent.putExtras(bundle);
					PatrolRouteManagementService.rate = 30000;
					PatrolRouteManagementService.guid = guid;
					PatrolRouteManagementService.pauseServiceFlag = PatrolRouteManagementService.FALG_START;
					startService(intent);
					break;
				case 2:// 驾车
//					Bundle bundle1 = new Bundle();
//					bundle1.putInt("rate", 10000);
//					bundle1.putString("GUID", guid);
//					intent.putExtras(bundle1);
					PatrolRouteManagementService.rate = 10000;
					PatrolRouteManagementService.guid = guid;
					PatrolRouteManagementService.pauseServiceFlag = PatrolRouteManagementService.FALG_START;
					startService(intent);
					break;
				default:
					return;
				}
				lvBtnPrmStartAndEnd.setBackgroundResource(R.drawable.fire_prevention_btn);
				tvName.setText(R.string.stop);
			} else {
				new AlertDialog.Builder(activity).setTitle("提示").setIcon(R.drawable.ic_launcher)
						.setMessage("是否停止记录路线信息？")
						.setPositiveButton("暂停", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								arg0.dismiss();
								tvName.setText(R.string.continue_to);
								lvBtnPrmStartAndEnd.setBackgroundResource(R.drawable.security_btn);
//								Intent intent = new Intent(activity.getApplicationContext(),PatrolRouteManagementService.class);
//								intent.putExtra("pauseService", 1);//暂停记录标志
								PatrolRouteManagementService.pauseServiceFlag = PatrolRouteManagementService.FALG_PAUSE;
								startService(intent);
							}
						}).setNegativeButton("保存", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								stopService(intent);
								PatrolRouteManagementEntity patrol = new PatrolRouteManagementEntity();
								if (etPrmName.getText() != null
										&& !etPrmName.getText().toString().equals("")) {
									patrol.setName(etPrmName.getText().toString());
								} else {
									Toast.makeText(activity, "线路名称不能为空，请输入！", Toast.LENGTH_SHORT)
											.show();
									return;
								}
								if (spPrmType.getSelectedItemPosition() != 0) {
									patrol.setTypeId(spPrmType.getSelectedItemPosition());
								} else {
									Toast.makeText(activity, "请选择正确的线路类型！", Toast.LENGTH_SHORT)
											.show();
									return;
								}
								if (spPrmTravelAdvice.getSelectedItemPosition() != 0) {
									patrol.setTravelAdviceId(spPrmTravelAdvice
											.getSelectedItemPosition());
								} else {
									Toast.makeText(activity, "请选择正确的驾驶建议！", Toast.LENGTH_SHORT)
											.show();
									return;
								}
								if (spPrmWeather.getSelectedItemPosition() != 0) {
									patrol.setWeather(spPrmWeather.getSelectedItemPosition());
								} else {
									Toast.makeText(activity, "请选择正确的天气！", Toast.LENGTH_SHORT)
											.show();
									return;
								}
//								SharedPreferences sp = getSharedPreferences("RATE",Activity.MODE_PRIVATE);
								patrol.setRoute(guid);//sp.getString("location", "")
								try {
									patrol.setDistance(getDistance(guid));//sp.getString("location", "")
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								/*
								 * SubmitTask submit = new SubmitTask( activity,
								 * patrol, PatrolRouteManagement
								 * .PATROL_ROUTE_MANAGEMENT_TABLE_ID);
								 * submit.execute();
								 */
								submitData(patrol);
								Log.e("路线管理--->", patrol.toString());
							}
						}).show();

			}
		}
	}

	/**
	 * 上传至服务器
	 * */
	private ProgressDialog dialog;
	private PatrolRouteManagementDao patrolDao;

	private void submitData(final PatrolRouteManagementEntity patrol) {
		/*if (NetUtil.init(activity).netState(activity)) {
			// String routeString = "[" +
			// "{\"longitude\":116.302289,\"time\":\"2016-04-21 11:23\",\"latitude\":39.84316,\"locationType\":161,\"elevation\":4.9E-324},"
			// +
			// "{\"longitude\":116.302289,\"time\":\"2016-04-21 11:24\",\"latitude\":39.84316,\"elevation\":4.9E-324,\"locationType\":161},"
			// +
			// "{\"longitude\":116.302289,\"time\":\"2016-04-21 11:24\",\"latitude\":39.84316,\"elevation\":4.9E-324,\"locationType\":161},"
			// +
			// "{\"longitude\":116.302284,\"time\":\"2016-04-21 11:24\",\"latitude\":39.84316,\"elevation\":4.9E-324,\"locationType\":161},"
			// +
			// "{\"longitude\":116.302289,\"time\":\"2016-04-21 11:24\",\"latitude\":39.84356,\"elevation\":4.9E-324,\"locationType\":161},"
			// +
			// "{\"longitude\":116.302289,\"time\":\"2016-04-21 11:25\",\"latitude\":39.84356,\"elevation\":4.9E-324,\"locationType\":1000},"
			// +
			// "{\"longitude\":116.302289,\"time\":\"2016-04-21 11:23\",\"latitude\":39.84316,\"elevation\":4.9E-324,\"locationType\":61}]";
			// patrol.setRoute(routeString);
			dialog = ProgressDialog.show(activity, "", getString(R.string.dialog_message_loading));

			NetworkConnections.init(activity).callNetworkInterfaceByPost(activity,
					"PatrolRouteManagement/EditPatrolRouteManagement",
					PatrolRouteManagementDao.getParams(patrol, ActivityUtils.getUseId(activity)),
					new MyAjaxCallback("PatrolRouteManagement/EditPatrolRouteManagement", dialog) {
						@Override
						public void onSuccess(String t) {
							Log.e("tttt", t.toString());
							JSONObject json;
							try {
								json = new JSONObject(t);
								if (json.getInt("Error") == 0) {
									Toast.makeText(
											activity,
											getResources().getString(
													R.string.dialog_message_uploaded),
											Toast.LENGTH_SHORT).show();
									finish();
								} else {
									patrolDao = new PatrolRouteManagementDao(activity);
									patrolDao.insert(patrol);
									Toast.makeText(activity,
											"网络异常！已经保存到本地数据库！如包含视频和音频文件，请您在Wifi下与服务器同步！",
											Toast.LENGTH_LONG).show();

									finish();
								}
								SharedPreferences sp = activity.getSharedPreferences("RATE",
										Activity.MODE_PRIVATE);
								SharedPreferences.Editor editor = sp.edit();
								editor.clear();
								editor.commit();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (dialog != null) {
								dialog.dismiss();
							}
						}

						@Override
						public void onFailure(Throwable t, int errorNo, String strMsg) {
							// TODO Auto-generated method stub
//							super.onFailure(t, errorNo, strMsg);
							patrolDao = new PatrolRouteManagementDao(activity);
							patrolDao.insert(patrol);
							Toast.makeText(activity,
									"网络异常！已经保存到本地数据库！如包含视频和音频文件，请您在Wifi下与服务器同步！",
									Toast.LENGTH_LONG).show();
							SharedPreferences sp = activity.getSharedPreferences("RATE",
									Activity.MODE_PRIVATE);
							SharedPreferences.Editor editor = sp.edit();
							editor.clear();
							editor.commit();
							finish();
						}
					});
			if (null!= dialog && dialog.isShowing()) {
				dialog.dismiss();
			}
		} else {
			if (null!= dialog && dialog.isShowing()) {
				dialog.dismiss();
			}
			patrolDao = new PatrolRouteManagementDao(activity);
			patrolDao.insert(patrol);
			Toast.makeText(activity, "网络异常！已经保存到本地数据库！如包含视频和音频文件，请您在Wifi下与服务器同步！",
					Toast.LENGTH_LONG).show();
			finish();
		}*/
	}
}
