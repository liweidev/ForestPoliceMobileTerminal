package com.yhkj.yhsx.forestpolicemobileterminal.utils;

import android.R.anim;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.ApiSettingActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.LoginActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.PublicSecurityManagementActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter.AnimalInformationDetailActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter.ForestryKeyIndustriesQueryActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter.ItelligenceInformationActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter.ManagedObjectListActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter.PlantProtectDetailActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter.SocialStatisticsListActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter.WoodCuttingListActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.basic_parameter.WoodlandMiningListActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.OptionEntity;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Activity工具类
 *
 * @author liupeng
 */
public   class ActivityUtils {

    private static Dialog builder;

    private static ProgressDialog m_pDialog;

    private static ActivityUtils aUtility;

    private static Context ctx;

    /**
     * 是否处于调试或者开发模式
     */
    public static boolean ISDEBUG = false;

    /**
     * 初始化方法，只有一个实体类存在
     *
     * @param context
     * @return
     */
    public static ActivityUtils init(Context context) {
        if (aUtility == null) {
            aUtility = new ActivityUtils(context);
        } else {
            if (null != context) {
                aUtility.ctx = context;
            }
        }
        return aUtility;
    }

    public static ActivityUtils getInstance() {
        return aUtility;
    }

    /**
     * 构造方法
     */
    private ActivityUtils(Context context) {
        // TODO Auto-generated constructor stub
        this.ctx = context;
        ISDEBUG = isApkDebugable(context);
    }

    /**
     * 但是当我们没在AndroidManifest.xml中设置其debug属性时:
     * 使用Eclipse运行这种方式打包时其debug属性为true,使用Eclipse导出这种方式打包时其debug属性为法false.
     * 在使用ant打包时，其值就取决于ant的打包参数是release还是debug.
     * 因此在AndroidMainifest.xml中最好不设置android:debuggable属性置，而是由打包方式来决定其值.
     *
     * @param context
     * @return
     */
    private boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 进度条
     *
     * @param context
     * @param message
     */
    public static ProgressDialog showProgress(Context context, String message) {
        if (null != m_pDialog) {
            if (m_pDialog.isShowing()) {//销毁掉之前的显示的加载窗
                return m_pDialog;
            }
        }
        m_pDialog = new ProgressDialog(context, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);

        // 设置进度条风格，风格为圆形，旋转的
        m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // 设置ProgressDialog 提示信息
        m_pDialog.setMessage(message);

        // 设置ProgressDialog 的进度条是否不明确
        m_pDialog.setIndeterminate(true);

        // 设置ProgressDialog 是否可以按退回按键取消
        m_pDialog.setCancelable(false);

        // 让ProgressDialog显示
        m_pDialog.show();

        return m_pDialog;
    }

    public static void hideProgress() {
        m_pDialog.dismiss();
    }

    /**
     * Activity跳转动画
     *
     * @param ct
     */
    public static void starAnimForActivity(Activity ct) {
        ct.overridePendingTransition(anim.slide_in_left, anim.slide_out_right);
    }

    /*
     * 保存图片并返回图片路径
     */
    public static ArrayList<String> saveAccessoryToFile(ArrayList<String> photoList,
                                                        Activity context, int requestCode, Intent intent) {
        ArrayList<String> list = new ArrayList<String>();
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String name = "" + c.get(Calendar.YEAR) + c.get(Calendar.MONTH)
                + c.get(Calendar.DAY_OF_MONTH) + c.get(Calendar.HOUR_OF_DAY)
                + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND) + ".jpg";

        String string = null;
        if (photoList == null) {
            photoList = new ArrayList<String>();
        }

        switch (requestCode) {
            case 0:
            /*
             * Bundle bundle = intent.getExtras(); bitmap = (Bitmap)
			 * bundle.get("data");
			 */
                File fileDir = Environment.getExternalStorageDirectory();
                String fromFile = fileDir.getPath() + "/southwest/123.jpg" /*
                                                                         * +
																		 * name
																		 */;
                string = fileDir.getPath() + "/southwest/" + name;
                list.add(string);
            /*
             * try { File file = new File(fileDir.getPath() + "/forestPolice/");
			 * if (!file.exists()) { file.mkdir(); } fos = new
			 * FileOutputStream(new File(string));
			 * bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			 *
			 * } catch (FileNotFoundException e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); } finally { try { fos.flush();
			 * fos.close(); bitmap.recycle(); } catch (IOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 *
			 * }
			 */
                copyfile(new File(fromFile), new File(string), true);
                break;
            case 2:
            /*
             * Uri ingredientImg = intent.getData(); Cursor cursor =
			 * context.getContentResolver().query(ingredientImg, null, null, null, null);
			 * cursor.moveToFirst(); string = cursor.getString(1); // 图片文件路径
			 * cursor.close();
			 */

                list = (ArrayList<String>) intent.getSerializableExtra("photolist");
                break;

            default:
                break;
        }
        for (int i = 0; i < list.size(); i++) {
            photoList.add(list.get(i));
        }
        // Log.i("liupeng", "path   :   " + string);
        return photoList;
    }

    /**
     * 获取当前APP版本号
     * @return　当前APP版本号
     */
    public String getVersionCode(){
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 复制图片
     */
    private static void copyfile(File fromFile, File toFile, Boolean rewrite) {

        if (!fromFile.exists()) {
            return;
        }

        if (!fromFile.isFile()) {
            return;
        }
        if (!fromFile.canRead()) {
            return;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }

        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);

            byte[] bt = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            // 关闭输入、输出流
            fosfrom.close();
            fosto.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public static ArrayList<Integer> li;


    public static boolean isGPSOpen(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps && (gps || network);
    }

    /*
     * 判断是否登陆过
     */
    public static boolean isLogin(Context context) {
        SharedPreferences sp = context.getSharedPreferences("USER", Activity.MODE_PRIVATE);
        return sp.getInt("userID", 0) != 0;
    }

    public static int getUseId(Context ct) {
        SharedPreferences sp = ct.getSharedPreferences("USER", Activity.MODE_PRIVATE);
        return sp.getInt("userID", 0);
    }

    /*
     * 提示对话框，进入登陆
     */
    /*
     * public static void showLogin(final Context context) {
	 *
	 * new AlertDialog.Builder(context).setMessage("以下操作需用用户登陆")
	 * .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	 *
	 * @Override public void onClick(DialogInterface arg0, int arg1) { // TODO
	 * Auto-generated method stub Intent it = new Intent(context,
	 * LoginActivity.class); context.startActivity(it); }
	 * }).setNegativeButton("取消", null).create().show(); }
	 */
    /*
     * 提示对话框，进入登陆
	 */
    public static void showGPSSetting(final Context context) {

        new AlertDialog.Builder(context).setMessage("此功能需要精确的定位服务，请您在室外在位置设置中打开GPS！")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        ActivityUtils.openGPS(context);
                    }
                }).setNegativeButton("确定", null).create().show();
    }


    /**
     * 获取手机IMEI
     *
     * @param context
     * @return
     */
    public static String getDeviceID(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        // Toast.makeText(context, "IMEI   :   "+tm.getDeviceId(),
        // Toast.LENGTH_LONG).show();
        return tm.getDeviceId();
    }

    /**
     * @param context
     */
    public static void openGPS(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
            ActivityUtils.starAnimForActivity((Activity) context);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
            intent.setAction(Settings.ACTION_SETTINGS);
            try {
                context.startActivity(intent);
                ActivityUtils.starAnimForActivity((Activity) context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param imgPath
     * @param bitmap
     * @return
     */
    public static String imgToBase64(String imgPath, Bitmap bitmap) {
        if (imgPath != null && imgPath.length() > 0) {
            bitmap = readBitmap(imgPath);
        }
        if (bitmap == null) {
            // bitmap not found!!
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * @param imgPath
     * @return
     */
    public static Bitmap readBitmap(String imgPath) {
        try {
            return BitmapFactory.decodeFile(imgPath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }

    }


    public static double getDateProgress(String dateString, int field) {
        double progress = 0;
        String format = "yyyy-MM-dd";
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);
        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = dateformat.parse(dateString);
            calendar.setTime(date);

            int day = calendar.get(field);
            int dayOfMonthCount = calendar.getActualMaximum(field);

            progress = ((double) day / (double) dayOfMonthCount);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return progress * 100;
    }

    public static int getDisplayWidth(Context ct) {
        WindowManager wm = (WindowManager) ct.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }



    /**
     * 当前日期
     *
     * @param date 日期字符串
     */
    public Calendar getData(String date) {
        String format = "yyyy-MM-dd";
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);
        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        Date date1;
        try {
            if (date != null) {
                date1 = dateformat.parse(date);
                calendar.setTime(date1);
            } else {
                calendar = currentCalendar;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return calendar;
    }

    /**
     * 当前日期
     *
     * @param date 日期字符串
     */
    public String getDataForYear(String date) {
        String format = "yyyy-MM-dd";
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);
        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        Date date1;
        try {
            if (date != null) {
                date1 = dateformat.parse(date);
                calendar.setTime(date1);
            } else {
                calendar = currentCalendar;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日,";
    }

    // 创建文件夹及文件
    public static void CreateText(String filenameTemp) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory().getPath());
        if (!file.exists()) {
            try {
                // 按照指定的路径创建文件夹
                file.mkdirs();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        File dir = new File(filenameTemp);
        if (!dir.exists()) {
            try {
                // 在指定的文件夹中创建文件
                dir.createNewFile();
            } catch (Exception e) {
            }
        }

    }

    // 向已创建的文件中写入数据
    public static void print(String str, String filenameTemp) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        String datetime = "";
        try {
            CreateText(filenameTemp);
            SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
            datetime = tempDate.format(new Date()).toString();
            fw = new FileWriter(filenameTemp, true);//
            // 创建FileWriter对象，用来写入字符流
            bw = new BufferedWriter(fw); // 将缓冲对文件的输出
            String myreadline = datetime + "[]" + str;

            bw.write(myreadline + "\n"); // 写入文件
            bw.newLine();
            bw.flush(); // 刷新该流的缓冲
            bw.close();
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                bw.close();
                fw.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
            }
        }
    }

    /**
     * 判断应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        boolean hasInstalled = false;
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> list = pm.getInstalledPackages(PackageManager.PERMISSION_GRANTED);
        for (PackageInfo p : list) {
            if (packageName != null && packageName.equals(p.packageName)) {
                hasInstalled = true;
                break;
            }
        }
        return hasInstalled;
    }

    /**
     * 格式化日期字符串 （精确称秒或毫秒）
     *
     * @param datestring 日期字符串
     * @return
     */
    public static String getCalendarFromISO(String datestring) {
        String format;
        if (datestring.length() > 20) {
            format = "yyyy-MM-dd'T'HH:mm:ss.SS";
        } else {
            format = "yyyy-MM-dd'T'HH:mm:ss";
        }
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);

        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = dateformat.parse(datestring);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sdf.format(calendar.getTime());

    }

    public static String getCalendarFromISOWithoutT(String datestring) {
        String format;
        if (datestring.length() > 20) {
            format = "yyyy/MM/dd HH:mm:ss.SS";
        } else {
            format = "yyyy/MM/dd HH:mm:ss";
        }
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);

        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = dateformat.parse(datestring);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sdf.format(calendar.getTime());

    }

    /**
     * 格式化日期字符串 （获取小时）
     *
     * @param datestring 日期字符串
     * @return
     */
    public static int getHousFromISO(String datestring) {
        String format;
        if (datestring.length() > 20) {
            format = "yyyy-MM-dd'T'HH:mm:ss.SS";
        } else {
            format = "yyyy-MM-dd'T'HH:mm:ss";
        }
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);

        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = dateformat.parse(datestring);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTime().getHours();

    }

    /**
     * 格式化日期字符串 （精确称秒或毫秒）
     *
     * @param datestring 日期字符串
     * @return
     */
    public static String getDateFromISO(String datestring) {
        String format;
        if (datestring.length() > 20) {
            format = "yyyy-MM-dd'T'HH:mm:ss.SS";
        } else {
            format = "yyyy-MM-dd'T'HH:mm:ss";
        }
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentTime);

        SimpleDateFormat dateformat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date date = dateformat.parse(datestring);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return sdf.format(calendar.getTime());

    }

    // 向已创建的文件中写入数据
    public static void print(String str, String logType, String filenameTemp) {
        OutputStreamWriter out = null;
        BufferedWriter bw = null;
        String datetime = "";
        File dirPath = new File(Environment.getExternalStorageDirectory().getPath()
                + "/xnsn/management/" + logType);
        File strFile = null;
        try {
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            strFile = new File(dirPath.getPath() + "/" + filenameTemp);
            if (!strFile.exists()) {
                strFile.createNewFile();
            }
            SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            datetime = tempDate.format(new Date()).toString();
            out = new OutputStreamWriter(new FileOutputStream(strFile, true), "UTF-8");
            // 创建FileWriter对象，用来写入字符流
            bw = new BufferedWriter(out); // 将缓冲对文件的输出
            String myreadline = datetime + "[]" + str;
            // myreadline = new String(myreadline.getBytes(), "GBK");
            bw.write(myreadline + "\n"); // 写入文件
            bw.newLine();
            bw.flush(); // 刷新该流的缓冲
            bw.close();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                bw.close();
                out.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
            }
        }
    }

    private SharedPreferences sp;

    /**
     * 根据指定的name获取SharedPreferences
     *
     * @param filename 指定的文件名称
     * @return
     */
    public ActivityUtils SP(String filename) {
        sp = ctx.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return this;
    }

    /**
     * put data to SharedPreferences
     *
     * @param key   键
     * @param value 值
     */
    @SuppressWarnings("unchecked")
    public void putSP(String key, Object value) {
        if (null == sp) {
            return;
        }
        Editor editor = sp.edit();
        if (value instanceof String) {
//            editor.putString(key, (String) value);
            editor.putString(key, Base64.encodeToString(((String) value).getBytes(), Base64.DEFAULT));//字符串 采用base64加解密
            Log.i("liupeng", "key --> " + key + "     value --> " + value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
        } else if (value instanceof Set<?>) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                editor.putStringSet(key, (Set<String>) value);
            } else {
                System.out.println(Build.VERSION.SDK_INT + "is to low,put data failed");
            }
        }
        editor.apply();
//        editor.commit();
    }

    /**
     * get data from SharedPreferences
     *
     * @param key   键
     * @param clazz 指定需要获取的数据的类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getSP(String key, Class<T> clazz) {
        if (null == sp) {
            return null;
        }
        if ((String.class).equals(clazz)) {
//            return (T) sp.getString(key, null);
            return (T) (new String(Base64.decode(sp.getString(key, ""), Base64.DEFAULT)));//字符串 采用base64加解密
        } else if ((Integer.class).equals(clazz)) {
            return (T) Integer.valueOf(sp.getInt(key, Integer.MIN_VALUE));
        } else if ((Float.class).equals(clazz)) {
            return (T) Float.valueOf(sp.getFloat(key, Integer.MIN_VALUE));
        } else if ((Long.class).equals(clazz)) {
            return (T) Long.valueOf(sp.getLong(key, Integer.MIN_VALUE));
        } else if ((Boolean.class).equals(clazz)) {
            return (T) Boolean.valueOf(sp.getBoolean(key, false));
        } else if ((Set.class).equals(clazz)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                return (T) sp.getStringSet(key, null);
            } else {
                System.out.println(Build.VERSION.SDK_INT + "is to low,get data failed");
            }
        }
        return null;
    }

    private static final String DEVICE = "device";
    private static final String DEVICEID = "deviceId";

    /**
     * 统一将当前设备编号写入至SharedPreferences
     */
    public String putDeviceID() {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        SP(DEVICE).putSP(DEVICEID, deviceId);
        return deviceId;
    }

    /**
     * 统一从SharedPreferences获取当前设备编号
     *
     * @return
     */
    public String getDeviceID() {
        String deviceId = SP(DEVICE).getSP(DEVICEID, String.class);
        if (TextUtils.isEmpty(deviceId)) {//检查一遍是否启动没有put进去
            deviceId = putDeviceID();
        }
        return deviceId;
    }

    private static final String USER = "USER";
    private static final String USERID = "USERID";
    private static final String AuthenticationCode = "AuthenticationCode";
    private static final String TrueName = "TrueName";
    private static final String CUSTOMER = "CUSTOMER";
    private static final String USERINFO = "USERINFO";

    public int getUserID() {
        Integer userID = SP(USER).getSP(USERID, Integer.class);
        return userID;
    }

    public void putUserID(int userId) {
        SP(USER).putSP(USERID, userId);
    }

    public int getAuthenticationCode() {
        Integer userID = SP(USER).getSP(AuthenticationCode, Integer.class);
        return userID;
    }

    public void putAuthenticationCode(int authenticationCode) {
        SP(USER).putSP(AuthenticationCode, authenticationCode);
    }

    public void putTrueName(int storageList) {
        SP(USER).putSP(TrueName, storageList);
    }

    public Integer getTrueName() {
        return SP(USER).getSP(TrueName, Integer.class);
    }

//    public void putCustomerList(String customerList) {
//        SP(USER).putSP(CUSTOMER, customerList);
//    }
//
//    public String getCustomerList() {
//        return SP(USER).getSP(CUSTOMER, String.class);
//    }

    public void putUserInfo(String userinfo) {
        SP(USER).putSP(USERINFO, userinfo);
    }

    public String getUserInfo() {
        return SP(USER).getSP(USERINFO, String.class);
    }

    private static final String SERVER_URL = "SERVER_URL";//服务器地址
    private static final String SERVER_PORT = "SERVER_PORT";//服务器端口
    private static final String MQTT_PORT = "MQTT_PORT";//推送端口

    public String getServerUrl() {
        return SP(USER).getSP(SERVER_URL, String.class);
    }

    public void setServerUrl(String serverUrl) {
        SP(USER).putSP(SERVER_URL, serverUrl);
    }

    public String getServerPort() {
        return SP(USER).getSP(SERVER_PORT, String.class);
    }

    public void setServerPort(String serverPort) {
        SP(USER).putSP(SERVER_PORT, serverPort);
    }

    public String getMqttPort() {
        return SP(USER).getSP(MQTT_PORT, String.class);
    }

    public void setMqttPort(String mqttPort){
        SP(USER).putSP(MQTT_PORT, mqttPort);
    }



    private static final String CLIENTS = "CLIENTS";

    /**
     * 根据客户的编号查客户名称
     *
     * @param clientId
     * @return
     */
    public String getClentByClientId(int clientId) {
        sp = ctx.getSharedPreferences(CLIENTS, Context.MODE_PRIVATE);
        Map<String, String> map = (Map<String, String>) sp.getAll();
        if (null == map || map.isEmpty()) {
            return null;
        }
        return SP(CLIENTS).getSP(String.valueOf(clientId), String.class);
    }

    /**
     * 在BEBUG为true模式下<br>
     * 1.控制台简单打印<br>2.保存临时数据方便本地查看 覆盖之前存在的所有数据
     *
     * @param dataString String类型
     */
    public static void saveTempDataToSD(final String dataString) {
        if (!ISDEBUG) {
            return;
        }
//		System.out.println("temp_data>>>" + dataString);
        // TODO Auto-generated method stub
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    File file = new File(Environment.getExternalStorageDirectory(), "temp_data.txt");
                    fileDataOperation(dataString, file);
                }
            }
        }).start();
    }

    /**
     * 写入字符串到指定的文件
     *
     * @param dataString
     * @param file
     */
    private static void fileDataOperation(String dataString, File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                if (null != fw) {
//					fw.write("deviceId:" + getDeviceID()+"\n");
                    fw.write(dataString);
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileWriter fw = new FileWriter(file);
                if (null != fw) {
//					fw.write("deviceId:" + getDeviceID()+"\n");
                    fw.write(dataString);
                    fw.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    /**
     * MD5加密
     *
     * @param string
     * @return
     */
    public static String MD5(String string) {
        if (null != string && !"".equals(string)) {
            StringBuffer sb = new StringBuffer();
            try {
                MessageDigest md5 = MessageDigest.getInstance("Md5");
                byte[] bytes = md5.digest(string.getBytes("UTF-8"));
                for (int i = 0; i < bytes.length; i++) {
                    int value = bytes[i] & 0xff;
                    if (value < 16) {
                        sb.append("0");
                    }
                    sb.append(Integer.toHexString(value));
                }
                string = sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return string;
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                             int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param
     * @return
     */
    private static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    public static String encodeBase64File(String path) throws IOException {
        String type = path.substring(path.lastIndexOf(".") + 1);
        if (type.equals("jpg") || type.equals("png")) {
            Bitmap bm = getSmallBitmap(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (null == bm) {
                return null;
            }
            bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] b = baos.toByteArray();
            return Base64.encodeToString(b, Base64.DEFAULT);
        } else {
            File file = new File(path);
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        }
    }


    /**
     * @param context
     * @param file
     */
    public static void install(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 卸载指定的应用
     *
     * @param context
     * @param pcakageName
     */
    public static void uninstall(Context context, String pcakageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + pcakageName));
        context.startActivity(intent);
    }

    /**
     * 卸载自己
     *
     * @param context
     */
    public static void uninstallMySelf(Context context) {
        uninstall(context, context.getPackageName());
    }


    /**
     * dp  ->  px
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        return (int) (ctx.getResources().getDisplayMetrics().density * dp + 0.5);
    }

    /**
     * dp  ->  px
     *
     * @param dp
     * @return
     */
    public static float dp2px(float dp) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }


    /**
     * sp ->  px
     *
     * @param sp
     * @return
     */
    public static float sp2px(float sp) {
        final float scale = ctx.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }


    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    public static int getScreenSize() {
        return ctx.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 高斯模糊
     *
     * @param bkg
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void blur(Activity activity, Bitmap bkg) {
        long startMs = System.currentTimeMillis();
        float radius = 20;

        bkg = small(bkg);
        Bitmap bitmap = bkg.copy(bkg.getConfig(), true);

        final RenderScript rs = RenderScript.create(activity);
        final Allocation input = Allocation.createFromBitmap(rs, bkg, Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmap);

        bitmap = big(bitmap);
        activity.getWindow().getDecorView().setBackground(new BitmapDrawable(activity.getResources(), bitmap));
        rs.destroy();
        Log.d(TAG, "blur take away time(ms):" + (System.currentTimeMillis() - startMs) + "ms");
    }

    private static Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(4f, 4f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    private static Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.25f, 0.25f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }





    public void showDialogMenu(final Activity ct, final int menu[]) {
        builder = new Dialog(ct, R.style.mydialogstyle);
        builder.setCanceledOnTouchOutside(true);
        ListView lvDialog = new ListView(ct);
        lvDialog.setStackFromBottom(false);
        lvDialog.setAdapter(new BaseAdapter() {

            @Override
            public View getView(int arg0, View arg1, ViewGroup arg2) {
                // TODO Auto-generated method stub
                TextView tv = new TextView(ct);
                tv.setText(menu[arg0]);
                tv.setWidth(arg2.getWidth());
                tv.setHeight(160);
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setId(menu[arg0]);
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(16);
                tv.setPadding(50, 0, 0, 0);
                tv.setBackgroundResource(R.drawable.signature_save_bg);
                return tv;
            }

            @Override
            public long getItemId(int arg0) {
                // TODO Auto-generated method stub
                return arg0;
            }

            @Override
            public Object getItem(int arg0) {
                // TODO Auto-generated method stub
                return menu[arg0];
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return menu.length;
            }
        });
        lvDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Intent it = null;
                builder.dismiss();
                switch (arg1.getId()) {
                    case R.string.camera: // 拍照
                        break;
                    case R.string.photo: // 相册
                        break;
                    case R.string.security_management_2: // 派出所治安管理检查登记
                        if (ActivityUtils.judgeSimState(ct) == 1) {
                            return;
                        }
                        it = new Intent(ct, PublicSecurityManagementActivity.class);
                        ct.startActivity(it);
                        break;

                    //基础台账
                    case R.string.safet_precautions_4: // 重点保护植物登记
                        // it = new Intent(ct, PlantProtectActivity.class);
                        it = new Intent(ct, PlantProtectDetailActivity.class);
                        ct.startActivity(it);
                        break;

                    case R.string.safet_precautions_3: // 野生动物情况登记
                        it = new Intent(ct, AnimalInformationDetailActivity.class);
                        ct.startActivity(it);
                        break;

                    case R.string.basic_account_childmenu_3: // 重点列管人（单位）情况
                        // it = new Intent(ct, ManagedObjectQueryActivity.class);
                        it = new Intent(ct, ManagedObjectListActivity.class);
                        ct.startActivity(it);
                        break;

                    case R.string.basic_account_childmenu_12:// 涉林重点行业
                        it = new Intent(ct, ForestryKeyIndustriesQueryActivity.class);
                        ct.startActivity(it);
                        break;

                    case R.string.basic_account_childmenu_8: // 林地内开采、土石、矿登记
                        it = new Intent(ct, WoodlandMiningListActivity.class);
                        ct.startActivity(it);
                        break;

                    case R.string.basic_account_childmenu_9: // 木材采伐场（点）登记
                        it = new Intent(ct, WoodCuttingListActivity.class);
                        ct.startActivity(it);
                        break;

                    case R.string.basic_account_childmenu_10: // 情报信息登记
                        it = new Intent(ct, ItelligenceInformationActivity.class);
                        ct.startActivity(it);
                        break;

                    case R.string.basic_account_childmenu_11: // 社会情况统计
                        it = new Intent(ct, SocialStatisticsListActivity.class);
                        ct.startActivity(it);
                        break;


                    default:
                        break;
                }
            }
        });
        builder.setContentView(lvDialog);
        builder.show();
    }


    /**
     * 获取Web前台地址
     *
     * @param ct
     * @return
     */
    public static String getServerWebApi(Context ct) {
        SharedPreferences sp = ct.getSharedPreferences("SERVER", Activity.MODE_PRIVATE);
        String wsip = sp.getString("SERVERIP", "");
        String web = sp.getString("WEBPORT", "");

        String serverWebApi = "http://" + wsip + ":" + web + "/";

        if (wsip == null && web == null && serverWebApi.length() < 15) {
            Intent it = new Intent(ct, ApiSettingActivity.class);
            ct.startActivity(it);
        } else {
            return serverWebApi;
        }
        return serverWebApi;
    }


    /**
     * 根据ISO8601格式日期时间得到对应毫秒数
     *
     * @param datestring
     *            ISO8601格式日期时间
     * @return 毫秒数
     */
    public static long getDateMillisFromISO8601(String datestring) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS",
                Locale.getDefault());
        try {
            Date date = dateformat.parse(datestring);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 生成随机数<br>
     * GUID： 即Globally Unique Identifier（全球唯一标识符） 也称作 UUID(Universally Unique
     * IDentifier) 。
     *
     * 所以GUID就是UUID。
     *
     * GUID是一个128位长的数字，一般用16进制表示。算法的核心思想是结合机器的网卡、当地时间、一个随即数来生成GUID。
     *
     * 从理论上讲，如果一台机器每秒产生10000000个GUID，则可以保证（概率意义上）3240年不重复。
     *
     * @return
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 判断消息是否已到通知时间
     *
     * @param
     * @return
     */
    public static boolean getTime(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = null;
        try {
            date1 = formatter.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long timeLong = Calendar.getInstance().getTimeInMillis() - date1.getTime();
        return timeLong >= 0 ? true : false;

    }

    /**
     * 获取WebApi地址
     *
     * @param ct
     * @return web
     */
    public static String getServerApi(Context ct) {
        SharedPreferences sp = ct.getSharedPreferences("SERVER", Context.MODE_PRIVATE);
        String wsip = sp.getString("SERVERIP", "");
        String api = sp.getString("WEBPORT", "");
        String serverApi = "http://" + wsip + ":" + api + "/";

        if (wsip == null && api == null && serverApi.length() < 15) {
            Intent it = new Intent(ct, ApiSettingActivity.class);
            ct.startActivity(it);
        } else {
            return serverApi;
        }
        return serverApi;
    }

    /**
     * 判断当前网络是否为Wifi
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetInfo = connectivityManager.getActiveNetworkInfo();
        if (activenetInfo != null && activenetInfo.isConnected()
                && activenetInfo.getType() == connectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /*
	 * 提示对话框，进入登陆
	 */
    public static void showLogin(final Context context) {

        new AlertDialog.Builder(context).setMessage("以下操作需用用户登录")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        Intent it = new Intent(context, LoginActivity.class);
                        context.startActivity(it);
                    }
                }).setNegativeButton("取消", null).create().show();
    }

    /**
     * 取得相关系统服务 获取SIM卡状态信息
     *
     * @param context
     */
    public static int judgeSimState(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        switch (tm.getSimState()) {
            case TelephonyManager.SIM_STATE_ABSENT:
                Toast.makeText((Activity) context, context.getString(R.string.location_sim),
                        Toast.LENGTH_LONG).show();
                break;
        }
        return tm.getSimState();
    }

    /**
     * 显示下拉框
     *
     * @param activity
     * @param
     * @param sp
     */
    public static void showSpinnerAdapter(Context activity, ArrayList<String> list, Spinner sp) {
        sp.setAdapter(new ArrayAdapter(activity, R.layout.spinner_list_item, R.id.tvSpinnerItem,
                list));
    }

    public static void showSpinnerAdapter(Context activity, List<OptionEntity> list, Spinner sp) {
        sp.setAdapter(new ArrayAdapter(activity, R.layout.spinner_list_item, R.id.tvSpinnerItem,
                list));
        //sp.setAdapter(new SpinnerAdapter((Activity) activity, list));
    }

    public static void showSpinnerAdapter(Context activity, String con, Spinner sp) {
        ArrayList<String> sptca = new ArrayList<String>();
        sptca.add(con);
        sp.setAdapter(new ArrayAdapter(activity, android.R.layout.simple_list_item_1, sptca));
    }

    public static String getDictNameByDictValue(List<OptionEntity> optionList, int dictValue) {
        for (int i = 0; i < optionList.size(); i++) {
            if (optionList.get(i).getDictValue() == dictValue) {
                return optionList.get(i).getDictName();
            }
        }
        return null;
    }


    public static void showCameraOrPhoto(Activity activity, View view) {
        Intent it = null;
        String type = view.getTag().toString()
                .substring(view.getTag().toString().lastIndexOf(".") + 1);
        if (view.getTag() != null && view.getTag().equals(R.drawable.app_panel_add_icon)) {
            //ActivityUtils.showDialogMenu(activity, Constant.CAMERA_OR_PHOTO);
        } else if (view.getTag() != null
                && view.getTag().equals(android.R.drawable.ic_menu_report_image)) {
            Toast.makeText(activity, "服务器文件异常，请验证文件正确性后再试！", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (type.equals("jpg") || type.equals("png")) {
                //it = new Intent(activity, ImageShowActivity.class);
                it.putExtra("filepath", view.getTag().toString());
                it.putExtra("class", activity.getClass());
                activity.startActivityForResult(it, 10);
            } else if (type.equals("mp4") || type.equals("3gp") || type.equals("avi")) {
                // Uri uri = Uri.parse(view.getTag().toString());
                String strUri = view.getTag().toString();
                Uri uri = null;
                if (strUri.startsWith("http")) {
                    uri = Uri.parse(strUri);
                } else {
                    uri = Uri.fromFile(new File(strUri));
                }
                // 调用系统自带视频播放器
                it = new Intent(android.content.Intent.ACTION_VIEW);
                it.setDataAndType(uri, "video/*");
                activity.startActivity(it);
            } else if (type.equals("3gpp") || type.equals("amr") || type.equals("ogg")
                    || type.equals("pcm") || type.equals("mp3")) {
                // Uri uri = Uri.parse(view.getTag().toString());
                String strUri = view.getTag().toString();
                Uri uri = null;
                if (strUri.startsWith("http")) {
                    uri = Uri.parse(strUri);
                } else {
                    uri = Uri.fromFile(new File(strUri));
                }
                // 调用系统自带音乐播放器
                it = new Intent(android.content.Intent.ACTION_VIEW);
                it.setDataAndType(uri, "audio/*");
                activity.startActivity(it);
            }
        }
    }

    public static void showCameraOrPhoto(Activity activity, View view, int stateID,
                                         int hostPoliceID, int coPoliceID) {
        Intent it = null;
        String type = view.getTag().toString()
                .substring(view.getTag().toString().lastIndexOf(".") + 1);
        if (view.getTag() != null && view.getTag().equals(R.drawable.app_panel_add_icon)) {
            //ActivityUtils.showDialogMenu(activity, Constant.CAMERA_OR_PHOTO);
        } else {
            if (type.equals("jpg") || type.equals("png")) {
                //it = new Intent(activity, ImageShowActivity.class);
                it.putExtra("filepath", view.getTag().toString());
                it.putExtra("class", activity.getClass());
                it.putExtra("stateID", stateID);
                it.putExtra("hostPoliceID", hostPoliceID);
                it.putExtra("coPoliceID", coPoliceID);
                activity.startActivityForResult(it, 10);
            } else if (type.equals("mp4") || type.equals("3gp") || type.equals("avi")) {
                Uri uri = Uri.parse(view.getTag().toString());
                Log.e("json1", uri + "aaa");
                // Uri uri = Uri.fromFile(new File(view.getTag().toString()));
                // 调用系统自带视频播放器
                it = new Intent(android.content.Intent.ACTION_VIEW);
                it.setDataAndType(uri, "video/*");
                activity.startActivity(it);
            } else if (type.equals("3gpp") || type.equals("amr") || type.equals("ogg")
                    || type.equals("pcm") || type.equals("mp3")) {
                Uri uri = Uri.parse(view.getTag().toString());
                // Uri uri = Uri.fromFile(new File(view.getTag().toString()));
                // 调用系统自带音乐播放器
                it = new Intent(android.content.Intent.ACTION_VIEW);
                it.setDataAndType(uri, "audio/*");
                activity.startActivity(it);
            }
        }
    }

    /**
     * 根据定位信息获取两个定位点间的距离
     *
     * @param loca1
     * @param
     * @return 距离
     */
    public static double getDistance(Loaction loca1, Loaction location) {
        double distance = 0;
        if (loca1 == null || location == null || location.getLATITUDE() == 3.9E-324
                || location.getLONGITUDE() == 3.9E-324 || location.getLONGITUDE() == 0
                || location.getLATITUDE() == 0) {
            return Double.MAX_VALUE;
        }
        LatLng point = new LatLng(loca1.getLATITUDE(), loca1.getLONGITUDE());
        LatLng point1 = new LatLng(location.getLATITUDE(), location.getLONGITUDE());
        // distance += GetShortDistance(point.longitude, point.latitude,
        // point1.longitude, point1.latitude);
        distance += DistanceUtil.getDistance(point, point1);
        return distance;
    }

}
