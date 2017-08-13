package sinolight.cn.qgapp.views.view;


import sinolight.cn.qgapp.data.http.entity.StdInfoEntity;

/**
 * Created by xns on 2017/6/29.
 * This is knowledge database view interface about resource of Standard.
 */

public interface IKDBStdDetailActivityView {

    void showRefreshing(boolean enable);

    void showErrorToast(int msgId);

    void init2Show(StdInfoEntity stdData);

    void showStrToast(String msg);

    void setCollectState(boolean enable);
}
