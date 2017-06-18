package com.yhkj.yhsx.forestpolicemobileterminal.utils;

import android.util.Log;

/**
 * Created by qianhaohong on 2017/6/15.
 */

public class LogUtils {
    public static final boolean ISDEBUG=true;

    public static void d(String msg){
        if(ISDEBUG){
            Log.d("TAG",msg);
        }
    }


}
