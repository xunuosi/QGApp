package sinolight.cn.qgapp.views.view;

import sinolight.cn.qgapp.adapter.KDBResAdapter;

/**
 * Created by xns on 2017/8/14.
 * Collect book view frame
 */

public interface ICollectBookFragmentView {

    void showToast(int msgId);

    void showListView(KDBResAdapter adapter);

    void showRefreshing(boolean enable);

    void showLoadMoreing(boolean enable);

    void hasMoreData(boolean hasMore);

}
