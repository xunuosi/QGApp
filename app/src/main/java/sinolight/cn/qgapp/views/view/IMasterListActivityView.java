package sinolight.cn.qgapp.views.view;


import sinolight.cn.qgapp.adapter.MasterAdapter;

/**
 * Created by xns on 2017/6/29.
 * 资源库视频集合界面
 */

public interface IMasterListActivityView {

    void showToast(int msgId);

    void showToastStr(String msg);

    void showListView(MasterAdapter adapter);

    void showRefreshing(boolean enable);

    void showLoadMoreing(boolean enable);

    void hasMoreData(boolean hasMore);

    void popTreeMenu();

    void hideKeyboard(boolean enable);

}
