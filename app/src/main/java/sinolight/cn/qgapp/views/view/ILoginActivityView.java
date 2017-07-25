package sinolight.cn.qgapp.views.view;

import android.content.Intent;

/**
 * Created by xns on 2017/6/29.
 * LoginActivity View
 */

public interface ILoginActivityView {

    void initShow(String userName);

    void showToastMsg(int msgId);

    void showLoading(boolean enable);

    void gotoActivity(Intent callIntent);
}
