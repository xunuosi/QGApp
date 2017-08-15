package sinolight.cn.qgapp.views.view;

import android.content.Intent;

/**
 * Created by xns on 2017/8/15.
 * System activity view frame
 */

public interface ISystemActivityView {

    void gotoActivity(Intent intent);

    void showToast(int msgID);

    void showToast(String msg);
}
