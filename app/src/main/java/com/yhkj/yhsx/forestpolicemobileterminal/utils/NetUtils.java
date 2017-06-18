package com.yhkj.yhsx.forestpolicemobileterminal.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.lzy.okhttputils.exception.OkHttpException;
import com.lzy.okhttputils.request.BaseRequest;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.ApiSettingActivity;


/**
 * 网络连接工具类
 *
 * @author liupeng
 */
public final  class NetUtils {

//    private final static String url = "http://gyl.yinghekeji.com:80/api/"; // 正式版

    //private final static String url = "http://125.64.43.37/api/"; //远程测试

    /**
     * 默认的地址路径，优先级最低，当存在http开头的路径时候这个路径失效
     */

    private static String ipAndPort = "http://zgsnxh.yinghekeji.com:9005";

    private static String url = ipAndPort + "/api/"; //本地测试  MySSM/ http://192.168.1.82:15435  http://zgsnxh.yinghekeji.com:9005

    // private final static String url = "http://221.237.157.106:86/api/";// 新正式版
    private static NetUtils nUtils;

    private ConnectivityManager connectivityManager;
    private NetworkInfo info;

    public static NetUtils getInstance() {
        if (nUtils == null) {
            nUtils = new NetUtils();
        }
        return nUtils;
    }

    public String getUrl() {
        return ipAndPort;
    }

    /**
     * 判断网络情况
     *
     * @param context 上下文
     * @return false 表示没有网络 true 表示有网络
     */
    public boolean isNetworkAvalible(Context context) {
        boolean isOk = false;

        // 获得网络状态管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            isOk = false;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        isOk = true;
                    }
                }
            }
        }
        if (!isOk) {
            Toast.makeText(context, "当前没有可以使用的网络，请设置网络！",
                    Toast.LENGTH_SHORT).show();
        }
        return isOk;
    }

    /**
     * 判断网络状态
     *
     * @param ctx
     * @return
     */
    public Boolean isNetwork(Context ctx) {
        connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    /**
     * 判断是否是wifi网络环境
     *
     * @param ctx
     * @return
     */
    public Boolean isWifi(Context ctx) {
        connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return info.getType() == ConnectivityManager.TYPE_WIFI;
        } else {
            return false;
        }
    }

    /**
     * 获取网络连接
     *
     * @param context
     * @return
     */
    public String getNetWorkSubTypeName(Context context) {
        String newworkSubtype = null;
        // 获得网络状态管理器
        connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            newworkSubtype = null;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        newworkSubtype = net_info[i].getSubtypeName();
                    }
                }
            }
        }
        return newworkSubtype;
    }

    /**
     * 获取WebApi地址
     *
     * @param ct
     * @return
     */
    public String getServerApi(Context ct) {
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
     * 接口
     */
    public static final class API {
        //TODO 常量数据
        public static final String DistrictList = "DistrictList";//省市县数据列表
        public static final String versionUpload = "versionUpload";//版本控制
        public static final String DatadictionaryType = "DatadictionaryType";//根据类型获取字典项
        public static final String AndroidError = "AndroidError";//手机端报错日志上传

        //TODO 我的（用户管理）
        public static final String UserRegister = "UserRegister";//用户注册
        public static final String LOGIN = "UserLogin";//用户登录
        public static final String UserHome = "UserHome";//根据用户编号查询用户信息
        public static final String UserInfoUpload = "UserInfoUpload";//用户基本信息修改
        public static final String UserAuthenticationUpload = "UserAuthenticationUpload";//用户认证
        public static final String IdCardAuthenticationAdd = "IdCardAuthenticationAdd";//用户身份认证
        public static final String SMSVerifyUserPhone = "SMSVerifyUserPhone";//用户认证

        //TODO 生资管理
        public static final String IngredientInfoAdd = "IngredientInfoAdd";//添加生资
        public static final String IngredientInfo = "IngredientInfo";//生资列表
        public static final String SystemIngredient = "SystemIngredient";//获取种植，养殖类型
        public static final String IngredientPurchaseInfo = "IngredientPurchaseInfo";//生资采购使用详情
        public static final String IngredientEmployInfo = "IngredientEmployInfo";//根据生资ID查询生产过程使用生资详情
        public static final String IngredientPurchaseInfoAdd = "IngredientPurchaseInfoAdd";//生资采购
        public static final String IngredientStateUpdate = "IngredientStateUpdate";//状态更新


        //TODO 基地管理
        public static final String BaseInfoAdd = "BaseInfoAdd";//基地添加
        public static final String BaseInfoList = "BaseInfoList";//基地列表
        public static final String BaseInfo = "BaseInfo";//基地详情
        public static final String BaseInfoMaterialAdd = "BaseInfoMaterialAdd";//基地环境添加
        public static final String baseStateUpdate = "baseStateUpdate";//基地环境添加
        public static final String BaseInfoLandList = "BaseInfoLandList";//土地列表

        //TODO 基地管理-土地
        public static final String BaseInfoLandListAdd = "BaseInfoLandListAdd";//土地添加 0 成功 ，1 面积超出 ，56635 错误
        public static final String BaseInfoLandById = "BaseInfoLandById";//根据Id查询土地
        public static final String BaseInfoLandDel = "BaseInfoLandDel";//删除土地

        //TODO 产品管理
        public static final String ProductAdd = "ProductAdd";//产品添加
        public static final String BaseInfoIdLandTypeSelect = "BaseInfoIdLandTypeSelect";//根据基地ID 和基地类型查询所有土地
        public static final String BaseInfoByUserId = "BaseInfoByUserId";//据用户查询所有基地 baseState =1 启用 ， baseState =2停用
        public static final String GetProductListByUserId = "GetProductListByUserId";//根据用户Id查询产品  产品列表
        public static final String GetProductInfoById = "GetProductInfoById";//根据产品ID查询详情
        public static final String GetProductAdvantageById = "GetProductAdvantageById";//根据产品ID查三大优势
        public static final String ProductAdvantageAdd = "ProductAdvantageAdd";//产品三大优势  添加
        public static final String ProductionProcessSelect = "ProductionProcessSelect";//根据产品类型和生资类型 查询需要生资   ingredientType目前固定传值3
        public static final String GetIngredientCountSelectByUserId = "GetIngredientCountSelectByUserId";//根据用户Id 和 生资类型查询所有生资
        public static final String ProductionProcesstAdd = "ProductionProcesstAdd";//生产过程添加
        public static final String ProductionProcesstInfoListByProductId = "ProductionProcesstInfoListByProductId";//根据产品Id查询上产过程列表
        public static final String ProductVideoAdd = "ProductVideoAdd";//根据产品Id添加视频
        public static final String GetProductVideoListByProductId = "GetProductVideoListByProductId";//根据产品Id查询视频列表
        public static final String ProductVideoDelete = "ProductVideoDelete";//根据产品Id添加视频
        public static final String UserCustomProductionProcesstAdd = "UserCustomProductionProcesstAdd";//自定义生产过程添加
        public static final String GetProductByBaseInfoId = "GetProductByBaseInfoId";//根据基地获取产品
        public static final String ProductLabelSelect = "ProductLabelSelect";//根据用户查询产品标签
        public static final String ProductLabelAdd = "ProductLabelAdd";//自定义产品标签
        public static final String ProductCycleUpdateStatus = "ProductCycleUpdateStatus";//自定义产品标签

        //TODO 供应信息
        public static final String GetProductSupplyListByUserId = "GetProductSupplyListByUserId";//根据用户Id查询发布产品
        public static final String GetProductSelectByUserId = "GetProductSelectByUserId";//根据用户id查询产品select列表
        public static final String ProductSupplyAdd = "ProductSupplyAdd";//发布供应产品
        public static final String GetProductSupplyInfoById = "GetProductSupplyInfoById";//发布供应产品
        public static final String PorductSupplyUpdateStatus = "PorductSupplyUpdateStatus";//发布供应产品

        //TODO 受害情况
        public static final String DisasterSituationAdd = "DisasterSituationAdd";//添加受害情况

    }


    /**
     * 通用带有ProgressDialog的请求
     *
     * @param method
     * @param params
     * @param pDialog
     * @return
     */
    public boolean getString(Context cx, String method, Map<String, String> params, final ProgressDialog pDialog, final StringCallbackWithError stringCallback) {
        if (checkNetwork(cx, method, pDialog)) {
            return false;
        }
        stringCallback.setpDialog(pDialog);
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .execute(stringCallback);
        return true;
    }


    /**
     * 通用带有ProgressDialog的请求
     *
     * @param method
     * @param params
     * @param progressDialogTitle
     * @return
     */
    public boolean getString(Context cx, String method, Map<String, String> params, final String progressDialogTitle, final StringCallbackWithError stringCallback) {
        if (checkNetwork(cx, method)) {
            return false;
        }
        final ProgressDialog pDialog = showProgress(cx, progressDialogTitle);
        stringCallback.setpDialog(pDialog);
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .execute(stringCallback);
        return true;
    }


    /**
     * 基于OkHttp通用的请求，需要手动添加服务端错误提示信息
     *
     * @param method
     * @param params
     * @return
     */
    public boolean getString(Context cx, String method, Map<String, String> params, final StringCallback stringCallback) {
        if (checkNetwork(cx, method)) {
            return false;
        }
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .execute(stringCallback);
        return true;
    }

    /**
     * 通用没有ProgressDialog的请求，添加了错误信息提示{@link StringCallbackWithError}
     *
     * @param method
     * @param params
     * @return
     */
    public boolean getString(Context cx, String method, Map<String, String> params, final StringCallbackWithError stringCallbackWithError) {
        if (checkNetwork(cx, method)) {
            return false;
        }
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .execute(stringCallbackWithError);
        return true;
    }


    /**
     * 带有 ProgressDialog 的  Json String请求
     *
     * @param method
     * @param params
     * @param pDialog
     * @param entityCallback
     * @return
     */
    public <T> boolean getString(Context cx, String method, Map<String, String> params, final ProgressDialog pDialog, final Class<T> clazz, final EntityCallbackWithError<T> entityCallback) {
        if (checkNetwork(cx, method, pDialog)) {
            return false;
        }
        StringCallbackWithError stringCallbackWithError = new StringCallbackWithError() {
            @Override
            public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
                entityResolve(s, response, clazz, entityCallback);
            }
        };
        stringCallbackWithError.setpDialog(pDialog);
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .execute(stringCallbackWithError);
        return true;
    }

    /**
     * 带有 ProgressDialog 的   Json String请求
     *
     * @param method
     * @param params
     * @param progressDialogTitle
     * @param entityCallbackWithError
     * @return
     */
    public <T> boolean getString(final Context cx, String method, Map<String, String> params, final String progressDialogTitle, final Class<T> clazz, final EntityCallbackWithError<T> entityCallbackWithError) {
        if (checkNetwork(cx, method)) {
            return false;
        }
        final ProgressDialog pDialog = showProgress(cx, progressDialogTitle);
        StringCallbackWithError stringCallbackWithError = new StringCallbackWithError() {
            @Override
            public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
                entityResolve(s, response, clazz, entityCallbackWithError);
            }
        };
        stringCallbackWithError.setpDialog(pDialog);

        Map<String, String> newMap = new HashMap<>();
        for (Map.Entry<String, String> map : params.entrySet()) {
            newMap.put(map.getKey(), AESUtil.encrypt(map.getValue()));
        }

        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
//                .params(newMap)
                .execute(stringCallbackWithError);
        return true;
    }

    /**
     * 带有 ProgressDialog 的   Json String请求
     *
     * @param method
     * @param params
     * @param progressDialogTitle
     * @param entityCallback
     * @return
     */
    public <T> boolean getString(Context cx, String method, Map<String, String> params, ArrayList<File> files, final String progressDialogTitle, final Class<T> clazz, final EntityCallbackWithError<T> entityCallback) {
        if (checkNetwork(cx, method)) {
            return false;
        }
        final ProgressDialog pDialog = showProgress(cx, progressDialogTitle);
        StringCallbackWithError stringCallbackWithError = new StringCallbackWithError() {
            @Override
            public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
                entityResolve(s, response, clazz, entityCallback);
            }
        };
        stringCallbackWithError.setpDialog(pDialog);
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .addFileParams("file", files)
                .execute(stringCallbackWithError);


        return true;
    }

    /**
     * @param method
     * @param params
     * @param clazz
     * @param entityCallback
     * @return
     */
    public <T> boolean getString(Context cx, String method, Map<String, String> params, ArrayList<File> files, final Class<T> clazz, final EntityCallbackWithError<T> entityCallback) {
        if (checkNetwork(cx, method)) {
            return false;
        }
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .addFileParams("file", files)
                .execute(new StringCallbackWithError() {
                    @Override
                    public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
                        entityResolve(s, response, clazz, entityCallback);
                    }

                });
        return true;
    }

    /**
     * 结合Gson将json字符串转换为对应的实体对象
     *
     * @param s
     * @param response
     * @param clazz
     * @param entityCallback
     * @param <T>
     */
    private <T> void entityResolve(@Nullable String s, @Nullable Response response, Class<T> clazz, EntityCallbackWithError<T> entityCallback) {
        if (response.isSuccessful()) {
            T t = null;
            try {
                t = GsonUtils.fromJson(s, clazz);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof InvocationTargetException || e instanceof NumberFormatException || e instanceof com.google.gson.JsonSyntaxException) {
                    ToastUtility.showToast(R.string.resolve_data_error);
                } else {
                    ToastUtility.showToast(R.string.server_error);
                }
            }
            if (null != t) {
                entityCallback.onSuccess(t);
            } else {
                entityCallback.onFailed();
            }
        } else {
            entityCallback.onFailed();
        }
    }

    /**
     * @param method
     * @param params
     * @param clazz
     * @param entityCallback
     * @return
     */
    public <T> boolean getString(Context cx, String method, Map<String, String> params, final Class<T> clazz, final EntityCallbackWithError<T> entityCallback) {
        if (checkNetwork(cx, method)) {
            return false;
        }

        Map<String, String> newMap = new HashMap<>();
        for (Map.Entry<String, String> map : params.entrySet()) {
            newMap.put(map.getKey(), AESUtil.encrypt(map.getValue()));
        }
        String jsonString = "";
        try {
            jsonString = AESUtil.encrypt(GsonUtils.toJson(params));
            System.out.println("jsonString = " + jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
               // .params("jsonString",jsonString.trim())
                .execute(new StringCallbackWithError() {
                    @Override
                    public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
                        entityResolve(s, response, clazz, entityCallback);
                    }

                });
        return true;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param cx
     * @param method
     * @param jsonString
     * @param progressDialogTitle
     * @param clazz
     * @param jsonCallback
     * @param <T>
     * @return
     */
    public <T extends ResponseBody> boolean getJsonString(Context cx, String method, String jsonString, String progressDialogTitle, final Class<T> clazz, final EntityCallbackWithError<T> jsonCallback) {
        if (checkNetwork(cx, method)) {
            return false;
        }
        final ProgressDialog pDialog = showProgress(cx, progressDialogTitle);
        StringCallbackWithError stringCallbackWithError = new StringCallbackWithError() {
            @Override
            public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
                entityResolve(s, response, clazz, jsonCallback);
            }
        };
        stringCallbackWithError.setpDialog(pDialog);
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .upJson(jsonString)
                .execute(stringCallbackWithError);
        return true;
    }

    /**
     * @param cx
     * @param method
     * @param jsonString
     * @param clazz
     * @param jsonCallback
     * @param <T>
     * @return
     */
    public <T extends ResponseBody> boolean getJsonString(Context cx, String method, String jsonString, final Class<T> clazz, final EntityCallbackWithError<T> jsonCallback) {
        return getJsonString(cx, method, jsonString, "请求中...", clazz, jsonCallback);
    }


    /**
     * 取消请求
     */
    public void cancelRequest() {
        //根据 Tag 取消请求
        OkHttpUtils.getInstance().cancelTag(this);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 统一带有服务端发生错误的响应回调
     */
    public static abstract class StringCallbackWithError extends StringCallback {//implements IProgressDialogCallback<T>

        private ProgressDialog pDialog;

        public void setpDialog(ProgressDialog pDialog) {
            this.pDialog = pDialog;
        }

        @Override
        public void onBefore(BaseRequest request) {
            super.onBefore(request);
            if (pDialog != null && !pDialog.isShowing()) {//开启进度条
                pDialog.show();
            }
        }


        @Override
        public void onAfter(@Nullable String s, @Nullable Exception e) {
            super.onAfter(s, e);
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }

        @Override
        public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);

            analyizeError(e);
        }


//        @Override
//        public void onSuccess(T t) {
//
//        }
//
//        @Override
//        public void onFailed() {
//            ToastUtility.showToast(R.string.request_error);
//        }
    }

    /**
     * 统一带有服务端发生错误的响应回调
     */
    public static abstract class EntityCallbackWithError<T> extends StringCallbackWithError implements IProgressDialogCallback<T> {

        @Override
        public void onSuccess(String s, Call call, Response response) {

        }

        @Override
        public void onFailed() {
            ToastUtility.showToast(R.string.request_error);
        }

    }

    /**
     * 带有进度条的请求回调
     */
    public interface IProgressDialogCallback<T> {
        void onSuccess(T t);

        void onFailed();
    }

    /**
     * 分析服务端返回的异常,友好提示用户
     *
     * @param e
     */
    private static void analyizeError(Exception e) {
        if (e instanceof java.net.ConnectException) {
            ToastUtility.showToast(R.string.server_connect_exception);
        } else if (e instanceof java.net.SocketTimeoutException) {
            ToastUtility.showToast(R.string.server_connecttimeout_exception);
        } else if (e instanceof java.net.UnknownHostException) {
            ToastUtility.showToast(R.string.unknown_server_name_exception);
        } else if (e instanceof OkHttpException) {
            ToastUtility.showToast(R.string.server_url_error_exception);
        } else {
            ToastUtility.showToast(R.string.server_error);
        }
    }

    private static ProgressDialog m_pDialog;

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


    /**
     * @param cx
     * @return
     */
    private boolean checkNetwork(Context cx, String method) {
        return checkNetwork(cx, method, null);
    }

    /**
     * 检查网络情况、网络地址url
     *
     * @param cx
     * @param pDialog
     * @return
     */
    private boolean checkNetwork(Context cx, String method, final ProgressDialog pDialog) {
        if (!isNetwork(cx)) {
            if (cx instanceof Activity) {
                Activity activity = (Activity) cx;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeDialogAndCancelRequest(pDialog);
                    }
                });
            }

            return true;
        }

        if (TextUtils.isEmpty(url) && TextUtils.isEmpty(method)) {//url,method都为空
            if (cx instanceof Activity) {
                Activity activity = (Activity) cx;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtility.showToast("请求地址无效!");
                        closeDialogAndCancelRequest(pDialog);
                    }
                });
            }
            return true;
        } else if (!TextUtils.isEmpty(url) && TextUtils.isEmpty(method)) {//url 非空， method 为空，经验url是否为带有http或者https的完整路径
            method = "";
            if (url.startsWith("http://") || url.startsWith("https://")) {
                return false;
            } else {
                if (cx instanceof Activity) {
                    Activity activity = (Activity) cx;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtility.showToast("请设置正确的服务器地址！");
                            closeDialogAndCancelRequest(pDialog);
                        }
                    });
                }
                return true;
            }
        } else if (TextUtils.isEmpty(url) && !TextUtils.isEmpty(method)) {//url 为空， method 非空,，检验method是否为带有http或者https的完整路径
            url = "";
            if (method.startsWith("http://") || method.startsWith("https://")) {
                return false;
            } else {
                if (cx instanceof Activity) {
                    Activity activity = (Activity) cx;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtility.showToast("请求地址无效!");
                            closeDialogAndCancelRequest(pDialog);
                        }
                    });
                }
                return true;
            }
        } else {//url和method都非空,如果url是非全路径，method是对应的接口，就可以为url+method,例如:url为http://abc.com/,method为login ,这时候url+method就是 http://abc.com/login
            if (method.startsWith("http://") || method.startsWith("https://")) {//如果这时候method中是网络完整路径，url设置为""，将method作为请求路径
                url = "";
            }
        }
        return false;
    }

    /**
     * 关闭进度条、取消所有请求
     *
     * @param pDialog
     */
    private void closeDialogAndCancelRequest(ProgressDialog pDialog) {
        if (pDialog != null && pDialog.isShowing()) {//关闭进度条
            pDialog.dismiss();
        }
        ToastUtility.showToast(R.string.network_error);
        cancelRequest();
    }

}
