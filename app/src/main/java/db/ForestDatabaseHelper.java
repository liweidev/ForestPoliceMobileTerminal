package db;

import db.Database.AlarmResult;
import db.Database.AlarmSpinnerInfo;
import db.Database.AlarmingHistoryInfo;
import db.Database.AlarmingWork;
import db.Database.Attachment;
import db.Database.Breed;
import db.Database.FirePatrolInspection;
import db.Database.ForestPatrolWork;
import db.Database.ForestPotection;
import db.Database.ForestSurveys;
import db.Database.ForestryConditions;
import db.Database.ForestryKeyIndustries;
import db.Database.IllegalUseOfFireRegister;
import db.Database.Itelligence;
import db.Database.Interview;
import db.Database.KeyPopulation;
import db.Database.KeyPositions;
import db.Database.KeyProtectedPlant;
import db.Database.LawEnforcementInspection;
import db.Database.Location;
import db.Database.LocationInfo;
import db.Database.ManagedObject;
import db.Database.MountConditions;
import db.Database.Note;
import db.Database.NoticeTime;
import db.Database.Option;
import db.Database.PatrolRegisterLocation;
import db.Database.PatrolRouteManagement;
import db.Database.ProtectedAnimals;
import db.Database.PublicSecurityManagement;
import db.Database.QuestionedSituation;
import db.Database.ReceiveAlarm;
import db.Database.ReceiveAndDisposeAlarm;
import db.Database.Receive_DisposeAlarm;
import db.Database.RegistrationAreaFire;
import db.Database.RouteInfo;
import db.Database.SheConditions;
import db.Database.SocialStatistics;
import db.Database.TheWorkingLog;
import db.Database.TransientRegister;
import db.Database.WatchTower;
import db.Database.WoodCutting;
import db.Database.WoodlandMining;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 本地数据库结构
 * 
 * @author Administrator
 * 
 */
public class ForestDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "forest.db";
	private static final int DATABASE_VERSION = 1;
	private Context context;

	private static SQLiteDatabase mDb;

	public synchronized SQLiteDatabase getInstence() {

		if (null == mDb || (mDb != null && !mDb.isOpen())) {
			mDb = getReadableDatabase();
//			mDb = getWritableDatabase();
		}
		return mDb;

	}

	public static synchronized void closeDB() {
		if (null != mDb && mDb.isOpen()) {
			mDb.close();
		}
	}

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public ForestDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		mDb = arg0;

		/*
		 * 创建通知时间表
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ NoticeTime.NOTICE_TABLE_NAME + " ("
				+ NoticeTime.NOTICE_TABLE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ NoticeTime.NOTICE_NEWS_ID + " INTEGER , "
				+ NoticeTime.NOTICE_TYPE + " TEXT , " + NoticeTime.NOTICE_TIME
				+ " TEXT ,"+ NoticeTime.NOTICE_CONTENT + " TEXT );");

		/*
		 * 创建重点部位表
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ KeyPositions.KEY_POSITION_TABLE_NAME + " ("
				+ KeyPositions.KEY_POSITION_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ KeyPositions.KEY_POSITION_SERVER_ID + " INTEGER , "
				+ KeyPositions.YEAR + " TEXT , " + KeyPositions.NAME
				+ " TEXT , " + KeyPositions.SITING + " TEXT , "
				+ KeyPositions.PERSON_IN_CHARGE + " TEXT , "
				+ KeyPositions.HEAD_OF_SECURITY + " TEXT , "
				+ KeyPositions.EMPLOYEES + " INTEGER NOT NULL , "
				+ KeyPositions.CONTACT + " TEXT , " + KeyPositions.AREA
				+ " DOUBLE , " + KeyPositions.TRAFFIC + " TEXT , "
				+ KeyPositions.LAT_LON_ALTITUDE + " TEXT , "
				+ KeyPositions.SCOPE_AND_NATURE + " TEXT , "
				+ KeyPositions.FOUR_CASES + " TEXT , "
				+ KeyPositions.FS_SURROUNDING + " TEXT , "
				+ KeyPositions.REASON_TUBE + " TEXT , "
				+ KeyPositions.LOCATION_ID + " INTEGER , " + KeyPositions.NOTE
				+ " TEXT );");

		/*
		 * 创建重点动物表
		 */

		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ ProtectedAnimals.PROTECTED_ANIMALS_TABLE_NAME + " ("
				+ ProtectedAnimals.PROTECTED_ANIMALS_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ ProtectedAnimals.PROTECTED_ANIMALS_SERVER_ID + " INTEGER , "
				+ ProtectedAnimals.COMPETENT_DEPARMENT + " TEXT , "
				+ ProtectedAnimals.DR_AND_PN + " TEXT , "
				+ ProtectedAnimals.PROTECTION_LEVEL + " TEXT , "
				+ ProtectedAnimals.PROTECT_FILE_NUM + " TEXT , "
				+ ProtectedAnimals.PROTECT_THE_WAY + " TEXT , "
				+ ProtectedAnimals.MAPO_PERSONNEL + " TEXT , "
				+ ProtectedAnimals.CONTACT + " TEXT , "
				+ ProtectedAnimals.ATTACHMENT + " TEXT , "
				+ ProtectedAnimals.ACCOUNTABILITY_UNIT + " TEXT , "
				+ ProtectedAnimals.LOCATION_ID + " INTEGER , "
				+ ProtectedAnimals.NOTE + " TEXT );");

		/*
		 * 重点植物
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ KeyProtectedPlant.KEY_PROTECTED_PLANT_TABLE_NAME + "("
				+ KeyProtectedPlant.KEY_PROTECTED_PLANT_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ KeyProtectedPlant.KEY_PROTECTED_PLANT_SERVER_ID
				+ " INTEGER , " + KeyProtectedPlant.COMPETENT_DEPARMENT
				+ " TEXT , " + KeyProtectedPlant.DR_AND_PN + " TEXT , "
				+ KeyProtectedPlant.PLANT_NAME + " TEXT , "
				+ KeyProtectedPlant.TREE_AGE + " DOUBLE , "
				+ KeyProtectedPlant.PROTECTION_LEVEL + " TEXT , "
				+ KeyProtectedPlant.STATUS + " TEXT , "
				+ KeyProtectedPlant.FILE_NO + " TEXT , "
				+ KeyProtectedPlant.MAPO_PERSONNEL + " TEXT , "
				+ KeyProtectedPlant.CONTACT + " TEXT , "
				+ KeyProtectedPlant.LOCATION_ID + " INTEGER , "
				+ KeyProtectedPlant.ATTACHMENT + " TEXT , "
				+ KeyProtectedPlant.NOTE + " TEXT );");

		/*
		 * 林情调查表
		 */

		mDb.execSQL("CREATE TABLE IF NOT EXISTS " + ForestSurveys.FOREST_NAME
				+ " (" + ForestSurveys.FOREST_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ ForestSurveys.FOREST_SERVER_ID + " INTEGER , "
				+ ForestSurveys.FOREST_UNIT + " TEXT , "
				+ ForestSurveys.FOREST_NUMBER + " TEXT , "
				+ ForestSurveys.FOREST_AREA + " INTEGER,"
				+ ForestSurveys.LAND_TYPES + " TEXT , "
				+ ForestSurveys.FOREST_OWNERSHIP + " TEXT , "
				+ ForestSurveys.LAND_OWNERSHIP + " TEXT , "
				+ ForestSurveys.LOCATION_ID + " INTEGER , "
				+ ForestSurveys.ATTACHMENT + " TEXT , "
				+ ForestSurveys.FOREST_TYPES + " TEXT , " + ForestSurveys.NOTE
				+ " TEXT );");

		/*
		 * 受警登记
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ ReceiveAlarm.RECEIVE_ALARM_TABLE_NAME + "("
				+ ReceiveAlarm.RECEIVE_ALARM_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ ReceiveAlarm.RECEIVINT_ALARM_STAFF + " TEXT , "
				+ ReceiveAlarm.RECEIVINT_ALARM_DATE + " TEXT , "
				+ ReceiveAlarm.ALARM_MODE + " TEXT , "
				+ ReceiveAlarm.ALARM_CONTENT + " TEXT , "
				+ ReceiveAlarm.ALARM_OPINION_LEADER + " TEXT , "
				+ ReceiveAlarm.ALARM_NAME + " TEXT , " + ReceiveAlarm.ALARM_SEX
				+ " TEXT , " + ReceiveAlarm.ALARM_AGE + " INTEGER , "
				+ ReceiveAlarm.ALARM_NATIONALITY + " TEXT , "
				+ ReceiveAlarm.ALARM_PHONE + " TEXT , "
				+ ReceiveAlarm.ALARM_ADD + " TEXT , "
				+ ReceiveAlarm.ALARM_IDCARD + " TEXT , "
				+ ReceiveAlarm.INSTRUCTION_TIME + " TEXT , "
				+ ReceiveAlarm.TIME_TO_REACH + " TEXT , "
				+ ReceiveAlarm.ALARMING_COMMENT + " TEXT , "
				+ ReceiveAlarm.ALARMING_OPINION_LEADER + " TEXT , "
				+ ReceiveAlarm.PROCESSING_RESULT + " TEXT , "
				+ ReceiveAlarm.TRANSFER + " TEXT , "
				+ ReceiveAlarm.RECEIVE_TIME + " TEXT , "
				+ ReceiveAlarm.CROWN_CASE_NO + " TEXT ,"
				+ ReceiveAlarm.ADMINISTRATIVE_CASE_NO + " TEXT ,"
				+ ReceiveAlarm.LOCATION_ID + " INTEGER , "
				+ ReceiveAlarm.ATTACHMENT + " TEXT );");

		/*
		 * 处警信息
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ AlarmingWork.ALARMING_WORK_TABLE_NAME + "("
				+ AlarmingWork.ALARMING_WORK_ID// 表名
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ AlarmingWork.ALARM_ID + " INTEGER , "// 案件编号
				+ AlarmingWork.STATE_ID + " INTEGER , "// 审批状态ID
				+ AlarmingWork.INSTRUCTION_TIME + " TEXT , "// 接到出警指令时间
				+ AlarmingWork.TIME_TO_REACH + " TEXT , "// 到达现场时间
				+ AlarmingWork.CRIMES_NAME + " TEXT , "// 违法犯罪人基本情况 姓名
				+ AlarmingWork.CRIMES_SEX + " TEXT , "// null 性别
				+ AlarmingWork.CRIMES_AGE + " INTEGER , " // 年龄
				+ AlarmingWork.CRIMES_EDUCATION + " INTEGER , "// 文化程度
				+ AlarmingWork.CRIMES_ADD_OR_UNIT + " TEXT , "// 住址
				+ AlarmingWork.CRIMES_UNIT + " TEXT , "// 工作单位
				+ AlarmingWork.CRIMES_UNIT_NAME + " TEXT , "// 单位名称
				+ AlarmingWork.CRIMES_UNIT_ADD + " TEXT , "// 地址
				+ AlarmingWork.LEGAL_PERSON + " TEXT , "// 法人
				+ AlarmingWork.DUTY + " INTEGER , "// 职务
				+ AlarmingWork.PIB_RIEF_CASE + " TEXT , "// 初查简要情况（
				+ AlarmingWork.AP_ADVICE_ID + " INTEGER , "// not null 处警民警意见
				+ AlarmingWork.APPROVALPEOPLEID + " INTEGER , "// 审批人
				+ AlarmingWork.REMARK + " TEXT , "// 备注
				+ AlarmingWork.ELEVATION + " TEXT , "// 备注
				+ AlarmingWork.LATITUDE + " TEXT , "// 备注
				+ AlarmingWork.LONGITUDE + " TEXT , "// 备注
				+ AlarmingWork.TIME + " TEXT , "// 备注
				+ AlarmingWork.ATTACHMENT + " TEXT );");// null 附件

		/*
		 * 接警及处警工作
		 */

		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ ReceiveAndDisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME + "("
				+ ReceiveAndDisposeAlarm.RECEIVE_DISPOSE_ALARM_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ ReceiveAndDisposeAlarm.LOCATION_ID + " INTEGER , "
				+ ReceiveAndDisposeAlarm.ATTACHMENT + " TEXT , "
				+ ReceiveAndDisposeAlarm.NOTE + " TEXT , "
				+ ReceiveAndDisposeAlarm.RECEIVE_ALARM_PERSON + " TEXT , "
				+ ReceiveAndDisposeAlarm.RECEIVE_ALARM_TIME + " TEXT , "
				+ ReceiveAndDisposeAlarm.RECEIVE_ALARM_MODEL + " TEXT , "
				+ ReceiveAndDisposeAlarm.GIVE_ALARM_NAME + " TEXT ,"
				+ ReceiveAndDisposeAlarm.GIVE_ALARM_AGE + " INTEGER , "
				+ ReceiveAndDisposeAlarm.GIVE_ALARM_SEX + " TEXT , "
				+ ReceiveAndDisposeAlarm.GIVE_ALARM_CONTACT + " TEXT , "
				+ ReceiveAndDisposeAlarm.GIVE_ALARM_ID_CARD + " TEXT , "
				+ ReceiveAndDisposeAlarm.GIVE_ALARM_ADDRESS + " TEXT , "
				+ ReceiveAndDisposeAlarm.GIVE_ALARM_CONTENT + " TEXT , "
				+ ReceiveAndDisposeAlarm.OPINION_LEADER + " TEXT , "
				+ ReceiveAndDisposeAlarm.RECEIVE_ORDER_TIME + " TEXT , "
				+ ReceiveAndDisposeAlarm.ALARM_TIME + " TEXT , "
				+ ReceiveAndDisposeAlarm.ARRIVE_SITE_TIME + " TEXT , "
				+ ReceiveAndDisposeAlarm.ALARM_PERSON + " TEXT , "
				+ ReceiveAndDisposeAlarm.PARTS_NAME + " TEXT , "
				+ ReceiveAndDisposeAlarm.PARTS_AGE + " INTEGER , "
				+ ReceiveAndDisposeAlarm.PARTS_SEX + " TEXT , "
				+ ReceiveAndDisposeAlarm.PARTS_CULTURE + " TEXT , "
				+ ReceiveAndDisposeAlarm.PARTS_WORK_UNIT + " TEXT , "
				+ ReceiveAndDisposeAlarm.PARTS_HOME_ADDRESS + " TEXT , "
				+ ReceiveAndDisposeAlarm.PARTS_UNIT_NAME + " TEXT ,"
				+ ReceiveAndDisposeAlarm.PARTS_UNIT_ADDRESS + " TEXT , "
				+ ReceiveAndDisposeAlarm.PARTS_POST + " TEXT , "
				+ ReceiveAndDisposeAlarm.PARTS_LAW_PERSON + " TEXT , "
				+ ReceiveAndDisposeAlarm.FIRST_INSPECT + " TEXT , "
				+ ReceiveAndDisposeAlarm.INSPECT_END_TIME + " TEXT , "
				+ ReceiveAndDisposeAlarm.ALARM_POLICE_IDEA + " TEXT , "
				+ ReceiveAndDisposeAlarm.ALARM_IDEA_WRITE_TIME + " TEXT , "
				+ ReceiveAndDisposeAlarm.ALARM_AND_IDEA + " TEXT , "
				+ ReceiveAndDisposeAlarm.ALARM_RESPONSIBLE_IDEA + " TEXT , "
				+ ReceiveAndDisposeAlarm.RESPONSIBLE_IDEA_TIME + " TEXT , "
				+ ReceiveAndDisposeAlarm.ALARM_RESULT + " TEXT , "
				+ ReceiveAndDisposeAlarm.ACCEPT_UNIT + " TEXT , "
				+ ReceiveAndDisposeAlarm.ACCEPT_TIME + " TEXT , "
				+ ReceiveAndDisposeAlarm.CRIMINAL_CASE_CODE + " TEXT , "
				+ ReceiveAndDisposeAlarm.ADMINISTRATIVE_CASE_CODE + " TEXT , "
				+ ReceiveAndDisposeAlarm.PRINCIPAL_INSTRUCT + " TEXT , "
				+ ReceiveAndDisposeAlarm.PRINCIPAL_INSTRUCT_TIME + " TEXT , "
				+ ReceiveAndDisposeAlarm.ALARM_NATION + " TEXT );");

		/*
		 * 接警及处警工作
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME + "("
				+ Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ Receive_DisposeAlarm.ALARM_ID + " INTEGER ,"//报警信息ID
				+ Receive_DisposeAlarm.CASE_NUMBER + " TEXT ,"//编号
				+ Receive_DisposeAlarm.ALARM_TIME + " TEXT ,"
				+ Receive_DisposeAlarm.ALARM_WAY_ID + " TEXT ,"//接警方式
				+ Receive_DisposeAlarm.PEOPLE_POLICE_ID + " INTEGER ,"//接警民警的ID
				+ Receive_DisposeAlarm.PEOPLE_POLICE_NAME + " TEXT ,"//接警民警
				+ Receive_DisposeAlarm.TO_ALARM_NAME + " TEXT ,"
				+ Receive_DisposeAlarm.TO_ALARM_SEX + " TEXT ,"
				+ Receive_DisposeAlarm.TO_ALARM_AGE + " TEXT ,"
				+ Receive_DisposeAlarm.ADDRESS + " TEXT ,"
				+ Receive_DisposeAlarm.NATION_ID + " TEXT ,"
				+ Receive_DisposeAlarm.CONTACT + " TEXT ," //联系方式
				+ Receive_DisposeAlarm.CARD_NUMBER + " TEXT ,"//身份证号
				+ Receive_DisposeAlarm.TO_ALARM_MESSAGE + " TEXT ,"
				+ Receive_DisposeAlarm.APPROVAL_PEOPLE_ID + " INTEGER ,"//审批人ID
				+ Receive_DisposeAlarm.HOST_POLICE_ID + " INTEGER ,"//主办民警ID
				+ Receive_DisposeAlarm.COPOLICE_ID + " INTEGER ,"//协办民警ID
				+ Receive_DisposeAlarm.STATE_ID + " INTEGER ,"//审批状态id
				+ Receive_DisposeAlarm.ALARM_STATE + " TEXT ,"//审批状态
				+ Receive_DisposeAlarm.OPINION + " TEXT );");//审批意见

		/*
		 * 林区巡逻工作
		 */

		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ ForestPatrolWork.FOREST_PATROL_WORK_TABLE_NAME + "("
				+ ForestPatrolWork.FOREST_PATROL_WORK_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ ForestPatrolWork.BEGIN_DATE + " TEXT , "
				+ ForestPatrolWork.END_DATE + " TEXT , "
				+ ForestPatrolWork.PATROL_POLICE + " TEXT , "
				+ ForestPatrolWork.PATROL_ROUTE + " TEXT , "
				+ ForestPatrolWork.PATROL_CHRONICLE + " DOUBLE , "
				+ ForestPatrolWork.ANALYSIS_OF_DISPOSAL + " TEXT , "
				+ ForestPatrolWork.SHIFT_LEADERSHIP_OPINION + " TEXT , "
				+ ForestPatrolWork.LOCATION_ID + " INTEGER , "
				+ ForestPatrolWork.ATTACHMENT + " TEXT , "
				+ ForestPatrolWork.NOTE + " TEXT );");

		/*
		 * 林区治安防控巡逻检查记录--防火检查
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ FirePatrolInspection.FIRE_PATROL_INSPECTION_TABLE_NAME + "("
				+ FirePatrolInspection.FIRE_PATROL_INSPECTION_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ FirePatrolInspection.UNIT + " TEXT , "
				+ FirePatrolInspection.PC_TIME + " TEXT , "
				+ FirePatrolInspection.PC_ROUTE + " TEXT , "
				+ FirePatrolInspection.P_INSPECTORS + " TEXT , "
				+ FirePatrolInspection.PC_NO + " TEXT , "
				+ FirePatrolInspection.PC_CONTENT + " TEXT , "
				+ FirePatrolInspection.P_INSPECTION + " TEXT , "
				+ FirePatrolInspection.TBC_SIGNATURE + " TEXT , "
				+ FirePatrolInspection.TBC_PHONE + " TEXT , "
				+ FirePatrolInspection.LOCATION_ID + " INTEGER , "
				+ FirePatrolInspection.ACCESSORY + " TEXT , "
				+ FirePatrolInspection.NOTE + " TEXT );");

		/*
		 * 林区治安防控巡逻检查记录---执法检查
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_TABLE_NAME
				+ "("
				+ LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ LawEnforcementInspection.LE_UNIT + " TEXT , "
				+ LawEnforcementInspection.LE_TIME + " TEXT , "
				+ LawEnforcementInspection.LE_VEHICLE + " TEXT , "
				+ LawEnforcementInspection.LE_INSPECTORS + " TEXT , "
				+ LawEnforcementInspection.LE_NO + " TEXT , "
				+ LawEnforcementInspection.le_signature_bool + " Text , "
				+ LawEnforcementInspection.LE_CONTENT + " TEXT , "
				+ LawEnforcementInspection.LE_SITUATION + " TEXT , "
				+ LawEnforcementInspection.LE_SIGNATURE + " TEXT , "
				+ LawEnforcementInspection.LE_PHONE + " TEXT , "
				+ LawEnforcementInspection.ATTACHMENT + " TEXT , "
				+ LawEnforcementInspection.NOTE + " TEXT , "
				+ LawEnforcementInspection.LOCATION_ID + " INTEGER );");
		/*
		 * 辖区暂住人口登记
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TransientRegister.TRANSIENT_REGISTER_TABLE_NAME + "("
				+ TransientRegister.TRANSIENT_REGISTER_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ TransientRegister.ATTACHMENT + " TEXT , "
				+ TransientRegister.NOTE + " TEXT , "
				+ TransientRegister.TRANSIENT_REGISTER_TIME + " TEXT , "
				+ TransientRegister.TRANSIENT_REGISTER_NAME + " TEXT ,"
				+ TransientRegister.TRANSIENT_REGISTER_SEX + " TEXT , "
				+ TransientRegister.TRANSIENT_REGISTER_BIRTHDAY + " TEXT , "
				+ TransientRegister.TRANSIENT_REGISTER_ADDRESS + " TEXT , "
				+ TransientRegister.TRANSIENT_REGISTER_WORK + " TEXT , "
				+ TransientRegister.TRANSIENT_REGISTER_RESIDENCE + " TEXT , "
				+ TransientRegister.TRANSIENT_CONTACT_INFORMATION + " TEXT , "
				+ TransientRegister.TRANSIENT_ID_CARD + " TEXT , "
				+ TransientRegister.TRANSIENT_ARRIVE_TIME + " TEXT , "
				+ TransientRegister.TRANSIENT_LEAVE_TIME + " TEXT , "
				+ TransientRegister.LOCATION_ID + " INTEGER );");

		/*
		 * 辖区瞭望塔登记表
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ WatchTower.WATCH_TOWER_TABLE_NAME + "("
				+ WatchTower.WATCH_TOWER_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ WatchTower.ATTACHMENT + " TEXT , " + WatchTower.NOTE
				+ " TEXT ,  " + WatchTower.BASE_STATION_NAME + " TEXT , "
				+ WatchTower.SPECIFIC_LOCATION + " TEXT , "
				+ WatchTower.COORDINATES + " TEXT , " + WatchTower.ALTITUDE
				+ " TEXT , " + WatchTower.WATCH_TOWER_NORMS + " TEXT , "
				+ WatchTower.TRANSPORT_MODE + " TEXT , "
				+ WatchTower.BUILD_TIME + " TEXT , " + WatchTower.LOCATION_ID
				+ " INTEGER );");

		/*
		 * 派出所治安管理检查记录表--治安管理
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_TABLE_NAME
				+ "(" + PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ PublicSecurityManagement.ATTACHMENT + " TEXT , "
				+ PublicSecurityManagement.NOTE + " TEXT ,"
				+ PublicSecurityManagement.PSM_BUSINESS_PRACTICE + " TEXT ,"
				+ PublicSecurityManagement.PSM_BUSINESS_PROJECT + " TEXT , "
				+ PublicSecurityManagement.PSM_CHECK_THE_TIME + " TEXT , "
				+ PublicSecurityManagement.PSM_CONTENT_AND_PROBLEMS
				+ " TEXT , " + PublicSecurityManagement.PSM_HEAD + " TEXT , "
				+ PublicSecurityManagement.PSM_IMSPECTORS + " TEXT , "
				+ PublicSecurityManagement.le_signature_bool + " TEXT , "
				+ PublicSecurityManagement.PSM_LEGAL_REPRESENTATIVE
				+ " TEXT , " + PublicSecurityManagement.PSM_OWNER_OR_HEAD
				+ " TEXT , "
				+ PublicSecurityManagement.PSM_RECTIFICATION_OPINIONS
				+ " TEXT , " + PublicSecurityManagement.PSM_SIGNATRUE_DATE
				+ " TEXT , " + PublicSecurityManagement.PSM_UNITADD
				+ " TEXT , " + PublicSecurityManagement.PSM_UNITNAME
				+ " TEXT , " + PublicSecurityManagement.LOCATION_ID
				+ " INTEGER); ");

		/*
		 * 发生火情登记-----------防火工作
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ RegistrationAreaFire.REGISTRATION_AREA_FIRE_TABLE_NAME + "("
				+ RegistrationAreaFire.REGISTRATION_AREA_FIRE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ RegistrationAreaFire.ATTACHMENT + " TEXT , "
				+ RegistrationAreaFire.NOTE + " TEXT ,"
				+ RegistrationAreaFire.BURNED_AREA + " TEXT , "
				+ RegistrationAreaFire.EF_TIME + " TEXT , "
				+ RegistrationAreaFire.LOCATION_ID + " INTEGER , "
				+ RegistrationAreaFire.RTSF_TIME + " TEXT , "
				+ RegistrationAreaFire.TFO_PLACE + " TEXT );");
		/*
		 * 违法用火行为人------防火工作
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_TABLE_NAME
				+ "(" + IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ IllegalUseOfFireRegister.ATTACHMENT + " TEXT , "
				+ IllegalUseOfFireRegister.NOTE + " TEXT ,"
				+ IllegalUseOfFireRegister.LOCATION_ID + " INTEGER , "
				+ IllegalUseOfFireRegister.LUOF_TIME + " TEXT , "
				+ IllegalUseOfFireRegister.ORGANIZED_POLICE + " TEXT , "
				+ IllegalUseOfFireRegister.PENALTIES_TIME + " TEXT , "
				+ IllegalUseOfFireRegister.PROCESSING_RESULTS + " TEXT , "
				+ IllegalUseOfFireRegister.VIOLATOR + " TEXT , "
				+ IllegalUseOfFireRegister.VIOLATOR_ADD + " TEXT , "
				+ IllegalUseOfFireRegister.VIOLATOR_ID_CARD + " TEXT );");

		/*
		 * 路线管理
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TABLE_NAME
				+ "(" + PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
				+ PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_NAME
				+ " TEXT , "
				+ PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_ROUTE
				+ " TEXT , "
				+ PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TRAVEL_ADVICE
				+ " INTEGER , "
				+ PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TYPE
				+ " INTEGER , "
				+ PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_WEATHER
				+ " INTEGER ,"
				+ PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_DISTANCE
				+ " DOUBLE);");

		/**
		 * 工作日志
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TheWorkingLog.THE_WORKING_LOG_TABLE_NAME + "("
				+ TheWorkingLog.THE_WORKING_LOG_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
				+ TheWorkingLog.THE_WORKING_LOG_DATE + " TEXT , "
				+ TheWorkingLog.POLICE_ON_DUTY + " TEXT , "
				+ TheWorkingLog.CLASS_LEADERS + " TEXT , "
				+ TheWorkingLog.THE_MAIN_WORK_AND_IMPORTANT_EVENTS + " TEXT , "
				+ TheWorkingLog.WEATHER + " INTEGER );");

		/*
		 * 消息公告
		 * 
		 * arg0.execSQL("CREATE TABLE IF NOT EXISTS " + News.NEWS_TABLE_NAME +
		 * "(" + News.NEWS_ID + "INTEGER PRIMARY KEY AUTOINCEREMENT NOT NULL ,"
		 * + News.NEWS_TITLE + " TEXT ," + News.NEWS_CONTENT + "TEXT ," +
		 * News.NEWS_FLAG_READ + " TEXT ," + News.NEWS_ATTACHMENT + " TEXT );");
		 */
		/*
		 * 附件表
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ Attachment.ATTACHMENT_TABLE_NAME + "( "
				+ Attachment.ATTACHMENT_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ Attachment.TABLE_ID + " TEXT , " + Attachment.ROW_ID
				+ " INTEGER , " + Attachment.FILE_PATH + " TEXT , "
				+ Attachment.FILE_TYPE + " TEXT );");

		/*
		 * 定位表
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ Location.LOCATION_TABLE_NAME + "( " + Location.LOCATION_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ Location.LONGITUDE + " DOUBLE , " + Location.LATITUDE
				+ " DOUBLE , " + Location.ELEVATION + " DOUBLE , "
				+ Location.TIME + " TEXT );");

		/*
		 * 选择项表
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS " + Option.OPTION_TABLE_NAME
				+ "(" + Option.OPTION_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ Option.OPTION_SERVER_ID + " INTEGER , " + Option.FORM_MAIN_ID
				+ " INTEGER , " + Option.CTRL_VALUE + " INTEGER , "
				+ Option.DICT_NAME + " TEXT , " + Option.DICT_VALUE
				+ " INTEGER , " + Option.DICT_ORDER + " INTEGER , "
				+ Option.REMARK + " TEXT , " + Option.RELATION_DICT_LIST
				+ " TEXT , " + Option.CREATE_TIME + " TEXT );");

		/**
		 * 巡逻轨迹
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ LocationInfo.LOCATION_INFO_TABLE_NAME + "( "
				+ LocationInfo.INFO_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ LocationInfo.USER_ID + " INTEGER , " + LocationInfo.TIME
				+ " TEXT ," + LocationInfo.LONGITUDE + " DOUBLE , "
				+ LocationInfo.LATITUDE + " DOUBLE , " + LocationInfo.LOCATIONTYPE + " TEXT , " + LocationInfo.ELEVATION
				+ " DOUBLE );");

		mDb.execSQL("CREATE TABLE IF NOT EXISTS " + Note.NOTE_TABLE_NAME + "( "
				+ Note.NOTE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ Note.USER_ID + " INTEGER , " + Note.CONTENT + " TEXT );");

		/**
		 * 辖区重点人口情况登记表
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ KeyPopulation.KEY_POPULATION_TABLE_NAME + "("
				+ KeyPopulation.KEY_POPULATION_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ KeyPopulation.ATTACHMENT + " TEXT , " + KeyPopulation.NOTE
				+ " TEXT , " + KeyPopulation.KEY_ADDRESS + " TEXT , "
				+ KeyPopulation.KEY_BIRTHDAY + " TEXT , "
				+ KeyPopulation.KEY_FILE_CODE + " TEXT , "
				+ KeyPopulation.KEY_MANAGE_CAUSE + " TEXT , "
				+ KeyPopulation.KEY_MANAGE_TIME + " TEXT , "
				+ KeyPopulation.KEY_NAME + " TEXT , "
				+ KeyPopulation.KEY_NICKNAME + " TEXT , "
				+ KeyPopulation.KEY_REPEAL_TIME + " TEXT , "
				+ KeyPopulation.KEY_SEX + " TEXT , " + KeyPopulation.KEY_TIME
				+ " TEXT , " + KeyPopulation.LOCATION_ID + " INTEGER );");

		/**
		 * 基础台账-养殖情况
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS " + Breed.BREED_TABLE_NAME
				+ "(" + Breed.BREED_TABLE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ Breed.BREED_ID + " INTEGER , " + Breed.ACQUISITIONTIME
				+ " TEXT , " + Breed.BREEDCOUNT + " TEXT , " + Breed.BREEDMODE
				+ " TEXT , " + Breed.BREEDNAME + " TEXT , " + Breed.BREEDTYPE
				+ " TEXT , " + Breed.UNITNAME + " TEXT , " + Breed.REMARK
				+ " TEXT , " + Breed.ATTACHMENT + " TEXT , "
				+ Breed.LOCATION_ID + " INTEGER );");

		/**
		 * 基础台账-情报工作
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ Itelligence.ITELLIGENCE_TABLE_NAME + "("
				+ Itelligence.ITELLIGENCE_TABLE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ Itelligence.ACQUISITIONTIME + " TEXT , "
				+ Itelligence.ACQUISITIONPOLICE + " TEXT , "
				+ Itelligence.INFORMATIONSOURCE + " TEXT , "
				+ Itelligence.INFORMATIONTYPE + " TEXT , "
				+ Itelligence.INFORMATIONABSTRACT + " TEXT , "
				+ Itelligence.REPORTEDUNIT + " TEXT , "
				+ Itelligence.REPORTEDTIME + " TEXT , " + Itelligence.FEEDBACK
				+ " TEXT , " + Itelligence.ATTACHMENT + " TEXT , "
				+ Itelligence.ITELLIGENCE_ID + " INTEGER );");

		/**
		 * 基础台账-木材采伐场（点）登记
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ WoodCutting.WOODCUTTING_TABLE_NAME + "("
				+ WoodCutting.WOODCUTTING_TABLE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ WoodCutting.CUTTINGUNIT + " TEXT , "
				+ WoodCutting.CUTTINGLOCATION + " TEXT , "
				+ WoodCutting.CUTTINGCARDID + " TEXT , "
				+ WoodCutting.ALLOWCOUNT + " TEXT , "
				+ WoodCutting.CUTTINGSPECIES + " TEXT , "
				+ WoodCutting.CUTTINGMODE + " TEXT , "
				+ WoodCutting.CUTTINGCOUNT + " TEXT , "
				+ WoodCutting.CUTTINGBEGINTIME + " TEXT , "
				+ WoodCutting.CUTTINGENDTIME + " TEXT , "
				+ WoodCutting.INSPECTION + " TEXT , " + WoodCutting.ATTACHMENT
				+ " TEXT , " + WoodCutting.LOCATION_ID + " INTEGER , "
				+ WoodCutting.WOODCUTTING_ID + " INTEGER );");

		/**
		 * 基础台账-社会情况统计
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ SocialStatistics.SOCIA_TABLE_NAME + "("
				+ SocialStatistics.SOCIASTATISTICS_TABLE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ SocialStatistics.UNIT + " TEXT , "
				+ SocialStatistics.TOTALAREA + " TEXT , "
				+ SocialStatistics.FAMILYCOUNT + " TEXT , "
				+ SocialStatistics.AGRICULTUREFAMILY + " TEXT , "
				+ SocialStatistics.NONAGRICULTURALFAMILY + " TEXT , "
				+ SocialStatistics.PROTECTFAMILY + " TEXT , "
				+ SocialStatistics.TEMPORARYFAMILY + " TEXT , "
				+ SocialStatistics.POPULATIONCOUNT + " TEXT , "
				+ SocialStatistics.ARRICULTUREPOPULATION + " TEXT , "
				+ SocialStatistics.NONARRICULTUREALPOPULATION + " TEXT , "
				+ SocialStatistics.TEMPORARYPOPULATION + " TEXT , "
				+ SocialStatistics.EMPHASISUNITCOUNT + " TEXT , "
				+ SocialStatistics.WOODPROCESSINGUNIT + " TEXT , "
				+ SocialStatistics.WOODPURCHASEUNIT + " TEXT , "
				+ SocialStatistics.QUARRYINGUNIT + " TEXT , "
				+ SocialStatistics.COLLIERYUNIT + " TEXT , "
				+ SocialStatistics.FLAYSTONEUNIT + " TEXT , "
				+ SocialStatistics.WILDANIMALUNIT + " TEXT , "
				+ SocialStatistics.TRAVELUNIT + " TEXT , "
				+ SocialStatistics.CONTRACTOR + " TEXT , "
				+ SocialStatistics.PRINCIPAL + " TEXT , "
				+ SocialStatistics.GRAZINGPERSONNEL + " TEXT , "
				+ SocialStatistics.EMPLOYER + " TEXT , "
				+ SocialStatistics.GRAVEMASTER + " TEXT , "
				+ SocialStatistics.MORONISMMANAGER + " TEXT , "
				+ SocialStatistics.ATTACHMENT + " TEXT , "
				+ SocialStatistics.LOCATION_ID + " INTEGER , "
				+ SocialStatistics.SOCIASTATISTICS_ID + " INTEGER );");

		/**
		 * 基础台账-林地内开采沙、土、石、矿情况表
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ WoodlandMining.WOODLANDMINING_TABLE_NAME + "("
				+ WoodlandMining.WOODLANDMINING_TABLE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ WoodlandMining.SPECIFICLOCATION + " TEXT , "
				+ WoodlandMining.MININGTYPE + " TEXT , "
				+ WoodlandMining.MININGAREA + " TEXT , "
				+ WoodlandMining.FALLIMENTIDELLO + " TEXT , "
				+ WoodlandMining.ALLOWUNIT + " TEXT , "
				+ WoodlandMining.REMARK + " TEXT , "
				+ WoodlandMining.ATTACHMENT + " TEXT , "
				+ WoodlandMining.LOCATION_ID + " INTEGER , "
				+ WoodlandMining.WOODLANDMINING_ID + " INTEGER );");

		/**
		 * 重点列管人员走访
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ Interview.INTERVIEW_TABLE_NAME + "("
				+ Interview.INTERVIEW_TABLE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ Interview.INTERVIEW_ID + " INTEGER ,"
				+ Interview.INTERVIEW_TIME + " TEXT ,"
				+ Interview.INTERVIEW_SITE + " TEXT ,"
				+ Interview.INTERVIEW_POLICE + " TEXT , "
				+ Interview.INTERVIEW_OBJECT + " TEXT , " + Interview.UNIT_NAME
				+ " TEXT , " + Interview.UNIT_ADDRESS + " TEXT , "
				+ Interview.NAME + " TEXT , " + Interview.GENDER + " TEXT, "
				+ Interview.AGE + " TEXT , " + Interview.ADDRESS + " TEXT , "
				+ Interview.MANAGED_CAUSE + " TEXT , "
				+ Interview.INTERVIEW_CONTENT + " TEXT , " + Interview.OPINION
				+ " TEXT , " + Interview.ATTACHMENT + " TEXT , "
				+ Interview.LOCATION + " INTEGER ) ; ");
		/**
		 * 重点列管人员（单位）情况
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ ManagedObject.MANAGED_OBJECT_TABLE_NAME + " ( "
				+ ManagedObject.MANAGED_OBJECT_TABLE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ ManagedObject.MANAGED_OBJECT_ID + " INTEGER ,"
				+ ManagedObject.GENDER + " TEXT , "
				+ ManagedObject.ADDRESS_OR_UNIT + " TEXT , "
				+ ManagedObject.UNIT_NAME + " TEXT , "
				+ ManagedObject.DOMICILE_PLACE + " TEXT , "
				+ ManagedObject.CARD_ID + " TEXT , " + ManagedObject.CAREER
				+ " TEXT , " + ManagedObject.MANAGED_CAUSE + " TEXT , "
				+ ManagedObject.SPECIFIC_CAUSES + " TEXT , "
				+ ManagedObject.MANAGED_TIME + " TEXT , "
				+ ManagedObject.RESPONSIBILITY_POLICE + " TEXT , "
				+ ManagedObject.ATTACHMENT + " TEXT , "
				+ ManagedObject.LOCATION + " INTEGER ) ;");
		/**
		 * 盘问情况
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ QuestionedSituation.QUESTIONED_SITUATION_TABLE_NAME + "("
				+ QuestionedSituation.QUESTIONED_SITUATION_TABLE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ QuestionedSituation.QUESTIONED_SITUATION_ID + " INTEGER ,"
				+ QuestionedSituation.YEAR + " TEXT , "
				+ QuestionedSituation.NAME + " TEXT , "
				+ QuestionedSituation.GENDER + " TEXT , "
				+ QuestionedSituation.BIRTHDAY + " TEXT , "
				+ QuestionedSituation.DOMICILE_PLACE + " TEXT , "
				+ QuestionedSituation.UNIT_OR_ADDRESS + " TEXT , "
				+ QuestionedSituation.ASK_WHY + " TEXT , "
				+ QuestionedSituation.ASK_BEGIN_TIME + " TEXT , "
				+ QuestionedSituation.ASK_END_TIME + " TEXT , "
				+ QuestionedSituation.SPONSOR + " TEXT , "
				+ QuestionedSituation.OVERTIME + " TEXT , "
				+ QuestionedSituation.ALLOW + " TEXT , "
				+ QuestionedSituation.HANDING_INFORMATION + " TEXT , "
				+ QuestionedSituation.ATTACHMENT + " TEXT ) ;");
		/**
		 * 涉林重点行业情况
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_TABLE_NAME
				+ "(" + ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_TABLE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_ID
				+ " INTEGER," + ForestryKeyIndustries.UNIT_NAME + " TEXT , "
				+ ForestryKeyIndustries.ADDRESS + " TEXT , "
				+ ForestryKeyIndustries.LICENSE + " TEXT , "
				+ ForestryKeyIndustries.OPERATE_TYPE + " TEXT , "
				+ ForestryKeyIndustries.PRINCIPAL + " TEXT , "
				+ ForestryKeyIndustries.CONTACT_INFORMATION + " TEXT , "
				+ ForestryKeyIndustries.VARIETIES_SOURCESS + " TEXT , "
				+ ForestryKeyIndustries.PROTECTION_LEVEL + " TEXT , "
				+ ForestryKeyIndustries.PRACTITIONER_NUMBER + " TEXT , "
				+ ForestryKeyIndustries.ATTACHMENT + " TEXT , "
				+ ForestryKeyIndustries.LOCATION + " INTEGER , "
				+ ForestryKeyIndustries.REMARK + " TEXT ) ;");
		/**
		 * 护林基本力量
		 */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ ForestPotection.FOREST_POTECTION_TABLE_NAME + "("
				+ ForestPotection.FOREST_POTECTION_TABLE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ ForestPotection.FOREST_POTECTION_ID + " INTEGER,"
				+ ForestPotection.YEAR + " TEXT , "
				+ ForestPotection.ADMINISTRATION + " TEXT , "
				+ ForestPotection.UNIT + " TEXT  ,"
				+ ForestPotection.OFFICIAL_RANK + " TEXT , "
				+ ForestPotection.NAME + " TEXT , " + ForestPotection.GENDER
				+ " TEXT , " + ForestPotection.AGE + " TEXT , "
				+ ForestPotection.TEL + " TEXT , "
				+ ForestPotection.MANAGEMENT_AREA + " TEXT , "
				+ ForestPotection.RESPONSIBILITY + " TEXT , "
				+ ForestPotection.ATTACHMENT + " TEXT, "
				+ ForestPotection.LOCATION_ID + " INTEGER  ) ; ");
		
		/**
		 * 三情--林情
		 * 
		 * */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ ForestryConditions.FORESTRY_CONDITIONS_TABLE_NAME+"("
				+ ForestryConditions.FORESTRY_CONDITIONS_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ ForestryConditions.VILLAGE_NAME +" TEXT , "
				+ ForestryConditions.FORESTRY_ID+" INTEGER , "
				+ ForestryConditions.CATALOGID+" INTEGER , "
				+ ForestryConditions.USERID+" INTEGER , "
				+ ForestryConditions.LQWOODLANDAREA+" TEXT , "//林地面积
				+ ForestryConditions.LQFORESTSTOCKVOLUME+" TEXT , "//林地蓄积力量
				+ ForestryConditions.LQMAJORAREASOFWILDLIFE+" TEXT , "// 野生动物主要活动地区
				+ ForestryConditions.LQFORESTHIGHAREAS+" TEXT ,  "// 涉林案件高发区
				+ ForestryConditions.LQWILDANIMALMARKET+" TEXT , "// 野生动物市场
				+ ForestryConditions.LQSITUATIONPROTECTION+" TEXT , "// 生态管护员情况
				+ ForestryConditions.LQGRAZINGFARMERS+" TEXT , "// 放牧养殖户
				+ ForestryConditions.LQSEMINUMBER+" TEXT , "// 半专业数量及人数
				+ ForestryConditions.LQPROFESSIONALNUMBER+" TEXT , "// 专业数量及人数
				+ ForestryConditions.LQFORESTTYPES+" TEXT , "// 森林的种类
				+ ForestryConditions.LQAGECOMPOSITION+" TEXT , "//树龄组成
				+ ForestryConditions.LQWILDANIMALSPECIES+" TEXT , "// 野生动物种类
				+ ForestryConditions.LQTREESPECIES+" TEXT , "// 林木种类
				+ ForestryConditions.LQOLDFAMOUSTREES+" TEXT ,"// 古树名木
				+ ForestryConditions.LQKEYCOLUMNMANAGEMENT+" TEXT ,"// 重点列管人员
				+ ForestryConditions.LQTYPESFORESTL+" TEXT ) ;"// 林业用地的种类
		 );
		
		/**
		 * 三情--山情
		 * */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ MountConditions.KEY_MOUNT_CONDITIONS_NAME+"("
				+ MountConditions.MOUNT_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ MountConditions.ID+" INTEGER , "
				+ MountConditions.TABLE_ID+" INTEGER , "//表ID
				+ MountConditions.VILLAGE_NAME+" TEXT , "//村名
				+ MountConditions.FILETYPE+" TEXT , "//文件类型
				+ MountConditions.THUMBNAIL+" TEXT , "//图片名称
				+ MountConditions.THUMBNAILTYPE+" TEXT , "//图片类型
				+ MountConditions.SQADMINISTRATIVEAREA+" TEXT , "//行政区域总面积(公顷)
				+ MountConditions.SQTOPOGRAPHYGEOMORPHOLOGY+" TEXT , "//地形地貌
				+ MountConditions.SQLTITUDE+" TEXT , "// 海拔
				+ MountConditions.SQLATITUDELONGITUDE+" TEXT , "// 经纬度
				+ MountConditions.SQCOMPLETELY+" TEXT , "// 截然地区(四至)
				+ MountConditions.SQRIVERLAKEPOSITION+" TEXT , "// 河流湖泊位置
				+ MountConditions.SQTRAFFICROADDISTRIBUTION+" TEXT , "// 交通道路分布
				+ MountConditions.SQREGIONALSPECIALTY+" TEXT , "//地区特产
				+ MountConditions.SQMINERALDISTRIBUTION+" TEXT ); "// 矿产资源分布
		 );
		
		/**
		 * 三情--社情
		 * */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ SheConditions.KEY_SHE_CONDITONS_NAME+"("
				+ SheConditions.SHE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ SheConditions.ID+" INTEGER , "
				+ SheConditions.TABLE_ID+" INTEGER , "//表id
				+ SheConditions.VILLAGE_NAME+" TEXT , "//村名
				+ SheConditions.FILETYPE+" TEXT , "// 文件类型
				+ SheConditions.THUMBNAIL+" TEXT , "// 图片名称
				+ SheConditions.THUMBNAILTYPE+" TEXT , "// 图片类型
				+ SheConditions.SHQLANDCONTRACT+" TEXT , "// 土地承包情况
				+ SheConditions.SHQVILLAGECONTACT+" TEXT , "// 村领导干部及联系方式
				+ SheConditions.SHQSTAYSILLYPATIENT+" TEXT , "// 呆、傻精神病人情况
				+ SheConditions.SHQMAJORINDUSTRY+" TEXT , "// 主要餐饮业情况
				+ SheConditions.SHQTOMBCUSTOMS+" TEXT , "// 清明节扫墓时间及风俗
				+ SheConditions.SHQTRAFFICDISTRIBUTION+" TEXT , "// 交通分布情况
				+ SheConditions.SHQTRANSIENTPOPULATION+" TEXT , "// 暂住人口数
				+ SheConditions.SHQRESIDENTPOPULATION+" TEXT , "// 居民人口数
				+ SheConditions.SHQRESIDENTVILLAGE+" TEXT , "// 行政村常住人口数
				+ SheConditions.SHQCULTURALSITES+" TEXT , "// 文物古迹
				+ SheConditions.SHQMAJORSOURCES+" TEXT , "// 主要产业及经济来源
				+ SheConditions.SHQCHARACTERISTICPROPERTY+" TEXT ); "// 特色物产
		 );
		
		/**
		 * 巡逻登记--巡逻轨迹
		 * */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ PatrolRegisterLocation.PATROLREGISTER_TABLE_NAME+"("
				+ PatrolRegisterLocation.ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ PatrolRegisterLocation.USER_ID+" TEXT , "// userid
				+ PatrolRegisterLocation.LATITUDE+" TEXT , "// 纬度
				+ PatrolRegisterLocation.LONGITUDE+" TEXT , "// 经度
				+ PatrolRegisterLocation.ELEVATION+" TEXT , "// 海拔
				+ PatrolRegisterLocation.LOCATIONTYPE+" TEXT , "// 定位类型
				+ PatrolRegisterLocation.GUID+" TEXT , "// guid标识
				+ PatrolRegisterLocation.TIME+" TEXT ); "// 时间
		 );
		
		/**
		 * 路线管理--路线坐标轨迹点
		 * */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+ RouteInfo.ROUTE_INFO_TABLE_NAME + "("
				+ RouteInfo.ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+ RouteInfo.USER_ID+" TEXT , "// userid
				+ RouteInfo.INFO_ID+" TEXT , "// infoid
				+ RouteInfo.GUID+" TEXT , "// guid
				+ RouteInfo.LATITUDE+" TEXT , "// 纬度
				+ RouteInfo.LONGITUDE+" TEXT , "// 经度
				+ RouteInfo.ELEVATION+" TEXT , "// 海拔
				+ RouteInfo.LOCATIONTYPE+" TEXT , "// 定位类型
				+ RouteInfo.STATUS+" TEXT , "// 数据状态
				+ RouteInfo.TIME+" TEXT ); "// 时间
				);
		/**
		 * 报警信息--下拉菜单
		 * */
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+AlarmSpinnerInfo.ALARM_SPINNER_INFO_TABLE_NAME+"("
				+AlarmSpinnerInfo.ID
				+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+AlarmSpinnerInfo.ALARMWAY_ID+" INTEGER , "//接警方式ID
				+AlarmSpinnerInfo.ALARMWAY_NAME+" TEXT , "//接警方式
				+AlarmSpinnerInfo.NATION_ID+" INTEGER ,"//民族ID
				+AlarmSpinnerInfo.NATION_NAME+" TEXT ,"//民族
				+AlarmSpinnerInfo.APPROVALPEOPLEID+" INTEGER ,"//审批领导ID
				+AlarmSpinnerInfo.LEADER+" TEXT ,"//审批领导
				+AlarmSpinnerInfo.PARTS_CULTURE_ID+" INTEGER ,"//学历
				+AlarmSpinnerInfo.PARTS_CULTURE+" TEXT ,"//学历
				+AlarmSpinnerInfo.PARTS_POST_ID+" INTEGER ,"//职务
				+AlarmSpinnerInfo.PARTS_POST+" TEXT ,"//职务
				+AlarmSpinnerInfo.TREATMENT_ADVICE_ID+" INTEGER ,"//处理意见
				+AlarmSpinnerInfo.TREATMENT_ADVICE+" TEXT ,"//处理意见
				+AlarmSpinnerInfo.HOST_POLICE_ID+" INTEGER ,"//主办民警
				+AlarmSpinnerInfo.COPOLICE_ID+" INTEGER,"//协办民警
				+AlarmSpinnerInfo.STATE_ID+" INTEGER,"//协办民警
				+AlarmSpinnerInfo.STATE_NAME+" TEXT );"//审批状态
				);
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+AlarmingHistoryInfo.ALARMING_HISTORY_INFO_TABLE_NAME+"("
				+AlarmingHistoryInfo.ID
				+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+AlarmingHistoryInfo.TIME+" TEXT ,"//接警时间
				+AlarmingHistoryInfo.HISOPTION+" TEXT ,"//意见
				+AlarmingHistoryInfo.ALARM_STATE+" TEXT , "//审批状态
				+AlarmingHistoryInfo.POLICE+" TEXT ,"//审批领导
				+AlarmingHistoryInfo.ALARMID+" INTEGER );"//编号
				);
		mDb.execSQL("CREATE TABLE IF NOT EXISTS "
				+AlarmResult.RECEIVE_DISPOSE_ALARM_TABLE_NAME+"("
				+AlarmResult.RESULT_ID
				+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "
				+AlarmResult.ALARMID +" INTEGER ,"//案件编号
				+AlarmResult.STATEID +" INTEGER ,"//案件编号
				+AlarmResult.ALARM_RESULT+" TEXT ,"//处理结果
				+AlarmResult.ACCEPT_UNIT+" TEXT , "//接受单位
				+AlarmResult.ACCEPT_TIME+" TEXT ,"//接收时间
				+AlarmResult.CRIMINAL_CASE_CODE+" INTEGER ,"//刑事立案编号
				+AlarmResult.POLICE_PEOPLE_ID+" INTEGER ,"//刑事立案编号
				+AlarmResult.ADMINISTRATIVE_CASE_CODE+" INTEGER );"//行政立案编号
				);
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
