package sinolight.cn.qgapp.views.view;

import sinolight.cn.qgapp.adapter.MasterAdapter;

/**
 * Created by xns on 2017/8/3.
 * 章节Activity的View层
 */

public interface IMasterHomeActivityView {

    void showToast(int msgId);

    void showListView(MasterAdapter adapter);

    void showToastStr(String msg);

    void showRefreshing(boolean enable);

    void showTopBanner(String cover);
}
