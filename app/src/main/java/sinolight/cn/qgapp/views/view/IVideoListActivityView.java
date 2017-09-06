package sinolight.cn.qgapp.views.view;


import sinolight.cn.qgapp.adapter.VideoAdapter;

/**
 * Created by xns on 2017/6/29.
 * 资源库视频集合界面
 */

public interface IVideoListActivityView {

    void showToast(int msgId);

    void showToastByStr(String msg);

    void showListView(VideoAdapter adapter);

    void showRefreshing(boolean enable);

    void showLoadMoreing(boolean enable);

    void hasMoreData(boolean hasMore);

}
