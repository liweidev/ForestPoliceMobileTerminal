package com.yhkj.yhsx.forestpolicemobileterminal.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhkj.yhsx.forestpolicemobileterminal.MyApplication;
import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.adapter.MainFragmentPagerAdapter;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.HandlingCaseFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.info_collection.InformationCollectionFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.IntelligenceOfficeFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.fragment.PatrolManageFragment;
import com.yhkj.yhsx.forestpolicemobileterminal.services.Upload30DatasService;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ToastUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import db.AlarmResultDao;
import db.AlarmingDao;
import db.AlarmingWorkDao;
import db.BreedDao;
import db.FirePatrolInspectionDao;
import db.ForestPatrolWorkDao;
import db.ForestPotectionDao;
import db.ForestSurveyDao;
import db.ForestryKeyIndustriesDao;
import db.IllegalUseOfFireRegisterDao;
import db.InterviewDao;
import db.ItelligenceDao;
import db.KeyPopulationDao;
import db.KeyPositionsDao;
import db.KeyProtectedPlantDao;
import db.LawEnforcementInspectionDao;
import db.LinQingDao;
import db.LocationInfoDao;
import db.ManagedObjectDao;
import db.MountConditionDao;
import db.NoteDao;
import db.PatrolRegisterDao;
import db.PatrolRouteManagementDao;
import db.ProtectedAnimalsDao;
import db.PublicSecurityManagementDao;
import db.QuestionedSituationDao;
import db.ReceiveAlarmDao;
import db.ReceiveAndDisposeAlarmDao;
import db.RegistrationAreaFireDao;
import db.SheConditionDao;
import db.SocialStatisticsDao;
import db.TheWorkingLogDao;
import db.TransientRegisterDao;
import db.WatchTowerDao;
import db.WoodCuttingDao;
import db.WoodlandMiningDao;

/**
 * 主页菜单Activity
 */
public final class MainActivity extends ParentActivity implements View.OnClickListener{


    //退出登录图标
    @BindView(R.id.ivAlarm)
    ImageView ivAlarm;
    //同步图标
    @BindView(R.id.ivNews)
    ImageView ivNews;
    //需同步数据数量
    @BindView(R.id.tvCount)
    TextView tvCount;
    //顶部导航条
    @BindView(R.id.mytitle)
    RelativeLayout mytitle;
    //滚动值班信息条
    @BindView(R.id.mk)
    com.yhkj.yhsx.forestpolicemobileterminal.widget.MarqueeTextView mk;
    //主菜单
    @BindView(R.id.pagerMain)
    ViewPager pagerMain;

    //巡防管理图标
    @BindView(R.id.imgg1)
    ImageView imgg1;
    //巡防管理文字
    @BindView(R.id.textt1)
    TextView textt1;
    //巡防管理布局
    @BindView(R.id.img_weixin)
    LinearLayout imgWeixin;
    //信息采集图标
    @BindView(R.id.imgg2)
    ImageView imgg2;
    //信息采集文字
    @BindView(R.id.textt2)
    TextView textt2;
    //信息采集布局
    @BindView(R.id.img_address)
    LinearLayout imgAddress;
    //执法办案图标
    @BindView(R.id.imgg3)
    ImageView imgg3;
    //执法办案文字
    @BindView(R.id.textt3)
    TextView textt3;
    //执法办案布局
    @BindView(R.id.img_friends)
    LinearLayout imgFriends;
    //智慧办公图标
    @BindView(R.id.imgg4)
    ImageView imgg4;
    //智慧办公文字
    @BindView(R.id.textt4)
    TextView textt4;
    //智慧办公布局
    @BindView(R.id.img_settings)
    LinearLayout imgSettings;
    //底部导航菜单
    @BindView(R.id.main_bottom)
    LinearLayout mainBottom;

    private List<Fragment> pageViews;// 页面

    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        imgWeixin.setOnClickListener(this);
        imgAddress.setOnClickListener(this);
        imgFriends.setOnClickListener(this);
        imgSettings.setOnClickListener(this);
        addFragmentPager();
        setSelector(0);
    }

    public class UpdateBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // submitLocationBefore30Datas();

            int count = getUnSynchronizationCount();
            if (count > 0 && count < 99) {
//                tvCounts.setText(count + "");
//                tvCounts.setVisibility(View.VISIBLE);
                tvCount.setText(count + "");
                tvCount.setVisibility(View.VISIBLE);
                ivNews.setEnabled(true);
            } else if (count >= 99) {
                tvCount.setText(99 + "+");
             //   tvCounts.setText(99 + "+");
            } else {
               // tvCounts.setVisibility(View.GONE);
                tvCount.setVisibility(View.GONE);
                ivNews.setEnabled(true);
            }

        }

    }
    public UpdateBroadcastReceiver receiver;
    public static final String UPDATE_DATABASE = "update_database";
    @Override
    protected void initData() {
        MyApplication.getApplication(this).startLocation();

        receiver = new UpdateBroadcastReceiver();
        IntentFilter filter = new IntentFilter(UPDATE_DATABASE);
        registerReceiver(receiver, filter);

        Intent uploadIntent = new Intent(this, Upload30DatasService.class);
        startService(uploadIntent);

        //DownloadApkService.startActionFoo(this, null, null);

    }

    private int getUnSynchronizationCount() {
        int count = 0;
        ReceiveAlarmDao raDao = new ReceiveAlarmDao(mContext);
        count += raDao.getCount();
        ProtectedAnimalsDao paDao = new ProtectedAnimalsDao(mContext);
        count += paDao.getCount();
        KeyProtectedPlantDao kppDao = new KeyProtectedPlantDao(mContext);
        count += kppDao.getCount();
        KeyPositionsDao kpDao = new KeyPositionsDao(mContext);
        count += kpDao.getCount();
        ForestPatrolWorkDao fpwDao = new ForestPatrolWorkDao(mContext);
        count += fpwDao.getCount();
        FirePatrolInspectionDao fpiDao = new FirePatrolInspectionDao(mContext);
        count += fpiDao.getCount();
        LawEnforcementInspectionDao leiDao = new LawEnforcementInspectionDao(mContext);
        count += leiDao.getCount();
        ForestSurveyDao fsDao = new ForestSurveyDao(mContext);
        count += fsDao.getCount();
        AlarmingWorkDao awDao = new AlarmingWorkDao(mContext);
        count += awDao.getCount();
        ReceiveAndDisposeAlarmDao radaDao = new ReceiveAndDisposeAlarmDao(mContext);
        count += radaDao.getCount();
        TransientRegisterDao trDao = new TransientRegisterDao(mContext);
        count += trDao.getCount();
        WatchTowerDao wtDao = new WatchTowerDao(mContext);
        count += wtDao.getCount();
        RegistrationAreaFireDao rafDao = new RegistrationAreaFireDao(mContext);
        count += rafDao.getCount();
        IllegalUseOfFireRegisterDao ifrDao = new IllegalUseOfFireRegisterDao(mContext);
        count += ifrDao.getCount();
        PublicSecurityManagementDao psmDao = new PublicSecurityManagementDao(mContext);
        count += psmDao.getCount();
        LocationInfoDao lld = new LocationInfoDao(mContext);
        count += lld.getCount();
        NoteDao nd = new NoteDao(mContext);
        count += nd.getCount();
        KeyPopulationDao keyDao = new KeyPopulationDao(mContext);
        count += keyDao.getCount();
        PatrolRouteManagementDao patrolDao = new PatrolRouteManagementDao(mContext);
        count += patrolDao.getCount();
        TheWorkingLogDao logDao = new TheWorkingLogDao(mContext);
        count += logDao.getCount();
        BreedDao bDao = new BreedDao(mContext);
        count += bDao.getCount();
        ForestPotectionDao fpDao = new ForestPotectionDao(mContext);
        count += fpDao.getCount();
        ForestryKeyIndustriesDao fkiDao = new ForestryKeyIndustriesDao(mContext);
        count += fkiDao.getCount();
        InterviewDao iDao = new InterviewDao(mContext);
        count += iDao.getCount();
        ManagedObjectDao moDao = new ManagedObjectDao(mContext);
        count += moDao.getCount();
        QuestionedSituationDao qsDao = new QuestionedSituationDao(mContext);
        count += qsDao.getCount();
        ItelligenceDao telDao = new ItelligenceDao(mContext);
        count += telDao.getCount();
        WoodCuttingDao wcDao = new WoodCuttingDao(mContext);
        count += wcDao.getCount();
        WoodlandMiningDao wmDao = new WoodlandMiningDao(mContext);
        count += wmDao.getCount();
        SocialStatisticsDao ssDao = new SocialStatisticsDao(mContext);
        count += ssDao.getCount();
        // 三情——林情
        LinQingDao lqd = new LinQingDao(mContext);
        count += lqd.getCount();
        // 三情——山情
        MountConditionDao mcd = new MountConditionDao(mContext);
        count += mcd.getCount();
        // 三情——社情
        SheConditionDao scd = new SheConditionDao(mContext);
        count += scd.getCount();
        // 巡逻登记轨迹坐标点
        // if (flag) {
        PatrolRegisterDao prDao = new PatrolRegisterDao(mContext);
        count += prDao.getCount();
        // 执法办案——处理结果
        AlarmResultDao ard = new AlarmResultDao(mContext);
        count += ard.getCount();
        // 执法办案——接警信息
        AlarmingDao ad = new AlarmingDao(mContext);
        count += ad.getSyncCount("等待接警审批");
        // RouteInfoDao riDao = new RouteInfoDao(mContext);
        // count += riDao.getCount();
        // }
        return count;
    }

    /**
     * 添加主页菜单Fragment
     */
    private void addFragmentPager(){
        pageViews = new ArrayList<Fragment>();
        for (int i = 0; i < 4; i++) {
            Fragment fragment = null;
            switch (i) {
                case 0:
                    fragment = new PatrolManageFragment();
                    break;
                case 1:
                    fragment = new InformationCollectionFragment();
                    break;
                case 2:
                    fragment = new HandlingCaseFragment();
                    break;
                case 3:
                    fragment = new IntelligenceOfficeFragment();
                    break;
                default:
                    break;
            }
            pageViews.add(fragment);
        }
        pagerMain.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(),pageViews));
        pagerMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelector(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /***
     * 选中效果
     */
    public void setSelector(int id) {
        switch (id) {
            case 0:
                textt1.setTextColor(android.graphics.Color.parseColor("#2482d3"));
                imgg1.setImageDrawable(getResources()
                        .getDrawable(R.mipmap.xunfgl));

                textt2.setTextColor(android.graphics.Color.parseColor("#A1A1A1"));
                imgg2.setImageDrawable(getResources()
                        .getDrawable(R.mipmap.xinxih));
                textt3.setTextColor(android.graphics.Color.parseColor("#A1A1A1"));
                imgg3.setImageDrawable(getResources().getDrawable(R.mipmap.zfba1));
                textt4.setTextColor(android.graphics.Color.parseColor("#A1A1A1"));
                imgg4.setImageDrawable(getResources().getDrawable(R.mipmap.zhbg1));
                break;
            case 1:
                textt2.setTextColor(android.graphics.Color.parseColor("#2482d3"));
                imgg2.setImageDrawable(getResources().getDrawable(R.mipmap.xinxi));
                textt1.setTextColor(android.graphics.Color.parseColor("#A1A1A1"));
                imgg1.setImageDrawable(getResources().getDrawable(
                        R.mipmap.xunfang1));
                textt3.setTextColor(android.graphics.Color.parseColor("#A1A1A1"));
                imgg3.setImageDrawable(getResources().getDrawable(R.mipmap.zfba1));
                textt4.setTextColor(android.graphics.Color.parseColor("#A1A1A1"));
                imgg4.setImageDrawable(getResources().getDrawable(R.mipmap.zhbg1));
                break;
            case 2:
                textt2.setTextColor(android.graphics.Color.parseColor("#A1A1A1"));
                imgg2.setImageDrawable(getResources()
                        .getDrawable(R.mipmap.xinxih));
                textt1.setTextColor(android.graphics.Color.parseColor("#A1A1A1"));
                imgg1.setImageDrawable(getResources().getDrawable(
                        R.mipmap.xunfang1));

                textt3.setTextColor(android.graphics.Color.parseColor("#2482d3"));
                imgg3.setImageDrawable(getResources().getDrawable(R.mipmap.zfba));
                textt4.setTextColor(android.graphics.Color.parseColor("#A1A1A1"));
                imgg4.setImageDrawable(getResources().getDrawable(R.mipmap.zhbg1));
                break;
            case 3:
                textt2.setTextColor(android.graphics.Color.parseColor("#A1A1A1"));
                imgg2.setImageDrawable(getResources()
                        .getDrawable(R.mipmap.xinxih));
                textt1.setTextColor(android.graphics.Color.parseColor("#A1A1A1"));
                imgg1.setImageDrawable(getResources().getDrawable(
                        R.mipmap.xunfang1));
                textt3.setTextColor(android.graphics.Color.parseColor("#A1A1A1"));
                imgg3.setImageDrawable(getResources().getDrawable(R.mipmap.zfba1));
                textt4.setTextColor(android.graphics.Color.parseColor("#2482d3"));
                imgg4.setImageDrawable(getResources().getDrawable(R.mipmap.zhbg));
                break;
            default:
                break;
        }
        pagerMain.setCurrentItem(id);
        // }
        // }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_weixin:
                setSelector(0);
                break;
            case R.id.img_address:
                setSelector(1);
                break;
            case R.id.img_friends:
                setSelector(2);
                break;
            case R.id.img_settings:
                setSelector(3);
                break;
        }
    }

    private long startTime = 0;
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        if (tempTime - startTime > 2000) { //时间差2秒内退出
            ToastUtility.showToast("再点击一次退出");
            startTime = System.currentTimeMillis();
        } else {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
