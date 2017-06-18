package com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yhkj.yhsx.forestpolicemobileterminal.MyApplication;
import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.ParentActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.FocusOfWildAnimal;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.OptionEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.NetworkConnections;

import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import db.Database;
import db.OptionDao;

/**
 * 重点动物-查询
 * 
 * @author xingyimin
 * 
 */
public class AnimalInformationDetailActivity extends ParentActivity
		implements OnClickListener {

	private LinearLayout llAdd;
	private Button btnSearchContent, btnSearchNearby;
	private Spinner spSearch, spSearchContent;
	private ListView lvSearchResults;
	private ArrayList<FocusOfWildAnimal> larList;
	private static ArrayList<OptionEntity> paAdapter;
	private Context ct;
	private ProgressDialog dialog;



	@Override
	protected int layoutResID() {
		return R.layout.activity_animal_information_detail;
	}

	@Override
	protected void initView() {
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
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.activity_animal_information_detail);


		OptionDao dao = new OptionDao(ct);
		ArrayList<OptionEntity> options = dao
				.getOptionByFormMainId(Database.ProtectedAnimals.PROTECTED_ANIMALS_TABLE_ID);
//		if (options != null && options.size() > 0) {
		paAdapter = new ArrayList<OptionEntity>();
		paAdapter.add(0, new OptionEntity(0, "动物名称"));
		for (int i = 0; i < options.size(); i++) {
			switch (options.get(i).getCtrlValue()) {
				case 1:
					paAdapter.add(new OptionEntity(options.get(i)
							.getDictValue(), options.get(i).getDictName()));
					break;
			}
		}
		ActivityUtils.showSpinnerAdapter(ct, paAdapter, spSearchContent);

		llAdd.setOnClickListener(this);

		btnSearchContent.setOnClickListener(this);
		btnSearchNearby.setOnClickListener(this);

//		} else {
		Toast.makeText(ct, R.string.update_date_under_wifi, Toast.LENGTH_SHORT)
				.show();
		//finish();
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
			Intent it = new Intent(ct, AnimalsAddActivity.class);
			startActivity(it);
			break;
		case R.id.btnSearch:
			/*if(null!=dialog){
				dialog.dismiss();
			}
			btnSearchContent.setEnabled(false);
			if (NetUtil.init(ct).netState(ct)) {
				params = new AjaxParams();
				dialog = ProgressDialog.show(ct, "",
						getString(R.string.dialog_message_loading),false,true);
				params.put("DeviceSN", ActivityUtils.getDeviceID(ct));
				if (!spSearchContent.getSelectedView().getTag().toString().equals("")) {
					params.put("competent_departmentID", spSearchContent
							.getSelectedView().getTag().toString());
				}
				
				NetworkConnections.init(ct).callNetworkInterfaceByPost(
						ct,
						"Animal/GetAnimalListByParams",
						params,
						new MyAjaxCallback("Animal/GetAnimalListByParams",
								dialog) {
							@Override
							public void onSuccess(String t) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								System.out
										.println("-------------------动物条件查询返回值："
												+ t.toString());
								try {
									larList = ProtectedAnimalsDao.init(ct).getObject(t);
									if (larList == null) {
										Toast.makeText(
												ct,
												getString(R.string.toast_message_Abnormal_server),
												Toast.LENGTH_LONG).show();
										lvSearchResults
												.removeAllViewsInLayout();
									} else if (larList.size() == 0) {
										Toast.makeText(
												ct,
												getString(R.string.toast_message_no_data),
												Toast.LENGTH_LONG).show();
										lvSearchResults
												.removeAllViewsInLayout();
									} else {

										*//*
										 * Comparator<FocusOfWildAnimal>
										 * comparator = new
										 * Comparator<FocusOfWildAnimal>() {
										 * 
										 * @Override public int compare(
										 * FocusOfWildAnimal lhs,
										 * FocusOfWildAnimal rhs) { // TODO
										 * Auto-generated method stub Loaction l
										 * = ((ForestApplication)
										 * getApplication()).loca; if
										 * (ActivityUtils.getDistance(l,
										 * lhs.getLocationId()) > ActivityUtils
										 * .getDistance(l, rhs.getLocationId()))
										 * { return 1; } else if (ActivityUtils
										 * .getDistance(l, lhs.getLocationId())
										 * == ActivityUtils .getDistance(l,
										 * rhs.getLocationId())) { return 0; }
										 * else { return -1; } } };
										 * Collections.sort(larList,
										 * comparator);
										 *//*
										lvSearchResults
												.setAdapter(new AnimalAdapter());
										lvSearchResults
												.setOnItemClickListener(new OnItemClickListener() {

													@Override
													public void onItemClick(
															AdapterView<?> arg0,
															View arg1,
															int arg2, long arg3) {
														// TODO
														// Auto-generated
														// method stub

														ListView listView = (ListView) arg0;
														listView.getItemAtPosition(arg2);
														Bundle bundle = new Bundle();
														bundle.putSerializable(
																"FOWA",
																larList.get(arg2));
														Intent it = new Intent(
																ct,
																AnimalsAddActivity.class);
														it.putExtra(
																"FOWA",
																larList.get(arg2));

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
				if(null!=dialog){
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
		case R.id.btnSearchNearby:
			if(null!=dialog){
				dialog.dismiss();
			}
			btnSearchNearby.setEnabled(false);
			/*if (NetUtil.init(ct).netState(ct)) {
				params = new AjaxParams();
				dialog = ProgressDialog.show(ct, "",
						getString(R.string.dialog_message_loading),false,true);
				params.put("DeviceSN", ActivityUtils.getDeviceID(ct));
				NetworkConnections.init(ct).callNetworkInterfaceByPost(ct,
						"Animal/GetAllAnimalList", params,
						new MyAjaxCallback("GetAllAnimalList", dialog) {
							@Override
							public void onSuccess(String t) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								System.out
										.println("-------------------动物附近查询返回值："
												+ t.toString());
								try {
									larList = ProtectedAnimalsDao.init(ct).getObject(t);
									if (larList == null) {
										Toast.makeText(
												ct,
												getString(R.string.toast_message_Abnormal_server),
												Toast.LENGTH_LONG).show();
										lvSearchResults
												.removeAllViewsInLayout();
									} else if (larList.size() == 0) {
										Toast.makeText(
												ct,
												getString(R.string.toast_message_no_data),
												Toast.LENGTH_LONG).show();
										lvSearchResults
												.removeAllViewsInLayout();
									} else {
										// Comparator<FocusOfWildAnimal>
										// comparator
										// = new
										// Comparator<FocusOfWildAnimal>()
										// {
										//
										// @Override
										// public int compare(
										// FocusOfWildAnimal lhs,
										// FocusOfWildAnimal rhs) {
										// // TODO Auto-generated method
										// stub
										// Loaction l =
										// ((ForestApplication)
										// getApplication()).loca;
										// if
										// (ActivityUtils.getDistance(l,
										// lhs.getLocationId()) >
										// ActivityUtils
										// .getDistance(l,
										// rhs.getLocationId())) {
										// return 1;
										// } else if (ActivityUtils
										// .getDistance(l,
										// lhs.getLocationId()) ==
										// ActivityUtils
										// .getDistance(l,
										// rhs.getLocationId())) {
										// return 0;
										// } else {
										// return -1;
										// }
										// }
										// };
										// Collections.sort(larList,
										// comparator);
										lvSearchResults
												.setAdapter(new AnimalAdapter());
										lvSearchResults
												.setOnItemClickListener(new OnItemClickListener() {

													@Override
													public void onItemClick(
															AdapterView<?> arg0,
															View arg1,
															int arg2, long arg3) {
														// TODO
														// Auto-generated
														// method stub

														ListView listView = (ListView) arg0;
														listView.getItemAtPosition(arg2);
														Bundle bundle = new Bundle();
														bundle.putSerializable(
																"FOWA",
																larList.get(arg2));
														Intent it = new Intent(
																ct,
																AnimalsAddActivity.class);
														it.putExtra(
																"FOWA",
																larList.get(arg2));

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
				if(null!=dialog){
					dialog.dismiss();
				}
				btnSearchNearby.setEnabled(true);
			} else {
				if (null != dialog) {
					dialog.dismiss();
				}
				btnSearchNearby.setEnabled(true);
				Toast.makeText(ct, "网络异常，请稍候再试", Toast.LENGTH_LONG).show();
			}*/
			break;
		default:
			break;
		}
	}

	private class AnimalAdapter extends BaseAdapter {

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater listContainer; // 视图容器工厂
			listContainer = LayoutInflater.from(ct); // 创建视图容器工厂并设置上下文
			if (convertView == null) {
				convertView = listContainer.inflate(
						R.layout.animal_information, null);
				convertView.setTag(larList.get(position).getFowaId());
			}
			TextView tvTitle = (TextView) convertView
					.findViewById(R.id.tv_animal_imformationTitle);
			// tvTitle.setText(paAdapter.get(Integer.parseInt(larList
			// .get(position).getAnimalName())));
			tvTitle.setText(ActivityUtils.getDictNameByDictValue(paAdapter,
					Integer.parseInt(larList.get(position).getAnimalName())));
			TextView tvContent = (TextView) convertView
					.findViewById(R.id.tv_animal_imformationContent);
			tvContent.setText(larList.get(position).getDrAndPn());
			TextView tvDistance = (TextView) convertView
					.findViewById(R.id.tvDistance);
			double distance = ActivityUtils.getDistance(
					((MyApplication) getApplication()).loca,
					larList.get(position).getLocationId());
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
					.findViewById(R.id.iv_animal_imformationLocation);
			if (larList.get(position).getLocationId() != null) {
				ivLocation.setImageResource(R.drawable.ic_dialog_map);
				ivLocation.setOnClickListener(new OnClickListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent it = null;
						try {

							// it =
							// Intent.getIntent("intent://map/marker?location="
							// + larList.get(position).getLocationId()
							// .getLATITUDE()
							// + ","
							// + larList.get(position).getLocationId()
							// .getLONGITUDE()
							// + "&title="
							// + paAdapter.get(Integer.parseInt(larList
							// .get(position).getAnimalName()))
							// + "&content="
							// + larList.get(position).getDrAndPn()
							// +
							// "&src＝forest#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
							it = Intent.getIntent("intent://map/marker?location="
									+ larList.get(position).getLocationId()
											.getLATITUDE()
									+ ","
									+ larList.get(position).getLocationId()
											.getLONGITUDE()
									+ "&title="
									+ ActivityUtils.getDictNameByDictValue(
											paAdapter,
											Integer.parseInt(larList.get(
													position).getAnimalName()))
									+ "&content="
									+ larList.get(position).getDrAndPn()
									+ "&src＝forest#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (ActivityUtils.isInstalled(ct, "com.baidu.BaiduMap")) {
							startActivity(it);
						} else {
							Toast.makeText(ct, "您没有安装百度地图应用程序!请安装!",
									Toast.LENGTH_LONG).show();
						}
					}
				});
			} else {
				ivLocation.setImageResource(R.drawable.ic_menu_mylocation);
			}

			ImageView ivAccessory = (ImageView) convertView
					.findViewById(R.id.iv_animal_imformationFile);
			if (larList.get(position).getFowaAccessory() != null
					&& larList.get(position).getFowaAccessory().size() > 0
					&& larList.get(position).getFowaAccessory().get(0)
							.getAccessoryPath() != null
					&& !larList.get(position).getFowaAccessory().get(0)
							.getAccessoryPath().equals("")) {
				String type = larList
						.get(position)
						.getFowaAccessory()
						.get(0)
						.getAccessoryPath()
						.substring(
								larList.get(position).getFowaAccessory().get(0)
										.getAccessoryPath().lastIndexOf(".") + 1);
				if (type.equals("mp4") || type.equals("3gp")
						|| type.equals("avi") || type.equals("rmvb")
						|| type.equals("wmv")) {
					ivAccessory
							.setImageResource(R.drawable.presence_video_online);
				} else if (type.equals("jpg") || type.equals("png")) {
					NetworkConnections.init(ct).setImageUrl(
							ivAccessory,
							larList.get(position).getFowaAccessory().get(0)
									.getAccessoryPath(), 60, 60,
							R.drawable.loading, R.drawable.load_fali);
				} else if (type.equals("3gpp") || type.equals("amr")
						|| type.equals("ogg") || type.equals("pcm")) {
					ivAccessory
							.setImageResource(R.drawable.presence_audio_online);
				}else{
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
			return larList.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return larList.size();
		}
	}

}
