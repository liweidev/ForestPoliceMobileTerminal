package com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.yhkj.yhsx.forestpolicemobileterminal.MyApplication;
import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.ParentActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.KeyProtectedPlants;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.OptionEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.NetworkConnections;
import com.yhkj.yhsx.forestpolicemobileterminal.widget.NetworkedCacheableImageView;

import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import db.Database;
import db.OptionDao;

/**
 * 重点植物----查询
 * 
 * @author xingyimin
 * 
 */
public class PlantProtectDetailActivity extends ParentActivity implements
		OnClickListener {
	private LinearLayout llAdd;
	private Button btnSearchContent, btnSearchNearby;
	private Spinner spSearch, spSearchContent;
	private ListView lvSearchResults;
	private ArrayList<KeyProtectedPlants> kppList;
	private static ArrayList<OptionEntity> tpnAdapter;
	private Context ct;
	private ProgressDialog dialog;

	@Override
	protected int layoutResID() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		return R.layout.activity_animal_information_detail;
	}

	@Override
	protected void initView() {
		TextView tvtitle = (TextView) findViewById(R.id.tvAdd);
		tvtitle.setText(getString(R.string.add_plant));
		llAdd = (LinearLayout) findViewById(R.id.llAdd);
		spSearchContent = (Spinner) findViewById(R.id.spSearchContent);
		btnSearchContent = (Button) findViewById(R.id.btnSearch);
		btnSearchNearby = (Button) findViewById(R.id.btnSearchNearby);
		spSearch = (Spinner) findViewById(R.id.spSearch);
		lvSearchResults = (ListView) findViewById(R.id.lvSearchResults);
	}

	@Override
	protected void initData() {
		ct = this;
		// 去除title

		//setContentView(R.layout.activity_animal_information_detail);

		OptionDao dao = new OptionDao(ct);
		ArrayList<OptionEntity> options = dao
				.getOptionByFormMainId(Database.KeyProtectedPlant.KEY_PROTECTED_PLANT_TABLE_ID);
//		if (options.size() > 0 && options != null) {
			tpnAdapter = new ArrayList<OptionEntity>();
			tpnAdapter.add(0, new OptionEntity(0, "植物名称"));
			for (int i = 0; i < options.size(); i++) {
				switch (options.get(i).getCtrlValue()) {
					case 4:
						tpnAdapter.add(new OptionEntity(options.get(i)
								.getDictValue(), options.get(i).getDictName()));
						break;
				}
			}
			ActivityUtils.showSpinnerAdapter(ct, tpnAdapter, spSearchContent);
			llAdd.setOnClickListener(this);
			btnSearchContent.setOnClickListener(this);
			btnSearchNearby.setOnClickListener(this);
//		} else {
			/*Toast.makeText(ct, R.string.update_date_under_wifi,
					Toast.LENGTH_SHORT).show();
			finish();*/
//		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//AjaxParams params;
		switch (v.getId()) {
		case R.id.llAdd:
			if (ActivityUtils.judgeSimState(ct) == 1) {
				return;
			}
			//ToastUtility.showToast("添加");
			Intent it = new Intent(ct, PlantsProtectAddActivity.class);
			startActivity(it);
			break;
		case R.id.btnSearch://搜索
			/*btnSearchContent.setEnabled(false);
			final ProgressDialog dialog = ProgressDialog.show(ct, "",
					getString(R.string.dialog_message_loading),false,true);
			if (NetUtil.init(ct).netState(ct)) {
				params = new AjaxParams();
				
				JSONObject joAll=new JSONObject();
				
				params.put("DeviceSN", ActivityUtils.getDeviceID(ct));
				if (spSearchContent.getSelectedItemPosition() != 0) {
					params.put("plant_namesID", spSearchContent
							.getSelectedView().getTag().toString());
				}
				JSONObject jo=new JSONObject();
				try {
					jo.put("longitude", MyApplication.getApplication(ct).loca.getLONGITUDE());
					jo.put("latitude", MyApplication.getApplication(ct).loca.getLATITUDE());
					jo.put("elevation", MyApplication.getApplication(ct).loca.getELEVATION());
					jo.put("Time", MyApplication.getApplication(ct).loca.getTIME());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				params.put("location_id", jo.toString());
				// AjaxParams param = new AjaxParams();
				// param.put("elevation",
				// ((ForestApplication) getApplication()).loca.getELEVATION()
				// + "");
				// param.put("latitude",
				// ((ForestApplication) getApplication()).loca.getLATITUDE()
				// + "");
				// param.put("longitude",
				// ((ForestApplication) getApplication()).loca.getLONGITUDE()
				// + "");
				// params.put("location_id", param.getParamString());
				System.out.println(params.toString());
				NetworkConnections.init(ct).callNetworkInterfaceByPost(ct,
						"Plant/GetPlantListByParams", params,
						new MyAjaxCallback("GetPlantListByParams", dialog) {
							@Override
							public void onSuccess(String t) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								System.out
										.println("---------------------植物条件查询返回值："
												+ t.toString());
								try {
									kppList = KeyProtectedPlantDao.init(ct)
											.getObject(t);
									if (kppList == null) {
										Toast.makeText(
												ct,
												getString(R.string.toast_message_Abnormal_server),
												Toast.LENGTH_LONG).show();
										kppList = new ArrayList<KeyProtectedPlants>();
										PlantAdapter adapter = new PlantAdapter();
										lvSearchResults.setAdapter(adapter);
										// lvSearchResults.invalidate();
										// lvSearchResults
										// .removeAllViewsInLayout();
									} else if (kppList.size() == 0) {
										Toast.makeText(
												ct,
												getString(R.string.toast_message_no_data),
												Toast.LENGTH_LONG).show();
										// lvSearchResults
										// .removeAllViewsInLayout();
										kppList = new ArrayList<KeyProtectedPlants>();
										PlantAdapter adapter = new PlantAdapter();
										lvSearchResults.setAdapter(adapter);
									} else {
										// Comparator<KeyProtectedPlants>
										// comparator = new
										// Comparator<KeyProtectedPlants>() {
										//
										// @Override
										// public int compare(
										// KeyProtectedPlants lhs,
										// KeyProtectedPlants rhs) {
										// // TODO Auto-generated method stub
										// Loaction l = ((ForestApplication)
										// getApplication()).loca;
										// if (ActivityUtils.getDistance(l,
										// lhs.getLocation()) > ActivityUtils
										// .getDistance(l,
										// rhs.getLocation())) {
										// return 1;
										// } else if (ActivityUtils
										// .getDistance(l,
										// lhs.getLocation()) == ActivityUtils
										// .getDistance(l,
										// rhs.getLocation())) {
										// return 0;
										// } else {
										// return -1;
										// }
										// }
										// };
										// Collections.sort(kppList,
										// comparator);
										lvSearchResults
												.setAdapter(new PlantAdapter());
										lvSearchResults
												.setOnItemClickListener(new OnItemClickListener() {

													@Override
													public void onItemClick(
															AdapterView<?> arg0,
															View arg1,
															int arg2, long arg3) {
														// TODO Auto-generated
														// method stub
														// Toast.makeText(ct,
														// "arg2       :      "+arg2,
														// Toast.LENGTH_LONG).show();
														ListView listView = (ListView) arg0;
														listView.getItemAtPosition(arg2);
														Bundle bundle = new Bundle();
														bundle.putSerializable(
																"KPP",
																kppList.get(arg2));
														Intent it = new Intent(
																ct,
																PlantsProtectAddActivity.class);
														it.putExtras(bundle);
														startActivity(it);
													}
												});
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
				if (null != dialog) {
					dialog.dismiss();
				}
				btnSearchContent.setEnabled(true);
			} else {
				if (null != dialog) {
					dialog.dismiss();
				}
				btnSearchContent.setEnabled(true);
				Toast.makeText(ct, "网络异常，请稍候再试", Toast.LENGTH_LONG).show();
			}*/
			break;
		case R.id.btnSearchNearby://搜索附近
			/*btnSearchNearby.setEnabled(false);
			final ProgressDialog dialog1 = ProgressDialog.show(ct, "",
					getString(R.string.dialog_message_loading));
			if (NetUtil.init(ct).netState(ct)) {
				params = new AjaxParams();
				
				params.put("DeviceSN", ActivityUtils.getDeviceID(ct));

				NetworkConnections.init(ct).callNetworkInterfaceByPost(ct,
						"Plant/GetAllPlantList", params,
						new MyAjaxCallback("GetAllPlantList", dialog1) {
							
							@Override
							public void onSuccess(String t) {
								// TODO Auto-generated method stub
								dialog1.dismiss();
								System.out
										.println("---------------------植物附近查询返回值："
												+ t.toString());
								try {
									kppList = KeyProtectedPlantDao.init(ct)
											.getObject(t);
									if (kppList == null) {
										Toast.makeText(
												ct,
												getString(R.string.toast_message_Abnormal_server),
												Toast.LENGTH_LONG).show();
										// lvSearchResults
										// .removeAllViewsInLayout();
									} else if (kppList.size() == 0) {
										Toast.makeText(
												ct,
												getString(R.string.toast_message_no_data),
												Toast.LENGTH_LONG).show();
										lvSearchResults
												.removeAllViewsInLayout();
									} else {
										// Comparator<KeyProtectedPlants>
										// comparator = new
										// Comparator<KeyProtectedPlants>() {
										//
										// @Override
										// public int compare(
										// KeyProtectedPlants lhs,
										// KeyProtectedPlants rhs) {
										// // TODO Auto-generated method stub
										// Loaction l = ((ForestApplication)
										// getApplication()).loca;
										// if (ActivityUtils.getDistance(l,
										// lhs.getLocation()) > ActivityUtils
										// .getDistance(l,
										// rhs.getLocation())) {
										// return 1;
										// } else if (ActivityUtils
										// .getDistance(l,
										// lhs.getLocation()) == ActivityUtils
										// .getDistance(l,
										// rhs.getLocation())) {
										// return 0;
										// } else {
										// return -1;
										// }
										// }
										// };
										// Collections.sort(kppList,
										// comparator);
										lvSearchResults
												.setAdapter(new PlantAdapter());
										lvSearchResults
												.setOnItemClickListener(new OnItemClickListener() {

													@Override
													public void onItemClick(
															AdapterView<?> arg0,
															View arg1,
															int arg2, long arg3) {
														// TODO Auto-generated
														// method stub
														// Toast.makeText(ct,
														// "arg2       :      "+arg2,
														// Toast.LENGTH_LONG).show();
														ListView listView = (ListView) arg0;
														listView.getItemAtPosition(arg2);
														Bundle bundle = new Bundle();
														bundle.putSerializable(
																"KPP",
																kppList.get(arg2));
														Intent it = new Intent(
																ct,
																PlantsProtectAddActivity.class);
														it.putExtras(bundle);
														startActivity(it);
													}
												});
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
				if (null != dialog1) {
					dialog1.dismiss();
				}
				btnSearchNearby.setEnabled(true);
			} else {
				if (null != dialog1) {
					dialog1.dismiss();
				}
				btnSearchNearby.setEnabled(true);
				Toast.makeText(ct,
						getString(R.string.toast_message_netException),
						Toast.LENGTH_LONG).show();
			}*/
			break;
		default:
			break;
		}
	}

	private class PlantAdapter extends BaseAdapter {

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			Log.i("liupeng", "plant position    :     " + position);
			LayoutInflater listContainer; // 视图容器工厂
			listContainer = LayoutInflater.from(ct); // 创建视图容器工厂并设置上下文
			if (convertView == null) {
				convertView = listContainer.inflate(R.layout.plant_protect,
						null);
				convertView.setTag(kppList.get(position).getPkkId());
			}
			TextView tvTitle = (TextView) convertView
					.findViewById(R.id.tv_proteted_plant_title);

			// tvTitle.setText(tpnAdapter.get(Integer.parseInt(kppList.get(
			// position).getPlantNames())));
			tvTitle.setText(ActivityUtils.getDictNameByDictValue(tpnAdapter,
					Integer.parseInt(kppList.get(position).getPlantNames())));

			TextView tvContent = (TextView) convertView
					.findViewById(R.id.tv_proteted_plant_Content);
			/*
			 * tvContent.setText(kppList.get(position)
			 * .getAccountabilityUnit());
			 */
			tvContent.setText(kppList.get(position).getComOrSLNumber());
			TextView tvDistance = (TextView) convertView
					.findViewById(R.id.tv_proteted_plant_Distance);
			double distance = ActivityUtils.getDistance(
					((MyApplication) getApplication()).loca,
					kppList.get(position).getLocation());
			DecimalFormat decimalf = new DecimalFormat("0.00");
			if (distance != Double.MAX_VALUE) {
				String str = "";
				if (distance < 50) {
					str = "< 50 米";
				} else if (distance >= 50 && distance < 1000) {
					str = decimalf.format(distance) + " 米";
				} else {
					str = decimalf.format(distance / 1000) + " 公里";
				}
				tvDistance.setText("距离 " + str);
				tvDistance.setVisibility(View.VISIBLE);
			} else {
				tvDistance.setVisibility(View.GONE);
			}
			ImageView ivLocation = (ImageView) convertView
					.findViewById(R.id.iv_proteted_plant_location);
			if (kppList.get(position).getLocation() != null) {
				ivLocation.setImageResource(R.drawable.ic_dialog_map);
				ivLocation.setOnClickListener(new OnClickListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent it = null;
						try {
							it = Intent.getIntent("intent://map/marker?location="
									+ kppList.get(position).getLocation()
											.getLATITUDE()
									+ ","
									+ kppList.get(position).getLocation()
											.getLONGITUDE()
									+ "&title="
									+ ActivityUtils.getDictNameByDictValue(
											tpnAdapter,
											Integer.parseInt(kppList.get(
													position).getPlantNames()))
									+ "&content="
									+ kppList.get(position).getComOrSLNumber()
									+ "&src＝forest#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch
							// block
							e.printStackTrace();
						}
						startActivity(it);
					}
				});
			} else {
				ivLocation.setImageResource(R.drawable.ic_menu_mylocation);
			}

			NetworkedCacheableImageView ivAccessory = (NetworkedCacheableImageView) convertView
					.findViewById(R.id.iv_proteted_plant);
			if (kppList.get(position).getKppAccessory() != null
					&& kppList.get(position).getKppAccessory().size() > 0
					&& kppList.get(position).getKppAccessory().get(0)
							.getAccessoryPath() != null
					&& !kppList.get(position).getKppAccessory().get(0)
							.getAccessoryPath().equals("")) {
				String type = kppList
						.get(position)
						.getKppAccessory()
						.get(0)
						.getAccessoryPath()
						.substring(
								kppList.get(position).getKppAccessory().get(0)
										.getAccessoryPath().lastIndexOf(".") + 1);
				if (type.equals("mp4") || type.equals("3gp")
						|| type.equals("avi") || type.equals("rmvb")
						|| type.equals("wmv")) {
					ivAccessory
							.setImageResource(R.drawable.presence_video_online);
				} else if (type.equals("jpg") || type.equals("png")) {
					NetworkConnections.init(ct).setImageUrl(
							ivAccessory,
							kppList.get(position).getKppAccessory().get(0)
									.getAccessoryPath(), 60, 60,
							R.drawable.upload,
							android.R.drawable.ic_menu_report_image);
				} else if (type.equals("3gpp") || type.equals("amr")
						|| type.equals("ogg") || type.equals("pcm")) {
					ivAccessory
							.setImageResource(R.drawable.presence_audio_online);
				}else {
					ivAccessory.setImageResource(R.drawable.ic_launcher);
				}
			} else {
				ivAccessory.setImageResource(R.drawable.ic_launcher);
			}
			return convertView;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return kppList.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return kppList == null ? 0 : kppList.size();
		}
	}
}
