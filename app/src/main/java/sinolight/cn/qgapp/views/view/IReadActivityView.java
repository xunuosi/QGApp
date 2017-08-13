package sinolight.cn.qgapp.views.view;


import sinolight.cn.qgapp.data.http.entity.ReaderEntity;

/**
 * Created by xns on 2017/6/29.
 * 阅读界面
 */

public interface IReadActivityView {

    void showToast(int msgId);

    void showRefreshing(boolean enable);

    void showData(ReaderEntity readData);

    void showFooter(String name);

    void showStrToast(String msg);

    void setCollectState(boolean enable);
}
