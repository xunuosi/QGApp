package sinolight.cn.qgapp.views.view;

import sinolight.cn.qgapp.adapter.CommonTitleAdapter;

/**
 * Created by xns on 2017/8/14.
 * Collect video view frame
 */

public interface ICollectVideoFragmentView {

    void showToast(int msgId);

    void showListView(CommonTitleAdapter adapter);

    void showRefreshing(boolean enable);

    void showLoadMoreing(boolean enable);

    void hasMoreData(boolean hasMore);

    void setHideEmpty(boolean enable);

}
