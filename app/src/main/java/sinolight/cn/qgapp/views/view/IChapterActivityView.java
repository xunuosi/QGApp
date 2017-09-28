package sinolight.cn.qgapp.views.view;

import sinolight.cn.qgapp.adapter.KDBResAdapter;

/**
 * Created by xns on 2017/8/3.
 * 章节Activity的View层
 */

public interface IChapterActivityView {

    void showToast(int msgId);

    void showListView(KDBResAdapter adapter, String title);

    void showToastStr(String msg);

    void showRefreshing(boolean enable);

    void showLoadMoreing(boolean enable);

    void hasMoreData(boolean hasMore);
}
