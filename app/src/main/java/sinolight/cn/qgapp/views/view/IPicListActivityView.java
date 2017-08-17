package sinolight.cn.qgapp.views.view;



import sinolight.cn.qgapp.adapter.KDBResAdapter;

/**
 * Created by xns on 2017/7/5.
 * 资源库图片列表View层接口
 */

public interface IPicListActivityView {

    void showToast(int msgId);

    void showStrToast(String msg);

    void showListView(KDBResAdapter adapter);

    void showRefreshing(boolean enable);

    void showLoadMoreing(boolean enable);

    void hasMoreData(boolean hasMore);

}
