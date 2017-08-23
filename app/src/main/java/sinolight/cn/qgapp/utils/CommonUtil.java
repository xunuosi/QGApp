package sinolight.cn.qgapp.utils;

import android.os.Build;

/**
 * Created by xns on 2017/8/23.
 * 通用Util
 */

public class CommonUtil {

    /**
     * Like {@link android.os.Build.VERSION#SDK_INT}, but in a place where it can be conveniently
     * overridden for local testing.
     */
    public static final int SDK_INT =
            (Build.VERSION.SDK_INT == 25 && Build.VERSION.CODENAME.charAt(0) == 'O') ? 26
                    : Build.VERSION.SDK_INT;
}
