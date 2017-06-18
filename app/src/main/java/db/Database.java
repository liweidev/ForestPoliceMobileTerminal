/**
 * 
 */
package db;

import java.util.ArrayList;


import android.provider.BaseColumns;

/**
 * @author liupeng 数据库列信息
 * 
 */
public class Database {

	/*
	 * 重点部位------安全防范
	 */
	public static class KeyPositions implements BaseColumns {
		public static final String KEY_POSITION_TABLE_NAME = "key_positions"; // 表名
		public static final int KEY_POSITION_TABLE_ID = 632; // 表ID
		public static final String KEY_POSITION_ID = "key_position_id";
		public static final String KEY_POSITION_SERVER_ID = "key_position_server_id";
		public static final String YEAR = "year";
		public final static String NAME = "name"; // varchar 200 not null 名称  
		public final static String SITING = "siting"; // varchar 200 not null
														// 坐落地点
		public final static String PERSON_IN_CHARGE = "personIn_charge"; // char
																			// 12
																			// not
																			// null
																			// 负责人
		public final static String HEAD_OF_SECURITY = "head_of_security"; // char
																			// 12
																			// not
																			// null
																			// 安全负责人
		public final static String EMPLOYEES = "employees"; // int 20 not null
															// 职工人数
		public final static String CONTACT = "contact"; // char 11 not null 联系方式
		public final static String AREA = "area"; // float 20 not null 面积
		public final static String TRAFFIC = "traffic"; // varchar 200 not null
														// 交通情况
		public final static String LAT_LON_ALTITUDE = "latLon_altitude"; // varchar
																			// 200
																			// not
																			// null
																			// 经纬度及海拔
		public final static String SCOPE_AND_NATURE = "scope_and_nature"; // varchar
																			// 200
																			// not
																			// null
																			// 经营范围及性质
		public final static String FOUR_CASES = "four_cases"; // varchar 200 not
																// null 四至情况
		public final static String FS_SURROUNDING = "fs_surrounding"; // varchar
																		// 200
																		// not
																		// null
																		// 周边林情
		public final static String REASON_TUBE = "the_reason_tube"; // varchar
																	// 200 not
																	// null 列管原因
		public final static String LOCATION_ID = "location_id"; // 定位表ID
		public final static String ATTACHEMENT = "attachment"; // varchar 2000
																// null 附件
		public final static String NOTE = "note"; // varchar 200 null 备注

	}

	/*
	 * 重点动物------安全防范
	 */
	public static class ProtectedAnimals implements BaseColumns {
		public static final String PROTECTED_ANIMALS_TABLE_NAME = "protected_animals"; // 表名
		public static final int PROTECTED_ANIMALS_TABLE_ID = 633; // 表ID
		public static final String PROTECTED_ANIMALS_ID = "protected_animals_id";
		public static final String PROTECTED_ANIMALS_SERVER_ID = "protected_animals_server_id";

		public final static String ACCOUNTABILITY_UNIT = "accountability_unit"; // varchar
																				// 50
																				// not
																				// null
																				// 责任单位

		public final static String COMPETENT_DEPARMENT = "competent_department"; // varchar
																					// 50
																					// not
																					// null
																					// 动物名称

		public final static String DR_AND_PN = "dr_and_pn"; // varchar 100 not
															// null 分布具体范围及地点名称

		public final static String PROTECTION_LEVEL = "protection_level"; // varchar
																			// 10
																			// not
																			// null
																			// 保护级别

		public final static String PROTECT_FILE_NUM = "protect_file_num"; // varchar
																			// 20
																			// not
																			// null
																			// 保护档案编号

		public final static String PROTECT_THE_WAY = "protect_the_way"; // varchar
																		// 100
																		// not
																		// null
																		// 保护方式

		public final static String MAPO_PERSONNEL = "mapo_personnel"; // varchar
																		// 100
																		// not
																		// null
																		// 管理人员

		public final static String CONTACT = "contact"; // char 11 not null 联系方式

		public final static String ATTACHMENT = "attachment"; // varchar 2000
																// null 附件

		public final static String LOCATION_ID = "location_id"; // 定位表ID
		public final static String NOTE = "note"; // varchar 100 null 备注
	}

	/*
	 * 重点植物------安全防范
	 */
	public static class KeyProtectedPlant implements BaseColumns {
		public static final String KEY_PROTECTED_PLANT_TABLE_NAME = "key_protected_plant"; // 表名
		public static final int KEY_PROTECTED_PLANT_TABLE_ID = 634; // 表名
		public static final String KEY_PROTECTED_PLANT_ID = "key_protected_plant_id";
		public static final String KEY_PROTECTED_PLANT_SERVER_ID = "key_protected_plant_server_id";

		/*
		 * public final static String ACCOUNTABILITY_UNIT =
		 * "accountability_unit";
		 */// varchar 50 not null 责任单位

		public final static String COMPETENT_DEPARMENT = "competent_department"; // varchar
																					// 50
																					// not
																					// null
																					// 主管部门
																					// 改为
																					// <责任单位>

		public final static String DR_AND_PN = "dr_and_pn"; // varchar 100 not
															// null 分布具体范围及地点名称

		public final static String PLANT_NAME = "plant_names"; // varchar 100
																// not null 植物名称

		public final static String TREE_AGE = "tree_age"; // float 10 not null
															// 树龄

		public final static String PROTECTION_LEVEL = "protection_level"; // varchar
																			// 10
																			// not
																			// null
																			// 保护级别

		public final static String STATUS = "status"; // varchar 200 not null 现况
		public final static String FILE_NO = "file_no"; // varchar 20 not null
														// 古树编号

		public final static String MAPO_PERSONNEL = "mapo_personnel"; // varchar
																		// 100
																		// not
																		// null
																		// 管理人员

		public final static String CONTACT = "contact"; // char 11 not null 联系方式
		public final static String LOCATION_ID = "location_id"; // 定位表ID
		public final static String ATTACHMENT = "attachment";// varchar 2000
																// null 附件
		public final static String NOTE = "note";// varchar 200 null 备注

	}

	/*
	 * 林情调查------安全防范
	 */
	public static class ForestSurveys implements BaseColumns {
		public static final String FOREST_NAME = "forest_name";// 表名
		public static final int FOREST_TABLE_ID = 653;// 表ID
		public static final String FOREST_SERVER_ID = "forest_server_id";
		public static final String FOREST_ID = "forest_id";// 表Id
		public static final String FOREST_UNIT = "forest_unit";// 林情调查负责单位
		public static final String FOREST_NUMBER = "forest_number";// 小班号
		public static final String FOREST_AREA = "forest_area";// 调查面积
		public static final String FOREST_TYPES = "forest_types";// 林种
		public static final String LAND_TYPES = "land_types";// 地类
		public static final String FOREST_OWNERSHIP = "forest_ownership";// 林木权属
		public static final String LAND_OWNERSHIP = "land_ownership"; // 土地所有权
		public static final String ATTACHMENT = "attachment";// 附件
		public static final String NOTE = "note";// 备注
		public final static String LOCATION_ID = "location_id"; // 定位表ID
	}

	/*
	 * 受警登记---------执法办案
	 */
	public static class ReceiveAlarm implements BaseColumns {
		public static final String RECEIVE_ALARM_TABLE_NAME = "receive_alarm"; // 表名
		public static final int RECEIVE_ALARM_TABLE_ID = 639; // 表名
		public static final String RECEIVE_ALARM_ID = "receive_alarm_id"; // 表名
		public final static String RECEIVINT_ALARM_STAFF = "receiving_alarm_staff"; // char
																					// 12
																					// not
																					// null
																					// 接警人员
		public final static String RECEIVINT_ALARM_DATE = "receiving_alarm_date"; // datetime
																					// 100
																					// not
																					// null
																					// 接警时间
		public final static String ALARM_MODE = "alarm_mode"; // char 20 not
																// null 报警方式
																// 上级批准，电话报警，来信来访，110转
		public final static String ALARM_NAME = "alarm_name"; // char 12 not
																// null 报警人信息
																// 报警人姓名
		public final static String ALARM_SEX = "alarm_sex"; // char 2 not null
															// 报警人性别
		public final static String ALARM_AGE = "alarm_age"; // int 3 not null
															// 报警人年龄
		public final static String ALARM_NATIONALITY = "alarm_nationality"; // char
																			// 20
																			// not
																			// null
																			// 报警人民族
		public final static String ALARM_PHONE = "alarm_phone"; // char 11 not
																// null 报警人电话
		public final static String ALARM_ADD = "alarm_add"; // varchar 50 not
															// null 报警人现住址
		public final static String ALARM_IDCARD = "alarm_idcard"; // char 18 not
																	// null
																	// 报警人身份证号
		public final static String ALARM_CONTENT = "alarm_content"; // varchar
																	// 200 not
																	// null 报警内容
		public final static String ALARM_OPINION_LEADER = "alarm_opinion_leaders"; // varchar
																					// 200
																					// not
																					// null
																					// 接警领导意见
		public final static String INSTRUCTION_TIME = "instruction_time"; // datetime
																			// 100
																			// not
																			// null
																			// 收到出警指定时间;//
		public final static String TIME_TO_REACH = "time_to_reach"; // datetime
																	// 100 not
																	// null
																	// 到达警情现场时间
		public final static String ALARMING_COMMENT = "alarming_comments"; // 处警情况及意见
		public final static String SIGNATURE_DATE = "signature_date"; // varchar
																		// 100
																		// not
																		// null
																		// 填写人及日期
		public final static String ALARMING_OPINION_LEADER = "alarming_opinion_leaders"; // varchar
																							// 200
																							// not
																							// null
																							// 处警领导意见
		public final static String PROCESSING_RESULT = "processingr_esults"; // varchar
																				// 200
																				// not
																				// null
																				// 处理结果
		public final static String TRANSFER = "transfer"; // varchar 100 not
															// null 接收单位
		public final static String RECEIVE_TIME = "receive_time"; // datetime
																	// 100 not
																	// null 接收
																	// 接收时间
		public final static String CROWN_CASE_NO = "crown_case_no"; // varchar
																	// 20 null
																	// 备注
																	// 刑事案件立案编号
		public final static String ADMINISTRATIVE_CASE_NO = "administrative_case_no"; // 行政案件立案编号
		public final static String LOCATION_ID = "location_id";
		public final static String ATTACHMENT = "attachment"; // varchar 2000.00
																// null 附件

	}

	/*
	 * 处警---------执法办案
	 */

	public static class AlarmingWork implements BaseColumns {
		public static final String ALARMING_WORK_TABLE_NAME = "alarming_work"; // 表名
		public static final int ALARMING_WORK_TABLE_ID = 640;// 表Id
		public static final String ALARMING_WORK_ID = "alarming_work_id"; // 表名
		public final static String ALARM_ID = "alarm_id";
		public final static String STATE_ID = "state_id";
		public final static String INSTRUCTION_TIME = "instruction_time"; // datetime
																			// 100.00
																			// not
																			// null
																			// 接到出警指令时间
		public final static String TIME_TO_REACH = "time_to_reach"; // datetime
																	// 100.00
																	// not null
																	// 到达现场时间

		public final static String CRIMES_NAME = "crimes_name"; // char 12.00
																// not null
																// 违法犯罪人基本情况 姓名
		public final static String CRIMES_SEX = "crimes_sex"; // cahr 2.00 not
																// null 性别
		public final static String CRIMES_AGE = "crimes_age"; // 年龄
		public final static String CRIMES_EDUCATION = "crimes_education"; // char
																			// 20.00
																			// not
																			// null
																			// 文化程度
		public final static String CRIMES_UNIT = "crimes_unit"; // 工作单位
		public final static String CRIMES_ADD_OR_UNIT = "crimes_add"; // varchar
																		// 50.00
																		// not
																		// null
																		// 住址
		public final static String CRIMES_UNIT_NAME = "crimes_unit_name"; // 单位名称
		public final static String CRIMES_UNIT_ADD = "crimes_unit_add"; // 地址
		public final static String LEGAL_PERSON = "legal_person";// 法人
		public final static String DUTY = "duty";// 职务
		// public final static String CRIMES_IDCARD = "crimes_idcard"; // char
		// 18.00 not null 身份证号码
		public final static String PIB_RIEF_CASE = "pib_rief_case";// varchar
																	// 200.00
																	// not null
																	// 初查简要情况（时间、地点、人员、事实经过等）
		public final static String AP_ADVICE_ID = "ap_advice_id"; // varchar
																	// 200.00
		// not null 处警民警意见
		// public final
		// static String
		// AP_ADVICE_DATE =
		// "ap_advice_date";
		// // varchar
		public final static String AP_ADVICE = "ap_advice"; // varchar 200.00
		// not null 处警民警意见
		// public final
		// static String
		// AP_ADVICE_DATE =
		// "ap_advice_date";
		// // varchar
		// // 200.00
		// // not
		// // null
		// // 处警民警意见
		public final static String REMARK = "remark"; // 备注
		public final static String ATTACHMENT = "attachment"; // varchar 2000.00
																// null 附件
		public final static String APPROVALPEOPLEID = "approvalpeopleid"; // 审批
		public final static String LONGITUDE = "longitude"; // varchar 20 not
		// null 经度
		public final static String LATITUDE = "latitude"; // varchar 20 not null
		// 纬度
		public final static String ELEVATION = "elevation";// varchar 20 not
		// null 海拔
		public final static String TIME = "Time";// 时间

	}

	/*
	 * 接警及处警登记工作---------执法办案
	 */
	public static class ReceiveAndDisposeAlarm implements BaseColumns {

		public static final String RECEIVE_DISPOSE_ALARM_TABLE_NAME = "receive_dispose_alarm";// 表名
		public static final int RECEIVE_DISPOSE_ALARM_TABLE_ID = 729;// 表ID
		public static final String RECEIVE_DISPOSE_ALARM_ID = "receive_dispose_alarm_id";// 表名
		public static final String LOCATION_ID = "location_id";
		public final static String ATTACHMENT = "attachment"; // varchar 2000.00
																// null 附件
		public final static String NOTE = "note"; // varchar 200 null 备注
		// 报警信息
		public final static String RECEIVE_ALARM_PERSON = "receive_alarm_person";// 接警人员
		public final static String RECEIVE_ALARM_TIME = "receive_alarm_time";// 接警时间
		public final static String RECEIVE_ALARM_MODEL = "receive_alarm_model";// 接警方式
		public final static String GIVE_ALARM_NAME = "give_alarm_name";// 报警人姓名
		public final static String GIVE_ALARM_AGE = "give_alarm_age";// 报警人年龄
		public final static String ALARM_NATION = "alarm_nation";// 民族
		public final static String GIVE_ALARM_SEX = "give_alarm_sex";// 报警人性别
		public final static String GIVE_ALARM_CONTACT = "give_alarm_contact";// 报警人联系方式
		public final static String GIVE_ALARM_ID_CARD = "give_alarm_id_card";// 报警人身份证号码
		public final static String GIVE_ALARM_ADDRESS = "give_alarm_address";// 报警人暂住地址
		public final static String GIVE_ALARM_CONTENT = "give_alarm_content";// 报警内容
		// 审批
		public final static String OPINION_LEADER = "opinion_leader";// 意见领导
																		// Opinion
																		// Leader
		// 处警信息
		public final static String RECEIVE_ORDER_TIME = "receive_order_time";// 接到指令时间
		public final static String ALARM_TIME = "alarm_time";// 处警时间
		public final static String ARRIVE_SITE_TIME = "arrive_site_time";// 到达现场时间
		public final static String ALARM_PERSON = "alarm_person";// 处警人员
		// 违法人信息
		public final static String PARTS_NAME = "parts_name";// 违法人姓名
		public final static String PARTS_AGE = "parts_age";// 违法人年龄
		public final static String PARTS_SEX = "parts_sex";// 违法人性别
		public final static String PARTS_CULTURE = "parts_culture";// 违法人文化程度
		public final static String PARTS_WORK_UNIT = "parts_work_unit";// 工作单位
		public final static String PARTS_HOME_ADDRESS = "parts_home_address";// 住址
		public final static String PARTS_UNIT_NAME = "parts_unit_name";// 单位名称
		public final static String PARTS_UNIT_ADDRESS = "parts_unit_address";// 单位地址
		public final static String PARTS_POST = "parts_post";// 职务
		public final static String PARTS_LAW_PERSON = "parts_law_person";// 法定代表人
		// 初查情况及意见
		public final static String FIRST_INSPECT = "first_situation";// 初查情况
		public final static String INSPECT_END_TIME = "inspect_end_time";// 初查结束时间
		public final static String ALARM_POLICE_IDEA = "alarm_police_idea";// 处警民警意见
		public final static String ALARM_IDEA_WRITE_TIME = "alarm_idea_write_time";// 处警民警意见填写时间
		public final static String ALARM_AND_IDEA = "alarm_and_idea";// 处警情况及意见
		public final static String ALARM_RESPONSIBLE_IDEA = "alarm_responsible_idea";// 处警部门负责人意见
		public final static String RESPONSIBLE_IDEA_TIME = "responsible_idea_time";// 部门负责人意见填写时间
		// 处警情况及意见
		public final static String ALARM_RESULT = "alarm_result";// 处理结果
		public final static String ACCEPT_UNIT = "accept_unit";// 接受单位
		public final static String ACCEPT_TIME = "accept_time";// 接受时间
		public final static String CRIMINAL_CASE_CODE = "criminal_case_code";// criminal
																				// case刑事案件立案编号
		public final static String ADMINISTRATIVE_CASE_CODE = "administrative_case_code";// administrative
																							// case行政案件立案编号
		public final static String PRINCIPAL_INSTRUCT = "principal_instruct";// Principal
																				// instruct森林公安负责人批示
		public final static String PRINCIPAL_INSTRUCT_TIME = "principal_instruct_time";// 负责人批示时间
	}

	/*
	 * 接处警
	 */
	public static class Receive_DisposeAlarm implements BaseColumns {
		public static final String RECEIVE_DISPOSE_ALARM_TABLE_NAME = "receive_dispose_alarm_new";// 表名
		public static final int RECEIVE_DISPOSE_ALARM_TABLE_ID = 729;// 表ID
		public static final String RECEIVE_DISPOSE_ALARM_ID = "receive_dispose_alarm_id";// 表id
		// 接报警信息
		public static final String ALARM_INFO = "AlarmInfo";// 接报警信息
		public static final String ALARM_TIME = "AlarmTime";// 接警时间
		public static final String ALARM_WAY_ID = "AlarmWayID";// 接警方式
		public static final String PEOPLE_POLICE_ID = "PeoplePoliceID";// 接警民警ID
		public static final String TO_ALARM_NAME = "ToAlarmName";// 报警人姓名
		public static final String TO_ALARM_AGE = "ToAlarmAge";// 报警人年龄
		public static final String NATION_ID = "NationID";// 报警人民族ID
		public static final String TO_ALARM_SEX = "ToAlarmSex";// 报警人性别
		public static final String CONTACT = "Contact";// 联系方式
		public static final String CARD_NUMBER = "CardNumber";// 身份证号
		public static final String ADDRESS = "Address";// 暂住地址
		public static final String TO_ALARM_MESSAGE = "ToAlarmMessage";// 报警内容
		// 查询报警信息
		public static final String CASE_NUMBER = "CaseNumber";// 接警编号
		public static final String PEOPLE_POLICE = "PeoplePolice";// 接警民警信息
		public static final String PEOPLE_POLICE_NAME = "PeoplePoliceName";// 接警民警姓名
		public static final String ALARM_STATE = "AlarmState";// 状态信息
		public static final String STATE_ID = "StateID";// 状态Id
		public static final String STATE_NAME = "StateName";// 状态
		// 审批
		public static final String DEVICE_SN = "DeviceSN";// 手机设备码
		public static final String APPROVAL = "Approval";// 审批信息
		public static final String ALARM_ID = "AlarmID";// 报警信息ID
		public static final String APPROVAL_PEOPLE_ID = "ApprovalPeopleID";// 审批人ID
		public static final String HOST_POLICE_ID = "HostPoliceID";// 主办民警ID
		public static final String COPOLICE_ID = "CoPoliceID";// 协办民警ID
		public static final String OPINION = "Opinion";// 意见
		public static final String RESULT = "Result";// 处理结果
		public static final String ACCEPTANCEUNIT = "AcceptanceUnit";// 接收单位
		public static final String ACCEPTANCETIME = "AcceptanceTime";// 接收时间
		public static final String CRIMINALCASENUM = "CriminalCaseNum";// 刑事立案编号
		public static final String ADMINISTRATIVECASENUM = "AdministrativeCaseNum";// 行政案件立案编号

		// 处警
		public static final String RECEIVED_TIME = "ReceivedTime";// 接到处警指令时间
		public static final String ARRIVALS_TIME = "ArrivalsTime";// 到达警情现场时间
		public static final String NAME = "Name";// 姓名
		public static final String AGE = "Age";// 年龄
		public static final String SEX = "Sex";// 性别
		public static final String EDUCATION = "Education";// 文化程度
		public static final String WORKPLACE = "Workplace";// 工作单位
		public static final String ADDRESS_Z = "AddressZ";// 住址
		public static final String COMPANY_NAME = "CompanyName";// 单位名称
		public static final String ADDRESS_D = "AddressD";// 地址
		public static final String LEGAL = "Legal";// 法人
		public static final String POSITION = "Position";// 职务
		public static final String BRIEF_CASE = "BriefCase";// 简要情况
		public static final String TREATMENT_ADVICE = "TreatmentAdviceID";// 处理意见
		public static final String REMARK = "Remark";// 备注
		public static final String ACCESSORY = "Accessory";// 附件
		public static final String LOCATION = "location";// 定位

	}

	/*
	 * 执法办案----------处理结果
	 */
	public static class AlarmResult implements BaseColumns {
		public static final String RECEIVE_DISPOSE_ALARM_TABLE_NAME = "alarm_result";// 表名
		public static final int KEY_SHE_CONDITIONS_ID = 911; // 表ID
		public static final String RESULT_ID = "result_id";// 表id
		public final static String ALARMID = "alarm_id";// 案件编号
		public final static String STATEID = "state_id";// 案件编号
		public final static String ALARM_RESULT = "alarm_result";// 处理结果
		public final static String ACCEPT_UNIT = "accept_unit";// 接受单位
		public final static String ACCEPT_TIME = "accept_time";// 接受时间
		public final static String CRIMINAL_CASE_CODE = "criminal_case_code";// criminal
																				// case刑事案件立案编号
		public final static String ADMINISTRATIVE_CASE_CODE = "administrative_case_code";// administrative
																							// case行政案件立案编号
		public final static String POLICE_PEOPLE_ID = "Police_People_ID";
	}

	/*
	 * 林区治安防控巡逻检查记录----------执法检查
	 */
	public static class LawEnforcementInspection implements BaseColumns {
		public static final String LAW_ENFORCEMENT_INSPECTION_TABLE_NAME = "law_enforcement_inspection"; // 表名
		public static final int LAW_ENFORCEMENT_INSPECTION_TABLE_ID = 641; // 表ID
		public static final String LAW_ENFORCEMENT_INSPECTION_ID = "law_enforcement_inspection_id"; // 表id
		public final static String LE_UNIT = "le_unit";// 单位
		public final static String le_signature_bool="le_signature_bool";
		public final static String LE_TIME = "le_time"; // datetime 0 not null
														// 巡逻检查时间
		public final static String LE_VEHICLE = "le_vehicle"; // varchar 200 not
																// null 巡逻检查车辆
		public final static String LE_INSPECTORS = "le_inspectors"; // varchar
																	// 200
																	// not null
																	// 巡逻检察人员
		public final static String LE_NO = "le_no";// 编号
		public final static String LE_CONTENT = "le_contents"; // varchar 200
																// not null
																// 巡逻检查内容
		public final static String LE_SITUATION = "le_situation"; // varchar 200
																	// not null
																	// 巡逻检查情况
		public final static String LE_SIGNATURE = "le_signature"; // varchar
																	// 200 not
																	// null
																	// 被检查单位（个人）签字
		public final static String LE_PHONE = "le_phone"; // char 11 not null
															// 电话
		public final static String ATTACHMENT = "attachment"; // varchar 2000.00
		public static final String LOCATION_ID = "location_id";
		public final static String NOTE = "note";// varchar 200 null 备注
	}

	/*
	 * 林区巡逻工作--治安管理
	 */
	public static class ForestPatrolWork implements BaseColumns {

		public static final String FOREST_PATROL_WORK_TABLE_NAME = "forest_patrol_work"; // 表名
		public static final int FOREST_PATROL_WORK_TABLE_ID = 629; // 表ID
		public static final String FOREST_PATROL_WORK_ID = "forest_patrol_work_id"; // 表名
		// public final static String UNIT = "unit";// text 单位
		public final static String BEGIN_DATE = "begin_date"; // datetime 20 not
																// null 开始时间
		public final static String END_DATE = "end_date"; // datetime 20 not
															// null 结束时间
		public final static String PATROL_POLICE = "patrol_police"; // varchar
																	// 50 not
																	// null 巡逻民警
		public final static String PATROL_ROUTE = "patrol_route"; // varchar 100
																	// not null
																	// 巡逻路线
		public final static String PATROL_CHRONICLE = "patrol_chronicle"; // varchar
																			// 200
																			// not
																			// null
																			// 巡逻记事
		public final static String ANALYSIS_OF_DISPOSAL = "analysis_of_disposal"; // varchar
																					// 200
																					// not
																					// null
																					// 研判分析处置情况
		public final static String SHIFT_LEADERSHIP_OPINION = "shift_leadership_opinion"; // varchar
																							// 200
																							// not
																							// null
																							// 带班领导意见
		public final static String LOCATION_ID = "location_id";// 定位
		public final static String ATTACHMENT = "attachment"; // varchar 2000.00
																// null 附件
		public final static String NOTE = "note";// varchar 200 null 备注

	}

	/*
	 * 派出所治安管理检查记录表--治安管理
	 */
	public static class PublicSecurityManagement implements BaseColumns {
		public static final String PUBLIC_SECURITY_MANAGEMENT_TABLE_NAME = "public_security_management";
		public static final int PUBLIC_SECURITY_MANAGEMENT_TABLE_ID = 630;
		public static final String PUBLIC_SECURITY_MANAGEMENT_ID = "public_security_management_id";
		public static final String PSM_UNITNAME = "psm_unitname";// 被检单位名称
		public static final String PSM_UNITADD = "psm_unitadd";// 经营地点
		public static final String PSM_LEGAL_REPRESENTATIVE = "psm_legal_representative";// 法人代表
		public static final String PSM_HEAD = "psm_head";// 负责人
		public static final String PSM_BUSINESS_PROJECT = "psm_business_project";// 经营项目
		public static final String PSM_BUSINESS_PRACTICE = "psm_business_practice"; // 经营方式
		public static final String le_signature_bool="le_signature_bool";
		public static final String PSM_CHECK_THE_TIME = "psm_check_the_time"; // 检查时间

		public static final String PSM_IMSPECTORS = "psm_inspectors"; // 检查人员
		public static final String PSM_CONTENT_AND_PROBLEMS = "psm_content_and_problems"; // 检查内容及存在问题
		public static final String PSM_RECTIFICATION_OPINIONS = "psm_rectification_opinions"; // 整改意见
		public static final String PSM_OWNER_OR_HEAD = "psm_owner_or_head"; // 业主或负责人意见
		public static final String PSM_SIGNATURE = "psm_signature"; // 签名
		public static final String PSM_SIGNATRUE_DATE = "psm_signature_date"; // 日期
		public final static String LOCATION_ID = "location_id";// 定位
		public final static String ATTACHMENT = "attachment"; // 附件
		public final static String NOTE = "note";// 备注

	}

	/*
	 * 林区治安防控巡逻检查记录【防火巡逻检查】-防火工作
	 */
	public static class FirePatrolInspection implements BaseColumns {
		public static final String FIRE_PATROL_INSPECTION_TABLE_NAME = "fire_patrol_inspection"; // 表名
		public static final int FIRE_PATROL_INSPECTION_TABLE_ID = 663; // 表ID
		public static final String FIRE_PATROL_INSPECTION_ID = "fire_patrol_inspection_id"; // 表名
		public final static String UNIT = "unit";// 单位
		public final static String PC_TIME = "pc_time"; // datetime 0 not null
														// 巡逻检查时间
		public final static String PC_ROUTE = "pc_routes"; // varchar 200 not
															// null 巡逻检查路线地点
		public final static String P_INSPECTORS = "p_inspectors"; // varchar 200
																	// not null
																	// 巡逻检察人员
		public final static String PC_NO = "pc_no";// 编号
		public final static String PC_CONTENT = "pc_contents"; // varchar 200
																// not null
																// 巡逻检查内容
		public final static String P_INSPECTION = "p_inspection"; // varchar 200
																	// not null
																	// 巡逻检查意见
																	// public
																	// final
																	// static
																	// String
																	// RESULT =
																	// "result";//
																	// 整改结果
		public final static String TBC_SIGNATURE = "tbc_signature"; // varchar
																	// 200 not
																	// null
																	// 被检查单位（个人）签字
		public final static String TBC_PHONE = "tbc_phone"; // char 11 not null
															// 电话
		public final static String LOCATION_ID = "location_id";// 定位
		public final static String ACCESSORY = "Accessory"; // varchar 2000.00
															// null 附件
		public final static String NOTE = "note";// varchar 200 null 备注
	}

	/*
	 * 辖区瞭望塔登记表------防火工作
	 */
	public static class WatchTower implements BaseColumns {
		public static final String WATCH_TOWER_TABLE_NAME = "watch_tower";// 表名
		public static final String WATCH_TOWER_ID = "watch_tower_id";// 表名
		public static final int WATCH_TOWER_TABLE_ID = 635;// 表ID
		public final static String ATTACHMENT = "attachment"; // varchar 2000.00
																// null 附件
		public final static String NOTE = "note"; // varchar 200 null 备注
		public final static String BASE_STATION_NAME = "base_station_name";// 基站名称
		public final static String SPECIFIC_LOCATION = "specific_location";// 具体位置
		public final static String COORDINATES = "coordinates";// 经纬度
		public final static String ALTITUDE = "altitude"; // 海拔高度
		public final static String WATCH_TOWER_NORMS = "watch_tower_norms";// 瞭望塔规格
		public final static String TRANSPORT_MODE = "transport_mode";// 传输方式
		public final static String BUILD_TIME = "build_time";// 建塔时间
		public final static String LOCATION_ID = "location_id";// 定位

	}

	/*
	 * 违法用火行为人------防火工作
	 */
	public static class IllegalUseOfFireRegister implements BaseColumns {
		public static final String ILLEGAL_USE_FIRE_REGISTER_TABLE_NAME = "illegal_use_fire_register";
		public static final int ILLEGAL_USE_FIRE_REGISTER_TABLE_ID = 707;
		public static final String ILLEGAL_USE_FIRE_REGISTER_ID = "illegal_use_fire_register_id";// 序号
		public final static String VIOLATOR = "violator";// 违法行为人
		public final static String VIOLATOR_ID_CARD = "violator_id_card";// 身份证号
		public final static String VIOLATOR_ADD = "violator_add";// 现住址
		public final static String LUOF_TIME = "luof_time";// 违法用火时间
		public final static String PROCESSING_RESULTS = "processing_results";// 处理结果
		public final static String PENALTIES_TIME = "penalties_time";// 处罚时间
		public final static String ORGANIZED_POLICE = "organized_police";// 主办民警
		public final static String ATTACHMENT = "attachment";// 附件
		public final static String NOTE = "note";// 备注
		public final static String LOCATION_ID = "location_id";

	}

	/*
	 * 发生火情登记-----------防火工作
	 */
	public static class RegistrationAreaFire implements BaseColumns {
		public final static String REGISTRATION_AREA_FIRE_TABLE_NAME = "registration_area_fire";
		public final static int REGISTRATION_AREA_FIRE_TABLE_ID = 705;
		public final static String REGISTRATION_AREA_FIRE_ID = "registration_area_fire_id";
		public final static String RTSF_TIME = "rtsf_time";// 到达火情现场时间
		public final static String EF_TIME = "ef_time";// 扑灭明火时间
		public final static String TFO_PLACE = "tfo_Place";// 发生火情地点
		public final static String BURNED_AREA = "burned_area";// 过火面积
		public final static String ATTACHMENT = "attachment";// 附件
		public final static String NOTE = "note";// 备注
		public static final String LOCATION_ID = "location_id";

	}

	/*
	 * 辖区暂住人口登记表--------人口管理
	 */
	public static class TransientRegister implements BaseColumns {
		public static final String TRANSIENT_REGISTER_TABLE_NAME = "transient_register";// 表名
		public static final String TRANSIENT_REGISTER_ID = "transient_register_id";// 表Id
		public static final int TRANSIENT_REGISTER_TABLE_ID = 619;// 表Id
		public static final String LOCATION_ID = "location_id";
		public final static String ATTACHMENT = "attachment"; // varchar 2000.00
																// null 附件
		public final static String NOTE = "note"; // varchar 200 null 备注
		public final static String TRANSIENT_REGISTER_TIME = "transient_register_time";// 时间
		public final static String TRANSIENT_REGISTER_NAME = "transient_register_name";// 姓名
		public final static String TRANSIENT_REGISTER_SEX = "transient_register_sex";// 姓别
		public final static String TRANSIENT_REGISTER_BIRTHDAY = "transient_register_birthday";// 生日
		public final static String TRANSIENT_REGISTER_ADDRESS = "transient_register_address";// 暂住地址
		public final static String TRANSIENT_REGISTER_WORK = "transient_register_work";// 从事行业
		public final static String TRANSIENT_REGISTER_RESIDENCE = "transient_register_residence";// 户口所在地
		public final static String TRANSIENT_CONTACT_INFORMATION = "transient_contact_information";// 联系方式
		public final static String TRANSIENT_ID_CARD = "transient_id_card";// 身份证号码
		public final static String TRANSIENT_ARRIVE_TIME = "transient_arrive_time";// 到达时间
		public final static String TRANSIENT_LEAVE_TIME = "transient_leave_time";// 离开时间

	}

	/*
	 * 路线查询
	 */
	public static class PatrolRouteManagement implements BaseColumns {
		public static final String PATROL_ROUTE_MANAGEMENT_TABLE_NAME = "patrol_route_management";// 表名
		public static final String PATROL_ROUTE_MANAGEMENT_ID = "patrol_route_management_id";// 表自增长Id
		public static final int PATROL_ROUTE_MANAGEMENT_TABLE_ID = 740;// 表Id
		public static final String PATROL_ROUTE_MANAGEMENT_NAME = "name";// 路线名称
		public static final String PATROL_ROUTE_MANAGEMENT_TYPE = "type";// 线路类型
		public static final String PATROL_ROUTE_MANAGEMENT_DISTANCE = "distance";// 距离
		public static final String PATROL_ROUTE_MANAGEMENT_TRAVEL_ADVICE = "travel_advice";// 行驶建议
		public static final String PATROL_ROUTE_MANAGEMENT_ROUTE = "route";// 定位地点集合
		public static final String PATROL_ROUTE_MANAGEMENT_WEATHER = "weather";// 天气

	}

	/*
	 * 
	 * 消息推送--新闻公告
	 */
	public static class News implements BaseColumns {
		public static final String NEWS_TABLE_NAME = "news";// 表名
		public static final String NEWS_ID = "news_id";//
		public static final String NEWS_TITLE = "news_title";// 标题
		public static final String NEWS_CONTENT = "news_content";// 内容
		public final static String NEWS_ATTACHMENT = "affix"; // 附件varchar
																// 2000.00
	}

	/*
	 * 法律法规
	 */
	public static class LawsRegulations implements BaseColumns {
		public static final String LAWSREGULATIONS_TABLE_NAME = "laws_regulations";// 表名
		public static final int LAWSREGULATIONS_TABLE_ID = 55;
		public static final String LAWSREGULATIONS_ID = "laws_regulations_id";
		public static final String LAWSREGULATIONS_TITLE = "laws_regulations_title";// 标题
		public static final String LAWSREGULATIONS_CONTENT = "laws_regulations_content";// 表内容
		public final static String LAWSREGULATIONS_ATTACHMENT = "attachment"; // 附件varchar
	}

	/*
	 * 附件表
	 */
	public static class Attachment implements BaseColumns {
		public static final String ATTACHMENT_TABLE_NAME = "attachment"; // 表名

		public static final String ATTACHMENT_ID = "attachment_id"; // 表名
		public final static String TABLE_ID = "table_id"; // 对应表名
		public final static String ROW_ID = "row_id"; // 对应ID
		public final static String FILE_PATH = "file_path"; // varchar 20 not
															// null 路径
		public final static String FILE_TYPE = "file_type"; // varchar 10 not
															// null 类型
	}

	/*
	 * 定位表
	 */
	public static class Location implements BaseColumns {
		public static final String LOCATION_TABLE_NAME = "location"; // 表名
		public static final String LOCATION_ID = "location_id";
		// public final static String TABLE_ID = "table_id"; // 对应表名
		// public final static String ROW_ID = "row_id"; //对应ID
		public final static String LONGITUDE = "longitude"; // varchar 20 not
															// null 经度
		public final static String LATITUDE = "latitude"; // varchar 20 not null
															// 纬度
		public final static String ELEVATION = "elevation";// varchar 20 not
															// null 海拔
		public final static String TIME = "Time";// 时间
	}

	/*
	 * 选择项表
	 */
	public static class Option implements BaseColumns {
		public static final String OPTION_TABLE_NAME = "option"; // 表名
		public static final String OPTION_ID = "option_id"; // 表名
		public static final String OPTION_SERVER_ID = "option_server_id"; // 表名
		public static final String FORM_MAIN_ID = "form_main_id"; // 表名
		public static final String CTRL_VALUE = "ctrl_value"; // 表名
		public static final String DICT_NAME = "dict_name"; // 表名
		public static final String DICT_VALUE = "dict_value"; // 表名
		public static final String DICT_ORDER = "dict_order"; // 表名
		public static final String REMARK = "remark"; // 表名
		public static final String RELATION_DICT_LIST = "relation_dict_list"; // 表名
		public static final String CREATE_TIME = "create_time"; // 表名

	}

	/**
	 * 巡逻轨迹
	 */
	public static class LocationInfo implements BaseColumns {
		public static final String LOCATION_INFO_TABLE_NAME = "location_info";
		public static final String INFO_ID = "info_id";
		public static final String USER_ID = "user_id";
		public final static String LONGITUDE = "longitude"; // varchar 20 not //
															// null 经度
		public final static String LATITUDE = "latitude"; // varchar 20 not null
															// // 纬度
		public final static String ELEVATION = "elevation";// varchar 20 not //
															// null 海拔
		public final static String TIME = "time";// varchar 20 not 时间
		public final static String LOCATIONTYPE = "locationType";// varchar 20
																	// not 时间
	}

	/**
	 * 工作日志
	 * 
	 * @author xingyimin
	 * 
	 */
	public static class TheWorkingLog implements BaseColumns {
		public static final String THE_WORKING_LOG_TABLE_NAME = "the_working_log";// 表名
		public static final int THE_WORKING_LOG_TABLE_ID = 668;// 表Id
		public static final String THE_WORKING_LOG_ID = "the_working_log_id";
		public static final String THE_WORKING_LOG_DATE = "the_working_log_date";// 日期
		public static final String POLICE_ON_DUTY = "police_on_duty";// 值班民警
		public static final String WEATHER = "weather";// 天气
		public static final String CLASS_LEADERS = "class_leaders";// 带班领导
		public static final String THE_MAIN_WORK_AND_IMPORTANT_EVENTS = "the_main_work_and_important_events";// 当天主要工作情况及重要事件
	}

	/**
	 * 便签
	 * 
	 * @author liupeng
	 * 
	 */
	public static class Note implements BaseColumns {
		public static final String NOTE_TABLE_NAME = "note";
		public static final String NOTE_ID = "NotepadId";
		public static final String USER_ID = "UsersId";
		public static final String NOTE_LOG_DATE = "LogDate";
		public final static String CONTENT = "Info"; // varchar 20 not // null
														// 经度
	}

	/**
	 * 辖区重点人口情况登记表
	 */
	public static class KeyPopulation implements BaseColumns {
		public static final String KEY_POPULATION_TABLE_NAME = "key_population";// 表名
		public static final String KEY_POPULATION_ID = "KeyPpulationId";
		public static final int KEY_POPULATION_TABLE_ID = 620;// 表ID
																// catalogID=620
		public static final String LOCATION_ID = "location_id";
		public static final String KEY_TIME = "Time";// 时间 datatime varchar 20
														// not null
		public static final String KEY_NAME = "Name";// 姓名 varchar 200 not null
		public static final String KEY_NICKNAME = "NickName";// 绰号 varchar 200
																// not null
		public static final String KEY_SEX = "Sex";// 性别 char 2 not null
		public static final String KEY_BIRTHDAY = "Birthday";// 出生日期 datatime
																// varchar 20
																// not null
		public static final String KEY_ADDRESS = "Address";// 住址或服务处住所 varchar
															// 200 not null
		public static final String KEY_MANAGE_TIME = "ManageTime";// 列管时间
																	// datatime
																	// varchar
																	// 20 not
																	// null
		public static final String KEY_REPEAL_TIME = "RepealTime";// 撤管时间
																	// datatime
																	// varchar
																	// 20 not
																	// null
		public static final String KEY_MANAGE_CAUSE = "ManageCause";// 列管原因
																	// varchar
																	// 200 not
																	// null
		public static final String KEY_FILE_CODE = "FileCode";// 档案编号 varchar 20
																// not null
		public final static String ATTACHMENT = "attachment"; // varchar 2000.00
																// // null 附件
		public final static String NOTE = "note"; // varchar 200 null 备注

	}

	/**
	 * 护林基本力量
	 * 
	 * @author xingyimin
	 */
	public static class ForestPotection implements BaseColumns {
		public static final String FOREST_POTECTION_TABLE_NAME = "forest_potection";// 表名
		public static final String FOREST_POTECTION_TABLE_ID = "forest_potection_id";
		public static final String FOREST_POTECTION_ID = "forestPotectionID";// ID
		public static final int CATALOG_ID = 658;// 表ID
		public static final String YEAR = "year";// 年度
		public static final String ADMINISTRATION = "administration";// 主管部门
		public static final String UNIT = "unit";// 单位
		public static final String OFFICIAL_RANK = "officialRank";// 职别
		public static final String NAME = "name";// 姓名
		public static final String GENDER = "gender";// 性别
		public static final String AGE = "age";// 年龄
		public static final String TEL = "tel";// 联系电话
		public static final String MANAGEMENT_AREA = "managementArea";// 管护责任区
		public static final String RESPONSIBILITY = "responsibility";// 具体责任
		public static final String ATTACHMENT = "attachment";// 附件[{fileBase64Str;//文件Base64
																// fileName;//文件名
																// filetype;//文件类型
																// } ]
		public static final String LOCATION_ID = "location_id";// 坐标 :{
																// X;//经度
																// Y;//纬度
																// Z;//高度
																// Time;//时间}
		public static final String REMARK = "remark";// 备注

	}

	/**
	 * 涉林重点行业情况
	 * 
	 * @author xingyimin
	 */
	public static class ForestryKeyIndustries implements BaseColumns {
		public static final String FORESTRY_KEY_INDUSTRIES_TABLE_NAME = "forestry_key_industries";
		public static final String FORESTRY_KEY_INDUSTRIES_TABLE_ID = "forestry_key_industries_id";
		public static final String FORESTRY_KEY_INDUSTRIES_ID = "forestryKeyIndustriesID";// ID
		public static final int CATALOG_ID = 674;// 表ID
		public static final String UNIT_NAME = "unitName";// 单位名称
		public static final String ADDRESS = "address";// 地址
		public static final String LICENSE = "license";// 证照
		public static final String OPERATE_TYPE = "operateType";// 经营行业、种类、名称
		public static final String PRINCIPAL = "principal";// 负责人及联系方式
		public static final String CONTACT_INFORMATION = "contactInformation";// 管理人及联系方式
		public static final String VARIETIES_SOURCESS = "varietiesSourcess";// 品种来源
		public static final String PROTECTION_LEVEL = "protectionLevel";// 保护级别
		public static final String PRACTITIONER_NUMBER = "practitionerNumber";// 行业人员数量
		public static final String ATTACHMENT = "attachment";// 附件[
																// {fileBase64Str;//文件Base64
																// fileName;//文件名
																// filetype;//文件类型
																// } ]
		public static final String LOCATION = "location_id";// 坐标//location_id
															// :{ X;//经度
															// Y;//纬度
															// Z;//高度
															// Time;//时间}
		public static final String REMARK = "remark";// 备注

	}

	/**
	 * 盘问情况
	 * 
	 * @author xingyimin
	 */
	public static class QuestionedSituation implements BaseColumns {
		public static final String QUESTIONED_SITUATION_TABLE_NAME = "questioned_situation";
		public static final String QUESTIONED_SITUATION_TABLE_ID = "questioned_situation_id";// 表Id
		public static final String QUESTIONED_SITUATION_ID = "questionedSituationID";// serverID
		public static final int CATALOG_ID = 685;// 表ID
		public static final String YEAR = "year";// 年度
		public static final String NAME = "name";// 被盘问人姓名
		public static final String GENDER = "gender";// 性别
		public static final String BIRTHDAY = "birthday";// 出生年月
		public static final String DOMICILE_PLACE = "domicilePlace";// 户口所在地
		public static final String UNIT_OR_ADDRESS = "unitOrAddress";// 单位或地址
		public static final String ASK_WHY = "askWhy";// 盘问原因
		public static final String ASK_BEGIN_TIME = "askBeginTIme";// 继续盘问开始时间
		public static final String ASK_END_TIME = "askEndTIme";// 继续盘问结束时间
		public static final String SPONSOR = "sponsor";// 主办人
		public static final String OVERTIME = "overtime";// 延长时间
		public static final String ALLOW = "allow";// 上级机关批准人
		public static final String HANDING_INFORMATION = "handingInformation";// 处理情况
		public static final String ATTACHMENT = "attachment";// 附件[
																// {fileBase64Str;//文件Base64
																// fileName;//文件名
																// filetype;//文件类型
																// } ]
		public static final String REMARK = "remark";// 备注

	}

	/**
	 * 重点列管人员（单位）情况
	 * 
	 * @author xingyimin
	 */
	public static class ManagedObject implements BaseColumns {
		public static final String MANAGED_OBJECT_TABLE_NAME = "managedObject";// 表名
		public static final String MANAGED_OBJECT_TABLE_ID = "managedObject_id";// 表Id
		public static final String MANAGED_OBJECT_ID = "managedObjectID";// ID
		public static final int catalogID = 680;// 表ID
		public static final String GENDER = "gender";// 性别
		public static final String UNIT_NAME = "unitName";// 姓名或单位名称
		public static final String DOMICILE_PLACE = "domicilePlace";// 户籍地
		public static final String CARD_ID = "cardID";// 身份证或者执照号码
		public static final String ADDRESS_OR_UNIT = "addressOrUnit";// 现住址或单位
		public static final String CAREER = "career";// 职业
		public static final String MANAGED_CAUSE = "managedCause";// 列管原因
		public static final String SPECIFIC_CAUSES = "specificCauses";// 何时何因被何部门给何处罚
		public static final String MANAGED_TIME = "managedTIme";// 列管时间
		public static final String RESPONSIBILITY_POLICE = "responsibilityPolice";// 负责民警
		public static final String ATTACHMENT = "attachment";// 附件
		public static final String LOCATION = "location_id";// 坐标
		public static final String REMARK = "remark";// 备注
	}

	/**
	 * 重点列管人员走访
	 * 
	 * @author xingyimin
	 */
	public static class Interview implements BaseColumns {
		public static final String INTERVIEW_TABLE_NAME = "interview";// 表名
		public static final String INTERVIEW_TABLE_ID = "interview_id";
		public static final String INTERVIEW_ID = "interviewID";// ID
		public static final int catalogID = 731;// 表ID
		public static final String INTERVIEW_TIME = "interviewTime";// 走访时间
		public static final String INTERVIEW_SITE = "interviewSite";// 走访地点
		public static final String INTERVIEW_POLICE = "interviewPolice";// 走访民警
		public static final String INTERVIEW_OBJECT = "interviewObejct";// 走访对象
		public static final String UNIT_NAME = "unitName";// 单位名称
		public static final String UNIT_ADDRESS = "unitAddress";// 单位地址
		public static final String NAME = "name";// 性名
		public static final String GENDER = "gender";// 性别
		public static final String AGE = "age";// 年龄
		public static final String ADDRESS = "address";// 家庭住址
		public static final String MANAGED_CAUSE = "managedCause";// 列管原因
		public static final String INTERVIEW_CONTENT = "interviewContent";// 走访内容
		public static final String OPINION = "opinion";// 走访对象意见或建议及反映的其它问题
		public static final String ATTACHMENT = "attachment";// 附件
		public static final String LOCATION = "location_id";// 坐标
		public static final String REMARK = "remark";// 备注

	}

	/**
	 * 养殖情况
	 * 
	 * @author zhengtian
	 */
	public static class Breed implements BaseColumns {
		public static final String BREED_TABLE_NAME = "breed";// 表名
		public static final String BREED_ID = "breedID";
		public static final String BREED_TABLE_ID = "breed_Id";
		public static final int CATALOG_ID = 677;// catalogID
		public static final String ACQUISITIONTIME = "acquisitionTime";// 信息采集时间
		public static final String UNITNAME = "unitName";// 单位名称
		public static final String BREEDNAME = "breedName";// 养殖户名
		public static final String BREEDCOUNT = "breedCount";// 存栏数
		public static final String BREEDTYPE = "breedType";// 主要种类
		public static final String BREEDMODE = "breedMode";// 养殖方式
		public static final String REMARK = "remark";// 备注
		public static final String LOCATION_ID = "location_id";
		public final static String ATTACHMENT = "attachment";// varchar 2000.00
																// null 附件

	}

	/**
	 * 情报工作
	 * 
	 * @author zhengtiantian
	 */
	public static class Itelligence implements BaseColumns {
		public static final String ITELLIGENCE_TABLE_NAME = "Itelligence";// 表名
		public static final String ITELLIGENCE_ID = "itelligenceID";// 表名
		public static final String ITELLIGENCE_TABLE_ID = "itelligence_Id";
		public static final int CATALOG_ID = 203;// catalogID
		public static final String ACQUISITIONTIME = "acquisitionTime";// 收集时间
		public static final String ACQUISITIONPOLICE = "acquisitionPolice";// 收集民警
		public static final String INFORMATIONSOURCE = "informationSource";// 信息来源
		public static final String INFORMATIONTYPE = "informationType";// 信息类别
		public static final String INFORMATIONABSTRACT = "informationAbstract";// 信息摘要
		public static final String REPORTEDUNIT = "reportedUnit";// 上报单位
		public static final String REPORTEDTIME = "reportedTime";// 上报时间
		public static final String FEEDBACK = "feedback";// 反馈意见
		public final static String ATTACHMENT = "attachment"; // varchar 2000.00
																// null 附件
	}

	/**
	 * 木材采伐场（点）登记
	 * 
	 * @author zhengtiantian
	 */
	public static class WoodCutting implements BaseColumns {
		public static final String WOODCUTTING_TABLE_NAME = "WoodCutting";// 表名
		public static final String WOODCUTTING_ID = "woodCuttingID";// ID
		public static final String WOODCUTTING_TABLE_ID = "woodCutting_Id";
		public static final int CATALOG_ID = 670;// catalogID 表ID
		public static final String CUTTINGUNIT = "cuttingUnit";// 采伐单位或个人
		public static final String CUTTINGLOCATION = "cuttingLocation";// 采伐地点
		public static final String CUTTINGCARDID = "cuttingCardID";// 采伐证号
		public static final String ALLOWCOUNT = "allowCount";// 批准采伐数量
		public static final String CUTTINGSPECIES = "cuttingSpecies";// 采伐树种
		public static final String CUTTINGMODE = "cuttingMode";// 采伐方式
		public static final String CUTTINGCOUNT = "cuttingCount";// 进入林区采伐人员数
		public static final String CUTTINGBEGINTIME = "cuttingBeginTime";// 开始采伐时间
		public static final String CUTTINGENDTIME = "cuttingEndTime";// 采伐结束时间
		public static final String INSPECTION = "inspection";// 检查情况
		public static final String LOCATION_ID = "location_id";
		public final static String ATTACHMENT = "attachment"; // varchar 2000.00
																// null 附件
	}

	/**
	 * 社会情况统计
	 * 
	 * @author zhengtiantian
	 */
	public static class SocialStatistics implements BaseColumns {
		public static final String SOCIA_TABLE_NAME = "SocialStatistics";// 表名
		public static final String SOCIASTATISTICS_ID = "socialStatisticsID";// ID
		public static final String SOCIASTATISTICS_TABLE_ID = "socialStatistics_Id";
		public static final int CATALOG_ID = 672;// catalogID 表ID
		public static final String UNIT = "unit";// 单位名称
		public static final String TOTALAREA = "totalArea";// 总面积
		public static final String FAMILYCOUNT = "familyCount";// 户数合计
		public static final String AGRICULTUREFAMILY = "agricultureFamily";// 农业户
		public static final String NONAGRICULTURALFAMILY = "nonAgriculturalFamily";// 非农业户
		public static final String PROTECTFAMILY = "protectFamily";// 重点保护户
		public static final String TEMPORARYFAMILY = "temporaryFamily";// 外来临时户
		public static final String POPULATIONCOUNT = "populationCount";// 人口总数
		public static final String ARRICULTUREPOPULATION = "arriculturePopulation";// 农业人口
		public static final String NONARRICULTUREALPOPULATION = "nonArriculturealPopulation";// 非农业人口
		public static final String TEMPORARYPOPULATION = "temporaryPopulation";// 流动人口
		public static final String EMPHASISUNITCOUNT = "emphasisUnitCount";// 重点单位合计
		public static final String WOODPROCESSINGUNIT = "woodProcessingUnit";// 木材加工
		public static final String WOODPURCHASEUNIT = "woodPurchaseUnit";// 木材收购
		public static final String QUARRYINGUNIT = "quarryingUnit";// 采石
		public static final String COLLIERYUNIT = "collieryUnit";// 煤矿
		public static final String FLAYSTONEUNIT = "flaystoneUnit";// 石板
		public static final String WILDANIMALUNIT = "wildAnimalUnit";// 野生动物经营
		public static final String TRAVELUNIT = "travelUnit";// 旅游区
		public static final String CONTRACTOR = "contractor";// 承包人
		public static final String PRINCIPAL = "principal";// 学校校长
		public static final String GRAZINGPERSONNEL = "grazingPersonnel";// 放牧人员
		public static final String EMPLOYER = "employer";// 雇主
		public static final String GRAVEMASTER = "graveMaster";// 坟主
		public static final String MORONISMMANAGER = "moronismManager";// 呆傻监护人
		public static final String LOCATION_ID = "location_id";
		public final static String ATTACHMENT = "attachment"; // varchar 2000.00
																// null 附件
	}

	/**
	 * 林地内开采沙、土、石、矿情况表
	 * 
	 * @author zhengtiantian
	 */
	public static class WoodlandMining implements BaseColumns {
		public static final String WOODLANDMINING_TABLE_NAME = "WoodlandMining";// 表名
		public static final String WOODLANDMINING_ID = "woodlandMiningID";// 表ID
		public static final String WOODLANDMINING_TABLE_ID = "woodlandMining_Id";
		public static final int CATALOG_ID = 669;// catalogID 表ID
		public static final String SPECIFICLOCATION = "specificLocation";// 具体位置
		public static final String MININGTYPE = "miningType";// 开采类型
		public static final String MININGAREA = "miningArea";// 开采面积
		public static final String FALLIMENTIDELLO = "fallimentiDello";// 破坏林木情况
		public static final String ALLOWUNIT = "allowUnit";// 批准部门
		public static final String LOCATION_ID = "location_id";
		public final static String ATTACHMENT = "attachment"; // varchar 2000.00
																// null 附件
		public final static String REMARK = "remark";// 备注
	}

	/**
	 * 定位表
	 * 
	 * @author zhengtiantian
	 */
	public static class Locations implements BaseColumns {
		public static final String LOCATION_TABLE_NAME = "location"; // 表名
		public static final String LOCATION_ID = "location_id";
		public final static String LONGITUDE = "X"; // varchar 20 not //
													// null 经度
		public final static String LATITUDE = "Y"; // varchar 20 not null
													// // 纬度
		public final static String ELEVATION = "Z";// varchar 20 not //
													// null 海拔
		public final static String TIME = "Time";// 时间
	}

	/**
	 * 通知时间
	 * 
	 * @author xingyimin
	 * 
	 */
	public static class NoticeTime implements BaseColumns {
		public static final String NOTICE_TABLE_NAME = "natice_the_time";
		public static final String NOTICE_TABLE_ID = "notice_id";
		public static final String NOTICE_NEWS_ID = "notice_news_id";
		public static final String NOTICE_TYPE = "notice_type";
		public static final String NOTICE_TIME = "notice_time";
		public static final String NOTICE_CONTENT = "notice_content";
	}

	/**
	 * 三情--林情
	 * 
	 * @author liming
	 * */
	public static class ForestryConditions implements BaseColumns {
		public static final int KEY_FORESTRY_CONDITIONS_ID = 191; // 表ID
		public static final int DEPID = 3;// 部门ID

		public static final String FORESTRY_CONDITIONS_TABLE_NAME = "forestry_condition";// 表名
		public static final String LQWOODLANDAREA = "lqwoodlandArea";// 林地面积
		public static final String LQFORESTSTOCKVOLUME = "lqforeststockvolume";// 林地蓄积力量
		public static final String LQMAJORAREASOFWILDLIFE = "lqmajorareasofwildlife";// 野生动物主要活动地区
		public static final String LQFORESTHIGHAREAS = "lqforesthighareas";// 涉林案件高发区
		public static final String LQWILDANIMALMARKET = "lqwildanimalmarket";// 野生动物市场
		public static final String LQSITUATIONPROTECTION = "lqsituationprotection";// 生态管护员情况
		public static final String LQGRAZINGFARMERS = "lqgrazingfarmers";// 放牧养殖户
		public static final String LQSEMINUMBER = "lqseminumber";// 半专业数量及人数
		public static final String LQPROFESSIONALNUMBER = "lqprofessionalnumber";// 专业数量及人数
		public static final String LQFORESTTYPES = "lqforesttypes";// 森林的种类
		public static final String LQAGECOMPOSITION = "lqagecomposition";// 树龄组成
		public static final String LQWILDANIMALSPECIES = "lqwildanimalspecies";// 野生动物种类
		public static final String LQTREESPECIES = "lqtreespecies";// 林木种类
		public static final String LQOLDFAMOUSTREES = "lqoldfamoustrees";// 古树名木
		public static final String LQKEYCOLUMNMANAGEMENT = "lqkeycolumnmanagement";// 重点列管人员
		public static final String LQTYPESFORESTL = "lqtypesforestl";// 林业用地的种类
		public static final String FORESTRY_ID = "id";
		public static final String CATALOGID = "catalogid";// 功能id
		public static final String USERID = "userID";// 用户ID
		public static final String FORESTRY_CONDITIONS_ID = "_id";// 自增长ID
		public static final String VILLAGE_NAME = "village_name";// 村名

	}

	/**
	 * 三情--山情
	 * 
	 * @author liming
	 * 
	 * 
	 * */
	public static class MountConditions implements BaseColumns {
		public static final int KEY_MOUNT_CONDITIONS_ID = 746; // 表ID
		public static final int DEPID = 3;// 部门ID
		public static final String KEY_MOUNT_CONDITIONS_NAME = "mount_conditions";// 表名
		public static final String MOUNT_ID = "mount_id";// 自增长id
		public static final String FILETYPE = "fileType";// 文件类型
		public static final String THUMBNAIL = "thumbnail";// 图片名称
		public static final String THUMBNAILTYPE = "thumbnailtype";// 图片类型
		public static final String SQADMINISTRATIVEAREA = "sqadministrativearea";// 行政区域总面积(公顷)
		public static final String SQTOPOGRAPHYGEOMORPHOLOGY = "sqtopographygeomorphology";// 地形地貌
		public static final String SQLTITUDE = "sqltitude";// 海拔
		public static final String SQLATITUDELONGITUDE = "sqlatitudelongitude";// 经纬度
		public static final String SQCOMPLETELY = "sqcompletely";// 截然地区(四至)
		public static final String SQRIVERLAKEPOSITION = "sqriverlakeposition";// 河流湖泊位置
		public static final String SQTRAFFICROADDISTRIBUTION = "sqtrafficroaddistribution";// 交通道路分布
		public static final String SQREGIONALSPECIALTY = "sqregionalspecialty";// 地区特产
		public static final String SQMINERALDISTRIBUTION = "sqMineraldistribution";// 矿产资源分布
		public static final String TABLE_ID = "table_id";
		public static final String ID = "id";
		public static final String VILLAGE_NAME = "village_name";// 村名
	}

	/**
	 * 三情--社情
	 * 
	 * @author liming
	 * 
	 * */
	public static class SheConditions implements BaseColumns {
		public static final int KEY_SHE_CONDITIONS_ID = 747; // 表ID
		public static final String KEY_SHE_CONDITONS_NAME = "she_conditons";
		public static final int DEPID = 3;// 部门ID
		public static final String SHE_ID = "she_id";// 自增长id
		public static final String TABLE_ID = "table_id";// 表id
		public static final String FILETYPE = "fileType";// 文件类型
		public static final String THUMBNAIL = "thumbnail";// 图片名称
		public static final String THUMBNAILTYPE = "thumbnailtype";// 图片类型
		public static final String SHQLANDCONTRACT = "shqlandcontract";// 土地承包情况
		public static final String SHQVILLAGECONTACT = "shqvillagecontact";// 村领导干部及联系方式
		public static final String SHQSTAYSILLYPATIENT = "shqstaysillypatient";// 呆、傻精神病人情况
		public static final String SHQMAJORINDUSTRY = "shqmajorindustry";// 主要餐饮业情况
		public static final String SHQTOMBCUSTOMS = "shqtombcustoms";// 清明节扫墓时间及风俗
		public static final String SHQTRAFFICDISTRIBUTION = "shqtrafficdistribution";// 交通分布情况
		public static final String SHQTRANSIENTPOPULATION = "shqtransientpopulation";// 暂住人口数
		public static final String SHQRESIDENTPOPULATION = "shqresidentpopulation";// 居民人口数
		public static final String SHQRESIDENTVILLAGE = "shqresidentvillage";// 行政村常住人口数
		public static final String SHQCULTURALSITES = "shqculturalsites";// 文物古迹
		public static final String SHQMAJORSOURCES = "shqmajorsources";// 主要产业及经济来源
		public static final String SHQCHARACTERISTICPROPERTY = "shqcharacteristicproperty";// 特色物产
		public static final String ID = "id";// 部门ID
		public static final String VILLAGE_NAME = "village_name";// 村名
	}

	// 保存巡逻登记坐标点
	public static class PatrolRegisterLocation implements BaseColumns {
		public static final String PATROLREGISTER_TABLE_NAME = "patrolregister";// 表名
		public static final String ID = "_id";// 自增长列
		public static final String USER_ID = "user_id";// userid列
		public static final String LONGITUDE = "longitude";// 经度
		public static final String LATITUDE = "latitude";// 纬度
		public static final String ELEVATION = "elevation";// 海拔
		public static final String TIME = "time";// 时间
		public static final String LOCATIONTYPE = "locationType";// 定位的坐标的方式
		public static final String GUID = "guid";// 定位的坐标的方式
	}

	/**
	 * 路线管理--路线坐标轨迹点
	 */
	public static class RouteInfo implements BaseColumns {
		public static final String ROUTE_INFO_TABLE_NAME = "route_info";
		public static final String ID = "_id";// 自增长列
		public static final String INFO_ID = "info_id";
		public static final String USER_ID = "user_id";
		public static final String GUID = "guid";
		public final static String LONGITUDE = "longitude"; // varchar 20 not //
															// null 经度
		public final static String LATITUDE = "latitude"; // varchar 20 not null
															// // 纬度
		public final static String ELEVATION = "elevation";// varchar 20 not //
															// null 海拔
		public final static String TIME = "time";// varchar 20 not 时间
		public final static String LOCATIONTYPE = "locationType";// varchar 20
																	// not 时间
		public final static String STATUS = "status";// 数据状态 0:初始状态 1:删除
	}

	/**
	 * 报警信息--下拉菜单
	 */
	public static class AlarmSpinnerInfo implements BaseColumns {
		public static final String ALARM_SPINNER_INFO_TABLE_NAME = "alarm_spinner_info";// 表名
		public static final String ID = "id";// 自增长列
		public static final String ALARMWAY_ID = "alarmway_ID";// 报警方式ID
		public static final String ALARMWAY_NAME = "alarmwayname";// 报警方式
		public static final String NATION_ID = "nation_ID";// 民族ID
		public static final String NATION_NAME = "nationname";// 民族
		public static final String APPROVALPEOPLEID = "approvalPeopleID";// 审批领导ID
		public static final String LEADER = "leader";// 审批领导
		public static final String PARTS_CULTURE_ID = "parts_culture_ID";// 学历
		public static final String PARTS_CULTURE = "parts_culture";// 学历
		public static final String PARTS_POST_ID = "parts_post_ID";// 职务
		public static final String PARTS_POST = "parts_post";// 职务
		public static final String TREATMENT_ADVICE_ID = "treatmentAdviceID";// 处理意见
		public static final String TREATMENT_ADVICE = "treatmentAdvice";// 处理意见
		public static final String HOST_POLICE_ID = "hostPolice";// 主办民警ID
		public static final String COPOLICE_ID = "coPolice";// 协办民警
		public static final String STATE_ID = "state_ID";// 审批状态ID
		public static final String STATE_NAME = "stateName";// 审批状态
	}

	/**
	 * 接处警历史信息
	 */
	public static class AlarmingHistoryInfo implements BaseColumns {
		public static final String ALARMING_HISTORY_INFO_TABLE_NAME = "alarming_history_info";// 表名
		public static final String ID = "id";// 自增长列
		public static final String TIME = "time";// 报警时间
		public static final String HISOPTION = "hisOpinion";// 意见
		public static final String ALARM_STATE = "alarmState";// 审批状态
		public static final String POLICE = "police";// 审批人
		public static final String ALARMID = "AlarmID";// 编号
	}
}
