package sinolight.cn.qgapp.views.view;



import sinolight.cn.qgapp.adapter.CommonTitleAdapter;

/**
 * Created by xns on 2017/7/5.
 * 资源库素材标签页 View层接口
 */

public interface IPicSetActivityView {

    void showToast(int msgId);

    void showStrToast(String msg);

    void showListView(CommonTitleAdapter adapter);

    void showRefreshing(boolean enable);

    void showLoadMoreing(boolean enable);

    void hasMoreData(boolean hasMore);

}
