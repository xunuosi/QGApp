package sinolight.cn.qgapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tcloud on 12/7/16..
 * Sharedpreference工具类
 */

public class SharedPfUtil {
    private static final String FILE_NAME = "shared_date";

    public static void setParam(Context context, String key, Object obj) {
        String type = obj.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (type.equals("String")) {
            editor.putString(key, (String) obj);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) obj);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) obj);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) obj);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) obj);
        }

        editor.apply();
    }

    public static <T> T getParam(Context context, String key, T defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return (T) sp.getString(key, null);
        } else if ("Integer".equals(type)) {
            return (T) (Integer) sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return (T) (Boolean) sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return (T) (Float) sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return (T) (Long) sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }
}
