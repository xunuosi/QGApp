package sinolight.cn.qgapp.views.view;

import sinolight.cn.qgapp.adapter.CommonTitleAdapter;

/**
 * Created by xns on 2017/8/14.
 * Collect book view frame
 */

public interface ICollectCookFragmentView {

    void showToast(int msgId);

    void showListView(CommonTitleAdapter adapter);

    void showRefreshing(boolean enable);

    void showLoadMoreing(boolean enable);

    void hasMoreData(boolean hasMore);

    void setHideEmpty(boolean enable);

}
