package sinolight.cn.qgapp.views.view;



import sinolight.cn.qgapp.adapter.KDBResAdapter;

/**
 * Created by xns on 2017/6/29.
 * 行业库资源界面
 */

public interface IEBookActivityView {

    void initShow(String title);

    void popTreeMenu();

    void showToast(int msgId);

    void showListView(KDBResAdapter adapter);

    void showRefreshing(boolean enable);

    void showLoadMoreing(boolean enable);

    void hasMoreData(boolean hasMore);

    void changeSortView(int position, int state);

}
