package sinolight.cn.qgapp.views.view;


import sinolight.cn.qgapp.adapter.CommonTitleAdapter;

/**
 * Created by xns on 2017/6/29.
 * 资源库视频集合界面
 */

public interface IMaterialListActivityView {

    void showToast(int msgId);

    void showListView(CommonTitleAdapter adapter);

    void showRefreshing(boolean enable);

    void showLoadMoreing(boolean enable);

    void hasMoreData(boolean hasMore);

    void popTreeMenu();

}
