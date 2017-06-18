package com.yhkj.yhsx.forestpolicemobileterminal.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 *
 * @author liupeng
 */
public final  class ToastUtility {

    private static Context ctx;

    public static void init(Context context) {
        if(null == ctx){
            ctx = context;
        }
    }

    /**
     * 构造函数
     */
    private ToastUtility() {
        // TODO Auto-generated constructor stub
    }

    private static Toast toast;

    public static void showToast(CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
        }else{
            toast.setText(message);
        }
        toast.show();
    }

    public static void showToast(int resId) {
        showToast(ctx.getResources().getText(resId));
    }

//    public void showToast(String message, int gravity) {
//        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
//        toast.setGravity(gravity, 0, 0);
//        toast.show();
//    }
//
//
//    public void showToast(String message, float horizontalMargin, float verticalMargin) {
//        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
//        toast.setMargin(horizontalMargin, verticalMargin);
//        toast.show();
//    }

}
