package sinolight.cn.qgapp.views.view;



import sinolight.cn.qgapp.adapter.KDBResAdapter;

/**
 * Created by xns on 2017/7/5.
 * 资源库素材标签页 View层接口
 */

public interface IBaiKeFragmentView {

    void showToast(int msgId);

    void showListView(KDBResAdapter adapter);

    void showRefreshing(boolean enable);

    void showLoadMoreing(boolean enable);

    void hasMoreData(boolean hasMore);

    void showFooterView(boolean enable, String msg);

}
