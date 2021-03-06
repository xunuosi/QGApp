package sinolight.cn.qgapp.utils;

import android.util.Log;

/**
 * Created by xns on 2017/7/3.
 * Log输出控制类
 */

public class L {
    // 控制台输出工具类的控制开关
    public static boolean isDebug = false;
    private static final String TAG = "QGApp";

    public static void i(String msg){
        if(isDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg){
        if(isDebug) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg){
        if(isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void v(String msg){
        if(isDebug) {
            Log.v(TAG, msg);
        }
    }

    public static void i(String tag, String msg){
        if(isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg){
        if(isDebug) {
            Log.v(tag, msg);
        }
    }
}