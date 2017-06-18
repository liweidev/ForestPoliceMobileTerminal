package com.yhkj.yhsx.forestpolicemobileterminal.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.patrol_route.PatrolRouteInformationActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.LocationInfoEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

/**
 * 地图显示
 * 
 * @author xingyimin
 * 
 */
public class MapActivity extends ParentActivity {
	// private OverlayOptions mOptions, afterOption, textOptions;
	private BitmapDescriptor iconBitmap, circleBitmap;

	@BindView(R.id.bmapsView)
	 MapView mvSalers;

	private BaiduMap mBaiduMap;
	// private List<Loaction> locationList;
	// private List<LatLng> points;
	// private MKOfflineMap mOffline = null;
	private Activity mContext;
	private int flag = 0;

	@Override
	protected int layoutResID() {
		return R.layout.activity_map;
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void initData() {
		//mvSalers = (MapView) findViewById(R.id.bmapsView);
		mBaiduMap = mvSalers.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(12.0f);
		mBaiduMap.setBuildingsEnabled(true);
		mContext = this;
		Intent intent = getIntent();
		if (null == intent) {
			loadRoute();
			return;
		}
		int selectUserId = intent.getIntExtra("selectUserId", 0);
		if (0 == selectUserId) {
			loadRoute();
		} else {
			String beginTime = intent.getStringExtra("beginTime");
			// String endTime = intent.getStringExtra("endTime");
			loadOnline(selectUserId, beginTime, beginTime);
		}

	}

	private void loadOnline(int userId, final String beginTime, String endTime) {
		// TODO Auto-generated method stub
		if (0 == userId) {
			return;
		}
		/*AjaxParams params = new AjaxParams();
		params.put("usersID", userId + "");
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);

		NetworkConnections.init(mContext).callNetworkInterfaceByPost(mContext,
				"CoordinateInfo/GetDayRouteByUserIDAndTime",
				params, // CoordinateInfo/GetGisInfoAllList
				new MyAjaxCallback("CoordinateInfo/GetDayRouteByUserIDAndTime",
						null) {

					@Override
					public void onSuccess(String t) {
						// System.out.println(t);
						if (TextUtils.isEmpty(t)) {
							Toast.makeText(mContext,
									R.string.toast_message_no_data,
									Toast.LENGTH_SHORT).show();
							return;
						}
						try {
							JSONObject jsonObject = new JSONObject(t);
							if (0 == jsonObject.getInt("Error")) {
								JSONArray jsonArray = jsonObject
										.getJSONArray("GetUserPatrolInformationobject");

								if (jsonArray != null && jsonArray.length() > 0) {
									for (int i = 0; i < jsonArray.length(); i++) {
										List<LocationInfoEntity> locationList = guoLv(LocationInfoEntity
												.getLocationInfoEntityLists(jsonArray
														.getJSONArray(i)));
										if (locationList.size() >= 2
												&& locationList.size() <= 10000) {
											loadRoute(locationList);
										} else if (locationList.size() > 10000) {
											Toast.makeText(MapActivity.this,
													"记录时间太长！",
													Toast.LENGTH_LONG).show();
											finish();
										} *//*
										 * else {
										 * Toast.makeText(MapActivity.this,
										 * "记录时间太短！", Toast.LENGTH_LONG)
										 * .show(); finish(); }
										 *//*
									}
								}

							} else {
								Toast.makeText(mContext,
										R.string.toast_message_Abnormal_server,
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});*/
	}

	/**
	 * 加载地图
	 */
	private void loadRoute() {
		try {
			// locationList = Loaction.getEntityList(new JSONArray(rate));
			List<Loaction> locationList = shaiXuan(Loaction.getEntityList(new JSONArray(
					PatrolRouteInformationActivity.Route.getRoute()
					.getStrroute())));

			if (locationList != null && locationList.size() > 0) {
				// MapStatusUpdate msu = MapStatusUpdateFactory
				// .newLatLng(new LatLng(locationList.get(
				// locationList.size() - 1).getLATITUDE(),
				// locationList.get(locationList.size() - 1)
				// .getLONGITUDE()));
				Collections.sort(locationList, new Comparator<Loaction>() {
					@Override
					public int compare(Loaction lhs, Loaction rhs) {
						// TODO Auto-generated method stub
						if (null != lhs && rhs != null) {
							return lhs.getTIME().compareTo(rhs.getTIME());
						}
						return -1;
					}
				});
				/*
				 * Calendar noonCalendar = Calendar.getInstance();
				 * noonCalendar.set(Calendar.HOUR_OF_DAY, 12);
				 * noonCalendar.set(Calendar.MINUTE, 0);
				 * noonCalendar.set(Calendar.SECOND, 0);
				 * noonCalendar.set(Calendar.MILLISECOND, 0); Calendar
				 * startCalendar = Calendar.getInstance(); Calendar stopCalendar
				 * = Calendar.getInstance();
				 */
				MapStatusUpdate msu = MapStatusUpdateFactory
						.newLatLngZoom(
								new LatLng(locationList.get(
										locationList.size() - 1).getLATITUDE(),
										locationList.get(
												locationList.size() - 1)
												.getLONGITUDE()), 18.0f);
				List<LatLng> points = new ArrayList<LatLng>();
				for (int i = 0; i < locationList.size(); i++) {
					LatLng point = new LatLng(
							locationList.get(i).getLATITUDE(), locationList
									.get(i).getLONGITUDE());
					points.add(point);
				}
				String title = "";
				String stopTime = "";
				if (locationList != null && locationList.size() > 0) {
					String str = locationList.get(0).getTIME();
					String sto = locationList.get(locationList.size() - 1)
							.getTIME();
					title = str.substring(str.indexOf(" ") + 1);
					stopTime = sto.substring(sto.indexOf(" ") + 1);
				}
				/*
				 * try { startCalendar.setTime(new
				 * SimpleDateFormat("HH:mm:ss",Locale.CHINA).parse(title));
				 * stopCalendar.setTime(new
				 * SimpleDateFormat("HH:mm:ss",Locale.CHINA).parse(stopTime)); }
				 * catch (ParseException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */
				int color = 0;
				if (flag % 4 == 0) {
					color = Color.RED;
				} else if (flag % 4 == 1) {
					color = Color.CYAN;
				} else if (flag % 4 == 2) {
					color = Color.BLUE;
				} else{
					color = Color.GREEN;
				} 
				OverlayOptions ooPolyline = new PolylineOptions().width(10)
						.color(color).points(points);
				mBaiduMap.addOverlay(ooPolyline);
				iconBitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.icon_st);
				circleBitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.icon_en);
				OverlayOptions textOptions = new MarkerOptions()
						.position(points.get(0)).icon(iconBitmap).title(title)
						.zIndex(1);
				OverlayOptions afterOption = new MarkerOptions()
						.position(points.get(locationList.size() - 1))
						.icon(circleBitmap).title(stopTime)
						.zIndex(locationList.size() - 1);
				mBaiduMap.addOverlay(afterOption);
				mBaiduMap.addOverlay(textOptions);
				mBaiduMap.setMapStatus(msu);
				mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
					private InfoWindow mInfoWindow;

					@Override
					public boolean onMarkerClick(Marker arg0) {
						// TODO Auto-generated method
						// stub
						View view = getLayoutInflater().inflate(
								R.layout.map_bubble, null);
						TextView tvDate = (TextView) view
								.findViewById(R.id.tvDate);
						tvDate.setText(arg0.getTitle());
						LatLng ll = arg0.getPosition();
						Point p = mBaiduMap.getProjection()
								.toScreenLocation(ll);
						p.y -= 87;
						LatLng llInfo = mBaiduMap.getProjection()
								.fromScreenLocation(p);
						mInfoWindow = new InfoWindow(view, llInfo, 0);
						mBaiduMap.showInfoWindow(mInfoWindow);
						view.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								mBaiduMap.hideInfoWindow();
							}
						});
						return true;
					}
				});

			}
			flag++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 2016/5/5
																				// 8:13:41

	/**
	 * 加载地图
	 */
	private void loadRoute(List<LocationInfoEntity> locationList) {
		try {
			// locationList = Loaction.getEntityList(new JSONArray(rate));
			if (locationList != null && locationList.size() > 0) {
				// MapStatusUpdate msu = MapStatusUpdateFactory
				// .newLatLng(new LatLng(locationList.get(
				// locationList.size() - 1).getLATITUDE(),
				// locationList.get(locationList.size() - 1)
				// .getLONGITUDE()));
				Collections.sort(locationList,
						new Comparator<LocationInfoEntity>() {
							@Override
							public int compare(LocationInfoEntity lhs,
									LocationInfoEntity rhs) {
								// TODO Auto-generated method stub
								if (null != lhs && null != rhs) {
									int result = -1;
									try {
										result = (int) (sdf
												.parse(lhs.getTime()).getTime() - sdf
												.parse(rhs.getTime()).getTime());
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									return result;
								}
								return -1;
							}
						});
				MapStatusUpdate msu = MapStatusUpdateFactory
						.newLatLngZoom(
								new LatLng(locationList.get(
										locationList.size() - 1).getLatitude(),
										locationList.get(
												locationList.size() - 1)
												.getLongitude()), 18.0f);
				// mBaiduMap.setMapStatus(msu);
				List<LatLng> points = new ArrayList<LatLng>();
				String title = "";
				String stopTime = "";
				for (int i = 0; i < locationList.size(); i++) {
					LatLng point = new LatLng(
							locationList.get(i).getLatitude(), locationList
									.get(i).getLongitude());
					points.add(point);
				}
				OverlayOptions mOptions, afterOptions;
				if (locationList != null && locationList.size() > 0) {
					String str = locationList.get(0).getTime();
					String sto = locationList.get(locationList.size() - 1)
							.getTime();
					title = str.substring(str.indexOf(" ") + 1);
					stopTime = sto.substring(sto.indexOf(" ") + 1);
				}

				/*
				 * if (locationList.size() >= 2 && locationList.size() < 10000)
				 * { List<LatLng> morningPoints = points .subList( 0,
				 * getMorningPointsIndexByPoinsList(locationList) + 1);
				 * List<LatLng> afternoonPoints = points .subList(
				 * getMorningPointsIndexByPoinsList(locationList),
				 * locationList.size()); if (morningPoints.size() >= 2 &&
				 * morningPoints.size() < 10000) { mOptions = new
				 * PolylineOptions() .width(10).zIndex(0) .color(Color.RED)
				 * .points(morningPoints); mBaiduMap.addOverlay(mOptions); } if
				 * (afternoonPoints.size() >= 2 && afternoonPoints.size() <
				 * 10000) { afterOptions = new PolylineOptions()
				 * .width(10).zIndex(0) .color(Color.BLUE)
				 * .points(afternoonPoints); mBaiduMap.addOverlay(afterOptions);
				 * } }
				 */
				int color = 0;
				if (flag % 4 == 0) {
					color = Color.RED;
				} else if (flag % 4 == 1) {
					color = Color.CYAN;
				} else if (flag % 4 == 2) {
					color = Color.BLUE;
				} else {
					color = Color.GREEN;
				} 
				OverlayOptions ooPolyline = new PolylineOptions().width(10)
						.color(color).points(points);
				mBaiduMap.addOverlay(ooPolyline);
				iconBitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.icon_st);
				circleBitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.icon_en);
				OverlayOptions textOptions = new MarkerOptions()
						.position(points.get(0)).icon(iconBitmap).title(title)
						.zIndex(1);
				OverlayOptions afterOption = new MarkerOptions()
						.position(points.get(locationList.size() - 1))
						.icon(circleBitmap).title(stopTime)
						.zIndex(locationList.size() - 1);
				mBaiduMap.addOverlay(afterOption);
				mBaiduMap.addOverlay(textOptions);

				mBaiduMap.setMapStatus(msu);

				mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
					private InfoWindow mInfoWindow;

					@Override
					public boolean onMarkerClick(Marker arg0) {
						// TODO Auto-generated method
						// stub
						View view = getLayoutInflater().inflate(
								R.layout.map_bubble, null);
						TextView tvDate = (TextView) view
								.findViewById(R.id.tvDate);
						tvDate.setText(arg0.getTitle());
						LatLng ll = arg0.getPosition();
						Point p = mBaiduMap.getProjection()
								.toScreenLocation(ll);
						p.y -= 87;
						LatLng llInfo = mBaiduMap.getProjection()
								.fromScreenLocation(p);
						mInfoWindow = new InfoWindow(view, llInfo, 0);
						mBaiduMap.showInfoWindow(mInfoWindow);
						view.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								mBaiduMap.hideInfoWindow();
							}
						});
						return true;
					}
				});
			}
			flag++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		if (null != mvSalers) {
			mvSalers.onPause();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		if (null != mvSalers) {
			mvSalers.onDestroy();
		}
	}

	@Override
	protected void onResume() {
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		super.onResume();
		if (null != mvSalers) {
			mvSalers.onResume();
		}
	}

	/**
	 * 获取中午12点以后第一个位置坐标的Index
	 * 
	 * @param pointList
	 * @return
	 */
	private int getMorningPointsIndexByPoinsList(
			List<LocationInfoEntity> pointList) {
		Calendar noonCalendar = Calendar.getInstance();
		noonCalendar.set(Calendar.HOUR_OF_DAY, 12);
		noonCalendar.set(Calendar.MINUTE, 0);
		noonCalendar.set(Calendar.SECOND, 0);
		noonCalendar.set(Calendar.MILLISECOND, 0);
		Calendar dataCalendar = Calendar.getInstance();
		int index = 0;

		if (pointList != null && pointList.size() > 0) {
			for (int i = 0; i < pointList.size(); i++) {
				try {
					dataCalendar.setTime(new SimpleDateFormat("HH:mm:ss")
							.parse(pointList
									.get(i)
									.getTime()
									.substring(
											pointList.get(i).getTime()
													.indexOf(" ") + 1)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (dataCalendar.get(Calendar.HOUR_OF_DAY) >= 12) {
					index = i;
					break;
				}
			}
		}
		return index;
	}

	// 过滤掉每秒距离大于33.3，米的点
	public List<Loaction> shaiXuan(List<Loaction> locas) {
		List<Loaction> nums = new ArrayList<Loaction>();
		int z=1;
		int j = 0;
		if (locas != null && locas.size() > 1) {
			nums.add(locas.get(0));
			for (int i = 1; i < locas.size(); i++) {
				String time = locas.get(j).getTIME()
						.substring(locas.get(j).getTIME().indexOf(" ") + 1);
				String time1 = locas.get(i).getTIME()
						.substring(locas.get(i).getTIME().indexOf(" ") + 1);
				String[] t1 = time.split(":");
				String[] t2 = time1.split(":");
				int c = Math.abs((Integer.parseInt(t1[0]) * 3600
						+ Integer.parseInt(t1[1]) * 60 + Integer
							.parseInt(t1[2]))
						- (Integer.parseInt(t2[0]) * 3600
								+ Integer.parseInt(t2[1]) * 60 + Integer
									.parseInt(t2[2])));
				double JL = DistanceUtil.getDistance(new LatLng(locas.get(j)
						.getLATITUDE(), locas.get(j).getLONGITUDE()),
						new LatLng(locas.get(i).getLATITUDE(), locas.get(i)
								.getLONGITUDE()));
				if (ActivityUtils.ISDEBUG) {
					ActivityUtils.print("--时间差--" + c + "\n" + "--距离--" + JL
							+ "\n/n", Environment.getExternalStorageDirectory()
							+ "/LocationText1" + ".txt");
				}
				if ((JL / (c > 0 ? c : 1)) > 33.3) {
					z++;
					j = i - z;
				} else {
					z=1;
					j = i;
					nums.add(locas.get(i));
				}
			}
		}
		return nums;
	}
	// 过滤掉每秒距离大于33.3，米的点
		public List<LocationInfoEntity> guoLv(List<LocationInfoEntity> locationList) {
			List<LocationInfoEntity> nums = new ArrayList<LocationInfoEntity>();
			int z=1;
			int j = 0;
			if (locationList != null && locationList.size() > 1) {
				nums.add(locationList.get(0));
				for (int i = 1; i < locationList.size(); i++) {
					String time = locationList.get(j).getTime()
							.substring(locationList.get(j).getTime().indexOf(" ") + 1);
					String time1 = locationList.get(i).getTime()
							.substring(locationList.get(i).getTime().indexOf(" ") + 1);
					String[] t1 = time.split(":");
					String[] t2 = time1.split(":");
					int c = Math.abs((Integer.parseInt(t1[0]) * 3600
							+ Integer.parseInt(t1[1]) * 60 + Integer
								.parseInt(t1[2]))
							- (Integer.parseInt(t2[0]) * 3600
									+ Integer.parseInt(t2[1]) * 60 + Integer
										.parseInt(t2[2])));
					double JL = DistanceUtil.getDistance(new LatLng(locationList.get(j)
							.getLatitude(), locationList.get(j).getLongitude()),
							new LatLng(locationList.get(i).getLatitude(), locationList.get(i)
									.getLongitude()));
					if (ActivityUtils.ISDEBUG) {
						ActivityUtils.print("--时间差--" + c + "\n" + "--距离--" + JL
								+ "\n/n", Environment.getExternalStorageDirectory()
								+ "/LocationText1" + ".txt");
					}
					if ((JL / (c > 0 ? c : 1)) > 33.3) {
						z++;
						j = i - z;
					} else {
						z=1;
						j = i;
						nums.add(locationList.get(i));
					}
				}
			}
			return nums;
		}
}
