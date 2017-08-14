package sinolight.cn.qgapp.views.view;

import sinolight.cn.qgapp.adapter.MyDatabaseAdapter;

/**
 * Created by xns on 2017/8/14.
 * My Database view frame
 */

public interface IMyDatabaseActivityView {

    void showToast(int msgID);

    void showToast(String msg);

    void setRefreshEnable(boolean enable);

    void showListView(MyDatabaseAdapter adapter);
}
