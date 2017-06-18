package com.yhkj.yhsx.forestpolicemobileterminal.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ToastUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 父Activity,所有Activity都要继承
 */
public abstract class ParentActivity extends AppCompatActivity {
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1 << 3;
    private static final int CAMERA_REQUEST_CODE = 1 << 4;


    protected int windowWidth, windowHeight;//系统窗体的宽度、高度

    protected static final int PAGESIZE = 10;//默认的获取页数

    private File file;

    protected SparseArray<File> mSparseArrayFiles = null;
    protected SparseArray<String> mSparseArrayStrings = null;//String:代表界面显示的第几个图片
    protected List<String> mStringLists = null;
    protected int addImageToCollectiontRemark = 0;//添加选择的图片的容器的标志，默认添加到mMapFiles和mStringFiles

    public static List<Activity> mActivityLists = new ArrayList<>();

    protected int operationImgIndex = 0;//操作第几个图片


//    @BindView(R.id.recyclerView)
    //@Nullable
    protected RecyclerView recyclerView;

    protected Activity mContext;

    private Dialog builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID());
        ButterKnife.bind(this);
        mContext = this;
        mActivityLists.add(this);

        windowWidth = getResources().getDisplayMetrics().widthPixels;
        windowHeight = getResources().getDisplayMetrics().heightPixels;

        initView();
        initData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(this);
    }

    /**
     * 简单封装去掉左右空格的getText()
     *
     * @param view
     * @return
     */
    protected String getTrimText(TextView view) {
        return view != null ? view.getText().toString().trim() : "note:view is null";
    }

    /**
     * 是否是空字符串
     *
     * @param view
     * @return true:为空 , false:不为空
     */
    protected boolean isNullString(TextView view) {
        return TextUtils.isEmpty(getTrimText(view));
    }

    /**
     * 是否是空字符串
     *
     * @param string
     * @return
     */
    protected boolean isNullString(String string) {
        return TextUtils.isEmpty(string);
    }

    /**
     * 父类为TextView必填项为空的时候进行提示
     *
     * @param view 需要验证的控件
     * @param msg  提示信息，不需要加:不为空
     * @return true:这个字段为空进行提示，false:这个字段不为空，不进行提示
     */
    protected boolean showNullStringInfo(TextView view, String msg) {
        if (isNullString(view)) {
            ToastUtility.showToast(msg + "不能为空,请重新输入");
            return true;
        }
        return false;
    }

    /**
     * 父类为TextView必填项为空的时候进行提示
     *
     * @param string 需要验证的字符串
     * @param msg    提示信息，不需要加:不能为空
     * @return true:这个字段为空进行提示，false:这个字段不为空，不进行提示
     */
    protected boolean showNullStringInfo(String string, String msg) {
        if (isNullString(string)) {
            ToastUtility.showToast(msg + "不能为空,请重新输入");
            return true;
        }
        return false;
    }

    /**
     * 布局资源的位置，必须要继承并且有正确的布局位置
     * <br/>比如返回:R.layout.activity_main
     *
     * @return
     */
    protected abstract int layoutResID();

    /**
     * 初始化视图，可以为空代码
     */
    protected abstract void initView();

    /**
     * 初始化数据，可以为空代码
     */
    protected abstract void initData();


    /**
     * 添加照片
     *
     * @param requestCode 请求码
     */
    private void takePhotos(int requestCode) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory() + File.separator + getPackageManager() + File.separator + "images" + File.separator + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(it, requestCode);
        } else {
            ToastUtility.showToast("请确认已经插入SD卡");
        }
    }

    /**
     * 照片
     *
     * @param requestCode
     */
    protected void takeOrReplaceNewPhoto(final int requestCode) {
        takePhotos(requestCode);
    }

    @Deprecated
    protected void receivePhoto(File file) {
        if (null != file) {
            if (addImageToCollectiontRemark == 0) {//标志0
                mSparseArrayFiles.put(operationImgIndex, file);//往集合中添加文件
                mSparseArrayStrings.put(operationImgIndex, file.getPath());//往集合中添加文件路径
                operationImgIndex++;
            } else if (addImageToCollectiontRemark == 1) {//标志1
                mStringLists.add(file.getPath());
            }
            if (null != recyclerView && addImageToCollectiontRemark == 0) {
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        } else {
            ToastUtility.showToast("获取照片失败，请再试一次");
        }
    }

    /**
     * 6.0系统以上获取权限
     *
     * @return
     */
    private boolean checkMPermission() {
        /**
         * 解决6.0（版本23）危险权限申请的问题
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断版本
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {//确认是否有对应的权限
                ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);//没有相应的需要主动申请
                return true;
            }
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);//没有相应的需要主动申请
                return true;
            }
        }
        return false;
    }

    protected static final int PHOTO_REQUESTCODE = 1;
    protected static final int GALLERY_REQUESTCODE = 3;

    /**
     * 6.0 系统权限问题
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takeOrReplaceNewPhoto(PHOTO_REQUESTCODE);
                } else {
                    new AlertDialog.Builder(mContext).setTitle("系统提示").setMessage("请允许打开摄像头权限或到设置中打开摄像头权限").setPositiveButton("确定", null).create().show();
                }
                break;
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takeOrReplaceNewPhoto(PHOTO_REQUESTCODE);
                } else {
                    new AlertDialog.Builder(mContext).setTitle("系统提示").setMessage("请授予该应用读写本地照片的权限").setPositiveButton("确定", null).create().show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * @param ct
     * @param menu
     */
    public void showDialogMenu(final Activity ct, final int menu[]) {
        if (checkMPermission()) {
            return;
        }

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
                        takePhotos(PHOTO_REQUESTCODE);
                        break;
                    case R.string.photo: // 相册
                        Intent intent = new Intent(
                                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, GALLERY_REQUESTCODE);
                        break;
                    default:
                        break;
                }
            }
        });
        builder.setContentView(lvDialog);
        builder.show();
    }

}
