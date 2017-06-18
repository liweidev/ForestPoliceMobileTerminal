package com.yhkj.yhsx.forestpolicemobileterminal.utils;

/**
 * creator(创建者):wangxiaofei
 * <p>
 * time(创建时间):2017/4/13 8:59
 * <p>
 * productImgContent(描述):处理用户多提交事件
 */

public final  class ClickUtil {
    public static final int SPACE_TIME = 2000;//手机端 click 事件会有大约 300ms 的延迟
    public static long last_click_time = 0;

    /**
     * 处理快速点击问题
     * @return
     */
    public static final boolean isFastDoubleClick() {
        long clickTime = System.currentTimeMillis();
        System.out.println("timeDistance = " + (clickTime - last_click_time));
        if (clickTime - last_click_time <= SPACE_TIME) {
            ToastUtility.showToast("您的操作太频繁，请稍后!");
            return true;
        } else {
            last_click_time = clickTime;
            return false;
        }
    }

}
