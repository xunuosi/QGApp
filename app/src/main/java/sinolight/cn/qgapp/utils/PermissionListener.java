package sinolight.cn.qgapp.utils;

import java.util.List;

/**
 * Created by admin on 2017/6/28.
 * 权限获取的监听接口
 */

public interface PermissionListener {

    void onGranted();

    void onDenied(List<String> deniedPermission);
}
