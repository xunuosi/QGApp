package sinolight.cn.qgapp.views.view;

import android.content.Intent;

/**
 * Created by xns on 2017/8/29.
 * SearchActivity View
 */

public interface ISearchActivityView {

    void showToast(int msgId);

    void showStrToast(String msg);

    void gotoActivity(Intent intent);

    void showLoading(boolean enable);
}
